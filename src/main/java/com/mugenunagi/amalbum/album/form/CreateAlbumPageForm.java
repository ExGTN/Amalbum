package com.mugenunagi.amalbum.album.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateAlbumPageForm {
	/**
	 * アルバムページ名
	 */
	//@Pattern(regexp="^.*[(\\|/|:|\*|?|\"|<|>|\|)].*$")
	@Pattern(regexp="^[a-z]+$",message="only a-z allowed.")
	private String name;
	
	/**
	 * アルバムID
	 */
	private Integer albumID;
	
	/**
	 * 戻りのパス
	 */
	private String returnPath;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the albumID
	 */
	public Integer getAlbumID() {
		return albumID;
	}
	/**
	 * @param albumID the albumID to set
	 */
	public void setAlbumID(Integer albumID) {
		this.albumID = albumID;
	}
	/**
	 * @return the returnPath
	 */
	public String getReturnPath() {
		return returnPath;
	}
	/**
	 * @param returnPath the returnPath to set
	 */
	public void setReturnPath(String returnPath) {
		this.returnPath = returnPath;
	}
	
}
