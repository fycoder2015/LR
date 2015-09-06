package com.job.lr.web.api;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Task;
import com.job.lr.filter.Constants;
import com.job.lr.rest.TaskRestController;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.task.TaskService;

/**
 * Phonenumber  的Restful API的Controller.
 * 
 * @author liuy
 */

@RestController
@RequestMapping(value = "/api/v1/phoneCollect")
public class PhoneRestController {
	
	private static Logger logger = LoggerFactory.getLogger(TaskRestController.class);

	@Autowired
	private AccountService  accountService;
	
	
	/**
	 * 根据 手机号和 验证码
	 *   核查phonenumber的验证码是否正确  是否匹配
	 *   需要验证生成验证码的时间是否超时
	 *   
	 *   
	 * @param 
	 * 		phonenumber
	 * 		captchacode
	 * @return 
	 * 		{@value}  url: /api/v1/phoneCollect/checkPhonenumber
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
			Phonenumber p = accountService.findUserPhone(phonenumber);
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
			gp.setRetInfo("未知错误");
		}		
		return gp;

	}
	
	/**
	 * 	根据手机号码 生成 验证码
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
	 *  			   2 
	 * 		{@value}  url: /api/v1/phoneCollect/genCaptchacodeByPhone
	 * */
	@RequestMapping(value = "/genCaptchacodeByPhone", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse  genCaptchacodeByPhone(ServletRequest request) {
		
		int phonestatus_not_activated = 0; 
		int phonestatus_activated = 1; 
		
		int phonestatus_not_activated_flag = 0;
		int phonestatus_activated_flag = 1;
		
		
		int returnCode = phonestatus_not_activated_flag; 
		 
		String phonenumber = request.getParameter("phonenumber").trim();		
		if("".equals(phonenumber)||phonenumber==null){
			//do nothing
		}else{		
			//			
			Phonenumber p = accountService.findUserPhone(phonenumber);
			if (p == null){
				//数据库中没有响应的手机号----  新用户
				accountService.registerUserPhone(phonenumber);	
				returnCode = 0; 
			}else{
				//老用户
				int nowstatus =  p.getPhonestatus();
				if (nowstatus == phonestatus_not_activated ){
					//分辨验证码是否超时 
					int gap_time =2; //两分钟  超时
					
					//返回时间： 0    超时  ； 1   未超时
					int  istimeoutflag =accountService.compareTimes(p.getRegisterDate(), new Date(), Constants.SMS_Gap_Time) ;
					if(istimeoutflag == 0){// 0    超时
						//重新生成验证码 和 日期 ，后 保存
						accountService.updatePhonenumber(p);						
					}else{// 1   未超时
						//不做改动
					}
					returnCode = phonestatus_not_activated_flag ; //手机未激活
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
			gp.setRetInfo("手机未激活");
		}else if(returnCode == phonestatus_activated_flag){
			//1 手机已激活
			gp.setRetInfo("手机已激活");
		}else{
			gp.setRetInfo("未知错误");
		}		
		return gp;
		
		
	}
	

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	
}
