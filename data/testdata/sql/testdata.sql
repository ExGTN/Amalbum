--
-- 既存データを削除
--
delete from material;
delete from contents;

-- =============================================================================
-- 単独の素材データ
-- =============================================================================
insert into material (
     materialid
    ,contentsid
    ,materialtype
    ,path
    ,status
    ,brief
    ,description
    ,seqno
    ,deletedate
    ,updatedate
    ,createdate
 ) values (
     nextval( 'materialidsequence' )
    ,-1
    ,0
    ,'120524/NEC_1274.JPG'
    ,0
    ,'Test'
    ,'テスト'
    ,1
    ,null
    ,now()
    ,now()
 );


-- =============================================================================
-- コンテンツ単独表示のデータ
-- =============================================================================
--
-- コンテンツグループのデータ
--
insert into contentsgroup (
	 contentsgroupid
	,parentid
	,name
	,brief
	,description
	,seqno
	,deletedate
	,updatedate
	,createdate
) values (
	 nextval( 'contentsgroupidsequence' )
	,-1
	,'Test'
	,'TestPage'
	,'This is test page.'
	,1
	,null
	,now()
	,now()
);


--
-- コンテンツのデータ - コンテンツ１
--
insert into contents (
     contentsid
    ,contentsgroupid
    ,name
    ,brief
    ,description
    ,basedir
    ,seqno
    ,deletedate
    ,updatedate
    ,createdate
 ) values (
     nextval( 'contentsidsequence' )
    ,currval( 'contentsgroupidsequence' )
    ,'TestName'
    ,'TestBrief'
    ,'TestDescription'
    ,'120524'
    ,1
    ,null
    ,now()
    ,now()
 );


--
-- 素材データ
--
insert into material (
     materialid
    ,contentsid
    ,materialtype
    ,path
    ,status
    ,brief
    ,description
    ,seqno
    ,deletedate
    ,updatedate
    ,createdate
 ) values (
     nextval( 'materialidsequence' )
    ,currval( 'contentsidsequence' )
    ,0
    ,'120524/NEC_1274.JPG'
    ,0
    ,'Test'
    ,'テスト'
    ,1
    ,null
    ,now()
    ,now()
 );


--
-- コンテンツのデータ - コンテンツ２
--
insert into contents (
     contentsid
    ,contentsgroupid
    ,name
    ,brief
    ,description
    ,basedir
    ,seqno
    ,deletedate
    ,updatedate
    ,createdate
 ) values (
     nextval( 'contentsidsequence' )
    ,currval( 'contentsgroupidsequence' )
    ,'TestName'
    ,'TestBrief'
    ,'TestDescription'
    ,'120524'
    ,2
    ,null
    ,now()
    ,now()
 );


--
-- 素材データ２
--
insert into material (
     materialid
    ,contentsid
    ,materialtype
    ,path
    ,status
    ,brief
    ,description
    ,seqno
    ,deletedate
    ,updatedate
    ,createdate
 ) values (
     nextval( 'materialidsequence' )
    ,currval( 'contentsidsequence' )
    ,0
    ,'120524/NEC_1133.JPG'
    ,0
    ,'Test'
    ,'テスト'
    ,1
    ,null
    ,now()
    ,now()
 );
