function FileNameCheck( str ) {
	if( str.match( /^.*[(\\|/|:|\*|?|\"|<|>|\|)].*$/ ) ) {
		alert("正しいファイル名を指定してください。");
		return false;
	} else if (str=="") {
		alert("ファイル名を入力してください。");
		return false;
	}
	return true;
}
