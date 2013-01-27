insert into ContentsGroup ( ContentsGroupID, ParentID, Name, Brief, Description, SeqNo, DeleteDate, UpdateDate, CreateDate )
 values ( 0, -1, 'Amalbum', 'デフォルトのアルバム', 'デフォルトのアルバム', 1, null, now(), now() );

insert into LoginUser (UserID,EncPassword,UserName,MailAddress,SeqNo,DeleteDate,UpdateDate,CreateDate)
 values ('auagcu','b07b3ef46b3577aed05829cbf50ea164','Guest','name@example.com',0,null,now(),now());
