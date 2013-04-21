package com.mugenunagi.amalbum.datastructure.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mugenunagi.amalbum.datastructure.dao.MaterialMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class ImageControllerTest {
	@Autowired
	private ImageRestController imageRestController;
	
	@Autowired MaterialMapper materialMapper; 

	@Test
	public void testRestImage() {
//		System.out.println(imageRestController.toString());
//		int materialID =21;
//		MaterialEntity materialEntity = materialMapper.getMaterialByMaterialID(materialID);
//		System.out.println( materialEntity.getPath() );
//		fail("Not yet implemented");
	}

	@Test
	public void testRestPhoto() {
//		fail("Not yet implemented");
	}

}
