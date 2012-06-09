package com.mugenunagi.amalbum.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 例外処理を引き受けるクラス
 * @author gtn
 *
 */
@Component
public class AmalbumExceptionManager {
	/**
	 * 例外ハンドラーのセレクター
	 */
	@Autowired
	ExceptionHandlerSelector exceptionHandlerSelector;

	/**
	 * 例外のハンドリングを行う
	 * @param e
	 */
	public void handle( Exception e ){
		AbstractExceptionHandler handler = exceptionHandlerSelector.selectHandler( e );
		handler.handle( e );
	}
}
