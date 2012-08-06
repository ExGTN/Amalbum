package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

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

	@Test
	public void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBaseDir() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetBaseDir() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSeqNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSeqNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeleteDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDeleteDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUpdateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUpdateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCreateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCreateDate() {
		fail("Not yet implemented");
	}

}
