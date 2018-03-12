<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	var modalChangePassword;
	function createContextMenu(id) {
	
		$(document).contextmenu({
			    delegate: "#"+id+"-content td",
			menu: [
			  {title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"},
			  {title: 'Tai PDF', cmd: "pdf", uiIcon: "glyphicon glyphicon-trash"},
			  {title: 'Change password', cmd: "changepassword", uiIcon: "glyphicon glyphicon-edit"}
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
					case "changepassword":
						jqChangePassword(data);
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
		console.log(data);
	}				
	function jqChangePassword(data){
		console.log(data);
		//alert("Form change password of staff " + data.staffId + " to be shown");
			new Promise(function(resolve, reject) {
				resolve(new modal("#modelChangePassword").setting({
					data: data,
					columns: [
						{
							'name': 'staffId',
							'value': 'staffId',
							'edit': false
						},
						{
							'name': 'Password',
							'value': 'password',
							'type': 'custom',
							'el': '<input type="password" class="form-control" id="password" value="">'
						}
					],
			        title: 'Change Password',
			        action: {
			        	type: "custom",
			        	name: "Update",
			        	update: changePassword
					}
				}).render());
			}).then(function(modal) {
				modalChangePassword = modal;
				$("#modelChangePassword #modal-template").modal('show');
			})
	}
	
	function changePassword(data) {
		console.log(data)
		openLoader()
		$.ajax({
			url: '/bkeuniv/control/update-password',
			type: 'POST',
			data: data,
			success: function(data) {
				setTimeout(function(){
					$("#modelChangePassword #modal-template").modal('hide');
					closeLoader();
					alertify.success("Ban da thay doi mat khau thanh cong");
				}, 300);
			},
			error: function(err) {
				setTimeout(function(){ closeLoader() }, 300);
				alertify.error(JSON.stringify(err));
			}
		})
		
	}			
	
</script>

<div class="body">
	<div id = "modelChangePassword">
	
	</div>
	
	<#assign columns=[
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"data": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"data": "staffEmail"
		},
		
		{
			"name": staffManagementUiLabelMap.BkEunivGender?j_string,
			"data": "genderName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"data": "staffDateOfBirth",
			"type": "date"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivPhone?j_string,
			"data": "staffPhone"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"data": "departmentName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivFaculty?j_string,
			"data": "facultyName"
		}
		
	] />
	
	<#assign sourceDepartment = [] />
	<#list resultDepartments.departments as dept>
		<#if dept?has_content>
             <#assign op = { "name": "${dept.departmentName}" ,"value": "${dept.departmentId}" } />
						<#assign sourceDepartment = sourceDepartment + [op] />
		</#if>
	</#list>
	
	<#assign sourceGender = [] />
	<#list resultGenders.genders as g>
		<#if g?has_content>
             <#assign op = { "name": "${g.genderName}" ,"value": "${g.genderId}" } />
						<#assign sourceGender = sourceGender + [op] />
		</#if>
	</#list>
	
	
	<#assign fields=[
		"staffId",
		"staffName",
		"staffEmail",
		"genderName",
		"staffGenderId",
		"staffDateOfBirth",
		"staffPhone",
		"departmentName",
		"departmentId",
		"facultyName",
		"facultyId"
		
	] />

	<#assign columnsChange=[
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"value": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"value": "staffEmail"
		},
		
		{
			"name": staffManagementUiLabelMap.BkEunivGender?j_string,
			"value": "staffGenderId",
			"type": "select",
			"option": {
				"source": sourceGender,
				"maxItem": 1
			}
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"value": "staffDateOfBirth",
			"type": "date"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivPhone?j_string,
			"value": "staffPhone"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"value": "departmentId",
			"type": "select",
			"option": {
				"source": sourceDepartment,
				"maxItem": 1
			}
		}
	] />

	<#assign columnsNew=[
		{
			"name": staffManagementUiLabelMap.BkEunivStaffId?j_string,
			"value": "staffId"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivStaffPassword?j_string,
			"value": "password"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivStaffPermissionGroup?j_string,
			"value": "groupId"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivFullName?j_string,
			"value": "staffName"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivEmail?j_string,
			"value": "staffEmail"
		},
		
		{
			"name": staffManagementUiLabelMap.BkEunivGender?j_string,
			"value": "staffGenderId",
			"type": "select",
			"option": {
				"source": sourceGender,
				"maxItem": 1
			}
		},
		{
			"name": staffManagementUiLabelMap.BkEunivBirthDate?j_string,
			"value": "staffDateOfBirth",
			"type": "date"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivPhone?j_string,
			"value": "staffPhone"
		},
		{
			"name": staffManagementUiLabelMap.BkEunivDepartment?j_string,
			"value": "departmentId",
			"type": "select",
			"option": {
				"source": sourceDepartment,
				"maxItem": 1
			}
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-a-staff" 
		urlAdd="/bkeuniv/control/create-a-staff" 
		urlDelete="/bkeuniv/control/remove-a-staff" 
		keysId=["staffId"] 
		fieldDataResult = "staffs" 
		titleChange=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleNew=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		titleDelete=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		jqTitle=staffManagementUiLabelMap.BkEunivStaffHeaderScreen
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
