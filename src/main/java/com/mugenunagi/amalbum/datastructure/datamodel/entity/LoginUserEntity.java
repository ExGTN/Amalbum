/**
 * LoginUserEntity
 */

package com.mugenunagi.amalbum.datastructure.datamodel.entity;

import java.util.Date;

public class LoginUserEntity {
	/**
	 * ユーザID
	 */
	private String userID;

	/**
	 * 暗号化パスワード
	 */
	private String encPassword;

	/**
	 * ユーザ名
	 */
	private String userName;

	/**
	 * メールアドレス
	 */
	private String mailAddress;

	/**
	 * 表示順
	 */
	private Integer seqNo;

	/**
	 * 削除日付
	 */
	private Date deleteDate;

	/**
	 * 更新日付
	 */
	private Date updateDate;

	/**
	 * 作成日付
	 */
	private Date createDate;

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
	 * 暗号化パスワードを取得する
	 * @return 暗号化パスワード
	 */
	public String getEncPassword() {
		return encPassword;
	}

	/**
	 * 暗号化パスワードを設定する
	 * @param encPassword 暗号化パスワード
	 */
	public void setEncPassword(String encPassword) {
		this.encPassword = encPassword;
	}

	/**
	 * ユーザ名を取得する
	 * @return ユーザ名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ユーザ名を設定する
	 * @param userName ユーザ名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * メールアドレスを取得する
	 * @return メールアドレス
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * メールアドレスを設定する
	 * @param mailAddress メールアドレス
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * 表示順を取得する
	 * @return 表示順
	 */
	public Integer getSeqNo() {
		return seqNo;
	}

	/**
	 * 表示順を設定する
	 * @param seqNo 表示順
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 削除日付を取得する
	 * @return 削除日付
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * 削除日付を設定する
	 * @param deleteDate 削除日付
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * 更新日付を取得する
	 * @return 更新日付
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 更新日付を設定する
	 * @param updateDate 更新日付
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 作成日付を取得する
	 * @return 作成日付
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 作成日付を設定する
	 * @param createDate 作成日付
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
