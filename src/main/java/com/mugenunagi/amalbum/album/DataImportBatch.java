package com.mugenunagi.amalbum.album;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mugenunagi.amalbum.albumstructure.AlbumPageService;
import com.mugenunagi.amalbum.albumstructure.AlbumService;
import com.mugenunagi.amalbum.albumstructure.AlbumStructureBusiness;
import com.mugenunagi.amalbum.albumstructure.ContentsRegistrator.ContentsFileUtil;
import com.mugenunagi.amalbum.datastructure.DataStructureBusiness;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.exception.InvalidParameterException;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;
import com.mugenunagi.gtnlib.html.HTMLUtil;

public class DataImportBatch {
	private static Integer ALBUM_ID = -1;
	
	// Beans
	public static AlbumService albumService;
	public static AlbumPageService albumPageService;
	public static DataStructureBusiness dataStructureBusiness;
	public static AlbumStructureBusiness albumStructureBusiness;
	public static ContentsFileUtil contentsFileUtil;

	/**
	 * データインポータ
	 * @param args 引数の配列 args[0]:アルバムID args[1]:インポートするファイルを格納したディレクトリ
	 */
	public static void main(String[] args) {
    	//アプリケーションコンテキストの生成
    	ApplicationContext context = new ClassPathXmlApplicationContext("amalbum-servlet-converter.xml");

		try{
			// Beanの取得
			albumService = (AlbumService) context.getBean("albumService");
			albumPageService = (AlbumPageService) context.getBean("albumPageService");
			dataStructureBusiness = (DataStructureBusiness) context.getBean("dataStructureBusiness");
			albumStructureBusiness = (AlbumStructureBusiness) context.getBean("albumStructureBusiness");
			contentsFileUtil = (ContentsFileUtil) context.getBean("contentsFileUtil");

			// 引数の取り込み
			if( args.length<2 ){
				System.out.println("引数の指定が不正です。0:AlbumID, 1:InputFileDir, 2:Force");
			}
			ALBUM_ID = Integer.parseInt(args[0]);
			String path = args[1];
			Boolean force = false;
			if( args.length>=3 ){
				force = Boolean.parseBoolean(args[2]);
			}
			
			// パスのなかにあるディレクトリ（アルバムページ）の一覧を取得
			File targetDir = new File(path);
			File[] albumPageDirs = targetDir.listFiles();
			Arrays.sort( albumPageDirs , new FilenameComparator() );

			// アルバムページごとに処理する
			for( int dirIndex=0;dirIndex<albumPageDirs.length;dirIndex++ ){
				// 進捗状況の表示
				File albumPageDir = albumPageDirs[dirIndex];
				System.out.println( albumPageDir.getAbsolutePath() + " ( " + (dirIndex+1)+" / "+albumPageDirs.length+" )" );

				// アルバムページを処理する
				importAlbumPageDir( albumPageDir, force );
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println( "Finish." );
	}
	
	/**
	 * 指定されたアルバムページのディレクトリについて、取り込みを実行します。
	 * @param albumPageDir
	 * @throws RecordNotFoundException
	 * @throws InvalidParameterException
	 * @throws InvalidStateException
	 * @throws IOException
	 */
	private static void importAlbumPageDir(File albumPageDir, Boolean force)
			throws RecordNotFoundException, InvalidParameterException,
			InvalidStateException, IOException {

		// アルバムページが既存かいなかを調べる。なければ作ってalbumPageIDを得る。
		String albumPageName = albumPageDir.getName();
		Integer albumPageID = albumService.getAlbumPageID(ALBUM_ID, albumPageName);
		if( albumPageID==null ){
			albumPageID = albumStructureBusiness.createAlbumPage(ALBUM_ID, albumPageName);
		}
		
		// アルバムページのコメントを処理する
		importAlbumPageComment(albumPageID, albumPageDir, force);

		// ディレクトリ内のファイル一覧を得る
		File[] contentsFiles = albumPageDir.listFiles();
		Arrays.sort(contentsFiles, new FilenameComparator());
		
		// ファイルごとに登録する
		for( File contentsFile : contentsFiles ){
			String fileName = contentsFile.getName();

			// ディレクトリはスキップする
			if( contentsFile.isDirectory() ){ continue; }
			
			// コメントファイルは、コンテンツに対応した形で処理するのでスキップする
			if( fileName.endsWith(".comment") ){ continue; }
			if( fileName.equals("comment.txt") ){ continue; }

			// ファイルを処理する
			importContentsFile( albumPageID, contentsFile, force );
		}
	}

	/**
	 * アルバムページのコメントをインポートする
	 * @param albumPageID 処理対象のアルバムページID
	 * @param albumPageDir 取りこむファイルが格納されているディレクトリ
	 * @throws IOException I/Oエラー
	 * @throws RecordNotFoundException 指定したアルバムページIDが存在しない
	 * @throws InvalidParameterException 入力パラメータが不正
	 * @throws InvalidStateException 想定しない状態
	 */
	private static void importAlbumPageComment(Integer albumPageID, File albumPageDir, Boolean force)
			throws IOException, RecordNotFoundException,
			InvalidParameterException, InvalidStateException {
		// コメントファイルの存在確認。無ければ処理不要。
		String albumPageCommentPath = albumPageDir.getAbsolutePath()+"/"+"comment.txt";
		File albumPageCommentFile = new File( albumPageCommentPath );
		if( !albumPageCommentFile.exists() ){
			return;
		}
		
		// ファイルとDBを比較して、ファイルが同時刻か古ければ処理不要
		long fileUpdateTime = albumPageCommentFile.lastModified();
		long albumPageUpdateTime = albumPageService.getAlbumPageEntity(albumPageID).getUpdateDate().getTime();
		if( (albumPageUpdateTime>fileUpdateTime)&&(!force) ){
			System.out.println(albumPageCommentFile.getName() + "：更新日付がDBより古いのでスキップします");
			return;
		}

		// コメントファイルを読み込む
		String comment = readTextFile( albumPageCommentFile );
		comment = HTMLUtil.htmlspecialchars(comment);
		
		// コンテンツグループのプロパティを書きかえる
		ContentsGroupEntity contentsGroupEntity = dataStructureBusiness.getContentsGroup(albumPageID);
		contentsGroupEntity.setDescription(comment);
		dataStructureBusiness.updateContentsGroup(contentsGroupEntity);
		
		// コメントファイルを出力する
		contentsFileUtil.writeAlbumPageComment(albumPageID);
	}

	/**
	 * 指定したアルバムページIDに対して、指定のコンテンツファイルをインポートします。
	 * @param albumPageID
	 * @param contentsFile
	 * @throws IOException 
	 * @throws InvalidParameterException 
	 * @throws InvalidStateException 
	 * @throws RecordNotFoundException 
	 */
	private static void importContentsFile(Integer albumPageID, File contentsFile, Boolean force) throws IOException, RecordNotFoundException, InvalidStateException, InvalidParameterException {
		// コンテンツファイルの情報収集
		String contentsFileName = contentsFile.getName();
		String contentsFilePath = contentsFile.getAbsolutePath();

		// コメントファイルの情報収集
		String commentFilePath = contentsFile.getAbsolutePath()+".comment";
		File commentFile = new File( commentFilePath );
		
		// 既存コンテンツなら、コンテンツIDを取得する
		Integer contentsID = null;
		ContentsEntity contentsEntity = albumPageService.getContentsEntityByFilename(albumPageID, contentsFileName);
		if( contentsEntity!=null ){
			contentsID = contentsEntity.getContentsID();
		}
		if( contentsID!=null ){
			// コンテンツファイル、もしくはコメントファイルのいずれかがDBより新しければ更新。
			// そうでなければスキップ。
			long fileUpdateTime = contentsFile.lastModified();
			if(commentFile.exists()&&(commentFile.lastModified()>fileUpdateTime)){
				fileUpdateTime = commentFile.lastModified();
			}

			long contentsUpdateTime = albumPageService.getContentsEntity(albumPageID, contentsID).getUpdateDate().getTime();
			if( (contentsUpdateTime>fileUpdateTime)&&(!force) ){
				System.out.println(contentsFileName+"：更新日付がDBより古いのでスキップします");
				return;
			}
		}
		
		// 一時ファイル名を作って、もとファイルをコピー
		System.out.println( contentsFile.getName() );
		File tempFile = File.createTempFile("tmp", ".tmp");
		FileUtils.copyFile(contentsFile, tempFile);

		// 登録
		if( contentsID==null ){
			contentsID = albumPageService.registContents(albumPageID, tempFile.getAbsoluteFile(), contentsFileName);
		} else {
			albumPageService.updateContents(albumPageID, contentsID, tempFile.getAbsoluteFile(), contentsFileName);
		}
		
		// コメントを処理する
		if( commentFile.exists() ){
			// コメントファイルが見つかったら処理する
			importContentsComment(contentsID, commentFile);
		}
	}

	/**
	 * コンテンツに対応するコメントファイルをインポートします
	 * @param contentsID
	 * @param commentFile
	 * @throws IOException
	 * @throws InvalidStateException
	 * @throws RecordNotFoundException
	 * @throws InvalidParameterException
	 */
	private static void importContentsComment(Integer contentsID,
			File commentFile) throws IOException, InvalidStateException,
			RecordNotFoundException, InvalidParameterException {
		String comment = readTextFile( commentFile );
		comment = HTMLUtil.htmlspecialchars(comment);
		
		// コンテンツのプロパティを書きかえる
		ContentsEntity contentsEntity = dataStructureBusiness.getContentsByContentsID(contentsID);
		contentsEntity.setDescription(comment);
		dataStructureBusiness.updateContents(contentsEntity);
		
		// コメントファイルを出力する
		contentsFileUtil.writeContentsComment(contentsID);
	}

	/**
	 * テキストファイルの内容を読み込んで、Stringに格納して返します。
	 * @param file 読み込むファイル
	 * @return テキストファイルの内容
	 * @throws IOException I/Oエラー
	 */
	private static String readTextFile( File file ) throws IOException{
		String path = file.getAbsolutePath();

		FileInputStream fis = new FileInputStream(path);
		InputStreamReader in = new InputStreamReader(fis, "UTF-8");
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
