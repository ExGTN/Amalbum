package com.mugenunagi.amalbum.album.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class EditAlbumPagePropertyForm {
	private Integer contentsGroupID;
	private String brief;
	private String description;

	/**
	 * @return the contentsGroupID
	 */
	public Integer getContentsGroupID() {
		return contentsGroupID;
	}
	/**
	 * @param contentsGroupID the contentsGroupID to set
	 */
	public void setContentsGroupID(Integer contentsGroupID) {
		this.contentsGroupID = contentsGroupID;
	}
	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
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
}
