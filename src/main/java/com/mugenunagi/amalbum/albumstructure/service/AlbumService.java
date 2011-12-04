package com.mugenunagi.amalbum.albumstructure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.albumstructure.business.AlbumStructureBusiness;
import com.mugenunagi.amalbum.albumstructure.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.albumstructure.datamodel.dto.element.AlbumContents;

/**
 * MyPage関連のサービス
 * @author GTN
 *
 */
@Service
public class AlbumService {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	AlbumStructureBusiness albumStructureBusiness;

	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * アルバムブックリストを取得する
	 */
	public List<AlbumCategory> getAlbumBookList() {
		List<AlbumCategory> albumCategoryList = getAlbumCategoryListByParentID( 0 );
		return albumCategoryList;
	}

	/**
	 * アルバムセクションリストを取得する
	 */
	public List<AlbumCategory> getAlbumSectionList( int parentID ) {
		List<AlbumCategory> albumCategoryList = getAlbumCategoryListByParentID( parentID );
		return albumCategoryList;
	}

	/**
	 * アルバムのコンテンツリストを取得する
	 */
	public List<AlbumContents> getAlbumContentsList( int albumSectionID ) {
		List<AlbumContents> albumContentsList = getAlbumContentsListByParentID( albumSectionID );
		return albumContentsList;
	}

	
	//=========================================================================
	// Privateメソッド
	//=========================================================================
	/**
	 * 指定したアルバムグループIDを親に持つアルバムグループを返す。<BR>
	 * parentID=0がルート
	 * @param parentID 親のアルバムグループID
	 * @return アルバムグループを格納したリスト
	 */
	private List<AlbumCategory> getAlbumCategoryListByParentID( int parentID ){
		// -----< アルバムカテゴリーを取得して返す >-----
		//
		List<AlbumCategory> albumCategoryList = albumStructureBusiness.getAlbumCategoryListByParentID( parentID );
		
		// -----< 結果を返す >-----
		//
		return albumCategoryList;
	}
	

	/**
	 * 指定したアルバムセクションIDを親に持つアルバムのコンテンツを返す。<BR>
	 * 
	 * @param parentID アルバムセクションのID
	 * @return アルバムのコンテンツを格納したリスト
	 */
	private List<AlbumContents> getAlbumContentsListByParentID( int parentID ){
		// -----< アルバムコンテンツを取得して返す >-----
		//
		List<AlbumContents> albumContentsList = albumStructureBusiness.getAlbumContentsListByParentID( parentID );
		
		// -----< 結果を返す >-----
		//
		return albumContentsList;
	}
}
