package com.example.springbootwebsocketdemo;

import com.example.springbootwebsocketdemo.controller.UserController;
import com.example.springbootwebsocketdemo.model.User;
import com.example.springbootwebsocketdemo.model.UserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWebsocketDemoApplicationTests {

	@Test
	public void testUserResponse() {
		UserController userController = new UserController();
		UserResponse userResponse= userController.getUser(new User("Jemmy"));
		Assert.assertEquals(userResponse.getContent(), "Hello, Jemmy");
	}

}
