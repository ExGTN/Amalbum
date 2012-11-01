package com.mugenunagi.amalbum.albumstructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidStateException;
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
	
	@Autowired
	PhotoRegistrator photoRegistrator;

	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 親IDを指定して、AlbumPageの一覧を作って返す
	 */
	public List<ContentsGroupEntity> getAlbumPageList( int parentID ){
		// -----< DataStructureから情報を取り出す >-----
		//
		List<ContentsGroupEntity> contentsGroupEntityList = dataStructureBusiness.getContentsGroupListByParentID( parentID );
		
		// -----< 結果を返す >-----
		//
		return contentsGroupEntityList;
	}


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
			photoDTO.setPath( materialList.get(0).getPath() );
			
			// リストに詰める
			photoDTOList.add( photoDTO );
		}
		
		// -----< 結果を返す >-----
		//
		return photoDTOList;
	}

	/**
	 * 写真IDを指定して、１枚の写真に関するPhotoDTOを作って返す
	 * @param photoID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public PhotoDTO getPhoto( int photoID ) throws RecordNotFoundException {
		// -----< DataStructureから情報を取り出す >-----
		//
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID( photoID );
		
		// -----< 取り出した情報をDTOに詰め替えて返す >-----
		//
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
		photoDTO.setPath( materialList.get(0).getPath() );
		
		// -----< 結果を返す >-----
		//
		return photoDTO;
	}

	/**
	 * 指定されたcontentsIDのアルバムページに、tempFileの写真を追加します
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 */
	public String registPhoto( Integer contentsID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException {
		fileName = photoRegistrator.registPhoto( contentsID, tempFile, fileName );
		return fileName;
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

	/**
	 * アルバムページを新規に作成する
	 */
	public Integer createAlbumPage(Integer albumID, String albumPageName) {
		// ためしにIDを取得し、既存ならそのIDを返す
		Integer albumPageID = dataStructureBusiness.getContentsGroupID( albumID, albumPageName );
		if( albumPageID!=null ){ return albumPageID; }

		// コンテンツグループを追加する
		dataStructureBusiness.createContentsGroup(albumID, albumPageName);
		
		// 追加したグループについて、IDを取得する
		albumPageID = dataStructureBusiness.getContentsGroupID( albumID, albumPageName );

		// 結果を返す
		return albumPageID;
	}
}
