package com.job.lr.web.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;


import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Task;
import com.job.lr.filter.Constants;
import com.job.lr.rest.TaskRestController;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.task.TaskService;
import com.job.lr.tools.UserPhoneTools;
import com.job.sendSms.SDKSendTemplateSMS;

/**
 * Phonenumber  的Restful API的Controller.
 * 
 * @author liuy
 */

@RestController
@RequestMapping(value = "/api/v1/phoneCollect")
public class PhoneRestController {
	
	private static Logger logger = LoggerFactory.getLogger(PhoneRestController.class);

	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private UserPhoneTools userPhoneTools;
	
	/**
	 * 根据 手机号和 验证码
	 *   核查phonenumber的验证码是否正确  是否匹配
	 *   需要验证生成验证码的时间是否超时
	 *   
	 *   比对Phonenumber中的对象
	 * @param 
	 * 		phonenumber
	 * 		captchacode
	 * @return 
	 * 		{@value}  url: /api/v1/phoneCollect/checkPhonenumber
	 * 
	 * 核实手机和验证码是否匹配 ？
	 * url ：
	 * 	http://localhost/lr/api/v1/phoneCollect/checkPhonenumber?phonenumber=13662127862&captchacode=3361
	 */
	@RequestMapping(value = "/checkPhonenumber", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse  checkPhonenumber(ServletRequest request) {
		System.out.println("in PhoneRestController() checkPhonenumber方法 ");
		//1 匹配	0 不匹配
		int returncode = 0 ;
		String phonenumber = request.getParameter("phonenumber");
		String captchacode = request.getParameter("captchacode");
		if("".equals(phonenumber)||"".equals(captchacode) ||phonenumber==null||captchacode==null ){			
		}else{
			phonenumber =phonenumber.trim();
			captchacode =captchacode.trim();
			Phonenumber p = accountService.findUserPhoneInPhonenumber(phonenumber);
			if(p== null){
				returncode = 0 ; //没有相关对象 ，不做比对 返回不匹配
			}else{
				Date startDate = p.getRegisterDate() ;
				int no_outtime= accountService.compareTimes( startDate ,new Date(), Constants.SMS_Gap_Time) ;
				if (no_outtime == 1){
					//未超时
					returncode = accountService.checkUserPhone(phonenumber, captchacode); //作比对
				}else{
					//超时
					returncode = 0 ; //不做比对，表示验证码不匹配
					//重新生成新的验证码 和 生成时间
					accountService.updatePhonenumber(p);
				}
				
			}
			
		}
		
		GeneralResponse gp = new GeneralResponse();
		gp.setRetCode(returncode);
		if(returncode == 0){
			//0 不匹配
			gp.setRetInfo("验证码不匹配");
		}else if(returncode == 1){
			//1  匹配
			gp.setRetInfo("验证码匹配");
		}else{
			gp.setRetInfo("phone未知错误 1001");
		}		
		return gp;

	}
	
	
	/**
	 * 	找回密码时，根据手机号码 生成 验证码
	 * 		 验证码的修改时间为 Constants.SMS_Gap_Time*分钟  
	 * 
	 *  ---- 短信接入
	 *  
	 *     向已激活的手机号，  发送手机验证码，每次请求都会发送
	 * 
	 *  @param
	 *  	phonenumber  
	 *  
	 *  @return 
	 *  returnCode -1 不存在相应的用户手机号
	 *  			   1  短信发送成功
	 *  			   0  短信发送失败
	 *  {@value}  url: 
	 * 	http://localhost/lr/api/v1/phoneCollect/genCaptchacodeByPhoneInFindPasswd?phonenumber={phonemum}
	 * 
	 *  
	 *  	
	 * */
	@RequestMapping(value = "/genCaptchacodeByPhoneInFindPasswd", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public GeneralResponse  genCaptchacodeByPhoneInFindPasswd (@RequestParam("phonenumber") String phonenum) {
		
		GeneralResponse gp = new GeneralResponse();
		int errcode = -1 ;
		String errmsg = "不存在相应的用户手机号";
		int successcode = 1 ;
		String successmsg = "已向对应手机号发送短信验证码，短信发送成功";
		int senderr = 0;
		String senderrmsg="向对应手机号发送验证码短信失败";
		int err2code = -2 ;
		String err2msg="未知错误";
		
		int returncode = userPhoneTools.genCaptchacodeByPhoneInFindPasswd(phonenum) ;
		if (returncode == errcode){
			gp.setRetCode(errcode);
			gp.setRetInfo(errmsg);			
		}else if(returncode == successcode){
			gp.setRetCode(successcode);
			gp.setRetInfo(successmsg);	
		}else if(returncode == senderr){
			gp.setRetCode(senderr);
			gp.setRetInfo(senderrmsg);
		}else{
			gp.setRetCode(err2code);
			gp.setRetInfo(err2msg);
		}
		
		return gp;
		
	}
		
	
	/**
	 * 	注册时，根据手机号码 生成 验证码        验证码的修改时间为 Constants.SMS_Gap_Time*分钟  
	 *  ---- 短信接入
	 *  
	 * 	         未有号码  增加验证码
	 *     未激活  未超时  不变任何信息（Phonenumber）
	 *     未激活  已超时 更换验证码 和 生成时间（Phonenumber）
	 *     
	 *     已激活  返回 已激活信息 
	 * 
	 *  @param
	 *  	phonenumber
	 *      phonestatus   0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
	 *  
	 *  @return 
	 *  	returnCode 0  未激活状态
	 *  			   1  手机已激活
	 *  			   2  短信发送失败
	 *  			   3  空值 
	 * 		{@value}  url: 
	 * 				http://localhost/lr/api/v1/phoneCollect/genCaptchacodeByPhone?phonenumber=
	 * 
	 *  注：
	 *  	PhoneRestController类色 genCaptchacodeByPhone() 相同于 UserPhoneTools genCaptchacodeByPhone()，后续考虑移植
	 * */
	@RequestMapping(value = "/genCaptchacodeByPhone", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse  genCaptchacodeByPhone(ServletRequest request) {
		
		int phonestatus_not_activated = 0; 
		int phonestatus_activated = 1; 
		int phone_no_send_sms = 2; 
		int null_phone  = 3; 
		
		int phonestatus_not_activated_flag = 0;
		int phonestatus_activated_flag = 1;	
		int phone_sms_not_send_flag = 2;
		int phone_null_flag = 3;
		
		int returnCode = phonestatus_not_activated_flag; 
		Date phoneRegisterDate = new Date();
		 
		String phonenumber = request.getParameter("phonenumber");		
		if("".equals(phonenumber)||phonenumber==null){
			//do nothing
			returnCode =  null_phone;
		}else{		
			//			
			Phonenumber p = accountService.findUserPhoneInPhonenumber(phonenumber);
			if (p == null){
				//数据库中没有响应的手机号----  新用户
				String captchacode = UserPhoneTools.getRandomString(Constants.CaptchacodeSize) ;//Constants.CaptchacodeSize  随机码位数							
				/**
				 * 发送短信 SendTemplateSMS* 
				 * 
				 * 
				 * */						
				SDKSendTemplateSMS s = new  SDKSendTemplateSMS();						
				Integer SMS_Gap_TimeI = Constants.SMS_Gap_Time ;
				phonenumber=phonenumber.trim();
				String message = s.SendTemplateSMS(phonenumber ,captchacode, "",  SMS_Gap_TimeI.toString()); //"" 是 短信模板数
				String sendOkflag ="sendok"; 
				
				if (sendOkflag.equals(message) ){  //短信发送成功验证处  liuy add  #####
				//if (true){ 	
					accountService.registerUserPhone(phonenumber,captchacode);	
					returnCode = phonestatus_not_activated; 
				}else{
					//调用短信接口 ，短息发送失败
					returnCode = phone_no_send_sms ;
				}				
				
			}else{
				//老用户
				int nowstatus =  p.getPhonestatus();
				if (nowstatus == phonestatus_not_activated ){
					//分辨验证码是否超时 
					int gap_time = Constants.SMS_Gap_Time; //两分钟  超时
					
					//返回时间： 0    超时  ； 1   未超时
					int  istimeoutflag =accountService.compareTimes(p.getRegisterDate(), new Date(), Constants.SMS_Gap_Time) ;
					if(istimeoutflag == 0){// 0    超时
						//重新生成验证码 和 日期 ，后 保存
						
						String captchacode = UserPhoneTools.getRandomString(Constants.CaptchacodeSize) ;//Constants.CaptchacodeSize  随机码位数
						p.setCaptchacode(captchacode);
						/**
						 * 发送短信 SendTemplateSMS* 
						 * */						
						SDKSendTemplateSMS s = new  SDKSendTemplateSMS();						
						Integer SMS_Gap_TimeI = Constants.SMS_Gap_Time ;
						String message = s.SendTemplateSMS(p.getPhonenumber() ,captchacode, "",  SMS_Gap_TimeI.toString()); //1 是 短信模板数
						String sendOkflag ="sendok";
						p.setRegisterDate(new Date()); //现在的时间 
						if (sendOkflag.equals(message) ){  //短信发送成功验证处  liuy add  #####
						//if (true){ 							
							accountService.updatePhonenumber(p);
							returnCode = phonestatus_not_activated; 
						}else{
							//调用短信接口 ，短息发送失败
							returnCode = phone_no_send_sms ;
						}						
											
					}else{// 1   未超时
						//不做改动
						phoneRegisterDate = p.getRegisterDate() ;
						returnCode = phonestatus_not_activated_flag ; //手机未激活
					}
					
				}else if(nowstatus == phonestatus_activated) {
					//手机已激活
					returnCode = phonestatus_activated_flag ; //手机已激活
				}
			}
		}
		
		GeneralResponse gp = new GeneralResponse();
		gp.setRetCode(returnCode);
		if(returnCode == phonestatus_not_activated_flag){
			//0  未激活
			SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMddHHmmss");
			String phoneRegisterDateString = myFmt.format(phoneRegisterDate);
			gp.setRetInfo("手机未激活,验证码已发送到此手机号，有效时间为"+Constants.SMS_Gap_Time+"分钟，超过时间后，请重新请求本接口，会重新生成新的验证码发送到用户手机.验证码生成时间为:"+phoneRegisterDateString);
			
		}else if(returnCode == phonestatus_activated_flag){
			//1 手机已激活
			gp.setRetInfo("手机已激活，需要先解绑手机号，才能再次注册本号码");
		}else if(returnCode == phone_sms_not_send_flag ){
			//2 短信发送失败
			gp.setRetInfo("短信发送失败");
		}else if(returnCode == phone_null_flag){
			//3  手机号空值
			gp.setRetInfo("手机号空值");
		}else{
			gp.setRetInfo("phone未知错误1002");
		}		
		return gp;
		
		
	}
	
	/**
	 * 产生验证码
	 * 
	 * @author ly 
	 * 
	 * 现使用固定号码  作为验证码  9951
	 * 当使用正常短信时 可以修改此接口
	 * 
	 * canbechang  ####
	 * 
	 * */
//	public static String getRandomString(int length) {   
//	      //StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
//	      StringBuffer buffer = new StringBuffer("012345678998765432100123456789");	      
//	      StringBuffer sb = new StringBuffer();   
//	      Random random = new Random();   
//	      int range = buffer.length();   
//	      for (int i = 0; i < length; i ++) {   
//	         sb.append(buffer.charAt(random.nextInt(range)));   
//	      }   
//	      
//	      return sb.toString();   
//	      //return "5123";
//	}
	
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}


	public UserPhoneTools getUserPhoneTools() {
		return userPhoneTools;
	}


	public void setUserPhoneTools(UserPhoneTools userPhoneTools) {
		this.userPhoneTools = userPhoneTools;
	}
	
	
}
