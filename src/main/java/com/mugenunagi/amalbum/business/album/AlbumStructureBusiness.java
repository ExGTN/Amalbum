package com.mugenunagi.amalbum.business.album;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.business.DataStructureBusiness;
import com.mugenunagi.amalbum.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.datamodel.entity.ContentsGroupEntity;

/**
 * アルバムのデータ構造を取り扱うビジネスクラス
 * @author GTN
 *
 */
@Component
public class AlbumStructureBusiness {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	DataStructureBusiness dataStructureBusiness;

	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 親IDを指定して、AlbumCategoryの一覧を作って返す
	 */
	public List<AlbumCategory> getAlbumCategoryListByParentID( int parentID ){
		// -----< DataStructureから情報を取り出す >-----
		//
		List<ContentsGroupEntity> contentsGroupEntityList = dataStructureBusiness.getContentsGroupListByParentID( parentID );
		
		// -----< 取り出した情報をDTOに詰め替えて返す >-----
		//
		List<AlbumCategory> albumCategoryList = new ArrayList<AlbumCategory>();
		for( ContentsGroupEntity contentsGroupEntity : contentsGroupEntityList ){
			// AlbumCategoryに詰める
			AlbumCategory albumCategory = new AlbumCategory();
			albumCategory.setContentsGroupEntity(contentsGroupEntity);
			
			// リストに詰める
			albumCategoryList.add( albumCategory );
		}
		
		// -----< 結果を返す >-----
		//
		return albumCategoryList;
	}
}
