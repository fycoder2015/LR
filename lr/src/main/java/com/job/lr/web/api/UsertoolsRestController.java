package com.job.lr.web.api;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.User;
import com.job.lr.service.account.AccountService;

/**
 * User工具类  的Restful API的Controller.
 * 
 * @author liuy
 */

@RestController
@RequestMapping(value = "/api/v1/usertools")
public class UsertoolsRestController {
	@Autowired
	private AccountService accountService;
	
	/**
	 *  通过用户名和加密的密码，获取用户的userId
	 * 
	 *  根据 username  和 加密后的   digest
	 * 
	 *  url ：
	 *  	/api/v1/usertools/gogetuserId?username={username}&digest={加密后的passwd}
	 * 
	 * */
	@RequestMapping(value = "gogetuserId",method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse gogetuserId(@RequestParam("username") String loginName ,@RequestParam("digest") String password) {
		
		User u =accountService.findUserByUsernamePasswd(loginName, password);
		
		GeneralResponse gp = new GeneralResponse();
		
		if(u== null ){
			gp.setRetCode(-1);
			gp.setRetInfo("不存在查询的用户");
		}else{			
			gp.setRetCode(1);
			gp.setRetInfo("用户id:"+u.getId().toString());
		}
		return gp;
	}
	
	

	
}
