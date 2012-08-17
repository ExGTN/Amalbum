package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class ContentsEntityTest {

	/**
	 * ContentsIDのget/setのテスト
	 */
	@Test
	public void testSetContentsID() {
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsID( 111 );
		Integer contentsID = contentsEntity.getContentsID();
		
		assertEquals( "testSetContentsID", 111, contentsID.intValue() );
	}

	/**
	 * ContentsGroupIDのget/setのテスト
	 */
	@Test
	public void testSetContentsGroupID() {
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsGroupID( 112 );
		Integer contentsGroupID = contentsEntity.getContentsGroupID();

		assertEquals("testSetContentsGroupID", 112, contentsGroupID.intValue());
	}

	/**
	 * nameのset/getのテスト
	 */
	@Test
	public void testSetName() {
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setName( "SampleString1" );
		String name = contentsEntity.getName();

		assertEquals("testSetName", "SampleString1", name);
	}

	/**
	 * Briefのset/getのテスト
	 */
	@Test
	public void testSetBrief() {
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setBrief("SampleBrief");
		String brief = contentsEntity.getBrief();

		assertEquals( "testSetBrief", "SampleBrief", brief );
	}

	/**
	 * Descriptionのset/getのテスト
	 */
	@Test
	public void testSetDescription() {
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setDescription("SampleDescription");
		String description = contentsEntity.getDescription();

		assertEquals( "testSetDescription", "SampleDescription", description );
	}

	/**
	 * BaseDirのset/getのテスト
	 */
	@Test
	public void testSetBaseDir() {
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setBaseDir("SampleBaseDir");
		String baseDir = contentsEntity.getBaseDir();

		assertEquals( "testSetBaseDir", "SampleBaseDir", baseDir );
	}

	/**
	 * SeqNoテスト
	 */
	@Test
	public void testSetSeqNo() {
		ContentsEntity contentsEntity = new ContentsEntity();
		
		// Test for not null.
		contentsEntity.setSeqNo( new Integer(123) );
		Integer seqNo = contentsEntity.getSeqNo();
		assertEquals( "testSetSeqNo( not null )" , new Integer(123) , seqNo );

		// Test for null.
		contentsEntity.setSeqNo( null );
		seqNo = contentsEntity.getSeqNo();
		assertEquals( "testSetSeqNo( null )" , null , seqNo );
	}

	/**
	 * 削除日付のテスト
	 */
	@Test
	public void testSetGetDates() {
		ContentsEntity contentsEntity = new ContentsEntity();
		
		// Test for null.
		contentsEntity.setDeleteDate(null);
		contentsEntity.setCreateDate(null);
		contentsEntity.setUpdateDate(null);
		Date deleteDate = contentsEntity.getDeleteDate();
		Date createDate = contentsEntity.getCreateDate();
		Date updateDate = contentsEntity.getUpdateDate();
		assertEquals( "testSetDeleteDate:DeleteDate(null)", null, deleteDate );
		assertEquals( "testSetDeleteDate:CreateDate(null)", null, createDate );
		assertEquals( "testSetDeleteDate:UpdateDate(null)", null, updateDate );
		
		// Test for not null.
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 8, 12, 11, 3, 39);
		calendar.set(Calendar.MILLISECOND,0);

		Date targetDeleteDate = calendar.getTime();
		long millsDelete = calendar.getTimeInMillis();
		contentsEntity.setDeleteDate(targetDeleteDate);

		calendar.add(Calendar.DATE, 1);
		Date targetCreateDate = calendar.getTime();
		long millsCreate = calendar.getTimeInMillis();
		contentsEntity.setCreateDate(targetCreateDate);

		calendar.add(Calendar.DATE, 1);
		Date targetUpdateDate = calendar.getTime();
		long millsUpdate = calendar.getTimeInMillis();
		contentsEntity.setUpdateDate(targetUpdateDate);

		deleteDate = contentsEntity.getDeleteDate();
		createDate = contentsEntity.getCreateDate();
		updateDate = contentsEntity.getUpdateDate();
		assertEquals( "testSetDeleteDate(not null):Delete", millsDelete, deleteDate.getTime());
		assertEquals( "testSetCreateDate(not null):Create", millsCreate, createDate.getTime());
		assertEquals( "testSetUpdateDate(not null):Update", millsUpdate, updateDate.getTime());
	}
}
