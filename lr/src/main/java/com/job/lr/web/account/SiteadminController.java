package com.job.lr.web.account;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Subject;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;
import com.job.lr.entity.User;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.task.TaskService;

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
	private static final String AdminPAGE_SIZE = "4";
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TaskService taskService;

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
	
	/**
	 * 网站用户列表
	 * 
	 * 	http://localhost:8080/lr/webadmin/userlist 
	 * 
	 * 	/webadmin/userlist
	 * 成功 跳转
	 * 
	 * 		
	 * */
	
	@RequestMapping(value = "userlist", method = RequestMethod.GET)
	public String userlist(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = AdminPAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		
		//Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");		
		Page<User> users = accountService.getUserlists(userId, searchParams, pageNumber, pageSize, sortType);		
		model.addAttribute("users", users);
		model.addAttribute("sortType", sortType);
		//model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "webadmin/userlist1";
	}
	
	/**
	 * 大学列表
	 * 
	 * 	http://localhost:8080/lr/webadmin/universitylist 
	 * 
	 * 	/webadmin/universitylist
	 * 成功 跳转
	 * 
	 * 		
	 * */
	
	@RequestMapping(value = "universitylist", method = RequestMethod.GET)
	public String universitylist(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = AdminPAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {

		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){			
			Page<University> universitylist = accountService.getUniversitylists(pageNumber, pageSize, sortType);					
			model.addAttribute("universitys", universitylist);
			model.addAttribute("sortType", sortType);		
		}
		
		return "webadmin/universitylist1";
	}
	
	
	/**
	 * 学院列表
	 * 
	 * 	http://localhost:8080/lr/webadmin/subjectlist 
	 * 
	 * 	${ctx}/webadmin/subjectlist?universityId=
	 * 成功 跳转
	 * 
	 * 		
	 * */
	
	@RequestMapping(value = "subjectlist", method = RequestMethod.GET)
	public String subjectlist(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = AdminPAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			@RequestParam(value = "universityId") Long universityId,
			Model model,
			ServletRequest request) {

		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){	
			
			model.addAttribute("universityId", universityId);
			Page<Subject> subjectlist = accountService.gogetSubjectlists(pageNumber, pageSize, sortType, universityId);		
			if(subjectlist == null){
				
			}else{
				model.addAttribute("subjectlists", subjectlist);
				model.addAttribute("sortType", sortType);	
			}
		}
		
		return "webadmin/subjectlist1";
	}
	/**
	 * 显示用户详情
	 * 	/webadmin/showuserinfo?showuserId=${user.id}
	 * 
	 * 
	 * */
	@RequestMapping(value = "showuserinfo", method = RequestMethod.GET)
	public String userlist(@RequestParam(value = "showuserId" ) Long showuserId,Model model,
			ServletRequest request) {	
		
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			User showuser = accountService.findUserByUserId(showuserId);
			model.addAttribute("showuser", showuser);
		}else{
			model.addAttribute("showuser", null);
		}
		return "webadmin/showuserinfo1";
	}
	
	/**
	 * 显示学校详情
	 * 	
	 * 	/webadmin/showuniversityinfo?showuniversityId
	 * 
	 * */
	@RequestMapping(value = "showuniversityinfo", method = RequestMethod.GET)
	public String showuniversityinfo(@RequestParam(value = "showuniversityId" ) Long showuniversityId,Model model,
			ServletRequest request) {	
		
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){			
			University showuniversity = accountService.findUniversityById(showuniversityId);
			model.addAttribute("showuniversity", showuniversity);
		}else{
			model.addAttribute("showuser", null);
		}
		return "webadmin/showuniversityinfo1";
	}
	
	
	/**
	 * 更新大学信息
	 * /webadmin/updateUniversity 
	 *  
	 * */
	@RequestMapping(value = "updateUniversity", method = RequestMethod.POST)
	public String updateUniversity(@Valid @ModelAttribute("showuniversity") University u, RedirectAttributes redirectAttributes) {
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			//显示大学
			int  beshow =1 ;
			u.setStsint(beshow);//stsint -1 为失效 ，不做显示 ; 显示 默认为1   (必填) 
			
			accountService.saveUniversity(u);
			redirectAttributes.addFlashAttribute("message", "更新任务成功");
		}
		return "redirect:/webadmin/universitylist";
	}
	
	/**
	 * 增加大学信息
	 * /webadmin/addUniversity 
	 *  
	 * */
	@RequestMapping(value = "addUniversity", method = RequestMethod.POST)
	public String addUniversity(@Valid @ModelAttribute("university") University u, RedirectAttributes redirectAttributes) {
		//System.out.println("111111111111111111111111111111");
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			//显示大学
			int  beshow =1 ;
			u.setStsint(beshow);//stsint -1 为失效 ，不做显示 ; 显示 默认为1   (必填) 
			
			accountService.saveUniversity(u);
			redirectAttributes.addFlashAttribute("message", "增加任务成功");
		}
		return "redirect:/webadmin/universitylist";
	}
	
	
	
	/**
	 * 增加学院信息
	 * 
	 *   ${ctx}/webadmin/addSubject
	 *  	universityId
	 * */
	@RequestMapping(value = "addSubject", method = RequestMethod.POST)
	public String addSubject(@Valid @ModelAttribute("Subject") Subject s, @RequestParam(value="universityId" ) Long universityId,
			RedirectAttributes redirectAttributes) {
		
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			accountService.addSubject(s, universityId);
			redirectAttributes.addFlashAttribute("message", "增加学院成功");
		}
		String jumpurl = "redirect:/webadmin/subjectlist?universityId="+universityId ;
		return jumpurl;
		
	}
	
	/**
	 * 跳转到添加大学信息的页面
	 *  /webadmin/gotoaddUniversity 
	 *  
	 * */
	@RequestMapping(value = "gotoaddUniversity", method = RequestMethod.GET)
	public String gotoaddUniversity( ) {

		return "webadmin/adduniversityinfo1";
	}
	
	
	
	/**
	 * 跳转到添加专业信息的页面
	 *  ${ctx}/webadmin/gotoaddSubject?universityId=${universityId}
	 *  
	 * */
	@RequestMapping(value = "gotoaddSubject", method = RequestMethod.GET)
	public String gotoaddSubject( @RequestParam(value = "universityId" ) Long universityId,Model model,
			ServletRequest request) {
		model.addAttribute("universityId", universityId);
		return "webadmin/addsubjectinfo1";
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
	
	/**
	 * 验证权限是否是 admin
	 * 
	 * @return true 是
	 * 		   false 不是管理员
	 * 
	 * */
	private Boolean checkUserRoleIsAdmin(String  rolestr) {	
		 	   
	    String[] splitstr=rolestr.split(",");
	    boolean behaveadminrole = false ;
	    for(int j=0 ;j<splitstr.length ;j++){	    	
	    	if (splitstr[j].equals(adminRoleStr)){
	    		behaveadminrole = true;
	    		break;
	    	}
	    }
	    return behaveadminrole;
	}
	
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {		
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user== null){
			return 0L;
		}else{
			//System.out.println("user.id +user.id： "+user.id);
			return user.id;
		}	
	}
}
