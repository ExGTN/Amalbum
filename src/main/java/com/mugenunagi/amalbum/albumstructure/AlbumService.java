package com.mugenunagi.amalbum.albumstructure;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageListDTO;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
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
	/**
	 * 指定されたalbumPageIDについての、アルバムページの情報を構築し、DTOに格納して返す
	 * @throws RecordNotFoundException 
	 */
	public AlbumPageListDTO getAlbumPageList( int albumID ) throws RecordNotFoundException {
		// 指定されたアルバム属するアルバムページを取得する
		List<ContentsGroupEntity> albumPageList = albumStructureBusiness.getAlbumPageList( albumID );

		// 指定されたアルバムの情報を取得する
		ContentsGroupEntity albumInfo = dataStructureBusiness.getContentsGroup( albumID );
		
		// DTOを構築して返す
		AlbumPageListDTO albumPageListDTO = new AlbumPageListDTO();
		albumPageListDTO.setAlbumPageList(albumPageList);
		albumPageListDTO.setAlbumInfo(albumInfo);
		
		// 結果を返す
		return albumPageListDTO;
	}
	
	/**
	 * 指定されたalbumPageIDについての、アルバムページの情報を構築し、DTOに格納して返す
	 */
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
	
	/**
	 * 指定したアルバムのなかで、指定したアルバムページ名を持つアルバムページIDを返す
	 * @param albumPageName
	 * @return
	 */
	public Integer getAlbumPageID( Integer albumID, String albumPageName ){
		// 検索する
		Integer albumPageID = dataStructureBusiness.getContentsGroupID( albumID, albumPageName );

		// 結果を返す
		return albumPageID;
	}

	/**
	 * 写真/動画を登録します。
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String registContents( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException{
		ContentsType contentsType = albumStructureBusiness.getContentsTypeFromFilename( fileName );
		if( contentsType==null ){
			throw new InvalidParameterException( "指定されたファイルが画像でも動画でもないか、対応していないフォーマットです。File="+fileName );
		}
		
		// 種類ごとに処理を分ける
		switch( contentsType ){
		case Photo:
			// 写真を登録する
			fileName = albumStructureBusiness.registPhoto(contentsGroupID, tempFile, fileName);
			break;
			
		case Movie:
			// 動画を登録する
			fileName = albumStructureBusiness.registMovie(contentsGroupID, tempFile, fileName);
			break;
		}
		return fileName;
	}

	/**
	 * 指定したアルバムIDについて、指定したアルバムページ名でアルバムページを作成します
	 * @param albumPageID
	 * @param defaultAlbumPageName
	 * @return
	 */
	public Integer createAlbumPage(Integer albumID, String defaultAlbumPageName) {
		// 作る
		Integer albumPageID = albumStructureBusiness.createAlbumPage(albumID, defaultAlbumPageName);

		// 構築したページのIDを返す
		return albumPageID;
	}

	
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
	

//	/**
//	 * 指定したアルバムセクションIDを親に持つアルバムのコンテンツを返す。<BR>
//	 * 
//	 * @param parentID アルバムセクションのID
//	 * @return アルバムのコンテンツを格納したリスト
//	 */
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
