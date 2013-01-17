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
	 * マテリアルの次のSeqNoを取得します
	 * @param contentsID
	 * @return
	 */
	Integer getNextMaterialSeqNo( Integer contentsID );

	/**
	 * 次のコンテンツIDを取得します
	 * @return
	 */
	Integer getNextContentsID();
	
	/**
	 * コンテンツの次のSeqNoを取得します
	 * @param contentsGroupID
	 * @return
	 */
	Integer getNextContentsSeqNo( Integer contentsGroupID );
	
	/**
	 * 次のコンテンツグループIDを取得します
	 * @return
	 */
	Integer getNextContentsGroupID();
	
	/**
	 * コンテンツグループの次のSeqNoを取得します
	 * @param parentID
	 * @return
	 */
	Integer getNextContentsGroupSeqNo( Integer parentID );
}
