package com.mugenunagi.amalbum.albumstructure.datamodel.dto.element;

import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsEntity;

/**
 * アルバムのコンテンツのDTO
 * @author GTN
 *
 */
public class AlbumContents {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * コンテンツのエンティティ
	 */
	ContentsEntity contentsEntity;


	//=========================================================================
	// 操作
	//=========================================================================
	/**
	 * @return the contentsEntity
	 */
	public ContentsEntity getContentsEntity() {
		return contentsEntity;
	}

	/**
	 * @param contentsEntity the contentsEntity to set
	 */
	public void setContentsEntity(ContentsEntity contentsEntity) {
		this.contentsEntity = contentsEntity;
	}
	
}
