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
 * User register  的Restful API的Controller.
 * 
 * 
 *  与com.job.lr.web.account   RegisterController 有重复部分 修改时需要注意
 * @author liuy
 */

@RestController
@RequestMapping(value = "/api/v1/userregister")
public class UserregisterRestController {
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public GeneralResponse registerForm() {
		
		GeneralResponse gp = new GeneralResponse();
		gp.setRetCode(0);
		gp.setRetInfo("请使用post方法传参");
		return gp;
	}
	
	/**
	 * post传递的参数
	 * 
	 * 	String loginName;
	 * 	String name;
	 *  @see 
	 *  String password; 此处传递的是明文 非加密后的  用来代替  plainPassword，这个PlainPassword属性不能缺。
	 *  
	 *  String phonenumber;
	 *  String captchacode;
	 * 
	 * */

	@RequestMapping(method = RequestMethod.POST)
	public GeneralResponse register(@Valid User user, RedirectAttributes redirectAttributes) {
		int  be_activated = 1 ;
		String phonenumber = user.getPhonenumber();
		int  bematch = 0; //不匹配
		bematch = accountService.checkUserPhone(phonenumber, user.getCaptchacode().trim());
		
		if( bematch ==1 ){
			//匹配
			user.setPlainPassword(user.getPassword());
			accountService.registerUser(user);//注册用户
			//-----注册用户时 改变  Phonenumber 类中 对应的用户号码装态	
			Phonenumber p = accountService.findUserPhoneInPhonenumber(phonenumber);
			p.setPhonestatus(be_activated);//0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
			p.setRegisterDate(new Date());
			accountService.updatePhonenumber(p);//更新手机号
			//-----
		}
		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		
		GeneralResponse gp = new GeneralResponse();
		gp.setRetCode(bematch);
		if(bematch == 0){
			//0 不匹配
			gp.setRetInfo("false,用户名手机号不匹配");
		}else if(bematch == 1){
			//1  匹配
			gp.setRetInfo("true,用户注册成功");
		}else{
			gp.setRetInfo("101未知错误");
		}	
		return gp;
	}

	/**
	 * 请求校验loginName是否唯一
	 * 
	 * url
	 * 	/api/v1/userregister/checkLoginName?loginName=admin
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public GeneralResponse checkLoginName(@RequestParam("loginName") String loginName) {
		String flag = "false";
		int   flagi = 0;
		if (accountService.findUserByLoginName(loginName) == null) {
			flag= "true";flagi=1;
		} else {
			flag= "false";flagi=0;
		}
		
		GeneralResponse gp = new GeneralResponse();
		gp.setRetCode(flagi);
		if(flagi == 0){
			//0 不匹配
			gp.setRetInfo("false,已有用户名");
		}else if(flagi == 1){
			//1  匹配
			gp.setRetInfo("true,可以使用用户名");
		}else{
			gp.setRetInfo("未知错误");
		}		
		return gp;
	}
}
