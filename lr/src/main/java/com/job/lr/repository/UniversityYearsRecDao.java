
package com.job.lr.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.job.lr.entity.UniversitySubjectRec;
import com.job.lr.entity.UniversityYearsRec;


public interface UniversityYearsRecDao extends PagingAndSortingRepository<UniversityYearsRec, Long> {
	
	
	List <UniversityYearsRec> findByUniversityIdAndYearsIdOrderByIdDesc (Long universityId ,Long yearsId);

	List <UniversityYearsRec> findByUniversityIdOrderByIdDesc (Long universityId );

	List <UniversityYearsRec> findByYearsIdOrderByIdDesc (Long yearsId );
}
