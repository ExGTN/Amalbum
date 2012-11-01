package com.mugenunagi.amalbum.datastructure.dao;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;

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
    List<ContentsEntity> selectContentsByContentsGroupID( Integer contentsGroupID );
   
    
    /**
     * 指定したコンテンツIDを持つコンテンツを検索する
     * @param contentsEntity
     * @return
     */
    ContentsEntity getContentsByContentsID( ContentsEntity contentsEntity );
    
    /**
     * コンテンツを追加します
     * @param contentsEntity
     */
    void insertContents( ContentsEntity  contentsEntity );
    
    /**
     * コンテンツを更新します
     * @param contentsEntity
     */
    void updateContents( ContentsEntity  contentsEntity );
    
    /**
     * コンテンツを削除します
     * @param contentsID
     */
    void deleteContents( Integer contentsID );
}
