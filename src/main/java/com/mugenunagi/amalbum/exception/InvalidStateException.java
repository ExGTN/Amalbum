package com.mugenunagi.amalbum.exception;

/**
 * 想定外の状況にぶつかったときの例外
 * @author gtn
 *
 */
public class InvalidStateException extends AmalbumException {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -5029378372219741643L;

	public InvalidStateException(String message) {
		super( message );
	}
}
