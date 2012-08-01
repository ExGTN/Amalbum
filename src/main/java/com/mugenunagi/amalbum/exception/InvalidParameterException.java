package com.mugenunagi.amalbum.exception;

/**
 * 指定されたパラメータが想定外だった場合の例外
 * @author gtn
 *
 */
public class InvalidParameterException extends AmalbumException {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -9035505178984952765L;

	public InvalidParameterException(String message) {
		super( message );
	}
}
