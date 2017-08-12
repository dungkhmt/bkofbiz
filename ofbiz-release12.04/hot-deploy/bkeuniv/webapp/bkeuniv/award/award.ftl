<head>
	<!-- import DataTable -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/dataTables.bootstrap.min.js"></script>
	
	<!-- import css component -->
	<link rel="stylesheet" href="/resource/bkeuniv/css/award.css">
		

</head>

<script>

	var remove = '${uiLabelMap.BkEunivRemove}';
	var edit = '${uiLabelMap.BkEunivEdit}';
	
	var awardId = '${uiAwardLabelMap.BkEunivAwardId}';
	var description = '${uiAwardLabelMap.BkEunivDescription}';
	var year = '${uiAwardLabelMap.BkEunivYear}';
	var staffId = '${uiAwardLabelMap.BkEunivStaffId}';
	var titleEditAward = '${uiAwardLabelMap.BkEunivTitleEditAward}';
	var add = '${uiAwardLabelMap.BkEunivAddRow}';
	var update = '${uiAwardLabelMap.BkEunivUpdate}';
	var titleAddAward = '${uiAwardLabelMap.BkEunivTitleNewAward}';
<!-- import js component -->
</script>
<script src="/resource/bkeuniv/js/award.js"></script>
<body>
<div class="body">
	<div class="award">
		<div class="title">
			<a href="#" class="title-hyperlink">
				${uiLabelMap.BkEunivAward}
			</a>
		</div>
		<div id="button-add-award" onClick="newAward()">
			${uiLabelMap.BkEunivAdd}
		</div>
		
		<table id="table-award" class="table table-striped table-bordered">
			<thead>
				<td>${uiAwardLabelMap.BkEunivAwardId}</td>
				<td>${uiAwardLabelMap.BkEunivDescription}</td>
				<td>${uiAwardLabelMap.BkEunivYear}</td>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>

<div id="add-award"></div>
<div id="change-award"></div>