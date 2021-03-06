package com.mugenunagi.amalbum.albumstructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
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
public class AlbumPageService {
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
	 * 写真/動画を登録します。
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public Integer registContents( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException{
		ContentsType contentsType = albumStructureBusiness.getContentsTypeFromFilename( fileName );
		if( contentsType==null ){
			throw new InvalidParameterException( "指定されたファイルが画像でも動画でもないか、対応していないフォーマットです。File="+fileName );
		}
		
		// 種類ごとに処理を分ける
		Integer registedContentsID = null;
		switch( contentsType ){
		case Photo:
			// 写真を登録する
			registedContentsID = albumStructureBusiness.registPhoto(contentsGroupID, tempFile, fileName);
			break;
			
		case Movie:
			// 動画を登録する
			registedContentsID = albumStructureBusiness.registMovie(contentsGroupID, tempFile, fileName);
			break;
			
		default:
			// 不正ないコンテンツタイプ
			throw new InvalidParameterException( "不正なコンテンツタイプです。ContentsType="+contentsType.getValue() );
		}
		return registedContentsID;
	}

	/**
	 * コンテンツの更新登録を行う。物理ファイルも書き換える。
	 * @param albumPageID
	 * @param contentsID
	 * @param absoluteFile
	 * @param contentsFileName
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvalidParameterException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 */
	public void updateContents(Integer albumPageID, Integer contentsID, File tempFile, String fileName) throws FileNotFoundException, IOException, InvalidParameterException, RecordNotFoundException, InvalidStateException {
		ContentsType contentsType = albumStructureBusiness.getContentsTypeFromFilename( fileName );
		if( contentsType==null ){
			throw new InvalidParameterException( "指定されたファイルが画像でも動画でもないか、対応していないフォーマットです。File="+fileName );
		}
		
		// 種類ごとに処理を分ける
		switch( contentsType ){
		case Photo:
			// 写真を更新する
			albumStructureBusiness.replacePhoto(albumPageID, contentsID, tempFile, fileName);
			break;
			
		case Movie:
			// 動画を更新する
			albumStructureBusiness.replaceMovie(albumPageID, contentsID, tempFile, fileName);
			break;
			
		default:
			// 不正ないコンテンツタイプ
			throw new InvalidParameterException( "不正なコンテンツタイプです。ContentsType="+contentsType.getValue() );
		}
	}


	/**
	 * 指定したIDのコンテンツを返します
	 * @param albumPageID
	 * @param contentsID
	 * @return
	 */
	public ContentsEntity getContentsEntity(Integer albumPageID, Integer contentsID) {
		return dataStructureBusiness.getContentsByContentsID(contentsID);
	}

	/**
	 * 指定したファイル名を持ったコンテンツのエンティティを返します
	 * @param albumPageID
	 * @param contentsFilename
	 * @return
	 * @throws InvalidStateException 
	 */
	public ContentsEntity getContentsEntityByFilename(Integer albumPageID, String contentsFilename) throws InvalidStateException {
		return dataStructureBusiness.getContentsByContentsName(albumPageID, contentsFilename);
	}

	/**
	 * アルバムページIDで指定されたアルバムページのEntityを返します
	 * @param albumPageID
	 * @return
	 * @throws RecordNotFoundException
	 */
	public ContentsGroupEntity getAlbumPageEntity(Integer albumPageID) throws RecordNotFoundException {
		return dataStructureBusiness.getContentsGroup(albumPageID);
	}
}
