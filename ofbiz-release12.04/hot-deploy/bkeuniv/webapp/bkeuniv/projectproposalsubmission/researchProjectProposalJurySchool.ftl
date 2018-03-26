<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

  <head>

    <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/south-street/jquery-ui.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    
    
    <script src="https://cdn.jsdelivr.net/jquery.ui-contextmenu/1.7.0/jquery.ui-contextmenu.min.js"></script>
    
    <meta charset="utf-8" />
    
    <title>DataTables - Context menu integration</title>
  
  </head>
  
 <style>
#form-add {
    margin-top: 20px;
    margin-left: 20px;
}
</style>



  
<@Loader handleToggle="loader">
	<@IconSpinner/>
</@Loader>

<script>
  	loader.open();
  </script>

<div id="form-add">
<@buttonStore text="Them moi" action="addProjectCall()"/>


<div id="table-list" style="overflow-y: auto; padding: 2em;">
	<table id="list" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
				<th>${uiLabel.ProjectCallName}</th>
				<th>${uiLabel.Category}</th>
				<th>${uiLabel.Year}</th>
				<th>${uiLabel.Status}</th>
				<th></th>
			</tr>
		</thead>
	<tbody>
	<#list resultProjectCalls.projectCalls as pc>
		<tr>
			<td style="display: none">${pc.projectCallName}</td>
			<#if pc.projectCallName?exists>
				<td>${pc.projectCallName}</td>
			<#else>
				<td></td>
			</#if>
			<#if pc.projectCategoryName?exists>
				<td>${pc.projectCategoryName}</td>
			<#else>
				<td></td>
			</#if>
			<#if pc.year?exists>
				<td>${pc.year}</td>
			<#else>
				<td></td>
			</#if>
			<#if pc.statusName?exists>
				<td>${pc.statusName}</td>
			<#else>
				<td></td>
			</#if>
			<#if pc.juryId?exists>
				<td><a href="/bkeuniv/control/detail-jury-proposal?juryId=${pc.juryId}">${pc.juryName}</a></td>
			<#else>
				<td><a href="/bkeuniv/control/form-add-project-proposal-jury?projectCallId=${pc.projectCallId}">${uiLabel.CreateJury}</a></td>
			</#if>
			
		</tr>
	</#list>
	</tbody>
	</table>
	
</div>

</div>

<script>
var obj;

function addProjectCall(){
	alert("them dot goi de tai");
}

$(document).ready(function() {
  loader.close();
  var oTable = $('#list').dataTable({
    "bJQueryUI": true,
    "sDom": 'l<"H"Rf>t<"F"ip>'
  });
  $(document).contextmenu({
    delegate: ".dataTable td",
    menu: [
      {title: "Delete", cmd: "delete"},
      {title: "Edit", cmd: "edit"}
    ],
    select: function(event, ui) {
        switch(ui.cmd){
            case "delete":
                $(ui.target).parent().remove();
                break;
            case "edit":
				obj = ui;
				var el = ui.target.parent();
				var paperId = el.children()[0].innerHTML;
				alert('edit paper ' + paperId);
			    break;
        }
    },
    beforeOpen: function(event, ui) {
        var $menu = ui.menu,
            $target = ui.target
        ui.menu.zIndex(0);
    }
  });
    
} );
</script>
