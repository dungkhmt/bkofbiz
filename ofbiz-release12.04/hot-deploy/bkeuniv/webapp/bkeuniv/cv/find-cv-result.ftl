<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>

<script>
	
	<#assign urlGetData = "/bkeuniv/control/jqxGeneralServicer?sname=jqGetListScienceCV" />	
	
	

	<#if parameters.sections?has_content>
		<#assign sections = parameters.sections />
		<#if sections?has_content>
			<#list sections as section>
				<#assign urlGetData = urlGetData + "&sections=" + "${StringUtil.wrapString(section?if_exists)}" />
			</#list>
		</#if>
	</#if>

	<#if parameters.staffId?has_content>
		<#assign urlGetData = urlGetData + "&staffId=" + "${StringUtil.wrapString(parameters.staffId)}" />
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
