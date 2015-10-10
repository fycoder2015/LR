package com.job.lr.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.filter.Constants;
import com.job.lr.service.account.AccountService;
import com.job.sendSms.SDKSendTemplateSMS;

@Component
public class UserPhoneTools {
	
	private static Logger logger = LoggerFactory.getLogger(UserPhoneTools.class);
	@Autowired
	private AccountService  accountService;


	/**
	 * 根据 手机号和 验证码
	 *   核查phonenumber的验证码是否正确  是否匹配
	 *   需要验证生成验证码的时间是否超时
	 * -------------------------------------
	 * 功能描述：
	 * 		核实手机和验证码是否匹配 ,另外验证码是否超时 
	 * -------------------------------------  
	 * @param 
	 * 		phonenumber
	 * 		captchacode
	 * @return 
	 * 		 returncode	0		 验证码不匹配
	 * 					1		 验证码匹配
	 * 					其他值 	phone未知错误 1001
	 * 
	 *  
	 */
	public Integer checkPhonenumCaptchacodeMatch(String phonenumber,String captchacode) {
		System.out.println("in UserPhoneTools  checkPhonenumCaptchacodeMatch() 方法 ");
		//0 不匹配  1 匹配
		Integer returncode = 0 ;
		phonenumber =phonenumber.trim();
		captchacode =captchacode.trim();		
		Phonenumber p = accountService.findUserPhone(phonenumber);  //倒序查找最新的一个 Phonenumber
		if(p== null){
			returncode = 0 ; //没有相关对象 ，不做比对 返回不匹配
		}else{
			Date startDate = p.getRegisterDate() ;
			int no_outtime= accountService.compareTimes( startDate ,new Date(), Constants.SMS_Gap_Time) ;
			if (no_outtime == 1){
				//未超时
				returncode = accountService.checkUserPhone2(p, captchacode); //作比对
			}else{
				//超时
				returncode = 0 ; //不做比对，表示验证码不匹配
				////重新生成新的验证码 和 生成时间
				//accountService.updatePhonenumber(p);
			}				
		}			
		return returncode;		
	}
	
	/**
	 * 根据phonenumber生成短信验证码
	 * 验证码的修改时间为 Constants.SMS_Gap_Time*分钟  
	 * 
	 * -------------------------------------
	 * 功能描述：
	 * 		根据phonenumber
	 *    	Phonenumber对象的调整
	 *    	未有号码  增加验证码
	 *     	未激活  未超时  不变任何信息（Phonenumber）
	 *     	未激活  已超时 更换验证码 和 生成时间（Phonenumber）
	 *     
	 *     	已激活  返回 已激活信息 
	 * -------------------------------------  
	 * @param 
	 * 		phonenumber
	 * 		
	 * @return 
	 *  	returnCode 0  未激活状态
	 *  			   1  手机已激活
	 *  			   2  短信发送失败
	 *  			   3  空值 
	 *  
	 *  注：
	 *  PhoneRestController类色 genCaptchacodeByPhone() 相同于 UserPhoneTools genCaptchacodeByPhone()，后续考虑移植
	 *  
	 */
	public GeneralResponse genCaptchacodeByPhone(String phonenumber ) {	
		
		Integer phonestatus_not_activated = 0; 
		Integer phonestatus_activated = 1; 
		Integer phone_no_send_sms = 2; 
		Integer null_phone  = 3; 		
		Integer phonestatus_not_activated_flag = 0;
		Integer phonestatus_activated_flag = 1;	
		Integer phone_sms_not_send_flag = 2;
		Integer phone_null_flag = 3;
		
		Integer returnCode = phonestatus_not_activated_flag; 
		Date phoneRegisterDate = new Date();
		
	
		Phonenumber p = accountService.findUserPhone(phonenumber);
		if (p == null){
			//数据库中没有响应的手机号----  新用户
			String captchacode = getRandomString(Constants.CaptchacodeSize) ;//Constants.CaptchacodeSize  随机码位数							
			/**
			 * 发送短信 SendTemplateSMS* 
			 * */						
			SDKSendTemplateSMS s = new  SDKSendTemplateSMS();						
			Integer SMS_Gap_TimeI = Constants.SMS_Gap_Time ;
			phonenumber=phonenumber.trim();
			String message = s.SendTemplateSMS(phonenumber ,captchacode, "",  SMS_Gap_TimeI.toString()); //"" 是 短信模板数
			String sendOkflag ="sendok"; 				
			if (sendOkflag.equals(message) ){  
				//短信发送成功验证处  liuy add  #####	
				accountService.registerUserPhone(phonenumber,captchacode);	
				returnCode = phonestatus_not_activated; 
			}else{
				//调用短信接口 ，短息发送失败
				returnCode = phone_no_send_sms;
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
					String captchacode = getRandomString(Constants.CaptchacodeSize) ;//Constants.CaptchacodeSize  随机码位数
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
				returnCode = phonestatus_activated_flag  ; //手机已激活
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
	 * 当使用正常短信时 可以修改此接口
	 * 
	 * canbechang  ####
	 * 
	 * */
	public static String getRandomString(int length) {   
	      //StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
	      StringBuffer buffer = new StringBuffer("012345678998765432100123456789");	      
	      StringBuffer sb = new StringBuffer();   
	      Random random = new Random();   
	      int range = buffer.length();   
	      for (int i = 0; i < length; i ++) {   
	         sb.append(buffer.charAt(random.nextInt(range)));   
	      }   
	      
	      return sb.toString();   
	      //return "5123";
	}
	
	
	/**
	 * 将Phonenumber对象中的，手机号状态置为未激活状态
	 *   //0 ,未激活  not_activated ； 1，已激活 ； 
	 * 解绑手机号
	 * -------------------------------------
	 * 功能描述：
	 * 		找出phonenumber的所有Phonenumber对象，并将其中的 phonestatus 值改为0 
	 * -------------------------------------  
	 * @param 
	 * 		phonenumber
	 * 		
	 * @return 
	 * 		 returncode	0		未能修改 ，对象为空，未能修改
	 * 					1		成功
	 *  
	 */
	public Integer unbindedPhonenum(String phonenumber) {
		Integer returncode = 0 ;
	
		List <Phonenumber> lp = accountService.findAllPhonenumberByphone(phonenumber); 
		if(null == lp || lp.size() ==0){
			returncode = 0;
		}else{
			int not_activated = 0 ;
			Iterator <Phonenumber> lpi = lp.iterator();  
			while(lpi.hasNext()){
				Phonenumber p =lpi.next();
				p.setPhonestatus(not_activated);
				accountService.updatePhonenumber(p);				
			}
			returncode = 1;	
		}		
		return returncode;		
	}
	
	
	
	
	
	
	
	
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		UserPhoneTools.logger = logger;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

}
