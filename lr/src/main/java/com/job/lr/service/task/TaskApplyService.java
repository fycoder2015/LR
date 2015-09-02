package com.job.lr.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.TaskApplyRecord;
import com.job.lr.repository.TaskApplyRecordDao;

@Component
@Transactional
public class TaskApplyService extends BaseService {
	
	private TaskApplyRecordDao taskApplyDao;
	
	public TaskApplyRecord getApply(Long id) {
		return taskApplyDao.findOne(id);
	}
	
	/**
	 * 保存申请记录
	 * @param entity
	 */
	public void saveApply(TaskApplyRecord entity) {
		taskApplyDao.save(entity);
	}
	
	/**
	 * 创建申请记录
	 * @param entity
	 * @return
	 */
	public void createApply(TaskApplyRecord entity) {
		
		entity.setApplyDate(Clock.DEFAULT.getCurrentDate());
		taskApplyDao.save(entity);

	}
	
	/**
	 * 根据任务Id分页查询申请记录
	 * @param taskId
	 * @param pageNum
	 * @return
	 */
	public Page<TaskApplyRecord> findPageByTaskId(Long taskId,int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		
		return taskApplyDao.findByTaskId(taskId, pageRequest);
				
	}

	@Autowired
	public void setTaskApplyDao(TaskApplyRecordDao taskApplyDao) {
		this.taskApplyDao = taskApplyDao;
	}
	
	
	
}
