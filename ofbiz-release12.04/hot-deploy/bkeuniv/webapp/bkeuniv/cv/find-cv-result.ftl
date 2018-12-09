<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<script>
	
	<#assign urlGetData = "/bkeuniv/control/jqxGeneralServicer?sname=jqGetListScienceCV" />	
	
	

	<<#if parameters.researchDomainId?has_content>
		<#assign urlGetData = urlGetData + "&researchDomainId="+ parameters.researchDomainId />
		var researchDomainId = '${parameters.researchDomainId}';
	</#if>
	
	<#if parameters.researchSubDomainSeqId?has_content>
		<#assign urlGetData =  urlGetData + "&researchSubDomainSeqId="+ parameters.researchSubDomainSeqId />
		var researchSubDomainSeqId = '${parameters.researchSubDomainSeqId}';
	</#if>
	
	<#if parameters.researchSpecialitySeqId?has_content>
		<#assign urlGetData =  urlGetData + "&researchSpecialitySeqId="+ parameters.researchSpecialitySeqId />
		var researchSpecialitySeqId = '${parameters.researchSpecialitySeqId}';
	</#if>

	<#if parameters.numberProjectApplied?has_content>
		<#assign urlGetData =  urlGetData + "&numberProjectApplied="+ parameters.numberProjectApplied />
		var numberProjectApplied = '${parameters.numberProjectApplied}';
	</#if>

	<#if parameters.numberScientificService?has_content>
		<#assign urlGetData =  urlGetData + "&numberScientificService="+ parameters.numberScientificService />
		var numberScientificService = '${parameters.numberScientificService}';
	</#if>

	<#if parameters.numberPublications?has_content>
		<#assign urlGetData =  urlGetData + "&numberPublications="+ parameters.numberPublications />
		var numberPublications = '${parameters.numberPublications}';
	</#if>

	<#if parameters.numberRecent5YearProjects?has_content>
		<#assign urlGetData =  urlGetData + "&numberRecent5YearProjects="+ parameters.numberRecent5YearProjects />
		var numberRecent5YearProjects = '${parameters.numberRecent5YearProjects}';
	</#if>

</script>

<body>
<div class="body">
	<#assign columns=[
		
		{
			"name": bkEunivUiLabelMap.BkEunivFullName?j_string,
			"data": "staffName"
		},
		{
			"name": bkEunivUiLabelMap.NumberPublicationPaper?j_string,
			"data": "cvPaperNumber"
		},
		{
			"name": bkEunivUiLabelMap.NumberRecentProject?j_string,
			"data": "cvProjectNumber"
		},
		{
			"name": bkEunivUiLabelMap.NumberScienceService?j_string,
			"data": "scientificServiceExperienceNumber"
		},
		{
			"name": bkEunivUiLabelMap.NumberAppliedProject?j_string,
			"data": "appliedResearchProjectNumber"
		}
	] />
	
	<#assign fields=[
		"staffId",
		"staffName",
		"cvPaperNumber",
		"cvProjectNumber",
		"scientificServiceExperienceNumber",
		"appliedResearchProjectNumber"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData = urlGetData 
		optionData={
			"data": {
				"pagesize": "-1"?j_string
			}
		}
		columns=columns 
		dataFields=fields 
		sizeTable=sizeTable
		keysId=["staffId"] 
		fieldDataResult = "results" 
		jqTitle=bkEunivUiLabelMap.TitleWorkProgress?j_string
		contextmenu=false
	/>
</div>
