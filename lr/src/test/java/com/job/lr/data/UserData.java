package com.job.lr.data;

import com.job.lr.entity.User;
import org.springside.modules.test.data.RandomData;

public class UserData {

	public static User randomNewUser() {
		User user = new User();
		user.setLoginName(RandomData.randomName("user"));
		user.setName(RandomData.randomName("User"));
		user.setPlainPassword(RandomData.randomName("password"));

		return user;
	}
}
