/**
 * AlbumEntity
 */

package com.mugenunagi.amalbum.entity;

import java.util.Date;

public class AlbumEntity {
	/**
	 * アルバムID
	 */
	private Integer albumID;

	/**
	 * アルバムグループID
	 */
	private Integer albumGroupID;

	/**
	 * アルバム名
	 */
	private String name;

	/**
	 * ディレクトリ
	 */
	private String dir;

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
	 * アルバム名を取得する
	 * @return アルバム名
	 */
	public String getName() {
		return name;
	}

	/**
	 * アルバム名を設定する
	 * @param name アルバム名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ディレクトリを取得する
	 * @return ディレクトリ
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * ディレクトリを設定する
	 * @param dir ディレクトリ
	 */
	public void setDir(String dir) {
		this.dir = dir;
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
