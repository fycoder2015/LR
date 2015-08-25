package com.job.lr.rest;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.job.lr.service.account.ShiroDbRealm.ShiroUser;

public class ControllerUtil {

	public static Long getCurrentUserId() {
		Subject s = SecurityUtils.getSubject();
		ShiroUser  su = (ShiroUser)s.getPrincipal() ;
		
		return ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).id;
		
		
	}
}
