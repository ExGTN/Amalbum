package com.mugenunagi.amalbum.datastructure.dao;

/**
 * Materialを取り扱うマッパー
 * @author GTN
 *
 */
public interface SequenceMapper {
	/**
	 * 次のマテリアルIDを取得します
	 * @return
	 */
	Integer getNextMaterialID();

	/**
	 * 次のコンテンツIDを取得します
	 * @return
	 */
	Integer getNextContentsID();
	
	/**
	 * 次のコンテンツグループIDを取得します
	 * @return
	 */
	Integer getNextContentsGroupID();
}
