/**
 * MaterialEntity
 */

package com.mugenunagi.amalbum.datastructure.datamodel.entity;

import java.util.Date;

public class MaterialEntity {
	/**
	 * マテリアルID
	 */
	private Integer materialID;

	/**
	 * コンテンツID
	 */
	private Integer contentsID;

	/**
	 * タイプ
	 */
	private Integer materialType;

	/**
	 * ファイルパス
	 */
	private String path;

	/**
	 * ステータス
	 */
	private Integer status;

	/**
	 * 説明
	 */
	private String brief;

	/**
	 * 詳細説明
	 */
	private String description;

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
	 * タイプを取得する
	 * @return タイプ
	 */
	public Integer getMaterialType() {
		return materialType;
	}

	/**
	 * タイプを設定する
	 * @param materialType タイプ
	 */
	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	/**
	 * ファイルパスを取得する
	 * @return ファイルパス
	 */
	public String getPath() {
		return path;
	}

	/**
	 * ファイルパスを設定する
	 * @param path ファイルパス
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * ステータスを取得する
	 * @return ステータス
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * ステータスを設定する
	 * @param status ステータス
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
