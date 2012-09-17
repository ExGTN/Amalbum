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

import com.mugenunagi.amalbum.albumstructure.PhotoFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.gtnlib.graphics.image.ImageUtils;

/**
 * 画像制御のWebサービスインタフェースのコントローラ
 * @author GTN
 *
 */
@Controller
public class ImageController {
	@Autowired
	DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	DataFileUtil dataFileUtil;

	Logger logger = Logger.getLogger( this.getClass() );
	
	/**
	 * 画像を出力する（マテリアルID指定）
	 * @param modelMap
	 * @return
	 * @throws RecordNotFoundException 
	 */
    @RequestMapping("/ads/restImage.do/{materialID}")
    public String restImage( @PathVariable Integer materialID, HttpServletResponse response ) throws RecordNotFoundException {
    	// -----< パスを作る >-----
    	//
    	String filePath = dataFileUtil.getMaterialPath(materialID);
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
	 * @throws RecordNotFoundException 
	 */
    @RequestMapping("/ads/restPhoto.do/{photoID}/{materialType}")
    public String restPhoto( @PathVariable Integer photoID, @PathVariable Integer materialType, HttpServletResponse response ) throws RecordNotFoundException {
    	// -----< コンテンツ（写真）を検索する >-----
    	//
    	String filePath = dataFileUtil.getMaterialPathSingle(photoID, materialType);
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
