package com.job.lr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Category;

public interface CategoryDao extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {

	@Query("from Category t where t.groupId=?1")
	public Page<Category> pageAllCate(int groupId, Pageable pageRequest);
	
}
