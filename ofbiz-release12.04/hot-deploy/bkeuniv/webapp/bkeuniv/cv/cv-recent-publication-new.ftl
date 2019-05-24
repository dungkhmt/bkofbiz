<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<div class="body">
	
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivPublicationName?j_string,
			"data": "paperName"
		},
		{
			"name": uiLabelMap.BkEunivCVProjectSeq?j_string,
			"data": "sequenceInCVPaper",
			"render": 'function(value, name, dataColumns, id) {
				return \'<form action="javascript:void(0);" class="form-input-seq"><input id="input-seq\'+dataColumns.paperId+\'" value="\'+value+\'" pattern="[1-9]([0-9]{0,2})" onBlur="updateListChangeSeq(event)"><button type="submit" id="check-validate-seq" style="display: none"></button></form><script>setCustomValidity("#input-seq\'+dataColumns.paperId+\'","'+StringUtil.wrapString(uiLabelMap.BkEunivMatchIntegerFormat)+'")<\\/script>\';
			}'
		}
	] />
	
	<#assign fields=[
		"paperId",
		"staffPaperDeclarationId",
		"sequenceInCVPaper",
		"staffName",
		"authors",
		"paperName",
		"paperCategoryName",
		"journalConferenceName",
		"researchProjectProposalName",
		"departmentName",
		"year",
		"volumn",
		"ISSN",
		"academicYearId",
		"paperDeclarationStatusName",
		"sequence"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetPapersOfStaffCV" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["paperId"]
		contextmenu=false
		advanceActionButton=[
			{
				"id": "submit",
				"onClick": "submit()",
				"width": "120px",
				"dImage": "M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z",
				"text": "Submit"
			}
		]
	/>
</div>

<script>
var test;
var list=[];
	function addUpdate(staffPaperDeclarationId, seq){
		var found = false;
		for(i = 0; i < list.length; i++){
			if(list[i].staffPaperDeclarationId==staffPaperDeclarationId){
				list[i].seq = seq; found = true; break; 
			}
		}
		if(found == false){
			var o = {"staffPaperDeclarationId":staffPaperDeclarationId,"seq":seq};
			list.push(o);
		}
	}
	function updateListChangeSeq(e) {
		//console.log(e)
		test=e
		$(e.target).parent()[0].reportValidity();
		var data = jqDataTable.table.row($(e.target).parent().parent()).data();

		var buttons = $(e.target.parentElement).find("#check-validate-seq");
		
		if(buttons.length > 0) {
			
			setTimeout(function(){ buttons[0].click(); }, 150);
			
			if(e.target.parentElement.checkValidity()) {
				
				var seqChange = e.target.value;
				addUpdate(data.staffPaperDeclarationId,seqChange);
			}
		}

		//console.log(data, seqChange)
		<#--  $(e.target).parent()[0].reportValidity()
		console.log($(e.target).parent()[0].reportValidity())  -->
		
	}
	
	function submit(){
		console.log(list);
		
		if(list.length > 0) {
			
			var json = JSON.stringify(list);
			
			
			$.ajax({
				url: "/bkeuniv/control/update-cv-papers",
				type: 'POST',
				data:{"json":json},
				success: function(rs){
					setTimeout(function(){ 
						loader.close();
						alertify.success("${uiLabelMap.BkEunivSaveSuccess}");
						list = [];
					}, 500);
				}
			})
		}

				
	}
</script>