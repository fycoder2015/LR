
package com.job.lr.service.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.metamodel.source.annotations.attribute.type.EnumeratedTypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.entity.Daysignin;
import com.job.lr.entity.Daysigninlog;
import com.job.lr.entity.Enterprise;
import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.Subject;
import com.job.lr.entity.Task;
import com.job.lr.entity.University;
import com.job.lr.entity.UniversitySubjectRec;
import com.job.lr.entity.User;

import com.job.lr.entity.UserPicoo;
import com.job.lr.entity.UserPointsLog;
import com.job.lr.entity.UserRole;
import com.job.lr.entity.UserRoleRec;
import com.job.lr.entity.Years;
import com.job.lr.filter.Constants;
import com.job.lr.repository.DaysigninDao;
import com.job.lr.repository.DaysigninlogDao;
import com.job.lr.repository.EnterpriseDao;
import com.job.lr.repository.PhonenumberDao;
import com.job.lr.repository.SubjectDao;
import com.job.lr.repository.TaskDao;
import com.job.lr.repository.UniversityDao;
import com.job.lr.repository.UniversitySubjectRecDao;
import com.job.lr.repository.UserDao;
import com.job.lr.repository.UserHeadimgDao;
import com.job.lr.repository.UserPointsLogDao;
import com.job.lr.repository.UserRoleDao;
import com.job.lr.repository.UserRoleRecDao;
import com.job.lr.repository.YearsDao;
import com.job.lr.service.ServiceException;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

