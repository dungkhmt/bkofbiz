<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<div class="body">
	
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivStaffId?j_string,
			"data": "staffName",
			"width": "150px"
		},
		{
			"name": "Tac gia"?j_string,
			"data": "authors",
			"width": "200px"
		},
		{
			"name": uiLabelMap.BkEunivPaperName?j_string,
			"data": "paperName",
			"width": "350px",
			 "render": 'function(value, name, dataColumns, id) {
				return \'<a href="/bkeuniv/control/approve-paper?paperId=\'+dataColumns.paperId+ \'" target="_blank">\'+value+\'</a>\';
			}'
		},
		{
			"name": uiLabelMap.BkEunivPaperCategory?j_string,
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
		"journalConferenceName",
		"researchProjectProposalName",
		"departmentName",
		"year",
		"volumn",
		"ISSN",
		"academicYearId",
		"paperDeclarationStatusName"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	
	<#assign optionData={ "data": {"facultyId": '$("#faculty").css("display")=="none"&&$("#faculty-input").val()!="all"?$("#faculty-input").val():undefined#JS'}} />
	

    <#assign faculties=[{"text": uiLabelMap.BkEunivAll, "value": "all"}] />
    <#list listFaculties.faculties as f>
        <#assign faculties=faculties+[{"text": f.facultyName, "value": f.facultyId}] />
    </#list>

    <#assign academicYears=[{"text": uiLabelMap.BkEunivAll, "value": "all"}] />
    <#list listAcademicYears.academicYears as y>
        <#assign academicYears=academicYears+[{"text": y.academicYearName, "value": y.academicYearId}] />
    </#list>

    <#assign paperCategory=[{"text": uiLabelMap.BkEunivAll, "value": "all"}] />
    <#list listPaperCategory.result as pc>
        <#assign paperCategory=paperCategory+[{"text": pc.paperCategoryName, "value": pc.paperCategoryId}] />
    </#list>

    <#assign status=[{"text": uiLabelMap.BkEunivAll, "value": "all"}] />
    <#list listStatus.statuses as s>
        <#assign status=status+[{"text": s.paperDeclarationStatusName, "value": s.paperDeclarationStatusId}] />
    </#list>

	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetPaperDeclarations" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["paperId"]
		contextmenu=false
        filters=[
			{
				"id": "faculty",
				"label": uiLabelMap.SelectFaculty,
				"type": "select-render-html",
				"data": faculties,
				"autoSelect": true,
				"onChange": "JqRefresh()",
				"require": false
			},
            {
				"id": "academicYear",
				"label": uiLabelMap.SelectYear,
				"type": "select-render-html",
				"data": academicYears,
				"autoSelect": true,
				"onChange": "JqRefresh()",
				"require": true
			},
            {
				"id": "paperCategory",
				"label": uiLabelMap.SelectPaperCategory,
				"type": "select-render-html",
				"data": paperCategory,
				"autoSelect": true,
				"onChange": "JqRefresh()",
				"require": true
			},
            {
				"id": "paperDeclarationStatus",
				"label": uiLabelMap.SelectStatus,
				"type": "select-render-html",
				"data": status,
				"autoSelect": true,
                "require": true,
				"onChange": "JqRefresh()"
			}
		]
		getDataFilter= "getFilter()"
		optionData=optionData
	/>
</div>

<script>
	
	function getFilter() {
        var academicYear = $("#academicYear").val();
        var paperCategory = $("#paperCategory").val();
        var paperDeclarationStatus = $("#paperDeclarationStatus").val();

        var filter = {
			"expressions": [],
			"operation": "AND"
		};

        if(!!academicYear && academicYear!="all") {
            filter.expressions.push({
				"field": "academicYearId",
				"operation": "EQUAL",
				"value": academicYear
			});
        }

        if(!!paperCategory && paperCategory!="all") {
            filter.expressions.push({
				"field": "paperCategoryId",
				"operation": "EQUAL",
				"value": paperCategory
			});
        }

        if(!!paperDeclarationStatus && paperDeclarationStatus!="all") {
            filter.expressions.push({
				"field": "academicYearId",
				"operation": "EQUAL",
				"value": paperDeclarationStatus
			});
        }

		if(filter.expressions.length > 0) {
			return filter;
		}
	}
</script>