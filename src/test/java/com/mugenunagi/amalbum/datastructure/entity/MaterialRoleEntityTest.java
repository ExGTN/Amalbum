package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaterialRoleEntityTest {

	@Test
	public void testFields() {
		MaterialRoleEntity entity = new MaterialRoleEntity();

		Integer materialID	= 321;	// マテリアルID
		Integer roleID		= 123;	// 権限ID
		
		// set
		entity.setMaterialID(materialID.intValue());
		entity.setRoleID(roleID.intValue());
		
		// get
		assertEquals( "MaterialRoleEntityTest", materialID, entity.getMaterialID() );
		assertEquals( "MaterialRoleEntityTest", roleID, entity.getRoleID() );
	}
}
