package com.mugenunagi.gtnlib.io;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mugenunagi.amalbum.exception.InvalidStateException;

/**
 * ファイルIOに関する便利機能を提供するクラス
 * @author gtn
 *
 */
public class FileUtils {
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
    	File newDir = new File( dirPath );

    	// 既存確認
    	if( newDir.exists() ){
	    	if( newDir.isDirectory() ){
	    		// ディレクトリが既存なら何もしない
	    		return;
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
}
