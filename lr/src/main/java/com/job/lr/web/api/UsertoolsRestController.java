package com.job.lr.web.api;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Task;
import com.job.lr.entity.User;
import com.job.lr.rest.RestException;
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
	
	private static Logger logger = LoggerFactory.getLogger(UsertoolsRestController.class);
	
	@Autowired
	private Validator validator;
	
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
	 *  通过用户名和加密的密码，获取用户信息
	 * 
	 *  根据 username  和 加密后的   digest
	 * 
	 *  url ：
	 *  	/api/v1/usertools/gogetuser?username={username}&digest={加密后的passwd}
	 * 		http://localhost/lr/api/v1/usertools/gogetuser?username=user007&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * */
	@RequestMapping(value = "gogetuser",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public User gogetUser(@RequestParam("username") String loginName ,@RequestParam("digest") String password) {		
		
		//System.out.println("lallalallalalalaaaaaaaalalallala");
		User u =accountService.findUserByUsernamePasswd(loginName, password);
		//u.setPlainPassword("*******");
		/**
		 * 改变json显示 ，但没保存，不会更改数据库，
		 * 
		 * 或是在User中的对应不在Json上显示的属性或是get方法上 加@JsonIgnore，就可以不在json的返回值中显示了
		 * */
		//u.setSalt("*******");  
		return u;
	}
	
	

	/**
	 *  通过用户名和加密的密码，更新用户信息
	 * 
	 *  根据 username  和 加密后的   digest
	 * 
	 *  url ：Post 
	 *  	/api/v1/usertools/updateuser?username={username}&digest={加密后的passwd}
	 * 		http://localhost/lr/api/v1/usertools/updateuser?username=user007&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * */
											//RequestMethod.PUT
	@RequestMapping(value = "updateuser", method = RequestMethod.POST) //, consumes = MediaTypes.JSON
	//按Restful风格约定，返回204状态码, 无内容. 也可以返回200状态码.
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	//@ResponseBody
	//public GeneralResponse updateUser(@RequestBody User user) {//,@RequestParam("username") String loginName ,@RequestParam("digest") String password
	//--public GeneralResponse updateUser(@Valid User user ,@RequestParam("username") String loginName ,@RequestParam("digest") String password,RedirectAttributes redirectAttributes) {
	public GeneralResponse updateUser(@Valid User user ,@RequestParam("username") String loginName ,@RequestParam("digest") String password) {
		// System.out.println("-----------------in  updateUser() --------------------");
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, user);
		
		User old_u =accountService.findUserByUsernamePasswd(loginName,password);
		
		GeneralResponse gp = new GeneralResponse();	
		
		if(old_u.getLoginName().equals(loginName)){			
			user.setId(old_u.getId());
			user.setLoginName(loginName);
			user.setRegisterDate(old_u.getRegisterDate());
			user.setSalt(old_u.getSalt());
			String  newpasswd = user.getPassword();
			if(newpasswd == null || "".equals(newpasswd)){
				user.setPassword(password);
			}
			user.setRoles(old_u.getRoles()); //---- liuy add  设置权限
			
			accountService.updateUser(user);
			gp.setRetCode(1);
			gp.setRetInfo("已更新成功");
		}else{
			gp.setRetCode(-1);
			gp.setRetInfo("更新失败");
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
	
	
	
	/**
	 * 通过用户手机号和加密密码获取用户名 loginName
	 * 
	 * @param
	 *  	phonenum
	 *  	password 加密的密码   digest参数体现
	 *  	
	 * @return
	 * 		loginName
	 * 
	 *  url ：
	 *  	/api/v1/usertools/gogetUsernameByPhonenumPasswd?phonenum={phonenum}&digest={加密后的password}
	 *  
	 * 		http://localhost/lr/api/v1/usertools/gogetUsernameByPhonenumPasswd?phonenum=15522214561&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * */
	@RequestMapping(value = "gogetUsernameByPhonenumPasswd",method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse gogetUsernameByPhonenumPasswd(@RequestParam("phonenum") String phonenum,@RequestParam("digest") String password ) {
		
		User u =accountService.findUserByPhonenumberPassword(phonenum, password);
		
		GeneralResponse gp = new GeneralResponse();		
		if(u == null ){
			gp.setRetCode(-1);
			gp.setRetInfo("此电话号码和密码不匹配任何的用户");
		}else{			
			gp.setRetCode(1);
			gp.setRetInfo(u.getLoginName());
		}
		return gp;
	}
	
}
