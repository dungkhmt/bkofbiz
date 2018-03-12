<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resource/bkeuniv/css/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>

<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

JURY ${juryId}

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		Thanh vien
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="staffId" style="width: 100%" type="text" width="1000" onchange="changeStaff()">
			<#list resultMembers.members as m>
		 		<option value="${m.staffId}">${m.staffName}</option>
		 	</#list>
		</select> 
	</div>

<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateAssignment"/>

</div>
	


<div id="tbl-project-proposals">
</div>
<!--
<table>
<#list resultProposals.projectproposals as p>
	<tr>
	<td>
	<input type="checkbox" class="projectproposal" name="functions" value="${p.researchProjectProposalId}"/>${p.researchProjectProposalName}<br>
	
	</td>
	</tr>
</#list>
</table>
-->

<script>

function changeStaff(){
	var staffId = document.getElementById("staffId").value;
	$.ajax({
			url: "/bkeuniv/control/get-list-project-proposals-assigned-for-review",
			type: 'POST',
			data: {
				"juryId": ${juryId},
				"staffId": staffId
			},
			success: function(rs){
				console.log(rs.proposals);
				var html = '';
				for(i = 0; i < rs.proposals.length; i++){
					html += '<input type=' + '"' + 'checkbox' + '"' + 
					'class=' + '"' + 'projectproposal' + '"' + 'name=' + '"' + 'functions' + '"'
					+ 'value=' + '"' + rs.proposals[i].id + '"';// 
					if(rs.proposals[i].checked === 1) html += ' checked';
					html += '/>' + rs.proposals[i].name + '<br>';
				}
				document.getElementById("tbl-project-proposals").innerHTML = html;
			}
	})
}

function updateAssignment(){
	var staffId = document.getElementById("staffId").value;
	var checked_projectproposal = Array.from($('.projectproposal').map(function(index) {
		return {
			name: this.value,
			checked: this.checked
		}
	})).filter(function(el) {
		return el.checked
	})

	var unchecked_projectproposal = Array.from($('.projectproposal').map(function(index) {
		return {
			name: this.value,
			checked: this.checked
		}
	})).filter(function(el) {
		return !el.checked
	})
		
	//console.log('checked = ' + checked_projectproposal);
	//console.log('unchecked = ' + checked_projectproposal);
	
	var sel_proposalIds = "";
	if(checked_projectproposal.length === 0) sel_proposalIds = "";
	else{
		sel_proposalIds = checked_projectproposal[0].name;
		for(i = 1; i < checked_projectproposal.length; i++){
			sel_proposalIds = sel_proposalIds + ',' + checked_projectproposal[i].name;
		}
	}
	
	var unsel_proposalIds = "";
	if(unchecked_projectproposal.length === 0) unsel_proposalIds = "";
	else{
		unsel_proposalIds = unchecked_projectproposal[0].name;
		for(i = 1; i < unchecked_projectproposal.length; i++){
			unsel_proposalIds = unsel_proposalIds + ',' + unchecked_projectproposal[i].name;
		}
	}
	
	console.log('SELETCED = ' + sel_proposalIds);
	console.log('NOT SELETCED = ' + unsel_proposalIds);

	
	$.ajax({
			url: "/bkeuniv/control/store-reviewer-project-proposal-assignment",
			type: 'POST',
			data: {
				"juryId": ${juryId},
				"staffId": staffId,
				"sel_proposalIds": sel_proposalIds,
				"unsel_proposalIds": unsel_proposalIds
			},
			success: function(rs){
				//window.location.href="/bkeuniv/control/enable-security-group-function";
			}
	})
	
}

</script>