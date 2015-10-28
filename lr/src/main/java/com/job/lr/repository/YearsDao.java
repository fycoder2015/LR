
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.Subject;
import com.job.lr.entity.University;
import com.job.lr.entity.Years;

public interface YearsDao extends PagingAndSortingRepository<Years, Long>, JpaSpecificationExecutor<Years> {


	//@Modifying
	//@Query("delete from Task task where task.user.id=?1")
	//void deleteByUserId(Long id);
	
	
	Page <Years> findByStsintOrderByIdDesc(Integer stsint , Pageable pageRequest);
	
	List <Years> findByStsintOrderByIdDesc(Integer stsint);
	
}
