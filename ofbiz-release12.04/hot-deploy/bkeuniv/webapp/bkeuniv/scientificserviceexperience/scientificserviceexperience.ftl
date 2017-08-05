<head>
	<!-- import DataTable -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	
	
	
	<!-- import css component -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/ssEmanagement.css">
		

</head>
<body>
<script>
var SID='${sseLabels.BkEunivSSESID}';
var Description='${sseLabels.BkEunivSSESDescription}';
var Quantity='${sseLabels.BkEunivSSESQuantity}';
</script>
<script src="/resource/bkeuniv/js/ssEManagement.js"></script>
<div class="body">
<div class="scientificserviceexperience-management">
		<div class="title">
			<a href="#" class="title-hyperlink">
				${sseLabels.BkEunivEMSATA}
			</a>
		</div>
		<div id="button-add-scientificserviceexperience" onClick="newScientificServiceExperience()">
			${sseLabels.BkEunivAdd}
		</div>
		
		<table id="table-scientificserviceexperience-management" class="table table-striped table-bordered">
			<thead>
				<td>ID</td>
				<td>${sseLabels.BkEunivSSESID}</td>
				<td>${sseLabels.BkEunivSSESDescription}</td>
				<td>${sseLabels.BkEunivSSESQuantity}</td>
				
			</thead>
			<tbody>
			</tbody>
		</table>
	</div></div>
<div id="add-scientificserviceexperience"></div>
<div id="change-scientificserviceexperience"></div>