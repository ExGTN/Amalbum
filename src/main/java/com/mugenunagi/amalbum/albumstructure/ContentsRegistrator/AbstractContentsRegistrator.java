package com.mugenunagi.amalbum.albumstructure.ContentsRegistrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.dao.ContentsMapper;
import com.mugenunagi.amalbum.datastructure.dao.MaterialMapper;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

/**
 * コンテンツ登録処理の抽象クラス
 * @author gtn
 *
 */
@Component
public abstract class AbstractContentsRegistrator {
	//=========================================================================
	// DI
	//=========================================================================
	@Autowired ApplicationProperties applicationProperties;
	@Autowired DataStructureBusiness dataStructureBusiness;
	@Autowired MaterialMapper materialMapper;
	@Autowired ContentsMapper contentsMapper;
	@Autowired ContentsFileUtil contentsFileUtil;

	//=========================================================================
	// 抽象メソッド
	//=========================================================================
	/**
	 * コンテンツを登録します
	 * @throws InvalidParameterException 
	 */
	abstract public Integer regist( Integer contentsGroupID, File tempFile, String fileName ) throws RecordNotFoundException, InvalidStateException, IOException, InvalidParameterException;

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
	abstract public String locateThumbnail( Integer contentsID ) throws RecordNotFoundException, FileNotFoundException, InvalidStateException, IOException, InvalidParameterException;

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
	abstract public String locateThumbnail( String contentsGroupBasePath, String fileName ) throws FileNotFoundException, IOException, InvalidStateException;

	/**
	 * 指定したコンテンツについてのサムネイルを削除します。
	 * @param contentsID
	 * @throws InvalidParameterException 
	 * @throws RecordNotFoundException 
	 */
	abstract public void removeThumbnail( Integer contentsID ) throws RecordNotFoundException, InvalidParameterException;


	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * 写真を削除します。物理ファイルとＤＢの両方が削除されます。
	 * @param photoID
	 * @throws Throwable
	 */
	public void removeContents( Integer photoID ) throws Throwable{
		// ファイルを削除する
		removeContentsFiles( photoID );
		
		// DBから削除する
		this.removeFromDB(photoID);
	}
	
	/**
	 * 指定されたcontentsIDに関係する物理ファイルを削除します。ＤＢは削除されません。
	 * @param contentsID
	 * @throws Throwable
	 */
	protected void removeContentsFiles( Integer contentsID ) throws Throwable {
		// マテリアルの情報を得る
		List<MaterialEntity> materialList = materialMapper.selectMaterialByContentsID(contentsID);
		
		// ファイルを削除する
		for( MaterialEntity material : materialList ){
			// 配置された写真のファイルを消す
			Integer materialID = material.getMaterialID();
			String materialPath = contentsFileUtil.getMaterialPath(materialID);
			if( materialPath==null ){ continue; }
			File placedFile = new File( materialPath );
			if( placedFile.exists() ){ placedFile.delete(); }
		}
		
		// コメントファイルを削除する
		String contentsCommentPath = contentsFileUtil.getContentsCommentPath(contentsID);
		contentsFileUtil.deleteFileWithRemoveDir( contentsCommentPath );

		// ディレクトリが空なら削除する
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
		Integer contentsTypeValue = contentsEntity.getContentsType();
		ContentsType contentsType = Constants.ContentsTypeMap.get( contentsTypeValue );
		Integer contentsGroupID = contentsEntity.getContentsGroupID();
		String albumPageDirPath	= contentsFileUtil.getContentsGroupBasePath(contentsGroupID, contentsType);
		String thumbDirPath		= contentsFileUtil.getContentsGroupThumbnailPath(contentsGroupID, contentsType);
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
	protected void removeFromDB( Integer contentsID ){
		dataStructureBusiness.deleteWholeContents(contentsID);
	}
}
