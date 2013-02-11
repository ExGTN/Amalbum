package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContentsRoleEntityTest {

	@Test
	public void testFields() {
		ContentsRoleEntity entity = new ContentsRoleEntity();
		
		Integer contentsID	= 1;	// コンテンツID
		Integer roleID		= 2;	// 権限ID
		
		// set
		entity.setContentsID(contentsID.intValue());
		entity.setRoleID(roleID.intValue());
		
		// get
		assertEquals( "ContentsRoleEntityTest", contentsID, entity.getContentsID() );
		assertEquals( "ContentsRoleEntityTest", roleID, entity.getRoleID());
	}

}
