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
			"name": projectDeclarationUiLabelMap.EquipmentBudget?j_string,
			"data": "equipmentBudget"
		},
		{
			"name": projectDeclarationUiLabelMap.ManagementBudget?j_string,
			"data": "managementBudget"
		},
		{
			"name": projectDeclarationUiLabelMap.AllocatedBudgetYear?j_string,
			"data": "allocatedBudgetYear"
		},
		{
			"name": projectDeclarationUiLabelMap.AcademicYearId?j_string,
			"data": "academicYearName"
		}
	] />
	
	<#assign fields=[
		"researchProjectDeclarationYearId",
		"researchProjectProposalId",
		"staffId",
		"createStaffId",
		"equipmentBudget",
		"managementBudget",
		"allocatedBudgetYear",
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
		{
			"name": projectDeclarationUiLabelMap.EquipmentBudget?j_string,
			"value": "equipmentBudget"
		},
		{
			"name": projectDeclarationUiLabelMap.ManagementBudget?j_string,
			"value": "managementBudget"
		},
		{
			"name": projectDeclarationUiLabelMap.AllocatedBudgetYear?j_string,
			"value": "allocatedBudgetYear"
		},
		
		{
			"name": projectDeclarationUiLabelMap.AcademicYearId?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": listAcademicYear,
				"maxItem": 1
			}
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
		{
			"name": projectDeclarationUiLabelMap.EquipmentBudget?j_string,
			"value": "equipmentBudget"
		},
		{
			"name": projectDeclarationUiLabelMap.ManagementBudget?j_string,
			"value": "managementBudget"
		},
		{
			"name": projectDeclarationUiLabelMap.AllocatedBudgetYear?j_string,
			"value": "allocatedBudgetYear"
		},

		{
			"name": projectDeclarationUiLabelMap.AcademicYearId?j_string,
			"value": "academicYearId",
			"type": "select",
			"option": {
				"source": listAcademicYear,
				"maxItem": 1
			}
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-project-budget-declaration" 
		columns=columns 
		columnsNew=columnsNew 
		columnsChange=columnsChange 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["researchProjectDeclarationYearId"] 
		fieldDataResult = "projectDeclarations"
		urlAdd="/bkeuniv/control/create-project-budget-declaration" 
		urlUpdate="/bkeuniv/control/update-project-budget-declaration" 
		urlDelete="/bkeuniv/control/delete-project-budget-declaration"
		titleChange=projectDeclarationUiLabelMap.TitleEditHour?j_string
		titleNew=projectDeclarationUiLabelMap.TitleNewHour?j_string
		titleDelete=projectDeclarationUiLabelMap.TitleDeleteHour?j_string
	/>
</div>
