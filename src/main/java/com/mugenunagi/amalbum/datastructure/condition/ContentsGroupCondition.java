package com.mugenunagi.amalbum.datastructure.condition;

public class ContentsGroupCondition {
	private Integer contentsGroupID;
	private Integer parentID;
	private Integer limit;
	private Integer offset;
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
	 * @return the parentID
	 */
	public Integer getParentID() {
		return parentID;
	}
	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}
	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}
