package com.mugenunagi.amalbum.datastructure.entity;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class MaterialEntityTest {

	@Test
	public void testGetMaterialID() {
		MaterialEntity materialEntity = new MaterialEntity();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 8, 12, 12, 18, 54);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.add(Calendar.DATE, 1);
		long deleteMil = calendar.getTime().getTime();

		calendar.add(Calendar.DATE, 2);
		long createMil = calendar.getTime().getTime();

		calendar.add(Calendar.DATE, 3);
		long updateMil = calendar.getTime().getTime();
		
		Integer materialID		= 123;					// マテリアルID
		Integer contentsID		= 321;					// コンテンツID
		Integer materialType	= 5;					// タイプ
		String path				= "SamplePath";			// ファイルパス
		Integer status			= 6;					// ステータス
		String brief			= "SampleBrief";		// 説明
		String description		= "SampleDescription";	// 詳細説明
		Integer seqNo			= 7;					// 表示順
		Date deleteDate			= new Date(deleteMil);	// 削除日付
		Date updateDate			= new Date(createMil);	// 更新日付
		Date createDate			= new Date(updateMil);	// 作成日付

		// set
		materialEntity.setMaterialID(materialID.intValue());
		materialEntity.setContentsID(contentsID.intValue());
		materialEntity.setMaterialType(materialType.intValue());
		materialEntity.setPath( new String(path) );
		materialEntity.setStatus(status.intValue());
		materialEntity.setBrief( new String(brief) );
		materialEntity.setDescription( new String(description) );
		materialEntity.setSeqNo(seqNo.intValue());
		materialEntity.setDeleteDate(new Date(deleteMil));
		materialEntity.setCreateDate(new Date(createMil));
		materialEntity.setUpdateDate(new Date(updateMil));
		
		// get
		assertEquals( "MaterialEntity", materialID, materialEntity.getMaterialID() );
		assertEquals( "MaterialEntity", contentsID, materialEntity.getContentsID() );
		assertEquals( "MaterialEntity", materialType, materialEntity.getMaterialType() );
		assertEquals( "MaterialEntity", path, materialEntity.getPath() );
		assertEquals( "MaterialEntity", status, materialEntity.getStatus() );
		assertEquals( "MaterialEntity", brief, materialEntity.getBrief() );
		assertEquals( "MaterialEntity", description, materialEntity.getDescription() );
		assertEquals( "MaterialEntity", seqNo, materialEntity.getSeqNo() );
		assertEquals( "MaterialEntity", deleteMil, materialEntity.getDeleteDate().getTime() );
		assertEquals( "MaterialEntity", createMil, materialEntity.getCreateDate().getTime() );
		assertEquals( "MaterialEntity", updateMil, materialEntity.getUpdateDate().getTime() );
	}

}
