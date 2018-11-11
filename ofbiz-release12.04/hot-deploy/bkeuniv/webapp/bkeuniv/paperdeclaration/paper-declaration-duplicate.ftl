<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>
<script>
	function updateStyleCell(idTable) {
		Array.from($("#jqDataTable .distance-success")).forEach(function(el) {
			var cell = el.parentElement;
			var row = cell.parentElement;
			row.style.backgroundColor = "#80ff80";
		})

		Array.from($("#jqDataTable .distance-warning")).forEach(function(el) {
			var cell = el.parentElement;
			var row = cell.parentElement;
			row.style.backgroundColor = "#ffdb4d";
		})

		Array.from($("#jqDataTable .distance-danger")).forEach(function(el) {
			var cell = el.parentElement;
			var row = cell.parentElement;
			row.style.backgroundColor = "#ff8080";
		})
	}

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

	function synchronize() {
		loader.open();
		$.ajax({
			url: "/bkeuniv/control/run-job-paper-declarations-duplicate",
			type: 'POST',
			success: function(rs){
				if(rs.statusCode=="200") {
					alertify.success(rs.message);
				} else {
					alertify.error(rs.message);
				}
				JqRefresh();
			},
			error: function(e) {
				alertify.error(e);
			}
		})
	}

</script>

<div class="body">
	
	<#assign columns=[
		{
			"name": uiLabelMap.BkEunivPaper1?j_string,
			"data": "data1",
            "orderable": "false",
			"render": 'function(value, name, dataColumns, meta) {

				return \'<a href="/bkeuniv/control/detail-paper?paperId=\'+value["paperId"]+ \'">\'+value["paperName"]+\'</a>\';
				
			}'
		},
		{
			"name": uiLabelMap.BkEunivPaper2?j_string,
			"data": "data2",
            "orderable": "false",
			"render": 'function(value, name, dataColumns, meta) {
				
				return \'<a href="/bkeuniv/control/detail-paper?paperId=\'+value["paperId"]+ \'">\'+value["paperName"]+\'</a>\';

			}'
		},
		{
			"name": uiLabelMap.BkEunivMatching?j_string,
			"data": "distance",
            "orderable": "false",
			"render": 'function(value, name, dataColumns, meta) {
				var levelDuplicate="distance-success";
				if(value > 50) {
					levelDuplicate="distance-success";
				} else {
					if(value > 10) {
						levelDuplicate="distance-warning";
					} else {
						levelDuplicate="distance-danger";
					}
				}
				
				return \'<div class="\'+levelDuplicate+\'">\'+value+\'</div>\';
			}'
		}
	] />
	
	<#assign fields=[
		"paperId",
		"data1",
		"data2",
		"distance"
	] />

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

	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<#assign groups=[
		{"text": uiLabelMap.BkEunivAll, "value": "all"},
		{"text": uiLabelMap.BkEunivNewStaff, "value": "new"},
		{"text": uiLabelMap.BkEunivUpdateStaff, "value": "update"},
		{"text": uiLabelMap.BkEunivUnqualifiedStaff, "value": "unqualified"}
	] />
	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetPaperDeclarationsDuplicate" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["paperId"]
		contextmenu=false
		fnInfoCallback = 'function() {updateStyleCell("jqDataTable")}'
		advanceActionButton=[
			{
				"id": "run-job-papers",
				"onClick": "synchronize()",
				"width": "120px",
				"dImage": "M12 4V1L8 5l4 4V6c3.31 0 6 2.69 6 6 0 1.01-.25 1.97-.7 2.8l1.46 1.46C19.54 15.03 20 13.57 20 12c0-4.42-3.58-8-8-8zm0 14c-3.31 0-6-2.69-6-6 0-1.01.25-1.97.7-2.8L5.24 7.74C4.46 8.97 4 10.43 4 12c0 4.42 3.58 8 8 8v3l4-4-4-4v3z",
				"text": uiLabelMap.BkEunivSync
			}
		]
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
