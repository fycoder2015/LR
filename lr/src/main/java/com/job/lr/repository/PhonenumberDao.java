
package com.job.lr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Phonenumber;


public interface PhonenumberDao extends PagingAndSortingRepository<Phonenumber, Long> {
	Phonenumber findByPhonenumber(String phonenumber);
}
