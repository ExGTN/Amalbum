package com.mugenunagi.amalbum.datastructure.entity;

import java.util.Date;

/**
 * ContentsEntity
 * @author gtn
 *
 */
public class ContentsEntity {
	/**
	 * コンテンツID
	 */
	private Integer contentsID;

	/**
	 * コンテンツグループID
	 */
	private Integer contentsGroupID;

	/**
	 * コンテンツ名
	 */
	private String name;

	/**
	 * 説明
	 */
	private String brief;

	/**
	 * 詳細説明
	 */
	private String description;

	/**
	 * ベースディレクトリ
	 */
	private String baseDir;

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
	 * コンテンツグループIDを取得する
	 * @return コンテンツグループID
	 */
	public Integer getContentsGroupID() {
		return contentsGroupID;
	}

	/**
	 * コンテンツグループIDを設定する
	 * @param contentsGroupID コンテンツグループID
	 */
	public void setContentsGroupID(Integer contentsGroupID) {
		this.contentsGroupID = contentsGroupID;
	}

	/**
	 * コンテンツ名を取得する
	 * @return コンテンツ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * コンテンツ名を設定する
	 * @param name コンテンツ名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 説明を取得する
	 * @return 説明
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * 説明を設定する
	 * @param brief 説明
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * 詳細説明を取得する
	 * @return 詳細説明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 詳細説明を設定する
	 * @param description 詳細説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * ベースディレクトリを取得する
	 * @return ベースディレクトリ
	 */
	public String getBaseDir() {
		return baseDir;
	}

	/**
	 * ベースディレクトリを設定する
	 * @param baseDir ベースディレクトリ
	 */
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
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
