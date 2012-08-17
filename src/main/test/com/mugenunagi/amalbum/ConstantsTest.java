package com.mugenunagi.amalbum;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class ConstantsTest {
	@Autowired
	ApplicationContextWrapper applicationContextWrapper;
	
	/**
	 * ResourceBundle名の取得
	 */
	@Test
	public void testGetApplicationPropertiesName() {
		Constants constants = applicationContextWrapper.getConstants();
		
		String bundleName = constants.getApplicationPropertiesName();
		assertEquals( "BundleName", "application-test", bundleName );
	}

}
