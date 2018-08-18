<script src="/resource/bkeuniv/js/lib/Chart.min.js"></script>
<body>

<canvas id="paper-chart" width="700" height="400"></canvas> 

<script> <!--doan nay de ve bieu do cot len canvas-->

var barDataPaper = {
	labels : [<#list papers.years as y>"${y}",</#list>],
	datasets : [
		{
			label:"paper",
			fillColor : "Blue", 
			data : [<#list papers.countPapers as c>${c},</#list>]
		},
		{
			label:"paperISI",
			fillColor : "Red", 
			data : [<#list papers.countPapersISI as c>${c},</#list>]
		}			
		]
	}

	var paperChart = document.getElementById("paper-chart").getContext("2d");
	
	new Chart(paperChart).Bar(barDataPaper);

</script>

<div class="body">
</div>
