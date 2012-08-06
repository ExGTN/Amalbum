package com.mugenunagi.amalbum.datastructure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mugenunagi.amalbum.datastructure.dao.MaterialMapper;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class ImageControllerTest {
	@Autowired
	private ImageController imageController;
	
	@Autowired MaterialMapper materialMapper; 

	@Test
	public void testRestImage() {
		System.out.println(imageController.toString());
		int materialID =21;
		MaterialEntity materialEntity = materialMapper.getMaterialByMaterialID(materialID);
		System.out.println( materialEntity.getPath() );
		fail("Not yet implemented");
	}

	@Test
	public void testRestPhoto() {
		fail("Not yet implemented");
	}

}
