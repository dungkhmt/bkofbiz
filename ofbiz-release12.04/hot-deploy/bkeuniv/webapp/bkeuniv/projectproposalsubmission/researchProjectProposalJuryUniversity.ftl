<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	function createContextMenu(id) {
	
	}
		
</script>

<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.ProjectCallName?j_string,
			"data": "projectCallName"
		},
		{
			"name": uiLabelMap.Category?j_string,
			"data": "projectCategoryName"
		},
		
		{
			"name": uiLabelMap.Year?j_string,
			"data": "year"
		},
		{
			"name": uiLabelMap.Status?j_string,
			"data": "statusName"
		},
		{
			"name": uiLabelMap.JuryName?j_string,
			"data": "juryName",
			"render": 'function(value, name, dataColumns, id) {
				if(!!value) {
					return \'<a href="/bkeuniv/control/detail-jury-proposal?juryId=\'+dataColumns.juryId+\'">\'+value+\'</a>\';
				} else {
					return \'<a href="/bkeuniv/control/form-add-project-proposal-jury-university?projectCallId=\'+dataColumns.projectCallId+\'&facultyId=UNIVERSITY">'+uiLabelMap.CreateJury+'</a>\';'+
				'}
			}'
		}
	] />
	
	<#assign fields=[
		"projectCallId",
		"projectCallName",
		"year",
		"projectCategoryId",
		"statusId",
		"projectCategoryName",
		"statusName",
		"juryId",
		"juryName"
	] />

	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListProjectCallsAndProposalJuriesUniversity" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["projectCallId"]
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
	/>
</div>
