<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<script>
	function createContextMenu(id) {
		$(document).contextmenu({
			delegate: '#'+id+ ' td',
		menu: [
			{title: '${uiLabelMap.BkEunivDownload}', cmd: "download", uiIcon: "fa fa-download"},
			{title: '${uiLabelMap.BkEunivUpdateProjectProposal}', cmd: "update", uiIcon: "fa fa-pencil"},
			{title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
            {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
		],
		select: function(event, ui) {
			var el = ui.target.parent();
			var data = jqDataTable.table.row( el ).data();
			switch(ui.cmd){
				
				case "update":
					window.open("/bkeuniv/control/form-edit-project-proposal?researchProjectProposalId="+data.researchProjectProposalId);
					break;
				case "download":
					window.open("/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId="+data.researchProjectProposalId, "_blank")
					break;
                case "edit":
                    jqChange(data)
                    break;
                case "delete":
                    jqDelete(data);
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

</script>

<body>
<div class="body">
	<#assign columns=[
		{
			"name": projectDeclarationUiLabelMap.ProjectCategoryId?j_string,
			"data": "projectCategoryName"
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"width": "300px",
			"data": "researchProjectProposalName",
			"render": 'function(value, name, dataColumns, id) {
                return "<a href=\\"/bkeuniv/control/form-edit-project-proposal?researchProjectProposalId="+dataColumns.researchProjectProposalId+"\\">" + value + "</a>";
			}'
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalCode?j_string,
			"data": "researchProjectProposalCode"
		},
		{
			"name": projectDeclarationUiLabelMap.StartDate?j_string,
			"width": "120px",
			"data": "startDate"
		},
		{
			"name": projectDeclarationUiLabelMap.TotalBudget?j_string,
			"width": "200px",
			"type": "currency",
			"data": "totalBudget"
		}
		
	] />
	
	<#assign fields=[
		"researchProjectProposalId",
		"researchProjectProposalCode",
		"partyId",
		"createStaffId",
		"projectCallId",
		"projectCategoryId",
		"researchProjectProposalName",
		"researchProjectProposalCode",
		"totalBudget",
		"statusId",
		"approvedByStaffId",
		"sourceFileUpload",
		"facultyId",
		"startDate",
		"endDate",
		"deliverable",
		"materialBudget",
		"evaluationOpenFlag", 
		"createStaffName",
		"projectCallName",
		"projectCategoryName",
		"facultyName",
		"statusName"
	] />
	
	<#assign listProjectProposalStatus = [] />
	<#list projectProposalStatusS.projectProposalStatus as project>
		<#if project?has_content>
             <#assign op = { "name": project.statusName?j_string ,"value": project.statusId?j_string } />
						<#assign listProjectProposalStatus = listProjectProposalStatus + [op] />
		</#if>
	</#list>
	
	<#assign listProjectCall = [] />
	<#list projectCallS.projectCalls as project>
		<#if project?has_content>
             <#assign op = { "name": project.projectCallName?j_string ,"value": project.projectCallId?j_string } />
						<#assign listProjectCall = listProjectCall + [op] />
		</#if>
	</#list>
	
	<#assign listProjectCategory = [] />
	<#list projectCategoryS.projectCategorys as project>
		<#if project?has_content>
             <#assign op = { "name": project.projectCategoryName?j_string ,"value": project.projectCategoryId?j_string } />
						<#assign listProjectCategory = listProjectCategory + [op] />
		</#if>
	</#list>
	
	<#assign listFaculty = [] />
	<#list facultyS.facultys as project>
		<#if project?has_content>
             <#assign op = { "name": project.facultyName?j_string ,"value": project.facultyId?j_string } />
						<#assign listFaculty = listFaculty + [op] />
		</#if>
	</#list>
	
	<#assign listStaff = [] />
	<#list staffS.staffs as project>
		<#if project?has_content>
             <#assign op = { "name": project.staffName?j_string ,"value": project.staffId?j_string } />
						<#assign listStaff = listStaff + [op] />
		</#if>
	</#list>
	
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
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"value": "researchProjectProposalName",
			"require": "true#JS",
			"customValidity": StringUtil.wrapString(uiLabelMap.BkEunivNotNull)?j_string
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalCode?j_string,
			"value": "researchProjectProposalCode"
		},

		{
			"name": projectDeclarationUiLabelMap.TotalBudget?j_string,
			"value": "totalBudget",
			"pattern": "[1-9]([0-9]{0,20})",
			"title": "So nguyen duong"
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
		}
	] />
	
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
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"value": "researchProjectProposalName"
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalCode?j_string,
			"value": "researchProjectProposalCode"
		},
		{
			"name": projectDeclarationUiLabelMap.TotalBudget?j_string,
			"value": "totalBudget",
			"pattern": "[1-9]([0-9]{0,20})",
			"title": "So nguyen duong"
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
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-declaration-new" 
		columns=columns 
		columnsNew=columnsNew 
		columnsChange=columnsChange 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["researchProjectProposalId"] 
		fieldDataResult = "projectDeclarations"
		urlAdd="/bkeuniv/control/create-project-declaration-new" 
		urlUpdate="/bkeuniv/control/update-project-declaration-new" 
		urlDelete="/bkeuniv/control/delete-project-declaration-new"
		titleChange=projectDeclarationUiLabelMap.TitleEditProjectDeclaration?j_string
		titleNew=projectDeclarationUiLabelMap.TitleNewProjectDeclaration?j_string
		titleDelete=projectDeclarationUiLabelMap.TitleDeleteProjectDeclaration?j_string
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
