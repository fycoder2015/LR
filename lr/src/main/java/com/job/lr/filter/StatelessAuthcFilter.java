package com.job.lr.filter;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.job.lr.entity.User;
import com.job.lr.entity.Usertoken;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.UsertokenService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>User: liuy
 * <p>Date: 15-8-26
 * <p>Version: 1.0
 * 
 * in use
 */
public class StatelessAuthcFilter extends AccessControlFilter {
	
	protected AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	protected UsertokenService usertokenService;
	
	public UsertokenService getUsertokenService() {
		return usertokenService;
	}

	public void setUsertokenService(UsertokenService usertokenService) {
		this.usertokenService = usertokenService;
	}

	/**
	 * step 1 :  be use
	 * 是根据当前请求上下文信息每次请求时都要登录的认证过滤器。
	 * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	//System.out.println("in StatelessAuthcFilter 的isAccessAllowed()");
        return false;
    }

    /**
     * 自定义认证过滤器
     * 
     * 认证方式选择此方法 
     * 
     * step 2 :  be use
     * 
     * */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	System.out.println("in StatelessAuthcFilter 的 onAccessDenied() ---");
        //1、客户端生成的消息摘要   seceretstr 等同于密钥
        String clientDigest = request.getParameter("digest"); 
        //System.out.println("clientDigest:"+clientDigest);
        //2、客户端传入的用户身份
        String username = request.getParameter("username");
        //String seceretstr = request.getParameter(Constants.PARAM_SECRETSTR); //MD5加密后的str值        
        //3、客户端请求的参数列表
        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        params.remove(Constants.PARAM_DIGEST);
        boolean remeberme = Constants.PARAM_REMBER;
        String hostip = request.getRemoteHost();
        //System.out.println("username:"+username);
        //System.out.println("clientDigest:"+clientDigest);
        //System.out.println("param1:"+params.get("param1"));
        //System.out.println("hostip:"+hostip);
        //4、生成无状态Token  liuy add
        //  判断用户是否可以登录 ，比对用户的数据库加密后密码和上传的加密后的密码是否一致
        //  一致进入登录，不一致退出登录
	        //StatelessToken token = new StatelessToken(username, params, clientDigest);
	        //change org.apache.shiro.web.filter.authc.AuthenticatingFilter  liuy add
	        //AuthenticationToken token = createToken(request, response);     
	        //System.out.println("StatelessAuthcFilter onAccessDenied ok");
        if ("".equals(username)|| username ==null||"".equals(clientDigest)||clientDigest==null ){
        	onLoginFail(response); //6、登录失败
        	return false;
        }else{
        	//6、登录成功 传递登陆的 token
        	username = username.trim() ;
        	clientDigest = clientDigest.trim();
        	User user = accountService.findUserByLoginName(username);
        	String userpassword = user.getPassword();
        	if (userpassword.equals(clientDigest)){
	        	//AuthenticationToken token = new UsernamePasswordToken(username, seceretstr, remeberme, hostip);
	        	StatelessToken token = new StatelessToken(username, params, clientDigest);  
	        	/** 
	             * To ShiroDbRealm.class  old
	             * 
	             * TO StatelessRealm.class new
	             * */
	        	Usertoken ut = new  Usertoken();
	        	Long userId =user.getId() ;
	        	ut.setUserId(userId);
	        	System.out.println("开始查询 Usertoken");
	        	usertokenService.findUsertoken(userId);
	        	
	            try {
	                //5、委托给 StatelessRealm 进行登录
	            	//SecurityUtils.getSubject().login(token);
	            	getSubject(request, response).login(token);
	            } catch (Exception e) {
	                e.printStackTrace();
	                onLoginFail(response); //6、登录失败
	                return false;
	            }
	            return true;
        	}else{        		
        		 onLoginFail(response); //6、登录失败
	             return false;
        	}
        }
        
    }

    
    
    

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response) throws IOException {
    	System.out.println("in StatelessAuthcFilter 的 onLoginFail()");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}