
package com.job.lr.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Task;
import com.job.lr.entity.TaskViewRec;
import com.job.lr.repository.TaskDao;
import com.job.lr.repository.TaskViewRecDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.utils.Clock;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class TaskService {

	private TaskDao taskDao;
	
	private TaskViewRecDao viewRecDao;

	public Task getTask(Long id) {
		return taskDao.findOne(id);
	}

	public void saveTask(Task entity) {
		taskDao.save(entity);
	}
	
	public void createTask(Task entity) {
		entity.setCreateTime(Clock.DEFAULT.getCurrentDate());
		taskDao.save(entity);
	}

	public void deleteTask(Long id) {
		taskDao.delete(id);
	}

	public List<Task> getAllTask() {
		return (List<Task>) taskDao.findAll();
	}
	
	public Page<Task> pageAllTask(int pageNumber,int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		return taskDao.findAll(pageRequest);
	}

	public Page<Task> getUserTask(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Task> spec = buildSpecification(userId, searchParams);

		return taskDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 按照用户Id分页查询任务列表
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	public Page<Task> getUserTask(Long userId, int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		Map<String, Object> searchParams = new HashMap<String, Object> ();
//		searchParams.put("user.id", userId);
		Specification<Task> spec = buildSpecification(userId, searchParams);
//		Specification<Task> spec = buildSpecification(searchParams);
		return taskDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 分页查询“开放”状态的任务列表
	 * @param pageNumber
	 * @param cityId
	 * @return
	 */
	public Page<Task> getPagedOpenTask(int pageNumber,String cityId) {
		
		PageRequest pageRequest = buildPageRequest(pageNumber, 20, "auto");
		Map<String, Object> searchParams = new HashMap<String, Object> ();
		searchParams.put(Operator.EQ+"_jobSts", "开放");
		searchParams.put(Operator.EQ+"_cityId", cityId);
		Specification<Task> spec = this.buildSpecification(searchParams);
		return taskDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 查询获取所有处于“开放”状态的任务列表
	 * @return
	 */
	public List<Task> getAllOpenTask() {
		
		Map<String, Object> searchParams = new HashMap<String, Object> ();
		searchParams.put(Operator.EQ+"_jobSts", "D");
		Specification<Task> spec = this.buildSpecification(searchParams);
		return taskDao.findAll(spec);
		
	}
	
	/**
	 *  根据任务分类查询兼职任务信息
	 * @param jobClass
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Task> pageTaskByClass(String jobClass, int pageNum, int pageSize) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, pageSize, "auto");
		return this.taskDao.pageByClass(jobClass, pageRequest);
	}
	
	/**
	 * 把任务的状态置为“关闭”
	 * @param id
	 */
	public GeneralResponse closeTask(Long id) {
		
		try {
			
			Task task  = taskDao.findOne(id);
			task.setJobSts("关闭");
			taskDao.save(task);
		} 
		catch (Exception e) {
			return new GeneralResponse(1,e.getMessage());
		}
		
		
		return new GeneralResponse();
	}
	
	/**
	 * 把任务的状态置为“开放”
	 * @param id
	 */
	public GeneralResponse openTask(Long id) {
		
		try {
			
			Task task  = taskDao.findOne(id);
			task.setJobSts("开放");
			taskDao.save(task);
		} 
		catch (Exception e) {
			return new GeneralResponse(1,e.getMessage());
		}
		
		return new GeneralResponse();
	}
	
	/**
	 * 更新任务的pv、uv值
	 * @param taskId
	 * @param userId
	 */
	public void pvUvRec(Long taskId, Long userId) {
		
		Task task = taskDao.findOne(taskId);
		task.setPv(task.getPv()==null?(1):(task.getPv()+1));
		
		List<TaskViewRec> viewRecList = viewRecDao.findByTaskUserId(taskId, userId);
		
		if (viewRecList == null || viewRecList.size()<1) {
			TaskViewRec viewRec = new TaskViewRec(userId,taskId);
			viewRecDao.save(viewRec);
			
			task.setUv(task.getUv()==null?(1):(task.getUv()+1));
		}
		
		taskDao.save(task);
	}
	
	/**
	 * 分页查询所有通过审核的兼职任务信息（审核通过，且为开放状态）
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Task> getAuditedTask(int pageNum, int pageSize) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, pageSize, "auto");
		return taskDao.pageAuditedTask(pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Task> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Task> spec = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
		return spec;
	}
	
	/**
	 * 创建动态条件查询
	 * @param searchParams
	 * @return
	 */
	private Specification<Task> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Task> spec = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
		return spec;
	}
	
	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Autowired
	public void setViewRecDao(TaskViewRecDao viewRecDao) {
		this.viewRecDao = viewRecDao;
	}
}
