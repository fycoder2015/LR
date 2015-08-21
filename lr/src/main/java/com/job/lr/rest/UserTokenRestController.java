package com.job.lr.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.job.lr.entity.Task;
import com.job.lr.entity.User;
import com.job.lr.entity.Usertoken;
import com.job.lr.filter.Constants;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.UsertokenService;
import com.job.lr.service.task.TaskService;

/**
 * UserToken的Restful API的Controller.
 * 
 * @author liuy
 */

@RestController
@RequestMapping(value = "/api/v1/usertoken")
public class UserTokenRestController {
	
	private static Logger logger = LoggerFactory.getLogger(UserTokenRestController.class);

	@Autowired
	private UsertokenService usertokenService;

	public UsertokenService getUsertokenService() {
		return usertokenService;
	}

	public void setUsertokenService(UsertokenService usertokenService) {
		this.usertokenService = usertokenService;
	}
	@Autowired
	protected AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	
	/**
	 * 获取现有usertoken
	 * 
	 * username = loginName
	 * digest = password
	 * @return
	 */
	@RequestMapping(value = "/gogetUsertoken",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String gogetUsertoken(HttpServletRequest request) {
		System.out.println("in UserTokenRestController ---- gogetUsertoken()");
		String username = request.getParameter("username");
		String digest = request.getParameter("digest");
		String resettoken = request.getParameter("reset");
		User u = accountService.findUserByLoginName(username);
		if(u!=null){
			if(digest == null ||"".equals(digest)){
				return null;
			}else{
				if(digest.equals(u.getPassword())){
					//用户名和密码都匹配 返回token
					Long userId = u.getId();
					Usertoken ut =usertokenService.findUsertoken(userId);
					String returntoken = "";
					
					if(("yes").equals(resettoken)){	
						//重置Usertoken
						String newtoken = ut.getNewtoken() ;				
						ut.setStartDate(new Date());
						ut.setUserId(userId); 
						ut.setUsertoken(newtoken);
						ut.setUsertokenold(newtoken);
						ut.setUsertokenold2("");		
						usertokenService.saveUsertoken(ut);	
						returntoken = newtoken;						
						return returntoken;
					}else{						
						//不重置
						if( ut == null){
							//新建usertoken
							ut =new Usertoken ();
							ut.setStartDate(new Date());
							ut.setUserId(userId);
							String newtoken = ut.getNewtoken() ; 
							ut.setUsertoken(newtoken);
							ut.setUsertokenold(newtoken);
							ut.setUsertokenold2("");
							//System.out.println("ut.getId():"+ut.getId());
							//System.out.println("ut.getUserId:"+ut.getUserId());
							usertokenService.saveUsertoken(ut);							
							returntoken = newtoken;
						}else{
							//已存在usertoken对象
							Date StartDate = ut.getStartDate();
							Date nowDate  = new Date() ;
							Long StartDatel = StartDate.getTime() ;
							Long nowDatel = nowDate.getTime() ;
							//两小时的时间间隔+初始时间
							Long totaltime= StartDatel+Constants.PARAM_TIMEGAP;
							
							if ( nowDatel > totaltime) {
								//超时 生成新的usertoken 
								String newtoken = ut.getNewtoken() ;
								String oldtoken = ut.getUsertoken() ;
								ut.setUsertoken(newtoken);
								totaltime =totaltime+StartDatel; //超过两倍的间隔时间
								if(nowDatel < totaltime){ //处于可以并用期间
									ut.setUsertokenold(oldtoken);
								}else{
									//过了并用时间
									ut.setUsertokenold(newtoken);
								}
								ut.setStartDate(nowDate);
								usertokenService.saveUsertoken(ut);
								returntoken = newtoken;
								
							}else{
								//未超时1 直接返回 returntoken 
								returntoken = ut.getUsertoken();
							}
						}
						
						return returntoken;
					}
				}else{
					return null;
				}
			}			
		}else{
			return null;
		}
		
	}
	

}
