<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">


  <head>
    <!-- Bootstrap Core CSS -->
    <link href="/resource/bkeuniv/bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resource/bkeuniv/bootstrap/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resource/bkeuniv/bootstrap/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resource/bkeuniv/bootstrap/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


    <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/south-street/jquery-ui.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">

    
    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    
    
    <script src="https://cdn.jsdelivr.net/jquery.ui-contextmenu/1.7.0/jquery.ui-contextmenu.min.js"></script>
    
    <script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
    <link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
    
    <meta charset="utf-8" />
    
    <title>DataTables - Context menu integration</title>
  
  </head>


<style>
#form-add {
    margin-top: 20px;
    margin-left: 20px;
    width: 100%;
    overflow: scroll
}
</style>

<div id="form-add">

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
			
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="projectCallId" type="hidden" value="${parameters.projectCallId}"/>
	</div>
	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
			${uiLabel.ProjectProposalName}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="projectProposalName" class="form-control" style="width: 100%" type="text" value=""/>
	</div>	
</div>
<!--
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
			${uiLabel.Budget}
			
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>
-->
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.MaterialBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="material-budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.ExternalServiceBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="external-service-budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.DomesticConferenceBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="domestic-conference-budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.InternationalConferenceBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="international-conference-budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.PublicationBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="publication-budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.ManagementBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="management-budget" class="form-control" style="width: 100%" type="text" value="0" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.Faculty}
	</div>
	<div style="display: inline-block;width: 100%;">
		<select id="facultyId" class="form-control" style="width: 100%" type="text">
		 	<#list resultFaculties.faculties as f>
		 		<option value="${f.facultyId}">${f.facultyName}</option>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		Ghi chu
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="note" class="form-control" style="width: 100%" type="text" value=""/>
		
	</div>
</div>

<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="addProjectProposal"/>

</div>

<script>
function addProjectProposal(){
	var facultyId = document.getElementById("facultyId").value;
	var projectCallId =  document.getElementById("projectCallId").value;
	var projectProposalName = document.getElementById("projectProposalName").value;
	var note = document.getElementById("note").value;
	/*
	var budget = 0;
	if(document.getElementById("budget").checkValidity()){
		budget = document.getElementById("budget").value;
	}else{
		alert("Kinh phi nhap chua dung dinh dang");
		return;
	}
	*/
	var materialbudget = 0;
	var externalServiceBudget = 0;
	var domesticConferenceBudget = 0;
	var internationalConferenceBudget = 0;
	var publicationBudget = 0;
	var managementBudget = 0;
	if(document.getElementById("material-budget").checkValidity()){
		materialbudget = Number(document.getElementById("material-budget").value);
	}else{
		//alert("Kinh phi nhap chua dung dinh dang");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectMaterialBudgetInvalidFormat)}');
		return;
	}
	if(document.getElementById("external-service-budget").checkValidity()){
		externalServiceBudget = Number(document.getElementById("external-service-budget").value);
	}else{
		//alert("Kinh phi thue dich vuj ngoai nhap chua dung dinh dang");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectExternalServiceBudgetInvalidFormat)}');
		return;
	}
	if(document.getElementById("domestic-conference-budget").checkValidity()){
		domesticConferenceBudget = Number(document.getElementById("domestic-conference-budget").value);
	}else{
		//alert("Kinh phi hoi nghi trong nuoc nhap chua dung dinh dang");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectDomesticConferenceBudgetInvalidFormat)}');
		return;
	}
	if(document.getElementById("international-conference-budget").checkValidity()){
		internationalConferenceBudget = Number(document.getElementById("international-conference-budget").value);
	}else{
		//alert("Kinh phi hoi nghi nuoc ngoai nhap chua dung dinh dang");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectInternationalConferenceBudgetInvalidFormat)}');
		return;
	}
	if(document.getElementById("publication-budget").checkValidity()){
		publicationBudget = Number(document.getElementById("publication-budget").value);
	}else{
		//alert("Kinh phi cong bo bai bao nhap chua dung dinh dang");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectInternationalConferenceBudgetInvalidFormat)}');
		return;
	}
	if(document.getElementById("management-budget").checkValidity()){
		managementBudget = Number(document.getElementById("management-budget").value);
	}else{
		//alert("Kinh phi quan ly nhap chua dung dinh dang");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectManagementBudgetInvalidFormat)}');
		return;
	}

	var totalBudget = 0;
	totalBudget = materialbudget + externalServiceBudget + domesticConferenceBudget
	+ internationalConferenceBudget + publicationBudget + managementBudget;
	//alert("total budget  " + totalBudget);
	
	if(materialbudget > 0.7*totalBudget){
		//alert("Kinh phi vat tu nguyen lieu phai nho hon 70% tong kinh phi");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectMaterialBudgetInvalid)}');
		return;
	}
	if(externalServiceBudget > 0.7*totalBudget){
		//alert("Kinh phi thue dich vu ngoai phai nho hon 70% tong kinh phi");
	    alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectExternalServiceBudgetInvalid)}');
		return;
	}
	if(domesticConferenceBudget > 0.3*totalBudget){
		//alert("Kinh phi di hoi thao trong nuoc phai nho hon 30% tong kinh phi");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectDomesticConferenceBudgetInvalid)}');
		return;	
	}	
	if(internationalConferenceBudget > 0.5*totalBudget){
		//alert("Kinh phi tham du hoi thao quoc te phai nho hon 50% tong kinh phi");	
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectInternationalConferenceBudgetInvalid)}');
		return;
	}				
	if(publicationBudget > 0.2*totalBudget){
		//alert("Kinh phi xuat bai bai bao phai nho hon 20% tong kinh phi");
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectPublicationBudgetInvalid)}');
		return;		
	}
	if(managementBudget > 0.025*totalBudget){
		alertify.alert('${StringUtil.wrapString(uiLabelMap.ProjectManagementBudgetInvalid)}');
		return;		
	}
											
	//alert('addProposal facultyId = ' + facultyId + ', and projecCallId = ' + projectCallId);
	
		$.ajax({
			url: "/bkeuniv/control/add-a-project-proposal",
			type: 'POST',
			data: {
				"facultyId": facultyId,
				"projectProposalName": projectProposalName,
				"projectCallId": projectCallId,
				"materialbudget": materialbudget,
				"externalServiceBudget": externalServiceBudget,
				"domesticConferenceBudget": domesticConferenceBudget,
				"internationalConferenceBudget": internationalConferenceBudget,
				"publicationBudget": publicationBudget,
				"managementBudget": managementBudget,
				"note": note
			},
			success: function(rs){
				window.location.href = "/bkeuniv/control/my-project-proposal";
				console.log(rs.result);
			}
		})

}

</script>