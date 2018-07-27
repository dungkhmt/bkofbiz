<html>
	<head>
		<meta charset="utf8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" href="/resource/bkeuniv/image/logo_soict2-75x75.png" />
		<!-- import lib -->
		
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dragula.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/materialize.min.css">
		<!-- import css -->
		<#list layoutSettings.stylesheets as sheet>
			<link rel="stylesheet" href="${StringUtil.wrapString(sheet)}">
		</#list> 
		
		<!-- import js -->
		<script src="/resource/bkeuniv/js/lib/jquery.min.js"></script>
		<script src="/resource/bkeuniv/js/lib/materialize.min.js"></script>
		<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>
		<script src="/resource/bkeuniv/js/lib/dragula.min.js"></script>
		<script src="/resource/bkeuniv/js/common.js"></script>
		<script src="/resource/bkeuniv/js/lib/deepmerge.js"></script>
		
		<#list layoutSettings.scripts as script>
			<script src="${StringUtil.wrapString(script)}"></script>
		</#list>

		<script>
			window.entrypoint = window.location.origin + "<@ofbizUrl>/</@ofbizUrl>";
		</script>
		
		<title>H&#x1EC7; th&#x1ED1;ng qu&#x1EA3;n l&#xFD; khoa h&#x1ECD;c c&#xF4;ng ngh&#x1EC7;</title>
	</head>
<body>
	<div id="body" style="height: 95%;margin-top:40px;width:100%; background-color: #fff;display: flex;flex: 1 1 0%;overflow-y: hidden;overflow-x: hidden;">