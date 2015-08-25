package com.job.lr.rest;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Task;
import com.job.lr.entity.TaskCollection;
import com.job.lr.entity.User;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.task.TaskCollectionService;

@RestController
@RequestMapping(value = "/api/v1/taskCollect")
public class TaskCollectRestController {
	
	private static Logger logger = LoggerFactory.getLogger(TaskRestController.class);


	@Autowired
	TaskCollectionService collectionService;

	@Autowired
	private Validator validator;
	
	/**
	 * 根据Id查询申请收藏记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public TaskCollection get(@PathVariable("id") Long id) {
		
		TaskCollection coll = collectionService.getCollection(id);
		if (coll == null) {
			String message = "申请不存在(id:" + id + ")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return coll;
	}
	
	/**
	 * 根据任务Id分页查询收藏列表
	 * @param taskId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/pageByTaskId/{taskId}_{pageNum}",method = RequestMethod.GET) 
	public Page<TaskCollection> pageByTaskId (@PathVariable("taskId") Long taskId,
			@PathVariable("pageNum") int pageNum){
		return collectionService.findPageByTaskId(taskId,pageNum);
	}
	
	/**
	 * 根据用户Id分页查询收藏列表
	 * @param userId:用户的id
	 * @param pageNum:页码数
	 * @return:收藏记录的列表
	 */
	@RequestMapping(value = "/pageByUserId/{userId}_{pageNum}",method = RequestMethod.GET) 
	public Page<TaskCollection> pageByUserId (@PathVariable("userId") Long userId,
			@PathVariable("pageNum") int pageNum){
		return collectionService.findPageByUserId(userId, pageNum);
	}
	
	/**
	 * 取消收藏
	 * @param id:收藏记录的id
	 * @return:操作结果
	 */
	@RequestMapping(value = "/disable/{id}",method = RequestMethod.GET) 
	public GeneralResponse diableCollect (@PathVariable("id") Long id){
		try {
			TaskCollection collect = collectionService.getCollection(id);
			if (collect.getUser().getId().equals(ControllerUtil.getCurrentUserId())) {
				collect.setSts("S");
				collectionService.saveCollection(collect);
			}
			else {
				return new GeneralResponse(-1,"无权进行此操作");
			}
		}
		catch (Exception e) {
			return new GeneralResponse(-1,e.getMessage());
		}
		
		return new GeneralResponse(0,"成功");
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println("create user.id +user.id： "+user.id);
		return user.id;
	}
	
	/**
	 * 通过向URL发起GET请求建立收藏记录
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/create/{taskId}",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse createApply(@PathVariable("taskId") Long taskId) {
		System.out.println("taskId:"+taskId);
		System.out.println("----ControllerUtil.getCurrentUserId():"+ControllerUtil.getCurrentUserId());
		TaskCollection entity = new TaskCollection();
		
		Task task = new Task();
		task.setId(taskId);
		entity.setTask(task);
		
		User user = new User();
		
		user.setId(ControllerUtil.getCurrentUserId());
		entity.setUser(user);
		
		try {
			collectionService.createCollection(entity);
		}
		catch (Exception e) {
			GeneralResponse response = new GeneralResponse(-1,e.getMessage());
			return response;
		}
		
		GeneralResponse response = new GeneralResponse();
		return response;
	}
	
	/**
	 * 通过向URL提交REST对象建立任务申请记录
	 * @param apply
	 * @param uriBuilder
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody TaskCollection collection, UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, collection);

		// 保存任务
		collectionService.createCollection(collection);

		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = collection.getId();
		URI uri = uriBuilder.path("/api/v1/taskCollect/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

}
