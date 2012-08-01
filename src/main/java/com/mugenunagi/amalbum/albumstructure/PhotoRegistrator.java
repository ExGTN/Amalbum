package com.mugenunagi.amalbum.albumstructure;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.applicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
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
import com.mugenunagi.gtnlib.io.FileUtils;

/**
 * 写真を登録する機能を提供するクラス
 * @author GTN
 *
 */
@Component
public class PhotoRegistrator {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	private SequenceMapper sequenceMapper;
	
	@Autowired
	private ContentsMapper contentsMapper;
	
	@Autowired
	private MaterialMapper materialMapper;
	
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
		String localContentsBasePath		= applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );

		String photoRelativePath			= applicationProperties.getString( "PHOTO_RELATIVE_PATH" );
		String photoBasePath				= localContentsBasePath + "/" + photoRelativePath;

		String photoThumbnailRelativePath	= applicationProperties.getString("PHOTO_THUMBNAIL_RELATIVE_PATH");
		String photoThumbnailPrefix			= applicationProperties.getString("PHOTO_THUMBNAIL_PREFIX");
		String photoThumbnailWidth			= applicationProperties.getString("PHOTO_THUMBNAIL_WIDTH");
		String photoThumbnailHeight			= applicationProperties.getString("PHOTO_THUMBNAIL_HEIGHT");
		
		// アルバムページの情報を取得する
		ContentsGroupEntity albumPageInfo = dataStructureBusiness.getContentsGroup(contentsGroupID);
		if( albumPageInfo==null ){
			throw new RecordNotFoundException( "指定されたContentsGroupIDのレコードが存在しませんでした。ContentsGroupID="+contentsGroupID );
		}

		// YYYYMMDDディレクトリを作る
		String albumPageName = albumPageInfo.getName();
		if( albumPageName==null ){
			throw new InvalidStateException( "アルバムページのNameがnullです。contentsGroupID="+contentsGroupID );
		}
		FileUtils.prepareDirectory( photoBasePath , albumPageName );

		// 画像ファイルを配置する
		String destFilePath = FileUtils.makeUniqueFileName( photoBasePath + "/" + albumPageName + "/" + fileName );
		File serverFile = new File( destFilePath );
		String serverFileName = serverFile.getName();
		fileName = serverFileName;
		
		String destRelativePath = photoRelativePath + "/" + albumPageName + "/" + fileName;
		File destFile = new File( destFilePath );
		tempFile.renameTo( destFile );
		
		// 画像のタイプを判別しておく
		String imageType = ImageUtils.getFormatName(destFilePath);
		
		// サムネイルの配置先ディレクトリを用意する
		String albumPagePath = photoBasePath + "/" + albumPageName;
		FileUtils.prepareDirectory( albumPagePath, photoThumbnailRelativePath );

		// サムネイル画像のフルパスを作る
		String thumbnailDirPath = albumPagePath + "/" + photoThumbnailRelativePath;
		String thumbnailName = photoThumbnailPrefix + "." + fileName;
		String thumbnailPath = thumbnailDirPath + "/" + thumbnailName;
		String thumbnailRelativePath = photoRelativePath + "/" + albumPageName + "/" + photoThumbnailRelativePath + "/" + thumbnailName;
		
		// サムネイルを作る
		int width = Integer.parseInt(photoThumbnailWidth);
		int height = Integer.parseInt(photoThumbnailHeight);
		BufferedImage bi = ImageUtils.createThumbnailImage(destFilePath, width, height);
		File thumbnailFile = new File( thumbnailPath );
		ImageIO.write(bi, imageType, thumbnailFile);

		// ファイルパスを返す
		Map<String,String> result = new HashMap<String,String>();
		result.put( "ContentsBasePath", photoRelativePath+"/"+albumPageName );
		result.put( "Photo", fileName);
		result.put( "Thumbnail", photoThumbnailRelativePath + "/" + thumbnailName);
		result.put( "ServerFileName", serverFileName );
		return result;
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
}
