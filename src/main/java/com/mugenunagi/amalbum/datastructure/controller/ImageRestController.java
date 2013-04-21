package com.mugenunagi.amalbum.datastructure.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.gtnlib.graphics.image.ImageUtils;

/**
 * 画像制御のWebサービスインタフェースのコントローラ
 * @author GTN
 *
 */
@Controller
public class ImageRestController {
	@Autowired
	DataStructureBusiness dataStructureBusiness;
	
	@Autowired
	ContentsFileUtil contentsFileUtil;

	Logger logger = Logger.getLogger( this.getClass() );
	
	/**
	 * 画像を出力する（マテリアルID指定）
	 * @param modelMap
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 * @throws IOException 
	 */
    @RequestMapping("/ads/restImage.do/{materialID}/{dummyFileName}")
    public String restImage( HttpServletRequest request, @PathVariable Integer materialID, @PathVariable String dummyFileName, HttpServletResponse response ) throws RecordNotFoundException, InvalidParameterException, IOException {
    	// -----< パスを作る >-----
    	//
    	String filePath = contentsFileUtil.getMaterialPath(materialID);
    	logger.debug( "File=" + filePath );

    	// -----< ファイルを開いてレスポンスに送る >-----
    	//
    	ServletOutputStream outputStream = null;
        FileInputStream in = null;
    	try {
    		File file = new File( filePath );
    		String fileName = file.getName();
    		String mime = ImageUtils.getMimeTypeFromFilePath(filePath);
        	response.setContentType(mime);
        	response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");

        	outputStream = response.getOutputStream();
            in = new FileInputStream( filePath );

            int count;
            byte[] buffer = new byte[65536];
            while ((count = in.read( buffer )) != -1) {
                //System.out.print(Integer.toHexString(ch) + " ");
                outputStream.write( buffer, 0, count );
            }
            outputStream.flush();
        } catch (IOException ioe) {
        	logger.debug("File transfer aborted.");
        } catch (Exception e) {
        	logger.error(e,e);
        } finally {
            if( in!=null ){ in.close(); }
            if( outputStream!=null ){ outputStream.close(); }
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
	 * @throws InvalidParameterException 
	 * @throws IOException 
	 */
    @RequestMapping("/ads/restPhoto.do/{photoID}/{materialType}/{dummyFileName}")
    public String restPhoto( HttpServletRequest request, @PathVariable Integer photoID, @PathVariable Integer materialType, @PathVariable String dummyFileName, HttpServletResponse response ) throws RecordNotFoundException, InvalidParameterException, IOException {
    	// -----< コンテンツ（写真）を検索する >-----
    	//
    	String filePath = contentsFileUtil.getMaterialPathSingle(photoID, materialType);
    	logger.debug( "File=" + filePath );

    	// -----< ファイルを開いてレスポンスに送る >-----
    	//
    	ServletOutputStream outputStream = null;
        FileInputStream in = null;
    	try {
    		File file = new File( filePath );
    		String fileName = file.getName();
    		String mime = ImageUtils.getMimeTypeFromFilePath(filePath);
        	response.setContentType(mime);
        	response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");

        	outputStream = response.getOutputStream();
            in = new FileInputStream( filePath );

            int count;
            byte[] buffer = new byte[65536];
            while ((count = in.read( buffer )) != -1) {
                //System.out.print(Integer.toHexString(ch) + " ");
                outputStream.write( buffer, 0, count );
            }
            
            outputStream.flush();
        } catch (IOException ioe) {
        	logger.debug("File transfer aborted.");
        } catch (Exception e) {
        	logger.error(e,e);
        } finally {
            if( in!=null ){ in.close(); }
            if( outputStream!=null ){ outputStream.close(); }
        }
        
    	// -----< 結果を返す >-----
    	//
    	return null;
    }
}
