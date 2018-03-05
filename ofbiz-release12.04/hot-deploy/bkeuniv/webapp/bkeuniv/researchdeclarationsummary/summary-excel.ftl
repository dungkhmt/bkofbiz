<#include "component://bkeuniv/webapp/bkeuniv/layout/JqLibrary.ftl"/>
<style>
.floating-box {
    display: inline-block;
    width: 200px;
    height: 50px;
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
    color: black;
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
    background-color: lightgreen;
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
		//alert("openModelKV01");
		model = document.getElementById('model-kv01');
		span = document.getElementsByClassName("close")[0];
		span.onclick = function(){
			model.style.display = "none";
		}	
		
		$.ajax({
					url: "/bkeuniv/control/get-academic-years-faculties",
					type: 'POST',
					data: {
						"universityId": "HUST"
					},
					success:function(rs){
						console.log(rs);
						var select_years = document.getElementById("reportyear-kv01");
						var select_faculty = document.getElementById("facultyId-kv01");
						
						var lst_years = rs.years;
						var lst_faculties = rs.faculties;
						
						for(i = 0; i < lst_years.length; i++){	
							var o = document.createElement("option");
							o.text = lst_years[i].name;
							o.value = lst_years[i].id;
							select_years.appendChild(o);
						}
						for(i = 0; i < lst_faculties.length; i++){
							var o = document.createElement("option");
							o.text = lst_faculties[i].name;
							o.value = lst_faculties[i].id;
							select_faculty.appendChild(o);
						}
						model.style.display = "block";

					}
				})
	}

	function openModalKV04() {
		//alert("openModelKV01");
		model = document.getElementById('model-kv04');
		span = document.getElementsByClassName("close")[1];
		span.onclick = function(){
			model.style.display = "none";
		}	
		
		$.ajax({
					url: "/bkeuniv/control/get-academic-years-faculties",
					type: 'POST',
					data: {
						"universityId": "HUST"
					},
					success:function(rs){
						console.log(rs);
						var select_years = document.getElementById("reportyear-kv04");
						var select_faculty = document.getElementById("facultyId-kv04");
						
						var lst_years = rs.years;
						var lst_faculties = rs.faculties;
						
						for(i = 0; i < lst_years.length; i++){	
							var o = document.createElement("option");
							o.text = lst_years[i].name;
							o.value = lst_years[i].id;
							select_years.appendChild(o);
						}
						for(i = 0; i < lst_faculties.length; i++){
							var o = document.createElement("option");
							o.text = lst_faculties[i].name;
							o.value = lst_faculties[i].id;
							select_faculty.appendChild(o);
						}
						model.style.display = "block";

					}
				})
	}

	function openModalISI() {
		model = document.getElementById('model-isi');
		span = document.getElementsByClassName("close")[2];
		span.onclick = function(){
			model.style.display = "none";
		}	
		
		$.ajax({
					url: "/bkeuniv/control/get-academic-years-faculties",
					type: 'POST',
					data: {
						"universityId": "HUST"
					},
					success:function(rs){
						console.log(rs);
						var select_years = document.getElementById("reportyear-isi");
						var select_faculty = document.getElementById("facultyId-isi");
						
						var lst_years = rs.years;
						var lst_faculties = rs.faculties;
						
						for(i = 0; i < lst_years.length; i++){	
							var o = document.createElement("option");
							o.text = lst_years[i].name;
							o.value = lst_years[i].id;
							select_years.appendChild(o);
						}
						for(i = 0; i < lst_faculties.length; i++){
							var o = document.createElement("option");
							o.text = lst_faculties[i].name;
							o.value = lst_faculties[i].id;
							select_faculty.appendChild(o);
						}
						model.style.display = "block";

					}
				})
	}

	function openModalBomon010203() {
		model = document.getElementById('model-bm-01-02-03');
		span = document.getElementsByClassName("close")[3];
		span.onclick = function(){
			model.style.display = "none";
		}	
		
		$.ajax({
					url: "/bkeuniv/control/get-academic-years-faculties",
					type: 'POST',
					data: {
						"universityId": "HUST"
					},
					success:function(rs){
						console.log(rs);
						var select_years = document.getElementById("reportyear-bm-01-02-03");
						var select_faculty = document.getElementById("facultyId-bm-01-02-03");
						
						var lst_years = rs.years;
						var lst_faculties = rs.faculties;
						
						for(i = 0; i < lst_years.length; i++){	
							var o = document.createElement("option");
							o.text = lst_years[i].name;
							o.value = lst_years[i].id;
							select_years.appendChild(o);
						}
						for(i = 0; i < lst_faculties.length; i++){
							var o = document.createElement("option");
							o.text = lst_faculties[i].name;
							o.value = lst_faculties[i].id;
							select_faculty.appendChild(o);
						}
						model.style.display = "block";

					}
				})
	}

	function clearSelectBox(sel){
		var i;
		for(i = sel.options.length-1; i >= 0; i--){
			sel.remove(i);
		}
	}
	
	function changeFacultyBM010203(){
		var fId = document.getElementById("facultyId-bm-01-02-03").value;
		$.ajax({
					url: "/bkeuniv/control/get-departments-of-faculty",
					type: 'POST',
					data: {
						"facultyId": fId
					},
					success:function(rs){
						console.log(rs);
						var select_department = document.getElementById("departmentId-bm-01-02-03");
						
						clearSelectBox(select_department);
						
						var lst = rs.departments;
						
						
						for(i = 0; i < lst.length; i++){	
							var o = document.createElement("option");
							o.text = lst[i].name;
							o.value = lst[i].id;
							select_department.appendChild(o);
						}
					}
				})
		
	}
