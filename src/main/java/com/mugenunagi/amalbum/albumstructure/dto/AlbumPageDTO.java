package com.mugenunagi.amalbum.albumstructure.dto;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;

/**
 * アルバム内の１ページを取り扱うDTO
 * @author gtn
 *
 */
public class AlbumPageDTO {
	private ContentsGroupEntity albumPageInfo;
	private List<PhotoDTO> photoDTOList;

	/**
	 * @return the contentsGroupEntity
	 */
	public ContentsGroupEntity getAlbumPageInfo() {
		return albumPageInfo;
	}
	/**
	 * @param contentsGroupEntity the contentsGroupEntity to set
	 */
	public void setAlbumPageInfo(ContentsGroupEntity contentsGroupEntity) {
		this.albumPageInfo = contentsGroupEntity;
	}
	/**
	 * @return the photoDTOList
	 */
	public List<PhotoDTO> getPhotoDTOList() {
		return photoDTOList;
	}
	/**
	 * @param photoDTOList the photoDTOList to set
	 */
	public void setPhotoDTOList(List<PhotoDTO> photoDTOList) {
		this.photoDTOList = photoDTOList;
	}
}
