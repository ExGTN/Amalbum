package com.mugenunagi.amalbum.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.datamodel.dao.ContentsGroupMapper;
import com.mugenunagi.amalbum.datamodel.dao.ContentsMapper;
import com.mugenunagi.amalbum.datamodel.entity.ContentsEntity;
import com.mugenunagi.amalbum.datamodel.entity.ContentsGroupEntity;

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
}
