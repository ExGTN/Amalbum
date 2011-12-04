/**
 * MaterialRoleEntity
 */

package com.mugenunagi.amalbum.datastructure.datamodel.entity;

import java.util.Date;

public class MaterialRoleEntity {
	/**
	 * マテリアルID
	 */
	private Integer materialID;

	/**
	 * 権限ID
	 */
	private Integer roleID;

	/**
	 * マテリアルIDを取得する
	 * @return マテリアルID
	 */
	public Integer getMaterialID() {
		return materialID;
	}

	/**
	 * マテリアルIDを設定する
	 * @param materialID マテリアルID
	 */
	public void setMaterialID(Integer materialID) {
		this.materialID = materialID;
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
