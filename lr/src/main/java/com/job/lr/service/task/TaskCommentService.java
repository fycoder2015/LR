package com.job.lr.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.TaskComment;
import com.job.lr.repository.TaskCommentDao;

@Component
@Transactional
public class TaskCommentService extends BaseService {
	
	@Autowired
	private TaskCommentDao commentDao;
	
	public TaskComment getComment(Long id) {
		return commentDao.findOne(id);
	}
	
	/**
	 * 保存收藏记录
	 * @param entity
	 */
	public void saveComment(TaskComment entity) {
		commentDao.save(entity);
	}
	
	/**
	 * 创建评论
	 * @param entity
	 */
	public void createComment(TaskComment entity) {
		entity.setCommentDate(Clock.DEFAULT.getCurrentDate());
		commentDao.save(entity);
	}
	
	/**
	 * 根据任务Id分页查询收藏记录
	 * @param taskId
	 * @param pageNum
	 * @return
	 */
	public Page<TaskComment> findPageByTaskId(Long taskId,int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		
		return commentDao.findByTaskId(taskId, pageRequest);
				
	}
	
	/**
	 * 根据用户Id分页查询收藏记录
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	public Page<TaskComment> findPageByUserId(Long userId, int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		
		return commentDao.findByUserId(userId, pageRequest);
	}

}
