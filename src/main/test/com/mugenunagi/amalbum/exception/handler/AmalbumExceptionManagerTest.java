package com.mugenunagi.amalbum.exception.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mugenunagi.amalbum.exception.AmalbumException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class AmalbumExceptionManagerTest {
	@Autowired
	AmalbumExceptionManager exceptionManager;

	@Test
	public void testHandle() {
		AmalbumException exception = new AmalbumException( "ForTest" );
		
		exceptionManager.handle( exception );
		
		// 特にassertすることなし・・・。
	}

}
