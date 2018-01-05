<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>

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
  
<@Loader handleToggle="loader">
	<@IconSpinner/>
</@Loader>

<script>
  	loader.open();
  </script>

<a href="/bkeuniv/control/form-add-project-proposal-jury">Them moi hoi dong duyet thuyet minh de tai</a>
<div id="table-list" style="overflow-y: auto; padding: 2em;">
	<table id="list" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
				<th>Ten hoi dong duyet thuyet minh de tai</th>
				<th>Khoa/vien</th>
				<th>Dot goi de tai</th>
			</tr>
		</thead>
	<tbody>
	<#list resultJuries.projectproposaljuries as J>
		<tr>
			<td style="display: none">${J.juryId}</td>
			<#if J.juryName?exists>
				<td>${J.juryName}</td>
			<#else>
				<td></td>
			</#if>
			<#if J.facultyName?exists>
				<td>${J.facultyName}</td>
			<#else>
				<td></td>
			</#if>
			<#if J.projectCallName?exists>
				<td>${J.projectCallName}</td>
			<#else>
				<td></td>
			</#if>
			
			
		</tr>
	</#list>
	</tbody>
	</table>
	
</div>
<script>
var obj;

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
      {title: "Edit", cmd: "edit"},
      {title: "Thanh vien hoi dong", cmd: "members"},
      {title: "Phan cong phan bien thuyet minh", cmd: "asignreviewer"},
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
			case "members":
				obj = ui;
				var el = ui.target.parent();
				var juryId = el.children()[0].innerHTML;
				window.location.href="/bkeuniv/control/research-project-jury-members?juryId=" + juryId;
				//alert('member of jury ' + juryId);
			    break;
			case "asignreviewer":
				obj = ui;
				var el = ui.target.parent();
				var juryId = el.children()[0].innerHTML;
				window.location.href="/bkeuniv/control/research-project-jury-assgin-reviewer?juryId=" + juryId;
				//alert('member of jury ' + juryId);
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