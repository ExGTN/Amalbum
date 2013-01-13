function onDeleteThumbs(){
	result = confirm( "サムネイルの再作成のため、既存サムネイルを削除します。\n本当に削除しますか？" );
	if( result==true ){
		document.deleteThumbsForm.submit();
	}
}

function onDeleteFile( objForm ){
	result = confirm( "写真を削除します。\n削除すると元に戻せませんが、本当に削除しますか？" );
	if( result==true ){
		objForm.submit();
	}
}

function onDeleteDir(){
	result = confirm( "このディレクトリの写真を全て削除します。\n削除すると元に戻せませんが、本当に削除しますか？" );
	if( result==true ){
		document.deleteDirForm.submit();
	}
}
