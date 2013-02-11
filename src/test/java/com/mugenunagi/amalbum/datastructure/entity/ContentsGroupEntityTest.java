package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * ContentsGroupEntityのテスト
 * @author gtn
 *
 */
public class ContentsGroupEntityTest {

	/**
	 * フィールドへのset/getのテスト(not null)
	 */
	@Test
	public void testFieldsNotNull() {
		ContentsGroupEntity contentsGroupEntity = new ContentsGroupEntity();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 8, 12, 11, 27, 45);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.DATE, 1);
		long deleteDateMil	= calendar.getTime().getTime();

		calendar.add(Calendar.DATE, 1);
		long createDateMil	= calendar.getTime().getTime();

		calendar.add(Calendar.DATE, 1);
		long updateDateMil	= calendar.getTime().getTime();

		Integer contentsGroupID	= 123;		// コンテンツグループID
		Integer parentID		= 321;		// 親コンテンツグループID
		String name				= "SampleName";				// コンテンツグループ名
		String brief			= "SampleBrief";				// 説明
		String description		= "SampleDescription";			// 詳細説明
		Integer seqNo			= 567;				// 表示順
		Date deleteDate = new Date( deleteDateMil );
		Date createDate = new Date( createDateMil );
		Date updateDate = new Date( updateDateMil );
		
		// セット
		contentsGroupEntity.setContentsGroupID(contentsGroupID.intValue());
		contentsGroupEntity.setParentID(parentID.intValue());
		contentsGroupEntity.setName(new String(name));
		contentsGroupEntity.setBrief(new String(brief));
		contentsGroupEntity.setDescription(new String(description));
		contentsGroupEntity.setSeqNo(seqNo.intValue());
		contentsGroupEntity.setDeleteDate(new Date(deleteDate.getTime()));
		contentsGroupEntity.setCreateDate(new Date(createDate.getTime()));
		contentsGroupEntity.setUpdateDate(new Date(updateDate.getTime()));
		
		// ゲット
		assertEquals("ContentsGroupEntityTest:not null", contentsGroupID, contentsGroupEntity.getContentsGroupID());
		assertEquals("ContentsGroupEntityTest:not null", parentID, contentsGroupEntity.getParentID());
		assertEquals("ContentsGroupEntityTest:not null", name, contentsGroupEntity.getName());
		assertEquals("ContentsGroupEntityTest:not null", brief, contentsGroupEntity.getBrief());
		assertEquals("ContentsGroupEntityTest:not null", description, contentsGroupEntity.getDescription());
		assertEquals("ContentsGroupEntityTest:not null", seqNo, contentsGroupEntity.getSeqNo());
		assertEquals("ContentsGroupEntityTest:not null", deleteDate.getTime(), contentsGroupEntity.getDeleteDate().getTime());
		assertEquals("ContentsGroupEntityTest:not null", createDate.getTime(), contentsGroupEntity.getCreateDate().getTime());
		assertEquals("ContentsGroupEntityTest:not null", updateDate.getTime(), contentsGroupEntity.getUpdateDate().getTime());
	}


	/**
	 * フィールドへのset/getのテスト(null)
	 */
	@Test
	public void testFieldsNull() {
		ContentsGroupEntity contentsGroupEntity = new ContentsGroupEntity();

		Integer contentsGroupID	= null;		// コンテンツグループID
		Integer parentID		= null;		// 親コンテンツグループID
		String name				= null;				// コンテンツグループ名
		String brief			= null;				// 説明
		String description		= null;			// 詳細説明
		Integer seqNo			= null;				// 表示順
		Date deleteDate = null;
		Date createDate = null;
		Date updateDate = null;
		
		// セット
		contentsGroupEntity.setContentsGroupID(contentsGroupID);
		contentsGroupEntity.setParentID(parentID);
		contentsGroupEntity.setName(name);
		contentsGroupEntity.setBrief(brief);
		contentsGroupEntity.setDescription(description);
		contentsGroupEntity.setSeqNo(seqNo);
		contentsGroupEntity.setDeleteDate(deleteDate);
		contentsGroupEntity.setCreateDate(createDate);
		contentsGroupEntity.setUpdateDate(updateDate);
		
		// ゲット
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getContentsGroupID());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getParentID());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getName());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getBrief());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getDescription());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getSeqNo());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getDeleteDate());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getCreateDate());
		assertNull("ContentsGroupEntityTest:null", contentsGroupEntity.getUpdateDate());
	}
}
