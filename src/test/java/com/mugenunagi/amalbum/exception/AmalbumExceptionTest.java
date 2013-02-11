package com.mugenunagi.amalbum.exception;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mugenunagi.amalbum.exception.handler.AmalbumExceptionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class AmalbumExceptionTest {
	@Autowired
	AmalbumExceptionManager exceptionManager;

	@Test
	public void testAmalbumException() {
		// AmalbumException
		try{
			throw new AmalbumException( "Test:AmalbumException" );
		} catch ( AmalbumException e ){
			assertEquals( "TestAmalbumException", "Test:AmalbumException", e.getMessage() );
			exceptionManager.handle(e);
		}
		
		// InvalidParameterException
		try{
			throw new InvalidParameterException( "Test:InvalidParameterException" );
		} catch ( InvalidParameterException e ){
			assertEquals( "TestAmalbumException", "Test:InvalidParameterException", e.getMessage() );
			exceptionManager.handle(e);
		}

		// InvalidStateException
		try{
			throw new InvalidStateException( "Test:InvalidStateException" );
		} catch (InvalidStateException e){
			assertEquals("TestAmalbumException", "Test:InvalidStateException", e.getMessage() );
			exceptionManager.handle(e);
		}

		// RecordNotFoundException
		try{
			throw new RecordNotFoundException( "Test:RecordNotFoundException" );
		} catch ( RecordNotFoundException e ){
			assertEquals( "TestAmalbumException", "Test:RecordNotFoundException", e.getMessage());
			exceptionManager.handle(e);
		}
	}

}
