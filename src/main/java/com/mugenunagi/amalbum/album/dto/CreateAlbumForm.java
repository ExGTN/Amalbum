package com.mugenunagi.amalbum.album.dto;

public class CreateAlbumForm {
	/**
	 * アルバム名
	 */
	private String albumName;
	
	/**
	 * 概要
	 */
	private String brief;

	/**
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * @param albumName the albumName to set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}

	
}
