package com.mugenunagi.gtnlib.io;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.mugenunagi.amalbum.exception.InvalidStateException;

/**
 * ファイルIOに関する便利機能を提供するクラス
 * @author gtn
 *
 */
public class FilePathUtil {
	private static Logger logger = Logger.getLogger(FilePathUtil.class); 
	/**
	 * 日付とベースパスを指定して実行すると、ベースパス以下にYYYYMMDDの名前のディレクトリを作成します。
	 * ディレクトリが既存の場合は、何もせずに返ります。
	 * @param date
	 * @throws InvalidStateException 
	 * @throws IOException 
	 */
	public static void prepareYYYYMMDDDirectory( String basePath, Date date ) throws InvalidStateException, IOException {
		// 日付の文字列を作る
		SimpleDateFormat sf = new SimpleDateFormat( "yyyyMMdd" );
		String newDirName = sf.format(date);
		
		prepareDirectory( basePath, newDirName );
	}

	/**
	 * 指定したベースパス内に、指定した名称のディレクトリを作成します。
	 * ディレクトリが既存だった場合は、何もせずに返ります。
	 * @param basePath
	 * @param newDirName
	 * @throws InvalidStateException
	 * @throws IOException
	 */
	public static void prepareDirectory( String basePath, String newDirName ) throws InvalidStateException, IOException {
		// ディレクトリのフルパスを作る
    	String dirPath = basePath + "/" + newDirName;
    	FilePathUtil.prepareDirectory(dirPath);
    	
		// 正常終了
	}

	/**
	 * 指定したベースパス内に、指定した名称のディレクトリを作成します。
	 * ディレクトリが既存だった場合は、何もせずに返ります。
	 * @param basePath
	 * @param newDirName
	 * @throws InvalidStateException
	 * @throws IOException
	 */
	public static void prepareDirectory( String path ) throws InvalidStateException, IOException {
		String separator = File.separator.toString();
		separator = separator.replaceAll( "\\\\", "\\\\\\\\" );
		String[] elements = path.split( "\\\\|/" );
		if( elements.length==0 ){ return; }
		
		StringBuilder dirPath = new StringBuilder();
		if( path.startsWith("/") ){
			dirPath.append("/");
		}

		for( int i=0;i<elements.length;i++ ){
			if( i!=0 ){ dirPath.append("/"); }
			dirPath.append( elements[i] );
			
			// ディレクトリのフルパスを作る
	    	File newDir = new File( dirPath.toString() );
	
	    	// 既存確認
	    	if( newDir.exists() ){
		    	if( newDir.isDirectory() ){
		    		// ディレクトリが既存なら何もしない
		    		continue;
		    	} else {
		    		// ファイルシステム上既存だがディレクトリでない場合は例外
		    		throw new InvalidStateException( "同名のファイルが存在しています。Path="+newDir.getAbsolutePath() );
		    	}
	    	}
	
	    	// 作成する
	   		boolean result = newDir.mkdir();
			if( !result ){
				// 一時ファイルの配置先を用意できなかった
				throw new IOException( "ディレクトリを生成できませんでした。Dir="+newDir.getAbsolutePath() );
			}
		}
		
		// 正常終了
	}

	/**
	 * 指定されたファイルパスについて重複するファイルが存在したときに、主ファイル名に _n をつけて
	 * ファイル名が重ならないように調整し、結果を返します。
	 * @param filePath ファイルパス
	 * @return ファイルが重ならないように加工されたファイルパス
	 */
	public static String makeUniqueFileName( String filePath ){
		// ファイルがもともと重ならないなら、そのまま返す
		File file = new File(filePath);
		if( !file.exists() ){
			return filePath;
		}

		// ファイル名が重なる場合は分割して、間に _n を入れる
		int count=1;
		while( count<10000000 ){
			String mainName = filePath;
			String extention = "";
	
			int dotIndex = filePath.lastIndexOf('.');
			if( dotIndex>0 ){
				mainName = filePath.substring(0,dotIndex);
				if( dotIndex!=filePath.length()-1 ){
					// .が最後の文字でない場合だけ評価する
					extention = filePath.substring(dotIndex+1);
				}
			}
			
			String newPath = mainName + "_" + count + "." + extention;
			file = new File(newPath);
			if( !file.exists() ){
				return newPath;
			}
			count += 1;
		}
		
		// 結果を返す
		return filePath;
	}

	/**
	 * ファイル名から拡張子を返します。
	 * @param fileName ファイル名
	 * @return ファイルの拡張子
	 */
	public static String getExtension(String fileName) {
		if (fileName == null){
			return null;
		}

		int point = fileName.lastIndexOf(".");
		if (point != -1) {
			return fileName.substring(point + 1);
		}
		return fileName;
	}

	/**
	 * 指定されたファイル file について、作成日付でフォーマットされたファイル名を作って返します。拡張子には、originalFilename で指定されるファイル名の拡張子が使われます。
	 * @param file ファイル
	 * @param originalFilename オリジナルのファイル名
	 * @return 生成されたファイル名
	 */
	public static String getDateFormattedFilename(File file, String originalFilename) {
		// JPEGでなければ、そのまま返す
		String extention = FilePathUtil.getExtension(originalFilename).toLowerCase();
		if(extention==null){
			return originalFilename;
		}
		if((!extention.equals("jpg"))&&(!extention.equals("jpeg"))){
			return originalFilename;
		}

		// 作成日付が取れる場合は、その日付でファイル名を作る
		String fileName = originalFilename;
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(file);
		} catch (ImageProcessingException e) {
			logger.error(e, e);
			return originalFilename;
		} catch (IOException e) {
			logger.error(e, e);
			return originalFilename;
		}

		ExifIFD0Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
		if ((directory == null) || (!directory.containsTag(ExifIFD0Directory.TAG_DATETIME))) {
			return originalFilename;
		}

		Date date = directory.getDate(ExifIFD0Directory.TAG_DATETIME);
		if (date == null) {
			return originalFilename;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		fileName = "Image-" + sf.format(date) + "." + extention;

		return fileName;
	}
}
