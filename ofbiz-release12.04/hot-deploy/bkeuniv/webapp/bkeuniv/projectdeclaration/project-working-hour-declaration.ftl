<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"data": "researchProjectProposalName"
		},
		{
			"name": projectDeclarationUiLabelMap.CreateStaffId?j_string,
			"data": "createStaffId"
		},
		{
			"name": projectDeclarationUiLabelMap.StaffId?j_string,
			"data": "staffName"
		},
		{
			"name": projectDeclarationUiLabelMap.ProjectParticipationRoleId?j_string,
			"data": "projectParticipationRoleName"
		},
		{
			"name": projectDeclarationUiLabelMap.AcademicYearId?j_string,
			"data": "academicYearName"
		},
		{
			"name": projectDeclarationUiLabelMap.BkEunivWorkingHours?j_string,
			"data": "workinghours",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon 9999"
		}
	] />
	
	<#assign fields=[
		"researchProjectDeclarationYearId",
		"researchProjectProposalId",
		"staffId",
		"createStaffId",
		"projectParticipationRoleId",
		"workinghours",
		"academicYearId",
		"academicYearName",
		"staffName",
		"researchProjectProposalName",
		"projectParticipationRoleName"
	] />
	
	<#assign listAcademicYear = [] />
	<#list academicYearS.academicYears as project>
		<#if project?has_content>
             <#assign op = { "name": project.academicYearName?j_string ,"value": project.academicYearId?j_string } />
						<#assign listAcademicYear = listAcademicYear + [op] />
		</#if>
	</#list>
	
	<#assign listResearchProjectProposal = [] />
	<#list researchProjectProposalS.researchProjectProposals as project>
		<#if project?has_content>
             <#assign op = { "name": project.researchProjectProposalName?j_string ,"value": project.researchProjectProposalId?j_string } />
						<#assign listResearchProjectProposal = listResearchProjectProposal + [op] />
		</#if>
	</#list>
	
	<#assign listProjectParticipationRole = [] />
	<#list projectParticipationRoleS.projectParticipationRoles as project>
		<#if project?has_content>
             <#assign op = { "name": project.projectParticipationRoleName?j_string ,"value": project.projectParticipationRoleId?j_string } />
						<#assign listProjectParticipationRole = listProjectParticipationRole + [op] />
		</#if>
	</#list>
	
	
	<#assign columnsNew=[
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"value": "researchProjectProposalId",
			"type": "select",
			"option": {
				"source": listResearchProjectProposal,
				"maxItem": 1
			}
		},
		<!--
		{
			"name": projectDeclarationUiLabelMap.StaffId?j_string,
			"value": "staffId",
			"type": "select",
			"option": {
				"source": listStaff,
				"maxItem": 1
			}
		},
		-->
		{
			"name": projectDeclarationUiLabelMap.StaffId?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {id: r.staffId, text: "[" + r.staffId + "] "+ r.staffName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
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
			"name": projectDeclarationUiLabelMap.AcademicYearId?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": listAcademicYear,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.BkEunivWorkingHours?j_string,
			"value": "workinghours",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon 9999"
		}
	] />
	
	<#assign columnsChange=[
	{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"value": "researchProjectProposalId",
			"type": "select",
			"option": {
				"source": listResearchProjectProposal,
				"maxItem": 1
			}
		},
		<!--
		{
			"name": projectDeclarationUiLabelMap.StaffId?j_string,
			"value": "staffId",
			"type": "select",
			"option": {
				"source": listStaff,
				"maxItem": 1
			}
		},
		-->
		{
			"name": projectDeclarationUiLabelMap.StaffId?j_string,
			"value": "staffId",
			"type":"select_server_side",
			"option":{
				"maxItem": 1,
				"render": 'function(r){return {id: r.staffId, text: "[" + r.staffId + "] "+ r.staffName}}',
				"url": "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs"
			}
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
			"name": projectDeclarationUiLabelMap.AcademicYearId?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": listAcademicYear,
				"maxItem": 1
			}
		},
		{
			"name": projectDeclarationUiLabelMap.BkEunivWorkingHours?j_string,
			"value": "workinghours",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon 9999"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-working-hour-declaration" 
		columns=columns 
		columnsNew=columnsNew 
		columnsChange=columnsChange 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["researchProjectDeclarationYearId"] 
		fieldDataResult = "projectDeclarations"
		urlAdd="/bkeuniv/control/create-project-working-hour-declaration" 
		urlUpdate="/bkeuniv/control/update-project-working-hour-declaration" 
		urlDelete="/bkeuniv/control/delete-project-working-hour-declaration"
		titleChange=projectDeclarationUiLabelMap.TitleEditHour?j_string
		titleNew=projectDeclarationUiLabelMap.TitleNewHour?j_string
		titleDelete=projectDeclarationUiLabelMap.TitleDeleteHour?j_string
	/>
</div>
