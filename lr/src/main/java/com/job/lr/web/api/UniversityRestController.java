package com.job.lr.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import javax.validation.Validator;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job.lr.entity.GeneralResponse;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Subject;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;
import com.job.lr.entity.UniversitySubjectRec;
import com.job.lr.entity.User;
import com.job.lr.entity.UserRole;
import com.job.lr.entity.Years;
import com.job.lr.repository.UniversityDao;
import com.job.lr.repository.UserDao;
import com.job.lr.rest.RestException;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import com.job.lr.service.university.UniversityService;
import com.job.lr.tools.UserPhoneTools;

/**
 * University  大学  的Restful API的Controller.
 * 
 * @author liuy
 */

@RestController
@RequestMapping(value = "/api/v1/university")
public class UniversityRestController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UniversityService universityService;
	
	private static Logger logger = LoggerFactory.getLogger(UniversityRestController.class);
	
	@Autowired
	private Validator validator;
	

	/**
	 * 所有大学列表
	 * url ：
	 * 	/api/v1/university/gogetUniversity
	 * 
	 * 	http://localhost/lr/api/v1/university/gogetUniversity
	 * 
	 * @return
	 */
	//@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@RequestMapping(value = "/gogetUniversity", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<University> gogetUniversity() {
		return universityService.getAllUniversity() ;		
	}
	

	/**
	 * 某大学所有专业列表
	 * url ：
	 * 	/api/v1/university/gogetSubjects?universityId={universityId}
	 * 
	 * 	http://localhost/lr/api/v1/university/gogetSubjects?universityId=1
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gogetSubjects", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Subject> gogetSubject(@RequestParam("universityId") Long universityId ) {
		List <Subject> ss = new ArrayList<Subject>() ;
		List <UniversitySubjectRec> lusrec = universityService.findUniversitySubjectRecByUniversityId(universityId) ;
		
		if(null == lusrec || lusrec.size() ==0){
			 
		}else{
			Iterator <UniversitySubjectRec> lusreci = lusrec.iterator();  
			while(lusreci.hasNext()){
				UniversitySubjectRec usrec = lusreci.next();
				Long subjectId = usrec.getSubjectId() ;
				Subject s = universityService.findOneSubjectBysubjectId(subjectId) ;
				ss.add(s);							
			}			
		}		
		return ss; 
	}

	/**
	 * 所有年份列表
	 * url ：
	 * 	/api/v1/university/gogetYears
	 * 
	 * 	http://localhost/lr/api/v1/university/gogetYears
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gogetYears", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Years> gogetYears() {
		return universityService.getAllYears() ;		
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user== null){
			return 0L;
		}else{
			return user.id;
		}	
	}
	
	

	
}
