
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;
import com.job.lr.entity.User;

public interface UniversityDao extends PagingAndSortingRepository<University, Long>, JpaSpecificationExecutor<University> {

	List <University> findByCity(String city);
	//Page <University> findAllOrderByIdDesc( Pageable pageRequest);
	
	Page <University> findByStsintNotOrderByIdDesc(Integer stsint , Pageable pageRequest);
 
}
