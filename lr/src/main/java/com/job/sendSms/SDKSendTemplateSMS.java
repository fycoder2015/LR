package com.job.sendSms;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSmsSDK;



public class SDKSendTemplateSMS {

	/**
	 * be useed
	 * 
	 * @param  
	 * 			phonenum
	 * 			captchacode  验证码
	 * 		 	templatNum 模板号   
	 * 				 如果不是0  为真模板号 ，直接传值即可
	 * 				是0 ，选用默认模板号
	 * 						
	 * 		   	timegap  时间间隔
	 * 
	 * @return returnString "sendok" or "错误码 加 报错信息"  
	 * */
	public String  SendTemplateSMS(String phonenum ,String captchacode,String templatNum,String timegap) {
		String returnString = "";
		HashMap<String, Object> result = null;
		
		/**
		 * 短信四要素  start * 
		 * */
		String accountSid = "8a48b5514f4fc588014f5e24b3cc15fe";
		String accountToken = "67960318c8b8431ab8b1d189f6c95ccc";
		if("".equals(templatNum)|| templatNum == null){
			templatNum = "36337"; //短信模板号
		}else if(templatNum.equals("0")){
			templatNum = "36337"; //短信模板号
		}else{
			//采用传进的 templatNum 值。  //templatNum
		}			
		String AppId = "8a48b5514f4fc588014f5e7c410717fb";//换应用需要调整 				
		/**
		 * 短信四要素  end * 
		 * */
		
		
		
		
		
		
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();		
		//******************************注释*********************************************
		//*初始化服务器地址和端口                                                       *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		//restAPI.init("sandboxapp.cloopen.com", "8883");
		//-- restAPI.init("app.cloopen.com", "8883"); 
		restAPI.init("sandboxapp.cloopen.com", "8883"); 
		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
		/**
		 * 
		 * 短信验证码接口链接：http://www.yuntongxun.com/activity/smsDevelop#tiyan
		 * 下面是平台提供的ID信息
		 * ACCOUNT SID：8a48b5514f4fc588014f5e24b3cc15fe 
		 * TOKEN：67960318c8b8431ab8b1d189f6c95ccc
		 * (开发) Rest URL：https://sandboxapp.cloopen.com:8883
		 * (生产) Rest URL：https://app.cloopen.com:8883
		 * 
		 * */
		//-- restAPI.setAccount("8a48b5514f4fc588014f5e24b3cc15fe", "67960318c8b8431ab8b1d189f6c95ccc");
		restAPI.setAccount(accountSid, accountToken);
		
		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		
		//-- restAPI.setAppId("8a48b5514f4fc588014f5e7c410717fb");
		restAPI.setAppId(AppId);
		//
		//测试  ff8080813c37da53013c3054f567007e
		
		
		//******************************注释****************************************************************
		//*调用发送模板短信的接口发送短信                                                                  *
		//*参数顺序说明：                                                                                  *
		//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
		//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
		//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		//*第三个参数是要替换的内容数组。																														       *
		//**************************************************************************************************
		
		//**************************************举例说明***********************************************************************
		//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
		//*********************************************************************************************************************
		result = restAPI.sendTemplateSMS(phonenum,templatNum ,new String[]{captchacode,timegap});

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);				
			}
			returnString="sendok" ; //发送成功
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			returnString="错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg") ;
		}
		
		return returnString  ;
	}
}
