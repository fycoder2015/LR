package com.job.lr.service.university;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Subject;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;
import com.job.lr.entity.UniversitySubjectRec;
import com.job.lr.entity.Usertoken;
import com.job.lr.entity.Years;
import com.job.lr.repository.SubjectDao;
import com.job.lr.repository.TaskDao;
import com.job.lr.repository.UniversityDao;
import com.job.lr.repository.UniversitySubjectRecDao;
import com.job.lr.repository.UniversityYearsRecDao;
import com.job.lr.repository.UsertokenDao;
import com.job.lr.repository.YearsDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class UniversityService {

	private UniversityDao universityDao;	
	private YearsDao yearsDao ;
	private SubjectDao subjectDao ;
	private UniversityYearsRecDao universityyearsRecDao ;
	private UniversitySubjectRecDao universitysubjectRecDao  ;
	
	
	public List<University> getAllUniversity() {
		return (List<University>) universityDao.findAll();
	}
	
	public List<University> getAllUniversityByCity(String city) {
		return (List<University>) universityDao.findByCity(city);
		
	}
	
	public List<UniversitySubjectRec> findUniversitySubjectRecByUniversityId( Long universityId){
		return  universitysubjectRecDao.findByUniversityIdOrderByIdDesc(universityId);
	}
	
	public Subject findOneSubjectBysubjectId(Long subjectId){
		return  subjectDao.findOne(subjectId);
	}
	
	public List<Years> getAllYears() {
		return (List<Years>) yearsDao.findAll();
	}
	
	public List<Years> getAllBeUsedYears() {
		int  stsint =1 ;
		return (List<Years>) yearsDao.findByStsintOrderByIdDesc(stsint);
		
	}
	

	/**
	 * Getter Setter ()
	 * 
	 * */
	public UniversityDao getUniversityDao() {
		return universityDao;
	}
	@Autowired
	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}
	public YearsDao getYearsDao() {
		return yearsDao;
	}
	@Autowired
	public void setYearsDao(YearsDao yearsDao) {
		this.yearsDao = yearsDao;
	}
	public SubjectDao getSubjectDao() {
		return subjectDao;
	}
	@Autowired
	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	public UniversityYearsRecDao getUniversityyearsRecDao() {
		return universityyearsRecDao;
	}
	@Autowired
	public void setUniversityyearsRecDao(UniversityYearsRecDao universityyearsRecDao) {
		this.universityyearsRecDao = universityyearsRecDao;
	}
	public UniversitySubjectRecDao getUniversitysubjectRecDao() {
		return universitysubjectRecDao;
	}
	@Autowired
	public void setUniversitysubjectRecDao(UniversitySubjectRecDao universitysubjectRecDao) {
		this.universitysubjectRecDao = universitysubjectRecDao;
	}




}
