package com.job.lr.rest;

import org.apache.shiro.SecurityUtils;
import com.job.lr.service.account.ShiroDbRealm.ShiroUser;

public class ControllerUtil {

	static Long getCurrentUserId() {
		return ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).id;
	}
}
