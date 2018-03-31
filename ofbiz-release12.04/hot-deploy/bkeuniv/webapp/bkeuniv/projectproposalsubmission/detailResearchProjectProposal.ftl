<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<script src="/resource/bkeuniv/js/lib/dropify.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dropify.min.css">
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:15px
	}
	.modal-dialog {
		width: 60%!important;
		margin-left: 20%!important;
	}
</style>

<div class="info-table">
<table>
	<tr>
		<td>
		<table>
			<!--
			<#if resultProjectProposal.projectCallStatusId == "OPEN">
			<tr>
				<td><a href = "/bkeuniv/control/members-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">Thanh vien de tai</a></td>
				<td><a href = "/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">Noi dung cong viec</a></td>
				<td><a href = "/bkeuniv/control/products-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">San pham de tai</a></td>
				<td><@buttonStore text="Upload thuyet minh" action="uploadFileProposal()"/></td>
			</tr>
			</#if>
			-->
			<tr>
				<#assign evaluation = "NO">
				<#if resultProjectProposal.projectproposal.evaluationOpenFlag?exists>
				<#if resultProjectProposal.evaluation == "YES" && resultProjectProposal.projectproposal.evaluationOpenFlag == "Y">
					<#assign evaluation = "YES">
				</#if>
					
				</#if>
				<#if evaluation == "YES">
					<td><a href="/bkeuniv/control/detail-evaluation-project-proposal?researchProjectProposalId=${researchProjectProposalId}">${uiLabel.DetailEvaluation}</a></td>
				<#else>
					<td></td>
				</#if>
				
			</tr>
			<tr>
				<td><a href = "/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId=${researchProjectProposalId}">${uiLabel.DownloadProposal}</a></td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	<tr>
		<td>		
		<div class="info-box">
			${uiLabel.ProjectProposalName}: ${resultProjectProposal.projectproposal.researchProjectProposalName}
		</div>
		</td>
		
	</tr>
	<tr>
		<td>		
		<div class="info-box">
			${uiLabel.Status}: ${resultProjectProposal.projectproposal.statusName}
		</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="info-box">
			${uiLabel.MembersOfProject}
			<table border=1>
				<thead>
					<td>${uiLabel.FullName}</td>
					<td>${uiLabel.Role}</td>
				</thead>
			<#list resultMembers.members as m>
				<tr>
					<td>${m.staffName}</td>
					<td>${m.roleTypeName}</td>
				<tr>
			</#list>
			</table>
			</div>
		</td>
	</tr>

	
	<tr>
		<td>
			<div class="info-box">
			${uiLabel.ListWorkPackages}
			<table border = 1>
				<thead>
					<td width="200">${uiLabel.FullName}</td>
					<td width="400">${uiLabel.WorkPackage}</td>
					<td width="100">${uiLabel.Workingdays}</td>
					<td width="100">${uiLabel.Budget}</td>
				</thead>
			<#list resultContentItems.projectProposalContentItems as ci>
				<tr>
					<td width="200">${ci.staffName}</td>
					<td width="400">${ci.content}</td>
					<td width="100">${ci.workingDays}</td>
					<td width="100">${ci.budget}</td>
				</tr>
			</#list>
			</table>
			</div>
		</td>
	</tr>
	
	<tr>
		<td>
			<div class="info-box">
			${uiLabel.ListProducts}
			<table border=1>
				<thead>
					<td>${uiLabel.Products}</td>
					<td>${uiLabel.Quantity}</td>
				</thead>
			<#list resultProducts.projectProposalProducts as p>
				<tr>
					<td>${p.researchProductTypeName}</td>
					<td>${p.quantity}</td>
					
				</tr>
			</#list>
			</div>
		</td>
	</tr>
</table>
