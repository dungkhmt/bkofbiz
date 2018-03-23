<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<style>
	.info-table{
		width: 100%;
    }
	.info-box{
		padding:15px
	}
</style>

<div>
<input id="researchProjectProposalId" type="hidden" value="${researchProjectProposalId}"/>
<table>
	<tr>
		<td>
		<table>
			<tr>
				<td><@buttonStore text="Phe duyet" action="approve()"/></td>
				<td><@buttonStore text="Khong phe duyet" action="notApprove()"/></td>
				<td><@buttonStore text="Yeu cau chinh sua" action="revise()"/></td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>

</div>
<script>
function approve(){
	var researchProjectProposalId = document.getElementById("researchProjectProposalId").value;
	//alert('approve ' + researchProjectProposalId);
	$.ajax({
					url: "/bkeuniv/control/approve-project-proposal",
					type: 'POST',
					data: {
						"researchProjectProposalId": researchProjectProposalId
					},
					success:function(rs){
						console.log(rs);
						alert('Da phe duyet de tai ' + researchProjectProposalId);
					}
				})
}
function notApprove(){
	var researchProjectProposalId = document.getElementById("researchProjectProposalId").value;
	//alert('notApprove ' + researchProjectProposalId);
	$.ajax({
					url: "/bkeuniv/control/not-approve-project-proposal",
					type: 'POST',
					data: {
						"researchProjectProposalId": researchProjectProposalId
					},
					success:function(rs){
						console.log(rs);
						alert('Khong phe duyet de tai ' + researchProjectProposalId);
					}
				})

}
function revise(){
	var researchProjectProposalId = document.getElementById("researchProjectProposalId").value;
	//alert('revise ' + researchProjectProposalId);
	$.ajax({
					url: "/bkeuniv/control/accept-revise-project-proposal",
					type: 'POST',
					data: {
						"researchProjectProposalId": researchProjectProposalId
					},
					success:function(rs){
						console.log(rs);
						alert('Yeu cau chinh sua de tai ' + researchProjectProposalId);
					}
				})

}
</script>