package com.mugenunagi.amalbum.albumstructure.ContentsRegistrator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.gtnlib.html.HTMLUtil;
import com.mugenunagi.gtnlib.io.FilePathUtil;

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
	public String getContentsGroupBasePath( Integer contentsGroupID, ContentsType contentsType ) throws RecordNotFoundException, InvalidParameterException{
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
	 * 
	 * @param albumID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getAlbumPageTitlePath( Integer albumID ) throws RecordNotFoundException{
		String result = this.getAlbumPagePropertyDescPath(albumID, "title.txt");
		return result;
	}

	/**
	 * 
	 * @param albumID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getAlbumPageCommentPath( Integer albumID ) throws RecordNotFoundException{
		String result = this.getAlbumPagePropertyDescPath(albumID, "comment.txt");
		return result;
	}

	/**
	 * アルバムページに関するコメントファイルのフルパスを返す
	 * @param albumPageID
	 * @return
	 * @throws RecordNotFoundException
	 */
	private String getAlbumPagePropertyDescPath( Integer albumPageID, String filename ) throws RecordNotFoundException{
		String contentsBasePath = applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );
		String commentRelativePath = applicationProperties.getString( "COMMENT_RELATIVE_PATH" );
		
		// アルバムページに関する ContentsGroup を得る
		ContentsGroupEntity albumPageEntity = dataStructureBusiness.getContentsGroup(albumPageID);
		
		// アルバムに関するContentsGroupを得る
		Integer albumID = albumPageEntity.getParentID();
		ContentsGroupEntity albumEntity = dataStructureBusiness.getContentsGroup(albumID);
		
		// フルパスを作って返す
		String fullPath = contentsBasePath
						+ "/" + commentRelativePath
						+ "/" + albumEntity.getName()
						+ "/" + albumPageEntity.getName()
						+ "/" + filename;
		return fullPath;
	}
	

	/**
	 * 写真や動画に関するコメントファイルのフルパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getContentsCommentPath( Integer contentsID ) throws RecordNotFoundException{
		String contentsBasePath = applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );
		String commentRelativePath = applicationProperties.getString( "COMMENT_RELATIVE_PATH" );
		
		// 写真や動画に関する ContentsEntity を得る
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
		Integer albumPageID = contentsEntity.getContentsGroupID();
		
		// 主たるマテリアルのMaterialEntityを得る
		ContentsType contentsType = Constants.ContentsTypeMap.get( contentsEntity.getContentsType() );
		MaterialEntity materialEntity = null;
		List<MaterialEntity> materialEntityList = dataStructureBusiness.getMaterialListByContentsID(contentsID);
		for( MaterialEntity entity : materialEntityList ){
			Integer materialType = entity.getMaterialType();
			if(   (materialType.intValue()==Constants.MaterialType.Photo.getValue().intValue())
				||(materialType.intValue()==Constants.MaterialType.Movie.getValue().intValue())
			){
				materialEntity = entity;
				break;
			}
		}

		// アルバムページに関する ContentsGroup を得る
		ContentsGroupEntity albumPageEntity = dataStructureBusiness.getContentsGroup(albumPageID);
		
		// アルバムに関するContentsGroupを得る
		Integer albumID = albumPageEntity.getParentID();
		ContentsGroupEntity albumEntity = dataStructureBusiness.getContentsGroup(albumID);

		// フルパスを作って返す
		String contentsBaseDir = contentsEntity.getBaseDir();
		if( contentsBaseDir.length()>0 ){
			contentsBaseDir = "/" + contentsBaseDir;
		}
		String fullPath = contentsBasePath
						+ "/" + commentRelativePath
						+ "/" + albumEntity.getName()
						+ "/" + albumPageEntity.getName()
						+ contentsBaseDir
						+ "/" + materialEntity.getPath()
						+ ".comment";
		return fullPath;
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

	/**
	 * 指定されたコンテンツIDのコメントファイルを出力します
	 * @param contentsID
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public void writeContentsComment(Integer contentsID) throws InvalidStateException, IOException, RecordNotFoundException, InvalidParameterException {
		// コンテンツの内容を取得する
		ContentsEntity contentsEntity = this.dataStructureBusiness.getContentsByContentsID(contentsID);
		String comment = contentsEntity.getDescription();
		comment = HTMLUtil.decodeHtmlSpecialChars(comment);
		String commentFilePath = this.getContentsCommentPath(contentsID);
		
		// 内容が空なら削除
		if( comment.length()==0 ){
			this.deleteFileWithRemoveDir( commentFilePath );
			return;
		}
		
		// 既存なら一旦消す
		File commentFile = new File( commentFilePath );
		if( commentFile.exists() ){
			commentFile.delete();
		}
		
		// ファイルを作成
		String dir = commentFile.getParent();
		FilePathUtil.prepareDirectory(dir);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(commentFile)));
		pw.println( comment );
		pw.close();
	}

	/**
	 * 指定されたアルバムページに関するタイトルファイルを出力します
	 * @param contentsGroupID
	 * @throws RecordNotFoundException
	 * @throws InvalidParameterException
	 * @throws InvalidStateException
	 * @throws IOException
	 */
	public void writeAlbumPageTitle(Integer contentsGroupID) throws RecordNotFoundException, InvalidParameterException, InvalidStateException, IOException {
		// コンテンツグループの内容を取得する
		ContentsGroupEntity contentsGroupEntity = this.dataStructureBusiness.getContentsGroup(contentsGroupID);
		String title = contentsGroupEntity.getBrief();
		title = HTMLUtil.decodeHtmlSpecialChars(title);
		String titleFilePath = this.getAlbumPageTitlePath(contentsGroupID);
		
		// 内容が空なら削除
		if( title.length()==0 ){
			this.deleteFileWithRemoveDir( titleFilePath );
			return;
		}
		
		// 既存なら一旦消す
		File titleFile = new File( titleFilePath );
		if( titleFile.exists() ){
			titleFile.delete();
		}
		
		// ファイルを作成
		String dir = titleFile.getParent();
		FilePathUtil.prepareDirectory(dir);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(titleFile)));
		pw.println( title );
		pw.close();
	}

	/**
	 * 指定されたアルバムページに関するコメントファイルを出力します
	 * @param contentsGroupID
	 * @throws RecordNotFoundException
	 * @throws InvalidParameterException
	 * @throws InvalidStateException
	 * @throws IOException
	 */
	public void writeAlbumPageComment(Integer contentsGroupID) throws RecordNotFoundException, InvalidParameterException, InvalidStateException, IOException {
		// コンテンツグループの内容を取得する
		ContentsGroupEntity contentsGroupEntity = this.dataStructureBusiness.getContentsGroup(contentsGroupID);
		String comment = contentsGroupEntity.getDescription();
		comment = HTMLUtil.decodeHtmlSpecialChars(comment);
		String commentFilePath = this.getAlbumPageCommentPath(contentsGroupID);
		
		// 内容が空なら削除
		if( comment.length()==0 ){
			this.deleteFileWithRemoveDir( commentFilePath );
			return;
		}
		
		// 既存なら一旦消す
		File commentFile = new File( commentFilePath );
		if( commentFile.exists() ){
			commentFile.delete();
		}
		
		// ファイルを作成
		String dir = commentFile.getParent();
		FilePathUtil.prepareDirectory(dir);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(commentFile)));
		pw.println( comment );
		pw.close();
	}

	/**
	 * 指定したファイルを削除します。当該のディレクトリに含まれるファイルが無くなった場合は
	 * LOCAL_CONTENTS_BASE_PATHを限界に、親ディレクトリを辿ってディレクトリも削除します。
	 * @param commentFilePath
	 * @throws InvalidParameterException 
	 */
	public void deleteFileWithRemoveDir(String targetPath) throws InvalidParameterException {
		// 限界のパスを取得する
		String limitPath = applicationProperties.getString("LOCAL_CONTENTS_BASE_PATH");
		File limitFile = new File( limitPath );
		limitPath = limitFile.getAbsolutePath();
		
		// limitPathから始まってなければ不正
		File targetFile = new File( targetPath );
		targetPath = targetFile.getAbsolutePath();
		if( !targetPath.startsWith(limitPath) ){
			throw new InvalidParameterException( "指定されたパス（"+targetPath+"）が、LOCAL_CONTENTS_BASE_PATH（"+limitPath+"）から始まっていません。" );
		}
		
		// 順次消していく
		while( !targetFile.getAbsolutePath().equals(limitFile.getAbsolutePath()) ){
			// ターゲットが不在ならここまで
			if( !targetFile.exists() ){
				break;
			}
			
			// ディレクトリに他のファイルが含まれるならここまで
			if( targetFile.isDirectory() ){
				String[] fileList = targetFile.list();
				if( fileList.length>0 ){
					break;
				}
			}
			
			// 削除
			targetFile.delete();
			
			// ひとつ上の階層へ
			String prevPath = targetFile.getAbsolutePath();
			targetFile = targetFile.getParentFile();
			if( prevPath.equals(targetFile.getAbsolutePath()) ){
				// 親ディレクトリを辿ってもパスがかわらない＝ルートディレクトリなら、ここで抜け
				break;
			}
		}
		
		// 消しおわり
	}
}
