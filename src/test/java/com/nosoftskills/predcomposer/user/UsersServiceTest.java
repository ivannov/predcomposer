package com.nosoftskills.predcomposer.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.nosoftskills.predcomposer.common.BaseServiceTest;
import com.nosoftskills.predcomposer.model.User;

public class UsersServiceTest extends BaseServiceTest {

	private UsersService usersService;
	
	@Override
	protected void setupClassUnderTest() {
		usersService = new UsersService(entityManager);
	}

	@Test
	public void test() {
		List<User> users = usersService.getAllUsers();

		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals("ivan", users.get(0).getUserName());
		assertEquals("koko", users.get(1).getUserName());
	}

}
