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
	 * 現在のページ番号
	 */
	private Integer page;
	
	/**
	 * 前のページ番号
	 */
	private Integer prevPage;

	/**
	 * 次のページ番号
	 */
	private Integer nextPage;
	
	/**
	 * １ページに表示する項目数
	 */
	private Integer pagingUnit;
	
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
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the prevPage
	 */
	public Integer getPrevPage() {
		return prevPage;
	}

	/**
	 * @param prevPage the prevPage to set
	 */
	public void setPrevPage(Integer prevPage) {
		this.prevPage = prevPage;
	}

	/**
	 * @return the nextPage
	 */
	public Integer getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage the nextPage to set
	 */
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return the pagingUnit
	 */
	public Integer getPagingUnit() {
		return pagingUnit;
	}

	/**
	 * @param pagingUnit the pagingUnit to set
	 */
	public void setPagingUnit(Integer pagingUnit) {
		this.pagingUnit = pagingUnit;
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
