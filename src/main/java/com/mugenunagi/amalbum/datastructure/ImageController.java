package com.mugenunagi.amalbum.datastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.amalbum.Constants.ContentsType;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
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
	ContentsFileUtil contentsFileUtil;

	Logger logger = Logger.getLogger( this.getClass() );
	
	/**
	 * 画像を出力する（マテリアルID指定）
	 * @param modelMap
	 * @return
	 * @throws RecordNotFoundException 
	 * @throws InvalidParameterException 
	 */
    @RequestMapping("/ads/restImage.do/{materialID}/{dummyFileName}")
    public String restImage( HttpServletRequest request, @PathVariable Integer materialID, @PathVariable String dummyFileName, HttpServletResponse response ) throws RecordNotFoundException, InvalidParameterException {
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
	 * @throws InvalidParameterException 
	 */
    @RequestMapping("/ads/restPhoto.do/{photoID}/{materialType}/{dummyFileName}")
    public String restPhoto( HttpServletRequest request, @PathVariable Integer photoID, @PathVariable Integer materialType, @PathVariable String dummyFileName, HttpServletResponse response ) throws RecordNotFoundException, InvalidParameterException {
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
     * 動画を配信する
     * @param request
     * @param photoID
     * @param materialType
     * @param dummyFileName
     * @param response
     * @return
     * @throws RecordNotFoundException
     * @throws InvalidParameterException
     */
    @RequestMapping("/ads/restMovie.do/{movieID}/{materialType}/{dummyFileName}")
    public String restMovie( HttpServletRequest request, @PathVariable Integer movieID, @PathVariable Integer materialType, @PathVariable String dummyFileName, HttpServletResponse response ) throws RecordNotFoundException, InvalidParameterException {
    	// -----< コンテンツ（写真）を検索する >-----
    	//
    	String filePath = contentsFileUtil.getMaterialPathSingle(movieID, materialType);
    	logger.debug( "Movie=" + filePath );

    	// -----< 206か否かを判定し、情報を収集する >-----
    	//
    	String httpRange = request.getHeader("HTTP_RANGE");
    	Integer offset = 0;
    	Integer end = 0;
    	Integer len = 0;
    	boolean r206 = false;
    	if( httpRange!=null ){
    		httpRange=httpRange.trim().toLowerCase().replaceAll(" ", "");
    		if( httpRange.startsWith("bytes") ){
    			int indexEq = httpRange.indexOf("=");
    			int indexMi = httpRange.indexOf("-");
    			if( (indexEq!=-1)&&(indexMi!=-1) ){
    				offset = Integer.parseInt( httpRange.substring(indexEq+1, indexMi) );
    				end = Integer.parseInt( httpRange.substring(indexMi+1) );
    				offset = end - offset + 1;
    				r206 = true;
    			}
    		}
    	}

    	// -----< ファイルを開いてレスポンスに送る >-----
    	//
    	ServletOutputStream outputStream = null;
        FileInputStream in = null;
    	try {
    		File file = new File( filePath );
    		String fileName = file.getName();
    		String mime = ImageUtils.getMimeTypeFromFilePath(filePath);
    		int fsize = (int)file.length();
    		Date lastModified = new Date( file.lastModified() );
    		SimpleDateFormat sf = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss Z", Locale.US );
    		String lastModifiedString = sf.format( lastModified );

    		response.setContentType(mime);
        	response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
        	
        	if( r206 ){
        	    //response.setHeader('HTTP/1.1 206 Partial content');
        	    response.setHeader("Accept-Ranges", "bytes");
        	    response.setContentLength( len );
        	    response.setHeader("Content-Range", "bytes "+offset+"-"+end+"/"+fsize );
        	    response.setHeader("Last-Modified", lastModifiedString );
        	} else {
        		response.setHeader("x-jphone-copyright", "no-transfer");
        	    response.setHeader("Last-Modified", lastModifiedString );
        	    response.setHeader("Accept-Ranges" ,"bytes");
        	    response.setContentLength( fsize );
        	}

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
