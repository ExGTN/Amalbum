/**
 * AlbumGroupEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class AlbumGroupEntity {
	/**
	 * アルバムグループID
	 */
	private Integer albumGroupID;

	/**
	 * アルバムグループ名
	 */
	private String name;

	/**
	 * 親アルバムグループID
	 */
	private Integer parentID;

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
	 * アルバムグループIDを取得する
	 * @return アルバムグループID
	 */
	public Integer getAlbumGroupID() {
		return albumGroupID;
	}

	/**
	 * アルバムグループIDを設定する
	 * @param albumGroupID アルバムグループID
	 */
	public void setAlbumGroupID(Integer albumGroupID) {
		this.albumGroupID = albumGroupID;
	}

	/**
	 * アルバムグループ名を取得する
	 * @return アルバムグループ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * アルバムグループ名を設定する
	 * @param name アルバムグループ名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 親アルバムグループIDを取得する
	 * @return 親アルバムグループID
	 */
	public Integer getParentID() {
		return parentID;
	}

	/**
	 * 親アルバムグループIDを設定する
	 * @param parentID 親アルバムグループID
	 */
	public void setParentID(Integer parentID) {
		this.parentID = parentID;
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
