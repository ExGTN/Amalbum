package com.mugenunagi.amalbum.albumstructure.ContentsRegistrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

@Component
public class ContentsFileUtil {
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private DataStructureBusiness dataStructureBusiness;
	
	/**
	 * 指定されたコンテンツグループのベースパスを返します
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	protected String getContentsGroupBasePath( Integer contentsGroupID, ContentsType contentsType ) throws RecordNotFoundException, InvalidParameterException{
		// キーの選択
		String relativePathKey = null;
		switch( contentsType ){
		case Photo:
			relativePathKey = "PHOTO_RELATIVE_PATH";
			break;
		case Movie:
			relativePathKey = "MOVIE_RELATIVE_PATH";
			break;
		default:
			throw new InvalidParameterException( "不正なContentsTypeが指定されました。ContentsType="+contentsType.name() );
		}
		
		// ベースパスを取得する
		String localContentsBasePath		= applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );
		String contentsRelativePath			= applicationProperties.getString( relativePathKey );
		String contentsBasePath				= localContentsBasePath + "/" + contentsRelativePath;
		
		// 指定されたコンテンツIDが所属するコンテンツグループを辿ってパスを作る
		String contentsGroupPath = "";
		while( (contentsGroupID!=null)&&(contentsGroupID!=Constants.undefined) ){
			ContentsGroupEntity contentsGroupEntity = dataStructureBusiness.getContentsGroup(contentsGroupID);
			
			// パスを接続する
			String name = contentsGroupEntity.getName();
			if( name.length()>0 ){
				if( contentsGroupPath.length()==0 ){
					contentsGroupPath = name;
				} else {
					contentsGroupPath = name + "/" + contentsGroupPath;
				}
			}
			
			// contentsGroupID を更新
			contentsGroupID = contentsGroupEntity.getParentID();
		}
		if( contentsBasePath.length()>0 ){
			if( contentsGroupPath.length()>0 ){
				contentsGroupPath = contentsBasePath + "/" + contentsGroupPath;
			} else {
				contentsGroupPath = contentsBasePath;
			}
		}
		
		return contentsGroupPath;
	}

	/**
	 * 指定されたコンテンツIDについてのベースパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String getContentsBasePath( Integer contentsID ) throws RecordNotFoundException, InvalidParameterException{
		// コンテンツの情報を得る
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);

		// 指定されたコンテンツIDが所属するコンテンツグループを辿ってパスを作る
		Integer contentsType = contentsEntity.getContentsType();
		String contentsGroupPath = this.getContentsGroupBasePath( contentsEntity.getContentsGroupID(), Constants.ContentsTypeMap.get(contentsType) );
		
		// コンテンツのベースディレクトリをつなげる
		String contentsBasePath = contentsGroupPath;
		if( contentsEntity.getBaseDir().length()>0 ){
			if( contentsBasePath.length()>0 ){
				contentsBasePath = contentsBasePath + "/" + contentsEntity.getBaseDir();
			} else {
				contentsBasePath = contentsEntity.getBaseDir();
			}
		}
		
		return contentsBasePath;
	}

	/**
	 * 指定したコンテンツIDに属する、指定したマテリアルタイプのファイルの配置先をフルパスで返します。<BR>
	 * 複数存在する場合は、最初に見つかったマテリアルのパスのみを返します。
	 * @param contentsID
	 * @param materialType
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String getMaterialPathSingle( Integer contentsID, Integer materialType ) throws RecordNotFoundException, InvalidParameterException{
    	ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
    	if( contentsEntity==null ){
    		return null;
    	}

    	// -----< コンテンツに紐付くマテリアルを検索する >-----
    	//
    	List<MaterialEntity> materialEntityList = dataStructureBusiness.getMaterialListByContentsID(contentsID);
    	
    	// 素材タイプが一致するものを選ぶ
    	MaterialEntity materialEntity = null;
    	for( MaterialEntity mat : materialEntityList ){
    		Integer type = mat.getMaterialType();
    		if( type==null ){ continue; }
    		if( type.equals(materialType) ){
    			materialEntity = mat;
    		}
    	}
    	
    	// -----< パスを作る >-----
    	//
    	String contentsPath = getContentsBasePath(contentsID);
    	if( contentsPath!=null ){
    		contentsPath = contentsPath + "/" + materialEntity.getPath();
    	} else {
    		contentsPath = materialEntity.getPath();
    	}

    	// -----< 結果を返す >-----
    	//
    	return contentsPath;
	}

	public String getMaterialPath( Integer materialID ) throws RecordNotFoundException, InvalidParameterException{
		MaterialEntity materialEntity = dataStructureBusiness.getMaterialByMaterialID(materialID);
		Integer contentsID = materialEntity.getContentsID();
		
    	// -----< パスを作る >-----
    	//
    	String contentsPath = getContentsBasePath(contentsID);
    	if( contentsPath!=null ){
    		contentsPath = contentsPath + "/" + materialEntity.getPath();
    	} else {
    		contentsPath = materialEntity.getPath();
    	}

    	// -----< 結果を返す >-----
    	//
    	return contentsPath;
	}

	/**
	 * 指定されたコンテンツIDについての写真のフルパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String getPhotoFilePath( Integer contentsID ) throws RecordNotFoundException, InvalidParameterException{
		// コンテンツのベースパスを返す
		String fullPath = this.getMaterialPathSingle(contentsID, Constants.MaterialType.Photo.getValue() );
		
		// 結果を返す
		return fullPath;
	}
	
	/**
	 * 指定されたコンテンツIDについての動画のフルパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String getMovieFilePath( Integer contentsID ) throws RecordNotFoundException, InvalidParameterException{
		// コンテンツのベースパスを返す
		String fullPath = this.getMaterialPathSingle(contentsID, Constants.MaterialType.Movie.getValue() );
		
		// 結果を返す
		return fullPath;
	}


	/**
	 * 指定されたコンテンツIDについてのサムネイルのフルパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String getThumbnailFilePath( Integer contentsID ) throws RecordNotFoundException, InvalidParameterException{
		// コンテンツのベースパスを返す
		String fullPath = this.getMaterialPathSingle(contentsID, Constants.MaterialType.Thumbnail.getValue() );
		
		// 結果を返す
		return fullPath;
	}
	
	/**
	 * 指定されたコンテンツグループのベースパスに存在するサムネイルのディレクトリのパスを返します
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String getContentsGroupThumbnailPath( Integer contentsGroupID, ContentsType contentsType ) throws RecordNotFoundException, InvalidParameterException{
		String thumbnailRelativePath = applicationProperties.getString( "CONTENTS_THUMBNAIL_RELATIVE_PATH" );
		String contentsGroupBasePath = this.getContentsGroupBasePath(contentsGroupID, contentsType);
		
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
							+ applicationProperties.getString( "CONTENTS_TEMP_RELATIVE_PATH" );
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
		String thumbnailRelativePath = applicationProperties.getString("CONTENTS_THUMBNAIL_RELATIVE_PATH");
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
		String prefix = applicationProperties.getString("CONTENTS_THUMBNAIL_PREFIX");
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
		String value = applicationProperties.getString("CONTENTS_THUMBNAIL_WIDTH");
		return Integer.parseInt(value);
	}


	/**
	 * サムネイルの最大高さを返します
	 * @return
	 */
	public int getThumbnailHeight() {
		String value = applicationProperties.getString("CONTENTS_THUMBNAIL_HEIGHT");
		return Integer.parseInt(value);
	}
}
