<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>


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
			  {title: 'Thanh vien de tai', cmd: "projectmember", uiIcon: "glyphicon glyphicon-user"},
			  {title: 'Upload File', cmd: "upload", uiIcon: "glyphicon glyphicon-open"},
			  {title: 'Tai File', cmd: "pdf", uiIcon: "glyphicon glyphicon-save"},
			  {title: '${uiLabel.WorkPackage}', cmd: "workpackage", uiIcon: "glyphicon glyphicon-th-list"},
			  {title: '${uiLabel.Products}', cmd: "products", uiIcon: "glyphicon glyphicon-book"},
			  {title: '${uiLabel.ViewDetail}', cmd: "viewdetail", uiIcon: "glyphicon glyphicon-list-alt"},
			  {title: '${uiLabel.Submit}', cmd: "submit", uiIcon: "glyphicon glyphicon-send"}
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
					case "projectmember":
						jqProjectMember(data);
						break;
					case "submit":
						jqSubmit(data);
						break;
					
					case "workpackage":
						jqWorkPackage(data);
						break;
					
					case "products":
						jqProducts(data);
						break;
					case "viewdetail":
						jqViewDetail(data);
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
				
function 	jqProjectMember(data){
	//alert(data.researchProjectProposalId);
	window.location.href = "/bkeuniv/control/members-of-project-proposals?researchProjectProposalId=" + data.researchProjectProposalId;
}
function jqWorkPackage(data){
	window.location.href = "/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=" + data.researchProjectProposalId;
}
function jqProducts(data){
	window.location.href = "/bkeuniv/control/products-of-project-proposals?researchProjectProposalId=" + data.researchProjectProposalId;
}
function jqViewDetail(data){
	window.location.href = "/bkeuniv/control/detail-research-project-proposal?researchProjectProposalId=" + data.researchProjectProposalId;
}

	function jqPDF(data){
		console.log(data);
		window.open("/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId=" + data.researchProjectProposalId, "_blank")
	}	
	
	function jqUploadFile(data){
		selectedEntry = data;
		document.getElementById("upload").click();
	}
	function uploadFile(e){
		var file = e.target.files || e.dataTransfer.files;
		
		var reader = new FileReader();
		if(e.target.files.length !== 0) {
			test = e
			var formData = new FormData();
			formData.append("researchProjectProposalId", selectedEntry.researchProjectProposalId);
			formData.append("file", e.target.files[0]);
			
			$.ajax({
					url: "/bkeuniv/control/upload-file-research-project-proposal",
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
			"name": uiLabel.ProjectProposalName?j_string,
			"data": "researchProjectProposalName"
			
		},
		{
			"name": uiLabel.ProjectCallName?j_string,
			"data": "projectCallName"
		},
		{
			"name": uiLabel.CreateStaff?j_string,
			"data": "createStaffName"
		},
		{
			"name": uiLabel.Faculty?j_string,
			"data": "facultyName"
		}
	] />
	
	<#assign fields=[
		"researchProjectProposalId",
		"researchProjectProposalName",
		"projectCallName",
		"projectCategoryName",
		"statusName",
		"createStaffName",
		"facultyName",
		"statusName",
		"year"
	] />
	
	<#assign projectCallList = [] />
	<#list projectCalls.projectCalls as pc>
		<#if pc?has_content>
             <#assign op = { "name": pc.projectCallName?j_string ,"value": pc.projectCallId?j_string } />
						<#assign projectCallList = projectCallList + [op] />
		</#if>
	</#list>
	
	<#assign facultyList = []/>
	<#list faculties.faculties as f>
		<#if f?has_content>
			<#assign op = {"name": f.facultyName?j_string, "value": f.facultyId? j_string}/>
			<#assign facultyList = facultyList + [op]/>
		</#if>
	</#list>
	
	<#assign columnsChange=[
		{
			"name": uiLabel.ProjectProposalName?j_string,
			"value": "researchProjectProposalName"
		
		},
		{
			"name": uiLabel.ProjectCallName?j_string,
			"value": "projectCallId",
			"type": "select",
			"option":{
				"source": projectCallList,
				"maxItem": 1
			}
		
		},
		{
			"name": uiLabel.Faculty?j_string,
			"value": "facultyId",
			"type": "select",
			"option":{
				"source": facultyList,
				"maxItem": 1
			}
		
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": uiLabel.ProjectProposalName?j_string,
			"value": "researchProjectProposalName"
		
		},
		{
			"name": uiLabel.ProjectCallName?j_string,
			"value": "projectCallId",
			"type": "select",
			"option":{
				"source": projectCallList,
				"maxItem": 1
			}
		
		},
		{
			"name": uiLabel.Faculty?j_string,
			"value": "facultyId",
			"type": "select",
			"option":{
				"source": facultyList,
				"maxItem": 1
			}
		
		}
		
		]
	 />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-proposals-of-staff" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-a-project-submission" 
		urlAdd="/bkeuniv/control/create-a-project-submission" 
		urlDelete="/bkeuniv/control/delete-a-project-submission" 
		keysId=["researchProjectProposalId"] 
		fieldDataResult = "projectproposals" 
		titleChange=""
		titleNew=""
		titleDelete=""
		jqTitle=uiLabel.TitleProjectSubmissionManagement?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
	<input class="input" id="upload" type="file" onChange="uploadFile(event)">
</div>
