<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
<div class="body">

	<#assign columns=[
		{
			"name": researchDomainUiLabelMap.BkEunivResearchDomainId?j_string,
			"data": "researchDomainId"
		},
		{
			"name": researchDomainUiLabelMap.BkEunivResearchDomainName?j_string,
			"data": "researchDomainName"
		}
	] />
	
	<#assign fields=[
		"researchDomainId",
		"researchDomainName"		
	] />
	
	<#assign columnsChange=[
		{
			"name": researchDomainUiLabelMap.BkEunivResearchDomainId?j_string,
			"value": "researchDomainId"
		},
		{
			"name": researchDomainUiLabelMap.BkEunivResearchDomainName?j_string,
			"value": "researchDomainName"
		}
	] />
	
	<#assign columnsNew=[
		{
			"name": researchDomainUiLabelMap.BkEunivResearchDomainId?j_string,
			"value": "researchDomainId"
		},
		{
			"name": researchDomainUiLabelMap.BkEunivResearchDomainName?j_string,
			"value": "researchDomainName"
		}
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		urlData="/bkeuniv/control/get-research-domain-management" 
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		columnsChange=columnsChange 
		columnsNew=columnsNew 
		urlUpdate="/bkeuniv/control/update-research-domain-management" 
		urlAdd="/bkeuniv/control/create-research-domain-management" 
		urlDelete="/bkeuniv/control/delete-research-domain-management" 
		keysId=["researchDomainId"] 
		fieldDataResult = "researchDomain" 
		titleChange="Edit"
		titleNew="Add"
		titleDelete="Delete"
		jqTitle="Research Domain"
	/>
</div>
