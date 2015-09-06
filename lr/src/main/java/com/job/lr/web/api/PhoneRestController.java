package com.job.lr.web.api;

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
import com.job.lr.entity.Task;
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
	 * 根据手机号和验证码
	 *   核查phonenumber的验证码是否正确
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
			returncode = accountService.checkUserPhone(phonenumber, captchacode);
		}
		
		GeneralResponse gp = new GeneralResponse();
		gp.setRetCode(returncode);
		if(returncode == 0){
			//1 匹配	0 不匹配
			gp.setRetInfo("验证码不匹配");
		}else if(returncode == 1){
			gp.setRetInfo("验证码匹配");
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
