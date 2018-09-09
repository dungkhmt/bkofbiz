<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": projectDeclarationUiLabelMap.ResearchProjectProposalName?j_string,
			"data": "researchProjectProposalName",
			"render": 'function(value, name, dataColumns, id) {
                return "<a href=\\"/bkeuniv/control/detail-research-project-proposal?researchProjectProposalId="+dataColumns.researchProjectProposalId+"\\">" + value + "</a>";
			}'
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
			"name": projectDeclarationUiLabelMap.ProjectParticipationPercentage?j_string,
			"data": "staffParticipationPercentage",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon hoac bang 100"
		}
	] />
	
	<#assign fields=[
		"researchProjectDeclarationYearId",
		"researchProjectProposalId",
		"staffId",
		"createStaffId",
		"projectParticipationRoleId",
		"workinghours",
		"staffParticipationPercentage",
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
				"render": 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}',
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
			"name": projectDeclarationUiLabelMap.ProjectParticipationPercentage?j_string,
			"value": "staffParticipationPercentage",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon hoac bang 100"
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
				"render": 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}',
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
			"name": projectDeclarationUiLabelMap.ProjectParticipationPercentage?j_string,
			"value": "staffParticipationPercentage",
			"pattern": "[1-9]([0-9]{0,3})",
			"title": "So nguyen duong nho hon hoac bang 100"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-participation-declaration" 
		columns=columns 
		columnsNew=columnsNew 
		columnsChange=columnsChange 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["researchProjectDeclarationYearId"] 
		fieldDataResult = "projectDeclarations"
		urlAdd="/bkeuniv/control/create-project-participation-declaration" 
		urlUpdate="/bkeuniv/control/update-project-participation-declaration" 
		urlDelete="/bkeuniv/control/delete-project-participation-declaration"
		titleChange=projectDeclarationUiLabelMap.TitleEditHour?j_string
		titleNew=projectDeclarationUiLabelMap.TitleNewHour?j_string
		titleDelete=projectDeclarationUiLabelMap.TitleDeleteHour?j_string
	/>
</div>
