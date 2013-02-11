package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserRoleEntityTest {

	@Test
	public void testFields() {
		UserRoleEntity entity = new UserRoleEntity();

		Integer roleID	= 123;	// 権限ID
		String userID	= "SampleUserID";	// ユーザID
		Integer value	= 321;	// 権限値
		
		// set
		entity.setRoleID(roleID.intValue());
		entity.setUserID(new String(userID));
		entity.setValue(value.intValue());
		
		// get
		assertEquals( "UserRoleEntityTest", roleID, entity.getRoleID());
		assertEquals( "UserRoleEntityTest", userID, entity.getUserID());
		assertEquals( "UserRoleEntityTest", value, entity.getValue());
	}

}
