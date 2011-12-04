package com.mugenunagi.amalbum.album.datamodel.dto.parts;

import java.util.List;

import com.mugenunagi.amalbum.album.datamodel.dto.element.AlbumCategory;

/**
 * アルバムカテゴリーの一覧表示パーツのDTO
 * @author GTN
 *
 */
public class AlbumCategoryListPartsDTO {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * AlbumCategory の一覧
	 */
	private List<AlbumCategory> albumCategoryList;
	
	//=========================================================================
	// アクセサ
	//=========================================================================
	/**
	 * @return the albumCategoryList
	 */
	public List<AlbumCategory> getAlbumCategoryList() {
		return albumCategoryList;
	}

	/**
	 * @param albumCategoryList the albumGroupEntityList to set
	 */
	public void setAlbumCategoryList(List<AlbumCategory> albumCategoryList) {
		this.albumCategoryList = albumCategoryList;
	}

}
