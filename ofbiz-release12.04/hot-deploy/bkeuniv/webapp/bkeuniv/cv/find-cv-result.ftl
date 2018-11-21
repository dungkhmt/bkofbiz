<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<script>

<#if parameters.researchSpecialityId?has_content>
	
	<#assign urlGetData = "/bkeuniv/control/jqxGeneralServicer?sname=jqGetListScienceCV&researchSpecialityId=" + parameters.researchSpecialityId />	
	
	<#if parameters.sections?has_content>
		<#assign sections = parameters.sections />
		<#if sections?size gt 1>
			<#list sections as section>
				<#assign urlGetData = urlGetData + "&sections=" + section />
			</#list>
		<#else>
			<#assign urlGetData = urlGetData + "&sections=" + sections/>
		</#if>

	</#if>

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
			"name": bkEunivUiLabelMap.BkEunivResearchSpeciality?j_string,
			"data": "researchSpecialityName"
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
		"researchSpecialityName",
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
