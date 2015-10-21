
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.User;
import com.job.lr.entity.UserRole;

public interface UserRoleDao extends PagingAndSortingRepository<UserRole, Long> {
	
	
//	List <User> findByLoginNameAndPasswordOrderByIdDesc (String loginName ,String password);
//	
//	List <User> findByPhonenumberOrderByIdDesc (String Phonenumber );
//	
//	List <User> findByPhonenumberAndPasswordOrderByIdDesc (String Phonenumber,String password );
	
}
