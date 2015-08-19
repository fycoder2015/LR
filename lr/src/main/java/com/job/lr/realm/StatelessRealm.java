package com.job.lr.realm;


import com.job.lr.entity.User;
import com.job.lr.filter.HmacSHA256Utils;
import com.job.lr.filter.StatelessToken;
import com.job.lr.service.account.AccountService;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public class StatelessRealm extends AuthorizingRealm {
	
	protected AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
    
    
	/**
	 * 认证回调函数,登录时调用.
	 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	System.out.println("in StatelessRealm 的  doGetAuthenticationInfo() ---");
    	StatelessToken statelessToken = (StatelessToken) token;
        String username = statelessToken.getUsername();
        if (username == null || ("").equals(username)){
        	return null ;
        }else {        	
        	User user = accountService.findUserByLoginName(username);
        	//String key = getKey(username);//根据用户名获取密钥（和客户端的一样）
        	String key = user.getPassword(); //数据库存储的key 加密后的
        	String upkey =statelessToken.getClientDigest() ; //上传key ，加密后的
        	//System.out.println("key:"+key);
        	//System.out.println("upkey:"+upkey);        	
        	if (key == null || "".equals(key) ||upkey == null || "".equals(upkey)){        		
        		return null ;//走到这里程序出错
        	}else if(upkey.equals(key) ){ //二次验证 liuy add
        		//在服务器端生成客户端参数消息摘要
        		//String serverDigest = HmacSHA256Utils.digest(key, statelessToken.getParams());
        		//System.out.println("上传密钥："+upkey);
        		//System.out.println("serverDigest:"+serverDigest);   
        		//System.out.println("数据库密码:"+key);    
        		//然后进行客户端消息摘要和服务器端消息摘要的匹配
//        		return new SimpleAuthenticationInfo(
//                username,
//                serverDigest,
//                getName());        	
        		return new SimpleAuthenticationInfo(
        			new ShiroUser(user.getId(), user.getLoginName(), user.getName()),
        			upkey,//statelessToken.getClientDigest()
                    getName());        	
        	}else{
        		return null ;//走到这里程序出错
        	}
        }
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
        //String username = (String) principals.getPrimaryPrincipal();
        //SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        //authorizationInfo.addRole("admin");
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		System.out.println("user.getRoleList():"+user.getRoleList());
		info.addRoles(user.getRoleList());
        return info;
    }


//    private String getKey(String username) {//得到密钥，此处硬编码一个
//        if("admin".equals(username)) {
//            return "dadadswdewq2ewdwqdwadsadasd";
//        }
//        return null;
//    }
}