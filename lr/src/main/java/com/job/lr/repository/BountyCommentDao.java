package com.job.lr.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.BountyComment;

public interface BountyCommentDao extends PagingAndSortingRepository<BountyComment, Long>, JpaSpecificationExecutor<BountyComment> {

}
