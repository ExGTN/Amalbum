package com.mugenunagi.gtnlib.graphics.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

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
		mimeTypeMap.put( "BMP"	, "image/bmp" );
		mimeTypeMap.put( "bmp"	, "image/bmp" );

		mimeTypeMap.put( "jpg"	, "image/jpeg" );
		mimeTypeMap.put( "JPG"	, "image/jpeg" );
		mimeTypeMap.put( "jpeg"	, "image/jpeg" );
		mimeTypeMap.put( "JPEG"	, "image/jpeg" );

		mimeTypeMap.put( "png"	, "image/png" );
		mimeTypeMap.put( "PNG"	, "image/png" );

		mimeTypeMap.put( "wbmp"	, "image/vnd.wap.wbmp" );
		mimeTypeMap.put( "WBMP"	, "image/vnd.wap.wbmp" );

		mimeTypeMap.put( "GIF"	, "image/gif" );
		mimeTypeMap.put( "gif"	, "image/gif" );
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
		// <<< 比率計算 >>>
		double ratio = ImageUtils.getFittingRatio( image.getWidth(), image.getHeight(), width, height );
		
		// <<< 変換先のGraphicsを取得する >>>
		Graphics2D graphics2D = resizedImage.createGraphics();

		// <<< 変換先に描き込む >>>
		AffineTransform af = new AffineTransform();
		af.setToIdentity();
		af.scale( ratio, ratio );
		
		graphics2D.drawImage(image, af, null);
		graphics2D.dispose();
		image.flush();

		// -----< 描画結果を
		return resizedImage;
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
		af.rotate( angle );
		af.translate(-dWidth/2.0, -dHeight/2.0);
		
		graphics2D.drawImage(image, af, null);
		graphics2D.dispose();
		image.flush();

		// -----< 描画結果を
		return rotatedImage;
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
		// -----< ImageReader を取得する >-----
		//
    	File imageFile = new File( filePath );
    	FileImageInputStream fileImageInputStream = new FileImageInputStream( imageFile );
    	Iterator<ImageReader> iterator = ImageIO.getImageReaders( fileImageInputStream );
    	
    	// -----< ImageReader からフォーマット名を取得する >-----
    	//
    	String formatName = null;
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
		String mimeType = ImageUtils.mimeTypeMap.get( formatName );
		return mimeType;
	}
}
