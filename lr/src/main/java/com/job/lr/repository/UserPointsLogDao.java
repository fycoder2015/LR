
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.Task;
import com.job.lr.entity.UserPointsLog;

public interface UserPointsLogDao extends PagingAndSortingRepository<UserPointsLog, Long>, JpaSpecificationExecutor<UserPointsLog> {

	Page<UserPointsLog> findByUserId(Long id, Pageable pageRequest);

	List<UserPointsLog> findByUserId(Long userId);
}
