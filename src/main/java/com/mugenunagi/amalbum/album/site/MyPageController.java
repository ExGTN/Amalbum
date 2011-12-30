package com.mugenunagi.amalbum.album.site;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.album.datamodel.dto.form.CreateAlbumForm;
import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumCategoryListPartsDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.view.ViewMyPageDTO;
import com.mugenunagi.amalbum.albumstructure.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.albumstructure.service.AlbumService;

/**
 * MyPageのコントローラ
 * @author GTN
 *
 */
@Controller
public class MyPageController {
	@Autowired
	AlbumService myPageService;
	
	Logger log = Logger.getLogger( MyPageController.class.getName() );

	/**
	 * アルバムの一覧を参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/viewMyPage.do")
    public String viewMyPage( ModelMap modelMap ) {
    	//DOMConfigurator.configure("D:/GTNWORK/workspace/Amalbum/src/main/webapp/WEB-INF/log4j.xml");
    	System.out.println( MyPageController.class.getName() );
    	log.fatal("viewaaaaaaaMyPage-do called");

    	// -----< 参照可能なアルバムグループの一覧を取得する >-----
    	//
    	List<AlbumCategory> albumBookList = myPageService.getAlbumBookList();

    	// -----< DTOに格納する >-----
    	//
    	// DTO の構造を作る
    	ViewMyPageDTO viewMyPageDTO = new ViewMyPageDTO();
    	AlbumCategoryListPartsDTO albumCategoryListPartsDTO = new AlbumCategoryListPartsDTO();
    	viewMyPageDTO.setAlbumCategoryListPartsDTO(albumCategoryListPartsDTO);
    	
    	// 内容を設定する
    	albumCategoryListPartsDTO.setAlbumCategoryList( albumBookList );
    	
    	// -----< VIEWに引き渡す >-----
    	//
    	modelMap.addAttribute("createAlbumForm", new CreateAlbumForm());
    	modelMap.addAttribute( "viewMyPageDTO", viewMyPageDTO );
    	return "site/viewMyPage";
    }
}
