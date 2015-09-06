package com.job.lr.service.account;

import java.util.Random;

public class genRandom {

	//生成随机数
	public static void main(String[] args) {
		Random rand = new Random();
		int n = 100000+rand.nextInt(99900000);
		System.out.println(n);

	}

}
