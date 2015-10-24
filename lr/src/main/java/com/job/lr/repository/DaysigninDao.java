package com.job.lr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Category;
import com.job.lr.entity.Daysignin;
import com.job.lr.entity.Daysigninlog;

public interface DaysigninDao extends PagingAndSortingRepository<Daysignin, Long>, JpaSpecificationExecutor<Daysignin> {
	List <Daysignin> findByUserIdOrderByIdDesc (Long  userId );
}
