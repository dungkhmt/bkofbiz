$(document).ready(function(){
	
});

$.ajax({
    url: "/bkeuniv/control/get-department?facultyId=SOICT",
    method: 'GET',
    contentType: "application/json",
    success: function(data) {
        console.log("success");
    },
    complete: function(data, status) {
    	departments = JSON.parse(data.responseText.slice(data.responseText.indexOf('{'))).departments;
    	$('#table-department-management').DataTable({
   		 data: departments,
           columns: [
               { "data": "departmentId" },
               { "data": "departmentName" },
               { "data": "facultyId" }
           ]
       });
    }
});