
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Phonenumber;


public interface PhonenumberDao extends PagingAndSortingRepository<Phonenumber, Long> {
	List <Phonenumber> findByPhonenumberOrderByIdDesc(String phonenumber);
}
