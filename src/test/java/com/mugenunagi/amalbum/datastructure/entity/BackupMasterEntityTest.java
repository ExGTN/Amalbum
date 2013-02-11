package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class BackupMasterEntityTest {

	@Test
	public void testFields() {
		BackupMasterEntity entity = new BackupMasterEntity();

		Integer backupType	= 1;	// バックアップタイプ
		String name	= "SampleName";	// バックアップ名
		
		// set
		entity.setBackupType(backupType.intValue());
		entity.setName(new String(name));
		
		// get
		assertEquals("BackupMaster", backupType, entity.getBackupType());
		assertEquals("BackupMaster", name, entity.getName() );
	}
}
