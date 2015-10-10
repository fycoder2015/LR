
package com.job.lr.web.account;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.User;
import com.job.lr.service.account.AccountService;

/**
 * 用户注册的Controller.
 * 
 * 与com.job.lr.web.api  UserregisterRestController 有重复部分 修改时需要注意
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "account/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes) {
		int  be_activated = 1 ;
		String phonenumber = user.getPhonenumber();
		
		int  bematch = accountService.checkUserPhone(phonenumber, user.getCaptchacode().trim());
		
		if( bematch ==1 ){
			//匹配
			user.setPlainPassword(user.getPassword()); //需要设置一下
			accountService.registerUser(user);//注册用户
			//-----注册用户时 改变  Phonenumber 类中 对应的用户号码装态	
			Phonenumber p = accountService.findUserPhoneInPhonenumber(phonenumber);
			p.setPhonestatus(be_activated);//0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
			p.setRegisterDate(new Date());
			accountService.updatePhonenumber(p);//更新手机号
			//-----
		}


		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (accountService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
