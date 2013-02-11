package com.mugenunagi.amalbum.exception.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mugenunagi.amalbum.exception.AmalbumException;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class ExceptionHandlerSelectorTest {
	@Autowired
	private ExceptionHandlerSelector exceptionHandlerSelector;

	@Test
	public void testSelectHandler() {
		AbstractExceptionHandler handler = null;

		// テスト対象の例外の一覧を作る
		ArrayList<Exception> exceptionList		= new ArrayList<Exception>();
		ArrayList<String> handlerNameList	= new ArrayList<String>();

		exceptionList.add( new AmalbumException("AmalbumException") );
		handlerNameList.add( GeneralExceptionHandler.class.getName() );
		
		exceptionList.add( new InvalidParameterException("InvalidParameterException") );
		handlerNameList.add( GeneralExceptionHandler.class.getName() );
		
		exceptionList.add( new InvalidStateException("InvalidStateException") );
		handlerNameList.add( GeneralExceptionHandler.class.getName() );

		exceptionList.add( new RecordNotFoundException("RecordNotFoundException") );
		handlerNameList.add( GeneralExceptionHandler.class.getName() );
		
		{
			// 普通の例外とCauseのテスト
			Exception ex = new Exception("Exception");
			ex.initCause( new Exception("Cause") );
			exceptionList.add( ex );
			handlerNameList.add( GeneralExceptionHandler.class.getName() );
		}

		// Check.
		for( int i=0;i<exceptionList.size();i++ ){
			// 対応するハンドラーの想定を得る
			String handlerName = handlerNameList.get(i);
			
			// 例外オブジェクトを得る
			Exception exception = exceptionList.get(i);

			// 比較する
			handler = exceptionHandlerSelector.selectHandler( exception );
			System.out.println( "Class="+exception.getClass().getName()+", Expected:"+handlerName+", Actual:"+handler.getClass().getName());
			assertEquals( exception.getMessage(), handlerName, handler.getClass().getName() );
			
			// handleしてみる
			handler.handle(exception);
		}
	}

}
