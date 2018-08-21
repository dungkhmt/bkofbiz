<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>
<script>
	function updateStyleCell(idTable) {
		Array.from($("#jqDataTable .unqualified-staff")).forEach(function(el) {
			var cell = el.parentElement;
			var row = cell.parentElement;
			row.style.backgroundColor = "#ff8080";
		})

		Array.from($("#jqDataTable .new-staff")).forEach(function(el) {
			var cell = el.parentElement;
			var row = cell.parentElement;
			row.style.backgroundColor = "#80ff80";
		})

		Array.from($("#jqDataTable .update-staff")).forEach(function(el) {
			var cell = el.parentElement;
			var row = cell.parentElement;
			cell.style.backgroundColor = "#ffdb4d";
		})
	}

	function synchronize() {
		loader.open();
		$.ajax({
			url: "/bkeuniv/control/pull-staff",
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

	function mergeSynchronize() {
		loader.open();
		$.ajax({
			url: "/bkeuniv/control/merge-synchronize-staff",
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
			"name": uiLabelMap.BkEunivFullName?j_string,
			"data": "staffName",
			"render": 'function(value, name, dataColumns, meta) {
				if(!!dataColumns.old&&!!dataColumns.staffEmail) {
					var old = dataColumns.old;
					if(old["staffName"] != dataColumns["staffName"]) {
						return \'<div class="update-staff">\'+value+\'</div>\';
					}
				}
				return value;
			}'
		},
		{
			"name": uiLabelMap.BkEunivEmail?j_string,
			"data": "staffEmail",
			"render": 'function(value, name, dataColumns, meta) {
				if(!value&&!dataColumns.staffId) {
					return \'<div class="unqualified-staff">\'+value+\'</div>\';
				} else {
					if(!dataColumns.old&&!dataColumns.staffId) {
						return \'<div class="new-staff">\'+value+\'</div>\';
					}
				}
				return value;
			}'
		},
		{
			"name": uiLabelMap.BkEunivGender?j_string,
			"data": "genderName",
			"render": 'function(value, name, dataColumns, meta) {
				if(!!dataColumns.old&&!!dataColumns.staffEmail) {
					var old = dataColumns.old;
					if(old["genderName"] != dataColumns["genderName"]) {
						return \'<div class="update-staff">\'+value+\'</div>\';
					}
				}
				return value;
			}'
		},
		{
			"name": uiLabelMap.BkEunivBirthDate?j_string,
			"data": "staffDateOfBirth",
			"orderable": "false",
			"render": 'function(value, name, dataColumns, meta) {
				if(!!dataColumns.old&&!!dataColumns.staffEmail) {
					var old = dataColumns.old;
					if(old["staffDateOfBirth"] != dataColumns["staffDateOfBirth"]) {
						return \'<div class="update-staff">\'+parseDate(value)+\'</div>\';
					}
				}
				return parseDate(value);
			}'
		},
		{
			"name": uiLabelMap.BkEunivDepartment?j_string,
			"data": "departmentName",
			"render": 'function(value, name, dataColumns, meta) {
				if(!!dataColumns.old&&!!dataColumns.staffEmail) {
					var old = dataColumns.old;
					if(old["departmentName"] != dataColumns["departmentName"]) {
						return \'<div class="update-staff">\'+value+\'</div>\';
					}
				}
				return value;
			}'
		},
		{
			"name": uiLabelMap.BkEunivFaculty?j_string,
			"data": "facultyName",
			"render": 'function(value, name, dataColumns, meta) {
				if(!!dataColumns.old&&!!dataColumns.staffEmail) {
					var old = dataColumns.old;
					if(old["facultyName"] != dataColumns["facultyName"]) {
						return \'<div class="update-staff">\'+value+\'</div>\';
					}
				}
				return value;
			}'
		}
	] />
	
	<#assign fields=[
		"staffId",
		"staffName",
		"staffEmail",
		"genderName",
		"staffDateOfBirth",
		"departmentName",
		"facultyName",
		"old"
	] />

	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
	<#assign groups=[
		{"text": uiLabelMap.BkEunivAll, "value": "all"},
		{"text": uiLabelMap.BkEunivNewStaff, "value": "new"},
		{"text": uiLabelMap.BkEunivUpdateStaff, "value": "update"},
		{"text": uiLabelMap.BkEunivUnqualifiedStaff, "value": "unqualified"}
	] />
	<@jqDataTable
		id="jqDataTable"
		optionData={
			"data": {
				"category": '$("#category").val()#JS'
			}
		}
		advanceActionButton=[
			{
				"id": "synchronize-staff",
				"onClick": "synchronize()",
				"width": "120px",
				"dImage": "M12 4V1L8 5l4 4V6c3.31 0 6 2.69 6 6 0 1.01-.25 1.97-.7 2.8l1.46 1.46C19.54 15.03 20 13.57 20 12c0-4.42-3.58-8-8-8zm0 14c-3.31 0-6-2.69-6-6 0-1.01.25-1.97.7-2.8L5.24 7.74C4.46 8.97 4 10.43 4 12c0 4.42 3.58 8 8 8v3l4-4-4-4v3z",
				"text": uiLabelMap.BkEunivSync
			},
			{
				"id": "merge-synchronize",
				"onClick": "mergeSynchronize()",
				"width": "120px",
				"dImage": "M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z",
				"text": uiLabelMap.BkEunivSave
			}
		]
		filters=[
			{
				"id": "category",
				"label": uiLabelMap.BkEunivCategory,
				"type": "select-render-html",
				"data": groups,
				"autoSelect": true,
				"onChange": "JqRefresh()",
				"require": true
			}
		]
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListSynchronizeStaff" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["staffEmail"]
		contextmenu=false
		fnInfoCallback = 'function() {updateStyleCell("jqDataTable")}'
	/>
</div>
