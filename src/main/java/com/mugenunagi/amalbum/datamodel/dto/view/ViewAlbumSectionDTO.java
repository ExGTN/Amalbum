package com.mugenunagi.amalbum.datamodel.dto.view;

import com.mugenunagi.amalbum.datamodel.dto.parts.AlbumCategoryListPartsDTO;

/**
 * アルバムセクションの一覧表示のDTO
 * @author GTN
 *
 */
public class ViewAlbumSectionDTO {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * AlbumCategory の一覧のパーツのDTO
	 */
	private AlbumCategoryListPartsDTO albumCategoryListPartsDTO;


	//=========================================================================
	// アクセサ
	//=========================================================================
	/**
	 * @return the albumCategoryListPartsDTO
	 */
	public AlbumCategoryListPartsDTO getAlbumCategoryListPartsDTO() {
		return albumCategoryListPartsDTO;
	}

	/**
	 * @param albumCategoryListPartsDTO the albumCategoryListPartsDTO to set
	 */
	public void setAlbumCategoryListPartsDTO(
			AlbumCategoryListPartsDTO albumCategoryListPartsDTO) {
		this.albumCategoryListPartsDTO = albumCategoryListPartsDTO;
	}

}
