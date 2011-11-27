package com.mugenunagi.amalbum.datamodel.dto.view;

import com.mugenunagi.amalbum.datamodel.dto.parts.AlbumContentsListPartsDTO;

/**
 * アルバムセクションの一覧表示のDTO
 * @author GTN
 *
 */
public class ViewAlbumDTO {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * AlbumContents の一覧のパーツのDTO
	 */
	private AlbumContentsListPartsDTO albumContentsListPartsDTO;

	
	//=========================================================================
	// アクセサ
	//=========================================================================
	/**
	 * @return the albumContentsListPartsDTO
	 */
	public AlbumContentsListPartsDTO getAlbumContentsListPartsDTO() {
		return albumContentsListPartsDTO;
	}

	/**
	 * @param albumContentsListPartsDTO the albumContentsListPartsDTO to set
	 */
	public void setAlbumContentsListPartsDTO(
			AlbumContentsListPartsDTO albumContentsListPartsDTO) {
		this.albumContentsListPartsDTO = albumContentsListPartsDTO;
	}

}
