package com.mugenunagi.amalbum.datastructure;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.datastructure.condition.ContentsGroupCondition;
import com.mugenunagi.amalbum.datastructure.dao.ContentsGroupMapper;
import com.mugenunagi.amalbum.datastructure.dao.ContentsMapper;
import com.mugenunagi.amalbum.datastructure.dao.LoginUserMapper;
import com.mugenunagi.amalbum.datastructure.dao.MaterialMapper;
import com.mugenunagi.amalbum.datastructure.entity.ContentsEntity;
import com.mugenunagi.amalbum.datastructure.entity.ContentsGroupEntity;
import com.mugenunagi.amalbum.datastructure.entity.LoginUserEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;
import com.mugenunagi.amalbum.exception.InvalidStateException;
import com.mugenunagi.amalbum.exception.RecordNotFoundException;

/**
 * データ構造を取り扱うビジネスクラス
 * @author GTN
 *
 */
@Component
public class DataStructureBusiness {
	//=========================================================================
	// 属性
	//=========================================================================
	@Autowired
	ContentsGroupMapper contentsGroupMapper;

	@Autowired
	ContentsMapper contentsMapper;

	@Autowired
	MaterialMapper materialMapper;
	
	@Autowired
	LoginUserMapper loginUserMapper;
	
	//=========================================================================
	// メソッド
	//=========================================================================
	/**
	 * コンテンツグループIDを指定して、コンテンツグループを取得する
	 * @throws RecordNotFoundException 
	 */
	public ContentsGroupEntity getContentsGroup( int contentsGroupID ) throws RecordNotFoundException{
		// コンテンツグループIDを指定して、コンテンツグループを取得する
		ContentsGroupEntity contentsGroupEntity = contentsGroupMapper.selectContentsGroupByContentsGroupID(contentsGroupID);
		if( contentsGroupEntity==null ){
			throw new RecordNotFoundException( "指定されたコンテンツグループIDのレコードが存在しませんでした。ID="+contentsGroupID );
		}
		
		// 結果を返す
		return contentsGroupEntity;
	}
	
	/**
	 * 親IDを指定して、コンテンツグループの一覧を検索して返す
	 * @param parentID
	 * @return
	 */
	public List<ContentsGroupEntity> getContentsGroupListByParentID( int parentID ){
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsGroupEntity contentsGroupEntity = new ContentsGroupEntity();
		contentsGroupEntity.setParentID(parentID);
		
		// 検索
		List<ContentsGroupEntity> contentsGroupEntityList = contentsGroupMapper.selectContentsGroupByParentID( parentID );
		
		// -----< 結果を返す >-----
		//
		return contentsGroupEntityList;
	}
	

	/**
	 * 指定した条件でコンテンツグループを取得します
	 * @param condition
	 * @return
	 */
	public List<ContentsGroupEntity> getContentsGroupListByCondition( Integer parentID, Integer limit, Integer offset ){
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsGroupCondition contentsGroupCondition = new ContentsGroupCondition();
		contentsGroupCondition.setContentsGroupID( null );
		contentsGroupCondition.setParentID( parentID );
		contentsGroupCondition.setLimit(limit);
		contentsGroupCondition.setOffset(offset);

		// 検索
		List<ContentsGroupEntity> contentsGroupEntityList = contentsGroupMapper.selectContentsGroupByCondition(contentsGroupCondition);
		
		// -----< 結果を返す >-----
		//
		return contentsGroupEntityList;
	}


	/**
	 * 親IDを指定して、コンテンツの一覧を検索して返す
	 * @param parentID
	 * @return
	 */
	public List<ContentsEntity> getContentsListByParentID( int parentID ){
		// -----< 検索する >-----
		//
		// 検索
		List<ContentsEntity> contentsEntityList = contentsMapper.selectContentsByContentsGroupID( parentID );
		
		// -----< 結果を返す >-----
		//
		return contentsEntityList;
	}

	
	/**
	 * 親IDを指定して、個ノードの数を得る
	 * @param parentID
	 * @return
	 */
	public Integer getContentsGroupCount( Integer parentID ){
		Integer count = contentsGroupMapper.getContentsGroupCountByParentID(parentID);
		return count;
	}

	/**
	 * IDを指定して、コンテンツを検索して返す
	 * @param parentID
	 * @return
	 */
	public ContentsEntity getContentsByContentsID( int contentsID ){
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsID(contentsID);
		
		// 検索
		ContentsEntity result = contentsMapper.getContentsByContentsID( contentsEntity );
		
		// -----< 結果を返す >-----
		//
		return result;
	}

	/**
	 * ファイル名を指定して、コンテンツを検索して返す
	 * @param parentID
	 * @return
	 * @throws InvalidStateException 
	 */
	public ContentsEntity getContentsByContentsName( Integer contentsGroupID, String contentsName ) throws InvalidStateException{
		// -----< 検索する >-----
		//
		// 検索条件を作る
		ContentsEntity contentsEntity = new ContentsEntity();
		contentsEntity.setContentsGroupID(contentsGroupID);
		contentsEntity.setName(contentsName);
		
		// 検索
		List<ContentsEntity> result = contentsMapper.getContentsByContentsName( contentsEntity );
		if(result.size()>=2){
			throw new InvalidStateException("２個以上のレコードがヒットしました");
		}
		
		// -----< 結果を返す >-----
		//
		if( result.size()==0 ){
			return null;
		}
		return result.get(0);
	}


        /**
         * IDを指定して、マテリアルを検索して返す
         * @param parentID
         * @return
         */
        public MaterialEntity getMaterialByMaterialID( int materialID ){
            // -----< 検索する >-----
            //
            // 検索
            MaterialEntity result = materialMapper.getMaterialByMaterialID( materialID );

            // -----< 結果を返す >-----
            //
            return result;
        }


