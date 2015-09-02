package com.job.lr.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.TaskViewRec;
import com.job.lr.repository.TaskViewRecDao;

@Component
@Transactional
public class TaskViewRecService extends BaseService{

	@Autowired
	private TaskViewRecDao viewRecDao;
	
	public void createViewRec(Long taskId,Long userId) {
		TaskViewRec viewRec = new TaskViewRec();
		viewRec.setTaskId(taskId);
		viewRec.setUserId(userId);
		
		viewRec.setViewDate(Clock.DEFAULT.getCurrentDate());
		
		viewRecDao.save(viewRec);
	}
	
	public void saveViewRec(TaskViewRec rec) {
		viewRecDao.save(rec);
	}
}
