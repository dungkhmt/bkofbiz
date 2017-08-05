var table;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-scientificserviceexperience",
	    method: 'POST',
	    dataType: "json",
	    data:"",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	        console.log("success");
	    },
	    complete: function(data, status) {
	    	console.log(data);
	    	/*departments = JSON.parse(data.responseText.slice(data.responseText.indexOf('{'))).departments;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 100;
	    	table = $('#table-department-management').DataTable({
	   		 data: departments,
	           columns: [
	               { "data": "departmentId" },
	               { "data": "departmentName" },
	               { "data": "facultyId" }
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
			$(document).contextmenu({
			    delegate: "#table-department-management td",
			menu: [
			  {title: "Edit", cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: "Delete", cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var department = table.row( el ).data();
				department.el = el;
				switch(ui.cmd){
					case "edit":
						changeDepartment(department)
						break;
					case "delete":
						deleteDepartment(department);
						break;
					}
				},
				beforeOpen: function(event, ui) {
					var $menu = ui.menu,
						$target = ui.target,
						extraData = ui.extraData;
					ui.menu.zIndex(9999);
			    }
			  });*/
	    }
	});
});

function changeScientificServiceExperience(scientificserviceexperience) {
	new Promise(function(resolve, reject) {
		resolve(new modal("#change-department").setting({
			data: scientificserviceexperience,
			columns: [
			          {
			        	  name: "ID",
			        	  value: "departmentId",
			        	  edit: false
			          },
			          {
			        	  name: SID,
			        	  value: "staffId"
			          },
			          {
			        	  name: Description,
			        	  value: "description"
			          },
			          {
			        	  name: Quantity,
			        	  value: "quantity"
			          }
			          ],
			          title: "Chỉnh sửa bộ môn",
			          action: {
			        	  func: "saveDepartment",
			        	  name: "Cập nhật"
			          }
		}).render());
	}).then(function(val) {
		$("#change-department #modal-template").modal('show');
	})
	
}

function newScientificServiceExperience() {
	console.log("here");
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-scientificserviceexperience").setting({
			data: {},
			columns: [
			          {
			        	  name: SID,
			        	  value: "departmentName"
			          },
			          {
			        	  name: Description,
			        	  value: "facultyId"
			          },
			          {
			        	  name: Quantity,
			        	  value: "facultyId"
			          }
			          ],
			          title: "Chỉnh sửa bộ môn",
			          action: {
			        	  func: "addScientificServiceExperience",
			        	  name: "Thêm mới"
			          }
		}).render());
	}).then(function(val) {
		$("#add-scientificserviceexperience #modal-template").modal('show');
	})
}

function saveDepartment(department) {
	console.log(table.row( department.el ).data());
	alertify.success('Success message');
}

function deleteDepartment(department) {
	alertify.confirm("Delete department", "Bạn muốn xoá bộ môn " + department.departmentName,
	  function(){
	    alertify.success('Ok');
	  },
	  function(){
	    alertify.error('Cancel');
	  });
}

function addScientificServiceExperience(){
	$.ajax({
	    url: "/bkeuniv/control/get-scientificserviceexperience",
	    method: 'POST',
	    dataType: "json",
	    data:"",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	        console.log("success");
	    },
	    complete: function(data, status) {
	    	console.log(data);
	    }
	});
}
