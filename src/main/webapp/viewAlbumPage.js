function onRemakeThumbs(){
	result = confirm( "サムネイルの再作成します。\nよろしいですか？" );
	if( result==true ){
		document.remakeThumbsForm.submit();
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
