package com.mugenunagi.amalbum.albumstructure.dto;

import static org.junit.Assert.*;

import org.junit.Test;

public class PhotoDTOTest {

	@Test
	public void testFields() {
		PhotoDTO photoDTO = new PhotoDTO();
		
		int contentsID		= 123;
		int materialID		= 321;
		String description	= "TestDescription";
		
		// set
		photoDTO.setContentsID(contentsID);
		photoDTO.setMaterialID(materialID);
		photoDTO.setDescription( new String( description) );
		
		// get
		assertEquals( "PhotoDTOTest", 123, photoDTO.getContentsID() );
		assertEquals( "PhotoDTOTest", 321, photoDTO.getMaterialID() );
		assertEquals( "PhotoDTOTest", description, photoDTO.getDescription() );
	}

}
