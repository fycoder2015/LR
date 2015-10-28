package com.job.lr.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import com.job.lr.entity.Category;
import com.job.lr.entity.Feedback;
import com.job.lr.repository.CategoryDao;
import com.job.lr.repository.FeedbackDao;
import com.job.lr.service.task.BaseService;

@Component
@Transactional
public class FeedbackService extends BaseService {

	@Autowired
	private FeedbackDao feedbackDao;
	
	/*public void getAllCategory() {
		this.categoryDao.f
	}*/
	
	public void create(Feedback feedback) {
		feedbackDao.save(feedback);
	}
	
	public void save(Feedback feedback) {
		feedbackDao.save(feedback);
	}


	
//	public Page<Category> pageAll(int pageNum, int pageSize) {
//		PageRequest request = this.buildPageRequest(pageNum, pageSize, "auto");
//		return categoryDao.findAll(request);
//	}
	
//	public Page<Category> pageAllTaskCate(int pageNum, int pageSize) {
//		PageRequest request = this.buildPageRequest(pageNum, pageSize, "auto");
//		return categoryDao.pageAllCate(1, request);
//	}
	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	
}
