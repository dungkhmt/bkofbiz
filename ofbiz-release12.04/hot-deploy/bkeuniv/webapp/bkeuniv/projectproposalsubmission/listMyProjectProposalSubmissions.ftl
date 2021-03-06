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
    width: 100%;
    overflow: scroll;
}
</style>



  
<@Loader handleToggle="loader">
	<@IconSpinner/>
</@Loader>

<script>
  	loader.open();
  </script>

<div id="form-add">
<!--
<@buttonStore text="Them moi" action="addProjectProposal()"/>
-->

<div id="table-list" style="overflow-y: auto; padding: 2em;">
	<table id="list" cellspacing="0" width="100%" class="display dataTable">
		<thead>
			<tr>
				<th style="display: none"></th>
				<th>${uiLabel.ProjectProposalName}</th>
				<th>${uiLabel.ProjectDirector}</th>
				<th>${uiLabel.ProjectCallName}</th>
				<th>${uiLabel.Faculty}</th>
				<th>${uiLabel.Status}</th>
			</tr>
		</thead>
	<tbody>
	<#list resultProjectProposals.projectproposals as pp>
		<#assign p = pp.project/>
		
		<tr>
			<td style="display: none">${p.researchProjectProposalId}</td>
			<#if p.researchProjectProposalName?exists>
				<#if pp.role == "DIRECTOR">
					<td><a href="/bkeuniv/control/detail-research-project-proposal-update?researchProjectProposalId=${p.researchProjectProposalId}">${p.researchProjectProposalName}</a></td>
				<#else>
					<td><a href="/bkeuniv/control/detail-research-project-proposal?researchProjectProposalId=${p.researchProjectProposalId}">${p.researchProjectProposalName}</a></td>
				</#if>
				
			<#else>
				<td></td>
			</#if>
			<#if p.createStaffName?exists>
				<td>${p.createStaffName}</td>
			<#else>
				<td></td>
			</#if>
			<#if p.projectCallName?exists>
				<td>${p.projectCallName}</td>
			<#else>
				<td></td>
			</#if>
			<#if p.facultyName?exists>
				<td>${p.facultyName}</td>
			<#else>
				<td></td>
			</#if>
			
			<#if p.statusName?exists>
				<td>${p.statusName}</td>
			<#else>
				<td></td>
			</#if>
			
		</tr>
	</#list>
	</tbody>
	</table>
	
</div>

</div>

<script>
var obj;

function addProjectProposal(){
	alert("add a project proposal");
}

function deleteProposal(researchProjectProposalId){
			$.ajax({
					url: "/bkeuniv/control/delete-a-project-proposal",
					type: 'POST',
					data: {
						"researchProjectProposalId": researchProjectProposalId
					},
					success:function(rs){
						console.log(rs);
						window.location.href="/bkeuniv/control/my-project-proposal";
					}
				})
	
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
      {title: "Delete", cmd: "delete"}
    ],
    select: function(event, ui) {
        switch(ui.cmd){
            case "delete":
				obj = ui;
				var el = ui.target.parent();
				var proposalId = el.children()[0].innerHTML;
				deleteProposal(proposalId);
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