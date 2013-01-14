package com.mugenunagi.amalbum.albumstructure.ContentsRegistrator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.AlbumService;
import com.mugenunagi.amalbum.albumstructure.AlbumStructureBusiness;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.dao.ContentsMapper;
import com.mugenunagi.amalbum.datastructure.dao.MaterialMapper;
import com.mugenunagi.amalbum.datastructure.dao.SequenceMapper;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.gtnlib.graphics.image.ImageUtils;
import com.mugenunagi.gtnlib.io.FilePathUtil;

/**
 * 動画を登録する機能を提供するクラス
 * @author GTN
 *
 */
@Component
public class MovieRegistrator extends AbstractContentsRegistrator {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
	DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	AlbumStructureBusiness albumStructureBusiness;
	
	@Autowired
	AlbumService albumService;
	
	@Autowired
	private SequenceMapper sequenceMapper;
	
	@Autowired
	private ContentsMapper contentsMapper;
	
	@Autowired
	private MaterialMapper materialMapper;
	
	@Autowired
	private ContentsFileUtil contentsFileUtil;

	
	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 指定されたcontentsGroupIDのアルバムページに、tempFileの動画を追加します
	 * @throws RecordNotFoundException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws InvalidParameterException 
	 */
	public String regist( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException {
		// 動画ファイルを配置する
		Map<String,String> filePathMap = this.locateMovieFiles(contentsGroupID, tempFile, fileName);
		fileName = filePathMap.get( "ServerFileName" );
		
		// DBに登録する
		registToDB( contentsGroupID, filePathMap, fileName );
		
		// 実際に登録されたファイル名を返す
		return fileName;
	}


	/**
	 * tempFileで指定された画像ファイルについて、アルバム用に動画を配置し、配置先のパス情報をMapで返します。
	 *   ContentsBasePath : コンテンツファイルの配置先のベースパス。具体的には、アルバムページのディレクトリが返る。
	 * @param contentsGroupID
	 * @param tempFile
	 * @param fileName
	 * @return
	 * @throws RecordNotFoundException
	 * @throws InvalidStateException
	 * @throws IOException
	 * @throws InvalidParameterException 
	 */
	public Map<String,String> locateMovieFiles( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException {
		
		// アルバムページの情報を取得する
		String contentsGroupBasePath = contentsFileUtil.getContentsGroupBasePath(contentsGroupID, ContentsType.Movie);

		// YYYYMMDDディレクトリを作る
		FilePathUtil.prepareDirectory( contentsGroupBasePath );

		// 画像ファイルを配置する
		String destFilePath = FilePathUtil.makeUniqueFileName( contentsGroupBasePath + "/" + fileName );
		File serverFile = new File( destFilePath );
		String serverFileName = serverFile.getName();
		fileName = serverFileName;
		
		File destFile = new File( destFilePath );
		tempFile.renameTo( destFile );
		
		// サムネイルを作る
		String thumbnailPath = locateThumbnail( contentsGroupBasePath, fileName );

		// 作成したサムネイルのパスを相対パスに変換する
		if( thumbnailPath.startsWith(contentsGroupBasePath)){
			thumbnailPath = thumbnailPath.substring( contentsGroupBasePath.length() );
			if( thumbnailPath.startsWith("/") ){
				thumbnailPath = thumbnailPath.substring(1);
			}
		}

		// ファイルパスを返す
		Map<String,String> result = new HashMap<String,String>();
		result.put( "ContentsBasePath", "" );
		result.put( "Movie", fileName);
		result.put( "Thumbnail", thumbnailPath );
		result.put( "ServerFileName", serverFileName );
		return result;
	}

	/**
	 * 指定したコンテンツIDの写真について、サムネイルを生成して配置します。<BR>
	 * このメソッドは、ContentsがすでにDB上に登録されている必要があります。Thumbnailを再作成するときに利用します。<BR>
	 * すでにファイルが存在する場合は上書きします。
	 * @param contentsID
	 * @throws RecordNotFoundException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws FileNotFoundException 
	 * @throws InvalidParameterException 
	 */
	public String locateThumbnail( Integer contentsID ) throws RecordNotFoundException, FileNotFoundException, InvalidStateException, IOException, InvalidParameterException{
		
		String contentsBasePath = contentsFileUtil.getContentsBasePath(contentsID);

		String movieFilePath = contentsFileUtil.getMovieFilePath(contentsID);
		File movie = new File( movieFilePath );
		String movieFileName = movie.getName();
		
		String result = this.locateThumbnail(contentsBasePath, movieFileName);
		return result;
	}
	
	
	/**
	 * サムネイルを配置します。<BR>
	 * このメソッドは、ContentsIDがまだ存在しない写真のファイルについて、Thumbnailを配置するときに利用します。
	 * @param contentsGroupBasePath
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvalidStateException 
	 */
	public String locateThumbnail( String contentsGroupBasePath, String fileName ) throws FileNotFoundException, IOException, InvalidStateException {
		// 元のファイル
		String thumbnailImageName = applicationProperties.getString( "MOVIE_THUMBNAIL_IMAGE_NAME" );
		String destFilePath = applicationProperties.getString("LOCAL_INSTALL_PATH") + "/images/" + thumbnailImageName;
		
		// 動画のタイプを判別しておく
		String imageType = ImageUtils.getFormatName(destFilePath);
		
		// サムネイルの配置先ディレクトリを用意する
		String thumbnailDirPath	= contentsFileUtil.makeThumbnailDirPath( contentsGroupBasePath );
		String thumbnailPath	= contentsFileUtil.makeThumbnailPath( contentsGroupBasePath , fileName+"."+thumbnailImageName );
		FilePathUtil.prepareDirectory( thumbnailPath );

		// サムネイルを作る
		int width =  contentsFileUtil.getThumbnailWidth();
		int height = contentsFileUtil.getThumbnailHeight();

		BufferedImage bi = ImageUtils.createThumbnailImage(destFilePath, width, height);
		File thumbnailFile = new File( thumbnailPath );
		ImageIO.write(bi, imageType, thumbnailFile);

		return thumbnailPath;
	}


	/**
	 * 指定されたコンテンツIDのサムネイルを削除します
	 * @throws InvalidParameterException 
	 * @throws RecordNotFoundException 
	 */
	@Override
	public void removeThumbnail(Integer contentsID) throws RecordNotFoundException, InvalidParameterException {

		// ContentsEntityを取得する
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsID( contentsID );
		contentsEntity = contentsMapper.getContentsByContentsID( contentsEntity );
		Integer contentsGroupID = contentsEntity.getContentsGroupID();
		
		// サムネイルのマテリアルを取得する
		List<MaterialEntity> materialEntityList = dataStructureBusiness.getMaterialListByContentsID(contentsID);
		
		// パスを構築して削除
		String contentsGroupBasePath = contentsFileUtil.getContentsGroupBasePath(contentsGroupID, ContentsType.Movie);
		for( MaterialEntity materialEntity : materialEntityList ){
			// サムネイルでなければ無視
			Integer typeValue = Constants.MaterialType.MovieThumbnail.getValue();
			if( materialEntity.getMaterialType().intValue()!=typeValue.intValue()){
				continue;
			}
			
			// サムネイルのパスを作る
			String relativePath = materialEntity.getPath();
			String thumbnailPath = contentsGroupBasePath + "/" + relativePath;
			
			// 削除
			File file = new File( thumbnailPath );
			if( file.exists() ){
				file.delete();
			}
		}
	}
	

	/**
	 * 動画についての情報をDBに設定する。
	 * @param contentsGroupID
	 * @param filePathMap
	 * @param fileName
	 */
	private void registToDB( Integer contentsGroupID, Map<String,String> filePathMap, String fileName ) {
		Date currentDate = new Date();
		
		// 動画のDTO（Contents）を作る
		Integer contentsID = null;
		{
			contentsID = sequenceMapper.getNextContentsID();
			
			ContentsEntity contentsEntity = new ContentsEntity();
			contentsEntity.setContentsID(contentsID);
			contentsEntity.setContentsGroupID(contentsGroupID);
			contentsEntity.setContentsType( ContentsType.Movie.getValue() );
			contentsEntity.setName(fileName);
			contentsEntity.setBrief(null);
			contentsEntity.setDescription(null);
			contentsEntity.setBaseDir( filePathMap.get( "ContentsBasePath" ) );
			contentsEntity.setSeqNo(0);
			contentsEntity.setDeleteDate(null);
			contentsEntity.setUpdateDate(currentDate);
			contentsEntity.setCreateDate(currentDate);
			
			// コンテンツを新規に追加して、発行されたIDを取得する
			contentsMapper.insertContents(contentsEntity);
		}

		//　マテリアル（写真）のDTO
		{
			Integer materialID = null;
			materialID = sequenceMapper.getNextMaterialID();

			MaterialEntity materialEntity = new MaterialEntity();
			materialEntity.setMaterialID(materialID);
			materialEntity.setContentsID(contentsID);
			materialEntity.setMaterialType( Constants.MaterialType.Movie.getValue() );
			materialEntity.setPath( filePathMap.get("Movie") );
			materialEntity.setStatus( Constants.StatusCode.Normal.getValue() );
			materialEntity.setBrief(null);
			materialEntity.setDescription(null);
			materialEntity.setSeqNo(0);
			materialEntity.setDeleteDate(null);
			materialEntity.setUpdateDate(currentDate);
			materialEntity.setCreateDate(currentDate);
			
			// DBに書き込む
			materialMapper.insertMaterial(materialEntity);
		}

		//　マテリアル（サムネイル）のDTO
		{
			Integer materialID = null;
			materialID = sequenceMapper.getNextMaterialID();

			MaterialEntity materialEntity = new MaterialEntity();
			materialEntity.setMaterialID(materialID);
			materialEntity.setContentsID(contentsID);
			materialEntity.setMaterialType( Constants.MaterialType.MovieThumbnail.getValue() );
			materialEntity.setPath( filePathMap.get("Thumbnail") );
			materialEntity.setStatus( Constants.StatusCode.Normal.getValue() );
			materialEntity.setBrief(null);
			materialEntity.setDescription(null);
			materialEntity.setSeqNo(0);
			materialEntity.setDeleteDate(null);
			materialEntity.setUpdateDate(currentDate);
			materialEntity.setCreateDate(currentDate);
			
			// DBに書き込む
			materialMapper.insertMaterial(materialEntity);
		}
	}
}
