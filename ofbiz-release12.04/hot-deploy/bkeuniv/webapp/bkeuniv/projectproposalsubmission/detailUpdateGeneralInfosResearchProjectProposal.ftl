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

<#assign prj = resultProjectProposal.projectproposal/>
<#assign note=""/>
<#if prj.note?exists>
	<#assign note=prj.note/>
</#if>
<#assign materialBudget=0/>
<#if prj.materialBudget?exists>
	<#assign materialBudget=prj.materialBudget>
</#if>
<#assign externalServiceBudget=0/>
<#if prj.externalServiceBudget?exists>
	<#assign externalServiceBudget=prj.externalServiceBudget>
</#if>
<#assign domesticConferenceBudget=0/>
<#if prj.domesticConferenceBudget?exists>
	<#assign domesticConferenceBudget=prj.domesticConferenceBudget>
</#if>
<#assign internationalConferenceBudget=0/>
<#if prj.internationalConferenceBudget?exists>
	<#assign internationalConferenceBudget=prj.internationalConferenceBudget>
</#if>
<#assign publicationBudget=0/>
<#if prj.publicationBudget?exists>
	<#assign publicationBudget=prj.publicationBudget>
</#if>
<#assign managementBudget=0/>
<#if prj.managementBudget?exists>
	<#assign managementBudget=prj.managementBudget>
</#if>

<#assign projectName=""/>
<#if prj.researchProjectProposalName?exists>
	<#assign projectName=prj.researchProjectProposalName>
</#if>

<#assign facultyId=""/>
<#if prj.facultyId?exists>
	<#assign facultyId=prj.facultyId>
</#if>

<div id="form-add">
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
			${uiLabel.ProjectProposalName}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="projectProposalName" class="form-control" style="width: 100%" type="text" value="${projectName}"/>
	</div>	
</div>
<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.MaterialBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="material-budget" class="form-control" style="width: 100%" type="text" value="${materialBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>


<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.ExternalServiceBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="external-service-budget" class="form-control" style="width: 100%" type="text" value="${externalServiceBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.DomesticConferenceBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="domestic-conference-budget" class="form-control" style="width: 100%" type="text" value="${domesticConferenceBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.InternationalConferenceBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="international-conference-budget" class="form-control" style="width: 100%" type="text" value="${internationalConferenceBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.PublicationBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="publication-budget" class="form-control" style="width: 100%" type="text" value="${publicationBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.ManagementBudget}
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="management-budget" class="form-control" style="width: 100%" type="text" value="${managementBudget}" pattern="[0-9]{0,19}" title="Nhap so nguyen duong khong qua 20 chu so"/>
	</div>	
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		${uiLabel.Faculty}
	</div>
	<div style="display: inline-block;width: 100%;">
		<select id="facultyId" class="form-control" style="width: 100%" type="text">
		 	<#list resultFaculties.faculties as f>
		 		<#if f.facultyId == facultyId>
		 			<option value="${f.facultyId}" selected>${f.facultyName}</option>
		 		<#else>
		 			<option value="${f.facultyId}">${f.facultyName}</option>
		 		</#if>
		 	</#list>
		</select> 
		
	</div>
</div>

<div class="inline-box" style="width: 50%; padding: 10px 0px;">
	<div style="display: inline-block;width: 100%;">
		Ghi chu
	</div>
	<div style="display: inline-block;width: 100%;">
		<input id="note" class="form-control" style="width: 100%" type="text" value="${note}"/>
		
	</div>
</div>

<@buttonStore text="${bkEunivUiLabelMap.BkEunivStore}" action="updateProjectProposal"/>

</div>

<script>
function updateProjectProposal(){
	var facultyId = document.getElementById("facultyId").value;
	//var projectCallId =  document.getElementById("projectCallId").value;
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
		materialbudget = document.getElementById("material-budget").value;
	}else{
		alert("Kinh phi nhap chua dung dinh dang");
		return;
	}
	if(document.getElementById("external-service-budget").checkValidity()){
		externalServiceBudget = document.getElementById("external-service-budget").value;
	}else{
		alert("Kinh phi thue dich vuj ngoai nhap chua dung dinh dang");
		return;
	}
	if(document.getElementById("domestic-conference-budget").checkValidity()){
		domesticConferenceBudget = document.getElementById("domestic-conference-budget").value;
	}else{
		alert("Kinh phi hoi nghi trong nuoc nhap chua dung dinh dang");
		return;
	}
	if(document.getElementById("international-conference-budget").checkValidity()){
		internationalConferenceBudget = document.getElementById("international-conference-budget").value;
	}else{
		alert("Kinh phi hoi nghi nuoc ngoai nhap chua dung dinh dang");
		return;
	}
	if(document.getElementById("publication-budget").checkValidity()){
		publicationBudget = document.getElementById("publication-budget").value;
	}else{
		alert("Kinh phi cong bo bai bao nhap chua dung dinh dang");
		return;
	}
	if(document.getElementById("management-budget").checkValidity()){
		managementBudget = document.getElementById("management-budget").value;
	}else{
		alert("Kinh phi quan ly nhap chua dung dinh dang");
		return;
	}

	var totalBudget = 0;
	totalBudget = materialbudget + externalServiceBudget + domesticConferenceBudget
	+ internationalConferenceBudget + publicationBudget + managementBudget;
	
	alert("total budget = " + totalBudget);
	
	if(materialbudget > 0.7*totalBudget)
		alert("Kinh phi vat tu nguyen lieu phai nho hon 70% tong kinh phi");
	if(externalServiceBudget > 0.7*totalBudget)
		alert("Kinh phi thue dich vu ngoai phai nho hon 70% tong kinh phi");
	if(domesticConferenceBudget > 0.3*totalBudget)
		alert("Kinh phi di hoi thao trong nuoc phai nho hon 30% tong kinh phi");
	if(internationalConferenceBudget > 0.5*totalBudget)
		alert("Kinh phi tham du hoi thao quoc te phai nho hon 50% tong kinh phi");				
	if(publicationBudget > 0.2*totalBudget)
		alert("Kinh phi xuat bai bai bao phai nho hon 20% tong kinh phi");
	
	//alert('addProposal facultyId = ' + facultyId + ', and projecCallId = ' + projectCallId);
	
		$.ajax({
			url: "/bkeuniv/control/update-general-infos-a-project-proposal",
			type: 'POST',
			data: {
				"researchProjectProposalId": ${resultProjectProposal.projectproposal.researchProjectProposalId},
				"facultyId": facultyId,
				"projectProposalName": projectProposalName,
				//"projectCallId": projectCallId,
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