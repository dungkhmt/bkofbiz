$(document).ready(function(){
	$.ajax({
	    url: "/bkeuniv/control/get-department?facultyId=all",
	    method: 'GET',
	    contentType: "application/json",
	    success: function(data) {
	        console.log("success");
	    },
	    complete: function(data, status) {
	    	departments = JSON.parse(data.responseText.slice(data.responseText.indexOf('{'))).departments;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 155;
	    	$('#table-department-management').DataTable({
	   		 data: departments,
	           columns: [
	               { "data": "departmentId" },
	               { "data": "departmentName" },
	               { "data": "facultyId" }
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	       });
	    }
	});
});

