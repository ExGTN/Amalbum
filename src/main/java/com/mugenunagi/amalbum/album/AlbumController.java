package com.mugenunagi.amalbum.album;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.mugenunagi.applicationProperties;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.AlbumService;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
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
	private AlbumService albumService;
	
	@Autowired
	private AmalbumExceptionManager exceptionManager;

//	/**
//	 * アルバムセクションの一覧を参照する
//	 * @param modelMap
//	 * @return
//	 */
//    @RequestMapping("/viewAlbumSection.do/{albumBookID}")
//    public String viewAlbumSection( @PathVariable Integer albumBookID, ModelMap modelMap ) {
//
//    	// -----< 参照可能なアルバムセクションの一覧を取得する >-----
//    	//
//    	List<AlbumCategory> albumSectionList = albumService.getAlbumSectionList( albumBookID );
//
//    	// -----< DTOに格納する >-----
//    	//
//    	// DTO の構造を作る
//    	ViewAlbumSectionDTO viewAlbumSectionDTO = new ViewAlbumSectionDTO();
//    	AlbumCategoryListPartsDTO albumCategoryListPartsDTO = new AlbumCategoryListPartsDTO();
//    	viewAlbumSectionDTO.setAlbumCategoryListPartsDTO(albumCategoryListPartsDTO);
//    	
//    	// 内容を設定する
//    	albumCategoryListPartsDTO.setAlbumCategoryList( albumSectionList );
//    	
//    	// -----< VIEWに引き渡す >-----
//    	//
//    	modelMap.addAttribute( "viewAlbumSectionDTO", viewAlbumSectionDTO );
//    	return "site/viewAlbumSection";
//    }
	
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
    
//    /**
//     * アルバムを作成する
//     * @param modelMap
//     * @return
//     */
//    @RequestMapping(value="/createAlbum.do", method=RequestMethod.POST)
//    public String createAlbum( @ModelAttribute("createAlbumForm") CreateAlbumForm createAlbumForm, ModelMap modelMap ){
//    	//
//    	String albumName = createAlbumForm.getAlbumName();
//    	String brief = createAlbumForm.getBrief();
//    	logger.debug( "AlbumName="+albumName+", Brief="+brief );
//    	
//    	albumService.createAlbum( albumName, brief );
//    	
//    	return "site/createAlbum";
//    }
}
