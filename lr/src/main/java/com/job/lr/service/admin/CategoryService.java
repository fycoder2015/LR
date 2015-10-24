package com.job.lr.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import com.job.lr.entity.Category;
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
	
	public void create(Category category) {
		categoryDao.save(category);
	}
	
	public void save(Category category) {
		categoryDao.save(category);
	}
	
	public Page<Category> pageAll(int pageNum, int pageSize) {
		PageRequest request = this.buildPageRequest(pageNum, pageSize, "auto");
		return categoryDao.findAll(request);
	}
	
	public Page<Category> pageAllTaskCate(int pageNum, int pageSize) {
		PageRequest request = this.buildPageRequest(pageNum, pageSize, "auto");
		return categoryDao.pageAllCate(1, request);
	}
	
	public Page<Category> pageAllBountyCate(int pageNum, int pageSize) {
		PageRequest request = this.buildPageRequest(pageNum, pageSize, "auto");
		return categoryDao.pageAllCate(2,request);
	}
	
	public Category findOne(Long id) {
		return categoryDao.findOne(id);
	}
	
	public void delete(Long id) {
		categoryDao.delete(id);
	}
	
}
