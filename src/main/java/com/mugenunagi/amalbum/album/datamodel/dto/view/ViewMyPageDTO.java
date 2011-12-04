package com.mugenunagi.amalbum.album.datamodel.dto.view;

import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumCategoryListPartsDTO;

/**
 * アルバムカテゴリーの一覧表示のDTO
 * @author GTN
 *
 */
public class ViewMyPageDTO {
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
