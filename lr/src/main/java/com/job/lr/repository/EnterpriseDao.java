package com.job.lr.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Enterprise;

public interface EnterpriseDao extends JpaSpecificationExecutor<Enterprise>, PagingAndSortingRepository<Enterprise,Long> {

}
