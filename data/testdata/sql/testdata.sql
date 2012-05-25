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

