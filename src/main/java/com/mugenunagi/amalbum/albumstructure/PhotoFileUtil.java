package com.mugenunagi.amalbum.albumstructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.datastructure.DataFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

@Component
public class PhotoFileUtil {
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	private DataFileUtil dataFileUtil;
	
	/**
	 * 指定されたコンテンツIDについての写真のフルパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getPhotoFilePath( Integer contentsID ) throws RecordNotFoundException{
		// コンテンツのベースパスを返す
		String fullPath = dataFileUtil.getMaterialPathSingle(contentsID, Constants.MaterialType.Photo.getValue() );
		
		// 結果を返す
		return fullPath;
	}


	/**
	 * 指定されたコンテンツIDについてのサムネイルのフルパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getThumbnailFilePath( Integer contentsID ) throws RecordNotFoundException{
		// コンテンツのベースパスを返す
		String fullPath = dataFileUtil.getMaterialPathSingle(contentsID, Constants.MaterialType.Thumbnail.getValue() );
		
		// 結果を返す
		return fullPath;
	}
	
	/**
	 * 指定されたコンテンツグループのベースパスに存在するサムネイルのディレクトリのパスを返します
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getContentsGroupThumbnailPath( Integer contentsGroupID ) throws RecordNotFoundException{
		String thumbnailRelativePath = applicationProperties.getString( "PHOTO_THUMBNAIL_RELATIVE_PATH" );
		String contentsGroupBasePath = dataFileUtil.getContentsGroupBasePath(contentsGroupID);
		
		String result = contentsGroupBasePath;
		if( result.length()>0 ){
			result = result + "/";
		}
		result = result + thumbnailRelativePath;
		return result;
	}

	/**
	 * 一時ファイルの格納先のパスを返します
	 * @return
	 */
	public String getTempPath() {
		String tempDirPath	= applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" )
							+ "/"
							+ applicationProperties.getString( "PHOTO_TEMP_RELATIVE_PATH" );
		return tempDirPath;
	}

	/**
	 * 写真ファイルの格納先のパスを返します
	 * @return
	 */
	public String getPhotoBasePath() {
		String tempDirPath	= applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" )
							+ "/"
							+ applicationProperties.getString( "PHOTO_RELATIVE_PATH" );
		return tempDirPath;
	}


	/**
	 * コンテンツグループのベースディレクトリを指定して実行すると、サムネイルのパスを返します。
	 * @param contentsGroupBasePath
	 * @return
	 */
	public String makeThumbnailDirPath(String contentsGroupBasePath) {
		String thumbnailRelativePath = applicationProperties.getString("PHOTO_THUMBNAIL_RELATIVE_PATH");
		String result = "";
		if( contentsGroupBasePath.length()==0 ){
			result = thumbnailRelativePath;
		} else {
			result = contentsGroupBasePath + "/" + thumbnailRelativePath;
		}
		return result;
	}


	/**
	 * サムネイルのベースディレクトリと、オリジナルのファイル名を指定すると、サムネイルのフルパスを作って返します。
	 * @param contentsGroupBasePath
	 * @param fileName
	 * @return
	 */
	public String makeThumbnailPath(String contentsGroupBasePath,
			String fileName) {

		// サムネイルの格納先ディレクトリを作る
		StringBuffer sb = new StringBuffer();
		sb.append( this.makeThumbnailDirPath(contentsGroupBasePath) );
		sb.append("/");
		
		// プレフィクスを作る
		String prefix = applicationProperties.getString("PHOTO_THUMBNAIL_PREFIX");
		sb.append(prefix);
		sb.append(".");
		sb.append(fileName);
		
		return sb.toString();
	}


	/**
	 * サムネイルの最大幅を返します
	 * @return
	 */
	public int getThumbnailWidth() {
		String value = applicationProperties.getString("PHOTO_THUMBNAIL_WIDTH");
		return Integer.parseInt(value);
	}


	/**
	 * サムネイルの最大高さを返します
	 * @return
	 */
	public int getThumbnailHeight() {
		String value = applicationProperties.getString("PHOTO_THUMBNAIL_HEIGHT");
		return Integer.parseInt(value);
	}
}