</script>
<body>
<div class="body">
	
	<div class="floating-box" onClick='openModalKV01()'>EXPORT KV01</div>
	<div class="floating-box" onClick='openModalISI()'>EXPORT ISI</div>
	<div class="floating-box" onClick='openModalBomon010203()'>EXPORT Bo mon 01-02-03</div>
	<div class="floating-box" onClick='openModalKV04()'>EXPORT KV04</div>
	
	
	<div id="model-kv01" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>Export KV01</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-kv01</@ofbizUrl>" method="post">
	         <select id="reportyear-kv01" name="reportyear-kv01" width="50px"></select><br>
			 <select id="facultyId-kv01" name="facultyId-kv01" width="100px">
			 </select>
			         
			 <input type="submit" value="export KV01"/>
			</form>
	    </div>
	  </div>
	</div>
	<div id="model-kv04" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>Export KV04</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-kv04</@ofbizUrl>" method="post">
	         <select id="reportyear-kv04" name="reportyear-kv04" width="50px"></select><br>
			 <select id="facultyId-kv04" name="facultyId-kv04" width="100px">
			 </select>
			         
			 <input type="submit" value="export KV04"/>
			</form>
	    </div>
	  </div>
	</div>
		
	<div id="model-isi" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>Export ISI</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-isi</@ofbizUrl>" method="post">
	         <select id="reportyear-isi" name="reportyear-isi" width="50px"></select><br>
			 <select id="facultyId-isi" name="facultyId-isi" width="100px">
			 
			 </select>        
			 <input type="submit" value="export ISI"/>
			</form>
	    </div>
	  </div>
	</div>


	<div id="model-bm-01-02-03" class="modal-export-excel">
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>Export Bo mon 01-02-03</h2>
	    </div>
	    
	    <div class="modal-body">
	     
	       <form action="<@ofbizUrl>export-excel-bm-01-02-03</@ofbizUrl>" method="post">
	         <select id="reportyear-bm-01-02-03" name="reportyear-bm-01-02-03" width="50px"></select><br>
			 <select id="facultyId-bm-01-02-03" name="facultyId-bm-01-02-03" width="100px" onchange="changeFacultyBM010203()">
			 </select>        
			 <select id="departmentId-bm-01-02-03" name="departmentId-bm-01-02-03" width="100px">
			 </select>        
			 
			 <input type="submit" value="export Bo mon 01-02-03"/>
			</form>
	    </div>
	  </div>
	</div>

</div>


