package com.mugenunagi.amalbum.album;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageListDTO;
import com.mugenunagi.amalbum.albumstructure.AlbumService;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageListDTO;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.amalbum.exception.handler.AmalbumExceptionManager;

/**
 * アルバムのコントローラ
 * @author GTN
 *
 */
@Controller
public class AlbumController {
	Logger logger = Logger.getLogger( AlbumController.class );
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AmalbumExceptionManager exceptionManager;

	/**
	 * アルバムページの一覧を参照する。アルバムのIDとして、デフォルトの値を用いる
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/viewAlbumPageList")
	public String viewAlbumPageListSimple( ModelMap modelMap ) {
		// デフォルトのIDを使ってブリッジする
		Integer defaultAlbumID = 0;
		
		return this.viewAlbumPageList(defaultAlbumID, modelMap);
	}

	/**
	 * アルバムページの一覧を参照する。アルバムIDは指定されるものとする。
	 * @param modelMap
	 * @return
	 * @throws RecordNotFoundException 
	 */
	@RequestMapping("/viewAlbumPageList/{albumID}")
	public String viewAlbumPageList( @PathVariable Integer albumID, ModelMap modelMap ) {
		try{
        	String baseURL = applicationProperties.getString( "BASE_URL" );

        	//-----< 参照可能なアルバムページの一覧を取得する >-----
			//
			AlbumPageListDTO albumPageListDTO = albumService.getAlbumPageList(albumID);
	
		  	// -----< DTOに格納する >-----
		  	//
		  	// DTO の構造を作る
		  	ViewAlbumPageListDTO viewAlbumPageListDTO = new ViewAlbumPageListDTO();
	  	
		  	// 内容を設定する
		  	viewAlbumPageListDTO.setAlbumPageListDTO(albumPageListDTO);
		  	viewAlbumPageListDTO.setBaseURL(baseURL);

		  	// -----< VIEWに引き渡す >-----
		  	//
		  	modelMap.addAttribute( "viewAlbumPageListDTO", viewAlbumPageListDTO );
		  	return "site/viewAlbumPageList";
		} catch (Exception e) {
    		exceptionManager.handle(e);
    		return null;
		}
	}
	
   
//   /**
//    * アルバムを作成する
//    * @param modelMap
//    * @return
//    */
//   @RequestMapping(value="/createAlbum.do", method=RequestMethod.POST)
//   public String createAlbum( @ModelAttribute("createAlbumForm") CreateAlbumForm createAlbumForm, ModelMap modelMap ){
//   	//
//   	String albumName = createAlbumForm.getAlbumName();
//   	String brief = createAlbumForm.getBrief();
//   	logger.debug( "AlbumName="+albumName+", Brief="+brief );
//   	
//   	albumService.createAlbum( albumName, brief );
//   	
//   	return "site/createAlbum";
//   }
	
	/**
	 * アルバムページを参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/viewAlbumPage.do/{albumPageID}")
    public String viewAlbumPage( @PathVariable Integer albumPageID, ModelMap modelMap ) {

    	try{
	    	// -----< アルバムページの情報を取得する  >-----
	    	//
        	String baseURL = applicationProperties.getString( "BASE_URL" );
	    	AlbumPageDTO albumPageDTO = albumService.getAlbumPage(albumPageID);
	
	    	// -----< DTOに格納する >-----
	    	//
	    	// DTO を作る
	    	ViewAlbumPageDTO viewAlbumPageDTO = new ViewAlbumPageDTO();
	    	viewAlbumPageDTO.setBaseURL(baseURL);
	    	viewAlbumPageDTO.setAlbumPageDTO(albumPageDTO);
	    	
	    	// -----< VIEWに引き渡す >-----
	    	//
	    	modelMap.addAttribute( "viewAlbumPageDTO", viewAlbumPageDTO );
	    	return "site/viewAlbumPage";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }

    /**
     * ファイルをアップロードする
     * @param contentsGroupID
     * @param uploadFile
     * @param map
     * @return
     */
    @RequestMapping(value="/aas/uploadFile.do", method=RequestMethod.POST)
    public String uploadFile( @RequestParam("contentsGroupID") Integer contentsGroupID, @RequestParam("uploadFile") MultipartFile uploadFile, ModelMap map ){
    	try {
	    	// 一時ファイルの配置先ディレクトリを用意する
	    	String tempDirPath = applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" )
	    						+ "/" + applicationProperties.getString( "PHOTO_TEMP_RELATIVE_PATH" );
	    	File tempDir = new File( tempDirPath );
	    	if( !tempDir.exists() ){
	    		boolean result = tempDir.mkdir();
	    		if( !result ){
	    			// 一時ファイルの配置先を用意できなかった
	    			throw new IOException( "ディレクトリを生成できませんでした。Dir="+tempDir.getAbsolutePath() );
	    		}
	    	}
	    	if( !tempDir.isDirectory() ){
	    		throw new InvalidStateException( "一時ファイルの格納先ディレクトリのパスが、ディレクトリではありません。Path="+tempDir.getAbsolutePath() );
	    	}
    
	    	// ファイルを取り込む
	    	File destinationFile = null;
			if (!uploadFile.isEmpty()) {
				destinationFile = File.createTempFile("amalbum", ".tmp", tempDir );
				uploadFile.transferTo(destinationFile);
			} else {
				throw new InvalidParameterException( "uploadFile was empty." );
			}

			// 取り込んだファイルについて処理する
			String fileName = uploadFile.getOriginalFilename();
			fileName = albumService.registPhoto( contentsGroupID, destinationFile, fileName );
			
			// 処理が終わったら一時ファイルを破棄する
			destinationFile.delete();
		} catch (Exception e) {
			exceptionManager.handle(e);
		}
		return "site/fileUploaded";
   	}
}
