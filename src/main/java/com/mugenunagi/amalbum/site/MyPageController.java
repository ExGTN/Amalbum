package com.mugenunagi.amalbum.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.datamodel.dto.element.AlbumCategory;
import com.mugenunagi.amalbum.datamodel.dto.parts.AlbumCategoryListPartsDTO;
import com.mugenunagi.amalbum.datamodel.dto.view.ViewMyPageDTO;
import com.mugenunagi.amalbum.service.MyPageService;

/**
 * MyPageのコントローラ
 * @author GTN
 *
 */
@Controller
public class MyPageController {
	@Autowired
	MyPageService myPageService;

	/**
	 * アルバムの一覧を参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/site/viewAlbumCategoryList.do")
    public String viewAlbumCategoryList( ModelMap modelMap ) {

    	// -----< 参照可能なアルバムグループの一覧を取得する >-----
    	//
    	List<AlbumCategory> albumCategoryList = myPageService.getAlbumCategoryListByParentID( 0 );
    	if( albumCategoryList==null ){
    		System.out.println( "null" );
    	} else {
    		System.out.println( albumCategoryList.size()+"個" );
    	}
    	
    	// -----< DTOに格納する >-----
    	//
    	// DTO の構造を作る
    	ViewMyPageDTO viewMyPageDTO = new ViewMyPageDTO();
    	AlbumCategoryListPartsDTO albumCategoryListPartsDTO = new AlbumCategoryListPartsDTO();
    	viewMyPageDTO.setAlbumCategoryListPartsDTO(albumCategoryListPartsDTO);
    	
    	// 内容を設定する
    	albumCategoryListPartsDTO.setAlbumCategoryList( albumCategoryList );
    	
    	// -----< VIEWに引き渡す >-----
    	//
    	modelMap.addAttribute( "viewAlbumCategoryDTO", viewMyPageDTO );
    	return "site/viewMyPage";
    }
}
