package com.mugenunagi.amalbum.album;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.form.LoginForm;

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
		// テスト用ファイルのフルパスを作る
		String testDataLocation = applicationProperties.getString("TESTDATA_LOCATION");
		String testDataFilename = applicationProperties.getString("TESTDATA_PHOTO_1");
		String testDataFilePath = testDataLocation + "/" + testDataFilename; 
		
		// モックオブジェクトを作る
		FileInputStream inputStream = new FileInputStream( testDataFilePath );
		MockMultipartFile uploadFile = new MockMultipartFile( testDataFilePath , testDataFilename , "image/jpeg" , inputStream );
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		// テスト用オブジェクトを作る
		ModelMap map = new ModelMap();
		String returnPath = "http://www.mugenunagi.com/amalbum/login.do";
		
		// アルバムページを作る
		Integer albumID = 0;
		Integer albumPageID = dataStructureBusiness.createContentsGroup( albumID , "TestPageToUploadFile" );
		
		// ----< アップロードする >-----
		//
		String jspName = albumController.uploadFile(request, albumPageID, uploadFile, returnPath, map);
		
		// 確認する
		assertEquals( "ファイルupload", "site/fileUploaded", jspName );
		String mapRetPath = (String)( map.get("returnPath") );
		assertEquals( "リターンパス", returnPath, mapRetPath );
		
		// -----< 削除する >-----
		//
		String deleteResult = albumController.deleteAlbumPage(request, albumPageID, map);
		String expected = "redirect:/site/viewAlbumPageList.do/"+albumID;
		assertEquals( "アルバムページ削除", expected , deleteResult );
	}
}
