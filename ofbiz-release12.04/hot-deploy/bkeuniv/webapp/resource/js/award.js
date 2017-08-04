var table;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-award",
	    method: 'POST',
	    dataType: "json",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	        console.log("success");
	    },
	    complete: function(data, status) {
	    	award = JSON.parse(data.responseText.slice(data.responseText.indexOf('{'))).award;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-award').DataTable({
	   		 data: award,
	           columns: [
	               { "data": "awardId" },
	               { "data": "staffId" },
	               { "data": "description" }
	               { "data": "year"}
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
			$(document).contextmenu({
			    delegate: "#table-award td",
			menu: [
			  {title: "Edit", cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: "Delete", cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var award = table.row( el ).data();
				award.el = el;
				switch(ui.cmd){
					case "edit":
						changeAward(award)
						break;
					case "delete":
						deleteAward(award);
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

function changeAward(award) {
	new Promise(function(resolve, reject) {
		resolve(new modal("#change-award").setting({
			data: award,
			columns: [
			          {
			        	  name: awardId,
			        	  value: "awardId",
			        	  edit: false
			          },
			          {
			        	  name: description,
			        	  value: "description"
			          },
			          {
			        	  name: year,
			        	  value: "year"
			          },
			          {
			        	  name: staffId,
			        	  value: "staffId"
			          }
			          ],
			          title: titleEditAward,
			          action: {
			        	  func: "saveAward",
			        	  name: update
			          }
		}).render());
	}).then(function(val) {
		$("#change-award #modal-template").modal('show');
	})
	
}

function newAward() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-award").setting({
			data: {},
			columns: [
					  {
						  name: description,
						  value: "description"
					  },
					  {
						  name: year,
						  value: "year"
					  },
					  {
						  name: staffId,
						  value: "staffId"
					  }
					],
			          ],
			          title: titleAddAward,
			          action: {
			        	  func: "addAward",
			        	  name: add
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
