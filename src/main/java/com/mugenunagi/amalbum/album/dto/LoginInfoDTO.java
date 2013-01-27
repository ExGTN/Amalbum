package com.mugenunagi.amalbum.album.dto;

import java.util.Date;

import com.mugenunagi.amalbum.datastructure.form.LoginForm;

public class LoginInfoDTO {
	LoginForm loginForm = null;
	Date loginDate = null;
	/**
	 * @return the loginForm
	 */
	public LoginForm getLoginForm() {
		return loginForm;
	}
	/**
	 * @param loginForm the loginForm to set
	 */
	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}
	/**
	 * @return the loginDate
	 */
	public Date getLoginDate() {
		return loginDate;
	}
	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	
}
