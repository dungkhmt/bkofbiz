<script src="/resource/bkeuniv/js/lib/Chart.min.js"></script>
<body>

<canvas id="paper-chart" width="700" height="400"></canvas> 
<canvas id="paper-isi-chart" width="700" height="400"></canvas> 


<script> <!--doan nay de ve bieu do cot len canvas-->

var barDataPaper = {
	labels : [<#list papers.years as y>"${y}",</#list>],
	datasets : [
		{
			fillColor : "#48A497", 
			data : [<#list papers.countPapers as c>${c},</#list>]
		}		
		]
	}

var barDataPaperISI = {
	labels : [<#list papers.years as y>"${y}",</#list>],
	datasets : [
		{
			fillColor : "#48A497", 
			data : [<#list papers.countPapersISI as c>${c},</#list>]
		}		
		]
	}
		
	//console.log(barData);
	var paperChart = document.getElementById("paper-chart").getContext("2d");
	var paperISIChart = document.getElementById("paper-isi-chart").getContext("2d");
	
	new Chart(paperChart).Bar(barDataPaper);
	new Chart(paperISIChart).Bar(barDataPaperISI);
</script>

<div class="body">
</div>
