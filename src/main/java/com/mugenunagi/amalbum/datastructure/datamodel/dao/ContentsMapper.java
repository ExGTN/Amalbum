package com.mugenunagi.amalbum.datastructure.datamodel.dao;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsEntity;

/**
 * Contentsを取り扱うマッパー
 * @author GTN
 *
 */
public interface ContentsMapper {
	/**
	 * 指定した親ContentsGroupIDを持つContentsを取得する
	 * @param contentsEntity
	 * @return
	 */
    List<ContentsEntity> selectContentsByContentsGroupID( ContentsEntity contentsEntity );
   
    
    /**
     * 指定したコンテンツIDを持つコンテンツを検索する
     * @param contentsEntity
     * @return
     */
    ContentsEntity getContentsByContentsID( ContentsEntity contentsEntity );
}
