package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.TaskApplyRecord;

public interface TaskApplyRecordDao extends PagingAndSortingRepository<TaskApplyRecord,Long>,JpaSpecificationExecutor<TaskApplyRecord> {
	
	@Query("from TaskApplyRecord t where t.taskId=?1")
	Page<TaskApplyRecord> findByTaskId(Long id, Pageable pageRequest);

}
