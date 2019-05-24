<script src="/resource/bkeuniv/js/lib/Chart.min.js"></script>
<body>

<canvas id="paper-chart" width="700" height="400"></canvas> 
<canvas id="bar-chart" width="700" height="400"></canvas> 
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


	new Chart(document.getElementById("bar-chart"), {
    type: 'bar',
    data: {
      labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
      datasets: [
        {
          label: "Population (millions)",
          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
          data: [2478,5267,734,784,433]
        }
      ]
    },
    options: {
      legend: { display: false },
      title: {
        display: true,
        text: 'Predicted world population (millions) in 2050'
      }
    }
});
</script>

<div class="body">
</div>
