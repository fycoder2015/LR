package com.job.lr.filter;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.job.lr.entity.User;
import com.job.lr.entity.Usertoken;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.UsertokenService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
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
     * ----------------------------------
     * username =  loginName
     * digest   =  password  
     * 
     * writesess=23  当是用户密码时候，23写入session 其他不写session 
     * nj = 0  不去跳转
     * ----------------------------------
     * usertoken 传递token
     *  reset 负责重置usertoken 
     * ---------------------------------- 
     *  链接示例：
     *  http://127.0.0.1:8080/lr/api/v1/usertoken/gogetUsertoken?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb
     *  http://127.0.0.1:8080/lr/api/v1/usertoken/gogetUsertoken?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb&reset=yes
     *  可以发起重置请求 挂上参数 ：reset=yes 
     *  
     *  
	 *  http://127.0.0.1:8080/lr/task?usertoken=ed89a34c93904ce2b50fb65030fc980cd31756a6da5a4c8db59596038b734d60 
	 *  使用token发起验证
	 *  
     * */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	System.out.println("in StatelessAuthcFilter 的 onAccessDenied() ---");    	
    	String  login_to_action= "/task" ;//登录成功后跳转到的页面 
    	
    	String urlparam_token = ""; //链接中传递的参数1
    	String urlparam_username_passwd = ""; //链接中传递的参数2
    	String urlparam_writesess_flag = ""; //链接中传递的参数3  23写入session
    	int urlparam_jump_flag = 2 ;//0 不去跳转     2 跳转
    	
        //----1、客户端生成的消息摘要   seceretstr 等同于密钥
        String clientDigest = request.getParameter("digest"); 
        //System.out.println("clientDigest:"+clientDigest);
        //----2、客户端传入的用户身份
        String username = request.getParameter("username");	       
        String requsertoken = request.getParameter("usertoken");
        //-- 标记参数 writesess 
        String writesess = request.getParameter("writesess");//判断是否需要将参数写入session，值为23 写入，其他不写入， writesess = 25 不跳转 
        													 //注意：当是从token中获取时，不能写session ( fromuserToken= true)
        String nojump = request.getParameter("nj");//跳转flag
       
        if(nojump == null || "".equals(nojump)){
        	urlparam_jump_flag =2;
        }else if("0".equals(nojump)){//传送0的值时 不去跳转
        	urlparam_jump_flag =0;
        }else{
        	urlparam_jump_flag =2;
        }
        
        boolean fromuserToken = false;//判断是否来自 request中的usertoken ，true 的话不会在session中写入 username和digest
        							  //flase 的话，表明不是来自 usertoken，可以在session中写入 
    	HttpServletRequest httpreq = (HttpServletRequest) request;
        //----2.1 测试session中是否有参数
        if(clientDigest == null ||username==null||"".equals(clientDigest)||  "".equals(username)){        
        	username  = (String)httpreq.getSession().getAttribute("username");     
        	clientDigest  = (String)httpreq.getSession().getAttribute("digest");
        	
        }
        //获取request中 usertoken的值 并比对相应的token值内容 
        //获取相应的 用户的 username（loginName）  password（clientDigest）
        if(requsertoken==null || "".equals(requsertoken)){        	
        }else{
        	fromuserToken= true;
        	Usertoken ut = usertokenService.findUsertokenByToken(requsertoken);
        	if(ut!= null){
	        	String usertoken1 =ut.getUsertoken() ; //较新的token
	        	String usertoken2 =ut.getUsertokenold() ;//较旧的token
	        	//比对时间是否超时 +date.getTime()
	        	Long starttime = ut.getStartDate().getTime(); //起始时间 
	        	Long totaltime = starttime+Constants.PARAM_TIMEGAP+Constants.PARAM_TIMEGAP;
	        	Long nowtime = new Date().getTime();
	        	//System.out.println("nowtime:" +nowtime);
	        	//System.out.println("starttime:" +starttime);
	        	//比对上传的token和数据库中的token是否一致 ---无用 可以删除
	        	if( usertoken1.equals(requsertoken) || usertoken2.equals(requsertoken) ){        		
		        	if (username == null|| "".equals(username)||clientDigest==null || "".equals(clientDigest)){
		        		User u = accountService.getUser(ut.getUserId()) ;
		        		if (u != null){		        			
			            	if(nowtime < totaltime) {
			            		//在允许时间内 赋予 username 和 clientDigest 的值 后面继续走
			            		username=u.getLoginName();
			            		clientDigest=u.getPassword(); 
			            		urlparam_token="usertoken="+requsertoken;
			            	}else{
			            		//时间超限 不允登陆
			            		username ="";
			            		clientDigest="";
			             		onLoginFail(response); //6、登录失败
			    	            return false;
			            	}
		        		}
		        	}
	        	}
        	}
        }
        
        //String seceretstr = request.getParameter(Constants.PARAM_SECRETSTR); //MD5加密后的str值        
        //----3、客户端请求的参数列表
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
        //----6、登录成功 传递登陆的 token
        	username = username.trim() ;
        	clientDigest = clientDigest.trim();
        	User user = accountService.findUserByLoginName(username);
        	String userpassword = user.getPassword();
        	if (userpassword.equals(clientDigest)){
        		//写入 session 中  
        		//当 fromuserToken=FLASE 时可以写入 
        		//当writesess = 23时 ，写入session，  username digest
        		urlparam_username_passwd="username="+username+"&digest="+clientDigest;
        		if(!fromuserToken ){
        			if ("".equals(writesess) || writesess== null){
        				
        			}else if (writesess.equals("23") ){
        			 //写session
        			 //System.out.println( "---- 写入 session");
        			 httpreq.getSession().setAttribute("username", username);	
        			 httpreq.getSession().setAttribute("digest", userpassword);
        			 urlparam_writesess_flag = "writesess=23" ;        			 
        			}else{        				
        			}
        		}
        		
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
	   //----7、委托给 StatelessRealm 进行登录 
	            	//SecurityUtils.getSubject().login(token);
	            	getSubject(request, response).login(token);	            	
	            	HttpServletResponse httpresponse = (HttpServletResponse) response;

	         	   //----8、随后跳转到不同的页面进行登录 	         
	            	if(urlparam_jump_flag != 0){//可以跳转  urlparam_jump_flag=0时,不能跳转
	            	   	String toactionnanme = httpreq.getServletPath();//获取现在的页面跳转action， 如/task
		            	String urlgetparm = httpreq.getQueryString(); //类似username=xxx&passwd= 
	            		String urlpath = httpreq.getContextPath()+"/task?"+urlgetparm;
	            		
//	            		if(toactionnanme!=login_to_action){ //来自 token  login_to_action 登录后跳转到的页面
//	            			System.out.println("--1111---");
//	            			urlpath=urlpath+"&nj=0"; //加上不去跳转的标志            		    
//	            		    //RequestDispatcher dispatcher = httpreq.getRequestDispatcher(urlpath);
//	            		    //dispatcher .forward(httpreq, httpresponse);
//	            		    System.out.println("做跳转");
//	            			System.out.println("--1111---toactionnanme---------"+toactionnanme+"-----urlpath:"+urlpath);
//	            			//httpresponse.sendRedirect(urlpath);	        		   
//	            		}	            	
	            	}
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