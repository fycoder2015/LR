package com.job.lr.service.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.entity.Task;
import com.job.lr.entity.Usertoken;
import com.job.lr.repository.TaskDao;
import com.job.lr.repository.UsertokenDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class UsertokenService {

	private UsertokenDao usertokenDao;	

	public UsertokenDao getUsertokenDao() {
		return usertokenDao;
	}
	
	@Autowired
	public void setUsertokenDao(UsertokenDao usertokenDao) {
		this.usertokenDao = usertokenDao;
	}

	public Usertoken getUsertoken(Long userId) {
		return usertokenDao.findByUserId(userId);
	}
	
	public void saveUsertoken(Usertoken entity) {
		usertokenDao.save(entity);
	}
	
	public Usertoken findUsertoken(Long  userId) {
		return usertokenDao.findByUserId(userId);
	}
}
