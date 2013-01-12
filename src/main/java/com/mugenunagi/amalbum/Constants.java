package com.mugenunagi.amalbum;

import java.util.HashMap;
import java.util.Map;

/**
 * 定数定義のクラス
 * @author gtn
 *
 */
public class Constants {
	// -----< 定義 >-----
	//
	public static final int undefined = -1;
	public static final int rootContentsGroupID = 0;

	// -----< enum定義 >-----
	//
	/**
	 * マテリアルの種類の定義
	 * @author gtn
	 *
	 */
	public enum MaterialType {
		  Photo(0)
		, Thumbnail(1)
		, Movie(2)
		, MovieThumbnail(3);
		
		private Integer value;
		MaterialType(Integer value){ this.value=value;}
		public Integer getValue(){ return value; }
	};

	/**
	 * コンテンツの種類の定義
	 * @author gtn
	 *
	 */
	public enum ContentsType {
		  Photo( 0 )
		, Movie( 1 );
		  
		  private Integer value;
		  ContentsType(Integer value){ this.value=value; }
		  public Integer getValue(){ return value; }
	}
	public static Map<Integer, ContentsType> ContentsTypeMap = new HashMap<Integer, ContentsType>();
	static{
		ContentsTypeMap.put( ContentsType.Photo.value, ContentsType.Photo );
		ContentsTypeMap.put( ContentsType.Movie.value, ContentsType.Movie );
	}

	/**
	 * DBへの登録状況を示すステータスコード
	 * @author gtn
	 *
	 */
	public enum StatusCode {
		  Normal(0);
		
		private Integer value;
		StatusCode(Integer value){ this.value=value;}
		public Integer getValue(){ return value; }
	};

	// -----< DIで注入されるプロパティの定義 >-----
	//
	/**
	 * ApplicationPropertiesの名前
	 */
	private String applicationPropertiesName;

	/**
	 * @return the applicationPropertiesName
	 */
	public String getApplicationPropertiesName() {
		return applicationPropertiesName;
	}

	/**
	 * @param applicationPropertiesName the applicationPropertiesName to set
	 */
	public void setApplicationPropertiesName(String applicationPropertiesName) {
		this.applicationPropertiesName = applicationPropertiesName;
	}
}
