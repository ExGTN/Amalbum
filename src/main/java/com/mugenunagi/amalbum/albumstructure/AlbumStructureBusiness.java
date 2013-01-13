package com.mugenunagi.amalbum.albumstructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.MovieRegistrator;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.PhotoRegistrator;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.gtnlib.graphics.image.ImageUtils;

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
	
	@Autowired
	MovieRegistrator movieRegistrator;

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
			Integer contentsType = contentsEntity.getContentsType();
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
			photoDTO.setContentsType( contentsType );
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
	 * 指定されたcontentsGroupIDのアルバムページに、tempFileの写真を追加します
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String registPhoto( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException {
		fileName = photoRegistrator.regist( contentsGroupID, tempFile, fileName );
		return fileName;
	}

	/**
	 * 指定されたcontentsGroupIDのアルバムページに、tempFileの動画を追加します
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String registMovie( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException {
		fileName = movieRegistrator.regist( contentsGroupID, tempFile, fileName );
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

	/**
	 * 指定されたファイル名について、コンテンツの種類を判別して、結果をContentsType型で返します
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public ContentsType getContentsTypeFromFilename(String fileName) throws FileNotFoundException, IOException {
		ContentsType contentsType = null;

		String mimeType = ImageUtils.getMimeTypeFromFilePath(fileName);
		if( mimeType.startsWith("image") ){
			contentsType = ContentsType.Photo;
		} else if ( mimeType.startsWith("video") ){
			contentsType = ContentsType.Movie;
		}
		
		// 結果を返す
		return contentsType;
	}

	/**
	 * 指定されたコンテンツIDについて、コンテンツの種類を判別して、結果をContentsType型で返します
	 * @param contentsID
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public ContentsType getContentsTypeFromContentsID(Integer contentsID) throws FileNotFoundException, IOException {
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
		Integer contentsTypeValue = contentsEntity.getContentsType();
		ContentsType contentsType = Constants.ContentsTypeMap.get(contentsTypeValue);
		return contentsType;
	}
}
