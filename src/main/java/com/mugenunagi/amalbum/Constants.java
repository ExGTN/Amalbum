package com.mugenunagi.amalbum;

public class Constants {
	public static final int rootContentsGroupID = 0;

	/**
	 * マテリアルの種類の定義
	 * @author gtn
	 *
	 */
	public enum MaterialType {
		  Photo(0)
		, Thumbnail(1);
		
		private Integer value;
		MaterialType(Integer value){ this.value=value;}
		public Integer getValue(){ return value; }
	};

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
}
