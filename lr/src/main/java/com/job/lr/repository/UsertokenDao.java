package com.job.lr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.User;
import com.job.lr.entity.Usertoken;

public interface UsertokenDao extends PagingAndSortingRepository<Usertoken, Long> {
	
	Usertoken findByUserId(Long  userId);
	Usertoken findByUsertoken(String  usertoken);
	Usertoken findByUsertokenold(String  usertoken);
	
}
