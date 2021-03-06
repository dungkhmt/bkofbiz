<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>
<div class="body">
	<#assign columns=[
		{
			"name": uiLabelMap.ProjectProposalName?j_string,
			"data": "researchProjectProposalName",
			"render": 'function(value, name, dataColumns, id) {
        return "<a href=\\"/bkeuniv/control/detail-research-project-proposal?researchProjectProposalId="+dataColumns.researchProjectProposalId+"\\">" + value + "</a>";
			}'
		},
		{
			"name": uiLabelMap.Evaluation?j_string,
			"data": "reviewerResearchProposalId",
			"className": "text-center",
			"render": 'function(value, name, dataColumns, id) {
				if(!!dataColumns.statusId) {
					switch(dataColumns.statusId) {
						case "ASSIGNED_REVIEWER":
							return \'<a href="/bkeuniv/control/form-evaluate-research-project-proposal?reviewerResearchProposalId=\'+dataColumns.reviewerResearchProposalId+\'">'+uiLabelMap.Evaluation+'</a>\';'+
						'case "CONFIRM_EVALUATION_PROPOSAL":
							return \'<a href="/bkeuniv/control/detail-current-evaluate-research-project-proposal?reviewerResearchProposalId=\'+dataColumns.reviewerResearchProposalId+\'">'+uiLabelMap.ViewEvaluation+'</a>\';'+
						'default:
							return "";
					}
				}
			}'
		}
	] />

  <#assign fields=[
		"reviewerResearchProposalId",
		"juryId",
		"researchProjectProposalId",
		"researchProjectProposalName",
		"staffId",
		"staffName",
		"statusId"
	] />

	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListProjectProposalsAssignedForReview" 
		<#--  optionData={
			"data": {
				"pagesize": "-1"?j_string
			}
		}  -->
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["researchProjectProposalId"]
		fieldDataResult = "results" 
		titleChange=uiLabelMap.BkEunivChange
		titleNew=uiLabelMap.BkEunivNew
		titleDelete=uiLabelMap.BkEunivDelete
		jqTitle=uiLabelMap.BkEunivManage
		contextmenu=false
	/>
</div>
