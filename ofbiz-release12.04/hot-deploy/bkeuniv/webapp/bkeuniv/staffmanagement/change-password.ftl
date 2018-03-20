<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resource/bkeuniv/css/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<#assign staffId=staff.staff.staffId />
<!--
ID: ${staff.staff.staffId}
-->
<style>
.form-change-password {
    margin: auto;
    width: 30%;
    border: 3px solid #73AD21;
    padding: 10px;
}
</style>

<div id="form-change-password" class="form-change-password">
	<div class="inline-box" style="width: 100%; padding: 10px 0px;">
		<div style="display: inline-block;width: 30%;">
			Mat Khau 
		</div>
		<div style="display: inline-block;width: 100%;">
			<input type="password" id="password" style="width: 100%" type="text" width="1000"/>
		</div>
		<div style="display: inline-block;width: 100%;">
			<input id="staff-id" style="width: 100%" type="text" width="1000" value="${staffId}" hidden/>
		</div>
	</div>
	
	<div class="inline-box" style="width: 100%; padding: 10px 0px;">
		<div style="display: inline-block;width: 30%;">
			Confirm Mat Khau 
		</div>
		<div style="display: inline-block;width: 100%;">
			<input type="password" id="confirm-password" style="width: 100%" type="text" width="1000"/>
		</div>
	</div>
<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateInfo"/>
</div>


<script>

function updateInfo(){
	
	var pwd = document.getElementById("password").value;
	var confirmpwd = document.getElementById("confirm-password").value;
	var staffId = document.getElementById("staff-id").value;
	if(pwd == null || pwd.length < 4 || pwd == ""){
		alert('Ban phai cung cap mat khau do dai it nhat 4 ky tu');
		return;
	}
	if(pwd != confirmpwd){
		alert('Mat khau phai NHAT QUAN!!!!');
		document.getElementById("password").value = "";
		document.getElementById("confirm-password").value = "";
		return;
	}
	
	//alert(pwd + "-" + staffId);
	$.ajax({
			url: "/bkeuniv/control/update-password",
			type: 'POST',
			data: {
				"password": pwd,
				"staffId": staffId
			},
			success: function(rs){
				alert('DOI MAT KHAU THANH CONG');
				window.location.href="/bkeuniv";
			}
		})
}

/*
function updateInfo1(){
	var pwd = document.getElementById("password").value;
	//var staffId = "dung.phamquang@hust.edu.vn";
	var staffId = ${staffId};
	alert(pwd + "-" + staffId);
	$.ajax({
			url: "/bkeuniv/control/update-password",
			type: 'POST',
			data: {
				"password": pwd,
				"staffId": staffId
			},
			success: function(rs){
				window.location.href="/bkeuniv";
			}
		})

}
*/
</script>

