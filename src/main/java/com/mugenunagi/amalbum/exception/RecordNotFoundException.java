package com.mugenunagi.amalbum.exception;

/**
 * データソースから必要な情報を取得できなかった際の例外処理
 * @author gtn
 *
 */
public class RecordNotFoundException extends AmalbumException {

	public RecordNotFoundException(String message) {
		super( message );
	}

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 8519539625647599245L;

}
