/**
 * BackupRuleEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class BackupRuleEntity {
	/**
	 * バックアップルールID
	 */
	private Integer backupRuleID;

	/**
	 * バックアップタイプ
	 */
	private Integer backupType;

	/**
	 * 年
	 */
	private Integer year;

	/**
	 * 月
	 */
	private Integer month;

	/**
	 * 日
	 */
	private Integer day;

	/**
	 * 時
	 */
	private Integer hour;

	/**
	 * 分
	 */
	private Integer minute;

	/**
	 * バックアップルールIDを取得する
	 * @return バックアップルールID
	 */
	public Integer getBackupRuleID() {
		return backupRuleID;
	}

	/**
	 * バックアップルールIDを設定する
	 * @param backupRuleID バックアップルールID
	 */
	public void setBackupRuleID(Integer backupRuleID) {
		this.backupRuleID = backupRuleID;
	}

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
	 * 年を取得する
	 * @return 年
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * 年を設定する
	 * @param year 年
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * 月を取得する
	 * @return 月
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * 月を設定する
	 * @param month 月
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * 日を取得する
	 * @return 日
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * 日を設定する
	 * @param day 日
	 */
	public void setDay(Integer day) {
		this.day = day;
	}

	/**
	 * 時を取得する
	 * @return 時
	 */
	public Integer getHour() {
		return hour;
	}

	/**
	 * 時を設定する
	 * @param hour 時
	 */
	public void setHour(Integer hour) {
		this.hour = hour;
	}

	/**
	 * 分を取得する
	 * @return 分
	 */
	public Integer getMinute() {
		return minute;
	}

	/**
	 * 分を設定する
	 * @param minute 分
	 */
	public void setMinute(Integer minute) {
		this.minute = minute;
	}

}
