package com.mugenunagi.amalbum.albumstructure.datamodel.dto.element;

import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsGroupEntity;

/**
 * アルバムカテゴリーのDTO
 * @author GTN
 *
 */
public class AlbumCategory {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * コンテンツグループのエンティティ
	 */
	ContentsGroupEntity contentsGroupEntity;


	//=========================================================================
	// 操作
	//=========================================================================
	/**
	 * @return the contentsGroupEntity
	 */
	public ContentsGroupEntity getContentsGroupEntity() {
		return contentsGroupEntity;
	}

	/**
	 * @param contentsGroupEntity the contentsGroupEntity to set
	 */
	public void setContentsGroupEntity(ContentsGroupEntity contentsGroupEntity) {
		this.contentsGroupEntity = contentsGroupEntity;
	}
	
}
