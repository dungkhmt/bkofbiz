<html>
	<head>
		<meta charset="utf8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" href="/resource/bkeuniv/image/logo_soict2-75x75.png" />
		<!-- import lib -->
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap.min.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
		<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">

		<!-- import css -->
		<#list layoutSettings.stylesheets as sheet>
			<link rel="stylesheet" href="${StringUtil.wrapString(sheet)}">
		</#list> 
		
		<!-- import js -->
		<script src="/resource/bkeuniv/js/lib/jquery.min.js"></script>
		<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>
		<script src="/resource/bkeuniv/js/common.js"></script>
		
		<#list layoutSettings.scripts as script>
			<script src="${StringUtil.wrapString(script)}"></script>
		</#list>

		<script>
			window.entrypoint = window.location.origin + "<@ofbizUrl>/</@ofbizUrl>";
		</script>
		
		<title>BKEUNIV</title>
	</head>
<body>
	<div id="body" style="height: 100%;margin-top:40px;width:100%; background-color: #fff;display: flex;flex: 1 1 0%;overflow-y: hidden;overflow-x: scroll;">