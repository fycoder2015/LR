package com.job.lr.filter;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;


public class StatelessToken implements AuthenticationToken {

    private String username;
    private Map<String, ?> params;
    private String clientDigest;
    private String url;

    public StatelessToken(String username,  Map<String, ?> params, String clientDigest) {
        this.username = username;
        this.params = params;
        this.clientDigest = clientDigest;
    }
    
    
    public StatelessToken(String username,  String url, String clientDigest) {
        this.username = username;
        this.url = url;
        this.clientDigest = clientDigest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public  Map<String, ?> getParams() {
        return params;
    }

    public void setParams( Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    
    
    
    
    public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	@Override
    public Object getPrincipal() {
       return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }
}