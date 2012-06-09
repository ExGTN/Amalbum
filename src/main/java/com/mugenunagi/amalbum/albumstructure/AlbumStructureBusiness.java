package com.mugenunagi.amalbum.albumstructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

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
//	/**
//	 * 親IDを指定して、AlbumCategoryの一覧を作って返す
//	 */
//	public List<AlbumCategory> getAlbumCategoryListByParentID( int parentID ){
//		// -----< DataStructureから情報を取り出す >-----
//		//
//		List<ContentsGroupEntity> contentsGroupEntityList = dataStructureBusiness.getContentsGroupListByParentID( parentID );
//		
//		// -----< 取り出した情報をDTOに詰め替えて返す >-----
//		//
//		List<AlbumCategory> albumCategoryList = new ArrayList<AlbumCategory>();
//		for( ContentsGroupEntity contentsGroupEntity : contentsGroupEntityList ){
//			// AlbumCategoryに詰める
//			AlbumCategory albumCategory = new AlbumCategory();
//			albumCategory.setContentsGroupEntity(contentsGroupEntity);
//			
//			// リストに詰める
//			albumCategoryList.add( albumCategory );
//		}
//		
//		// -----< 結果を返す >-----
//		//
//		return albumCategoryList;
//	}


	/**
	 * アルバムページIDを指定して、写真DTOの一覧を作って返す
	 * @throws RecordNotFoundException 
	 */
	public List<PhotoDTO> getPhotoList( int albumPageID ) throws RecordNotFoundException{
		// -----< DataStructureから情報を取り出す >-----
		//
		List<ContentsEntity> contentsEntityList = dataStructureBusiness.getContentsListByParentID( Integer.valueOf(albumPageID) );
		
		// -----< 取り出した情報をDTOに詰め替えて返す >-----
		//
		List<PhotoDTO> photoDTOList = new ArrayList<PhotoDTO>();
		for( ContentsEntity contentsEntity : contentsEntityList ){
			Integer contentsID = contentsEntity.getContentsID();
			String description = contentsEntity.getDescription();

			// ContentsIDに対応するMaterialを得る
			List<MaterialEntity> materialList = dataStructureBusiness.getMaterialListByContentsID( contentsID );
			if( materialList.size()==0 ){
				throw new RecordNotFoundException( "コンテンツID（写真ID）に対応するマテリアルID（素材ID）が見つかりませんでした。ContentsID="+contentsID );
			}
			Integer materialID = materialList.get(0).getMaterialID();
			
			// AlbumContentsに詰める
			PhotoDTO photoDTO = new PhotoDTO();
			photoDTO.setContentsID( contentsID );
			photoDTO.setDescription( description );
			photoDTO.setMaterialID( materialID );
			
			// リストに詰める
			photoDTOList.add( photoDTO );
		}
		
		// -----< 結果を返す >-----
		//
		return photoDTOList;
	}
	
//	/**
//	 * アルバムを新規に作成する
//	 * @param albumCategory
//	 */
//	public void createAlbum( AlbumCategory albumCategory ){
//		// コンテンツグループを構築する
//		ContentsGroupEntity contentsGroupEntity = albumCategory.getContentsGroupEntity();
//		dataStructureBusiness.createContentsGroup(contentsGroupEntity);
//	}
}
