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
import com.job.lr.tools.UserPhoneTools;

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
	
	
	@Autowired
	private UserPhoneTools userPhoneTools;
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
	
	
	/**
	 *  通过用户名和加密的密码，获取用户的 phonenum
	 *  
	 * 	@param
	 *  	根据 username
	 *  	加密后的 digest
	 *  @return
	 * 		phonenum
	 * 
	 *  url ：
	 *  	/api/v1/usertools/gogetPhonenum?username={username}&digest={加密后的passwd}
	 * 
	 * */
	@RequestMapping(value = "gogetPhonenum",method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse gogetPhonenum(@RequestParam("username") String loginName ,@RequestParam("digest") String password) {
		
		User u =accountService.findUserByUsernamePasswd(loginName, password);
		
		GeneralResponse gp = new GeneralResponse();
		
		if(u== null ){
			gp.setRetCode(-1);
			gp.setRetInfo("不存在查询的用户");
		}else{			
			gp.setRetCode(1);
			gp.setRetInfo(u.getPhonenumber());
		}
		return gp;
	}
	
	
	/**
	 * 检查用户手机号是否被占用
	 * 
	 * @param
	 *  	phonenum
	 *  	
	 *  @return
	 * 		
	 * 
	 *  url ：
	 *  	/api/v1/usertools/checkPhonenum?phonenum={phonenum}
	 *  
	 * 		http://localhost/lr/api/v1/usertools/checkPhonenum?phonenum=15522214561
	 * */
	@RequestMapping(value = "checkPhonenum",method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse checkPhonenum(@RequestParam("phonenum") String phonenum ) {
		Integer returnCode =userPhoneTools.checkPhonenumBinding(phonenum);
		Integer binding = 1 ;
		Integer unbinding = 0;
		Integer erro = 6 ;

		GeneralResponse gp = new GeneralResponse();		
		if(returnCode == binding ){
			gp.setRetCode(binding);
			gp.setRetInfo("手机号码已绑定");
		}else if(returnCode == unbinding){			
			gp.setRetCode(unbinding);
			gp.setRetInfo("手机号码未绑定");
		}else if(returnCode == erro){
			gp.setRetCode(erro);
			gp.setRetInfo("出现错误 错误码 6");
		}else{
			gp.setRetCode(7);
			gp.setRetInfo("出现错误 错误码7");
		}
		return gp;
	}
	
	/**
	 * 通过用户手机号获取用户名 loginName
	 * 
	 * @param
	 *  	phonenum
	 *  	
	 * @return
	 * 		loginName
	 * 
	 *  url ：
	 *  	/api/v1/usertools/gogetUsernameByPhonenum?phonenum={phonenum}
	 *  
	 * 		http://localhost/lr/api/v1/usertools/gogetUsernameByPhonenum?phonenum=15522214561
	 * */
	@RequestMapping(value = "gogetUsernameByPhonenum",method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse gogetLoginNameByPhonenum(@RequestParam("phonenum") String phonenum ) {
		
		User u =accountService.findUserByPhonenumber(phonenum);
		
		GeneralResponse gp = new GeneralResponse();		
		if(u == null ){
			gp.setRetCode(-1);
			gp.setRetInfo("此电话号码不匹配任何的用户");
		}else{			
			gp.setRetCode(1);
			gp.setRetInfo(u.getLoginName());
		}
		return gp;
	}
}
