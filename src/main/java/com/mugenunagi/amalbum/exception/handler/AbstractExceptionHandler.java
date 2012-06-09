package com.mugenunagi.amalbum.exception.handler;

import org.apache.log4j.Logger;

/**
 * 例外をハンドリングするハンドラーのベースクラス
 * @author gtn
 *
 */
public abstract class AbstractExceptionHandler {
	/**
	 * 例外を処理する
	 * @param e
	 */
	abstract public void handle( Exception e );

	/**
	 * ロガー
	 */
	private Logger logger = Logger.getLogger( AbstractExceptionHandler.class );
	
	/**
	 * 例外の内容をログに出力する
	 * @param e
	 */
	protected void outputExceptionLog( Exception e ){
		// ログにメッセージとスタックとレースを出力する
		logger.error(e,e);
		
		// Causeがある場合は、そちらも出力
		if( e.getCause()!=null ){
			String message = "【Cause】\n" + e.getCause();
			logger.error( message );
			System.out.println( message );
		}
	}
}
