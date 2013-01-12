package com.mugenunagi.gtnlib.html;

/**
 * HTMLに関係する処理のUtilクラス
 * 
 * @author gtn
 * 
 */
public class HTMLUtil {
	static String[] escape  = { "&",     "<"   , ">"   , "\""    , "\'"   , "\n"  , "\t" };
	static String[] replace = { "&amp;", "&lt;", "&gt;", "&quot;", "&#39;", "<br>", "&#x0009;" };

	/**
	 * HTMLの特殊文字を参照表現にします
	 * @param str
	 * @return
	 */
	public static String htmlspecialchars(String str) {
		String ret_val = str.replaceAll("\r\n","\n");
		ret_val = ret_val.replaceAll("\r","\n");


		for (int i = 0; i < escape.length; i++) {
			ret_val = ret_val.replaceAll(escape[i], replace[i]);
		}

		return ret_val;
	}

	/**
	 * HTMLの参照文字をもとの表現にもどします
	 * @param str
	 * @return
	 */
	public static String decodeHtmlSpecialChars(String str) {
		if( str==null ){ return null; }
		String ret_val = new String(str);
		
		for( int i=0;i<replace.length;i++ ){
			ret_val = ret_val.replaceAll(replace[i], escape[i]);
		}
		
		return ret_val;
	}
}
