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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.MediaTypes;
import org.springside.modules.web.Servlets;

import com.job.lr.entity.BountyTask;
import com.job.lr.entity.Category;
import com.job.lr.entity.Enterprise;
import com.job.lr.entity.Subject;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;
import com.job.lr.entity.User;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.admin.CategoryService;
import com.job.lr.service.bounty.BountyTaskService;
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
	
	@Autowired
	private BountyTaskService bountyService;
	
	@Autowired
	private CategoryService cateService;

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
	 * 企业用户列表
	 * 
	 * 	http://localhost:8080/lr/webadmin/enuserlist 
	 * 
	 * 	/webadmin/enuserlist
	 * 成功 跳转
	 * 
	 * 		
	 * */	
	@RequestMapping(value = "enuserlist", method = RequestMethod.GET)
	public String enuserlist(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = AdminPAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {

		Long userId = getCurrentUserId();		
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			int enterprisesign = 1 ; //企业用户的标记
			Page<User> users = accountService.getEnUserlists( enterprisesign ,pageNumber, pageSize, sortType);		
			model.addAttribute("users", users);
			model.addAttribute("sortType", sortType);

		}
		return "webadmin/enuserlist1";
		
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
	public String showuserInfo(@RequestParam(value = "showuserId" ) Long showuserId,Model model,
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
	 * 显示企业用户详情
	 * ${ctx}/webadmin/showenuserinfo?enuserId=${user.id}
	 * 
	 * */
	@RequestMapping(value = "showenuserinfo", method = RequestMethod.GET)
	public String showenuserInfo(@RequestParam(value = "enuserId" ) Long enuserId,Model model,
			ServletRequest request) {	
		
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			User showenenuser = accountService.findUserByUserId(enuserId);
			model.addAttribute("showenuser", showenenuser);
		}else{
			model.addAttribute("showenuser", null);
		}
		return "webadmin/showenuserinfo1";
		
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
	 * 更新学院信息
	 * /webadmin/updateSubject 
	 * 		subject
	 * 		universityId
	 *   
	 * */
	@RequestMapping(value = "updateSubject", method = RequestMethod.POST)
	public String updateUniversity(@Valid @ModelAttribute("subject") Subject subject,
			@RequestParam(value = "universityId") Long universityId,
			RedirectAttributes redirectAttributes) {
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){			
			accountService.updateSubject(subject);
			redirectAttributes.addFlashAttribute("message", "更新学院成功");
		}
		String jumpurl = "redirect:/webadmin/subjectlist?universityId="+universityId ;
		return jumpurl;
	}
	
	/**
	 * 更新专业信息
	 * /webadmin/toeditsubjectinfo?subjectId=${subject.id}&universityId=${universityId}"
	 *  
	 * */
	@RequestMapping(value = "toeditsubjectinfo", method = RequestMethod.GET)
	public String toeditsubjectinfo(@RequestParam(value = "subjectId" ) Long subjectId,
			@RequestParam(value = "universityId" ) Long universityId,
			Model model,
			ServletRequest request) {	
			
			Long userId = getCurrentUserId();	
			User admin = accountService.findUserByUserId(userId);
			//检验是否是 管理员
			boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
			if(bradmin){			
				Subject s = accountService.findSubjectById(subjectId);
				model.addAttribute("subject", s);
			}else{
				model.addAttribute("subject", null);
			}
			model.addAttribute("universityId", universityId);
			return "webadmin/showsubjectinfo1";
			
	}
	
	/**
	 * 删除企业用户
	 *  ${ctx}/webadmin/delenuserinfo?enuserId=${user.id}
	 * */
	@RequestMapping(value = "delenuserinfo", method = RequestMethod.GET)
	public String delEnuserInfo(@RequestParam(value = "enuserId" ) Long enuserId,			
			Model model,ServletRequest request,RedirectAttributes redirectAttributes) {	
			String message = "" ;
			Long userId = getCurrentUserId();	
			User admin = accountService.findUserByUserId(userId);
			//检验是否是 管理员
			boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
			if(bradmin){				
				message=accountService.delEnuserinfo(enuserId);			
			}else{	
				message="权限不够" ;
			}
			redirectAttributes.addFlashAttribute("message", message);
			String jumpurl = "redirect:/webadmin/enuserlist";
			return jumpurl;
		
	}
	/**
	 *  删除专业信息
	 * delsubjectinfo?subjectId=${subject.id}&universityId=${universityId}
	 *  
	 * */
	@RequestMapping(value = "delsubjectinfo", method = RequestMethod.GET)
	public String delsubjectinfo(@RequestParam(value = "subjectId" ) Long subjectId,
			@RequestParam(value = "universityId" ) Long universityId,
			Model model,ServletRequest request,RedirectAttributes redirectAttributes) {	
			String message = "" ;
			Long userId = getCurrentUserId();	
			User admin = accountService.findUserByUserId(userId);
			//检验是否是 管理员
			boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
			if(bradmin){				
				message=accountService.delSubject(subjectId, universityId);				
			}else{	
				message="权限不够" ;
			}
			redirectAttributes.addFlashAttribute("message", message);
			String jumpurl = "redirect:/webadmin/subjectlist?universityId="+universityId ;
			return jumpurl;
		
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
	 * 增加企业用户和信息
	 * /webadmin/addEnuser 
	 *  
	 *  额外加的entAddress,entManager, phoneCall存到enterprise里
	 *  
	 *  注意：
	 *  User中的 ，企业用户不要 添加 phonenumber 属性
	 * */
	@RequestMapping(value = "addEnuser", method = RequestMethod.POST)
	public String addEnuser(@Valid @ModelAttribute("user") User u, 
			//@RequestParam(value="plainPassword" ) String plainPassword,
			@RequestParam(value="entAddress" ) String entAddress,
			@RequestParam(value="entManager" ) String entManager,
			@RequestParam(value="phoneCall" ) String phoneCall,
			RedirectAttributes redirectAttributes) {
 
		Long userId = getCurrentUserId();	
		User admin = accountService.findUserByUserId(userId);
		//检验是否是 管理员
		boolean bradmin = checkUserRoleIsAdmin(admin.getRoles());
		if(bradmin){
			 
			int  enterprisesign =1 ; //企业用户
			String roles ="enterpriseuser";
			u.setPhonenumber("");
			u.setRegisterDate(new Date());
			u.setEnterprisesign(enterprisesign);
			u.setRoles(roles);
			//System.out.println("plainPassword:"+plainPassword);
			//u.setPlainPassword(plainPassword);
			Enterprise e = new Enterprise();
			e.setEntAddress(entAddress);
			e.setEntManager(entManager);
			e.setEntName(u.getName());
			e.setPhoneCall(phoneCall);
			e.setRegDate(new Date());
			u.setEnterprise(e);
			accountService.addEnterprise(e);
			accountService.addEnuser(u); 
			redirectAttributes.addFlashAttribute("message", "增加企业用户成功");
		}
		return "redirect:/webadmin/enuserlist";
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
	 * 跳转到添加企业用户信息的页面
	 *  "${ctx}/webadmin/gotoaddEnuser"
	 *  
	 * */
	@RequestMapping(value = "gotoaddEnuser", method = RequestMethod.GET)
	public String gotoaddEnuser( ) {

		return "webadmin/addenuserinfo1";
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
	
	/**
	 * 兼职任务列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listTask", method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		
		Page<Task> tasks = taskService.pageAllTask(pageNumber, pageSize);

		model.addAttribute("tasks", tasks);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "webadmin/listTask";
	}
	
	/**
	 * 根据Id显示Task对象详情
	 * @param taskId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "taskDetail", method = RequestMethod.GET)
	public String taskDetail( @RequestParam(value = "taskId" ) Long taskId,Model model,
			ServletRequest request) {
		Task task = this.taskService.getTask(taskId);
		model.addAttribute("task", task);
		return "webadmin/taskDetail";
	}
	
	/**
	 * 通过对Task对象内容的审核
	 * @param taskId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "auditTask", method = RequestMethod.GET)
	public String auditTask( @RequestParam(value = "taskId" ) Long taskId,Model model,
			ServletRequest request) {
		Task task = this.taskService.getTask(taskId);
		task.setAuditFlag(1);
		taskService.saveTask(task);
//		model.addAttribute("task", task);
		return "redirect:/webadmin/listTask";
	}
	
	/**
	 * 赏金任务审核列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listBounty", method = RequestMethod.GET)
	public String listBounty(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<BountyTask> bountys = bountyService.getPagedBountyAll(pageNumber);

		model.addAttribute("bountys", bountys);
		model.addAttribute("sortType", sortType);
//		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "webadmin/listBounty";
	}
	
	
	/**
	 * 根据Id显示Bounty对象详情
	 * @param bountyId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bountyDetail", method = RequestMethod.GET)
	public String bountyDetail( @RequestParam(value = "bountyId" ) Long bountyId,Model model,
			ServletRequest request) {
//		
		BountyTask bounty = this.bountyService.getBountTask(bountyId);
		model.addAttribute("bounty", bounty);
		return "webadmin/bountyDetail";
	}
	
	
	/**
	 * 通过对BountyTask对象内容的审核
	 * @param bountyId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "auditBounty", method = RequestMethod.GET)
	public String auditBounty( @RequestParam(value = "bountyId" ) Long bountyId,Model model,
			ServletRequest request) {
		BountyTask bounty = this.bountyService.getBountTask(bountyId);
		bounty.setAuditFlag(1);
		this.bountyService.saveBountyTask(bounty);
		return "redirect:/webadmin/listBounty";
	}
	
	
	
	@RequestMapping(value = "listTaskCate", method = RequestMethod.GET)
	public String listCate(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Page<Category> categories = cateService.pageAllTaskCate(pageNumber, pageSize);
		
		model.addAttribute("categories", categories);
		model.addAttribute("sortType", sortType);
		model.addAttribute("action","addClass");
		return "webadmin/listClass";
	}
	
	@RequestMapping(value = "listBountyCate", method = RequestMethod.GET)
	public String listBountyCate(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "20") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Page<Category> categories = cateService.pageAllBountyCate(pageNumber, pageSize);
		
		model.addAttribute("categories", categories);
		model.addAttribute("sortType", sortType);
		model.addAttribute("action","addBountyClass");
		return "webadmin/listClass";
	}
	
	@RequestMapping(value = "listRestBountyCate/{pageNum}_{pageSize}", method = RequestMethod.GET ,produces = MediaTypes.JSON_UTF_8)
	public Page<Category> listRestBountyCate(@PathVariable("pageNum") int pageNum,
			@PathVariable("pageSize") int pageSize) {
		Page<Category> categories =  cateService.pageAllBountyCate(pageNum, pageSize);
		return categories;
	}
	
	
	@RequestMapping(value = "addClass", method = RequestMethod.GET)
	public String addCateForm(Model model,ServletRequest request) {
		
		model.addAttribute("action","saveClass");
		model.addAttribute("groupId", 1);
		return "webadmin/cateForm";
	}
	
	@RequestMapping(value = "addBountyClass", method = RequestMethod.GET)
	public String addBountyCateForm(Model model,ServletRequest request) {
		
		model.addAttribute("action","saveClass");
		model.addAttribute("groupId", 2);
		return "webadmin/cateForm";
	}
	
	@RequestMapping(value = "saveClass", method = RequestMethod.POST)
	public String addCate(@Valid Category cate,Model model,ServletRequest request) {
		
		this.cateService.save(cate);
		
		if (cate.getGroupId().equals(1))
			return "redirect:/webadmin/listTaskCate";
		else
			return "redirect:/webadmin/listBountyCate";
	}
	
	@RequestMapping(value = "cateDetail", method = RequestMethod.GET)
	public String cateDetail(@RequestParam(value = "cateId" ) Long cateId,Model model,ServletRequest request) {
		
		Category cate = this.cateService.findOne(cateId);
		model.addAttribute("cate",cate);
		model.addAttribute("groupId", cate.getGroupId());
		model.addAttribute("action","saveClass");
		
		return "webadmin/cateForm";
	}
	
	
	@RequestMapping(value = "deleteCate", method = RequestMethod.GET)
	public String addCate(@RequestParam(value = "cateId" ) Long cateId,
			@RequestParam(value = "groupId" ) Long groupId,Model model,ServletRequest request) {
		
		this.cateService.delete(cateId);
		
		if (groupId<2)
			return "redirect:/webadmin/listTaskCate";
		else
			return "redirect:/webadmin/listBountyCate";
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
//	&universityId=${universityId}}
	
	
	
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
