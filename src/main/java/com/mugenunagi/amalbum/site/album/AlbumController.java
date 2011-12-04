package com.mugenunagi.amalbum.site.album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.album.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.album.datamodel.dto.element.AlbumContents;
import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumCategoryListPartsDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumContentsListPartsDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.view.ViewAlbumDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.view.ViewAlbumSectionDTO;
import com.mugenunagi.amalbum.album.service.AlbumService;

/**
 * アルバムのコントローラ
 * @author GTN
 *
 */
@Controller
public class AlbumController {
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
}
