<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<body>

<style>
	.upload {
		box-shadow: 0 0 1px rgba(34, 25, 25, 0.4);
	    margin-top: 20px;
        padding: 5px;
        z-index: 9999;
	}
	
	.upload.drag-over {
		border: 2px dashed gray;
	}
	
	.upload .upload-file {
		position: relative;
		text-align: center;

		width: 80px;
		color: #53a7df;
		font-size: 60px;
		padding: 10px;
	    margin: auto;

		cursor: pointer;
	}
	
	.upload .upload-file:hover {
		color: rgba(83, 167, 223, 0.9);
	}
	
	.icon-upload input, .upload-file input {
		cursor: pointer;
		opacity: 0.0;
		position: absolute;
		top: 0;
		left: 0;
		bottom: 0;
		right: 0;
		width: 100%;
		height: 100%;
	}
	
	.drop-file {
		text-align: center;
		background: transparent;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 30px;
		color: gray;

		margin-bottom: 20px;
	}
	
	
	
	#download-papger{
		    position: relative;
		    width: 80px;
		    padding: 6px;
		    font-size: 12px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #53a7df;
		    color: #fff;
		    text-align: center;
		    border: 1px solid #68b2e3;
		}
		
		#download-papger:hover {
			background-color: #3d9cdb;
		}
		
		#upload {
			width: 0;
			height: 0;
		}
		
		

#myModal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
#myModal .modal-content {
    position: relative;
    background-color: #fefefe;
    margin: auto;
    padding: 0;
    border: 1px solid #888;
    width: 50%;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
    -webkit-animation-name: animatetop;
    -webkit-animation-duration: 0.4s;
    animation-name: animatetop;
    animation-duration: 0.4s
}

/* Add Animation */
@-webkit-keyframes animatetop {
    from {top:-300px; opacity:0} 
    to {top:0; opacity:1}
}

@keyframes animatetop {
    from {top:-300px; opacity:0}
    to {top:0; opacity:1}
}

/* The Close Button */
.close {
    color: black;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

#myModal .modal-content .modal-header {
    padding: 2px 16px;
    background-color: #ebebe0;
    color: black;
}

#myModal .modal-content .modal-body {padding: 10px 26px;}

#myModal .modal-content .modal-footer {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}
			
</style>

<!-- The Modal -->
<div id="myModal" class="modal-add-member">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h4>${paperDeclarationUiLabelMap.BkEunivPaperMembers}</h4>
    </div>
    
    <div class="modal-body">
      	<div class="inline-box" style="width: 100%; padding: 10px 0px;">	
      		<div style="display: inline-block;width: 30%; padding: 10px 0px;">
				Chon Khoa/vien 
			</div>
			<div style="display: inline-block;width: 100%; padding: 10px 0px;">
      			<select id="facultyId" style="width: 100%" onchange="changeFaculty()"></select>
        	</div>
        	<div style="display: inline-block;width: 30%; padding: 10px 0px;">
				Chon Bo mon 
			</div>
			<div style="display: inline-block;width: 100%; padding: 10px 0px;">
      			<select id="departmentId" width=50px" onchange="changeDepartment()"></select>
      		</div>
      		<div style="display: inline-block;width: 30%; padding: 10px 0px;">
				Chon giang vien 
			</div>
			<div style="display: inline-block;width: 100%; padding: 10px 0px;">
      			<select id = "staffs" width="30px"></select>
      		</div>
      		
      		<@buttonStore text="Them" action="addMemberPaper"/>
      	</div>
      <table id="staffs-of-paper" border = "1"></table>
      
      <!--
      <button id="jqDataTable-button-update" onclick="updateMemberPaper()">Update</button>
      -->
      
    </div>
   
  </div>

</div>


