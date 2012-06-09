package com.mugenunagi.amalbum.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.exception.RecordNotFoundException;

/**
 * 例外のタイプごとに、適切な例外ハンドラーを選択して返す
 * @author gtn
 *
 */
@Component
public class ExceptionHandlerSelector {
	/**
	 * 例外クラス名をキーに、ExceptionHandlerを値に持つMap
	 */
	private static Map<String, AbstractExceptionHandler> exceptionHandlerMap;
	
	/**
	 * RecordNotFoundExceptionのハンドラー
	 */
	@Autowired
	private GeneralExceptionHandler generalExceptionHandler;

	/**
	 * exceptionHandlerMapを構築する
	 */
	private void makeExceptionHandlerMap() {
		exceptionHandlerMap = new HashMap<String, AbstractExceptionHandler>();
		exceptionHandlerMap.put( RecordNotFoundException.class.toString(), generalExceptionHandler );
	}

	/**
	 * 例外の種類によって、ハンドラを選択して返す
	 * @param e
	 * @return
	 */
	public AbstractExceptionHandler selectHandler( Exception e){
		// 必要に応じてマップを構築する
		if( ExceptionHandlerSelector.exceptionHandlerMap==null ){
			makeExceptionHandlerMap();
		}

		// 選択
		AbstractExceptionHandler result = exceptionHandlerMap.get( e.getClass().toString() );
		if( result==null ){
			result = generalExceptionHandler;
		}
		
		// 結果を返す
		return result;
	}
}
