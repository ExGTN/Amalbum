package com.mugenunagi.amalbum.albumstructure;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.PhotoRegistrator;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.dao.ContentsGroupMapper;
import com.mugenunagi.amalbum.datastructure.dao.ContentsMapper;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class PhotoRegistratorTest {
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
	ContentsGroupMapper contentsGroupMapper;
	
	@Autowired
	ContentsMapper contentsMapper;
	
	@Autowired
	PhotoRegistrator photoRegistrator;
	
	@Autowired
	DataStructureBusiness dataStructureBusiness;
	
	@Test
	public void testRegistPhoto() throws Throwable {
		// 各種のパスの情報を取得する
		String testDataDir = applicationProperties.getString("TESTDATA_LOCATION");
		String basePath = applicationProperties.getString("LOCAL_CONTENTS_BASE_PATH");
		String fileName = applicationProperties.getString("TESTDATA_PHOTO_1");
		String tempName = applicationProperties.getString("CONTENTS_TEMP_RELATIVE_PATH");
		String photoDirName = applicationProperties.getString("PHOTO_RELATIVE_PATH");
		String thumbDirName = applicationProperties.getString("CONTENTS_THUMBNAIL_RELATIVE_PATH");
		String thumbPrefix = applicationProperties.getString("CONTENTS_THUMBNAIL_PREFIX");
		String pageName = "Test";

		String filePath = testDataDir + "/" + fileName;
		String tempPath = basePath + "/" + tempName + "/" + fileName;

		// -----< 登録 >-----
		//
		ContentsGroupEntity contentsGroupEntity = null;
		{
			// -----< テスト用にContentsGroupを作る >-----
			//
			Calendar calendar = Calendar.getInstance();
			calendar.set(2012, 8, 15, 23, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date date = calendar.getTime();
			
			Integer contentsGroupID = 1;
			contentsGroupEntity = new ContentsGroupEntity();
			contentsGroupEntity.setContentsGroupID( contentsGroupID );
			contentsGroupEntity.setParentID( 0 );
			contentsGroupEntity.setName( pageName );
			contentsGroupEntity.setBrief( "TestBrief" );
			contentsGroupEntity.setDescription( "TestDescription" );
			contentsGroupEntity.setSeqNo( 1 );
			contentsGroupEntity.setDeleteDate( null );
			contentsGroupEntity.setUpdateDate( date );
			contentsGroupEntity.setCreateDate( date );

			// 登録前の状態を取得しておく
			List<ContentsEntity> prevContentsList = contentsMapper.selectContentsByContentsGroupID(contentsGroupID);
			
			// 登録
			contentsGroupMapper.insertContentsGroup(contentsGroupEntity);
			
			// -----< テストデータを使ってTempFileを用意する >-----
			//
			File sourceFile = new File( filePath );
			File destFile = new File( tempPath );
			FileUtils.copyFile(sourceFile, destFile);
	
			// -----< 登録したContentsGroupにContentsを登録 >-----
			//
			photoRegistrator.regist(contentsGroupID, destFile, fileName);
	
			// -----< 確認 >-----
			//
			// 一時ファイルが存在しないこと
			File tempFile = new File( tempPath );
			assertFalse( "一時ファイルが存在しないこと", tempFile.exists() );
			
			// フルサイズとサムネイルの画像が存在すること
			String assertPath = basePath + "/" + photoDirName + "/" + pageName + "/" + fileName;
			String assertThumbPath = basePath + "/" + photoDirName + "/" + pageName + "/" + thumbDirName + "/" + thumbPrefix + "." + fileName;
			File assertFile = new File( assertPath );
			File assertThumbFile = new File( assertThumbPath );
			assertTrue( "画像が存在すること", assertFile.exists() );
			assertTrue( "サムネイルが存在すること", assertFile.exists() );
			
			// フルサイズ画像が複製もとと同じであること
			boolean fileSizeEquals = assertFile.length()==sourceFile.length();
			assertTrue( "ファイルサイズが一致", fileSizeEquals );

			// contentsが追加されていること
			List<ContentsEntity> afterContentsList = contentsMapper.selectContentsByContentsGroupID(contentsGroupID);
			assertEquals( "contentsが追加されていること", prevContentsList.size()+1, afterContentsList.size() );
		}
		
		// -----< 登録したものを削除 >-----
		//
		{
			// 作成した一時ファイルが消えていなければ消す
			File tempFile = new File( tempPath );
			if( tempFile.exists() ){ tempFile.delete(); }
			
			// コンテンツを削除する（ＤＢと物理ファイル両方）
			if( contentsGroupEntity!=null ){
				Integer contentsGroupID = contentsGroupEntity.getContentsGroupID();
				List<ContentsEntity> contentsList = dataStructureBusiness.getContentsListByParentID(contentsGroupID);

				for( ContentsEntity contents : contentsList ){
					if( contents==null ){ continue; }
					Integer contentsID = contents.getContentsID();
					photoRegistrator.removeContents(contentsID);
				}
			}
			
			// コンテンツグループを消す
			if( contentsGroupEntity!=null ){
				Integer contentsGroupID = contentsGroupEntity.getContentsGroupID();
				dataStructureBusiness.deleteWholeContentsGroup( contentsGroupID );
			}
		}
	}

	@Test
	public void testException() {
		// -----< 登録 >-----
		//
		// <<< ContentsGroup不在の例外 >>>
		try{
			photoRegistrator.locatePhotoFiles(99999999, new File("test"), "test");
		} catch (RecordNotFoundException e) {
			// 想定通り
		} catch (Exception e){
			// 想定しない例外
			fail("想定しない例外が発生しました。"+e.getMessage());
		}
	}
}
