package com.mugenunagi.amalbum.album;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageListDTO;
import com.mugenunagi.amalbum.album.form.CreateAlbumPageForm;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.form.LoginForm;
import com.mugenunagi.amalbum.exception.InvalidParameterException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"classpath:amalbum-servlet-test.xml"} )
public class AlbumControllerTest {
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
	AlbumController albumController;
	
	@Autowired
	DataStructureBusiness dataStructureBusiness;

	/**
	 * ログイン画面のテスト
	 */
	@Test
	public void testLogin() {
		// <<< パラメータを用意する >>>
		// モックオブジェクト
		MockHttpServletRequest request = new MockHttpServletRequest();
		BindingResult result = Mockito.mock(BindingResult.class);
		Mockito.when(result.hasErrors()).thenReturn(false);
		
		// 通常のオブジェクト
		LoginForm loginForm = new LoginForm();
		ModelMap map = new ModelMap();
		
		// <<< テスト実行 >>>
		String jspName = albumController.login(request, loginForm, result, map);
		
		// <<< 確認 >>>
		assertEquals( "ログイン画面", "site/login", jspName );
	}
	
	/**
	 * ファイルアップロードのテスト
	 * @throws Throwable 
	 */
	@Test
	public void testUploadFile() throws Throwable {
		// ----< 準備 >-----
		//
		Integer albumID = 0;
		Date date = new Date();
		long timeValue = date.getTime();
		String albumPageNameForTest = "TestPageToUploadFile"+timeValue;
		ContentsGroupEntity albumEntity = dataStructureBusiness.getContentsGroup( albumID );
		String albumName = albumEntity.getName();

		// テスト用ファイルのフルパスを作る
		String testDataLocation = applicationProperties.getString("TESTDATA_LOCATION");
		String testDataFilename = applicationProperties.getString("TESTDATA_PHOTO_1");
		String testDataFilePath = testDataLocation + "/" + testDataFilename; 

		// ファイルアップロード先のフルパスを作る
		String targetLocation = applicationProperties.getString("LOCAL_CONTENTS_BASE_PATH");
		String photoRelativePath = applicationProperties.getString("PHOTO_RELATIVE_PATH");
		String thumbnailRelativePath = applicationProperties.getString("CONTENTS_THUMBNAIL_RELATIVE_PATH");
		String thumbnailPrefix = applicationProperties.getString("CONTENTS_THUMBNAIL_PREFIX");

		String targetFilePath = targetLocation + "/" + photoRelativePath + "/" + albumName + "/" + albumPageNameForTest + "/" + testDataFilename;
		String thumbnailPath = targetLocation + "/" + photoRelativePath + "/" + albumName + "/" + albumPageNameForTest + "/" + thumbnailRelativePath + "/" + thumbnailPrefix +"." + testDataFilename;
		
		// モックオブジェクトを作る
		FileInputStream inputStream = new FileInputStream( testDataFilePath );
		MockMultipartFile uploadFile = new MockMultipartFile( testDataFilePath , testDataFilename , "image/jpeg" , inputStream );
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		// テスト用オブジェクトを作る
		ModelMap map = new ModelMap();
		String returnPath = "http://www.mugenunagi.com/amalbum/login.do";
			
		// アルバムページを作る
		Integer albumPageID = dataStructureBusiness.createContentsGroup( albumID , albumPageNameForTest );
		
		// ----< アップロードする >-----
		//
		{
			String jspName = albumController.uploadFile(request, albumPageID, uploadFile, null, null, null, null, returnPath, map);
			
			// 確認する
			assertEquals( "ファイルupload", "site/fileUploaded", jspName );
			String mapRetPath = (String)( map.get("returnPath") );
			assertEquals( "リターンパス", returnPath, mapRetPath );
			
			File targetFile = new File( targetFilePath );
			File thumbnailFile = new File( thumbnailPath );
			assertTrue( "TargetFile" , targetFile.exists() );
			assertTrue( "ThumbnailFile" , thumbnailFile.exists() );
		}
		
		
		// -----< アルバムページリストを表示する >-----
		//
		{
			CreateAlbumPageForm createAlbumPageForm = null;
			BindingResult result = Mockito.mock(BindingResult.class);
			Mockito.when(result.hasErrors()).thenReturn(false);
			int page = 0;
			ModelMap modelMap = new ModelMap();

			String jspName = albumController.viewAlbumPageList(request, createAlbumPageForm, result, albumID, page, modelMap);
			
			assertEquals("viewAlbumPageList", "site/viewAlbumPageList", jspName );

		  	ViewAlbumPageListDTO viewAlbumPageListDTO = (ViewAlbumPageListDTO)modelMap.get( "viewAlbumPageListDTO" );
		  	createAlbumPageForm = (CreateAlbumPageForm)( modelMap.get( "createAlbumPageForm" ) );
		  	
		  	assertNotNull( "createAlbumPageForm", createAlbumPageForm );
		  	List<ContentsGroupEntity> albumPageList = viewAlbumPageListDTO.getAlbumPageListDTO().getAlbumPageList();
		  	boolean nameExists = false;
		  	for( ContentsGroupEntity albumPage : albumPageList){
		  		String name = albumPage.getName();
		  		if( albumPageNameForTest.equals( name ) ){
		  			nameExists = true;
		  		}
		  	}
		  	assertTrue( "Registed Album Page exists", nameExists );
		}
		
		// -----< アルバムページを表示する >-----
		//
		{
			ModelMap modelMap = new ModelMap();
			String jspName = albumController.viewAlbumPage(request, albumPageID, null, "true", modelMap);
			assertEquals( "アルバムページ表示", "site/viewAlbumPage", jspName );

			Integer pageFrom = (Integer)modelMap.get("pageFrom");
			assertEquals("pageFrom", new Integer(0), pageFrom);

			ViewAlbumPageDTO viewAlbumPageDTO = (ViewAlbumPageDTO)modelMap.get("viewAlbumPageDTO");
			String pageName = viewAlbumPageDTO.getAlbumPageDTO().getAlbumPageInfo().getName();
			assertEquals("PageName", albumPageNameForTest, pageName);

			Integer photoCount = viewAlbumPageDTO.getAlbumPageDTO().getPhotoDTOList().size();
			assertEquals("PhotoCount", new Integer(1), photoCount);
		}
		
		
		// -----< 削除する >-----
		//
		{
			String deleteResult = albumController.deleteAlbumPage(request, albumPageID, map);
			String expected = "redirect:/site/viewAlbumPageList.do/"+albumID;
			assertEquals( "アルバムページ削除", expected , deleteResult );

			File targetFile = new File( targetFilePath );
			File thumbnailFile = new File( thumbnailPath );
			assertFalse( "TargetFile" , targetFile.exists() );
			assertFalse( "ThumbnailFile" , thumbnailFile.exists() );
		}
	}
	
	//@Test
	public void testViewPhoto() throws InvalidParameterException {
		// アルバムページを作る
		{
			MockHttpServletRequest request = new MockHttpServletRequest();
			BindingResult result = Mockito.mock(BindingResult.class);
			Mockito.when(result.hasErrors()).thenReturn(false);
			ModelMap map = new ModelMap();
	
			CreateAlbumPageForm createAlbumPageForm = new CreateAlbumPageForm();
			createAlbumPageForm.setAlbumID(0);
			createAlbumPageForm.setName("TestAlbumPage");
			createAlbumPageForm.setReturnPath("http://www.mugenunagi.com");
			albumController.createAlbumPage(request, createAlbumPageForm, result, map);
		}
		
		// ファイルをアップロードする
	}
}
