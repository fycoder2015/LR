package com.job.lr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Category;
import com.job.lr.entity.Daysignin;
import com.job.lr.entity.Daysigninlog;
import com.job.lr.entity.User;

public interface DaysigninlogDao extends PagingAndSortingRepository<Daysigninlog, Long>, JpaSpecificationExecutor<Daysigninlog> {
	List <Daysigninlog> findByUserIdOrderByIdDesc (Long  userId );
}
