/**
 * AlbumRoleEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class AlbumRoleEntity {
	/**
	 * アルバムID
	 */
	private Integer albumID;

	/**
	 * 権限ID
	 */
	private Integer roleID;

	/**
	 * アルバムIDを取得する
	 * @return アルバムID
	 */
	public Integer getAlbumID() {
		return albumID;
	}

	/**
	 * アルバムIDを設定する
	 * @param albumID アルバムID
	 */
	public void setAlbumID(Integer albumID) {
		this.albumID = albumID;
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
