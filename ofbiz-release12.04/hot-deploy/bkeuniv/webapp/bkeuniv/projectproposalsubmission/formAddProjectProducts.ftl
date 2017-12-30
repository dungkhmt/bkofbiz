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

<style>
#form-add-workpackage-project {
    margin-top: 60px;
    margin-left: 100px;
}
</style>


<div id="form-add-workpackage-project">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
			projectId 
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="researchProjectProposalId" style="width: 100%" type="text" width="1000" value="${projectProposalId}" disabled/>
	</div>
	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		product type id
	</div>
	<div style="display: inline-block;width: 69%;">
		<select id="researchProductTypeId" style="width: 100%" type="text" width="1000">
		 	<#list resultProductTypes.projectProposalProductTypes as pt>
		 		<option value="${pt.researchProductTypeId}">${pt.researchProductTypeName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 30%;">
		quantity
	</div>
	<div style="display: inline-block;width: 69%;">
		<input id="quantity" style="width: 100%" type="text" width="1000" value="quantity"/>
	</div>
</div>



<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addProjectProposalProduct"/>
</div>


<script>


function addProjectProposalProduct(){
	var researchProjectProposalId = document.getElementById("researchProjectProposalId").value;
	var quantity = document.getElementById("quantity").value;
	var researchProductTypeId = document.getElementById("researchProductTypeId").value;
	
	$.ajax({
			url: "/bkeuniv/control/add-a-product-project-proposal",
			type: 'POST',
			data: {
				"researchProjectProposalId": researchProjectProposalId,
				"researchProductTypeId": researchProductTypeId,
				"quantity": quantity
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/products-of-project-proposals?researchProjectProposalId=" + researchProjectProposalId;
				console.log(rs.result);
			}
		})
		

}
</script>