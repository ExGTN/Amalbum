package com.mugenunagi.amalbum.datastructure.dao;

import java.util.List;

import com.mugenunagi.amalbum.datastructure.entity.LoginUserEntity;
import com.mugenunagi.amalbum.datastructure.entity.MaterialEntity;

/**
 * LoginUserを取り扱うマッパー
 * @author GTN
 *
 */
public interface LoginUserMapper {
	/**
	 * ログインユーザー情報を取得する
	 * @param userID
	 * @return
	 */
	LoginUserEntity getLoginUser( String userID );

}