<script>
	var modal;
	var span;
	var selectedEntry;
	function createContextMenu(id) {
		
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
			menu: [
			  {title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"},
			  {title: 'Thanh vien bai bao', cmd: "papermember", uiIcon: "glyphicon glyphicon-user"},
			  {title: 'Upload PDF', cmd: "upload", uiIcon: "glyphicon glyphicon-open"},
			  {title: 'Tai PDF', cmd: "pdf", uiIcon: "glyphicon glyphicon-save"}
			
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var data = jqDataTable.table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						jqChange(data)
						break;
					case "delete":
						jqDelete(data);
						break;
					case "pdf":
						jqPDF(data);
						break;
					case "upload":
						jqUploadFile(data);
						break;
					case "papermember":
						jqPaperMember(data);
						break;
					}		
					
				},
				beforeOpen: function(event, ui) {
					var $menu = ui.menu,
						$target = ui.target,
						extraData = ui.extraData;
					ui.menu.zIndex(9999);
			    }
			  });
	}
	function clearSelectBox(sel){
		var i;
		for(i = sel.options.length-1; i >= 0; i--){
			sel.remove(i);
		}
	}

	function changeFaculty(){
		var fId = document.getElementById("facultyId").value;
		//alert("SELECT " + fId);
		$.ajax({
					url: "/bkeuniv/control/get-departments-of-faculty",
					type: 'POST',
					data: {
						"facultyId": fId
					},
					success:function(rs){
						console.log(rs);
						var select_department = document.getElementById("departmentId");
						
						clearSelectBox(select_department);
						
						var lst = rs.departments;
						
						
						for(i = 0; i < lst.length; i++){	
							var o = document.createElement("option");
							o.text = lst[i].name;
							o.value = lst[i].id;
							select_department.appendChild(o);
						}
						
						changeDepartment();
					}
				})
		
		
	}
				
	function changeDepartment(){
		var dId = document.getElementById("departmentId").value;
		//alert("SELECT " + dId);
		$.ajax({
					url: "/bkeuniv/control/get-staffs-of-department",
					type: 'POST',
					data: {
						"departmentId": dId
					},
					success:function(rs){
						console.log(rs);
						var select_staff = document.getElementById("staffs");
						
						clearSelectBox(select_staff);
						
						var lst = rs.staffs;
						
						
						for(i = 0; i < lst.length; i++){	
							var o = document.createElement("option");
							o.text = lst[i].name;
							o.value = lst[i].id;
							select_staff.appendChild(o);
						}
					}
				})
		
		
	}

	function jqPDF(data){
		console.log(data);
		window.open("/bkeuniv/control/download-file-paper?id-paper=" + data.paperId, "_blank")
	}	
	
	function addMemberPaper(){
		var paperId = selectedEntry.paperId;
		var staffId = document.getElementById("staffs").value;
		alert("them thanh vien bai bao, paperId = " + paperId + ", staffId = " + staffId);
				$.ajax({
					url: "/bkeuniv/control/create-staffs-paper",
					type: 'POST',
					data: {
						"paperId": paperId,
						"staffId": staffId
					},
					success:function(rs){
						console.log(rs);
					/*	
					var select_staffs = document.getElementById("staffs");
					var lst_staffs = rs;//jQuery.parseJSON(rs);
					for(i = 0; i < lst_staffs.staffs.length; i++){	
						var o = document.createElement("option");
						o.text = lst_staffs.staffs[i].name;
						o.value = lst_staffs.staffs[i].id;
						select_staffs.appendChild(o);
					}
					*/
					
					var tbl = document.getElementById("staffs-of-paper");
					/*
					//var len = tbl.rows.length;
					//for(i = 0; i < len;i++)
					//	tbl.deleteRow(0);
						
					for(i = 0; i < rs.staffsofpaper.length; i++){
						var s = rs.staffsofpaper[i].name;
						var row = tbl.insertRow(i);
						var cell = row.insertCell(0);
						cell.innerHTML = s;
						
					}
					*/
					
					var len = tbl.rows.length;
					for(i = 0; i < len;i++)
						tbl.deleteRow(0);
					
					var lst_staffs = rs;	
					for(i = 0; i < lst_staffs.staffsofpaper.length; i++){
						var s = lst_staffs.staffsofpaper[i].name;
						var row = tbl.insertRow(i);
						console.log(row.__proto__)
						row.setAttribute("id-staff", lst_staffs.staffsofpaper[i].id);
						var cell = row.insertCell(0);
						cell.innerHTML = s;
						
						var cell_action = row.insertCell(1);
						var btn = '<button id="jqDataTable-button-remove" onclick=\'removeStaffPaper(' +
						selectedEntry.paperId + ',"' + lst_staffs.staffsofpaper[i].id + '", this)\'>Xoa</button>';
						cell_action.innerHTML = btn;
					}

					
					
					model.style.display = "block";

					}
				})
		
		
	}
	function jqUploadFile(data){
		selectedEntry = data;
		document.getElementById("upload").click();
		
		/*
		var form = document.createElement("form");
		form.setAttribute("enctype","multipart/form-data");
		form.setAttribute("method","POST");
		form.setAttribute("action","/bkeuniv/control/upload-file-paper");
		
		var I = document.createElement("input");
		I.setAttribute("type","file");
		form.appendChild(I);
		*/
		
		//form.submit();
		
	}			
	
	function jqPaperMember(data){
		/*
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", "test.jsp");

		// setting form target to a window named 'formresult'
		form.setAttribute("target", "formresult");

		var hiddenField = document.createElement("input");              
		hiddenField.setAttribute("name", "id");
		hiddenField.setAttribute("value", "bob");
		form.appendChild(hiddenField);
		document.body.appendChild(form);

		// creating the 'formresult' window with custom features prior to submitting the form
		window.open('test.html', 'formresult', 'scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,status=no');

		form.submit();
		*/
		
		selectedEntry = data;
		
		model = document.getElementById('myModal');
		span = document.getElementsByClassName("close")[0];
		span.onclick = function(){
			model.style.display = "none";
		}	
		$.ajax({
					url: "/bkeuniv/control/get-staffs-of-paper",
					type: 'POST',
					data: {
						"paperId": selectedEntry.paperId
					},
					success:function(rs){
						console.log(rs);
					var select_staffs = document.getElementById("staffs");
					var lst_staffs = rs;//jQuery.parseJSON(rs);
					for(i = 0; i < lst_staffs.staffs.length; i++){	
						var o = document.createElement("option");
						o.text = lst_staffs.staffs[i].name;
						o.value = lst_staffs.staffs[i].id;
						select_staffs.appendChild(o);
					}
					
					var select_faculty = document.getElementById("facultyId");
					for(i = 0; i < rs.faculties.length; i++){	
						var o = document.createElement("option");
						o.text = rs.faculties[i].name;
						o.value = rs.faculties[i].id;
						select_faculty.appendChild(o);
					}
					
					changeFaculty();
					
					
					var tbl = document.getElementById("staffs-of-paper");
					var len = tbl.rows.length;
					for(i = 0; i < len;i++)
						tbl.deleteRow(0);
					for(i = 0; i < lst_staffs.staffsofpaper.length; i++){
						var s = lst_staffs.staffsofpaper[i].name;
						var row = tbl.insertRow(i);
						console.log(row.__proto__)
						row.setAttribute("id-staff", lst_staffs.staffsofpaper[i].id);
						var cell = row.insertCell(0);
						cell.innerHTML = s;
						
						var cell_action = row.insertCell(1);
						var btn = '<button id="jqDataTable-button-remove" onclick=\'removeStaffPaper(' +
						selectedEntry.paperId + ',"' + lst_staffs.staffsofpaper[i].id + '", this)\'>Xoa</button>';
						cell_action.innerHTML = btn;
					}
					
					model.style.display = "block";

					}
				})
	}
	var a;
	function removeStaffPaper(paperId, staffId, item){
		item.parentElement.parentElement.remove()
		console.log(paperId, staffId, item)
		a = item;
		
		$.ajax({
					url: "/bkeuniv/control/remove-staffs-paper",
					type: 'POST',
					data: {
						"paperId": selectedEntry.paperId,
						"staffId": staffId
					},
					success:function(rs){
						console.log(rs);
					}
				})
	}
	
	function updateMemberPaper(){
		var staffsId = Array.from($("#staffs-of-paper tbody tr")).map(function(tr) {
			return tr.getAttribute("id-staff")
		})
		a = staffsId;
		console.log(staffsId)
		$.ajax({
					url: "/bkeuniv/control/update-staffs-of-paper",
					type: 'POST',
					data: {
						"paperId": selectedEntry.paperId,
						"staffsId": staffsId.join()
					},
					success:function(rs){
						console.log(rs);
						
						alertify
  							.alert("","Cap nhat thanh vien bai bao thanh cong", function(){
    						
  						});
					}
		});
				
	}
