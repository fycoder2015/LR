
package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.Subject;
import com.job.lr.entity.Years;

public interface YearsDao extends PagingAndSortingRepository<Years, Long>, JpaSpecificationExecutor<Years> {


	//@Modifying
	//@Query("delete from Task task where task.user.id=?1")
	//void deleteByUserId(Long id);
}
