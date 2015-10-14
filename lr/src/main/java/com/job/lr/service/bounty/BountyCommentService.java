package com.job.lr.service.bounty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.entity.BountyComment;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.repository.BountyCommentDao;
import com.job.lr.service.task.BaseService;

@Transactional
@Component
public class BountyCommentService extends BaseService {

	@Autowired
	private BountyCommentDao commentDao;
	
	public GeneralResponse createComment(BountyComment comment) {
		GeneralResponse resp = new GeneralResponse();
		commentDao.save(comment);
		return resp;
	}
	
	
	public BountyComment getById(Long commentId) {
		return commentDao.findOne(commentId);
	}
	
	public Page<BountyComment> pageAll(int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return commentDao.findAll(pageRequest);
	}
	
	public Page<BountyComment> pageByApplyId(Long applyId, int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return commentDao.pageByApplyId(applyId, pageRequest);
	}
	
	public Page<BountyComment>  pageByBountyId (Long bountyId, int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return commentDao.pageByBountyId(bountyId, pageRequest);
	}
	
	public  Page<BountyComment> pageByBountyUserId(Long commentedUserId, int pageNum){
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return commentDao.pageByCommentedUserId(commentedUserId, pageRequest);
	}
	
	public  Page<BountyComment> pageByCommentUserId(Long commentUserId, int pageNum){
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return commentDao.pageByCommentUserId(commentUserId, pageRequest);
	}
	
	public Page<BountyComment> pageByCommentedUserId(Long commentedUserId,int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return commentDao.pageByCommentedUserId(commentedUserId, pageRequest);
	}
}
