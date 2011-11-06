/**
 * PhotoEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class PhotoEntity {
	/**
	 * 写真ID
	 */
	private Integer photoID;

	/**
	 * アルバムID
	 */
	private Integer albumID;

	/**
	 * ファイル名
	 */
	private String filename;

	/**
	 * ステータス
	 */
	private Integer status;

	/**
	 * 説明
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
	 * ファイル名を取得する
	 * @return ファイル名
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * ファイル名を設定する
	 * @param filename ファイル名
	 */
	public void setFilename(String filename) {
		this.filename = filename;
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
	public String getDescription() {
		return description;
	}

	/**
	 * 説明を設定する
	 * @param description 説明
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
