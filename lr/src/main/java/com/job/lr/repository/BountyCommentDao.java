package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.BountyComment;

public interface BountyCommentDao extends PagingAndSortingRepository<BountyComment, Long>, JpaSpecificationExecutor<BountyComment> {

	@Query("from BountyComment t where t.apply.id=?1")
	public Page<BountyComment> pageByApplyId(Long applyId, Pageable pageRequest);
	
	@Query("from BountyComment t where t.commentUser.id=?1")
	public Page<BountyComment> pageByCommentUserId(Long userId, Pageable pageRequest);
	
	@Query("from BountyComment t where t.byCommentUserId=?1")
	public Page<BountyComment> pageByCommentedUserId(Long userId,  Pageable pageRequest);
	
	@Query("from BountyComment t where t.apply.bountyTask.id=?1")
	public Page<BountyComment> pageByBountyId(Long bountyId, Pageable pageRequest);
	
}