</script>



<script type="text/javascript">
	var dataFilePaper = {};
	
	
	
	function createUploadFile(data, name, dataRow, id) {
		return '<div class="upload" id="'+id+'">'+
					'<div class="upload-file" >'+
                    	'<i class="fa fa-cloud-upload" aria-hidden="false"></i>'+
						'<input class="input" id="input-file" type="file">'+
					'</div>'+
					'<div class="drop-file">'+
                    	'Drop file here'+
					'</div>'+
					'<script type="text/javascript">'+
						'$(function () {componentDidMount("'+id+'")});'+
					"<\/script>"+
			'</div>';
	};

	function componentDidMount(id) {
		var filedrag = document.getElementById(id);
		filedrag.addEventListener('dragleave', (e) => { this.fileDragLeave(e, filedrag) }, false);
		filedrag.addEventListener('drop', (e) => { this.fileSelectHandler(e, filedrag) }, false);
		filedrag.addEventListener('dragenter', (e) => { this.fileOndragenter(e, filedrag) }, false);
		filedrag.addEventListener('dragover', (e) => { this.fileDragHover(e, filedrag) }, false);
		document.getElementById("input-file").addEventListener('change', (e) => { this.fileSelectHandler(e, filedrag) }, false);
	}
	function fileOndragenter(e, filedrag) {
		var childrens = Array.from(filedrag.children);
		for (let children of childrens) {
			children.style.zIndex = 1049; 
		}
		e.target.classList.add('drag-over');
		
	}

	function fileDragHover(e) {
		e.stopPropagation();
		e.preventDefault();
	}

	function fileDragLeave(e, filedrag) {
		e.target.classList.remove('drag-over');
		var childrens = Array.from(filedrag.children);
		for (let children of childrens) {
			children.style.zIndex = ''; 
		}
		e.stopPropagation();
		e.preventDefault();
	}

	function fileSelectHandler(e, filedrag) {
		console.log(e)
		var file = e.target.files || e.dataTransfer.files;
        
		e.preventDefault();
		var reader = new FileReader();

		reader.readAsText(file[0], 'utf-8');
		reader.onload = (event) => {
			var data = event.target.result
			var fileName = file[0].name
			var lastModified = file[0].lastModified;
			dataFilePaper.fileName = fileName;
			dataFilePaper.data = data;			
		}

		reader.onerror = (event) => {
			this.props.onError(event);
		}
		filedrag.classList.remove('drag-over');
	}
	
	function getDataFile(id) {
		return dataFilePaper||{};
	}
	var test, a;
	function uploadFile(e){
		var file = e.target.files || e.dataTransfer.files;
		
		var reader = new FileReader();
		if(e.target.files.length !== 0) {
			test = e
			var formData = new FormData();
			//formData.append("filename", e.target.files[0].name);
			formData.append("paperId", selectedEntry.paperId);
			formData.append("file", e.target.files[0]);
			
			$.ajax({
					url: "/bkeuniv/control/upload-file-paper",
					type: 'POST',
					method: 'POST',
					contentType: false,
    				processData: false,
					data: formData,
					success:function(rs){
						console.log(rs);
					}
				})
		}

		
		
	}

