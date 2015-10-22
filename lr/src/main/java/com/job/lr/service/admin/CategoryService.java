package com.job.lr.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.repository.CategoryDao;
import com.job.lr.service.task.BaseService;

@Component
@Transactional
public class CategoryService extends BaseService {

	@Autowired
	private CategoryDao categoryDao;
	
	/*public void getAllCategory() {
		this.categoryDao.f
	}*/

}
