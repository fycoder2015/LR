
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
	
	List <User> findByLoginNameAndPasswordOrderByIdDesc (String loginName ,String password);
	
	List <User> findByPhonenumberOrderByIdDesc (String Phonenumber );
	
	List <User> findByPhonenumberAndPasswordOrderByIdDesc (String Phonenumber,String password );
	
	List <User> findByPhonenumberAndTempTokenOrderByIdDesc(String phonenumber,String tempToken);
	
}
