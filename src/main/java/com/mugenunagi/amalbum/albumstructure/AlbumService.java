package com.mugenunagi.amalbum.albumstructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

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
	private AlbumStructureBusiness albumStructureBusiness;
	
	@Autowired
	private DataStructureBusiness dataStructureBusiness;

	//=========================================================================
	// メソッド
	//=========================================================================
	public AlbumPageDTO getAlbumPage( int albumPageID ) throws RecordNotFoundException {
		// 指定されたアルバムページに属する写真を取得する
		List<PhotoDTO> photoDTOList = albumStructureBusiness.getPhotoList(albumPageID);
		
		// 指定されたアルバムページの情報を取得する
		ContentsGroupEntity albumPageInfo = dataStructureBusiness.getContentsGroup( albumPageID );
		
		// DTOを構築して返す
		AlbumPageDTO albumPageDTO = new AlbumPageDTO();
		albumPageDTO.setPhotoDTOList(photoDTOList);
		albumPageDTO.setAlbumPageInfo(albumPageInfo);
		
		// 結果を返す
		return albumPageDTO;
	}
	
//	/**
//	 * アルバムブックリストを取得する
//	 */
//	public List<AlbumCategory> getAlbumBookList() {
//		List<AlbumCategory> albumCategoryList = getAlbumCategoryListByParentID( 0 );
//		return albumCategoryList;
//	}

//	/**
//	 * アルバムセクションリストを取得する
//	 */
//	public List<AlbumCategory> getAlbumSectionList( int parentID ) {
//		List<AlbumCategory> albumCategoryList = getAlbumCategoryListByParentID( parentID );
//		return albumCategoryList;
//	}

//	/**
//	 * アルバムのコンテンツリストを取得する
//	 */
//	public List<AlbumContents> getAlbumContentsList( int albumSectionID ) {
//		List<AlbumContents> albumContentsList = getAlbumContentsListByParentID( albumSectionID );
//		return albumContentsList;
//	}
	
//	/**
//	 * アルバムを新規に作成する
//	 * @param albumContents
//	 */
//	public void createAlbum( String albumName, String brief ) {
//		// AlbumCategoryオブジェクトを作る
//		AlbumCategory albumCategory = new AlbumCategory();
//		albumCategory.setContentsGroupEntity( new ContentsGroupEntity() );
//		
//		ContentsGroupEntity contentsGroupEntity = albumCategory.getContentsGroupEntity();
//		contentsGroupEntity.setName( albumName );
//		contentsGroupEntity.setBrief( brief );
//		contentsGroupEntity.setParentID( Constants.rootContentsGroupID );
//		contentsGroupEntity.setDescription( null );
//		contentsGroupEntity.setCreateDate( new Date() );
//		contentsGroupEntity.setUpdateDate( new Date() );
//		contentsGroupEntity.setDeleteDate( null );
//		
//		// 新規のアルバムを作る
//		albumStructureBusiness.createAlbum( albumCategory );
//		
//	}

	
	//=========================================================================
	// Privateメソッド
	//=========================================================================
//	/**
//	 * 指定したアルバムグループIDを親に持つアルバムグループを返す。<BR>
//	 * parentID=0がルート
//	 * @param parentID 親のアルバムグループID
//	 * @return アルバムグループを格納したリスト
//	 */
//	private List<AlbumCategory> getAlbumCategoryListByParentID( int parentID ){
//		// -----< アルバムカテゴリーを取得して返す >-----
//		//
//		List<AlbumCategory> albumCategoryList = albumStructureBusiness.getAlbumCategoryListByParentID( parentID );
//		
//		// -----< 結果を返す >-----
//		//
//		return albumCategoryList;
//	}
	

	/**
	 * 指定したアルバムセクションIDを親に持つアルバムのコンテンツを返す。<BR>
	 * 
	 * @param parentID アルバムセクションのID
	 * @return アルバムのコンテンツを格納したリスト
	 */
//	private List<AlbumContents> getAlbumContentsListByParentID( int parentID ){
//		// -----< アルバムコンテンツを取得して返す >-----
//		//
//		List<AlbumContents> albumContentsList = albumStructureBusiness.getAlbumContentsListByParentID( parentID );
//		
//		// -----< 結果を返す >-----
//		//
//		return albumContentsList;
//	}
}
