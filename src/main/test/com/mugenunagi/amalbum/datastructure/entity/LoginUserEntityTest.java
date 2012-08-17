package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class LoginUserEntityTest {

	@Test
	public void testFields() {
		LoginUserEntity entity = new LoginUserEntity();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 8, 12, 20, 53, 12);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.add(Calendar.DATE, 1);
		long deleteMil	= calendar.getTime().getTime();
		long createMil	= calendar.getTime().getTime();
		long updateMil	= calendar.getTime().getTime();

		String userID		= "SmapleUserID";	// ユーザID
		String encPassword	= "SampleMD5";		// 暗号化パスワード
		String userName		= "SampleUserName";	// ユーザ名
		String mailAddress	= "SampleMailAddress";	// メールアドレス
		Integer seqNo		= 1;	// 表示順
		
		// set
		entity.setUserID(new String(userID));
		entity.setEncPassword(new String(encPassword));
		entity.setUserName(new String(userName));
		entity.setMailAddress(new String(mailAddress));
		entity.setSeqNo(seqNo.intValue());
		entity.setDeleteDate( new Date(deleteMil) );
		entity.setUpdateDate( new Date(updateMil) );
		entity.setCreateDate( new Date(createMil) );
		
		// get
		assertEquals( "LoginUserEntityTest", userID, entity.getUserID() );
		assertEquals( "LoginUserEntityTest", encPassword, entity.getEncPassword() );
		assertEquals( "LoginUserEntityTest", userName, entity.getUserName() );
		assertEquals( "LoginUserEntityTest", mailAddress, entity.getMailAddress() );
		assertEquals( "LoginUserEntityTest", seqNo, entity.getSeqNo() );
		assertEquals( "LoginUserEntityTest", deleteMil, entity.getDeleteDate().getTime() );
		assertEquals( "LoginUserEntityTest", updateMil, entity.getUpdateDate().getTime() );
		assertEquals( "LoginUserEntityTest", createMil, entity.getCreateDate().getTime() );
	}

}
