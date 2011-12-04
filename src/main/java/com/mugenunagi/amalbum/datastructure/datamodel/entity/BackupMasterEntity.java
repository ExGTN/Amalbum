/**
 * BackupMasterEntity
 */

package com.mugenunagi.amalbum.datastructure.datamodel.entity;

import java.util.Date;

public class BackupMasterEntity {
	/**
	 * バックアップタイプ
	 */
	private Integer backupType;

	/**
	 * バックアップ名
	 */
	private String name;

	/**
	 * バックアップタイプを取得する
	 * @return バックアップタイプ
	 */
	public Integer getBackupType() {
		return backupType;
	}

	/**
	 * バックアップタイプを設定する
	 * @param backupType バックアップタイプ
	 */
	public void setBackupType(Integer backupType) {
		this.backupType = backupType;
	}

	/**
	 * バックアップ名を取得する
	 * @return バックアップ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * バックアップ名を設定する
	 * @param name バックアップ名
	 */
	public void setName(String name) {
		this.name = name;
	}

}
