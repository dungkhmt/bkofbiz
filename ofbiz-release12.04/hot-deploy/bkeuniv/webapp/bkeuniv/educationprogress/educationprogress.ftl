<head>
	<!-- import DataTable -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	
	<!-- import css component -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/education-progress.css">

</head>
<script>

var remove = '${uiLabelMap.BkEunivRemove}';
var edit = '${uiLabelMap.BkEunivEdit}';

var educationProgressId = '${educationProgressUiLabelMap.BkEunivEducationProgressId?js_string}';
var educationType = '${educationProgressUiLabelMap.BkEunivEducationType}';
var institution = '${educationProgressUiLabelMap.BkEunivInstitution}';
var speciality = '${educationProgressUiLabelMap.BkEunivSpeciality}';
var graduateDate = '${educationProgressUiLabelMap.BkEunivGraduateDate}';
var staffId = '${educationProgressUiLabelMap.BkEunivStaffId}';
var titleEditEducationProgress = '${educationProgressUiLabelMap.BkEunivTitleEditEducationProgress}';
var add = '${uiLabelMap.BkEunivAddRow}';
var update = '${uiLabelMap.BkEunivUpdate}';
var titleNewEducationProgress = '${educationProgressUiLabelMap.BkEunivTitleNewEducationProgress}';

</script>
<!-- import js component -->
<script src="/resource/bkeuniv/js/education-progress.js"></script>
<body>
<div class="body">
	<div class="education-progress">
		<div class="title">
			<a href="#" class="title-hyperlink">
				${uiLabelMap.BkEunivEducationProgress}
			</a>
		</div>
		<div id="button-add-education-progress" onClick="newEducationProgress()">
			${uiLabelMap.BkEunivAdd}
		</div>
		
		<table id="table-education-progress" class="table table-striped table-bordered">
			<thead>
				<td>${educationProgressUiLabelMap.BkEunivEducationProgressId}</td>
				<td>${educationProgressUiLabelMap.BkEunivEducationType}</td>
				<td>${educationProgressUiLabelMap.BkEunivInstitution}</td>
				<td>${educationProgressUiLabelMap.BkEunivSpeciality}</td>
				<td>${educationProgressUiLabelMap.BkEunivGraduateDate}</td>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>

<div id="add-education-progress"></div>
<div id="change-education-progress"></div>