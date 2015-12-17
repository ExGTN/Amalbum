package com.mugenunagi.gtnlib.graphics.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.mugenunagi.gtnlib.io.FilePathUtil;

public class ImageUtils {
	//=========================================================================
	// static属性
	//=========================================================================
	// フォーマット名→MIMEタイプのマップ
	private static Map<String,String> mimeTypeMap;


	//=========================================================================
	// static節
	//=========================================================================
	static {
		mimeTypeMap = new HashMap<String,String>();

		// 画像
		mimeTypeMap.put( "bmp"	, "image/bmp" );
		mimeTypeMap.put( "jpg"	, "image/jpeg" );
		mimeTypeMap.put( "jpeg"	, "image/jpeg" );
		mimeTypeMap.put( "png"	, "image/png" );
		mimeTypeMap.put( "wbmp"	, "image/vnd.wap.wbmp" );
		mimeTypeMap.put( "gif"	, "image/gif" );

		// 動画
		mimeTypeMap.put( "3gp", "video/3gpp" );
		mimeTypeMap.put( "3g2", "video/3gpp2" );
		mimeTypeMap.put( "asf", "video/x-ms-asf" );
		mimeTypeMap.put( "asx", "video/x-ms-asf" );
		mimeTypeMap.put( "wmv", "video/x-ms-wmv" );
		mimeTypeMap.put( "wvx", "video/x-ms-wvx" );
		mimeTypeMap.put( "wm", "video/x-ms-wm" );
		mimeTypeMap.put( "wmx", "video/x-ms-wmx" );
		mimeTypeMap.put( "mts", "video/avchd" );
		mimeTypeMap.put( "avi", "video/avi" );
		mimeTypeMap.put( "mov", "video/quicktime" );

		// 音声
		mimeTypeMap.put( "wma", "audio/x-ms-wma" );
		mimeTypeMap.put( "wax", "audio/x-ms-wax" );

		// アプリケーション
		//mimeTypeMap.put( "mts", "application/metastream" );
		mimeTypeMap.put( "mmf", "application/x-smaf" );
		mimeTypeMap.put( "mld", "application/x-mld" );
		mimeTypeMap.put( "amc", "application/x-mpeg" );
		mimeTypeMap.put( "wmz", "application/x-ms-wmz" );
		mimeTypeMap.put( "wmd", "application/x-ms-wmd" );
	}


	//=========================================================================
	// 演算系
	//=========================================================================
	/**
	 * 元の画像サイズ(sourceWidth×sourceHeight)を、フィット先のサイズ(fittingWidth×fittingHeight)に収めるための比率を計算して返します。
	 */
	public static double getFittingRatio( int sourceWidth, int sourceHeight, int fittingWidth, int fittingHeight )
	{
		double dSourceWidth		= (double)sourceWidth;
		double dSourceHeight	= (double)sourceHeight;
		double dMaxWidth		= (double)fittingWidth;
		double dMaxHeight		= (double)fittingHeight;

		double ratio		= 1.0f;
		double dstHeight	= 0.0;

		// 横幅で合わせる
		ratio		= dMaxWidth / dSourceWidth;
		dstHeight	= dSourceHeight * ratio;
		
		// 縦幅が足りない場合は、縦幅で合わせる
		if( dstHeight>dMaxHeight ){
			ratio	= dMaxHeight / dSourceHeight;
			dstHeight	= sourceHeight * ratio;
		}
		
		// 結果を返す
		return ratio;
	}

	
	//=========================================================================
	// 画像処理系
	//=========================================================================
	/**
	 * 画像ファイルを指定すると、イメージをリサイズしてBufferedImageで返す
	 * @param filePath 元画像のファイル名
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage createResizedImage( String filePath, int width, int height ) throws IOException {
		// -----< 元画像のBufferedImageを作る >-----
		//
		FileInputStream inputStream = new FileInputStream( filePath );
		BufferedImage image = ImageIO.read( inputStream );
		inputStream.close();

		// -----< 変換先のBufferedImageを作る >-----
		//
		BufferedImage resizedImage = new BufferedImage(width, height, image.getType());

		// -----< 変換する >-----
		//
		// <<< 変換先のGraphicsを取得する >>>
		Graphics2D graphics2D = resizedImage.createGraphics();

		// <<< 変換先に描き込む >>>
		Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		AffineTransform af = new AffineTransform();
		af.setToIdentity();
		graphics2D.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION , 
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(scaledImage, af, null);
		graphics2D.dispose();
		scaledImage.flush();
		image.flush();

		// -----< 描画結果を返す >-----
		//
		return resizedImage;
	}
	
	
	/**
	 * 指定したサイズを最大の大きさとして、アスペクト比を保持したサムネイル画像を構築して返します。
	 * @param filePath
	 * @param fittingWidth
	 * @param fittingHeight
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static BufferedImage createThumbnailImage( String filePath, int fittingWidth, int fittingHeight ) throws FileNotFoundException, IOException {
		// -----< 元画像のBufferedImageを作る >-----
		//
		FileInputStream inputStream = new FileInputStream( filePath );
		BufferedImage image = ImageIO.read( inputStream );
		inputStream.close();
		
		// サイズを取得する
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		
		// -----< 縮小サイズを算出する >-----
		//
		double fittingRatio = ImageUtils.getFittingRatio(sourceWidth, sourceHeight, fittingWidth, fittingHeight);
		int thumbnailWidth = (int)((double)sourceWidth * fittingRatio);
		int thumbnailHeight = (int)((double)sourceHeight * fittingRatio);
		
		// -----< 縮小画像を作成する >-----
		//
		BufferedImage thumbnail = ImageUtils.createResizedImage(filePath, thumbnailWidth, thumbnailHeight);
		return thumbnail;
	}

	
	/**
	 * 画像ファイルを指定すると、イメージをリサイズしてBufferedImageで返す
	 * @param filePath 元画像のファイル名
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage createRotateImage( String filePath, double angle ) throws IOException {
		// -----< 元画像のBufferedImageを作る >-----
		//
		FileInputStream inputStream = new FileInputStream( filePath );
		BufferedImage image = ImageIO.read( inputStream );
		inputStream.close();

		// -----< 変換先のBufferedImageを作る >-----
		//
		BufferedImage rotatedImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());

		// -----< 変換する >-----
		//
		// <<< 変換先のGraphicsを取得する >>>
		Graphics2D graphics2D = rotatedImage.createGraphics();

		// <<< 変換先に描き込む >>>
		double dWidth = (double)image.getWidth();
		double dHeight = (double)image.getHeight();

		AffineTransform af = new AffineTransform();
		af.setToIdentity();
		af.translate(dHeight/2.0, dWidth/2.0);
		af.rotate( -(angle/180.0)*Math.PI );
		af.translate(-dWidth/2.0, -dHeight/2.0);
		
		graphics2D.drawImage(image, af, null);
		graphics2D.dispose();
		image.flush();

		// -----< 描画結果を
		return rotatedImage;
	}
	
	
	/**
	 * 指定されたBufferedImageを、thumbnailFile に書きだします。
	 * @param bi
	 * @param imageType
	 * @param thumbnailPath
	 * @throws IOException
	 */
	public static void writeImage( BufferedImage bi, String imageType, File thumbnailFile ) throws IOException{
		if( imageType.toUpperCase().equals("JPG") ){
			// JPGの場合は品質設定を行う
			JPEGImageWriteParam jiparam = new JPEGImageWriteParam(Locale.getDefault());
			jiparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			jiparam.setCompressionQuality(0.95f);

			ImageWriter iw = ImageIO.getImageWritersByFormatName(imageType).next();
			ImageOutputStream outputStream = ImageIO.createImageOutputStream( thumbnailFile );
			iw.setOutput( outputStream );
			iw.write(null, new IIOImage(bi, null, null), jiparam);
			bi.flush();
			outputStream.close();
		} else {
			ImageIO.write(bi, imageType, thumbnailFile);
		}
	}


