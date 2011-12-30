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
    public List<ContentsGroupEntity> selectContentsGroupByParentID( ContentsGroupEntity contentsGroupEntity );

    /**
     * 指定したIDを持つContentsGroupを取得する
     * @param contentsGroupEntity
     * @return
     */
    public ContentsGroupEntity selectContentsGroupByContentsGroupID( ContentsGroupEntity contentsGroupEntity );

    /**
     * ContentsGroupIDを発番する
     */
    public int getNextContentsGroupID();
	
	/**
	 * ContentsIDを発番する
	 */
    public int getNextContentsID();
	
	/**
	 * MaterialIDを発番する
	 */
    public int getNextMaterialID();

	/**
	 * ContentsGroupを作成する
	 */
    public void insertContentsGroup( ContentsGroupEntity contentsGroupEntity );

    /**
     * 次のSeqNoを発番する
     * @return
     */
    public int getNextSeqNo();

	/**
	 * ContentsGroup
	 */
    public void updateContentsGroup( ContentsGroupEntity contentsGroupEntity );
	
	/**
	 * ContentsGroupを論理削除する
	 */
    public void logicalDeleteContentsGroup( ContentsGroupEntity contentsGroupEntity );
	
	/**
	 * ContentsGroupを物理削除する
	 */
    public void deleteContentsGroup( ContentsGroupEntity contentsGroupEntity );
}
