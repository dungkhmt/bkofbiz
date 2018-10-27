<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<div class="body">
	
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName"
		},
		{
			"name": "Tac gia"?j_string,
			"data": "authors"
		},
		{
			"name": "seq"?j_string,
			"data": "sequenceInCVPaper",
			"render": 'function(value, name, dataColumns, id) {
				return \'<input value="\'+value+\'" onChange="updateListChangeSeq(event)">\';
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
		var data = jqDataTable.table.row($(e.target).parent()).data();
		var seqChange = e.target.value;
		//console.log(data, seqChange)
		addUpdate(data.staffPaperDeclarationId,seqChange);
	}
	
	function submit(){
		console.log(list);
		
		var json = JSON.stringify(list);
		
		$.ajax({
					url: "/bkeuniv/control/update-cv-papers",
					type: 'POST',
					data:{"json":json},
					success: function(rs){
						setTimeout(function(){ 
							loader.close();
							alertify.success("${uiLabelMap.BkEunivSaveSuccess}");
						}, 500);
					}
				})
				
	}
</script>