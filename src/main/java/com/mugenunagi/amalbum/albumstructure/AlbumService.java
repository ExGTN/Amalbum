package com.mugenunagi.amalbum.albumstructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageListDTO;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.form.LoginForm;
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
	
	@Autowired
	private ContentsFileUtil contentsFileUtil;

	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 指定されたalbumPageIDについての、アルバムページの情報を構築し、DTOに格納して返す
	 * @throws RecordNotFoundException 
	 */
	public AlbumPageListDTO getAlbumPageList( int albumID, int page ) throws RecordNotFoundException {
		// 指定されたアルバム属するアルバムページを取得する
		List<ContentsGroupEntity> albumPageList = albumStructureBusiness.getAlbumPageList( albumID, page );

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
	 * アルバムページ数を取得します
	 * @param parentID
	 * @return
	 */
	public Integer getAlbumPageCount( Integer parentID ){
		return albumStructureBusiness.getAlbumPageCount(parentID);
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
	
	/**
	 * 指定したアルバムページが所属するアルバムのアルバムIDを検索して返します
	 * @param albumPageID
	 * @return
	 * @throws RecordNotFoundException
	 */
	public Integer getAlbumIDFromAlbumPageID(Integer albumPageID) throws RecordNotFoundException{
		ContentsGroupEntity contentsGroupEntity = dataStructureBusiness.getContentsGroup(albumPageID);
		Integer albumID = contentsGroupEntity.getParentID();
		return albumID;
	}
	
	
	/**
	 * 指定されたアルバムページを削除します
	 * @param albumPageID
	 * @throws Throwable 
	 */
	public void removeAlbumPage( Integer albumPageID ) throws Throwable{
		// アルバムページのタイトルを削除する
		String albumPageTitlePath = contentsFileUtil.getAlbumPageTitlePath(albumPageID);
		contentsFileUtil.deleteFileWithRemoveDir( albumPageTitlePath );
		
		// アルバムページのコメントを削除する
		String albumPageCommentPath = contentsFileUtil.getAlbumPageCommentPath(albumPageID);
		contentsFileUtil.deleteFileWithRemoveDir( albumPageCommentPath );
		
		// アルバムに含まれるコンテンツを取得する
		AlbumPageDTO albumPageDTO = this.getAlbumPage(albumPageID);
		
		// アルバムに含まれるコンテンツごとの情報を削除する
		List<PhotoDTO> photoDTOList = albumPageDTO.getPhotoDTOList();
		for( PhotoDTO photoDTO : photoDTOList ){
			albumStructureBusiness.removeContents(photoDTO);
		}
		
		// アルバムページを削除する
		dataStructureBusiness.deleteWholeContentsGroup(albumPageID);
	}
	

	/**
	 * 指定されたアルバムページに関するサムネイルを再構築します
	 * @param albumPageID
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws FileNotFoundException 
	 */
	public void remakeThumbnail( Integer albumPageID ) throws RecordNotFoundException, InvalidParameterException, FileNotFoundException, InvalidStateException, IOException{
		// アルバムに含まれるコンテンツを取得する
		AlbumPageDTO albumPageDTO = this.getAlbumPage(albumPageID);
		
		// アルバムに含まれるコンテンツごとにサムネイルを作作成する
		List<PhotoDTO> photoDTOList = albumPageDTO.getPhotoDTOList();
		for( PhotoDTO photoDTO : photoDTOList ){
			Integer contentsTypeValue = photoDTO.getContentsType();
			ContentsType contentsType = Constants.ContentsTypeMap.get(contentsTypeValue);
			Integer contentsID = photoDTO.getContentsID();
			switch( contentsType ){
			case Photo:
				// 写真のサムネイルを再作成する
				albumStructureBusiness.remakePhotoThumbnail(contentsID);
				break;

			case Movie:
				// ムービーのサムネイルを再作成する
				albumStructureBusiness.remakeMovieThumbnail(contentsID);
				break;

			default:
				// 不正ないコンテンツタイプ
				throw new InvalidParameterException( "不正なコンテンツタイプです。ContentsType="+contentsType.getValue() );
			}
		}
	}

	/**
	 * パスワードチェック
	 * @param loginForm
	 * @return
	 */
	public Boolean checkPassword(LoginForm loginForm) {
		String userID = loginForm.getUserID();
		String passwordMD5 = loginForm.getPasswordMD5();
		
		Boolean result = dataStructureBusiness.checkPassword( userID, passwordMD5 );
		return result;
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
