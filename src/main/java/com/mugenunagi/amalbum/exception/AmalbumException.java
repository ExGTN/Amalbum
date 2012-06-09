package com.mugenunagi.amalbum.exception;

/**
 * Amalbumに関係した例外のベースクラス
 * @author gtn
 *
 */
public class AmalbumException extends Exception {
	public AmalbumException(String message) {
		super( message );
	}

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 6927670809232211568L;
}
