package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.BountyTask;

public interface BountyTaskDao extends PagingAndSortingRepository<BountyTask, Long>, JpaSpecificationExecutor<BountyTask> {

//	Page<BountyTask> findAllByUserId(Long id, Pageable pageRequest);
	
	@Query("from BountyTask t where t.auditFlag=1 and t.user.id=?1")
	Page<BountyTask> findByUserId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from BountyTask bountyTask where bountyTask.user.id=?1")
	void deleteByUserId(Long id);
	
	@Modifying
	@Query("delete from BountyTask bounty where bounty.id=?1")
	void deleteById(Long id);
	
	@Query("from BountyTask t where t.auditFlag=1 and t.paymentType=?1 and closeDate > current_timestamp()")
	Page<BountyTask> findByPaymentWay(String paymentType, Pageable pageRequest);
	
	@Query("from BountyTask t where t.auditFlag=1 and t.gender=?1 and closeDate > current_timestamp()")
	Page<BountyTask> findByGender(String gender, Pageable pageRequest);
	
	@Query("from BountyTask t where t.auditFlag=1 and t.paymentType=?1 and t.gender=?2 and closeDate > current_timestamp()")
	Page<BountyTask> findByPayTypeGender(String paymentType, String gender, Pageable pageRequest);
	
	@Query("from BountyTask t where t.auditFlag=1 and closeDate > current_timestamp()")
	Page<BountyTask> pageAuditedBounty(Pageable pageRequest);
	
}
