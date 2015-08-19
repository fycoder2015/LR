/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.job.lr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.User;
import com.job.lr.entity.Usertoken;

public interface UsertokenDao extends PagingAndSortingRepository<Usertoken, Long> {
	Usertoken findByUserId(Long  userId);
	
	
}