/**
 * 用户管理类.
 * 
 * @author liuy
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;
	private TaskDao taskDao;
	private UserRoleDao userroleDao;
	private PhonenumberDao  phonenumberDao;
	private UserRoleRecDao  userroleRecDao ;	
	private UserHeadimgDao	userheadimgDao ;
	private UserPointsLogDao	userpointslogDao ;
	private UniversityDao	universityDao ;
	private UniversitySubjectRecDao	universitysubjectrecDao ;
	private SubjectDao		subjectDao;
	private DaysigninDao		daysigninDao;
	private DaysigninlogDao		daysigninlogDao;
	private EnterpriseDao		enterpriseDao;
	private YearsDao		yearsDao;

	private Clock clock = Clock.DEFAULT;
	
	public UserPicoo saveUserPicoo(UserPicoo uhi) {
		UserPicoo ui = userheadimgDao.save(uhi) ;
		return ui;
	}
	
	public Daysigninlog findlastnearDaysigninlog(Long userId) {
		Daysigninlog daysigninlog ;

		List<Daysigninlog> ds = daysigninlogDao.findByUserIdOrderByIdDesc(userId) ;
		if(null == ds || ds.size() ==0){
			daysigninlog=null;
		}else{
			Iterator <Daysigninlog> dsi = ds.iterator(); 			
			daysigninlog = dsi.next(); //只取最近的一个
		}
		return daysigninlog ;
	}
	
	public Daysignin findDaysignin(Long userId) {
		Daysignin daysignin ;

		List<Daysignin> ds = daysigninDao.findByUserIdOrderByIdDesc(userId) ;
		if(null == ds || ds.size() ==0){
			daysignin=null;
		}else{
			Iterator <Daysignin> dsi = ds.iterator(); 			
			daysignin = dsi.next(); //只取最近的一个
		}
		return daysignin ;
	}
	
	public Daysignin saveDaysignin(Daysignin ds) {
		return daysigninDao.save(ds);
	}
	
	public Daysigninlog saveDaysigninlog(Daysigninlog ds) {
		return daysigninlogDao.save(ds);
	}

	
	public UserPicoo findUserPicoo(Long userHeadimgId) {
		UserPicoo ui = userheadimgDao.findOne(userHeadimgId) ;
		return ui;
	}

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	public User findUserBySmslogintoken(String smslogintoken) {
		return userDao.findBySmstoken(smslogintoken); 
	}
	
	public User findUserByUserId(Long userId) {
		return userDao.findOne(userId);
	}
	
	public University findUniversityById(Long universityId) {
		return universityDao.findOne(universityId);
	}
	
	/**
	 * true 删除成功
	 * 
	 * false 删除失败
	 * */	
	public String delUniversity(Long universityId) {
		University u = universityDao.findOne(universityId) ;
		String message  = "删除失败" ;
		if ( u == null){			
		}else{
			u.setSts("停用");
			u.setStsint(-1);
			universityDao.save(u);
			message ="学校删除成功" ;
		}
		return message;
	}
	
	
	public Subject findSubjectById(Long subjectId) {
		return subjectDao.findOne(subjectId);
	}
	
	public void saveUniversity(University entity) {
		universityDao.save(entity);
	}
	
	public void updateSubject(Subject entity) {
		subjectDao.save(entity);
	}
	public void updateYears(Years entity ,int  stsint) {
		boolean baupdate = false;
		if(stsint==1 ){ //正常			 
			entity.setSts("正常");
			baupdate=true;
		}else if(stsint==0 ) {//停用			
			entity.setSts("停用");
			baupdate=true;
		}else{
			//erro baupdate=false;
		}
		
		if(baupdate){
			entity.setStsint(stsint);
			yearsDao.save(entity);
		}
		
	}
	//新增学院
	public void addSubject(Subject entity,Long universityId) {
		Subject s = subjectDao.save(entity); //增加学院
		UniversitySubjectRec usrc = new UniversitySubjectRec() ;//增加关系表
		usrc.setSubjectId(s.getId());
		usrc.setUniversityId(universityId);
		usrc.setViewDate(new Date());
		universitysubjectrecDao.save(usrc);//保存关系表

	}
	
	//新增企业用户
	public void addEnuser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}
	
	//新增企业对象
	public void addEnterprise(Enterprise e) {
		enterpriseDao.save(e);
	}
	
	/***
	 * 删除学院
	 * 	学院下有人的话 不能删除
	 * 
	 * */
	public String delSubject(Long subjectId ,Long universityId) {
		String returnstr= "";
		String succedstr = "删除成功";
		String failstr = "该学院下面有用户，不能删除";
		
		List <User> ul =userDao.findBySubjectId(subjectId);
		
		if(null == ul || ul.size() ==0){
			//可以删  删subject表
			subjectDao.delete(subjectId);
			//删除关系表 UniversitySubjectRec 
			List<UniversitySubjectRec>  urc =universitysubjectrecDao.findByUniversityIdAndSubjectIdOrderByIdDesc(universityId, subjectId);
			if(null == urc || urc.size() ==0){}else{
				UniversitySubjectRec usrc = new UniversitySubjectRec() ;
				Iterator <UniversitySubjectRec> usrli = urc.iterator(); 			
				while (usrli.hasNext()) {
					usrc = usrli.next();
					universitysubjectrecDao.delete(usrc);					
				}
			}
			returnstr =succedstr;
			
		}else{
			//存在用户 不能删
			returnstr =failstr;
		}

		return returnstr;

	}
	 
	/***
	 * 删除入学年份
	 * 	 停用
	 * 
	 * */
	public String delYears(Long yearsId) {
		String returnstr= "";
		String succedstr = "删除成功";	
		String errostr = "删除失败";	
		Years y =yearsDao.findOne(yearsId);
		
		if(null == y){
			returnstr =errostr;
		}else{
			int stsint =0 ;  //停用 0
			y.setStsint(stsint);
			y.setSts("停用");
			yearsDao.save(y);
			returnstr =succedstr;
			
		}

		return returnstr;

	}
	
	
	/***
	 * 删除企业用户
	 * 	企业用户发布任务 不能删除， 
	 * 
	 * 	#二期
	 * */
	public String delEnuserinfo(Long enuserId ) {
		String returnstr= "";
		String succedstr = "删除成功";
		String failstr = "该企业下面有任务，不能删除";
		User u = userDao.findOne(enuserId);
		/**
		 *	注意： 
		 *		此处只是删除User表中对应的内容
		 * 		关联的Enterprise中的内容不做删除，怕影响兼职部分的发布信息
		 * */
		userDao.delete(u); //直接删用户   企业表关联的Enterprise中关联的内容不删除，不能级联删除
		returnstr =succedstr;
		
		//Enterprise e = enterpriseDao.findOne(u.getEnterprise().getId()) ;
		return returnstr;
		
	}
	
	public UserRole findUserRoleByUserRoleId(Long userroleId) {
		UserRole ur  ;
		boolean beexist = userroleDao.exists(userroleId) ;		
		if(beexist){
			ur = userroleDao.findOne(userroleId) ;
			return ur;
		}else{
			return null ;
		}
		
	}
	
	public List<UserPointsLog> findUserPointsLogByUserId (Long userId){
		List<UserPointsLog> lu =new ArrayList<UserPointsLog>();
		lu = userpointslogDao.findByUserId(userId);
		return lu ;
	}
	
	public void saveUserRole(UserRole ur) {
		userroleDao.save(ur) ;	
	}
	public void saveUserPointsLog(UserPointsLog ur) {
		userpointslogDao.save(ur) ;	
	}
	
	
	/**
	 * 比对 手机号 和 验证码 是否匹配
	 * 注意：此方法未做超时比对
	 * @return  1 匹配
	 * 			0 不匹配
	 * */
	public int checkUserPhone(String phonenumber ,String captchacode){
		int bematched = 1 ;
		int nomatched = 0 ;
		Phonenumber p = findUserPhoneInPhonenumber(phonenumber);

		if( p == null){
			return nomatched;
		}else{
			String correctcaptchacode= p.getCaptchacode();
			if("".equals(correctcaptchacode)||correctcaptchacode==null ){			
				return nomatched;
			}else{
				if(correctcaptchacode.equals(captchacode)){
					return bematched;
				}else{
					return nomatched;
				}
			}
		}		
	}
	
	
	/**
	 * 比对 手机号 和 验证码 是否匹配
	 * 注意：此方法未做超时比对
	 * @return  1 匹配
	 * 			0 不匹配
	 * */
	public int checkUserPhone2(Phonenumber p ,String captchacode){
		int bematched =1 ;
		int nomatched =0 ;
		if( p == null){
			return nomatched;
		}else{
			String correctcaptchacode= p.getCaptchacode();
			if("".equals(correctcaptchacode)||correctcaptchacode==null ){			
				return nomatched;
			}else{
				if(correctcaptchacode.equals(captchacode)){
					return bematched;
				}else{
					return nomatched;
				}
			}
		}		
	}
	
	
	
	/**
	 * 通过username 和 password查找用户 
	 * username  loginname
	 * password  digest 加密后的参数
	 * 
	 * @return  u 
	 * 
	 * */
	public User findUserByUsernamePasswd(String loginname ,String password){
		User u ;
		List <User> ul =userDao.findByLoginNameAndPasswordOrderByIdDesc(loginname,password);
		if(null == ul || ul.size() ==0){
			u = null;
		}else{
			Iterator <User> ui = ul.iterator();  
			u = ui.next();
		}
		return u ;
	}
	
	/**
	 * 通过username 和 password查找用户 
	 * username  loginname
	 * password  digest 加密后的参数
	 * 
	 * @return  u 
	 * 
	 * */
	public User findUserByPhonenumberAndTempToken(String phonenumber, String tempToken){
		User u ;
		List <User> ul =userDao.findByPhonenumberAndTempTokenOrderByIdDesc( phonenumber, tempToken);
		if(null == ul || ul.size() ==0){
			u = null;
		}else{
			Iterator <User> ui = ul.iterator();  
			u = ui.next();
		}
		return u ;
	}
	
	
	/**
	 * 通过phonenum 查找用户  在User中
	 * 
	 * @return  u 
	 * 
	 * */
	public User findUserByPhonenumber(String phonenumber){
		User u ;
		List <User> ul =userDao.findByPhonenumberOrderByIdDesc(phonenumber);
		if(null == ul || ul.size() ==0){
			u = null;
		}else{
			Iterator <User> ui = ul.iterator();  
			u = ui.next();
		}
		return u ;
	}
	
	/**
	 * 通过phonenum password  查找用户  在User中
	 * 
	 * @return  u 
	 * 
	 * */
	public User findUserByPhonenumberPassword(String phonenumber,String  password){
		User u ;
		List <User> ul =userDao.findByPhonenumberAndPasswordOrderByIdDesc(phonenumber, password);
		
		if(null == ul || ul.size() ==0){
			u = null;
		}else{
			Iterator <User> ui = ul.iterator();  
			u = ui.next();
		}
		return u ;
	}
	
	

	
	
	/**
	 * 返回对象不同
	 * */
	public Phonenumber findUserPhoneInPhonenumber(String phonenumber ){
		Phonenumber  p ;
		List <Phonenumber> lp =phonenumberDao.findByPhonenumberOrderByIdDesc(phonenumber);
		if(null == lp || lp.size() ==0){
			p = null;
		}else{
			Iterator <Phonenumber> lpi = lp.iterator();  
			p = lpi.next();
		}
		return p ;
	}
	
	/**
	 * 在已激活的手机号中查找 
	 * 返回对象不同
	 * */
	public Phonenumber findUserPhoneByPhonenumberInFindPasswd(String phonenumber ){
		Phonenumber  p ;
		int be_actived =  1;
		int phonestatus = be_actived ;
		List <Phonenumber> lp =phonenumberDao.findByPhonenumberAndPhonestatusOrderByIdDesc(phonenumber, phonestatus);
		if(null == lp || lp.size() ==0){
			p = null;
		}else{
			Iterator <Phonenumber> lpi = lp.iterator();  
			p = lpi.next();
		}
		return p ;
	}
	
	
	/**
	 * 在已激活的手机号中查找 
	 * 返回对象不同
	 * */
	public Phonenumber findUserPhoneByPhonenumberIsActivity(String phonenumber ){
		Phonenumber  p ;
		int be_actived =  1;
		int phonestatus = be_actived ;
		List <Phonenumber> lp =phonenumberDao.findByPhonenumberAndPhonestatusOrderByIdDesc(phonenumber, phonestatus);
		if(null == lp || lp.size() ==0){
			p = null;
		}else{
			Iterator <Phonenumber> lpi = lp.iterator();  
			p = lpi.next();
		}
		return p ;
	}
	
	
	
	
	/**
	 * 在已激活的手机号中查找  找回密码 中使用
	 * 返回对象不同
	 * */
	public Phonenumber findUserPhoneByPhonenumAndCaptchaInFindPasswd(String phonenumber,String captchacode ){
		Phonenumber  p ;
		int be_actived =  1;
		int phonestatus = be_actived ;
		List <Phonenumber> lp =phonenumberDao.findByPhonenumberAndCaptchacodeAndPhonestatusOrderByIdDesc(phonenumber, captchacode, phonestatus);
		if(null == lp || lp.size() ==0){
			p = null;
		}else{
			Iterator <Phonenumber> lpi = lp.iterator();  
			p = lpi.next();
		}
		return p ;
	}
	
	public List <Phonenumber>  findAllPhonenumberByphone(String phonenumber ){	
		List <Phonenumber> lp =phonenumberDao.findByPhonenumber(phonenumber);
		return lp ;
	}
	
	
	public List <Phonenumber>  findPhonenumberByPhoneAndStatus(String phonenumber, Integer  phonestatus){	
		List <Phonenumber> lp =phonenumberDao.findByPhonenumberAndPhonestatus(phonenumber, phonestatus) ;
		return lp ;
	}
	

	
	
	/**
	 * 注意： 已废弃 
	 * phonestatus   0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
	 * @deprecated
	 * */