	//=========================================================================
	// ファイルフォーマット系
	//=========================================================================
	/**
	 * 指定したファイルのフォーマット名を返します。<BR>
	 * フォーマット名は、ImageIO#getWriterFormatNames() の結果に準拠します。
	 * @param filePath フォーマットを判別するファイルのファイル名
	 * @return 判別した結果
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getFormatName( String filePath ) throws FileNotFoundException, IOException {
		// -----< ひとまず拡張子として評価する >-----
		//
    	String formatName = FilePathUtil.getExtension( filePath );;

    	// -----< ImageReader を取得する >-----
		//
    	File imageFile = new File( filePath );
    	FileImageInputStream fileImageInputStream = null;
    	try{
    		fileImageInputStream = new FileImageInputStream( imageFile );
    	} catch (FileNotFoundException e){
    		// ファイルが無かった場合は拡張子を返す
    		return formatName;
    	}
    	Iterator<ImageReader> iterator = ImageIO.getImageReaders( fileImageInputStream );
    	
    	// -----< ImageReader からフォーマット名を取得する >-----
    	//
    	while( iterator.hasNext() ){
        	ImageReader imageReader = iterator.next();
        	if( imageReader==null ){ continue; }

        	if( formatName==null ){
        		formatName = imageReader.getFormatName();
        	}
        	imageReader.dispose();
    	}
    	
    	// -----< 後片付け >-----
    	//
    	fileImageInputStream.close();
    	
    	// -----< 結果を返す >-----
    	//
    	return formatName;
	}
	
	
	/**
	 * ファイル名を指定して実行すると、その画像ファイルのMIMEタイプを判別して返します。<BR>
	 * フォーマット名は、ImageIO#getWriterFormatNames() の結果に準拠します。
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getMimeTypeFromFilePath( String filePath ) throws FileNotFoundException, IOException{

		// -----< フォーマットのタイプを取得する >-----
		//
		String formatName = ImageUtils.getFormatName( filePath );
		
		// -----< MIMEタイプを得る >-----
		//
		String mimeType = ImageUtils.getMimeType( formatName );
		return mimeType;
	}
	

	/**
	 * 指定したフォーマット名から、MIMEタイプを割り出して返します。<BR>
	 * フォーマット名は、ImageIO#getWriterFormatNames() の結果に準拠します。
	 * @param formatName
	 * @return
	 */
	public static String getMimeType( String formatName ){
		String mimeType = ImageUtils.mimeTypeMap.get( formatName.toLowerCase() );
		return mimeType;
	}
}
