
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.User;
import com.job.lr.entity.UserRole;
import com.job.lr.entity.UserRoleRec;

public interface UserRoleRecDao extends PagingAndSortingRepository<UserRoleRec, Long> {
	
	

	
}
