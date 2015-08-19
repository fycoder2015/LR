
package com.job.lr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
