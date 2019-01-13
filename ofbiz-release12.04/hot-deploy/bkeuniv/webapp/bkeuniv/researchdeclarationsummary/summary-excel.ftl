<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<header>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/selectize.default.css">
	<script src="/resource/bkeuniv/js/lib/selectize.js"></script>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
	<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
</header>
<style>
.card {
    display: inline-block;
    width: 200px;
    margin: 20px;
    cursor: pointer;
}

#title-modal-input {
	padding: 5px;
    line-height: 1.5;
    flex: 1 1 auto;
    display: inline-block;
    width: 30%;
}

.modal-dialog {
    width: 60%!important;
    margin-left: 20%!important;
}

.material-icons {
  font-family: 'Material Icons';
  font-weight: normal;
  font-style: normal;
  font-size: 24px;
  line-height: 1;
  letter-spacing: normal;
  text-transform: none;
  display: inline-block;
  white-space: nowrap; text-overflow: ellipsis; overflow: hidden;
  word-wrap: normal;
  direction: ltr;
  -webkit-font-feature-settings: 'liga';
  -webkit-font-smoothing: antialiased;
}

@font-face {
  font-family: 'Material Icons';
  font-style: normal;
  font-weight: 400;
  src: url(/resource/bkeuniv/css/fonts/materialicons.woff2) format('woff2');
}
</style>
<script>
	var URL_SUBMIT="";
	function buildSelect2(id, data, maxItem=1) {
		var _id = id + "_select2";
		var script = '<script type="text/javascript">'+
						'$(function () {'+
							'$("#'+_id+'").select2({'+
								(maxItem>1?'maximumSelectionLength: ' + maxItem + ',':"")+
								'data:' + JSON.stringify(data) +
							'});'+
						'});'+
					'<\/script>';
		
		return '<div style="width: 70%; display: inline-block;" id='+id+' name="'+id+'" modal-value=\'$("#'+_id+'").val()\'><select class="js-states form-control" style="width: 100%" id="'+_id+'" '+(maxItem>1?'multiple':"")+'></select></div>'+script;
	}

	function save() {
		var modal_content_eL = $("#modal-body");
		var url = modal_content_eL.attr('action');
		
		var form = document.createElement("form");

		form.method = "POST";
		form.action = url;   

		modal_content_eL.children().map(function() {
			var el = this.children[1];
			var id = el.id||"";
			var value = eval(el.getAttribute("modal-value")||"");
			
			var element = document.createElement("input");
			element.value=value;
			element.name=id;
			form.appendChild(element);
		});

		document.body.appendChild(form);

		alertify.success('File dang duoc tai xuong');
		form.submit();

		form.remove();
		setTimeout(function(){ $("#modal-setting-export").modal("hide"); }, 500);
		
	}

	function openModalKV01() {
		var start = Date.now();
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/get-academic-years-faculties",
			type: 'POST',
			data: {
				"universityId": "HUST"
			},
			success:function(rs){
				console.log(rs);
				var millis = Date.now() - start;
				setTimeout(function(){ loader.close(); }, millis>300?0:millis);
				var modal_content_eL = $("#modal-body");
				modal_content_eL.attr('action','/bkeuniv/control/export-excel-kv01');

				var els = modal_content_eL.children();
				for(var i = 0; i < els.length; ++i) {
					els[i].remove();
				}

				
				var faculties = rs.faculties.map(function(faculty) {
					return {
						id: faculty.id,
        				text: faculty.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.FacultyName}</label>'+buildSelect2("facultyId-kv01", faculties)+
					'</div>'
				);

				var years = rs.years.map(function(year) {
					return {
						id: year.id,
        				text: year.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivYearN}</label>'+buildSelect2("reportyear-kv01", years)+
					'</div>'
				);

				$("#modal-setting-export").modal("show");
			}
		})
	}

	function openModal01CN02CN() {
		var start = Date.now();
		loader.open();

		<#--  String year = (String) request.getParameter("reportyear-kv01");
		String staffId = (String)userLogin.getString("userLoginId");
		String academicYearId = "2017-2018";  -->

		$.ajax({
			url: "/bkeuniv/control/get-academic-years-faculties",
			type: 'POST',
			data: {
				"universityId": "HUST"
			},
			success:function(rs){
				console.log(rs);
				var millis = Date.now() - start;
				setTimeout(function(){ loader.close(); }, millis>300?0:millis);
				var modal_content_eL = $("#modal-body");
				modal_content_eL.attr('action','/bkeuniv/control/excel-01-cn-02-cn');

				var els = modal_content_eL.children();
				for(var i = 0; i < els.length; ++i) {
					els[i].remove();
				}

				
				var faculties = rs.faculties.map(function(faculty) {
					return {
						id: faculty.id,
        				text: faculty.name
					};
				});

				var script_facultie = 
				'<script type="text/javascript">'+
					'$(function () {'+
						'$("#facultyId-01cn-02cn_select2").on("change", function(e) { '+
							'var facultyId = $("#facultyId-01cn-02cn_select2").val();'+
							'changeFaculty(facultyId, "departmentId-01cn-02cn_select2");'+
						'});'+
					'});'+
				'<\/script>';

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.FacultyName}</label>'+buildSelect2("facultyId-01cn-02cn", faculties)+
						script_facultie +
					'</div>'
				);

				var script_department = 
				'<script type="text/javascript">'+
					'$(function () {'+
						'$("#departmentId-01cn-02cn_select2").on("change", function(e) { '+
							'var departmentId = $("#departmentId-01cn-02cn_select2").val();'+
							'changeDepartment(departmentId, "staff-01cn-02cn_select2");'+
						'});'+
					'});'+
				'<\/script>';

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.DepartmentName}</label>'+
						'<div style="width: 70%; display: inline-block;" id="departmentId-01cn-02cn" name="facultyId-kv01" modal-value=\'$("#departmentId-01cn-02cn_select2").val()\'>'+
							'<select class="form-control" style="width: 100%" id="departmentId-01cn-02cn_select2">'+
							'</select>'+
							script_department+
						'</div>'+
					'</div>'
				);

				var script_staff = 
				'<script type="text/javascript">'+
					'$(function () {'+
						'$("#staff-01cn-02cn_select2").select2({'+
							
						'});'+
						'changeDepartment($("#departmentId-01cn-02cn_select2").val(), "staff-01cn-02cn_select2");'+
					'});'+
				'<\/script>';

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivStaffName}</label>'+
						'<div style="width: 70%; display: inline-block;" id="staff-01cn-02cn" name="facultyId-kv01" modal-value=\'$("#staff-01cn-02cn_select2").val()\'>'+
							'<select class="form-control" style="width: 100%" id="staff-01cn-02cn_select2">'+
							'</select>'+
							script_staff+
						'</div>'+
					'</div>'
				);


				var years = rs.years.map(function(year) {
					return {
						id: year.id,
        				text: year.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivYearN}</label>'+buildSelect2("reportyear-bm-01-02-03", years)+
					'</div>'
				);

				$("#modal-setting-export").modal("show");
			}
		})
	}

		function openModalKV04() {

		var start = Date.now();
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/get-academic-years-faculties",
			type: 'POST',
			data: {
				"universityId": "HUST"
			},
			success:function(rs){
				console.log(rs);
				var millis = Date.now() - start;
				setTimeout(function(){ loader.close(); }, millis>300?0:millis);
				var modal_content_eL = $("#modal-body");
				modal_content_eL.attr('action','/bkeuniv/control/export-excel-kv04');

				var els = modal_content_eL.children();
				for(var i = 0; i < els.length; ++i) {
					els[i].remove();
				}

				
				var faculties = rs.faculties.map(function(faculty) {
					return {
						id: faculty.id,
        				text: faculty.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.FacultyName}</label>'+buildSelect2("facultyId-kv04", faculties)+
					'</div>'
				);

				var years = rs.years.map(function(year) {
					return {
						id: year.id,
        				text: year.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivYearN}</label>'+buildSelect2("reportyear-kv04", years)+
					'</div>'
				);

				$("#modal-setting-export").modal("show");
			}
		})
	}

		function openModalKNC() {

		var start = Date.now();
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/get-academic-years-faculties",
			type: 'POST',
			data: {
				"universityId": "HUST"
			},
			success:function(rs){
				console.log(rs);
				var millis = Date.now() - start;
				setTimeout(function(){ loader.close(); }, millis>300?0:millis);
				var modal_content_eL = $("#modal-body");
				modal_content_eL.attr('action','/bkeuniv/control/export-excel-knc');

				var els = modal_content_eL.children();
				for(var i = 0; i < els.length; ++i) {
					els[i].remove();
				}

				
				var faculties = rs.faculties.map(function(faculty) {
					return {
						id: faculty.id,
        				text: faculty.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.FacultyName}</label>'+buildSelect2("facultyId-kv04", faculties)+
					'</div>'
				);

				var years = rs.years.map(function(year) {
					return {
						id: year.id,
        				text: year.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivYearN}</label>'+buildSelect2("reportyear-kv04", years)+
					'</div>'
				);

				$("#modal-setting-export").modal("show");
			}
		})
	}

	function openModalISI() {

		var start = Date.now();
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/get-academic-years-faculties",
			type: 'POST',
			data: {
				"universityId": "HUST"
			},
			success:function(rs){
				console.log(rs);
				var millis = Date.now() - start;
				setTimeout(function(){ loader.close(); }, millis>300?0:millis);
				var modal_content_eL = $("#modal-body");
				modal_content_eL.attr('action','/bkeuniv/control/export-excel-isi');

				var els = modal_content_eL.children();
				for(var i = 0; i < els.length; ++i) {
					els[i].remove();
				}

				
				var faculties = rs.faculties.map(function(faculty) {
					return {
						id: faculty.id,
        				text: faculty.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.FacultyName}</label>'+buildSelect2("facultyId-isi", faculties)+
					'</div>'
				);

				var years = rs.years.map(function(year) {
					return {
						id: year.id,
        				text: year.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivYearN}</label>'+buildSelect2("reportyear-isi", years)+
					'</div>'
				);

				$("#modal-setting-export").modal("show");
			}
		})
	}

	function openModalBomon010203() { 
			 
		var start = Date.now();
		loader.open();

		$.ajax({
			url: "/bkeuniv/control/get-academic-years-faculties",
			type: 'POST',
			data: {
				"universityId": "HUST"
			},
			success:function(rs){
				console.log(rs);
				var millis = Date.now() - start;
				setTimeout(function(){ loader.close(); }, millis>300?0:millis);
				var modal_content_eL = $("#modal-body");
				modal_content_eL.attr('action','/bkeuniv/control/export-excel-bm-01-02-03');

				var els = modal_content_eL.children();
				for(var i = 0; i < els.length; ++i) {
					els[i].remove();
				}

				
				var faculties = rs.faculties.map(function(faculty) {
					return {
						id: faculty.id,
        				text: faculty.name
					};
				});

				var script_facultie = 
				'<script type="text/javascript">'+
					'$(function () {'+
						'$("#facultyId-bm-01-02-03_select2").on("change", function(e) { '+
							'var facultyId = $("#facultyId-bm-01-02-03_select2").val();'+
							'changeFaculty(facultyId, "departmentId-bm-01-02-03_select2");'+
						'});'+
					'});'+
				'<\/script>';

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.FacultyName}</label>'+buildSelect2("facultyId-bm-01-02-03", faculties)+
						script_facultie +
					'</div>'
				);

				var script_department = 
				'<script type="text/javascript">'+
					'$(function () {'+
						'$("#departmentId-bm-01-02-03_select2").select2({'+
							
						'});'+
					'});'+
				'<\/script>';

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.DepartmentName}</label>'+
						'<div style="width: 70%; display: inline-block;" id="departmentId-bm-01-02-03" name="facultyId-kv01" modal-value=\'$("#departmentId-bm-01-02-03_select2").val()\'>'+
							'<select class="form-control" style="width: 100%" id="departmentId-bm-01-02-03_select2">'+
							'</select>'+
							script_department+
						'</div>'+
					'</div>'
				);

				var years = rs.years.map(function(year) {
					return {
						id: year.id,
        				text: year.name
					};
				});

				modal_content_eL.append(
					'<div class="row inline-box">'+
						'<label id="title-modal-input">${uiLabelMap.BkEunivYearN}</label>'+buildSelect2("reportyear-bm-01-02-03", years)+
					'</div>'
				);

				$("#modal-setting-export").modal("show");
			}
		})
	}

	function clearSelectBox(sel){
		var i;
		for(i = sel.options.length-1; i >= 0; i--){
			sel.remove(i);
		}
	}

	function changeFaculty(facultyId, idSelect){
		$.ajax({
					url: "/bkeuniv/control/get-departments-of-faculty",
					type: 'POST',
					data: {
						"facultyId": facultyId
					},
					success:function(rs){
						var departments = rs.departments.map(function(department) {
							return {
								id: department.id,
								text: department.name
							};
						});

						console.log(departments)
						$("#"+idSelect).html('').select2();
						$("#"+idSelect).select2({
							data: departments
						});
						
					}
				})
		
	}

	function changeDepartment(departmentId, idSelect){
		//alert('changeDepartment, id = ' + departmentId);
		$.ajax({
					url: "/bkeuniv/control/get-staffs-of-department",
					type: 'POST',
					data: {
						"departmentId": departmentId
					},
					success:function(rs){
						var staffs = rs.staffs.map(function(staff) {
							return {
								id: staff.id,
								text: staff.name
							};
						});

						console.log(staffs)
						$("#" + idSelect).html('').select2();
						$("#" + idSelect).select2({
							data: staffs
						});
						
					}
				})
		
	}

