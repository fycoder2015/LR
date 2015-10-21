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
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.User;
import com.job.lr.filter.Constants;
import com.job.lr.service.account.AccountService;
import com.job.sendSms.SDKSendTemplateSMS;

@Component
@Transactional
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
		Phonenumber p = accountService.findUserPhoneInPhonenumber(phonenumber);  //倒序查找最新的一个 Phonenumber
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
	 * 注册时 根据phonenumber生成短信验证码
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
	
		Phonenumber p = accountService.findUserPhoneInPhonenumber(phonenumber);
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
	 * 找回密码时 根据 phonenumber和 验证码captchacode 判断匹配
	 * 验证码的修改时间为 Constants.SMS_Gap_Time*分钟  
	 * 
	 * -------------------------------------
	 * 功能描述：
	 * 		根据phonenumber captchacode 比对 
	 *    	Phonenumber对象 	    
	 *     		 
	 * -------------------------------------  
	 * @param 
	 * 		phonenumber
	 * 		
	 * @return 
	 *  	returnCode -1 比对不成功
	 *  			   1    匹配、比对成功 
	 *  			0  验证码超时
	 *  
	 *  验证码超时 需要重新获取验证码 
	 */
	public int checkPhoneInFindPasswd(String phonenumber ,String captchacode ) {
		int returncode =  0 ;
		int errcode = -1 ;
		String errmsg = "比对不成功";
		int successcode = 1 ;
		String successmsg = "匹配、比对成功 ";
		int overtimecode = 0 ;
		String overtimemsg = "验证码超时" ;
		
		Phonenumber p = accountService.findUserPhoneByPhonenumAndCaptchaInFindPasswd(phonenumber, captchacode);	
		//找到 已激活的用户的手机对象 
		if (p == null){	
			/*** 手机号 验证码 比对失败 * **/	
			//gp.setRetCode(errcode);
			//gp.setRetInfo(errmsg);
			returncode = errcode;
			
		}else{					
			/*** 手机号 验证码 匹配、比对成功 * **/
			// 验证验证码是否超时
			int gap_time = Constants.SMS_Gap_Time; //两分钟  超时
			//返回时间： 0    超时  ； 1   未超时
			int  istimeoutflag =accountService.compareTimes(p.getRegisterDate(), new Date(), Constants.SMS_Gap_Time) ;
			if(istimeoutflag == 1){
				// 1   未超时
				returncode = successcode ;
			}else{
				// 0    超时
				returncode = overtimecode ;
			}
				
		}
		
		return returncode ;
		
	}
	/**
	 * 找回密码时 根据phonenumber生成短信验证码
	 * 验证码的修改时间为 Constants.SMS_Gap_Time*分钟  
	 * 
	 * -------------------------------------
	 * 功能描述：
	 * 		根据phonenumber
	 *    	Phonenumber对象的调整	    
	 *     		只修改已激活的手机号  修改新生产的验证码 
	 * -------------------------------------  
	 * @param 
	 * 		phonenumber
	 * 		
	 * @return 
	 *  	returnCode -1 不存在相应的用户手机号
	 *  			   1  短信发送成功
	 *  			   0  短信发送失败
	 *  	
	 *  
	 * 
	 */
	public int genCaptchacodeByPhoneInFindPasswd(String phonenumber ) {
		//GeneralResponse gp = new GeneralResponse();
		int returncode =  0 ;
		int errcode = -1 ;
		String errmsg = "不存在相应的用户手机号";
		int successcode = 1 ;
		String successmsg = "短信发送成功";
		int senderr = 0;
		String senderrmsg="短信发送失败";

		
		Phonenumber p = accountService.findUserPhoneByPhonenumberInFindPasswd(phonenumber);	
		//找到 已激活的用户的手机对象 
		if (p == null){			
			//gp.setRetCode(errcode);
			//gp.setRetInfo(errmsg);
			returncode = errcode;
			
		}else{		
			/**
			 * 不论是否超时 都发送新的验证码 
			 * 重新生成验证码 和 日期 ，发送短信成功后 保存Phonenumber对象
			 * 
			 * **/	
		
			String captchacode = getRandomString(Constants.CaptchacodeSize) ;//Constants.CaptchacodeSize  随机码位数
		
			/**
			 * 发送短信 SendTemplateSMS* 
			 * */						
			SDKSendTemplateSMS s = new  SDKSendTemplateSMS();						
			Integer SMS_Gap_TimeI = Constants.SMS_Gap_Time ;
			String message = s.SendTemplateSMS(p.getPhonenumber() ,captchacode, "",  SMS_Gap_TimeI.toString()); //1 是 短信模板数
			String sendOkflag ="sendok";
			
			p.setRegisterDate(new Date()); //现在的时间 
			p.setCaptchacode(captchacode);
			
			if (sendOkflag.equals(message) ){  
				//短信发送成功
				accountService.updatePhonenumber(p);
				
				//gp.setRetCode(successcode);
				//gp.setRetInfo(successmsg);
				returncode = successcode;
			}else{
				//调用短信接口 ，短息发送失败
				//gp.setRetCode(senderr);
				//gp.setRetInfo(senderrmsg);
				returncode = senderr;
			}											
					
		}
		
		return returncode ;
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
	 * 将User对象中的phonenumber手机号状态置为空，或是新的手机号
	 *   
	 * 解绑手机号Step1
	 * -------------------------------------
	 * 功能描述：
	 * 		核对用户的loginame、密码后， 将User对象中的phonenumber手机号状态置为空，或是新的手机号
	 * -------------------------------------  
	 * @param 
	 * 		loginName
	 * 		password
	 * 		phonenumber
	 * 		
	 * @return 
	 * 		 returncode	0		未能修改 
	 * 					1		成功
	 *  
	 */
	public Integer unbindStep1UserPhonenum(String loginName ,String password , String phonenumber) {
		Integer returncode = 0 ;
		User u = accountService.findUserByUsernamePasswd(loginName, password);
		if(u==null){
			
		}else{
			if (phonenumber.equals(u.getPhonenumber())){	 
				u.setPhonenumber("");
				accountService.updateUser(u);
				returncode = 1 ;
			}
		}		
		return returncode;   
	}
	
	
	/**
	 * 将Phonenumber对象中的，手机号状态置为未激活状态
	 *   //0 ,未激活  not_activated ； 1，已激活 ； 
	 * 解绑手机号Step2
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
	public Integer unbindStep2PhoneOnlyInPhonenum(String phonenumber) {
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
	
	/**
	 * 将Phonenumber对象中的，手机号状态置为激活状态
	 *   //0 ,未激活  not_activated ； 1，已激活 ； 
	 * 绑定手机号Step1
	 * -------------------------------------
	 * 功能描述：
	 * 		找出phonenumber的所有Phonenumber对象，并将其中的 phonestatus 值改为1 
	 * -------------------------------------  
	 * @param 
	 * 		phonenumber
	 * 		
	 * @return 
	 * 		 returncode	0		未能修改 ，对象为空，未能修改
	 * 					1		成功
	 *  
	 */
	public Integer bindStep1PhoneOnlyInPhonenum(String phonenumber) {
		Integer returncode = 0 ;	
		List <Phonenumber> lp = accountService.findAllPhonenumberByphone(phonenumber); 
		if(null == lp || lp.size() ==0){
			returncode = 0;
		}else{
			int activated = 1 ;
			Iterator <Phonenumber> lpi = lp.iterator();  
			while(lpi.hasNext()){
				Phonenumber p =lpi.next();
				p.setPhonestatus(activated);
				accountService.updatePhonenumber(p);				
			}
			returncode = 1;	
		}		
		return returncode;		
	}
	
	
	/**
	 * 将User对象中的phonenumber手机号状态置为手机号
	 *   
	 * 绑定手机号Step2
	 * -------------------------------------
	 * 功能描述：
	 * 		核对用户的loginame、密码后， 将User对象中的phonenumber手机号状态置为手机号参数
	 * -------------------------------------  
	 * @param 
	 * 		loginName
	 * 		password
	 * 		phonenumber 
	 * 		
	 * @return 
	 * 		 returncode	0		未能修改 
	 * 					1		成功
	 *  
	 */
	public Integer bindStep2UserPhonenum(String loginName,String password,String phonenumber) {
		Integer returncode = 0 ;
		User u = accountService.findUserByUsernamePasswd(loginName, password);
		if(u==null){
			
		}else{
			u.setPhonenumber(phonenumber);
			accountService.updateUser(u);
			returncode = 1 ;
		}	
		return returncode;   
	}
	
	
	
	/**
	 * 在Phonenumber和User的对象中，检查是否存在phonenumber手机。查看号码是否已经绑定
	 * 
	 * @param 
	 * 		phonenumber 
	 * 		
	 * @return 
	 * 		 returncode	0		未绑定 unbinding
	 * 
	 * 					1		绑定     binding
	 * 
	 * 					6        	出现问题了    user表没有， Phonenumber中有
	 *  
	 */
	public Integer checkPhonenumBinding(String phonenumber) {
		int binding = 1 ;
		int unbinding = 0;
		int erro = 6 ;
		Integer returncode = unbinding ;
		
		User u = accountService.findUserByPhonenumber(phonenumber);
		if(u==null){
			//未绑定
			
			//---在Phonenumber表中再次查询 已绑定的号码 ，看是否存在
			Integer phonestatus = binding;
			List <Phonenumber> ul = accountService.findPhonenumberByPhoneAndStatus(phonenumber, phonestatus);
			if( ul== null ||ul.size() ==0 ){
				//ok  真没绑定
				returncode = unbinding ;
			}else{
				//出现问题了    user表没有， Phonenumber中有
				returncode = erro ;
			}			
			
		}else{
			returncode = binding ;
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
