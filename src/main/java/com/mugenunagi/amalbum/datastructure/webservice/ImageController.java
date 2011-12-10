package com.mugenunagi.amalbum.datastructure.webservice;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mugenunagi.applicationProperties;
import com.mugenunagi.amalbum.datastructure.business.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.datamodel.entity.ContentsEntity;

/**
 * 画像制御のWebサービスインタフェースのコントローラ
 * @author GTN
 *
 */
@Controller
public class ImageController {
	@Autowired
	DataStructureBusiness dataStructureBusiness;

	Logger log = Logger.getLogger( this.getClass() );
	
	/**
	 * 画像を出力する
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/restImage.do/{contentsID}")
    public String restImage( @PathVariable Integer contentsID, HttpServletResponse response ) {
    	// -----< コンテンツを検索する >-----
    	//
    	ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
    	if( contentsEntity==null ){
    		return null;
    	}
    	
    	// -----< パスを作る >-----
    	//
    	String localContentsBasePath = applicationProperties.getString( "localContentsBasePath" );
    	String filePath = localContentsBasePath + "/" + contentsEntity.getBaseDir() + "/" + contentsEntity.getName();
    	log.debug( "File=" + filePath );

    	// -----< ファイルを開いてレスポンスに送る >-----
    	//
		ServletOutputStream outputStream = null;
        FileInputStream in = null;
    	try {
        	response.setContentType("image/jpg");

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
            System.out.println(e);
        }
        
        
//        // ためし
//        try{
//        	//BufferedImage image = ImageUtils.createResizedImage( filePath, 320, 240);
//        	BufferedImage image = ImageUtils.createRotateImage( filePath, Math.PI/2.0 );
//        	String formatName = ImageUtils.getFormatName(filePath);
//        	String mimeType = ImageUtils.getMimeType(formatName);
//        	
//        	response.setContentType( mimeType );
//
//        	outputStream = response.getOutputStream();
//        	ImageIO.write( image, formatName, outputStream);
//        	outputStream.flush();
//        	outputStream.close();
//        	
//        	image.flush();
//        } catch (IOException e) {
//            System.out.println(e);
//        }
        
    	// -----< 結果を返す >-----
    	//
    	return null;
    }
}
