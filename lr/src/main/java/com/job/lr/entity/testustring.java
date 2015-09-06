package com.job.lr.entity;

import java.util.UUID;

public class testustring {
	/**
	 * 生成 UUID 字符串
	 * 
	 * */
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();  
		UUID uuid2 = UUID.randomUUID();  
        String str = uuid.toString(); 
        String str2 = uuid2.toString(); 
        //去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + 
        			str.substring(14, 18) + str.substring(19, 23) + str.substring(24); 
        String temp2 = str2.substring(0, 8) + str2.substring(9, 13) + 
    			str2.substring(14, 18) + str2.substring(19, 23) + str2.substring(24); 
        System.out.println(temp+temp2);
	}
	

}
