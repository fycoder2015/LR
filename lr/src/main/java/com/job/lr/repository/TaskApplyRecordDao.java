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
	
	@Query("from TaskApplyRecord t where t.taskId=?1 and t.user.id=?2")
	Page<TaskApplyRecord> findByTaskAndUser(Long taskId, Long userId, Pageable pageRequest);
	
	@Query("from TaskApplyRecord t where t.user.id=?1 and t.sts=?2")
	Page<TaskApplyRecord> findByUserAndApplySts(Long userId, String sts, Pageable pageRequest);
	
	@Query("from TaskApplyRecord t where t.taskId=?1 and t.sts=?2")
	Page<TaskApplyRecord> findByTaskAndApplySts(Long taskId, String sts, Pageable pageRequest);
	
}
