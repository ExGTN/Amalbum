package com.mugenunagi.amalbum.datastructure.dao;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;

/**
 * Materialを取り扱うマッパー
 * @author GTN
 *
 */
public interface MaterialMapper {
	/**
	 * 親のコンテンツIDを指定して、
	 * @param conteentsID
	 * @return
	 */
	List<MaterialEntity> selectMaterialByContentsID( Integer conteentsID );

	/**
     * 指定した素材IDを持つ素材を検索する
     * @param contentsEntity
     * @return
     */
    MaterialEntity getMaterialByMaterialID( Integer matrialID );
}
