package com.job.lr.web.api;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Task;
import com.job.lr.entity.User;
import com.job.lr.entity.UserPicoo;
import com.job.lr.entity.UserRole;
import com.job.lr.repository.UserDao;
import com.job.lr.rest.RestException;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
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
		Long userId = getCurrentUserId();
		User old_u = accountService.findUserByUserId(userId);
		//User old_u =accountService.findUserByUsernamePasswd(loginName,password);
		
		GeneralResponse gp = new GeneralResponse();	
		
		old_u.setUserage(user.getUserage()); ;
		old_u.setSexynum(user.getSexynum());
		old_u.setSexy(user.getSexy());
		old_u.setUsersign(user.getUsersign());
		old_u.setUserstarss(user.getUserstarss());
		
		old_u.setName(user.getName());
		old_u.setPhonenumber(user.getPhonenumber());  //更新的手机号需要是在接口验证过的 
		old_u.setPlainPassword(user.getPassword()); //新密码 为 密码原文  不是加密后的
		old_u.setUniversity(user.getUniversity());
		old_u.setUniversityId(user.getUniversityId());
		old_u.setSubject(user.getSubject());
		old_u.setSubjectId(user.getSubjectId());
		old_u.setYears(user.getYears());
		old_u.setYearsId(user.getYearsId());
		old_u.setUserpicooId(user.getUserpicooId() );
		accountService.updateUser(old_u);
		
		gp.setRetCode(1);
		gp.setRetInfo("已更新成功");
		
		/**
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
		
		***/
		return gp;
	}
	
	/**
	 *  通过用户名和加密的密码，更新用户密码
	 * 
	 *  根据 username  和 加密后的   digest
	 *  
	 *  post：  passwd 密码原文
	 * 
	 *  url ：Post 
	 *  	/api/v1/usertools/updateuserpasswd?username={username}&digest={加密后的passwd}
	 * 		http://localhost/lr/api/v1/usertools/updateuserPasswd?username=user007&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * 
	 * http://localhost/lr/api/v1/usertools/updateuserPasswd?username=fab8cf43a14d4f90aab28d31ce1aa11b&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * 
	 * post passwd
	 * */
	@RequestMapping(value = "updateuserPasswd", method = RequestMethod.POST) //, consumes = MediaTypes.JSON
	public GeneralResponse updateuserpasswd(@Valid String passwd ,@RequestParam("username") String loginName ,@RequestParam("digest") String password) {
		BeanValidators.validateWithException(validator, passwd);
		Long userId = getCurrentUserId();
		User old_u = accountService.findUserByUserId(userId);
		GeneralResponse gp = new GeneralResponse();	
		if( old_u == null) {
			gp.setRetCode(0);
			gp.setRetInfo("未找到相应的用户");
		}else{
			old_u.setPlainPassword(passwd); //新密码 为 密码原文  不是加密后的			
			accountService.updateUser(old_u);
			gp.setRetCode(1);
			gp.setRetInfo("用户密码已更新成功");			
		}
		return gp;
	}
	

	/**
	 *  找回密码中，重置用户密码
	 *  
	 *  通过手机号和临时令牌校验
	 *  重置密码 ：
	 *  	在User中，比对用户手机号 和 临时令牌（tempToken） 
	 *  		Yes 比对成功 ,存在相关记录，重置密码，并清空临时令牌，(注：临时令牌只能使用一次)
	 *  		No	比对不成功，不做理会。
	 *    
	 * 	@param
	 *  		phonenumber
	 *  		tempToken
	 *  		newpassword  此处传递的是明文
	 *  	
	 *  @return
	 * 		returnCode  -1 比对失败
	 * 					 1 成功 并修改密码成功，同时清空了临时令牌 
	 * 
	 *  url ：
	 *  	/api/v1/usertools/changeUserPassword?phonenumber={phonenumber}&tempToken={tempToken}&newpassword={newpassword}
	 * http://localhost/lr/api/v1/usertools/changeUserPassword?phonenumber=13662127862&tempToken=c2cffb66d9e44ee1af4dfc85af93384c&newpassword=1233321
	 * */
	@RequestMapping(value = "changeUserPassword",method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse changeUserPassword(@RequestParam("phonenumber") String phonenumber ,
			@RequestParam("tempToken") String tempToken,
			@RequestParam("newpassword") String newpassword) {
		
		User u =accountService.findUserByPhonenumberAndTempToken(phonenumber, tempToken);
		GeneralResponse gp = new GeneralResponse();
		
		if(u== null ){
			gp.setRetCode(-1);
			gp.setRetInfo("比对失败,不匹配");
		}else{	
			//比对成功
			//1. 修改密码
			u.setPlainPassword(newpassword);
			//u.setPlainPassword(newpassword);
			//2. 清空临时令牌
			u.setTempToken("");
			u.setTempTokenDate(null);
			accountService.updateUser(u);
			gp.setRetCode(1);
			gp.setRetInfo("匹配,同时修改成功修改密码");
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
	 *  通过用户名和加密的密码，获取用户正在使用的积分、认证等信息
	 * 
	 *  	根据 username（loginname） 和 加密后的   digest
	 * 
	 *  url ：Get 
	 *  	/api/v1/usertools/gogetUserRole?username={username}&digest={加密后的passwd}
	 *  
	 * 		http://localhost/lr/api/v1/usertools/gogetUserRole?username=user007&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * 
	 * 		http://localhost/lr/api/v1/usertools/gogetUserRole?username=7add6c21f9934cdaac631d16e6eafc49&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * 
	 * 
	 *   
	 * */
	@RequestMapping(value = "gogetUserRole",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
	//@ResponseBody
	public UserRole gogetUserRole(@RequestParam("username") String loginName ,@RequestParam("digest") String password) {
		Long userId = getCurrentUserId();
		User u = accountService.findUserByUserId(userId);
		if (u.getUserroleId() == null ){
			return null;
		}else{
			Long userroleId = u.getUserroleId();			
			UserRole ur = accountService.findUserRoleByUserRoleId(userroleId);
			if( ur == null ){
				return null;
			}else{
				return ur;
			}
		}
		
	}
	
	/**
	 *  通过用户名和加密的密码，增加用户正在使用的积分
	 * 
	 *  	根据 username（loginname） 和 加密后的   digest
	 *  	upoint 用户积分数值
	 * 
	 *  url ：Get 
	 *  	/api/v1/usertools/goaddUserPoint?username={username}&digest={加密后的passwd}&upoint={int}
	 *  
	 * 		
http://localhost/lr/api/v1/usertools/goaddUserPoint?username=7add6c21f9934cdaac631d16e6eafc49&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a&upoint=10
	 * 
	 * */
	@RequestMapping(value = "goaddUserPoint",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public GeneralResponse goaddUserPoint(@RequestParam("username") String loginName ,@RequestParam("digest") String password,@RequestParam("upoint") Integer userpoint) {
		GeneralResponse gp = new GeneralResponse();		
		int errcode = -1 ;
		int successcode = 1;		
		
		Long userId = getCurrentUserId();
		User u = accountService.findUserByUserId(userId);
		if (u.getUserroleId() == null ){
			gp.setRetCode(errcode);
			gp.setRetInfo("此用户不存在积分对象");			
		}else{
			Long userroleId = u.getUserroleId();			
			UserRole ur = accountService.findUserRoleByUserRoleId(userroleId);
			int urpointnum = ur.getUserpoint() ;			
			urpointnum = userpoint+urpointnum; //新增数值+ 用户原有积分数值
			ur.setUserpoint(urpointnum);
			accountService.saveUserRole(ur);
			gp.setRetCode(successcode);
			gp.setRetInfo("用户积分增加成功");		
		}

		return gp; 
	}
		
	
	/**
	 *  通过用户名和加密的密码，增加用户正在使用的信誉值
	 * 
	 *  	根据 username（loginname） 和 加密后的   digest
	 *  	ucredit 用户积分数值
	 * 
	 *  url ：Get 
	 *  	/api/v1/usertools/goaddUserCredit?username={username}&digest={加密后的passwd}&ucredit={int}
	 *  
	 * 		
http://localhost/lr/api/v1/usertools/goaddUserCredit?username=7add6c21f9934cdaac631d16e6eafc49&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a&ucredit=10
	 * 
	 * */
	@RequestMapping(value = "goaddUserCredit",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public GeneralResponse goaddUserCredit(@RequestParam("username") String loginName ,@RequestParam("digest") String password,@RequestParam("ucredit") Integer usercredit) {
		System.out.println("0000000000000000000000000000000");
		GeneralResponse gp = new GeneralResponse();		
		int errcode = -1 ;
		int successcode = 1;		
		
		Long userId = getCurrentUserId();
		User u = accountService.findUserByUserId(userId);
		if (u.getUserroleId() == null ){
			gp.setRetCode(errcode);
			gp.setRetInfo("此用户不存在信誉值对象");			
		}else{
			Long userroleId = u.getUserroleId();			
			UserRole ur = accountService.findUserRoleByUserRoleId(userroleId);
			int urcredit = ur.getUsercredit() ;			
			usercredit = usercredit+urcredit; //新增数值+ 用户原有积分数值
			ur.setUsercredit(usercredit);
			accountService.saveUserRole(ur);
			gp.setRetCode(successcode);
			gp.setRetInfo("用户信誉值增加成功");		
		}

		return gp; 
	}
	

	/**
	 *  通过用户名和加密的密码，上传图片
	 * 
	 *  根据 username 和
	 *  加密后的   digest
	 *  
	 *  返回值：
	 *  -1	上传文件为空，上传头像失败
	 *  0	服务端文件路径缺失，导致上传头像失败
	 * 	1  	上传头像成功
	 * 
	 * 
	 *  url ：
	 *  	/api/v1/usertools/uploaduserpic?username={username}&digest={加密后的passwd}
	 * 		http://localhost/lr/api/v1/usertools/uploaduserpic?username=7add6c21f9934cdaac631d16e6eafc49&digest=e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a
	 * */
	@RequestMapping(value = "uploaduserpic",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public GeneralResponse uploaduserpic(@RequestParam(value = "imageFile") MultipartFile imageFile ) {		
		//上传图片		
		Long userId = getCurrentUserId();
		
		GeneralResponse gp = new GeneralResponse();	
		//保存图片
		Date curDate = new Date();			
		if (imageFile!=null && imageFile.getContentType().contains("image")) {
            String fileName = imageFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.indexOf("."));	            
    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");	    		
    		String newFileName = userId+"_"+format.format(curDate)+suffix;
    		System.out.println(newFileName);	    	   
    		String destDir = System.getenv("IMAGE_DIR");
    		destDir="C:/uploadpic_here";
    		//System.out.println("destDir:"+destDir);
    		if (destDir == null|| destDir.equals("")) {
    			//redirectAttributes.addFlashAttribute("message", "未能获取文件保存位置，请联系系统管理员。");	    			
    			System.out.println("未能获取文件保存位置，请联系系统管理员。");
    			//空图片
        		gp.setRetCode(0);
        		gp.setRetInfo("服务端文件路径缺失，导致上传头像失败");
    		}else{	    		
	    		String path = destDir+"/"+newFileName ;
	    		File destFile = new File(path);
	    		
	    		try {
	    			imageFile.transferTo(destFile);
	    		}catch(Exception e) {	    			
	    			System.out.println("message"+e.getMessage());	    			
	    		}
	    		
	    		int  first = 1 ; 
	    		int  useing = 1 ;// 正在用   usering= 1 ; no use= 0 
	    		User u = accountService.findUserByUserId(userId);
	    		Long userpicooId = u.getUserpicooId();
	    		//Long userpicooId = userheadimgId ;
	    		
	    		if (userpicooId == null || userpicooId == 0){
	    			//新图片 之前没有图片信息
	    			UserPicoo uhi = new UserPicoo();    			
	    	    	uhi.setPicname(newFileName);
	    	    	uhi.setPicpath(path);
	    	    	uhi.setPicindate(curDate);
	    	    	uhi.setPicorder(first);
	    	    	uhi.setUseing(useing);
	    	    	uhi = accountService.saveUserPicoo(uhi);
	    	    	userpicooId = uhi.getId() ;
	    	    	u.setUserpicooId(userpicooId);
	    	    	u.setPicpathBig(newFileName);
	    	    	accountService.updateUser(u);
	    		}else{
	    			//旧图片 有相关的图片信息
	    			UserPicoo ui = accountService.findUserPicoo(userpicooId) ;
	    			if(ui == null ){
	    				//没有 图片对象，重新建立图片对象
	    				UserPicoo uhi = new UserPicoo();       	    	
	        	    	uhi.setPicname(newFileName);
	        	    	uhi.setPicpath(path);
	        	    	uhi.setPicindate(curDate);
	        	    	uhi.setPicorder(first);
	        	    	uhi.setUseing(useing);
	        	    	uhi = accountService.saveUserPicoo(uhi);
	        	    	userpicooId = uhi.getId() ;
	        	    	u.setUserpicooId(userpicooId);
	        	    	u.setPicpathBig(newFileName);
	        	    	accountService.updateUser(u);
	    			}else{
	    				//更新原有的 图片对象
	    			 	ui.setPicname(newFileName);
	        	    	ui.setPicpath(path);
	        	    	ui.setPicindate(curDate);
	        	    	ui.setPicorder(first);
	        	    	ui.setUseing(useing);
	        	    	accountService.saveUserPicoo(ui);
	    			}    	    		
	    		}
	    		gp.setRetCode(1);
	    		gp.setRetInfo("上传头像成功");
    		}
		}else{
			//空图片
    		gp.setRetCode(-1);
    		gp.setRetInfo("上传文件为空，上传头像失败");
		}		
		
		return gp;
	}
	
	
	
	
	
	
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user== null){
			return 0L;
		}else{
			return user.id;
		}	
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
