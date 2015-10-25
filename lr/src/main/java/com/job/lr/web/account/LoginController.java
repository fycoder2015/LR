
package com.job.lr.web.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.job.lr.entity.User;
import com.job.lr.service.account.AccountService;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin liuy
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	private static  String  EnuserRoleStr = "enterpriseuser";
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "account/login";
	}
	
	/**
	 * 企业用户登录页面
	 * */
	@RequestMapping(value = "enadminlogin" ,method = RequestMethod.GET)
	public String enadminlogin() {
		return "account/enadminlogin";
	}
	

	
	/**
	 * 企业用户登录
	 * ${ctx}/task/tolisttask
	 * **/
	@RequestMapping(value = "enfromForm",method = RequestMethod.POST)
	public String enloginForm(HttpServletRequest request,HttpServletResponse response) {
		String username = (request.getParameter("username")).trim();
		String password = (request.getParameter("password")).trim();
		if(username==null||"".equals(username) ||password==null||"".equals(password)){			
			return "redirect:/login/enadminlogin";
		}else{
			User u = accountService.findUserByLoginName(username);
			if(u!=null){
				String password_en = accountService.entryptPasswordByString(password);
				String password_db = u.getPassword();				
				System.out.println("password_db:"+password_db);
				System.out.println("password_en:"+password_en);
				//检验是否是企业用户
				boolean brenuser = checkUserRoleIsEnuser(u.getRoles());
				System.out.println("brenuser:"+brenuser);
				if((password_en.equals(password_db))&&(brenuser)){//满足 1.密码匹配  2.用户角色是企业用户 并行
					//可以登录用户 设置session  为了后续的过滤
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("digest", password_db);				
					return "redirect:/task/tolisttask";
				}else{
					return "redirect:/login/enadminlogin";
				}
			}else{
				//未查出用户
				return "redirect:/login/enadminlogin";
			}
		}

	}
	
	/**
	 * 验证权限是否是Enuser 企业用户
	 * 
	 * @return true 是
	 * 		   false 不是管理员
	 * 
	 * */
	private Boolean checkUserRoleIsEnuser(String  rolestr) {	
		 	   
	    String[] splitstr=rolestr.split(",");
	    boolean behaveenuserrole = false ;
	    for(int j=0 ;j<splitstr.length ;j++){
	    	//System.out.println("----------"+splitstr[j]);
	    	if (splitstr[j].equals(EnuserRoleStr)){
	    		behaveenuserrole = true;
	    		break;
	    	}
	    }
	    //System.out.println("----------"+behaveenuserrole);
	    return behaveenuserrole;
	}
	
	@RequestMapping(value = "fromForm",method = RequestMethod.POST)
	public String loginForm(HttpServletRequest request,HttpServletResponse response) {
		String username = (request.getParameter("username")).trim();
		String password = (request.getParameter("password")).trim();
		if(username==null||"".equals(username) ||password==null||"".equals(password)){			
			return "redirect:/login";
		}else{
			User u = accountService.findUserByLoginName(username);
			if(u!=null){
				String password_en = accountService.entryptPasswordByString(password);
				String password_db = u.getPassword();
				System.out.println(password_db);
				System.out.println(password_en);
				if(password_en.equals(password_db)){
					//可以登录用户 设置session  为了后续的过滤
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("digest", password_db);				
					return "redirect:/task/";
				}else{
					return "redirect:/login";
				}
			}else{
				//未查出用户
				return "redirect:/login";
			}
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "account/login";
	}


	public AccountService getAccountService() {
		return accountService;
	}


	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	
	

}
