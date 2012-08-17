package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class BackupRuleEntityTest {

	@Test
	public void testFields() {
		BackupRuleEntity entity = new BackupRuleEntity();

		Integer backupRuleID	= 1;	// バックアップルールID
		Integer backupType		= 2;	// バックアップタイプ
		Integer year			= 2012;	// 年
		Integer month			= 4;	// 月
		Integer day				= 5;	// 日
		Integer hour			= 6;	// 時
		Integer minute			= 7;	// 分
		
		// set
		entity.setBackupRuleID(backupRuleID.intValue());
		entity.setBackupType(backupType.intValue());
		entity.setYear(year.intValue());
		entity.setMonth(month.intValue());
		entity.setDay(day.intValue());
		entity.setHour(hour.intValue());
		entity.setMinute(minute.intValue());
		
		// get
		assertEquals( "BackupRuleEntity", backupRuleID, entity.getBackupRuleID() );
		assertEquals( "BackupRuleEntity", backupType, entity.getBackupType() );
		assertEquals( "BackupRuleEntity", year, entity.getYear() );
		assertEquals( "BackupRuleEntity", month, entity.getMonth() );
		assertEquals( "BackupRuleEntity", day, entity.getDay() );
		assertEquals( "BackupRuleEntity", hour, entity.getHour() );
		assertEquals( "BackupRuleEntity", minute, entity.getMinute() );
	}

}
