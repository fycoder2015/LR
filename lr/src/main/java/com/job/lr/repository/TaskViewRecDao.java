package com.job.lr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.TaskViewRec;

public interface TaskViewRecDao extends JpaSpecificationExecutor<TaskViewRec>, PagingAndSortingRepository<TaskViewRec, Long> {

	@Query("from TaskViewRec t where t.taskId=?1 and t.userId=?2")
	List<TaskViewRec> findByTaskUserId(Long taskId, Long userId);
}
