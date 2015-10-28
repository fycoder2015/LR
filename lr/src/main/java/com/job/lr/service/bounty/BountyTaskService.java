package com.job.lr.service.bounty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.BountyTask;
import com.job.lr.entity.User;
import com.job.lr.repository.BountyTaskDao;
import com.job.lr.service.task.BaseService;

@Component
@Transactional
public class BountyTaskService extends BaseService {
	
	private BountyTaskDao bountyTaskDao;
	
	
	/**
	 * 查询所有赏金任务
	 * @param pageNum
	 * @return
	 */
	public Page<BountyTask> getPagedBountyAll(int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return bountyTaskDao.findAll(pageRequest);
	}
	
	
	/**
	 * 根据Id查询赏金任务
	 * @param id
	 * @return
	 */
	public BountyTask getBountTask(Long id) {
		return bountyTaskDao.findOne(id);
	}
	
	
	/**
	 * 保存申请记录
	 * @param entity
	 */
	public void saveBountyTask(BountyTask entity) {
		bountyTaskDao.save(entity);
	}
	
	
	/**
	 * 创建赏金任务
	 * @param entity
	 * @return
	 */
	public void createBountyTask(BountyTask entity) {
		User user = new User(getCurrentUserId());
		entity.setUser(user);
		entity.setCreateDate(Clock.DEFAULT.getCurrentDate());
		bountyTaskDao.save(entity);
	}
	
	/**
	 * 按照发布任务的用户Id分页查询赏金任务列表
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	public Page<BountyTask> getUserBountyTask(Long userId, int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return bountyTaskDao.findByUserId(userId, pageRequest);
	}
	
	public Page<BountyTask> getBountyByPaymentWay(String paymentWay, int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return bountyTaskDao.findByPaymentWay(paymentWay, pageRequest);
	}

	public Page<BountyTask> getBountyByGender(String gender, int pageNum) {
		
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return bountyTaskDao.findByGender(gender, pageRequest);
	}

	public Page<BountyTask> getBountyByPaymentGender(String paymentType,String gender, int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return bountyTaskDao.findByPayTypeGender(paymentType, gender, pageRequest);
	}
	
	public Page<BountyTask> getAuditedBounty(int pageNum,int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNum,pageSize,"auto");
		return bountyTaskDao.pageAuditedBounty(pageRequest);
	}

	@Autowired
	public void setBountyTaskDao(BountyTaskDao bountyTaskDao) {
		this.bountyTaskDao = bountyTaskDao;
	}

}
