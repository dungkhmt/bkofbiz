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

<div>
<table>
	<tr>
		<td>
		<table>
			<#if pResultProjectProposal.projectCallStatusId == "OPEN" || pResultProjectProposal.projectCallStatusId == "OPEN_REVISE">
			<tr>
				<td><a href = "/bkeuniv/control/members-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">Thanh vien de tai</a></td>
				<td><a href = "/bkeuniv/control/workpackages-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">Noi dung cong viec</a></td>
				<td><a href = "/bkeuniv/control/products-of-project-proposals?researchProjectProposalId=${researchProjectProposalId}">San pham de tai</a></td>
				<!--
				<td><a href="/bkeuniv/control/detail-evaluation-project-proposal?researchProjectProposalId=${researchProjectProposalId}">Xem danh gia chi tiet</a></td>
				-->
				<td><@buttonStore text="Upload thuyet minh" action="uploadFileProposal()"/></td>
			</tr>
			</#if>
			
		</table>
		
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