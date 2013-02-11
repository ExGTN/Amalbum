package com.mugenunagi.amalbum.album;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.Constants;
import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.album.dto.LoginInfoDTO;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageDTO;
import com.mugenunagi.amalbum.album.dto.ViewAlbumPageListDTO;
import com.mugenunagi.amalbum.album.form.CreateAlbumPageForm;
import com.mugenunagi.amalbum.album.form.EditAlbumPagePropertyForm;
import com.mugenunagi.amalbum.album.form.EditPhotoPropertyForm;
import com.mugenunagi.amalbum.albumstructure.AlbumService;
import com.mugenunagi.amalbum.albumstructure.AlbumStructureBusiness;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.MovieRegistrator;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.PhotoRegistrator;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageListDTO;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.form.LoginForm;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.amalbum.exception.handler.AmalbumExceptionManager;
import com.mugenunagi.gtnlib.graphics.image.ImageUtils;
import com.mugenunagi.gtnlib.html.HTMLUtil;

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
	private AlbumStructureBusiness albumStructureBusiness;
	
	@Autowired
	private DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	private AmalbumExceptionManager exceptionManager;
	
	@Autowired
	private PhotoRegistrator photoRegistrator;
	
	@Autowired
	private ContentsFileUtil contentsFileUtil;

	/**
	 * ログイン画面
	 * @return
	 */
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, @Valid LoginForm loginForm, BindingResult result, ModelMap map){
		map.addAttribute("loginForm", loginForm);
		return "site/login";
	}

	/**
	 * ログインチェック処理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loginCheck.do", method=RequestMethod.POST)
	public String loginCheck(HttpServletRequest request, @Valid LoginForm loginForm, BindingResult result, ModelMap map){
		String userID = loginForm.getUserID();
		String passwordMD5 = loginForm.getPasswordMD5();
		map.addAttribute("loginForm", loginForm);
		
		// 入力チェック処理
		if( (userID==null)||(userID.length()==0) ){
			FieldError fieldError = new FieldError( LoginForm.class.getName(), "userID", "ユーザIDを入力してください" );
			result.addError( fieldError );
		}
		if( (passwordMD5==null)||(passwordMD5.length()==0) ){
			FieldError fieldError = new FieldError( LoginForm.class.getName(), "password", "パスワードを入力してください" );
			result.addError( fieldError );
		}
		
		// パスワード確認
		Boolean checkResult = albumService.checkPassword( loginForm );
		if( !checkResult ){
			ObjectError objectError = new ObjectError( LoginForm.class.getName(), "ユーザIDとパスワードの組み合わせが違います" );
			result.addError( objectError );
		}
		
		// バリデーションチェック
		if( result.hasErrors() ){
			return "site/login";
		}
		
		// ログインに成功したらセッションに入れる
		HttpSession session = request.getSession(true);
		LoginInfoDTO loginInfoDTO = (LoginInfoDTO)session.getAttribute("loginInfoDTO");
		if( loginInfoDTO!=null ){
			// 一旦消す
			session.removeAttribute("loginInfoDTO");
		}
		loginInfoDTO = new LoginInfoDTO();
		loginInfoDTO.setLoginForm(loginForm);
		loginInfoDTO.setLoginDate( new Date() );
		session.setAttribute("loginInfoDTO", loginInfoDTO);
		
		// リダイレクト先のURLを作る
		String redirectURL = (String)session.getAttribute("requestURL");
		if( (redirectURL==null)||(redirectURL.length()==0) ){
			redirectURL = "/site/viewAlbumPageList.do";
		}

		// リダイレクト
		return "redirect:"+redirectURL;
	}

	/**
	 * アルバムページの一覧を参照する。アルバムのIDとして、デフォルトの値を用いる
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/viewAlbumPageList.do")
	public String viewAlbumPageListSimple( HttpServletRequest request, @RequestParam(value="page", required=false) Integer page, ModelMap modelMap ) {
		// デフォルトのIDを使ってブリッジする
		Integer defaultAlbumID = 0;
		
		return this.viewAlbumPageList(request, null, null, defaultAlbumID, page, modelMap);
	}

	/**
	 * アルバムページの一覧を参照する。アルバムIDは指定されるものとする。
	 * @param modelMap
	 * @return
	 * @throws RecordNotFoundException 
	 */
	@RequestMapping("/viewAlbumPageList.do/{albumID}")
	public String viewAlbumPageList( HttpServletRequest request, @Valid CreateAlbumPageForm createAlbumPageForm, BindingResult result,@PathVariable Integer albumID, @RequestParam(value="page", required=false) Integer page, ModelMap modelMap ) {
		try{
        	// -----< ページング関連情報の取得 >-----
        	//
        	Integer pagingUnit = Integer.parseInt( applicationProperties.getString("PAGING_UNIT") );
        	if( pagingUnit<=0 ){ pagingUnit = 20; }
        	Integer albumPageCount = albumService.getAlbumPageCount( albumID );
        	Integer pageMax = albumPageCount / pagingUnit;

        	// -----< ページ番号制御 >-----
        	//
        	if( page==null ){ page = 0; }
        	if( page<0 ){ page = 0; }
        	if( page>pageMax ){ page=pageMax; }
        	Integer nextPage = page+1;
        	Integer prevPage = page-1;
        	if( prevPage<0 ){ prevPage = null; }
        	if( nextPage>pageMax){ nextPage = null; }
        	

        	//-----< 参照可能なアルバムページの一覧を取得する >-----
			//
			AlbumPageListDTO albumPageListDTO = albumService.getAlbumPageList(albumID, page);
	
		  	// -----< DTOに格納する >-----
		  	//
		  	// DTO の構造を作る
		  	ViewAlbumPageListDTO viewAlbumPageListDTO = new ViewAlbumPageListDTO();

		  	// デフォルトのアルバムページ名を作る
		  	Date date = new Date();
		  	SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
		  	String defaultAlbumPageName = sf.format( date );
		  	
		  	// 内容を設定する
		  	viewAlbumPageListDTO.setAlbumPageListDTO(albumPageListDTO);
		  	viewAlbumPageListDTO.setDefaultAlbumPageName(defaultAlbumPageName);
		  	viewAlbumPageListDTO.setPage(page);
		  	viewAlbumPageListDTO.setPrevPage(prevPage);
		  	viewAlbumPageListDTO.setNextPage(nextPage);
		  	viewAlbumPageListDTO.setPagingUnit(pagingUnit);
		  	
		  	// -----< アルバムページ作成フォームの設定 >-----
		  	//
		  	if(createAlbumPageForm==null){
		  		createAlbumPageForm = new CreateAlbumPageForm();
		  	}
		  	createAlbumPageForm.setAlbumID(albumID);

		  	// -----< VIEWに引き渡す >-----
		  	//
		  	modelMap.addAttribute( "viewAlbumPageListDTO", viewAlbumPageListDTO );
		  	modelMap.addAttribute( "createAlbumPageForm", createAlbumPageForm );
		  	return "site/viewAlbumPageList";
		} catch (Exception e) {
    		exceptionManager.handle(e);
    		return null;
		}
	}
	
	
	/**
	 * アルバムページを参照する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/viewAlbumPage.do/{albumPageID}")
    public String viewAlbumPage( HttpServletRequest request, @PathVariable Integer albumPageID, @RequestParam(value="pageFrom", required=false) Integer pageFrom, @RequestParam(value="editMode", required=false) String editModeString, ModelMap modelMap ) {
    	// editMode判定
    	boolean editMode = false;
    	if( (editModeString!=null)&&(editModeString.equals("true")) ){
    		editMode = true;
    	}

    	try{
	    	// -----< アルバムページの情報を取得する  >-----
	    	//
	    	AlbumPageDTO albumPageDTO = albumService.getAlbumPage(albumPageID);
	    	
	    	// -----< DTOに格納する >-----
	    	//
	    	// DTO を作る
	    	ViewAlbumPageDTO viewAlbumPageDTO = new ViewAlbumPageDTO();
	    	viewAlbumPageDTO.setAlbumPageDTO(albumPageDTO);
	    	viewAlbumPageDTO.setEditMode(editMode);
	    	
	    	// -----< VIEWに引き渡す >-----
	    	//
	    	if( pageFrom==null ){
	    		pageFrom = 0;
	    	}
	    	modelMap.addAttribute( "pageFrom", pageFrom );
	    	modelMap.addAttribute( "viewAlbumPageDTO", viewAlbumPageDTO );

	    	return "site/viewAlbumPage";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }

    /**
     * デフォルトのアルバムページにファイルをアップロードする
     * @param contentsGroupID
     * @param uploadFile
     * @param map
     * @return
     */
    @RequestMapping(value="/aas/uploadFileToDefaultAlbumPage.do", method=RequestMethod.POST)
    public String uploadFileToDefaultAlbumPage( HttpServletRequest request, @RequestParam("defaultAlbumPageName") String defaultAlbumPageName, @RequestParam("albumID") Integer albumID, @RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("returnPath") String returnPath, ModelMap map ){
    	// デフォルトのアルバムページ名について、コンテンツIDを取得する
    	Integer albumPageID = albumService.getAlbumPageID( albumID, defaultAlbumPageName );
    	if( albumPageID==null ){
    		albumPageID = albumService.createAlbumPage( albumID, defaultAlbumPageName );
    	}
    	
    	// コンテンツIDを指定して、登録処理を実行する
    	String result = uploadFile( request, albumPageID, uploadFile, returnPath, map );
    	return result;
    }

    /**
     * ファイルをアップロードする
     * @param contentsGroupID
     * @param uploadFile
     * @param map
     * @return
     */
    @RequestMapping(value="/aas/uploadFile.do", method=RequestMethod.POST)
    public String uploadFile( HttpServletRequest request, @RequestParam("contentsGroupID") Integer contentsGroupID, @RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("returnPath") String returnPath, ModelMap map ){
    	try {
	    	// 一時ファイルの配置先ディレクトリを用意する
	    	String tempDirPath = contentsFileUtil.getTempPath();
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
			Integer contentsID = albumService.registContents( contentsGroupID, destinationFile, fileName );
			
			// 処理が終わったら一時ファイルを破棄する
			destinationFile.delete();
			
			// 戻り先を設定する
			map.put("returnPath", returnPath);
		} catch (Exception e) {
			exceptionManager.handle(e);
		}
		return "site/fileUploaded";
   	}

    /**
     * アルバムページを追加する
     * @param contentsGroupID
     * @param uploadFile
     * @param map
     * @return
     * @throws InvalidParameterException 
     */
    @RequestMapping(value="/aas/createAlbumPage.do", method=RequestMethod.POST)
    public String createAlbumPage(HttpServletRequest request, @Valid CreateAlbumPageForm createAlbumPageForm,BindingResult result, ModelMap map ) throws InvalidParameterException{
    	// 追加の入力チェック
    	String name = createAlbumPageForm.getName();
    	Boolean nameInvalid = name.matches("^.*[(\\\\|/|:|\\*|?|\\\"|<|>|\\|)].*$");
    	if( nameInvalid ){
	    	FieldError fieldError = new FieldError( createAlbumPageForm.getClass().getName(), "name", "使用できない文字が含まれています" );
	    	result.addError(fieldError);
    	}
    	
    	// 既存確認
    	Integer existingAlbumID = albumService.getAlbumPageID(createAlbumPageForm.getAlbumID(), name);
    	if( existingAlbumID!=null ){
	    	FieldError fieldError = new FieldError( createAlbumPageForm.getClass().getName(), "name", "指定されたアルバムページは既存です" );
	    	result.addError(fieldError);
    	}

    	// バリデーション
    	if( result.hasErrors() ){
    		Integer albumID = createAlbumPageForm.getAlbumID();
    		Integer page = 0;
    		FieldError error = result.getFieldError("name");
    		String message = error.getDefaultMessage();
    		return this.viewAlbumPageList(request, createAlbumPageForm, result, albumID, page, map);
    		//throw new InvalidParameterException(message);
    	}
    	
    	// アルバムページを作る
    	albumService.createAlbumPage(createAlbumPageForm.getAlbumID(), createAlbumPageForm.getName());
    	
    	// ModelMapに設定
    	map.put("returnPath", createAlbumPageForm.getReturnPath());

    	// リンク先のJSPへ
    	return "site/albumPageCreated";
    }
    
    /**
     * 画像を回転させる
     * @param rotate
     * @param returnPath
     * @param editMode
     * @param contentsID
     * @param map
     * @return
     */
    @RequestMapping(value="/aas/rotateImage.do", method=RequestMethod.POST)
    public String rotateImage( HttpServletRequest request, @RequestParam("rotate") String rotate, @RequestParam("returnPath") String returnPath, @RequestParam("editMode") String editMode, @RequestParam("contentsID") Integer contentsID, ModelMap map ){
    	try{
	    	// 確認
	    	if( rotate==null ){
	    		throw new InvalidParameterException( "rotateがnullです" );
	    	}
	    	if( (!rotate.equals("left"))&&(!rotate.equals("right")) ){
	    		throw new InvalidParameterException( "rotateがが不正です。rotate="+rotate );
	    	}
	    	
	    	// 判定して回転させる
	    	PhotoRegistrator.RotateDirection direction = PhotoRegistrator.RotateDirection.RIGHT;
	    	if( rotate.equals("left") ){
	    		direction = PhotoRegistrator.RotateDirection.LEFT;
	    	}
    		photoRegistrator.rotateImage( direction, contentsID );
	    	
	    	// 結果を返す
	    	map.put("returnPath", returnPath);
	    	return "site/imageRotated";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }
    
    /**
     * 写真の属性を編集する
     * @param rotate
     * @param returnPath
     * @param editMode
     * @param contentsID
     * @param map
     * @return
     */
    @RequestMapping(value="/editPhotoProperty.do", method=RequestMethod.POST)
    public String editPhotoProperty( HttpServletRequest request, @RequestParam("contentsID") Integer contentsID
    								, ModelMap map ){
    	try{
        	// 確認
	    	if( contentsID==null ){
	    		throw new InvalidParameterException( "contentsIDがnullです" );
	    	}
	    	
	    	// 検索してphotoDTOを得る
	    	PhotoDTO photoDTO = albumStructureBusiness.getPhoto( contentsID );
	    	
	    	// 変換
	    	String description = photoDTO.getDescription();
	    	description = HTMLUtil.decodeHtmlSpecialChars(description);
	    	photoDTO.setDescription(description);
	    	
	    	// フォームを作る
	    	EditPhotoPropertyForm editPhotoPropertyForm = new EditPhotoPropertyForm();
	    	editPhotoPropertyForm.setContentsID(contentsID);
	    	editPhotoPropertyForm.setDescription(description);

	    	// 結果を返す
	    	map.put("photoDTO", photoDTO);
	    	map.put("editPhotoPropertyForm", editPhotoPropertyForm);
	    	return "site/editPhotoProperty";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }
    
    /**
     * アルバムページの属性を編集する
     */
    @RequestMapping(value="/aas/editAlbumPageProperty.do", method=RequestMethod.POST)
    public String editAlbumPageProperty( HttpServletRequest request, @RequestParam("contentsGroupID") Integer contentsGroupID
    								, ModelMap map ){
    	try{
        	// 確認
	    	if( contentsGroupID==null ){
	    		throw new InvalidParameterException( "contentsGroupIDがnullです" );
	    	}
	    	
	    	// 検索してalbumPageDTOを得る
	    	AlbumPageDTO albumPageDTO = albumService.getAlbumPage(contentsGroupID);
	    	
	    	// 変換
	    	String description = albumPageDTO.getAlbumPageInfo().getDescription();
	    	description = HTMLUtil.decodeHtmlSpecialChars(description);
	    	albumPageDTO.getAlbumPageInfo().setDescription(description);

	    	// -----< フォームの情報を用意する >-----
	    	//
	    	EditAlbumPagePropertyForm editAlbumPagePropertyForm = new EditAlbumPagePropertyForm();
	    	editAlbumPagePropertyForm.setContentsGroupID(contentsGroupID);
	    	editAlbumPagePropertyForm.setBrief( albumPageDTO.getAlbumPageInfo().getBrief() );
	    	editAlbumPagePropertyForm.setDescription( description );
	    	
	    	// 結果を返す
	    	map.put("albumPageDTO", albumPageDTO);
	    	map.put("editAlbumPagePropertyForm", editAlbumPagePropertyForm);
	    	return "site/editAlbumPageProperty";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }

    /**
     * 写真の属性を更新する
     * @param rotate
     * @param returnPath
     * @param editMode
     * @param contentsID
     * @param map
     * @return
     */
    @RequestMapping(value="/updatePhotoProperty.do", method=RequestMethod.POST)
    public String updatePhotoProperty( HttpServletRequest request, @Valid EditPhotoPropertyForm editPhotoPropertyForm, BindingResult result
    								, ModelMap map ){
    	try{
    		Integer contentsID = editPhotoPropertyForm.getContentsID();
    		String description = editPhotoPropertyForm.getDescription();
    		
	    	// 確認
	    	if( contentsID==null ){
	    		throw new InvalidParameterException( "contentsIDがnullです" );
	    	}
	    	
	    	// 変換
	    	description = HTMLUtil.htmlspecialchars( description );
	    	
	    	// 検索してphotoDTOを得る
	    	PhotoDTO photoDTO = albumStructureBusiness.getPhoto( contentsID );
	    	photoDTO.setDescription(description);
	    	
	    	// photoDTOについて更新する
	    	ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(photoDTO.getContentsID());
	    	contentsEntity.setUpdateDate( new Date() );
	    	contentsEntity.setDescription( photoDTO.getDescription() );
	    	dataStructureBusiness.updateContents( contentsEntity );
    	
	    	// albumPageIDを逆引き
	    	Integer albumPageID = contentsEntity.getContentsGroupID();
	    	
	    	// コメントを処理する
	    	contentsFileUtil.writeContentsComment( contentsID );

	    	// 写真の一覧に戻る
	    	return "redirect:/site/viewAlbumPage.do/"+albumPageID+"?editMode=true";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }

    @RequestMapping(value="/updateAlbumPageProperty.do", method=RequestMethod.POST)
    public String updateAlbumPageProperty( HttpServletRequest request, @Valid EditAlbumPagePropertyForm editAlbumPagePropertyForm, BindingResult result, ModelMap map ){
    	try{
    		Integer contentsGroupID = editAlbumPagePropertyForm.getContentsGroupID();
    		String brief = editAlbumPagePropertyForm.getBrief();
    		String description = editAlbumPagePropertyForm.getDescription();
    		
	    	// 確認
	    	if( contentsGroupID==null ){
	    		throw new InvalidParameterException( "contentsGroupIDがnullです" );
	    	}
	    	
	    	// 変換
	    	brief = HTMLUtil.htmlspecialchars( brief );
	    	description = HTMLUtil.htmlspecialchars( description );
	    	
	    	// albumPageDTOについて更新する
	    	ContentsGroupEntity contentsGroupEntity = dataStructureBusiness.getContentsGroup(contentsGroupID);
	    	contentsGroupEntity.setUpdateDate( new Date() );
	    	contentsGroupEntity.setBrief( brief );
	    	contentsGroupEntity.setDescription( description );
	    	dataStructureBusiness.updateContentsGroup(contentsGroupEntity);

	    	// コメントを処理する
	    	contentsFileUtil.writeAlbumPageTitle( contentsGroupID );
	    	contentsFileUtil.writeAlbumPageComment( contentsGroupID );
    	
	    	// 写真の一覧に戻る
	    	return "redirect:/site/viewAlbumPage.do/"+contentsGroupID+"?editMode=true";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }
    

    @RequestMapping(value="/aas/deletePhoto.do", method=RequestMethod.POST)
    public String deletePhoto(
    								  HttpServletRequest request 
    								, @RequestParam("contentsGroupID") Integer contentsGroupID
      								, @RequestParam("contentsID") Integer contentsID
    								, ModelMap map ) throws Throwable{
    	try{
	    	// 確認
	    	if( contentsGroupID==null ){
	    		throw new InvalidParameterException( "contentsGroupIDがnullです" );
	    	}
	    	if( contentsID==null ){
	    		throw new InvalidParameterException( "contentsIDがnullです" );
	    	}

	    	// 写真を削除する
	    	albumStructureBusiness.removeContents( contentsID );

	    	// 写真の一覧に戻る
	    	return "redirect:/site/viewAlbumPage.do/"+contentsGroupID+"?editMode=true";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }


    @RequestMapping(value="/aas/deleteAlbumPage.do", method=RequestMethod.POST)
    public String deleteAlbumPage(
    								  HttpServletRequest request 
    								, @RequestParam("contentsGroupID") Integer contentsGroupID
    								, ModelMap map ) throws Throwable{
    	try{
	    	// 確認
	    	if( contentsGroupID==null ){
	    		throw new InvalidParameterException( "contentsGroupIDがnullです" );
	    	}
	    	
	    	// アルバムIDを取得する
	    	Integer albumID = albumService.getAlbumIDFromAlbumPageID(contentsGroupID);

	    	// アルバムページを削除する
	    	albumService.removeAlbumPage(contentsGroupID);

	    	// アルバムページの一覧に戻る
	    	return "redirect:/site/viewAlbumPageList.do/"+albumID;
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }

    @RequestMapping(value="/aas/remakeThumbs.do", method=RequestMethod.POST)
    public String remakeThumbnail(
    								  HttpServletRequest request
    								, @RequestParam("contentsGroupID") Integer contentsGroupID
    								, ModelMap map ) throws Throwable{
    	try{
	    	// 確認
	    	if( contentsGroupID==null ){
	    		throw new InvalidParameterException( "contentsGroupIDがnullです" );
	    	}

	    	// アルバムページを削除する
	    	albumService.remakeThumbnail(contentsGroupID);

	    	// 写真の一覧に戻る
	    	return "redirect:/site/viewAlbumPage.do/"+contentsGroupID+"?editMode=true";
    	} catch (Exception e){
    		exceptionManager.handle(e);
    		return null;
    	}
    }
}
