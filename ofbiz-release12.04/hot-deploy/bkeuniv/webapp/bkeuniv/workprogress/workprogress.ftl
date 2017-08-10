<head>
	<!-- import DataTable -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	
	<!-- import css component -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/workprogress.css">
	
	<!-- import js component -->
	<script src="/resource/bkeuniv/js/workprogress.js"></script>
	<script>
	var workProgressId='${workProgressUiLabelMap.BkEunivWorkProgressId}';
	var staffId='${workProgressUiLabelMap.BkEunivStaffId}';
	var period='${workProgressUiLabelMap.BkEunivPeriod}';
	var position='${workProgressUiLabelMap.BkEunivPosition}';
	var specialization='${workProgressUiLabelMap.BkEunivSpecialization}';
	var institution='${workProgressUiLabelMap.BkEunivInstitution}';
	var titleNewWorkProgress='${workProgressUiLabelMap.BkEunivTitleNewWorkProgress}';
	var titleEditWorkProgress='${workProgressUiLabelMap.BkEunivTitleEditWorkProgress}';
	var titleDeleteWorkProgress='${workProgressUiLabelMap.BkEunivTitleDeleteWorkProgress}';
	var add='${uiLabelMap.BkEunivAdd}';
	var edit='${uiLabelMap.BkEunivEdit}';
	var remove='${uiLabelMap.BkEunivRemove}';
	var update='${uiLabelMap.BkEunivUpdate}';
</script>
</head>
<body>
<div class="body">

	<div class="work-progress">
		<div class="title">
			<a href="#" class="title-hyperlink">
				${uiLabelMap.BkEunivWorkProgress}
			</a>
		</div>
		<div id="button-add-work-progress" onClick="newWorkProgress()">
			${uiLabelMap.BkEunivAdd}
		</div>
		
		<table id="table-work-progress" class="table table-striped table-bordered">
			<thead>
				<td>${workProgressUiLabelMap.BkEunivWorkProgressId}</td>
				<td>${workProgressUiLabelMap.BkEunivStaffId}</td>
				<td>${workProgressUiLabelMap.BkEunivPeriod}</td>
				<td>${workProgressUiLabelMap.BkEunivPosition}</td>
				<td>${workProgressUiLabelMap.BkEunivSpecialization}</td>
				<td>${workProgressUiLabelMap.BkEunivInstitution}</td>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
<div class="loader hidden-loading"></div>
<div id="add-work-progress"></div>
<div id="change-work-progress"></div>
