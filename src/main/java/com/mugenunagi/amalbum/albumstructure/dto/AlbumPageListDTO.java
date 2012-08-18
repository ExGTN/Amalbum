package com.mugenunagi.amalbum.albumstructure.dto;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;

/**
 * 特定のアルバムに含まれるアルバムページの一覧を取り扱うDTO
 * @author gtn
 *
 */
public class AlbumPageListDTO {
	private ContentsGroupEntity albumInfo;
	private List<ContentsGroupEntity> albumPageList;
	/**
	 * @return the albumInfo
	 */
	public ContentsGroupEntity getAlbumInfo() {
		return albumInfo;
	}
	/**
	 * @param albumInfo the albumInfo to set
	 */
	public void setAlbumInfo(ContentsGroupEntity albumInfo) {
		this.albumInfo = albumInfo;
	}
	/**
	 * @return the albumPageList
	 */
	public List<ContentsGroupEntity> getAlbumPageList() {
		return albumPageList;
	}
	/**
	 * @param albumPageList the albumPageList to set
	 */
	public void setAlbumPageList(List<ContentsGroupEntity> albumPageList) {
		this.albumPageList = albumPageList;
	}

}
