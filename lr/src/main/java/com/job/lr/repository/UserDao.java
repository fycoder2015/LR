
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.job.lr.entity.Task;
import com.job.lr.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
	

	User findByLoginName(String loginName);
	
	List <User> findByLoginNameAndPasswordOrderByIdDesc (String loginName ,String password);
	
	List <User> findByPhonenumberOrderByIdDesc (String Phonenumber );
	
	List <User> findByPhonenumberAndPasswordOrderByIdDesc (String Phonenumber,String password );
	
	List <User> findByPhonenumberAndTempTokenOrderByIdDesc(String phonenumber,String tempToken);

	Page <User> findByRolesNotLikeOrderByIdDesc(String rolseadmin, Pageable pageRequest);
	
	List <User> findBySubjectId(Long subjectId );
	
}
