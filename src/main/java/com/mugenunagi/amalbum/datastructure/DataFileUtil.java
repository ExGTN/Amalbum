package com.mugenunagi.amalbum.datastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

@Component
public class DataFileUtil {
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private DataStructureBusiness dataStructureBusiness;
	
	/**
	 * 指定されたコンテンツグループのベースパスを返します
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getContentsGroupBasePath( Integer contentsGroupID ) throws RecordNotFoundException{
		// ベースパスを取得する
		String localContentsBasePath		= applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );
		String photoRelativePath			= applicationProperties.getString( "PHOTO_RELATIVE_PATH" );
		String photoBasePath				= localContentsBasePath + "/" + photoRelativePath;
		
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
		if( photoBasePath.length()>0 ){
			if( contentsGroupPath.length()>0 ){
				contentsGroupPath = photoBasePath + "/" + contentsGroupPath;
			} else {
				contentsGroupPath = photoBasePath;
			}
		}
		
		return contentsGroupPath;
	}
	
	/**
	 * 指定されたコンテンツIDについてのベースパスを返す
	 * @param contentsID
	 * @return
	 * @throws RecordNotFoundException 
	 */
	public String getContentsBasePath( Integer contentsID ) throws RecordNotFoundException{
		// コンテンツの情報を得る
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);

		// 指定されたコンテンツIDが所属するコンテンツグループを辿ってパスを作る
		String contentsGroupPath = this.getContentsGroupBasePath( contentsEntity.getContentsGroupID() );
		
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
	 */
	public String getMaterialPathSingle( Integer contentsID, Integer materialType ) throws RecordNotFoundException{
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

	public String getMaterialPath( Integer materialID ) throws RecordNotFoundException{
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
	 * 一時ファイルの格納先のパスを返します
	 * @return
	 */
	public String getTempPath() {
		String tempDirPath	= applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" )
							+ "/"
							+ applicationProperties.getString( "PHOTO_TEMP_RELATIVE_PATH" );
		return tempDirPath;
	}
}
