
package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;

public interface UniversityDao extends PagingAndSortingRepository<University, Long>, JpaSpecificationExecutor<University> {


	//@Modifying
	//@Query("delete from Task task where task.user.id=?1")
	//void deleteByUserId(Long id);
}
