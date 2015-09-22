
package com.job.lr.service.account;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.job.lr.entity.Phonenumber;
import com.job.lr.entity.User;
import com.job.lr.filter.Constants;
import com.job.lr.repository.PhonenumberDao;
import com.job.lr.repository.TaskDao;
import com.job.lr.repository.UserDao;
import com.job.lr.service.ServiceException;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

/**
 * 用户管理类.
 * 
 * @author calvin
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
	private PhonenumberDao  phonenumberDao;
	private Clock clock = Clock.DEFAULT;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	/**
	 * 比对 手机号 和 验证码 是否匹配
	 * @return  1 匹配
	 * 			0 不匹配
	 * */
	public int checkUserPhone(String phonenumber ,String captchacode){
		int bematched =1 ;
		int nomatched =0 ;
		Phonenumber p  = findUserPhone(phonenumber);

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
	
	public Phonenumber findUserPhone(String phonenumber ){
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
	 * 仅仅更新 验证码 和 更新日期
	 * phonestatus   0 ,未激活  not_activated ； 1，已激活 ； 2，解绑  <暂时不用>
	 * */
	public void updatePhonenumber(Phonenumber p ){			
		//p.setCaptchacode(p);		
		//p.setRegisterDate(new Date());		
		phonenumberDao.save(p);			
	}
	
	/**
	 * startdate 起始时间
	 * enddate	   终止时间  new Date()
	 * gaptime   时间间隔   1 = 一分钟
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

	public void registerUser(User user) {
		entryptPassword(user);
		user.setRoles("user");
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
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
	
	
	
}
