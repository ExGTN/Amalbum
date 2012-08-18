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

}
