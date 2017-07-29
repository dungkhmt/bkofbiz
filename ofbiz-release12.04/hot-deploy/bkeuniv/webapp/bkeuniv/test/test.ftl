<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>

<body>

<table>
	<tr>
		<td>a</td>
		<td><input type="text" name="a" id="a" value="0"/></td>
	</tr>
	<tr>
		<td>b</td>
		<td><input type="text" name="b" id="b" value="0"/></td>
	</tr>
	<tr>
		<td>
			<button id="process" value=""/>Process</button>
		</td>
	</tr>	
</table>

<script type="text/javascript">
$(document).ready(function(){
	$("#process").click(function(){
		var a = document.getElementById("a").value;
		var b = document.getElementById("b").value;
		
		$.ajax({
			type: "post",
			url: "<@ofbizUrl>testService</@ofbizUrl>", 
			data: {"a":a,"b":b},
			datatype:"json",
			success: function (rs) {
				alert("return: " + rs.sum + ", " + rs.prod);
			}
		}); 		
	});
});
	
</script>

</body>
</html>
