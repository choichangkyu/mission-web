<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/Mission-Web/assets/css/add_user_form.css">
<script src="/Mission-Web/assets/js/httpRequest.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var is_idCheck = false;
		$("form[name=signform]").submit(function() {
			return sign_check();
		});
		
		$("input[name=idCheck]").click(function(){
			idCheck();
		});
		
		function idCheck(){
			var id = document.signform.id;
			var params = {
					id : id.value
				};
			sendProcess("GET", "/Mission-Web/jsp/member/all_user_data.jsp", params, callback);
		}
		
		function callback(){
			if(httpRequest.readyState == 4){
				if(httpRequest.status == 200){
					var memberList = eval(httpRequest.responseText.trim());
					
					var id = document.signform.id.value;
					if(memberList[0]['id'] == id){
						debugTrace("중복된 아이디입니다");
						is_idCheck = false;
					} else {
						debugTrace("");
						is_idCheck = true;
					}
					
				}
			}
		}
		
		function debugTrace(msg){ // id 중복체크 메세지
			var debug = document.querySelector('div#id span');
			debug.innerHTML = msg;
		}
		
		function sign_check() {
			var is_ok = true;

			//아이디
			if (!check_value("id", true, 20) || !is_idCheck)
				is_ok = false;
			//이름
			if (!check_value("name", true, 20))
				is_ok = false;
			//비밀번호
			if (!check_value("password", true, 20))
				is_ok = false;

			//이메일
			if (!check_value("email", false, 50))
				is_ok = false;

			//전화번호
			var tell_value = document.signform.tel1.value;
			var tel2_value = document.signform.tel2.value;
			var tel3_value = document.signform.tel3.value;
			var tel1_error_msg = document
					.querySelector('div#tel span');

			if (tell_value.length > 3 || tel2_value.length > 4
					|| tel3_value.length > 4) {
				is_ok = false;
				tel1_error_msg.innerText = '3자,4자,4자 이하로 입력해주세요';
			} else {
				tel1_error_msg.innerText = '';
			}

			//우편번호
			if (!check_value("post", false, 7))
				is_ok = false;
			//기본주소
			if (!check_value("basic_addr", false, 200))
				is_ok = false;
			//상세주소
			if (!check_value("detail_addr", false, 200))
				is_ok = false;

			if (is_ok) {
				return is_ok;
			} else {
				return is_ok;
			}
		};

		function check_value(type, required, leng) {
			var value = $("form input[name=" + type + "]").val();
			var error_msg = $("form div#" + type + " span");

			if (!value && required) {
				switch (type) {
				case "id":
					$(error_msg).text('아이디를 입력하세요');
					break;
				case "password":
					$(error_msg).text('비밀번호를 입력하세요');
					break;
				case "name":
					$(error_msg).text('이름을 입력하세요');
					break;
				}
				return false;
			} else if (type == "email") {
				if (value.indexOf("@") > 30) {
					$(error_msg).text(
							"이메일 아이디를 " + 30 + "자 이하로 입력해주세요");
					return false;

				} else if (value.length
						- (value.indexOf("@") + 1) > 20) {
					$(error_msg).text(
							"이메일 도메인을 " + 20 + "자 이하로 입력해주세요");
					return false;
				} else {
					$(error_msg).text('');
				}
			} else if (value.length > leng) {
				$(error_msg).text(leng + "자 이하로 입력해주세요");
				return false;
			} else {
				if(type=="id" && !is_idCheck){
					$(error_msg).text("중복체크를 진행해주세요");
					return false;
				}
					
				$(error_msg).text('');
			}
			return true;
		};
	});
</script>
<hr>
<div id="add_user_form">
	<h3>회원가입</h3>
	<form action="/Mission-Web/jsp/member/add_user.jsp" name="signform"
		method="post">
		<div id="id">
			<label for="id">* 아이디 : </label><input type="text" name="id">
			<input type="button" value="중복체크" name="idCheck"><br>
			<span class="error_msg"></span><br>
		</div>
		<div id="name">
			<label for="name">* 이름 : </label><input type="text" name="name"><br>
			<span class="error_msg"></span><br>
		</div>

		<div id="password">
			<label for="password">* 비밀번호 : </label><input type="password"
				name="password"><br> <span class="error_msg"></span><br>
		</div>

		<div id="email">
			<label for="email">이메일 : </label><input type="email" name="email"
				placeholder="xxx@xxx.com" pattern="{30}@{20}" /><br> <span
				class="error_msg"></span><br>
		</div>

		<div id="tel">
			<label for="tel1">전화번호 : </label> <input type="text" name="tel1">
			<input type="text" name="tel2"> <input type="text"
				name="tel3"><br> <span class="error_msg"></span><br>
		</div>

		<div id="post">
			<label for="post">우편번호 : </label><input type="text" name="post"><br>
			<span class="error_msg"></span><br>
		</div>

		<div id="basic_addr">
			<label for="basic_addr">기본주소 : </label><input type="text"
				name="basic_addr"><br> <span class="error_msg"></span><br>
		</div>
		<div id="detail_addr">
			<label for="detail_addr">상세주소 : </label><input type="text"
				name="detail_addr"><br> <span class="error_msg"></span><br>
		</div>

		<input type="submit" value="회원가입" name="submit">
	</form>
</div>