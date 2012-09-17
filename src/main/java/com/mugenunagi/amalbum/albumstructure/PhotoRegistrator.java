package com.mugenunagi.amalbum.albumstructure;

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
import com.mugenunagi.amalbum.datastructure.DataFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.dao.ContentsMapper;
import com.mugenunagi.amalbum.datastructure.dao.MaterialMapper;
import com.mugenunagi.amalbum.datastructure.dao.SequenceMapper;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
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
public class PhotoRegistrator {
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
	private PhotoFileUtil photoFileUtil;
	
	@Autowired
	private DataFileUtil dataFileUtil;

	
	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 指定されたcontentsGroupIDのアルバムページに、tempFileの写真を追加します
	 * @throws RecordNotFoundException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 */
	public String registPhoto( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException {
		// 写真ファイルを配置する
		Map<String,String> filePathMap = this.locatePhotoFiles(contentsGroupID, tempFile, fileName);
		fileName = filePathMap.get( "ServerFileName" );
		
		// DBに登録する
		registToDB( contentsGroupID, filePathMap, fileName );
		
		// 実際に登録されたファイル名を返す
		return fileName;
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
	 */
	public Map<String,String> locatePhotoFiles( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException {
		
		// アルバムページの情報を取得する
		String contentsGroupBasePath = dataFileUtil.getContentsGroupBasePath(contentsGroupID);

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
	 */
	public String locateThumbnail( Integer contentsID ) throws RecordNotFoundException, FileNotFoundException, InvalidStateException, IOException{
		
		String contentsBasePath = dataFileUtil.getContentsBasePath(contentsID);
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
		FilePathUtil.prepareDirectory( thumbnailPath );

		// サムネイルを作る
		int width =  photoFileUtil.getThumbnailWidth();
		int height = photoFileUtil.getThumbnailHeight();
		BufferedImage bi = ImageUtils.createThumbnailImage(destFilePath, width, height);
		File thumbnailFile = new File( thumbnailPath );
		ImageIO.write(bi, imageType, thumbnailFile);

		return thumbnailPath;
	}
	

	/**
	 * 写真についての情報をDBに設定する。
	 * @param contentsGroupID
	 * @param filePathMap
	 * @param fileName
	 */
	private void registToDB( Integer contentsGroupID, Map<String,String> filePathMap, String fileName ) {
		Date currentDate = new Date();
		
		// 写真のDTO（Contents）を作る
		Integer contentsID = null;
		{
			contentsID = sequenceMapper.getNextContentsID();
			
			ContentsEntity contentsEntity = new ContentsEntity();
			contentsEntity.setContentsID(contentsID);
			contentsEntity.setContentsGroupID(contentsGroupID);
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
			materialEntity.setMaterialType( Constants.MaterialType.Photo.getValue() );
			materialEntity.setPath( filePathMap.get("Photo") );
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
			materialEntity.setMaterialType( Constants.MaterialType.Thumbnail.getValue() );
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

	/**
	 * 写真を削除します。物理ファイルとＤＢの両方が削除されます。
	 * @param photoID
	 * @throws Throwable
	 */
	public void removePhoto( Integer photoID ) throws Throwable{
		// ファイルを削除する
		removePhotoFiles( photoID );
		
		// DBから削除する
		this.removeFromDB(photoID);
	}
	
	/**
	 * 指定されたcontentsIDに関係する物理ファイルを削除します。ＤＢは削除されません。
	 * @param contentsID
	 * @throws Throwable
	 */
	public void removePhotoFiles( Integer contentsID ) throws Throwable {
		// マテリアルの情報を得る
		List<MaterialEntity> materialList = materialMapper.selectMaterialByContentsID(contentsID);
		
		// ファイルを削除する
		for( MaterialEntity material : materialList ){
			// 配置された写真のファイルを消す
			Integer materialID = material.getMaterialID();
			String materialPath = dataFileUtil.getMaterialPath(materialID);
			if( materialPath==null ){ continue; }
			File placedFile = new File( materialPath );
			if( placedFile.exists() ){ placedFile.delete(); }
		}

		// ディレクトリが空なら削除する
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
		Integer contentsGroupID = contentsEntity.getContentsGroupID();
		String albumPageDirPath	= dataFileUtil.getContentsGroupBasePath(contentsGroupID);
		String thumbDirPath		= photoFileUtil.getContentsGroupThumbnailPath(contentsGroupID);
		File thumbDir = new File( thumbDirPath );
		File albumPageDir = new File( albumPageDirPath );
		if( thumbDir.exists() ){
			String[] fileList = thumbDir.list();
			if( fileList.length==0 ){
				thumbDir.delete();
			}
		}
		if( albumPageDir.exists() ){
			String[] fileList = albumPageDir.list();
			if( fileList.length==0 ){
				albumPageDir.delete();
			}
		}
	}

	/**
	 * 写真のIDを指定して、写真の情報をＤＢから削除します。物理ファイルは削除されません。
	 * @param photoID
	 */
	public void removeFromDB( Integer photoID ){
		dataStructureBusiness.deleteWholeContents(photoID);
	}
	
	/**
	 * 指定されたコンテンツIDの写真を回転させます
	 * @param direction
	 * @param contentsID
	 * @throws RecordNotFoundException 
	 * @throws IOException 
	 * @throws InvalidStateException 
	 */
	public void rotateImage( PhotoRegistrator.RotateDirection direction, Integer contentsID ) throws RecordNotFoundException, IOException, InvalidStateException{
    	// 回転もとファイルを一時ディレクトリに複製する
    	String photoPath = photoFileUtil.getPhotoFilePath( contentsID );
    	String thumbPath = photoFileUtil.getThumbnailFilePath( contentsID );
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
    	ImageIO.write(bi, formatName, tempFile);
    	
    	// 回転した画像を書き戻す
    	FileUtils.copyFile( tempFile, photoFile );
    	tempFile.delete();
    	
    	// サムネイルを作りなおす
    	locateThumbnail( contentsID );
	}
}
