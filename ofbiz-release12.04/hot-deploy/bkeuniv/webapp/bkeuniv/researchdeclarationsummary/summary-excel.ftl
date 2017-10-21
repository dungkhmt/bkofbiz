<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<style>
.floating-box {
    display: inline-block;
    width: 300px;
    height: 175px;
    margin: 20px;
    border: 3px solid #73AD21;  
    cursor: pointer;
}

.after-box {
    border: 3px solid red; 
}

.modal-export-excel {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 50%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}
/* Modal Content */
.modal-content {
    position: relative;
    background-color: #fefefe;
    margin: auto;
    padding: 0;
    border: 1px solid #888;
    width: 80%;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
    -webkit-animation-name: animatetop;
    -webkit-animation-duration: 0.4s;
    animation-name: animatetop;
    animation-duration: 0.4s
}

/* Add Animation */
@-webkit-keyframes animatetop {
    from {top:-300px; opacity:0} 
    to {top:0; opacity:1}
}

@keyframes animatetop {
    from {top:-300px; opacity:0}
    to {top:0; opacity:1}
}

/* The Close Button */
.close {
    color: white;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

.modal-header {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}

.modal-body {padding: 10px 26px;}

.modal-footer {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}
</style>
<script>
	function openModal(template) {
		alert(template);
	}
	function openModalKV01() {
		model = document.getElementById('model-kv01');
		span = document.getElementsByClassName("close")[0];
		span.onclick = function(){
			model.style.display = "none";
		}	
		
		$.ajax({
					url: "/bkeuniv/control/get-academic-years",
					type: 'POST',
					data: {
						"abc": "def"
					},
					success:function(rs){
						console.log(rs);
						var select_years = document.getElementById("reportyear");
						var lst_years = rs;
						for(i = 0; i < lst_years.years.length; i++){	
							var o = document.createElement("option");
							o.text = lst_years.years[i].name;
							o.value = lst_years.years[i].id;
							select_years.appendChild(o);
						}
						model.style.display = "block";

					}
				})
	}
</script>
<body>
<div class="body">
	<#assign data=["Floating box 1", "Floating box 2", "Floating box 3", "Floating box 4", "Floating box 5"]>
	<#list data as template>
		<div class="floating-box" onClick='openModal("${template}")'>${template}</div>
	</#list>
	
<div class="floating-box" onClick='openModalKV01()'>EXPORT KV01</div>

<div id="model-kv01" class="modal-export-excel">
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2>Export KV01</h2>
    </div>
    
    <div class="modal-body">
     
       <form action="<@ofbizUrl>export-excel-kv01</@ofbizUrl>" method="post">
         <select id="reportyear" name="reportyear" width="50px"></select>
        
		 <input type="submit" value="export KV01"/>
		</form>
    </div>
    
  </div>


	
</div>
		
</div>


