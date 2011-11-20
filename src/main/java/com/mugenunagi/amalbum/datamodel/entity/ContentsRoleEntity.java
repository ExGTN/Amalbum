/**
 * ContentsRoleEntity
 */

package com.mugenunagi.amalbum.datamodel.entity;

import java.util.Date;

public class ContentsRoleEntity {
	/**
	 * コンテンツID
	 */
	private Integer contentsID;

	/**
	 * 権限ID
	 */
	private Integer roleID;

	/**
	 * コンテンツIDを取得する
	 * @return コンテンツID
	 */
	public Integer getContentsID() {
		return contentsID;
	}

	/**
	 * コンテンツIDを設定する
	 * @param contentsID コンテンツID
	 */
	public void setContentsID(Integer contentsID) {
		this.contentsID = contentsID;
	}

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

}
