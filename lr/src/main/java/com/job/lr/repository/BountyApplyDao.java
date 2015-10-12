package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.BountyApply;

public interface BountyApplyDao extends PagingAndSortingRepository<BountyApply, Long>, JpaSpecificationExecutor<BountyApply> {

	@Query("from BountyApply t where t.applyUser.id=?1")
	public Page<BountyApply> getByApplyUser(Long userId, Pageable pageRequest);
	
	@Query("from BountyApply t where t.taskUserId=?1")
	public Page<BountyApply> getByBountyUser(Long userId, Pageable pageRequest);
	
	@Query("from BountyApply t where t.applyUser.id=?1 and t.taskUserId=?2 ")
	public BountyApply getOne(Long userId,Long bountyId);
	
	@Query("from BountyApply t where t.bountyTask.id=?1")
	public Page<BountyApply> getByBounty(Long bountyId,Pageable pageRequest);
	
	
}
