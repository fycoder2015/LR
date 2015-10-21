package com.job.lr.service.task;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.TaskApplyRecord;
import com.job.lr.entity.User;
import com.job.lr.repository.TaskApplyRecordDao;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;

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
	public GeneralResponse createApply(TaskApplyRecord entity) {
		
		GeneralResponse resp = new GeneralResponse();
		
		PageRequest pageRequest = buildPageRequest(1, 40, "auto");
		
		Page<TaskApplyRecord> taskPage = taskApplyDao.findByTaskAndUser(entity.getTaskId(), entity.getUser().getId(), pageRequest);
		
		if (taskPage.getNumberOfElements()>0) {
			resp.setRetCode(-1);
			resp.setRetInfo("用户已经存在任务申请记录");
		}
		else {
			entity.setApplyDate(Clock.DEFAULT.getCurrentDate());
			taskApplyDao.save(entity);
		}
		
		return resp;

	}
	
	public GeneralResponse cancelApply(Long applyId) {
		
		GeneralResponse resp = new GeneralResponse();
		
		Long currentUserId =((ShiroUser) SecurityUtils.getSubject().getPrincipal()).id;
		
		TaskApplyRecord apply = this.taskApplyDao.findOne(applyId);
		
		if (apply==null) {
			resp.setRetCode(-1);
			resp.setRetInfo("申请记录不存在");
		}
		else if (!(apply.getUser().getId().equals(currentUserId))) {
			resp.setRetCode(-1);
			resp.setRetInfo("当前用户与申请记录中的用户不一致，无权取消申请");
		}
		else {
			apply.setSts("C");
			taskApplyDao.save(apply);
		}
		return resp;
	}
	
	/**
	 * 根据发起申请用户的ID和申请的状态分页查询申请列表
	 * @param userId 用户ID
	 * @param sts 申请状态
	 * @param pageNum 页数
	 * @param pageSize 每页记录数
	 * @return
	 */
	public Page<TaskApplyRecord> pageByUserAndSts(Long userId, String sts, int pageNum, int pageSize) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, pageSize, "auto");
		
		return this.taskApplyDao.findByUserAndApplySts(userId, sts, pageRequest);
	}
	
	/**
	 * 根据兼职任务ID和申请状态分页查询申请列表
	 * @param taskId 兼职任务ID
	 * @param sts 申请状态
	 * @param pageNum 页数
	 * @param pageSize 每页记录数
	 * @return
	 */
	public Page<TaskApplyRecord> pageByTaskAndSts(Long taskId, String sts, int pageNum, int pageSize) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, pageSize, "auto");
		
		return this.taskApplyDao.findByTaskAndApplySts(taskId, sts, pageRequest);
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
