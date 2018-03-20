<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:15px
	}
</style>

<div class="info-table">
<table>
	<tr>
		<td>
		<table>
			<tr>
				<td><a href = "/bkeuniv/control/members-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">Thanh vien de tai</a></td>
				<td><a href = "/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">Noi dung cong viec</a></td>
				<td><a href = "/bkeuniv/control/products-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">San pham de tai</a></td>
				<td><a href = "/bkeuniv/control/download-file-research-project-proposal?researchProjectProposalId=${researchProjectProposalId}">Download thuyet minh</a></td>
				<td><a href="/bkeuniv/control/detail-evaluation-project-proposal?researchProjectProposalId=${researchProjectProposalId}">Xem danh gia chi tiet</a></td>
				<td><@buttonStore text="Upload thuyet minh" action="uploadFileProposal()"/></td>
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
<input class="input" id="upload" style="display:none" type="file" onChange="uploadFile(event)">
</div>

<script>
	 
	function uploadFileProposal(){
		//alert('upload file thuyet minh');
		document.getElementById("upload").click();
	}
	function uploadFile(e){
		var file = e.target.files || e.dataTransfer.files;
		
		var reader = new FileReader();
		if(e.target.files.length !== 0) {
			test = e
			var formData = new FormData();
			formData.append("researchProjectProposalId", ${researchProjectProposalId});
			formData.append("file", e.target.files[0]);
			
			$.ajax({
					url: "/bkeuniv/control/upload-file-research-project-proposal",
					type: 'POST',
					method: 'POST',
					contentType: false,
    				processData: false,
					data: formData,
					success:function(rs){
						console.log(rs);
					}
				})
		}
	}
	
</script>