package com.mugenunagi.amalbum.albumstructure.ContentsRegistrator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
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
 * 写真を登録する機能を提供するクラス
 * @author GTN
 *
 */
@Component
public class PhotoRegistrator extends AbstractContentsRegistrator {
	//=========================================================================
	// static属性
	//=========================================================================
	public static enum RotateDirection{
		  RIGHT
		, LEFT
	}

	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
	DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	private SequenceMapper sequenceMapper;
	
	@Autowired
	private ContentsMapper contentsMapper;
	
	@Autowired
	private MaterialMapper materialMapper;
	
	@Autowired
	private ContentsFileUtil photoFileUtil;

	
	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 指定されたcontentsGroupIDのアルバムページに、tempFileの写真を追加します
	 * @throws RecordNotFoundException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws InvalidParameterException 
	 */
	public Integer regist( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException {
		// 写真ファイルを配置する
		Map<String,String> filePathMap = this.locatePhotoFiles(contentsGroupID, tempFile, fileName);
		fileName = filePathMap.get( "ServerFileName" );
		
		// DBに登録する
		Integer contentsID = registToDB( contentsGroupID, filePathMap, fileName );
		
		// 登録したコンテンツのコンテンツIDを返す
		return contentsID;
	}


	/**
	 * tempFileで指定された画像ファイルについて、アルバム用に画像を配置し、配置先のパス情報をMapで返します。
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
	public Map<String,String> locatePhotoFiles( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException {
		
		// アルバムページの情報を取得する
		String contentsGroupBasePath = contentsFileUtil.getContentsGroupBasePath(contentsGroupID, ContentsType.Photo);

		// YYYYMMDDディレクトリを作る
		FilePathUtil.prepareDirectory( contentsGroupBasePath );

		// 画像ファイルを配置する
		String destFilePath = FilePathUtil.makeUniqueFileName( contentsGroupBasePath + "/" + fileName );
		File serverFile = new File( destFilePath );
		String serverFileName = serverFile.getName();
		fileName = serverFileName;
		
		File destFile = new File( destFilePath );
		//tempFile.renameTo( destFile );
		FileUtils.copyFile( tempFile, destFile );
		tempFile.delete();
		
		// サムネイルを作る
		String thumbnailPath = locateThumbnail( contentsGroupBasePath, fileName );
		if( thumbnailPath.startsWith(contentsGroupBasePath)){
			thumbnailPath = thumbnailPath.substring( contentsGroupBasePath.length() );
			if( thumbnailPath.startsWith("/") ){
				thumbnailPath = thumbnailPath.substring(1);
			}
		}

		// ファイルパスを返す
		Map<String,String> result = new HashMap<String,String>();
		result.put( "ContentsBasePath", "" );
		result.put( "Photo", fileName);
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
		String photoFilePath = photoFileUtil.getPhotoFilePath(contentsID);
		File photo = new File( photoFilePath );
		String photoFileName = photo.getName();
		
		String result = this.locateThumbnail(contentsBasePath, photoFileName);
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
		// ファイル
		String destFilePath = contentsGroupBasePath + "/" + fileName;
		
		// 画像のタイプを判別しておく
		String imageType = ImageUtils.getFormatName(destFilePath);
		
		// サムネイルの配置先ディレクトリを用意する
		String thumbnailDirPath	= photoFileUtil.makeThumbnailDirPath( contentsGroupBasePath );
		String thumbnailPath	= photoFileUtil.makeThumbnailPath( contentsGroupBasePath , fileName );
		FilePathUtil.prepareDirectory( thumbnailDirPath );

		// サムネイルを作る
		int width =  photoFileUtil.getThumbnailWidth();
		int height = photoFileUtil.getThumbnailHeight();
		BufferedImage bi = ImageUtils.createThumbnailImage(destFilePath, width, height);

		// サムネイル画像を保存する
		File thumbnailFile = new File( thumbnailPath );
		ImageUtils.writeImage( bi, imageType, thumbnailFile );
		
		return thumbnailPath;
	}
	

	/**
	 * 写真についての情報をDBに設定する。
	 * @param contentsGroupID
	 * @param filePathMap
	 * @param fileName
	 */
	private Integer registToDB( Integer contentsGroupID, Map<String,String> filePathMap, String fileName ) {
		Date currentDate = new Date();
		
		// 写真のDTO（Contents）を作る
		Integer contentsID = null;
		{
			contentsID = sequenceMapper.getNextContentsID();
			Integer seqNo = sequenceMapper.getNextContentsSeqNo(contentsGroupID);
			if( seqNo==null ){ seqNo = 1; }
			
			ContentsEntity contentsEntity = new ContentsEntity();
			contentsEntity.setContentsID(contentsID);
			contentsEntity.setContentsGroupID(contentsGroupID);
			contentsEntity.setContentsType( ContentsType.Photo.getValue() );
			contentsEntity.setName(fileName);
			contentsEntity.setBrief(null);
			contentsEntity.setDescription(null);
			contentsEntity.setBaseDir( filePathMap.get( "ContentsBasePath" ) );
			contentsEntity.setSeqNo(seqNo);
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
			Integer seqNo = sequenceMapper.getNextMaterialSeqNo(contentsID);
			if( seqNo==null ){ seqNo = 1; }

			MaterialEntity materialEntity = new MaterialEntity();
			materialEntity.setMaterialID(materialID);
			materialEntity.setContentsID(contentsID);
			materialEntity.setMaterialType( Constants.MaterialType.Photo.getValue() );
			materialEntity.setPath( filePathMap.get("Photo") );
			materialEntity.setStatus( Constants.StatusCode.Normal.getValue() );
			materialEntity.setBrief(null);
			materialEntity.setDescription(null);
			materialEntity.setSeqNo(seqNo);
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
			Integer seqNo = sequenceMapper.getNextMaterialSeqNo(contentsID);
			if( seqNo==null ){ seqNo = 1; }

			MaterialEntity materialEntity = new MaterialEntity();
			materialEntity.setMaterialID(materialID);
			materialEntity.setContentsID(contentsID);
			materialEntity.setMaterialType( Constants.MaterialType.Thumbnail.getValue() );
			materialEntity.setPath( filePathMap.get("Thumbnail") );
			materialEntity.setStatus( Constants.StatusCode.Normal.getValue() );
			materialEntity.setBrief(null);
			materialEntity.setDescription(null);
			materialEntity.setSeqNo(seqNo);
			materialEntity.setDeleteDate(null);
			materialEntity.setUpdateDate(currentDate);
			materialEntity.setCreateDate(currentDate);
			
			// DBに書き込む
			materialMapper.insertMaterial(materialEntity);
		}
		
		return contentsID;
	}

	
	/**
	 * 指定されたコンテンツIDの写真を回転させます
	 * @param direction
	 * @param contentsID
	 * @throws RecordNotFoundException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 * @throws InvalidParameterException 
	 */
	public void rotateImage( PhotoRegistrator.RotateDirection direction, Integer contentsID ) throws RecordNotFoundException, IOException, InvalidStateException, InvalidParameterException{
    	// 回転もとファイルを一時ディレクトリに複製する
    	String photoPath = photoFileUtil.getPhotoFilePath( contentsID );
    	String thumbPath = contentsFileUtil.getThumbnailFilePath( contentsID );
    	String tempDirPath = photoFileUtil.getTempPath();
    	
    	File photoFile = new File( photoPath );
    	File thumbnailFile = new File( thumbPath );
    	String tempFilePath = tempDirPath + "/" + photoFile.getName();
    	File tempFile = new File( tempFilePath );
    	
    	FileUtils.copyFile( photoFile, tempFile );
    	
    	// サムネイルは削除しておく
    	thumbnailFile.delete();
    	
    	// 回転処理を行う
		String formatName = ImageUtils.getFormatName(tempFilePath);
		BufferedImage bi = null;
    	if( direction==PhotoRegistrator.RotateDirection.LEFT ){
    		bi = ImageUtils.createRotateImage(tempFilePath, 90.0);
    	} else {
    		bi = ImageUtils.createRotateImage(tempFilePath, -90.0);
    	}
    	ImageUtils.writeImage(bi, formatName, tempFile);
    	
    	// 回転した画像を書き戻す
    	FileUtils.copyFile( tempFile, photoFile );
    	tempFile.delete();
    	
    	// サムネイルを作りなおす
    	locateThumbnail( contentsID );
	}


	/**
	 * 指定されたコンテンツIDのサムネイルを削除します
	 * @throws InvalidParameterException 
	 * @throws RecordNotFoundException 
	 */
	@Override
	public void removeThumbnail(Integer contentsID)
			throws RecordNotFoundException, InvalidParameterException {


		// ContentsEntityを取得する
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsID( contentsID );
		contentsEntity = contentsMapper.getContentsByContentsID( contentsEntity );
		Integer contentsGroupID = contentsEntity.getContentsGroupID();
		
		// サムネイルのマテリアルを取得する
		List<MaterialEntity> materialEntityList = dataStructureBusiness.getMaterialListByContentsID(contentsID);
		
		// パスを構築して削除
		String contentsGroupBasePath = contentsFileUtil.getContentsGroupBasePath(contentsGroupID, ContentsType.Photo);
		for( MaterialEntity materialEntity : materialEntityList ){
			// サムネイルでなければ無視
			Integer typeValue = Constants.MaterialType.Thumbnail.getValue();
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
}
