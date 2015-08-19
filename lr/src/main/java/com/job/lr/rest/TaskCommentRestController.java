package com.job.lr.rest;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.Validator;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Task;
import com.job.lr.entity.TaskCollection;
import com.job.lr.entity.TaskComment;
import com.job.lr.entity.User;
import com.job.lr.service.task.TaskCommentService;

@RestController
@RequestMapping(value = "/api/v1/taskComment")
public class TaskCommentRestController {
	
	private static Logger logger = LoggerFactory.getLogger(TaskRestController.class);


	@Autowired
	TaskCommentService commentService;

	@Autowired
	private Validator validator;
	
	/**
	 * 根据Id查询评论记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public TaskComment get(@PathVariable("id") Long id) {
		
		TaskComment comment = commentService.getComment(id);
		if (comment == null) {
			String message = "评论不存在(id:" + id + ")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return comment;
	}
	
	/**
	 * 根据任务Id分页查询评论列表
	 * @param taskId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/pageByTaskId/{taskId}_{pageNum}",method = RequestMethod.GET) 
	public Page<TaskComment> pageByTaskId (@PathVariable("taskId") Long taskId,
			@PathVariable("pageNum") int pageNum){
		return commentService.findPageByTaskId(taskId,pageNum);
	}
	
	/**
	 * 根据用户Id分页查询评论列表
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/pageByUserId/{userId}_{pageNum}",method = RequestMethod.GET) 
	public Page<TaskComment> pageByUserId (@PathVariable("userId") Long userId,
			@PathVariable("pageNum") int pageNum){
		return commentService.findPageByUserId(userId, pageNum);
	}
	
	
	/**
	 * 删除评论
	 * @param id:评论记录的id
	 * @return:操作结果
	 */
	@RequestMapping(value = "/disable/{id}",method = RequestMethod.GET) 
	public GeneralResponse diableComment (@PathVariable("id") Long id){
		try {
			TaskComment comment = commentService.getComment(id);
			if (comment.getUser().getId().equals(ControllerUtil.getCurrentUserId())) {
				comment.setSts("S");
				commentService.saveComment(comment);
			}
			else {
				return new GeneralResponse(-1,"无权进行此操作");
			}
		}
		catch (Exception e) {
			return new GeneralResponse(-1,e.getMessage());
		}
		
		return new GeneralResponse();
	}
	
	
	@RequestMapping(value = "create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public GeneralResponse create(@Valid TaskComment comment) {
		
		try {
			User user = new User(ControllerUtil.getCurrentUserId());
			comment.setUser(user);

			commentService.createComment(comment);
		}
		catch (Exception e) {
			return new GeneralResponse(-1,e.getMessage());
		}
		
		return new GeneralResponse();
		
	}
	
	/**
	 * 通过向URL发起GET请求建立收藏记录
	 * @param taskId
	 * @return
	 */
//	@RequestMapping(value = "/create/{taskId}",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
//	public GeneralResponse createApply(@PathVariable("taskId") Long taskId) {
//		
//		TaskCollection entity = new TaskCollection();
//		
//		Task task = new Task();
//		task.setId(taskId);
//		entity.setTask(task);
//		
//		User user = new User();
//		user.setId(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).id);
//		entity.setUser(user);
//		
//		try {
//			collectionService.createCollection(entity);
//		}
//		catch (Exception e) {
//			GeneralResponse response = new GeneralResponse(-1,e.getMessage());
//			return response;
//		}
//		
//		GeneralResponse response = new GeneralResponse();
//		return response;
//	}
	
	
	
	
	/**
	 * 通过向URL提交REST对象建立任务申请记录
	 * @param apply
	 * @param uriBuilder
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/createRest", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody TaskComment comment, UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, comment);

		// 保存任务
		commentService.createComment(comment);

		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = comment.getId();
		URI uri = uriBuilder.path("/api/v1/taskComment/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

}
