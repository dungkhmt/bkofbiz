<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<body>
	<div class="body">

		<#assign columns=[
			{
				"name": ""?j_string,
				"data": "checked",
				"orderable": "false",
				"render": 'function ( value, name, dataColumns, id ) {
					return \'<input type="checkbox" \' + ((value == 1) ? "checked" : "") + \' id="input-\' + dataColumns.functionId + \'" class="filter-ck" onClick="ClickCheckboxF(this)" style="width: 1.3em; height: 1.3em;" />\';
				}'
			},
			{
				"name": "ID"?j_string,
				"data": "functionId"
			},
			{
				"name": "VN_LABEL"?j_string,
				"width": "5000px",
				"data": "vnLabel"
			},
			{
				"name": "PARENT_ID"?j_string,
				"data": "parentFunctionId"
			} 
		] />
		
		<#assign fields=[
			"functionId",
			"checked",
			"parentFunctionId",
			"vnLabel"
		] />
		
		<#assign groups=[] />

		<#list securityGroup.securityGroups as sg>
			<#assign groups = groups + [{"text": sg.description, "value": sg.groupId}] />
		</#list>
		
		<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />
		
		<@jqDataTable
			id="datatable-security-group-function"
			urlData="/bkeuniv/control/get-functions-of-security-group" 
			optionData={
				"data": {
					"groupId": '$("#security-group-function").val()#JS'
				}
			}
			columns=columns 
			dataFields=fields 
			sizeTable=sizeTable
			keysId=["functionId"] 
			fieldDataResult = "functions" 
			jqTitle=uiLabelMap.BkEunivManage
			contextmenu=false
			advanceActionButton=[
				{
					"id": "updatePermission",
					"onClick": "updatePermission()",
					"width": "120px",
					"dImage": "M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z",
					"text": uiLabelMap.BkEunivSave
				}
			]
			filters=[
				{
					"id": "security-group-function",
					"label": bkEunivUiLabelMap.SecurityGroup,
					"type": "select-render-html",
					"data": groups,
					"autoSelect": true,
					"onChange": "JqRefresh()",
					"require": true
				}
			]
		/>

		<script>
			function ClickCheckboxF(that) {
				var data = jqDataTable.table.row($(that).parent()).data();
				
				if (that.checked) {  //update the cell data with the checkbox state
					data.checked = 1;
				} else {
					data.checked = 0;
				}
			}

			function updatePermission() {
				var groupId = $("#security-group-function").val();
				var funcs = jqDataTable.table.rows().data().filter(function(el) {
					return el.checked
				}).map(function(f) {
					return f.functionId
				}).join(", ")
				loader.open();
				$.ajax({
					url: "/bkeuniv/control/store-security-group-functions",
					type: 'POST',
					data: {
						"groupId": groupId,
						"functions": funcs
					},
					success: function(rs){
						setTimeout(function(){ 
							loader.close();
							alertify.success("${uiLabelMap.BkEunivSaveSuccess}");
						}, 500);
					}
				})
			}
		</script>
	</div>
