
package com.job.lr.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Task;
import com.job.lr.entity.User;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.task.TaskService;

/**
 * Task的Restful API的Controller.
 * 
 * @author suiys
 */
@RestController
@RequestMapping(value = "/api/v1/task")
public class TaskRestController {

	private static Logger logger = LoggerFactory.getLogger(TaskRestController.class);

	@Autowired
	private TaskService taskService;
	
//	@Autowired
//	private TaskViewRecService viewRecService;

	@Autowired
	private Validator validator;

	/**
	 * 所有任务列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Task> list() {
		return taskService.getAllTask();
	}
	
	/**
	 * 查询获取所有“开放”状态任务列表
	 * @return
	 */
	@RequestMapping(value = "/getAllOpenTask", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Task> listOpenTask() {
		return taskService.getAllOpenTask();
	}
	
	/**
	 * 分页查询获取“开放”状态的任务列表
	 * @param pageNum
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "/getPageOpenTask/{cityId}_{pageNum}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<Task> listPageOpenTask(@PathVariable("pageNum") int pageNum,
			@PathVariable("cityId") String cityId) {
		return taskService.getPagedOpenTask(pageNum,cityId);
	}
	
	@RequestMapping(value = "/getPageUserTask/{userId}_{pageNum}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<Task> listUserTask(@PathVariable("userId") Long userId,
			@PathVariable("pageNum") int pageNum) {
		return taskService.getUserTask(userId, pageNum);
	}
	
	/**
	 * 根据任务ID关闭任务
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/close/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse closeTask(@PathVariable("id") Long id) {
		return taskService.closeTask(id);
	}
	
	/**
	 * 兼职任务PV、UV统计接口
	 * @param taskId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/pvUvRec/{taskId}_{userId}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse pvUvRec(@PathVariable("taskId") Long taskId,
			@PathVariable("userId") Long userId) {
		try {
			taskService.pvUvRec(taskId, userId); 
		}
		catch (Exception e) {
			return new GeneralResponse(-1,e.getMessage());
		}
		return new GeneralResponse();
	}

	/**
	 * 根据任务Id获取任务对象
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Task get(@PathVariable("id") Long id) {
		Task task = taskService.getTask(id);
		if (task == null) {
			String message = "任务不存在(id:" + id + ")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return task;
	}

	/**
	 * JSON方式提交任务内容，创建任务
	 * @param task
	 * @param uriBuilder
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody Task task, UriComponentsBuilder uriBuilder) {
		System.out.println("-- task 中 ------------create");
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, task);

		// 保存任务
		taskService.createTask(task);
		
		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = task.getId();
		URI uri = uriBuilder.path("/api/v1/task/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 创建Task任务
	 * 通过Post方法传递参数
	 * 
	 * */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public String create(@Valid Task newTask, RedirectAttributes redirectAttributes) {
		//System.out.println(" i am in here TaskRestCollection()create()");
		
		Long CurrentUserId = getCurrentUserId();
		if(CurrentUserId== 0L){
			return "get user fail";
		}else{
			User user = new User(CurrentUserId);
			newTask.setUser(user);
			taskService.createTask(newTask);
			//redirectAttributes.addFlashAttribute("message", "创建任务成功");
			return "crearte succeed";
		}
	}
	
	/**
	 * 根据工作类别查询通过审核的接口
	 * @param pageNum
	 * @param pageSize
	 * @param jobClass
	 * @return
	 */
	@RequestMapping(value = "/pageByClass/{pageNum}_{pageSize}_{jobClass}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<Task> pageByClass(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,
			@PathVariable("jobClass") String jobClass) {
		return taskService.pageTaskByClass(jobClass, pageNum, pageSize);
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


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	// 按Restful风格约定，返回204状态码, 无内容. 也可以返回200状态码.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Task task) {
		
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, task);

		// 保存任务
		taskService.saveTask(task);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		taskService.deleteTask(id);
	}

}
