<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	var modalChangePassword;
	function createContextMenu(id) {
	
		$(document).contextmenu({
			delegate: "#"+id+"-content td",
			menu: [
				//{title: ${paperDeclarationUiLabelMap.BkEunivApprovePaper}, cmd: "approve"},
				//{title: ${paperDeclarationUiLabelMap.BkEunivRejectPaper}, cmd: "reject"}
				{title: "Phe duyet", cmd: "approve"},
				{title: "Khong phe duyet", cmd: "reject"},
				{title: 'Download minh chung', cmd: "pdf", uiIcon: "glyphicon glyphicon-save"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var data = jqDataTable.table.row( el ).data();
				switch(ui.cmd){
					case "approve":
						//$(ui.target).parent().remove();
						//alert("phe duyet bai bao " + paperId);
						approvePaper(data);
						
						break;
					case "reject":
						//alert('edit paper ' + paperId);
						rejectPaper(data);
						break;
					case "pdf":
						//alert('download paper ' + paperId);
						jqPDF(data);
						break;
					
				}
			},
			beforeOpen: function(event, ui) {
				var $menu = ui.menu,
					$target = ui.target,
					extraData = ui.extraData;
				ui.menu.zIndex(9999);
			}
		});
	}
</script>

<div class="body">
	
	<#assign columns=[
		{
			"name": paperDeclarationUiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName",
			"width": "150px"
		},
		{
			"name": "Tac gia"?j_string,
			"data": "authors",
			"width": "200px"
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperName?j_string,
			"data": "paperName",
			"width": "350px",
			 "render": 'function(value, name, dataColumns, id) {
				return \'<a href="/bkeuniv/control/detail-paper?paperId=\'+dataColumns.paperId+ \'">\'+value+\'</a>\';
			}'
		},
		{
			"name": paperDeclarationUiLabelMap.BkEunivPaperCategory?j_string,
			"data": "paperCategoryName",
			"width": "150px"
		},
		{
			"name": "Thuoc de tai"?j_string,
			"width": "200px",
			"data": "researchProjectProposalName"
		},
		{
			"name": "Ten hoi nghi, tap chi"?j_string,
			"width": "150px",
			"data": "journalConferenceName"
		},
		{
			"name": "Nam"?j_string,
			"data": "year"
		},
		{
			"name": "Vol. number"?j_string,
			"data": "volumn"
		},
		{
			"name": "ISSN"?j_string,
			"data": "ISSN"
		},
		{
			"name": "Nam ke khai"?j_string,
			"data": "academicYearId"
		},
		{
			"name": "Trang thai"?j_string,
			"data": "paperDeclarationStatusName"
		}
	] />
	
	<#assign fields=[
		"paperId",
		"staffName",
		"authors",
		"paperName",
		"paperCategoryName",
		"researchProjectProposalName",
		"departmentName",
		"year",
		"volumn",
		"ISSN",
		"academicYearId",
		"paperDeclarationStatusName"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<#assign optionData={} />
	<#if parameters.facultyId??&&parameters.facultyId!="all">
		<#assign optionData={ "data": {"facultyId": parameters.facultyId?j_string}} />
	</#if>

	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetPaperDeclarations" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["paperId"]
		contextmenu=false
		fnInfoCallback = 'function() {createContextMenu("jqDataTable")}'
		getDataFilter= "getFilter()"
		optionData=optionData
	/>
</div>

<script>
var obj;

function jqPDF(data){
		var paperId = data.paperId
		console.log(paperId);
		window.open("/bkeuniv/control/download-file-paper?id-paper=" + paperId, "_blank")
}	
	
function approvePaper(data){
	var paperId = data.paperId;
	var staffId = "${login.userLoginId}";
	//alert("approve paper staff = " + staffId);
	$.ajax({
					url: "/bkeuniv/control/approve-a-paper-declaration",
					type: 'POST',
					data: {
						"paperId": paperId,
						"staffId": staffId
					},
					success:function(rs){
						console.log(rs);
						//alert("Bai bao khong duoc phe duyet " + rs.status);
					}
	
	});
				
}
function rejectPaper(data){
	var staffId = "${login.userLoginId}";
	var paperId = data.paperId;
	//alert("approve paper staff = " + staffId);
	$.ajax({
		url: "/bkeuniv/control/reject-a-paper-declaration",
		type: 'POST',
		data: {
			"paperId": paperId,
			"staffId": staffId
		},
		success:function(rs){
			console.log(rs);
			//alert("Bai bao da duoc phe duyet " + rs.status);
		}
	});
				
}
	var filter = {
			"expressions": [],
			"operation": "AND"
		};
	function getFilter() {
		<#--  var filter = {
			"expressions": [],
			"operation": "AND"
		};  -->

		<#if parameters.academicYearId??&&parameters.academicYearId!="all">
			filter.expressions.push({
				"field": "academicYearId",
				"operation": "EQUAL",
				"value": '${parameters.academicYearId}'
			});
		</#if>

		<#if parameters.paperCategoryId??&&parameters.paperCategoryId!="all">
			filter.expressions.push({
				"field": "paperCategoryId",
				"operation": "EQUAL",
				"value": '${parameters.paperCategoryId}'
			});
		</#if>

		<#if parameters.paperDeclarationStatusId??&&parameters.paperDeclarationStatusId!="all">
			filter.expressions.push({
				"field": "approveStatusId",
				"operation": "EQUAL",
				"value": '${parameters.paperDeclarationStatusId}'
			});
		</#if>

		if(filter.expressions.length > 0) {
			return filter;
		}
	}
</script>