//	public void registerUserPhone(String phonenumber ){
//		int not_activated = 0 ;
//		Phonenumber p = new Phonenumber();
//		p.setPhonenumber(phonenumber);
//		p.setCaptchacode(genRandom());
//		p.setPhonestatus(not_activated);//0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
//		p.setRegisterDate(new Date());		
//		phonenumberDao.save(p);			
//	}
//	
	
	/**
	 * phonestatus   0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
	 * 
	 * */
	public void registerUserPhone(String phonenumber ,String captchacode ){
		int not_activated = 0 ;
		Phonenumber p = new Phonenumber();
		p.setPhonenumber(phonenumber);
		p.setCaptchacode(captchacode);
		p.setPhonestatus(not_activated);//0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
		p.setRegisterDate(new Date());		
		phonenumberDao.save(p);			
	}
	
	
	/**
	 * 更新保存 Phonenumber对象
	 *  
	 * 注：
	 * 	phonestatus   0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
	 * */
	public void updatePhonenumber(Phonenumber p ){			
		//p.setCaptchacode(p);		
		//p.setRegisterDate(new Date());
		
		phonenumberDao.save(p);			
	}
	
	
	public void saveEnterprise(Enterprise en) {
		
		enterpriseDao.save(en);
	}
	
	
	/**
	 * startdate 起始时间
	 * enddate	   终止时间  new Date()
	 * gaptime   时间间隔   单位时间是  1 = 一分钟
	 * 
	 * @return
	 * 		0    超时
	 * 		1   未超时
	 * */
	public int compareTimes(Date startdate , Date enddate , int gaptime){
		int returnCode = 0 ;
		
		Long PARAM_TIMEGAP = 60000L ;//单位时间间隔(一分钟); 60*1000 = 一分钟
		Long gaptimeL = gaptime*PARAM_TIMEGAP ; //总间隔时间
		Long startdatel = startdate.getTime();
		Long enddatel =  enddate.getTime();
		
		Long totaltimeL = startdatel+gaptimeL+1000L ;
		if (totaltimeL > enddatel ){
			//未超时
			returnCode = 1 ;
		}else{
			//超时
			returnCode = 0 ;
		}
		
		return returnCode ;
	}
	
	
	public String  genRandom(){
		Random rand = new Random();
		int n = 100000+rand.nextInt(99900000);
		return n+"";
	}

	//注册用户
	public void registerUser(User user) {
		
		//创建  UserRole 数据
		int usering= 1 ;
		String rolename ="新用户" ;
		String roledescription ="新用户" ;
		int usercredit = 0 ;
		int userpoint = 0 ;
		Date daytime = clock.getCurrentDate() ; // new Date();
		UserRole ur = new UserRole();
		ur.setRoledescription(roledescription);
		ur.setRoledate(daytime);
		ur.setRolename(rolename);
		ur.setUseing(usering);
		ur.setUsercredit(usercredit);
		ur.setUserpoint(userpoint);
		UserRole newur = userroleDao.save(ur);		
		long newuserroleId = newur.getId();
		
		//创建  User 数据
		entryptPassword(user);
		user.setRoles("user");
		user.setRegisterDate(daytime);
		user.setUserroleId(newuserroleId);		
		User newu = userDao.save(user);
		
		//创建  UserRoleRec 数据
		UserRoleRec urr = new UserRoleRec();
		urr.setUserId(newu.getId());
		urr.setRoleId(newuserroleId);
		urr.setViewDate(daytime);
		userroleRecDao.save(urr);
	}
	

	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		taskDao.deleteByUserId(id);

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		//byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] salt = Constants.PARAM_SALT.getBytes() ;
		//Constants.PARAM_SALT
		//user.setSalt(Encodes.encodeHex(salt));
		user.setSalt(Constants.PARAM_SALT);
		

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	

	public String entryptPasswordByString(String  userplainpasswd) {
		//byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] salt = Constants.PARAM_SALT.getBytes() ;
		byte[] hashPassword = Digests.sha1(userplainpasswd.getBytes(), salt, HASH_INTERATIONS);
		String enPasswd= Encodes.encodeHex(hashPassword) ;
		return  enPasswd ;
	}
	
	public Years findYearsById(Long yearsId) {
		return yearsDao.findOne(yearsId);
	}
	
	/**
	 * 管理员查询用户列表
	 * */
	public Page<User> getUserlists(Long id, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		//Specification<User> spec = buildSpecification(id, searchParams);
		//return userDao.findAll(spec, pageRequest);
		//return userDao.findAll(spec);
		//return userDao.findAll(pageRequest);		
		String  rolseadmin = "admin";
		return userDao.findByRolesNotLikeOrderByIdDesc(rolseadmin, pageRequest) ;//查找角色中不包含admin字段的用户
	}	
	
	
	/**
	 * 管理员查询企业用户列表
	 * */
	public Page<User> getEnUserlists( int ensign ,int pageNumber, int pageSize,	String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);		 
		int enterprisesign =ensign ; //企业标记
		return userDao.findByEnterprisesignOrderByIdDesc(enterprisesign, pageRequest) ;//查找enterprisesign字段为1的企业用户
	}	
	
	
	/**
	 * 查询大学列表
	 * */
	public Page<University> getUniversitylists( int pageNumber, int pageSize,String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		
		Integer  stsint =1 ;//-1 为失效状态 ，不做显示  ;1 显示
		
		return universityDao.findByStsintOrderByIdDesc(stsint, pageRequest); 
	}	
	
	
	/**
	 * 查询入学年份列表
	 * */
	public Page<Years> getYearslists( int pageNumber, int pageSize,String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);	
		int beused= 1 ; 
		int stopuse = 0 ;
		int stsint=beused ;//1 正常  ;    0 停用 
		return yearsDao.findByStsintOrderByIdDesc(stsint, pageRequest)  ; 
	}	
	
	
	/**
	 * 查询大学下学院的列表
	 * */
	public Page<Subject> gogetSubjectlists( int pageNumber, int pageSize,String sortType ,Long universityId) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		List <UniversitySubjectRec> usrl =universitysubjectrecDao.findByUniversityIdOrderByIdDesc(universityId);
		
		UniversitySubjectRec usrc = new UniversitySubjectRec();
		List <Long> subjectIds= new ArrayList<Long>();
		Page<Subject> ps ;
		if(null == usrl || usrl.size() ==0){
			ps = null ;
		}else{
			Iterator <UniversitySubjectRec> usrli = usrl.iterator(); 			
			while (usrli.hasNext()) {
				usrc = usrli.next();
				subjectIds.add(usrc.getSubjectId()) ;
			}
		
		}
		ListLongComparator llc = new ListLongComparator();
		Collections.sort(subjectIds,llc); 
		//打印排序后的subjectIds
		System.out.println("in AccountServer() 中的 getSubjectlists()， 打印排序后的subjectIds List");
		for(int i=0;i<subjectIds.size();i++){
		     System.out.print(subjectIds.get(i)+",");
		}	
		if(null == subjectIds || subjectIds.size() ==0){
			ps= null;
		}else{
			ps = subjectDao.findByIdInOrderByIdDesc(subjectIds, pageRequest);
			//Integer  stsint =-1 ;//-1 为失效状态 ，不做显示	
		}
		return ps; 
	}	
	
	public void saveUser(User user) {
		this.userDao.save(user);
	}
	
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("sexy".equals(sortType)) {
			sort = new Sort(Direction.ASC, "sexy");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
//	private Specification<User> buildSpecification(Long  id, Map<String, Object> searchParams) {
//		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//		//filters.put("id", new SearchFilter("id", Operator.EQ, id));	
//		System.out.println("--------------filters.values() = "+filters.size());
//		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
//		return spec;
//	}
	

	
	

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public PhonenumberDao getPhonenumberDao() {
		return phonenumberDao;
	}
	@Autowired
	public void setPhonenumberDao(PhonenumberDao phonenumberDao) {
		this.phonenumberDao = phonenumberDao;
	}

	public UserRoleDao getUserroleDao() {
		return userroleDao;
	}
	@Autowired
	public void setUserroleDao(UserRoleDao userroleDao) {
		this.userroleDao = userroleDao;
	}

	public UserRoleRecDao getUserroleRecDao() {
		return userroleRecDao;
	}
	@Autowired
	public void setUserroleRecDao(UserRoleRecDao userroleRecDao) {
		this.userroleRecDao = userroleRecDao;
	}

	public UserHeadimgDao getUserheadimgDao() {
		return userheadimgDao;
	}
	
	
	
	public UserPointsLogDao getUserpointslogDao() {
		return userpointslogDao;
	}
	@Autowired
	public void setUserpointslogDao(UserPointsLogDao userpointslogDao) {
		this.userpointslogDao = userpointslogDao;
	}

	@Autowired
	public void setUserheadimgDao(UserHeadimgDao userheadimgDao) {
		this.userheadimgDao = userheadimgDao;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}
	@Autowired
	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public UniversitySubjectRecDao getUniversitysubjectrecDao() {
		return universitysubjectrecDao;
	}
	@Autowired
	public void setUniversitysubjectrecDao(UniversitySubjectRecDao universitysubjectrecDao) {
		this.universitysubjectrecDao = universitysubjectrecDao;
	}

	public SubjectDao getSubjectDao() {
		return subjectDao;
	}
	@Autowired
	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	public DaysigninDao getDaysigninDao() {
		return daysigninDao;
	}
	@Autowired
	public void setDaysigninDao(DaysigninDao daysigninDao) {
		this.daysigninDao = daysigninDao;
	}

	public DaysigninlogDao getDaysigninlogDao() {
		return daysigninlogDao;
	}
	@Autowired
	public void setDaysigninlogDao(DaysigninlogDao daysigninlogDao) {
		this.daysigninlogDao = daysigninlogDao;
	}

	public EnterpriseDao getEnterpriseDao() {
		return enterpriseDao;
	}
	@Autowired
	public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
		this.enterpriseDao = enterpriseDao;
	}

	public YearsDao getYearsDao() {
		return yearsDao;
	}
	@Autowired
	public void setYearsDao(YearsDao yearsDao) {
		this.yearsDao = yearsDao;
	}


	
	
	
}
