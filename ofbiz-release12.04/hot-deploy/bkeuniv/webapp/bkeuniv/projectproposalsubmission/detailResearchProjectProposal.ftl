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
				<td><a href="/bkeuniv/control/detail-evaluation-project-proposal?researchProjectProposalId=${researchProjectProposalId}">Xem danh gia chi tiet</a></td>
			</tr>
			<tr>
				<td><a href = "/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId=${researchProjectProposalId}">Download thuyet minh</a></td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	<tr>
		<td>		
		<div class="info-box">
			Ten de tai: ${resultProjectProposal.projectproposal.researchProjectProposalName}
		</div>
		</td>
		
	</tr>
	<tr>
		<td>		
		<div class="info-box">
			Tinh trang: ${resultProjectProposal.projectproposal.statusName}
		</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="info-box">
			Danh sach thanh vien cua de tai
			<table border=1>
				<thead>
					<td>Ho va ten</td>
					<td>Vai tro</td>
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
			Noi dung cong viec
			<table border = 1>
				<thead>
					<td width="200">Ho va ten</td>
					<td width="400">Noi dung</td>
					<td width="100">So ngay cong</td>
					<td width="100">Kinh phi</td>
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
			San pham
			<table border=1>
				<thead>
					<td>Loai san pham</td>
					<td>So luong</td>
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
