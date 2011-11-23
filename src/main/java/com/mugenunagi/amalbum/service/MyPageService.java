package com.mugenunagi.amalbum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.business.album.AlbumStructureBusiness;
import com.mugenunagi.amalbum.datamodel.dto.element.AlbumCategory;

/**
 * MyPage関連のサービス
 * @author GTN
 *
 */
@Service
public class MyPageService {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	AlbumStructureBusiness albumStructureBusiness;

	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 指定したアルバムグループIDを親に持つアルバムグループを返す。<BR>
	 * parentID=0がルート
	 * @param parentID 親のアルバムグループID
	 * @return アルバムグループを格納したリスト
	 */
	public List<AlbumCategory> getAlbumCategoryListByParentID( int parentID ){
		// -----< アルバムカテゴリーを取得して返す >-----
		//
		List<AlbumCategory> albumCategoryList = albumStructureBusiness.getAlbumCategoryListByParentID( parentID );
		
		// -----< 結果を返す >-----
		//
		return albumCategoryList;
	}
}
