package com.mugenunagi.amalbum.album.datamodel.dto.parts;

import java.util.List;

import com.mugenunagi.applicationProperties;
import com.mugenunagi.amalbum.albumstructure.datamodel.dto.element.AlbumContents;

/**
 * アルバムコンテンツの一覧表示パーツのDTO
 * @author GTN
 *
 */
public class AlbumContentsListPartsDTO {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * ローカルのコンテンツのベースパス
	 */
	private String localContentsBasePath;

	/**
	 * AlbumContents の一覧
	 */
	private List<AlbumContents> albumContentsList;


	//=========================================================================
	// コンストラクタ
	//=========================================================================
	public AlbumContentsListPartsDTO(){
		localContentsBasePath = applicationProperties.getString( "localContentsBasePath" );
	}

	
	//=========================================================================
	// アクセサ
	//=========================================================================
	/**
	 * @return the albumContentsList
	 */
	public List<AlbumContents> getAlbumContentsList() {
		return albumContentsList;
	}

	/**
	 * @param albumContentsList the albumGroupEntityList to set
	 */
	public void setAlbumContentsList(List<AlbumContents> albumContentsList) {
		this.albumContentsList = albumContentsList;
	}

	
	/**
	 * @return the localContentsBasePath
	 */
	public String getLocalContentsBasePath() {
		return localContentsBasePath;
	}

	/**
	 * @param localContentsBasePath the localContentsBasePath to set
	 */
	public void setLocalContentsBasePath(String localContentsBasePath) {
		this.localContentsBasePath = localContentsBasePath;
	}
}
