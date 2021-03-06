package com.job.lr.filter;

import org.junit.Assert;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

public class testUrl {

	public static void main(String[] args) {
		String username = "admin";  
	    String param11 = "param11";  
	    String param12 = "param12";  
	    String param2 = "param2";  
	    String key = "b4fff23ec4129a22fc8601817e399ebd7d70e4bb";  
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();  
	    params.add(Constants.PARAM_USERNAME, username);  
	    params.add("param1", param11);  
	    params.add("param1", param12);  
	    params.add("param2", param2);  
	    params.add(Constants.PARAM_DIGEST, HmacSHA256Utils.digest(key, params));  
	    params.set("param2", param2 + "1");  
	  
	    String url = UriComponentsBuilder  
	            .fromHttpUrl("http://localhost:8080/lr/login")  
	            .queryParams(params).build().toUriString();  
	    System.out.println(url);
	    /**
	     *  http://localhost:8080/hello?username=admin&param1=param11&param1=param12&param2=param21&digest=883e10ba0fea469d565851bab665fa8aa6ceb419672ef0982e842cc43995b9ee
		 * http://localhost:8080/lr/login?username=admin&param1=param11&param1=param12&param2=param21&digest=883e10ba0fea469d565851bab665fa8aa6ceb419672ef0982e842cc43995b9ee
		 * http://localhost:8080/lr/?username=admin&secretstr=admin
		 * http://localhost:8080/lr/login?username=admin
		 * 
		 * http://localhost:8080/lr/task?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb
		 * http://localhost:8080/lr/task?username=admin&secretstr=admin
		 * http://localhost:8080/lr/register
		 * 
		 * no
		 * http://localhost:8080/lr/task?username=admin&param1=param11&param1=param12&param2=param21&digest=883e10ba0fea469d565851bab665fa8aa6ceb419672ef0982e842cc43995b9ee
		 * writesess=23  写入     其他值不写入
		 * 
		 * ok
		 * http://localhost:8080/lr/task?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb&writesess=23
		 * 可以写入session 。   writesess=23 
		 * 没有写入session
		 * 
		 * http://localhost:8080/lr/task?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb&ng=0
		 * http://localhost:8080/lr/api/v1/taskApply/create/1
		 * 
		 * http://localhost:8080/lr/task?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb&writesess=23
		 * 
	     **/
//	    try {  
//	        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);  
//	    } catch (HttpClientErrorException e) {  
//	        Assert.assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());  
//	        Assert.assertEquals("login error", e.getResponseBodyAsString());  
//	    }  
	    
	    /**
	     * url
	     * 
	     * 使用token获取加密的用户页面
	     * http://127.0.0.1:8080/lr/task?usertoken=1829da327028494aa36f7d165db15166ab74141ab0414a698eef1f04c373741a
	     * 
	     * http://127.0.0.1:8080/lr/api/v1/task/getPageUserTask/1_1
	     * http://127.0.0.1:8080/lr/api/v1/usertoken/gogetUsertoken?111=
	     * 
	     * http://127.0.0.1:8080/lr/api/v1/usertoken/gogetUsertoken?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb&reset=yes
	     * 
	     * http://localhost:8080/lr/api/v1/taskCollect/create/2?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb
	     * reset 负责重置usertoken 
	     * 
	     * http://localhost:8080/lr/api/v1/taskCollect/create/2/?username=admin&digest=b4fff23ec4129a22fc8601817e399ebd7d70e4bb
	     * */

	}

}