</script>

<div class="body">
	<#assign columns=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivStaffId?j_string,
			<!--
			"data": "staffName"
			-->
			"data": "declareStaffName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"data": "paperName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperVolumn?j_string,
			"data": "volumn"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperCategory?j_string,
			"data": "categoryName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAuthors?j_string,
			"data": "authors"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperJournalConference?j_string,
			"data": "journalConferenceName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperMonth?j_string,
			"data": "month"
			
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperYear?j_string,
			"data": "year"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAcademicYear?j_string,
			"data": "academicYearId"
		}
	] />
	
	<#assign source = [] />
	<#list paperCategory.result as paper>
		<#if paper?has_content>
             <#assign op = { "name": paper.paperCategoryName?j_string ,"value": paper.paperCategoryCode?j_string } />
						<#assign source = source + [op] />
		</#if>
	</#list>
	
	<#assign sourceAcademicYear = [] />
	<#list result.academicYears as y>
		<#if y?has_content>
             <#assign opy = { "name": y.academicYearName?j_string ,"value": y.academicYearId?j_string } />
						<#assign sourceAcademicYear = sourceAcademicYear + [opy] />
		</#if>
	</#list>
	
	<#assign fields=[
		"paperId",
		"staffName",
		"declareStaffName",
		"volumn",
		"authors",
		"journalConferenceName",
		"paperCategoryId",
		"categoryName",
		"paperName",
		"month",
		"year",
		"academicYearId",
		"sourcePath"
	] />
	
	<#assign columnsChange=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAuthors?j_string,
			"value": "authors"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperCategory?j_string,
			"value": "paperCategoryId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		},
		
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperJournalConference?j_string,
			"value": "journalConferenceName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperVolumn?j_string,
			"value": "volumn"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperMonth?j_string,
			"value": "month"
			
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperYear?j_string,
			"value": "year"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperISSN?j_string,
			"value": "ISSN"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
		}

	] />
	
	<#assign columnsNew=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"value": "paperName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAuthors?j_string,
			"value": "authors"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperCategory?j_string,
			"value": "paperCategoryId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		},
		
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperJournalConference?j_string,
			"value": "journalConferenceName"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperVolumn?j_string,
			"value": "volumn"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperMonth?j_string,
			"value": "month"
			
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperYear?j_string,
			"value": "year"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperISSN?j_string,
			"value": "ISSN"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
		}
		<!--
		,
		{
			"name": "Up load file",
			"value": "fileName",
			"type": "render",
			"render": 'function(data, name, dataRow, id) {
				return createUploadFile(data, name, dataRow, id);
			}',
			"getData": 'function(id) {
				return getDataFile(id);
			}'
		}
		-->
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-papers-of-staff" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-paper" 
		urlAdd="/bkeuniv/control/create-paper-declaration" 
		urlDelete="/bkeuniv/control/delete-paper-declaration" 
		keysId=["paperId"] 
		fieldDataResult = "papers" 
		titleChange="${paperDeclarationUiLabelMap.BkEunivUpdatePaper}"
		titleNew="${paperDeclarationUiLabelMap.BkEunivAddPaper}"
		titleDelete=""
		jqTitle=""
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
	<input class="input" id="upload" type="file" onChange="uploadFile(event)">
</div>
