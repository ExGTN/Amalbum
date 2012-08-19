package com.mugenunagi.amalbum.album.dto;

import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageListDTO;

/**
 * アルバムページの一覧表示のDTO
 * @author GTN
 *
 */
public class ViewAlbumPageListDTO {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * AlbumPageの一覧のDTO
	 */
	private AlbumPageListDTO albumPageListDTO;
	
	/**
	 * ベースURL
	 */
	private String baseURL;

	/**
	 * デフォルトのページ名
	 */
	private String defaultAlbumPageName;

	
	//=========================================================================
	// アクセサ
	//=========================================================================
	/**
	 * @return the albumPageListDTO
	 */
	public AlbumPageListDTO getAlbumPageListDTO() {
		return albumPageListDTO;
	}

	/**
	 * @param albumPageListDTO the albumPageListDTO to set
	 */
	public void setAlbumPageListDTO(AlbumPageListDTO albumPageListDTO) {
		this.albumPageListDTO = albumPageListDTO;
	}

	/**
	 * @param baseURL the baseURL to set
	 */
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	/**
	 * @return the baseURL
	 */
	public String getBaseURL() {
		return baseURL;
	}

	/**
	 * @return the defaultAlbumPageName
	 */
	public String getDefaultAlbumPageName() {
		return defaultAlbumPageName;
	}

	/**
	 * @param defaultAlbumPageName the defaultAlbumPageName to set
	 */
	public void setDefaultAlbumPageName(String defaultAlbumPageName) {
		this.defaultAlbumPageName = defaultAlbumPageName;
	}

}
