package com.mugenunagi.amalbum.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.datamodel.dao.ContentsGroupMapper;
import com.mugenunagi.amalbum.datamodel.dto.album.AlbumCategory;
import com.mugenunagi.amalbum.datamodel.dto.view.ViewAlbumCategoryDTO;
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
    @RequestMapping("/viewAlbumCategoryList")
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
    	ViewAlbumCategoryDTO viewAlbumCategoryDTO = new ViewAlbumCategoryDTO();
    	viewAlbumCategoryDTO.setAlbumCategoryList( albumCategoryList );
    	
    	// -----< VIEWに引き渡す >-----
    	//
    	modelMap.addAttribute( "albumCategoryDTO", viewAlbumCategoryDTO );
    	return "site/viewAlbumCategory";
    }
}
