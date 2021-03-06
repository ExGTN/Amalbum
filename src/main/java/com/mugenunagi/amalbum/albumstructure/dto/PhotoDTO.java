package com.mugenunagi.amalbum.albumstructure.dto;

import java.io.File;

/**
 * アルバム内に表示される１枚の写真を取り扱うDTO
 * @author gtn
 *
 */
public class PhotoDTO {
	private int contentsType;
	private int contentsID;
	private int materialID;
	private String description;
	private String path;

	/**
	 * @return the contentsType
	 */
	public int getContentsType() {
		return contentsType;
	}
	/**
	 * @param contentsType the contentsType to set
	 */
	public void setContentsType(int contentsType) {
		this.contentsType = contentsType;
	}
	/**
	 * @return the contentsID
	 */
	public int getContentsID() {
		return contentsID;
	}
	/**
	 * @param contentsID the contentsID to set
	 */
	public void setContentsID(int contentsID) {
		this.contentsID = contentsID;
	}
	/**
	 * @return the materialID
	 */
	public int getMaterialID() {
		return materialID;
	}
	/**
	 * @param materialID the materialID to set
	 */
	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * パスのなかのファイル名の部分を返す
	 * @return
	 */
	public String getFilename() {
		if( path==null ){
			return null;
		}
		
		File file =new File( path );
		String filename = file.getName();
		return filename;
	}
}
