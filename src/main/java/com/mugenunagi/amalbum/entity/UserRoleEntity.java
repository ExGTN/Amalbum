/**
 * UserRoleEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class UserRoleEntity {
	/**
	 * 権限ID
	 */
	private Integer roleID;

	/**
	 * ユーザID
	 */
	private String userID;

	/**
	 * 権限値
	 */
	private String value;

	/**
	 * 権限IDを取得する
	 * @return 権限ID
	 */
	public Integer getRoleID() {
		return roleID;
	}

	/**
	 * 権限IDを設定する
	 * @param roleID 権限ID
	 */
	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	/**
	 * ユーザIDを取得する
	 * @return ユーザID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * ユーザIDを設定する
	 * @param userID ユーザID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 権限値を取得する
	 * @return 権限値
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 権限値を設定する
	 * @param value 権限値
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
