<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
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
</style>

<script>
	var listStaff;
	$(document).ready(function(){
		$.ajax({
			url: "/bkeuniv/control/get-staff-select",
	    	method: 'POST',
	    	dataType: "json",
	    	contentType: 'application/json; charset=utf-8',
	    	success: function(data) {
	    		listStaff=data.staffs;
	    	}
		});
	});
	function dropDownSelect(data,name,dataRow,id,jqModal){
		var selected=data;
		return  '<div class="row inline-box">'+
					'<label id="title-modal-input">'+name+'</label>'+
					'<select style="width: 70%;" id="'+id+'" multiple="" tabindex="-1" class="selectized"></select>'+
				'</div>'+
		'<script type="text/javascript">'+
			'$(function () {'+
				'$("#'+jqModal+' #'+id+'").selectize({'+
					'maxItems: 1, '+
					'sortField: "text",'+
					'valueField: "staffId",'+
					'labelField: "staffName",'+
					'searchField: "staffName",'+
					'options: listStaff,'+
					'items: ["'+data+'"],'+
					'maxOptions: 5'+
				'});'+
			'});'+
		'<\/script>'	
		;
	}
</script>

<script>
	
	function createContextMenu(id) {
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
				menu: [
			  		{title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  		{title: '${projectDeclarationUiLabelMap.DowloadPDF}', cmd: "pdf", uiIcon: "glyphicon glyphicon-trash"},
			  		{title: '${projectDeclarationUiLabelMap.ExportExcel}', cmd: "csv", uiIcon: "glyphicon glyphicon-trash"},
			  		{title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
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
						case "csv":
							jqCSV(data);
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
	
	function jqPDF(data){
  		alertify.confirm('Dowload file "Project_Declaration.pdf"',
 			function(){
 			window.open("/bkeuniv/control/dowload-pdf");
   			 	alertify.success('Success');
  			},
  			function(){
    			alertify.error('Cancel');
  		});
	}	
	
	function jqCSV(data){
  		alertify.confirm('Dowload file "Project_Declaration.xls"',
 			function(event){
   			 	window.open("/bkeuniv/control/export-excel-project-declaration");
   			 	alertify.success('Success');
  			},
  			function(){
    			alertify.error('Cancel');
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

</script>

<div class="body">
	<#assign columns=[
		<#-- 
		{
			"name": projectDeclarationUiLabelMap.ProjectDeclarationId?j_string,
			"data": "projectDeclarationId"
		},
		 -->
		{
			"name": projectDeclarationUiLabelMap.ProjectCategoryId?j_string,
			"data": "projectCategoryName"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectName?j_string,
			"data": "projectName"
		},
		{
			"name": projectDeclarationUiLabelMap.StartDate?j_string,
			"data": "startDate"
		},
		{
			"name": projectDeclarationUiLabelMap.EndDate?j_string,
			"data": "endDate"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectStatusId?j_string,
			"data": "projectStatusName"
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProgram?j_string,
			"data": "researchProgram"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectParticipationRoleId?j_string,
			"data": "projectParticipationRoleName"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectTotalHour?j_string,
			"data": "totalhour"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectHourOfStaff?j_string,
			"data": "hourOfStaff"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectAcademicYear?j_string,
			"data": "academicYearName"
		}
		<#-- 
		,
		{
			"name": projectDeclarationUiLabelMap.ApproverStaffId?j_string,
			"data": "staffName"
		}
		 -->
	] />
	
	<#assign fields=[
		"projectDeclarationId",
		"projectCategoryId",
		"projectCategoryName",
		"projectName",
		"startDate",
		"endDate",
		"projectStatusId",
		"projectStatusName",
		"researchProgram",
		"declarationStaffId",
		"projectParticipationRoleId",
		"projectParticipationRoleName",
		"hourOfStaff",
		"totalhour",
		"approverStaffId",
		"academicYearId",
		"academicYearName",
		"sponsor",
		"budget",
		"staffName"
	] />
	
	<#assign sourceAcademicYear = [] />
	<#list resultAcademicYears.academicYears as y>
		<#if y?has_content>
             <#assign opy = { "name": y.academicYearName?j_string ,"value": y.academicYearId?j_string } />
						<#assign sourceAcademicYear = sourceAcademicYear + [opy] />
		</#if>
	</#list>

	<#assign listProjectCategory = [] />
	<#list projectCategory.projectCategorys as project>
		<#if project?has_content>
             <#assign op = { "name": project.projectCategoryName?j_string ,"value": project.projectCategoryId?j_string } />
						<#assign listProjectCategory = listProjectCategory + [op] />
		</#if>
	</#list>
	
	<#assign listProjectStatus = [] />
	<#list projectStatus.projectStatuss as project>
		<#if project?has_content>
             <#assign op = { "name": project.projectStatusName?j_string ,"value": project.projectStatusId?j_string } />
						<#assign listProjectStatus = listProjectStatus + [op] />
		</#if>
	</#list>
	
	<#assign listProjectParticipationRole = [] />
	<#list projectParticipationRole.projectParticipationRoles as project>
		<#if project?has_content>
             <#assign op = { "name": project.projectParticipationRoleName?j_string ,"value": project.projectParticipationRoleId?j_string } />
						<#assign listProjectParticipationRole = listProjectParticipationRole + [op] />
		</#if>
	</#list>
	
	<#assign columnsChange=[
		{
			"name": projectDeclarationUiLabelMap.ProjectCategoryId?j_string,
			"value": "projectCategoryId",
			"type": "select",
			"option": {
				"source": listProjectCategory,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectName?j_string,
			"value": "projectName"
		},
		{
			"name": projectDeclarationUiLabelMap.StartDate?j_string,
			"value": "startDate",
			"type": "date"
		},
		{
			"name": projectDeclarationUiLabelMap.EndDate?j_string,
			"value": "endDate",
			"type": "date"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectStatusId?j_string,
			"value": "projectStatusId",
			"type": "select",
			"option": {
				"source": listProjectStatus,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProgram?j_string,
			"value": "researchProgram"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectParticipationRoleId?j_string,
			"value": "projectParticipationRoleId",
			"type": "select",
			"option": {
				"source": listProjectParticipationRole,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectTotalHour?j_string,
			"value": "totalhour"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectHourOfStaff?j_string,
			"value": "hourOfStaff"
		}
		<#-- 
		,
		{
			"name": projectDeclarationUiLabelMap.ApproverStaffId?j_string,
			"value": "approverStaffId"
		}
		 -->
	] />
	
	<#assign columnsNew=[
		{
			"name": projectDeclarationUiLabelMap.ProjectCategoryId?j_string,
			"value": "projectCategoryId",
			"type": "select",
			"option": {
				"source": listProjectCategory,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectAcademicYear?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": sourceAcademicYear,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectName?j_string,
			"value": "projectName"
		},
		{
			"name": projectDeclarationUiLabelMap.StartDate?j_string,
			"value": "startDate",
			"type": "date"
		},
		{
			"name": projectDeclarationUiLabelMap.EndDate?j_string,
			"value": "endDate",
			"type": "date"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectStatusId?j_string,
			"value": "projectStatusId",
			"type": "select",
			"option": {
				"source": listProjectStatus,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProgram?j_string,
			"value": "researchProgram"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectParticipationRoleId?j_string,
			"value": "projectParticipationRoleId",
			"type": "select",
			"option": {
				"source": listProjectParticipationRole,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectTotalHour?j_string,
			"value": "totalhour"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectHourOfStaff?j_string,
			"value": "hourOfStaff"
			
		}
		<#-- 
		,
		{
			"name": projectDeclarationUiLabelMap.ApproverStaffId?j_string,
			"value": "approverStaffId",
			"type": "render",
			"render": 'function(data,name,dataRow,id){
				var jqModal = "jqModalAdd";
				return dropDownSelect(data,name,dataRow,id,jqModal);
			}',
			"getData": 'function(id) {
				return $("#jqModalAdd "+id).val();
			}'
		},
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
		urlData="/bkeuniv/control/get-project-declaration" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-project-declaration" 
		urlAdd="/bkeuniv/control/create-project-declaration" 
		urlDelete="/bkeuniv/control/delete-project-declaration" 
		keysId=["projectDeclarationId"] 
		fieldDataResult = "projectDeclarations" 
		titleChange=projectDeclarationUiLabelMap.TitleEditProjectDeclaration?j_string
		titleNew=projectDeclarationUiLabelMap.TitleNewProjectDeclaration?j_string
		titleDelete=projectDeclarationUiLabelMap.TitleDeleteProjectDeclaration?j_string
		jqTitle=projectDeclarationUiLabelMap.TitleProjectDeclaration?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
