package com.mugenunagi.amalbum.exception.handler;

import org.springframework.stereotype.Component;

/**
 * 一般的な例外処理
 * @author gtn
 *
 */
@Component
public class GeneralExceptionHandler extends AbstractExceptionHandler{

	/**
	 * 例外処理
	 */
	public void handle( Exception e ){
		/**
		 * エラーログを出力する
		 */
		outputExceptionLog( e );
	}
}
