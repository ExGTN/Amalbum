/**
 * RoleMasterEntity
 */

package com.mugenunagi.amalbum.datastructure.datamodel.entity;

import java.util.Date;

public class RoleMasterEntity {
	/**
	 * 権限ID
	 */
	private Integer roleID;

	/**
	 * 権限名
	 */
	private String roleName;

	/**
	 * 権限説明
	 */
	private String description;

	/**
	 * 権限評価値
	 */
	private Integer value;

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
	 * 権限名を取得する
	 * @return 権限名
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 権限名を設定する
	 * @param roleName 権限名
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 権限説明を取得する
	 * @return 権限説明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 権限説明を設定する
	 * @param description 権限説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 権限評価値を取得する
	 * @return 権限評価値
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 権限評価値を設定する
	 * @param value 権限評価値
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

}
