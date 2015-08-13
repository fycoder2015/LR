package com.job.lr.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.TaskCollection;
import com.job.lr.repository.TaskCollectionDao;

@Component
@Transactional
public class TaskCollectionService extends BaseService {

	private TaskCollectionDao<TaskCollection> taskCollectionDao;


	public TaskCollection getCollection(Long id) {
		return taskCollectionDao.findOne(id);
	}
	
	/**
	 * 保存收藏记录
	 * @param entity
	 */
	public void saveCollection(TaskCollection entity) {
		taskCollectionDao.save(entity);
	}
	
	/**
	 * 创建收藏记录
	 * @param entity
	 * @return
	 */
	public void createCollection(TaskCollection entity) {
		
		entity.setCollectDate(Clock.DEFAULT.getCurrentDate());
		taskCollectionDao.save(entity);

	}
	
	/**
	 * 根据任务Id分页查询收藏记录
	 * @param taskId
	 * @param pageNum
	 * @return
	 */
	public Page<TaskCollection> findPageByTaskId(Long taskId,int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		
		return taskCollectionDao.findByTaskId(taskId, pageRequest);
				
	}
	
	/**
	 * 根据用户Id分页查询收藏记录
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	public Page<TaskCollection> findPageByUserId(Long userId, int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		
		return taskCollectionDao.findByUserId(userId, pageRequest);
	}
	
	

	@Autowired
	public void setTaskCollectionDao(TaskCollectionDao<TaskCollection> taskCollectionDao) {
		this.taskCollectionDao = taskCollectionDao;
	}

	
	
}
