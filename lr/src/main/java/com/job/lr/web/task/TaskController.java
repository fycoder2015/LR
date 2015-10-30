
package com.job.lr.web.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.job.lr.entity.Category;
import com.job.lr.entity.Enterprise;
import com.job.lr.entity.Task;
import com.job.lr.entity.TaskComment;
import com.job.lr.entity.User;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.admin.CategoryService;
import com.job.lr.service.task.TaskCommentService;
import com.job.lr.service.task.TaskService;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /task/
 * Create page : GET /task/create
 * Create action : POST /task/create
 * Update page : GET /task/update/{id}
 * Update action : POST /task/update
 * Delete action : GET /task/delete/{id}
 * Upload File page : GET /task/uploadfile/{id}
 * @author calvin  suiys
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

	private static final String PAGE_SIZE = "40";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskCommentService commentService;
	
	@Autowired
	private CategoryService cateService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Task> tasks = taskService.getUserTask(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("tasks", tasks);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

//		return "task/taskList";
		return "task/newTaskList";
	}
	
	@RequestMapping(value = "pageUserTask/{pageNum}", method = RequestMethod.GET)
	public String pageUserTask(@PathVariable("pageNum") int pageNum,
			Model model,
			ServletRequest request) {
		
		Long userId = this.getCurrentUserId();
		Page<Task> taskList = taskService.getUserTask(userId, pageNum);
		model.addAttribute("taskList",taskList);
		
		return "task/newTaskList";
	}
	
	@RequestMapping(value = "viewComment/{taskId}", method = RequestMethod.GET)
	public String viewComment(@PathVariable("taskId") Long taskId, 
			@RequestParam(value = "page", defaultValue = "1") int pageNum, 
			Model model,
			ServletRequest request) {
		
		Page<TaskComment> comments = this.commentService.findPageByTaskId(taskId, pageNum);
		
		model.addAttribute("comments", comments);
		model.addAttribute("pageNum",pageNum);
		return "task/listComment";
	}
	

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		
		Page<Category> categories = this.cateService.pageAllTaskCate(1, 500);		
		model.addAttribute("categories", categories);
		model.addAttribute("task", new Task());
		model.addAttribute("action", "create");		
		return "task/newTaskForm";
	}
	
	
	/**
	 *   对应映射地址  /task/logoutt
	 * 
	 * 
	 * */
	@RequestMapping(value = "logoutt", method = RequestMethod.GET)
	public String logoutt(HttpServletRequest request) {
		
		request.getSession().setAttribute("username", "");
		request.getSession().setAttribute("password", "");
		request.getSession().setAttribute("digest", "");
		
		return "redirect:/login/enadminlogin";
		
	}
	
	
	/**
	 * 增加图片上传  jpg后缀
	 * 
	 * @author  suiys
	 * 需要创建 环境变量， 定义 IMAGE_DIR
	 * 
	 * 服务端改动：
	 * 		服务端创建环境变量
	 * 			默认  IMAGE_DIR=/home/ubuntu/lr/images/
	 * 
	 * 		服务端 添加 
	 * 			在tomcat把这个目录做了个虚拟地址映射server.xml里面增加
	 * 
	 * 			<Context docBase="/home/ubuntu/lr/images" path="/upload" reloadable="true"/>
	 * 
	 * */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Task newTask, RedirectAttributes redirectAttributes,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

		User user = new User(getCurrentUserId());
		newTask.setUser(user);
		
		Date curDate = new Date();
		newTask.setCreateTime(curDate);
		String ctype = imageFile.getContentType() ;
		if (imageFile!=null && (ctype.contains("image") || ctype.contains("octet-stream"))) {

            String fileName = imageFile.getOriginalFilename();

            String suffix = fileName.substring(fileName.indexOf("."));
            
    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    		
    		String newFileName = user.getId()+"_"+format.format(curDate)+suffix;
    		System.out.println(newFileName);
    	   
    		String destDir = System.getenv("IMAGE_DIR");
    		//destDir="c:/loko";
    		//System.out.println("destDir:"+destDir);
    		if (destDir == null|| destDir.equals("")) {
    			redirectAttributes.addFlashAttribute("message", "未能获取文件保存位置，请联系系统管理员。");
    			return "redirect:/task/";
    		}
    		
    		File destFile = new File(destDir+"/"+newFileName);
    		
    		try {
    			imageFile.transferTo(destFile);
    		}
    		catch(Exception e) {
    			redirectAttributes.addFlashAttribute("message", e.getMessage());
    			return "redirect:/task/";
    		}
    		
    		newTask.setImageFileName(newFileName);
		}

		taskService.createTask(newTask);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		
		Page<Category> categories = this.cateService.pageAllTaskCate(1, 500);
		model.addAttribute("categories", categories);
		model.addAttribute("task", taskService.getTask(id));
		model.addAttribute("action", "update");
		
		return "task/updateForm";
	}
	
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
		taskService.saveTask(task);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/task/";
	}
	
	/**
	 * 跳转到图片上传页面   newadd
	 * 
	 *  /lr/task/toImagePlace
	 *  
	 * */
	@RequestMapping(value = "toImagePlace", method = RequestMethod.GET)
	public String toImagePlace(Model model, RedirectAttributes redirectAttributes) {
		return "/account/useruploadfileForm";
	}
	

	//转向到接收上传的文件页面 get   liuy
	@RequestMapping(value = "uploadfile/{id}", method = RequestMethod.GET)
	public String touploadfileForm(@PathVariable("id") Long id, Model model) {
		System.out.println("TaskController------------touploadfileForm()");
		model.addAttribute("task", taskService.getTask(id));
		model.addAttribute("action", "uploadfilehere");
		return "task/taskuploadfileForm";
	}

	
	//------------------------------
	//转向到接收上传的文件页面 post
	//独立的action ，不区分post和get 
	//------------------------------
	//@RequestMapping(value = "uploadfile/{id}", method = RequestMethod.POST)
	@RequestMapping(value = "/uploadfilehere")  //独立的action ，不区分post和get 
	//public String uploadfileForm(@PathVariable("id") Long id, Model model) {
	public	String uploadfileForm(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) throws Exception {  
		
		System.out.println("TaskController ----------here is uploadfileForm get  id=");
		System.out.println( file.getOriginalFilename());
		System.out.println( file.getName());
        String path = request.getSession().getServletContext().getResource("/").getPath();
        System.out.println("path="+path);
        String fileName = file.getOriginalFilename();  
        //String fileName = new Date().getTime()+".jpg";  
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  		
		
		//model.addAttribute("task", taskService.getTask(id));
		//model.addAttribute("action", "uploadfile");
		return "redirect:/task/";
	}
	
	
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		taskService.deleteTask(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/task/";
	}
	
	@RequestMapping(value = "close/{id}")
	public String close(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		taskService.closeTask(id);

		return "redirect:/task/";
	}
	
	@RequestMapping(value = "open/{id}")
	public String open(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		taskService.openTask(id);

		return "redirect:/task/";
	}
	
	@RequestMapping(value = "enterpriseForm" ,method = RequestMethod.GET )
	public String enterpriseForm(Model model) {
		Long userId = getCurrentUserId();
		User user = accountService.getUser(userId);
		
		model.addAttribute("user", user);
		model.addAttribute("action","updateEnt");
		return "task/enterpriseForm";
	}
	
	@RequestMapping(value = "updateEnt" ,method = RequestMethod.POST)
	public String updateEnt(@RequestParam("entManager") String entManager,
			@RequestParam("entName") String entName,@RequestParam("entAddress") String entAddress,
			@RequestParam("phoneCall") String phoneCall,Model model) {
		System.out.println("ruuhuhuhuhuh");
		Long userId = getCurrentUserId();
		User user = accountService.getUser(userId);
		if(user.getEnterprise() == null ){
			Enterprise e = new  Enterprise();
			e.setEntManager(entManager);
			e.setEntAddress(entAddress);
			e.setEntName(entName);
			e.setPhoneCall(phoneCall);
			e.setRegDate(new Date());
			accountService.saveEnterprise(e);
			user.setEnterprise(e);			
		}else{		
			user.getEnterprise().setEntManager(entManager);
			user.getEnterprise().setEntAddress(entAddress);
			user.getEnterprise().setEntName(entName);
			user.getEnterprise().setPhoneCall(phoneCall);			
		}
		
		accountService.saveUser(user);
		model.addAttribute("user", user);
		model.addAttribute("updateEnt","action");
		return "redirect:/task/enterpriseForm";
	}
	
	

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getTask(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("task", taskService.getTask(id));
		}
	}
	
	/**
	 * liuy add  
	 * 	测试后台企业管理用
	 * */
	@RequestMapping(value = "tolisttask", method = RequestMethod.GET)
	public String tolisttask(@RequestParam(value = "page", defaultValue = "1") int pageNum, 
			Model model,
			ServletRequest request) {
		
		//Page<TaskComment> comments = this.commentService.findPageByTaskId(taskId, pageNum);
		
		//model.addAttribute("comments", comments);
		//model.addAttribute("pageNum",pageNum);
		//return "enterprise/enListInUse";
		model.addAttribute("task", new Task());
		model.addAttribute("action", "create");
		return "task/newTaskForm";
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
