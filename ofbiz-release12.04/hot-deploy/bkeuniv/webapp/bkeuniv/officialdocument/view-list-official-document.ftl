<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	function createContextMenu(id) {
	
	}
</script>

<div class="body">

	<#assign columns=[
		{
			"name": uiLabelMap.officialDocumentId?j_string,
			"data": "officialDocumentId"
		},
		{
			"name": uiLabelMap.officialDocumentName?j_string,
			"data": "officialDocumentName"
		},
		{
			"name": uiLabelMap.officialDocumentTypeName?j_string,
			"data": "officialDocumentTypeName"
		},
		{
			"name": uiLabelMap.staffName?j_string,
			"data": "staffName"

		},
    	{
			"name": uiLabelMap.uploadDate?j_string,
			"data": "uploadDate",
			"type": "date"
		}
	] />
	
	<#assign fields=[
		"staffId",
		"officialDocumentId",
		"officialDocumentName",
		"officialDocumentTypeName",
		"officialDocumentTypeId",
		"uploadDate"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListOfficialDocument" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		urlDelete="/bkeuniv/control/remove-a-staff" 
		keysId=["officialDocumentId"] 
		fieldDataResult = "results"
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>

</div>