
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.UniversitySubjectRec;


public interface UniversitySubjectRecDao extends PagingAndSortingRepository<UniversitySubjectRec, Long> {
	
	
	List <UniversitySubjectRec> findByUniversityIdAndSubjectIdOrderByIdDesc (Long universityId ,Long subjectId);

	List <UniversitySubjectRec> findByUniversityIdOrderByIdDesc (Long universityId );

	
}
