package com.mugenunagi.amalbum.album.dto;

import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;

/**
 * アルバムセクションの一覧表示のDTO
 * @author GTN
 *
 */
public class ViewAlbumPageDTO {
	//=========================================================================
	// 属性
	//=========================================================================
	/**
	 * AlbumPage のDTO
	 */
	private AlbumPageDTO albumPageDTO;

	/**
	 * 編集モードか否か
	 */
	private boolean editMode = false;
	
	//=========================================================================
	// アクセサ
	//=========================================================================
	/**
	 * @return the albumPageDTO
	 */
	public AlbumPageDTO getAlbumPageDTO() {
		return albumPageDTO;
	}

	/**
	 * @param albumPageDTO the albumPageDTO to set
	 */
	public void setAlbumPageDTO(AlbumPageDTO albumPageDTO) {
		this.albumPageDTO = albumPageDTO;
	}

	/**
	 * 編集モードか否かを設定する
	 * @param editMode
	 */
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	/**
	 * 編集モードか否かを取得する
	 * @param editMode
	 */
	public boolean isEditMode() {
		return editMode;
	}

}
