package com.mugenunagi.amalbum.album;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mugenunagi.amalbum.albumstructure.AlbumService;
import com.mugenunagi.amalbum.albumstructure.AlbumStructureBusiness;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.albumstructure.dto.AlbumPageDTO;
import com.mugenunagi.amalbum.albumstructure.dto.PhotoDTO;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.gtnlib.html.HTMLUtil;

public class DataImportBatch {
	private static Integer ALBUM_ID = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	//アプリケーションコンテキストの生成
    	ApplicationContext context = new ClassPathXmlApplicationContext("amalbum-servlet-converter.xml");

		try{
			// Beanの取得
			AlbumService albumService = (AlbumService) context.getBean("albumService");
			DataStructureBusiness dataStructureBusiness = (DataStructureBusiness) context.getBean("dataStructureBusiness");
			AlbumStructureBusiness albumStructureBusiness = (AlbumStructureBusiness) context.getBean("albumStructureBusiness");
			ContentsFileUtil contentsFileUtil = (ContentsFileUtil) context.getBean("contentsFileUtil");

			// パスのなかにあるディレクトリ（アルバムページ）の一覧を取得
			String path = args[0];
			File targetDir = new File(path);
			File[] dateDirs = targetDir.listFiles();
			Arrays.sort( dateDirs , new FilenameComparator() );

			// ページごとに処理する
			for( int dirIndex=0;dirIndex<dateDirs.length;dirIndex++ ){
				File dateDir = dateDirs[dirIndex];
				System.out.println( dateDir.getAbsolutePath() + " ( " + (dirIndex+1)+" / "+dateDirs.length+" )" );

				// アルバムページが既存かいなかを調べる。なければ作ってalbumPageIDを得る。
				String albumPageName = dateDir.getName();
				Integer albumPageID = albumService.getAlbumPageID(ALBUM_ID, albumPageName);
				if( albumPageID==null ){
					albumPageID = albumStructureBusiness.createAlbumPage(ALBUM_ID, albumPageName);
				}
				
				// アルバムページのコメントを処理する
				String albumPageCommentPath = dateDir.getAbsolutePath()+"/"+"comment.txt";
				File albumPageCommentFile = new File( albumPageCommentPath );
				if( albumPageCommentFile.exists() ){
					// コメントファイルが見つかったら処理する
					String comment = readTextFile( albumPageCommentFile );
					comment = HTMLUtil.htmlspecialchars(comment);
					
					// コンテンツグループのプロパティを書きかえる
					ContentsGroupEntity contentsGroupEntity = dataStructureBusiness.getContentsGroup(albumPageID);
					contentsGroupEntity.setDescription(comment);
					dataStructureBusiness.updateContentsGroup(contentsGroupEntity);
					
					// コメントファイルを出力する
					contentsFileUtil.writeAlbumPageComment(albumPageID);
				}

				// ディレクトリ内のファイル一覧を得る
				File[] files = dateDir.listFiles();
				Arrays.sort(files, new FilenameComparator());
				
				// ファイルごとに登録する
				for( File file : files ){
					// ファイル名で判断して、対象外はスキップする
					String fileName = file.getName();
					if( file.isDirectory() ){ continue; }
					if( fileName.endsWith(".comment") ){ continue; }
					if( fileName.equals("comment.txt") ){ continue; }

					// 一時ファイル名を作って、もとファイルをコピー
					System.out.println( file.getName() );
					File tempFile = file.createTempFile("tmp", ".tmp");

					// 登録
					FileUtils.copyFile(file, tempFile);
					Integer contentsID = albumService.registContents(albumPageID, tempFile.getAbsoluteFile(), fileName);
					
					// コメントを処理する
					String commentFilePath = file.getAbsolutePath()+".comment";
					File commentFile = new File( commentFilePath );
					if( commentFile.exists() ){
						// コメントファイルが見つかったら処理する
						String comment = readTextFile( commentFile );
						comment = HTMLUtil.htmlspecialchars(comment);
						
						// コンテンツのプロパティを書きかえる
						ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
						contentsEntity.setDescription(comment);
						dataStructureBusiness.updateContents(contentsEntity);
						
						// コメントファイルを出力する
						contentsFileUtil.writeContentsComment(contentsID);
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static String readTextFile( File file ) throws IOException{
		String path = file.getAbsolutePath();

		FileInputStream fis = new FileInputStream(path);
		InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
		BufferedReader br = new BufferedReader(in);
		 
        StringBuffer sb = new StringBuffer();
        String line;
        boolean first = true;
        while ((line = br.readLine()) != null) {
        	if( first ){
        		first = false;
        	} else {
        		sb.append("\n");
        	}
            sb.append(line);
        }

        br.close();
        in.close();
        return sb.toString();
    }		

}
