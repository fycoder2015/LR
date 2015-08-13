package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.TaskComment;

public interface TaskCommentDao extends JpaSpecificationExecutor<TaskComment>, PagingAndSortingRepository<TaskComment, Long> {

	@Query("from TaskComment t where t.sts='A' and t.task.id=?1")
	Page<TaskComment> findByTaskId(Long id, Pageable pageRequest);
	
	@Query("from TaskComment c where c.sts='A' and c.user.id=?1")
	Page<TaskComment> findByUserId(Long id, Pageable pageRequest);
}
