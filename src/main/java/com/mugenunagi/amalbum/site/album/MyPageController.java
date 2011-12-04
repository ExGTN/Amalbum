package com.mugenunagi.amalbum.site.album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.album.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.album.datamodel.dto.parts.AlbumCategoryListPartsDTO;
import com.mugenunagi.amalbum.album.datamodel.dto.view.ViewMyPageDTO;
import com.mugenunagi.amalbum.album.service.AlbumService;

/**
 * MyPageのコントローラ
 * @author GTN
 *
 */
@Controller
public class MyPageController {
	@Autowired
	AlbumService myPageService;

	/**
	 * アルバムの一覧を参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/viewMyPage.do")
    public String viewMyPage( ModelMap modelMap ) {

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
    	modelMap.addAttribute( "viewMyPageDTO", viewMyPageDTO );
    	return "site/viewMyPage";
    }
}
