function checkExt(obj) {
	var forbidName = [ 'exe', 'java', 'jsp', 'js', 'class' ];
	var file_name = obj.value;
	var ext = file_name.substring(file_name.lastIndexOf('.') + 1);
	
	forbidName.forEach(function(n){
		if(n == ext){
			alert( ext + "확장자 파일은 업로드 정책에 위배됩니다");
			return false;
		}
	});
	return true;

}
