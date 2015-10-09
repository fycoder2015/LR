package com.job.lr.service.bounty;

import org.springframework.beans.factory.annotation.Autowired;
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
}
