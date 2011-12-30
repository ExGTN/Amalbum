package com.mugenunagi.amalbum.album.site;

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

import com.mugenunagi.amalbum.album.datamodel.dto.form.CreateAlbumForm;
import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumCategoryListPartsDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumContentsListPartsDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.view.ViewAlbumDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.view.ViewAlbumSectionDTO;
import com.mugenunagi.amalbum.albumstructure.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.albumstructure.datamodel.dto.element.AlbumContents;
import com.mugenunagi.amalbum.albumstructure.service.AlbumService;

/**
 * アルバムのコントローラ
 * @author GTN
 *
 */
@Controller
public class AlbumController {
	Logger logger = Logger.getLogger( AlbumController.class );
	
	@Autowired
	AlbumService albumService;

	/**
	 * アルバムセクションの一覧を参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/viewAlbumSection.do/{albumBookID}")
    public String viewAlbumSection( @PathVariable Integer albumBookID, ModelMap modelMap ) {

    	// -----< 参照可能なアルバムセクションの一覧を取得する >-----
    	//
    	List<AlbumCategory> albumSectionList = albumService.getAlbumSectionList( albumBookID );

    	// -----< DTOに格納する >-----
    	//
    	// DTO の構造を作る
    	ViewAlbumSectionDTO viewAlbumSectionDTO = new ViewAlbumSectionDTO();
    	AlbumCategoryListPartsDTO albumCategoryListPartsDTO = new AlbumCategoryListPartsDTO();
    	viewAlbumSectionDTO.setAlbumCategoryListPartsDTO(albumCategoryListPartsDTO);
    	
    	// 内容を設定する
    	albumCategoryListPartsDTO.setAlbumCategoryList( albumSectionList );
    	
    	// -----< VIEWに引き渡す >-----
    	//
    	modelMap.addAttribute( "viewAlbumSectionDTO", viewAlbumSectionDTO );
    	return "site/viewAlbumSection";
    }
	
	/**
	 * アルバムを参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/viewAlbum.do/{albumSectionID}")
    public String viewAlbum( @PathVariable Integer albumSectionID, ModelMap modelMap ) {

    	// -----< アルバムのコンテンツ一覧を取得する >-----
    	//
    	List<AlbumContents> albumContentsList = albumService.getAlbumContentsList( albumSectionID );

    	// -----< DTOに格納する >-----
    	//
    	// DTO の構造を作る
    	ViewAlbumDTO viewAlbumDTO = new ViewAlbumDTO();
    	AlbumContentsListPartsDTO albumContentsListPartsDTO = new AlbumContentsListPartsDTO();
    	viewAlbumDTO.setAlbumContentsListPartsDTO(albumContentsListPartsDTO);
    	
    	// 内容を設定する
    	albumContentsListPartsDTO.setAlbumContentsList( albumContentsList );
    	
    	// -----< VIEWに引き渡す >-----
    	//
    	modelMap.addAttribute( "viewAlbumDTO", viewAlbumDTO );
    	return "site/viewAlbum";
    }
    
    /**
     * アルバムを作成する
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/createAlbum.do", method=RequestMethod.POST)
    public String createAlbum( @ModelAttribute("createAlbumForm") CreateAlbumForm createAlbumForm, ModelMap modelMap ){
    	//
    	String albumName = createAlbumForm.getAlbumName();
    	String brief = createAlbumForm.getBrief();
    	logger.debug( "AlbumName="+albumName+", Brief="+brief );
    	
    	albumService.createAlbum( albumName, brief );
    	
    	return "site/createAlbum";
    }
}
