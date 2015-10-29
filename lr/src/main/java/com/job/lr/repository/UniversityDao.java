
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
	@Deprecated
	Page <University> findByStsintNotOrderByIdDesc(Integer stsint , Pageable pageRequest);
	@Deprecated
	List <University> findByStsintNotOrderByIdDesc(Integer stsint);
	//查找装填值为1的内容，1为做显示
	Page <University> findByStsintOrderByIdDesc(Integer stsint , Pageable pageRequest);
	List <University> findByStsintOrderByIdDesc(Integer stsint);
	List <University> findByCityAndStsintOrderByIdDesc(String city,Integer stsint);
}
