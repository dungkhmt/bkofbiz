<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"data": "researchSpecialityId"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityName?j_string,
			"data": "researchSpecialityName"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchDomainId?j_string,
			"data": "researchDomainId"
		}
	] />
	
	<#assign source = [] />
	<#list researchDomainManagement.result as rs>
		<#if rs?has_content>
             <#assign op = { "name": rs.researchDomainName?j_string ,"value": rs.researchDomainId?j_string } />
						<#assign source = source + [op] />
		</#if>
	</#list>
	
	<#assign fields=[
		"researchSpecialityId",
		"researchSpecialityName",
		"researchDomainId"		
	] />
	
	<#assign columnsChange=[
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"value": "researchSpecialityId"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityName?j_string,
			"value": "researchSpecialityName"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchDomainId?j_string,
			"value": "researchDomainId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityId?j_string,
			"value": "researchSpecialityId"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchSpecialityName?j_string,
			"value": "researchSpecialityName"
		},
		{
			"name": researchSpecialityUiLabelMap.BkEunivResearchDomainId?j_string,
			"value": "researchDomainId",
			"type": "select",
			"option": {
				"source": source,
				"maxItem": 1
			}
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-research-speciality-management" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-research-speciality-management" 
		urlAdd="/bkeuniv/control/create-research-speciality-management" 
		urlDelete="/bkeuniv/control/delete-research-speciality-management" 
		keysId=["researchSpecialityId"] 
		fieldDataResult = "researchSpeciality" 
		titleChange="Edit"
		titleNew="Add"
		titleDelete="Delete"
		jqTitle="Research Speciality"
	/>
</div>
