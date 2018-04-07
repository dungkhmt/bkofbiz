<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": projectDeclarationUiLabelMap.partyId?j_string,
			"data": "partyId"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectCategoryId?j_string,
			"data": "projectCategoryName"
		},
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"data": "researchProjectProposalName"
		},
		{
			"name": projectDeclarationUiLabelMap.StartDate?j_string,
			"data": "startDate"
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
			"value": "researchProjectProposalName"
		},
		{
			"name": projectDeclarationUiLabelMap.TotalBudget?j_string,
			"value": "totalBudget"
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
			"name": projectDeclarationUiLabelMap.TotalBudget?j_string,
			"value": "totalBudget"
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
	/>
</div>
