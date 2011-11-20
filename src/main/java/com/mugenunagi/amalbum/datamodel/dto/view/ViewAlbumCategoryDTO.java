package com.mugenunagi.amalbum.datamodel.dto.view;

import java.util.List;

import com.mugenunagi.amalbum.datamodel.dto.album.AlbumCategory;

/**
 * アルバムカテゴリーの一覧表示のDTO
 * @author GTN
 *
 */
public class ViewAlbumCategoryDTO {
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
