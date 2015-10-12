package com.job.lr.service.bounty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.BountyApply;
import com.job.lr.entity.BountyTask;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.User;
import com.job.lr.repository.BountyApplyDao;
import com.job.lr.repository.BountyTaskDao;
import com.job.lr.service.task.BaseService;

@Component
@Transactional
public class BountyApplyService extends BaseService {

	@Autowired
	private BountyApplyDao applyDao;
	
	@Autowired
	private BountyTaskDao bountyDao;
	
	/**
	 * 分页查询所有揭榜申请记录
	 * @param pageNum
	 * @return
	 */
	public Page<BountyApply> pageAllApply(int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return applyDao.findAll(pageRequest);
	}
	
	/**
	 * 按照赏金任务Id分页查询揭榜申请记录
	 * @param bountyId
	 * @param pageNum
	 * @return
	 */
	public Page<BountyApply>  pageByBountyId(Long bountyId, int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return applyDao.getByBounty(bountyId, pageRequest);
	}
	
	/**
	 * 按照揭榜申请用户ID分页查询揭榜申请记录
	 * @param applyUserId
	 * @param pageNum
	 * @return
	 */
	public Page<BountyApply> pageByApplyUser(Long applyUserId,int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return applyDao.getByApplyUser(applyUserId, pageRequest);
	}
	
	/**
	 * 按照赏金任务发布用户Id分页查询揭榜申请记录
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	public Page<BountyApply> pageByBountyUser(Long userId,int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return applyDao.getByBountyUser(userId, pageRequest);
	}
	
	/**
	 * 根据申请用户Id和赏金任务Id查询申请记录
	 * @param userId
	 * @param bountyId
	 * @return
	 */
	public BountyApply getOne(Long userId, Long bountyId) {
		return applyDao.getOne(userId, bountyId);
	}
	
	public BountyApply getOne(Long applyId) {
		return applyDao.findOne(applyId);
	}
	
	public GeneralResponse saveApply(BountyApply apply){
		
		this.applyDao.save(apply);
		GeneralResponse response = new GeneralResponse();
		return response;
	}
	
	/**
	 * 创建用户对赏金任务的申请记录，对应“揭榜”操作
	 * @param taskId
	 * @param applyUserId
	 */
	public GeneralResponse createApply(Long taskId, Long applyUserId) {
		
		GeneralResponse response = new GeneralResponse();
		
		BountyApply apply = this.getOne(applyUserId, taskId);
		
		if (apply != null) {
			response.setRetCode(-1);
			response.setRetInfo("用户ID"+applyUserId+"已存在申请记录");
			return response;
		}
		
		apply = new BountyApply();
		
		BountyTask task = bountyDao.findOne(taskId);		
		apply.setBountyTask(task);
		apply.setTaskUserId(task.getUser().getId());
		
		User user = new User();
		user.setId(applyUserId);
		apply.setApplyUser(user);
		
		apply.setApplyDate(Clock.DEFAULT.getCurrentDate());
		
		applyDao.save(apply);
		
		return response;
	}
	
	
	/**
	 * 赏金任务发布者确认提交的揭榜申请操作
	 * @param applyId
	 * @return
	 */
	public GeneralResponse confirmApply(Long applyId) {
		
		GeneralResponse response = new GeneralResponse();
		
		BountyApply apply = applyDao.findOne(applyId);
		
		if (apply==null) {
			response.setRetCode(-1);
			response.setRetInfo("没有找到ID为"+applyId+"的申请记录");
			return response;
		}
		
		if (apply.getSts().equals("D")) {
			response.setRetCode(-1);
			response.setRetInfo("该申请已经为确认状态");
			return response;
		}
		
		Long currentUserId = getCurrentUserId();
		
		if (!apply.getTaskUserId().equals(currentUserId)) {
			response.setRetCode(-1);
			response.setRetInfo("当前用户与赏金任务发布用户不一致，无权确认。");
			return response;
		}
		
		apply.setSts("D");
		
		applyDao.save(apply);
		
		BountyTask bounty = apply.getBountyTask();
		
		bounty.setSts("已委派");
		
		bountyDao.save(bounty);
		
		return response;
	}
	
	public GeneralResponse cancelApply(Long applyId) {
		
		GeneralResponse response = new GeneralResponse();
		
		BountyApply apply = applyDao.findOne(applyId);
		
		if (apply==null) {
			response.setRetCode(-1);
			response.setRetInfo("没有找到ID为"+applyId+"的申请记录");
			return response;
		}
		
		Long currentUserId = getCurrentUserId();
		
		if (!apply.getApplyUser().getId().equals(currentUserId)) {
			response.setRetCode(-1);
			response.setRetInfo("当前用户与与申请揭榜用户不一致，无权取消。");
			return response;
		}
		
		apply.setSts("C");
		
		applyDao.save(apply);
		
		return response;
		
	}

}
