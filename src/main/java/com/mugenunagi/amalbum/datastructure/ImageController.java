package com.mugenunagi.amalbum.datastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.ApplicationProperties;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.gtnlib.graphics.image.ImageUtils;

/**
 * 画像制御のWebサービスインタフェースのコントローラ
 * @author GTN
 *
 */
@Controller
public class ImageController {
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
	DataStructureBusiness dataStructureBusiness;

	Logger logger = Logger.getLogger( this.getClass() );
	
	/**
	 * 画像を出力する（マテリアルID指定）
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/ads/restImage.do/{materialID}")
    public String restImage( @PathVariable Integer materialID, HttpServletResponse response ) {
    	// -----< マテリアルを検索する >-----
    	//
    	MaterialEntity materialEntity = dataStructureBusiness.getMaterialByMaterialID(materialID);
    	if( materialEntity==null ){
    		return null;
    	}
    	
    	// -----< パスを作る >-----
    	//
    	String localContentsBasePath = applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );
    	String photoRelativePath     = applicationProperties.getString( "PHOTO_RELATIVE_PATH" );
    	String filePath = localContentsBasePath + "/" + photoRelativePath + "/" + materialEntity.getPath();
    	logger.debug( "File=" + filePath );

    	// -----< ファイルを開いてレスポンスに送る >-----
    	//
    	ServletOutputStream outputStream = null;
        FileInputStream in = null;
    	try {
    		String mime = ImageUtils.getMimeTypeFromFilePath(filePath);
        	response.setContentType(mime);

        	outputStream = response.getOutputStream();
            in = new FileInputStream( filePath );

            int count;
            byte[] buffer = new byte[65536];
            while ((count = in.read( buffer )) != -1) {
                //System.out.print(Integer.toHexString(ch) + " ");
                outputStream.write( buffer, 0, count );
            }
            
            in.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
        	logger.error(e,e);
        }
        
    	// -----< 結果を返す >-----
    	//
    	return null;
    }
	
	/**
	 * 写真を出力する（写真ID指定）
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/ads/restPhoto.do/{photoID}/{materialType}")
    public String restPhoto( @PathVariable Integer photoID, @PathVariable Integer materialType, HttpServletResponse response ) {
    	// -----< コンテンツ（写真）を検索する >-----
    	//
    	ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(photoID);
    	if( contentsEntity==null ){
    		return null;
    	}

    	// -----< コンテンツに紐付くマテリアルを検索する >-----
    	//
    	List<MaterialEntity> materialEntityList = dataStructureBusiness.getMaterialListByContentsID(photoID);
    	
    	// 素材タイプが一致するものを選ぶ
    	MaterialEntity materialEntity = null;
    	for( MaterialEntity mat : materialEntityList ){
    		Integer type = mat.getMaterialType();
    		if( type==null ){ continue; }
    		if( type.equals(materialType) ){
    			materialEntity = mat;
    		}
    	}
    	
    	// -----< パスを作る >-----
    	//
    	String localContentsBasePath = applicationProperties.getString( "LOCAL_CONTENTS_BASE_PATH" );

    	StringBuilder sb = new StringBuilder();
    	sb.append( localContentsBasePath );
    	sb.append( "/" );
    	sb.append( contentsEntity.getBaseDir() );
    	sb.append( "/" );
    	sb.append( materialEntity.getPath() );
    	String filePath = sb.toString();
    	logger.debug( "File=" + filePath );

    	// -----< ファイルを開いてレスポンスに送る >-----
    	//
    	ServletOutputStream outputStream = null;
        FileInputStream in = null;
    	try {
    		String mime = ImageUtils.getMimeTypeFromFilePath(filePath);
        	response.setContentType(mime);

        	outputStream = response.getOutputStream();
            in = new FileInputStream( filePath );

            int count;
            byte[] buffer = new byte[65536];
            while ((count = in.read( buffer )) != -1) {
                //System.out.print(Integer.toHexString(ch) + " ");
                outputStream.write( buffer, 0, count );
            }
            
            in.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
        	logger.error(e,e);
        }
        
    	// -----< 結果を返す >-----
    	//
    	return null;
    }
}
