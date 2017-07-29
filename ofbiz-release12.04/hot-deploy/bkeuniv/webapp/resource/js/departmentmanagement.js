var table;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-department?facultyId=all",
	    method: 'GET',
	    dataType: "json",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	        console.log("success");
	    },
	    complete: function(data, status) {
	    	departments = JSON.parse(data.responseText.slice(data.responseText.indexOf('{'))).departments;
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
			  });
	    }
	});
});

function changeDepartment(department) {
	new Promise(function(resolve, reject) {
		resolve(new modal("#change-department").setting({
			data: department,
			columns: [
			          {
			        	  name: "ID",
			        	  value: "departmentId",
			        	  edit: false
			          },
			          {
			        	  name: "Tên bộ môn",
			        	  value: "departmentName"
			          },
			          {
			        	  name: "Khoa",
			        	  value: "facultyId"
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

function newDepartment() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-department").setting({
			data: {},
			columns: [
			          {
			        	  name: "Tên bộ môn",
			        	  value: "departmentName"
			          },
			          {
			        	  name: "Khoa",
			        	  value: "facultyId"
			          }
			          ],
			          title: "Chỉnh sửa bộ môn",
			          action: {
			        	  func: "addDepartment",
			        	  name: "Thêm mới"
			          }
		}).render());
	}).then(function(val) {
		$("#add-department #modal-template").modal('show');
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

function addDepartment(){
	
}
