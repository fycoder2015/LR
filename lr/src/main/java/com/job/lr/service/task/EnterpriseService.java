package com.job.lr.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.job.lr.entity.Enterprise;
import com.job.lr.repository.EnterpriseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Component
@Transactional
public class EnterpriseService extends BaseService {

	private EnterpriseDao enterpriseDao;

	public Enterprise getEnterpriseById(Long id) {
		return enterpriseDao.findOne(id);
	}
	
	public Page<Enterprise> getPagedEnterpriseAll(int pageNum) {
		PageRequest pageRequest = buildPageRequest(pageNum, 20, "auto");
		return enterpriseDao.findAll(pageRequest);
	}
	
	public void saveEnterprise(Enterprise enterprise) {
		enterpriseDao.save(enterprise);
	}
	
	public void createEnterprise(Enterprise entity) {
		entity.setRegDate(Clock.DEFAULT.getCurrentDate());
		enterpriseDao.save(entity);
	}
	
	@Autowired
	public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
		this.enterpriseDao = enterpriseDao;
	}
	
	
	
}
