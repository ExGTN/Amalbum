package com.mugenunagi.amalbum.datastructure.datamodel.dao;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsGroupEntity;

/**
 * ContentsGroupを取り扱うマッパー
 * @author GTN
 *
 */
public interface ContentsGroupMapper {
	/**
	 * 指定した親IDを持つContentsGroupを取得する
	 * @param contentsGroupEntity
	 * @return
	 */
    List<ContentsGroupEntity> selectContentsGroupByParentID( ContentsGroupEntity contentsGroupEntity );
}