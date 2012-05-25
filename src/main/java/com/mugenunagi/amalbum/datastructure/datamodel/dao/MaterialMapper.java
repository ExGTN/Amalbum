package com.mugenunagi.amalbum.datastructure.datamodel.dao;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.datamodel.entity.MaterialEntity;

/**
 * Materialを取り扱うマッパー
 * @author GTN
 *
 */
public interface MaterialMapper {
    /**
     * 指定した素材IDを持つ素材を検索する
     * @param contentsEntity
     * @return
     */
    MaterialEntity getMaterialByMaterialID( MaterialEntity materialEntity );
}
