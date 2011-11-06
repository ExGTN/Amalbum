/**
 * PhotoRoleEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class PhotoRoleEntity {
	/**
	 * 写真ID
	 */
	private Integer photoID;

	/**
	 * 権限ID
	 */
	private Integer roleID;

	/**
	 * 写真IDを取得する
	 * @return 写真ID
	 */
	public Integer getPhotoID() {
		return photoID;
	}

	/**
	 * 写真IDを設定する
	 * @param photoID 写真ID
	 */
	public void setPhotoID(Integer photoID) {
		this.photoID = photoID;
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
