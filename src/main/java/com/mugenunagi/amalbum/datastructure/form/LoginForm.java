package com.mugenunagi.amalbum.datastructure.form;

public class LoginForm {
	String userID;
	String passwordMD5;

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the password
	 */
	public String getPasswordMD5() {
		return passwordMD5;
	}
	/**
	 * @param password the password to set
	 */
	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}
}
