<script src="/resource/bkeuniv/js/lib/Chart.min.js"></script>
<body>

<#assign years=[]/>
<#assign count=[]/>
<#list papers.years as y>
	<#assign years = years + [y]/>
	${y}<br>
</#list>

<#list papers.count as c>
	<#assign count = count + [c]/>
	${c}<br>
</#list>

<canvas id="paper-chart" width="600" height="400"></canvas> 


<script> <!--doan nay de ve bieu do cot len canvas-->

var barData = {
	labels : [<#list papers.years as y>'${y}'</#list>],
	datasets : [
		{
			fillColor : "#48A497", 
			data : [<#list papers.count as c>'${c}'</#list>]
		}		
		]
	}
	console.log(barData);
	var paperChart = document.getElementById("paper-chart").getContext("2d");
	new Chart(paperChart).Bar(barData);
</script>

<div class="body">
</div>