	/**
	 * コンテンツグループを作成する
	 * @param contentsGroupEntity
	 */
	public void createContentsGroup( ContentsGroupEntity contentsGroupEntity ) {
		// IDを発行する
		int contentsGroupID = contentsGroupMapper.getNextContentsGroupID();
		
		// インサートする
		contentsGroupEntity.setContentsGroupID(contentsGroupID);
		contentsGroupMapper.insertContentsGroup(contentsGroupEntity);
		
		// SeqNoを更新する
		Integer nextSeqNo = contentsGroupMapper.getNextSeqNo( contentsGroupID );
		if( nextSeqNo==null ){
			nextSeqNo = 1;
		}
		contentsGroupEntity.setSeqNo(nextSeqNo);
		
		// 更新する
		contentsGroupMapper.updateContentsGroup(contentsGroupEntity);
	}

	/**
	 * 指定されたコンテンツIDを親に持つ素材の一覧を返します
	 * @param contentsID
	 * @return
	 */
	public List<MaterialEntity> getMaterialListByContentsID( int contentsID ){
		List<MaterialEntity> materialList = materialMapper.selectMaterialByContentsID(contentsID);
		return materialList;
	}
	
	/**
	 * 指定されたコンテンツグループ、およびそれに紐付くコンテンツ、マテリアルをすべて削除します。
	 * @param contentsGroupID
	 */
	public void deleteWholeContentsGroup( Integer contentsGroupID ){
		// コンテンツの一覧を取得する
		List<ContentsEntity> contentsList = contentsMapper.selectContentsByContentsGroupID(contentsGroupID);
		
		// コンテンツに含まれるマテリアルを順次削除する
		for( ContentsEntity contents : contentsList ){
			if( contents==null ){ continue; }
			Integer contentsID = contents.getContentsID();
			deleteWholeContents( contentsID );
		}
		
		// ContentsGroupを消す
		contentsGroupMapper.deleteContentsGroup(contentsGroupID);
	}

	/**
	 * 指定されたコンテンツ、およびそれに紐付くマテリアルをすべて削除します。
	 * @param contentsID
	 */
	public void deleteWholeContents( Integer contentsID ){
		// マテリアルの一覧を取得する
		List<MaterialEntity> materialList = materialMapper.selectMaterialByContentsID(contentsID);
		
		// マテリアルを順次削除する
		for( MaterialEntity material : materialList ){
			if( material==null ){ continue; }
			
			// データベース上のレコードを物理削除する
			Integer materialID = material.getMaterialID();
			materialMapper.deleteMaterial( materialID );
		}
		
		// Contentsを削除する
		contentsMapper.deleteContents( contentsID );
	}

	/**
	 * 指定された親を持つコンテンツグループで、子のコンテンツグループ名が指定された名称であるものを選択して返す
	 * @param albumID
	 * @param albumPageName
	 * @return
	 */
	public Integer getContentsGroupID(Integer parentID, String name) {
		ContentsGroupEntity entity = new ContentsGroupEntity();
		entity.setParentID(parentID);
		entity.setName(name);
		Integer id = contentsGroupMapper.getContentsGroupID( entity );
		return id;
	}

	/**
	 * 指定されたContentsGroupの子として、ContentsGroupを登録する。
	 * @param albumID
	 * @param albumPageName
	 */
	public Integer createContentsGroup(Integer parentID, String contentsGroupName) {
		// 発番する
		Integer contentsGroupID = contentsGroupMapper.getNextContentsGroupID();
		Integer seqNo = contentsGroupMapper.getNextSeqNo( parentID );
		if( seqNo==null ){ seqNo = 1; }
		Date date = new Date();
		
		ContentsGroupEntity entity = new ContentsGroupEntity();

		entity.setContentsGroupID( contentsGroupID );	// コンテンツグループID
		entity.setParentID( parentID );	// 親コンテンツグループID
		entity.setName( contentsGroupName );	// コンテンツグループ名
		entity.setBrief( null );	// 説明
		entity.setDescription( null );	// 詳細説明
		entity.setSeqNo(seqNo);	// 表示順
		entity.setDeleteDate( null );	// 削除日付
		entity.setUpdateDate( date );	// 更新日付
		entity.setCreateDate( date );	// 作成日付

		contentsGroupMapper.insertContentsGroup(entity);
		
		return contentsGroupID;
	}
	
	/**
	 * コンテンツの内容を更新する
	 * @param contentsEntity
	 */
	public void updateContents( ContentsEntity contentsEntity ){
		contentsMapper.updateContents(contentsEntity);
	}
	
	/**
	 * コンテンツグループの内容を更新する
	 * @param contentsEntity
	 */
	public void updateContentsGroup( ContentsGroupEntity contentsGroupEntity ){
		contentsGroupMapper.updateContentsGroup(contentsGroupEntity);
	}

	/**
	 * パスワードチェック
	 * @param userID
	 * @param passwordMD5
	 * @return
	 */
	public Boolean checkPassword(String userID, String passwordMD5) {
		// 入力確認
		if( userID==null ){ return false; }
		if( passwordMD5==null ){ return false; }
		
		// ユーザ情報を取得する
		LoginUserEntity loginUserEntity = loginUserMapper.getLoginUser( userID );
		if( loginUserEntity==null ){ return false; }
		
		// パスワードを確認する
		String target = loginUserEntity.getEncPassword();
		if( !passwordMD5.equals(target) ){
			return false;
		}
		return true;
		
	}
}
