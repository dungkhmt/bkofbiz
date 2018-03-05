<script src="/resource/bkeuniv/js/lib/Chart.min.js"></script>
<body>

<canvas id="paper-chart" width="700" height="400"></canvas> 


<script> <!--doan nay de ve bieu do cot len canvas-->

var barData = {
	labels : [<#list papers.years as y>"${y}",</#list>],
	datasets : [
		{
			fillColor : "#48A497", 
			data : [<#list papers.count as c>${c},</#list>]
		}		
		]
	}
	console.log(barData);
	var paperChart = document.getElementById("paper-chart").getContext("2d");
	new Chart(paperChart).Bar(barData);
</script>

<div class="body">
</div>
