<#include "component://bkeuniv/webapp/bkeuniv/layout/jqServerSide.ftl"/>
<body>

<script>
	function getDataFilter() {
		last_group_id = $("#security-group-function").val();
		return {
			"field": "groupId",
			"value": $("#security-group-function").val(),
			"operation": "CONTAINS"
		}
	}

	var userChange=[];
	var last_group_id;

	function ClickCheckboxF(that) {
		var data = jqDataTable.table.row($(that).parent()).data();
		
		var d = userChange.find(function(user) {
			return user.staffId==data.staffId;
		})

		if(!d) {
			d = data;
			userChange.push(d);
		}

		if (that.checked) {  //update the cell data with the checkbox state
			d.groupId_t = $("#security-group-function").val();
		} else {
			d.groupId_t = undefined;
		}

	}

	function updatePermission(){
		var groupId = $("#security-group-function").val();
		var insert = (userChange.filter(function(u) {
			return !u.groupId&&!!u.groupId_t&&u.groupId_t==groupId;
		})||[]).map(function(u) {
			return u.staffId
		});

		var remove = (userChange.filter(function(u) {
			return !!u.groupId&&!u.groupId_t&&u.groupId_t!=groupId
		})||[]).map(function(u) {
			return u.staffId
		});

		if(insert.length == 0 && remove.length == 0) {
			alertify.success("${uiLabelMap.BkEunivNoChange}");
			return;
		}

		console.log(insert, remove)
		
		$.ajax({
			url: "/bkeuniv/control/store-security-group-users",
			type: 'POST',
			data: {
				"groupId": groupId,
				"staffsInsert": insert.join(", "),
				"staffsRemove": remove.join(", ")
			},
			success: function(rs){
				alertify.success("${uiLabelMap.BkEunivSaveSuccess}");
				userChange=[];
				refresh();
			}
		})

	}
	var test;
	function refresh(that) {
		test = that;
		var change = userChange.filter(function(u) {
			return u.groupId_t != u.groupId
		})||[];

		if(change.length > 0) {
			alertify.confirm("${uiLabelMap.BkEunivConfirmChange}", "B&#x1EA1;n hi&#x1EC7;n &#x111;ang c&#xF3; " + change.length + " thay &#x111;&#x1ED5;i ch&#x1B0;a &#x111;&#x1B0;&#x1EE3;c l&#x1B0;u l&#x1EA1;i",
				function(){
					JqRefresh();
					userChange=[];
				},
				function(){
					if(!!last_group_id) {
						document.getElementById("security-group-function").value = last_group_id;
					}
				}
			);
		} else {
			JqRefresh();
			userChange=[];
		}	
	}
</script>

<div class="body">
	
	<#assign columns=[
		{
			"name": "groupId"?j_string,
			"data": "groupId"
		},
		{
			"name": "description"?j_string,
			"data": "description"
		},
		{
			"name": "status"?j_string,
			"data": "status",
			"width": "80px"
		}
		
	] />
	
	
	
	<#assign fields=[
		"groupId",
		"description",
		"status"
	] />
	
	<#assign sizeTable="$(window).innerHeight() - $(\".nav\").innerHeight() - $(\".footer\").innerHeight()" />

	<@jqDataTable
		id="jqDataTable"
		urlData="/bkeuniv/control/jqxGeneralServicer?sname=JQGetListSecurityGroups" 
		columns=columns 
		dataFields=fields
		sizeTable=sizeTable
		keysId=["groupId"]
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
		JqRefresh='refresh()'
	/>
</div>

<#--  

<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

<style>
#table-list-security-group {
    margin-top: 60px;
}


</style>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">




<div id="table-list-security-group">
	<@buttonAdd text="${bkEunivUiLabelMap.BkEunivAdd}" action="addNew"/>
	
	<table id="list-security-groups" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th>Ma nhom chuc nang</th>
				<th>Mo ta</th>
			<tr>
		</thead>
		<tbody>
		<#list result.securityGroups as sg>
			<tr>
				<td>${sg.groupId}</td>
				<td>${sg.description}</td>
			<tr>
		</#list>
		</tbody>
	</table>
</div>

<script>
function addNew() {
	//window.location.href="https://google.com"
	window.location.href="form-create-security-group"
}
$(document).ready(function() {
    $('#list-security-groups').DataTable();
} );
</script>  -->