package com.mugenunagi.amalbum.datastructure.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.datastructure.datamodel.dao.ContentsGroupMapper;
import com.mugenunagi.amalbum.datastructure.datamodel.dao.MaterialMapper;
import com.mugenunagi.amalbum.datastructure.datamodel.dao.ContentsMapper;
import com.mugenunagi.amalbum.datastructure.datamodel.entity.MaterialEntity;
import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsGroupEntity;

/**
 * データ構造を取り扱うビジネスクラス
 * @author GTN
 *
 */
@Component
public class DataStructureBusiness {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	ContentsGroupMapper contentsGroupMapper;

	@Autowired
	ContentsMapper contentsMapper;

	@Autowired
	MaterialMapper materialMapper;
	
	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 親IDを指定して、コンテンツグループの一覧を検索して返す
	 * @param parentID
	 * @return
	 */
	public List<ContentsGroupEntity> getContentsGroupListByParentID( int parentID ){
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsGroupEntity contentsGroupEntity = new ContentsGroupEntity();
		contentsGroupEntity.setParentID(parentID);
		
		// 検索
		List<ContentsGroupEntity> contentsGroupEntityList = contentsGroupMapper.selectContentsGroupByParentID( contentsGroupEntity );
		
		// -----< 結果を返す >-----
		//
		return contentsGroupEntityList;
	}


	/**
	 * 親IDを指定して、コンテンツの一覧を検索して返す
	 * @param parentID
	 * @return
	 */
	public List<ContentsEntity> getContentsListByParentID( int parentID ){
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsGroupID(parentID);
		
		// 検索
		List<ContentsEntity> contentsEntityList = contentsMapper.selectContentsByContentsGroupID( contentsEntity );
		
		// -----< 結果を返す >-----
		//
		return contentsEntityList;
	}


	/**
	 * IDを指定して、コンテンツを検索して返す
	 * @param parentID
	 * @return
	 */
	public ContentsEntity getContentsByContentsID( int contentsID ){
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsID(contentsID);
		
		// 検索
		ContentsEntity result = contentsMapper.getContentsByContentsID( contentsEntity );
		
		// -----< 結果を返す >-----
		//
		return result;
	}


        /**
         * IDを指定して、マテリアルを検索して返す
         * @param parentID
         * @return
         */
        public MaterialEntity getMaterialByMaterialID( int materialID ){
                // -----< 検索する >-----
                //
                // 検索条件を作る
                MaterialEntity materialEntity = new MaterialEntity();
                materialEntity.setMaterialID(materialID);

                // 検索
                MaterialEntity result = materialMapper.getMaterialByMaterialID( materialEntity );

                // -----< 結果を返す >-----
                //
                return result;
        }


	/**
	 * コンテンツグループを作成する
	 * @param contentsGroupEntity
	 */
	public void createContentsGroup( ContentsGroupEntity contentsGroupEntity ) {
		// IDを発行する
		int contentsGroupID = contentsGroupMapper.getNextContentsGroupID();
		
		// インサートする
		contentsGroupEntity.setContentsGroupID(contentsGroupID);
		contentsGroupMapper.insertContentsGroup(contentsGroupEntity);
		
		// SeqNoを更新する
		int nextSeqNo = contentsGroupMapper.getNextSeqNo();
		contentsGroupEntity.setSeqNo(nextSeqNo);
		
		// 更新する
		contentsGroupMapper.updateContentsGroup(contentsGroupEntity);
	}
}
