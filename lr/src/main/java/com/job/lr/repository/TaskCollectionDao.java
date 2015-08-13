package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.TaskCollection;

public interface TaskCollectionDao<T> extends JpaSpecificationExecutor<TaskCollection>, PagingAndSortingRepository<TaskCollection,Long> {

	@Query("from TaskCollection t where t.task.id=?1")
	Page<TaskCollection> findByTaskId(Long id, Pageable pageRequest);
	
	@Query("from TaskCollection c where c.user.id=?1")
	Page<TaskCollection> findByUserId(Long id, Pageable pageRequest);
}
