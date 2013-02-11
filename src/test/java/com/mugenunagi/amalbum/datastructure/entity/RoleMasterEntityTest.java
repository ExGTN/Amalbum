package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoleMasterEntityTest {

	@Test
	public void testFields() {
		RoleMasterEntity entity = new RoleMasterEntity();
		
		Integer roleID		= 123;					// 権限ID
		String roleName		= "SampleRoleName";		// 権限名
		String description	= "SampleDescription";	// 権限説明
		Integer value		= 321;					// 権限評価値
		
		// set
		entity.setRoleID(roleID.intValue());
		entity.setRoleName(new String(roleName));
		entity.setDescription(new String(description));
		entity.setValue(value.intValue());
		
		// get
		assertEquals( "RoleMasterEntityTest", roleID, entity.getRoleID() );
		assertEquals( "RoleMasterEntityTest", roleName, entity.getRoleName());
		assertEquals( "RoleMasterEntityTest", description, entity.getDescription());
		assertEquals( "ROleMasterEntityTest", value, entity.getValue());
	}

}
