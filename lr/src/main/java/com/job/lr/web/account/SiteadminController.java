package com.job.lr.web.account;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * 网站管理员使用的Controller.
 *  
 * @author liuy
 * 
 */
@Controller
@RequestMapping(value = "/webadmin")
public class SiteadminController {
	
	private static  String  adminRoleStr = "admin";

	@Autowired
	private AccountService accountService;

	/**
	 * 网站管理员登录页面
	 * 
	 * http://localhost:8080/lr/webadmin/webadminlogin
	 * 
	 * */
	@RequestMapping(value = "webadminlogin" ,method = RequestMethod.GET)
	public String webadminlogin() {
		//System.out.println("hhahahahahhah");
		return "webadmin/webadmlogin";
	}
	
	/**
	 * 网站管理员登录验证
	 * 
	 * 	http://localhost:8080/lr/webadmin/webadminlogin  入口
	 * 
	 * 	/webadmin/fromForm
	 * 成功 跳转
	 * 
	 * 失败跳转 
	 * 		redirect:/webadmin/webadminlogin";
	 * */
	@RequestMapping(value = "fromForm",method = RequestMethod.POST)
	public String loginForm(HttpServletRequest request,HttpServletResponse response) {
		String username = (request.getParameter("username")).trim();
		String password = (request.getParameter("password")).trim();
		if(username==null||"".equals(username) ||password==null||"".equals(password)){			
			return "redirect:/webadmin/webadminlogin";
		}else{
			User u = accountService.findUserByLoginName(username);
			if(u!=null){
				String password_en = accountService.entryptPasswordByString(password);
				String password_db = u.getPassword();
				System.out.println(password_db);
				System.out.println(password_en);
				String str = u.getRoles() ;			   
			    String[] splitstr=str.split(",");
			    int behaveadminrole = 0 ;
			    for(int j=0 ;j<splitstr.length ;j++){
			    	//System.out.println("-------------------"+j+"=============="+splitstr[j]);
			    	if (splitstr[j].equals(adminRoleStr)){
			    		behaveadminrole =1;
			    	}
			    }
			    //用户名密码匹配，同时含有admin角色
				if(password_en.equals(password_db) || behaveadminrole==1){
					//可以登录用户 设置session  为了后续的过滤
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("digest", password_db);				
					return "/webadmin/adminshow";
				}else{
					return "redirect:/webadmin/webadminlogin";
				}
			}else{
				//未查出用户
				return "redirect:/webadmin/webadminlogin";
			}
		}

	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	public String registerForm() {
//		return "account/register";
//	}
	
//	/**
//	 * Ajax请求校验loginName是否唯一。
//	 */
//	@RequestMapping(value = "checkLoginName")
//	@ResponseBody
//	public String checkLoginName(@RequestParam("loginName") String loginName) {
//		if (accountService.findUserByLoginName(loginName) == null) {
//			return "true";
//		} else {
//			return "false";
//		}
//	}
}