</script>
<body>
<div class="body" style="flex: 1 1 0%;padding: 2em 3em 6em 3em;width: 100%;overflow-y: auto;height: 100%;background-color: rgb(237, 236, 236);">



<#--  	


<div id="model-kv01" class="modal-export-excel">
	<div class="modal-content">
	<div class="modal-header">
		<span class="close">&times;</span>
		<h2>KV01-KV03</h2>
	</div>
	
	<div class="modal-body">
		
	         <select id="reportyear-kv01" name="reportyear-kv01" width="50px"></select><br>
			 <select id="facultyId-kv01" name="facultyId-kv01" width="100px">
			 </select>
			         
			 <input type="submit" value="export KV01"/>
			</form>
	    </div>
	  </div>
	</div>
	<div id="model-kv04" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>KV04</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-kv04</@ofbizUrl>" method="post">
	         <select id="reportyear-kv04" name="reportyear-kv04" width="50px"></select><br>
			 <select id="facultyId-kv04" name="facultyId-kv04" width="100px">
			 </select>
			         
			 <input type="submit" value="export KV04"/>
			</form>
	    </div>
	  </div>
	</div>
		
	<div id="model-isi" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>ISI-SCOPUS</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-isi</@ofbizUrl>" method="post">
	         <select id="reportyear-isi" name="reportyear-isi" width="50px"></select><br>
			 <select id="facultyId-isi" name="facultyId-isi" width="100px">
			 
			 </select>        
			 <input type="submit" value="export ISI"/>
			</form>
	    </div>
	  </div>
	</div>


	<div id="model-bm-01-02-03" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>Bo mon 01-02-03</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-bm-01-02-03</@ofbizUrl>" method="post">
	         <select id="reportyear-bm-01-02-03" name="reportyear-bm-01-02-03" width="50px"></select><br>
			 <select id="facultyId-bm-01-02-03" name="facultyId-bm-01-02-03" width="100px" onchange="changeFacultyBM010203()">
			 </select>        
			 <select id="departmentId-bm-01-02-03" name="departmentId-bm-01-02-03" width="100px">
			 </select>        
			 
			 <input type="submit" value="export Bo mon 01-02-03"/>
			</form>
	    </div>
	  </div>
	</div>  -->

	<@Loader handleToggle="loader">
		<@IconSpinner/>
	</@Loader>

	<div class="card" onclick="openModal01CN02CN()">
		<div class="card-image">
		<img src="/resource/bkeuniv/image/Infor.png">
		<a class="btn-floating halfway-fab waves-effect waves-light" style="background-color:rgb(0, 188, 212); width: 30px; height: 30px;"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size: 1.3rem; line-height: 30px;"></i></a>
		</div>
		<div class="card-content" style="padding: 24px 10px 24px 10px;">
			<span class="card-title" style="font-size: 18px; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
				01CN-02CN
			</span>
		</div>
	</div>

	<div class="card" onclick="openModalKV01()">
		<div class="card-image">
		<img src="/resource/bkeuniv/image/Infor.png">
		<a class="btn-floating halfway-fab waves-effect waves-light" style="background-color:rgb(0, 188, 212); width: 30px; height: 30px;"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size: 1.3rem; line-height: 30px;"></i></a>
		</div>
		<div class="card-content" style="padding: 24px 10px 24px 10px;">
			<span class="card-title" style="font-size: 18px; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
				KV01-KV03
			</span>
		</div>
	</div>

	<div class="card" onClick="openModalISI()">
		<div class="card-image">
		<img src="/resource/bkeuniv/image/Infor.png">
		<a class="btn-floating halfway-fab waves-effect waves-light" style="background-color:rgb(0, 188, 212); width: 30px; height: 30px;"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size: 1.3rem; line-height: 30px;"></i></a>
		</div>
		<div class="card-content" style="padding: 24px 10px 24px 10px;">
			<span class="card-title" style="font-size: 18px; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
				ISI-SCOPUS
			</span>
		</div>
	</div>

	<div class="card" onClick="openModalBomon010203()">
		<div class="card-image">
		<img src="/resource/bkeuniv/image/Infor.png">
		<a class="btn-floating halfway-fab waves-effect waves-light" style="background-color:rgb(0, 188, 212); width: 30px; height: 30px;"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size: 1.3rem; line-height: 30px;"></i></a>
		</div>
		<div class="card-content" style="padding: 24px 10px 24px 10px;">
			<span class="card-title" style="font-size: 16px; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
				Bo mon 01-02-03
			</span>
		</div>
	</div>

	<div class="card" onClick="openModalKV04()">
		<div class="card-image">
		<img src="/resource/bkeuniv/image/Infor.png">
		<a class="btn-floating halfway-fab waves-effect waves-light" style="background-color:rgb(0, 188, 212); width: 30px; height: 30px;"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size: 1.3rem; line-height: 30px;"></i></a>
		</div>
		<div class="card-content" style="padding: 24px 10px 24px 10px;">
			<span class="card-title" style="font-size: 18px; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
				KV04
			</span>
		</div>
	</div>
	
	<div class="card" onClick="openModalKNC()">
		<div class="card-image">
		<img src="/resource/bkeuniv/image/Infor.png">
		<a class="btn-floating halfway-fab waves-effect waves-light" style="background-color:rgb(0, 188, 212); width: 30px; height: 30px;"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size: 1.3rem; line-height: 30px;"></i></a>
		</div>
		<div class="card-content" style="padding: 24px 10px 24px 10px;">
			<span class="card-title" style="font-size: 18px; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;">
				KNC
			</span>
		</div>
	</div>

	<div class="modal fade" style="margin-top: 5%;" id="modal-setting-export" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content" id="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">${uiLabelMap.BkEunivSetting}</h4>
				</div>
				<div class="modal-body" id="modal-body">
					<p>Some text in the modal.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onClick="save()">${uiLabelMap.BkEunivDownload}</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">${uiLabelMap.BkEunivClose}</button>
				</div>
			</div>

		</div>
	</div>

</div>


