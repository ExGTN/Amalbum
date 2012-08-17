-- Project Name : amalbum
-- Date/Time    : 2011/12/04 16:57:17
-- Author       : GTN
-- RDBMS Type   : PostgreSQL
-- Application  : A5:SQL Mk-2

-- バックアップルール
drop table BackupRule cascade;

create table BackupRule (
  BackupRuleID integer not null
  , BackupType integer not null
  , Year integer default 1900 not null
  , Month integer default 1 not null
  , Day integer default 1 not null
  , Hour integer default 0 not null
  , Minute integer default 0 not null
  , constraint BackupRule_PKC primary key (BackupRuleID,BackupType)
) ;

-- バックアップマスター
drop table BackupMaster cascade;

create table BackupMaster (
  BackupType integer not null
  , Name character varying not null
  , constraint BackupMaster_PKC primary key (BackupType)
) ;

-- マテリアル公開権限
drop table MaterialRole cascade;

create table MaterialRole (
  MaterialID integer not null
  , RoleID integer not null
  , constraint MaterialRole_PKC primary key (MaterialID,RoleID)
) ;

-- マテリアル
drop table Material cascade;

create table Material (
  MaterialID integer not null
  , ContentsID integer not null
  , MaterialType integer not null
  , Path character varying not null
  , Status integer not null
  , Brief character varying
  , Description character varying
  , SeqNo integer not null
  , DeleteDate timestamp default null
  , UpdateDate timestamp not null
  , CreateDate timestamp not null
  , constraint Material_PKC primary key (MaterialID)
) ;

-- コンテンツ公開権限
drop table ContentsRole cascade;

create table ContentsRole (
  ContentsID integer not null
  , RoleID integer not null
  , constraint ContentsRole_PKC primary key (ContentsID,RoleID)
) ;

-- 権限マスター
drop table RoleMaster cascade;

create table RoleMaster (
  RoleID integer not null
  , RoleName character varying not null
  , Description character varying
  , Value integer not null
  , constraint RoleMaster_PKC primary key (RoleID)
) ;

-- コンテンツグループ
drop table ContentsGroup cascade;

create table ContentsGroup (
  ContentsGroupID integer
  , ParentID integer not null
  , Name character varying not null
  , Brief character varying
  , Description character varying
  , SeqNo integer not null
  , DeleteDate timestamp default null
  , UpdateDate timestamp not null
  , CreateDate timestamp not null
  , constraint ContentsGroup_PKC primary key (ContentsGroupID)
) ;

-- コンテンツ
drop table Contents cascade;

create table Contents (
  ContentsID integer not null
  , ContentsGroupID integer not null
  , Name character varying not null
  , Brief character varying
  , Description character varying
  , BaseDir character varying not null
  , SeqNo integer not null
  , DeleteDate timestamp default null
  , UpdateDate timestamp not null
  , CreateDate timestamp not null
  , constraint Contents_PKC primary key (ContentsID)
) ;

-- ユーザ権限
drop table UserRole cascade;

create table UserRole (
  RoleID integer not null
  , UserID character varying not null
  , Value integer not null
  , constraint UserRole_PKC primary key (RoleID,UserID)
) ;

-- ユーザ
drop table LoginUser cascade;

create table LoginUser (
  UserID character varying not null
  , EncPassword character varying not null
  , UserName character varying not null
  , MailAddress character varying not null
  , SeqNo integer not null
  , DeleteDate timestamp default null
  , UpdateDate timestamp not null
  , CreateDate timestamp not null
  , constraint LoginUser_PKC primary key (UserID)
) ;

comment on table BackupRule is 'バックアップルール';
comment on column BackupRule.BackupRuleID is 'バックアップルールID';
comment on column BackupRule.BackupType is 'バックアップタイプ';
comment on column BackupRule.Year is '年';
comment on column BackupRule.Month is '月';
comment on column BackupRule.Day is '日';
comment on column BackupRule.Hour is '時';
comment on column BackupRule.Minute is '分';

comment on table BackupMaster is 'バックアップマスター';
comment on column BackupMaster.BackupType is 'バックアップタイプ';
comment on column BackupMaster.Name is 'バックアップ名';

comment on table MaterialRole is 'マテリアル公開権限';
comment on column MaterialRole.MaterialID is 'マテリアルID';
comment on column MaterialRole.RoleID is '権限ID';

comment on table Material is 'マテリアル	 画像データ，映像データ，音声データなど';
comment on column Material.MaterialID is 'マテリアルID';
comment on column Material.ContentsID is 'コンテンツID';
comment on column Material.MaterialType is 'タイプ';
comment on column Material.Path is 'ファイルパス';
comment on column Material.Status is 'ステータス';
comment on column Material.Brief is '説明';
comment on column Material.Description is '詳細説明';
comment on column Material.SeqNo is '表示順';
comment on column Material.DeleteDate is '削除日付';
comment on column Material.UpdateDate is '更新日付';
comment on column Material.CreateDate is '作成日付';

comment on table ContentsRole is 'コンテンツ公開権限';
comment on column ContentsRole.ContentsID is 'コンテンツID';
comment on column ContentsRole.RoleID is '権限ID';

comment on table RoleMaster is '権限マスター	 権限マスター';
comment on column RoleMaster.RoleID is '権限ID';
comment on column RoleMaster.RoleName is '権限名';
comment on column RoleMaster.Description is '権限説明';
comment on column RoleMaster.Value is '権限評価値';

comment on table ContentsGroup is 'コンテンツグループ	 コンテンツのグループ';
comment on column ContentsGroup.ContentsGroupID is 'コンテンツグループID';
comment on column ContentsGroup.ParentID is '親コンテンツグループID';
comment on column ContentsGroup.Name is 'コンテンツグループ名';
comment on column ContentsGroup.Brief is '説明';
comment on column ContentsGroup.Description is '詳細説明';
comment on column ContentsGroup.SeqNo is '表示順';
comment on column ContentsGroup.DeleteDate is '削除日付';
comment on column ContentsGroup.UpdateDate is '更新日付';
comment on column ContentsGroup.CreateDate is '作成日付';

comment on table Contents is 'コンテンツ	 コンテンツ情報';
comment on column Contents.ContentsID is 'コンテンツID';
comment on column Contents.ContentsGroupID is 'コンテンツグループID';
comment on column Contents.Name is 'コンテンツ名';
comment on column Contents.Brief is '説明';
comment on column Contents.Description is '詳細説明';
comment on column Contents.BaseDir is 'ベースディレクトリ';
comment on column Contents.SeqNo is '表示順';
comment on column Contents.DeleteDate is '削除日付';
comment on column Contents.UpdateDate is '更新日付';
comment on column Contents.CreateDate is '作成日付';

comment on table UserRole is 'ユーザ権限	 ユーザ権限';
comment on column UserRole.RoleID is '権限ID';
comment on column UserRole.UserID is 'ユーザID';
comment on column UserRole.Value is '権限値';

comment on table LoginUser is 'ユーザ	 ユーザ情報';
comment on column LoginUser.UserID is 'ユーザID';
comment on column LoginUser.EncPassword is '暗号化パスワード';
comment on column LoginUser.UserName is 'ユーザ名';
comment on column LoginUser.MailAddress is 'メールアドレス';
comment on column LoginUser.SeqNo is '表示順';
comment on column LoginUser.DeleteDate is '削除日付';
comment on column LoginUser.UpdateDate is '更新日付';
comment on column LoginUser.CreateDate is '作成日付';
