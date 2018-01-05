<style>
	.info-box{
		padding:15px
	}
</style>


<table>
	<tr>
		<td>${researchProjectProposalId}</td>
	</tr>
	
	<tr>
		<td><a href="/bkeuniv/control/detail-evaluation-project-proposal?researchProjectProposalId=${researchProjectProposalId}">
		Xem danh gia chi tiet</a>${researchProjectProposalId}</td>
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