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
	               { "data": "description" },
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
			          title: titleAddAward,
			          action: {
			        	  func: "addAward",
			        	  name: add
			          }
		}).render());
	}).then(function(val) {
		$("#add-award #modal-template").modal('show');
	})
}

function saveAward(award) {
	openLoader();
	var award = {
			"awardId": award["awardId"],
			"description": $("#change-award #description").val().trim(),
			"year": getDate("#change-award #year","yy-mm-dd"),
			"staffId": $("#change-award #staffid").val().trim()
		}
	$.ajax({
	    url: "/bkeuniv/control/update-award",
	    type: 'post',
	    data: award,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.result) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.awardId == award.awardId) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.description = award.description;
	    			el.year = award.year;
	    			el.staffId = award.staffId;
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-award #modal-template").modal('hide');
	    			alertify.success(data.result);
	    		}, 500);
	    	} else {
	    		setTimeout(function() {
	    			closeLoader();
	    			alertify.success(JSON.stringify(data._ERROR_MESSAGE_));
	    		}, 500);
	    	}
	    },
	    error: function(err) {
	    	setTimeout(function() {
	    		closeLoader();
	    		alertify.success(err.result);
	    	}, 500);
	    	console.log(err);
	    }
	})
}

function deleteAward(award) {
	alertify.confirm("Confirm", BkEunivTitleDeleteAward + " ID = " + award.awardId,
			function(){
				openLoader();

				$.ajax({
				    url: "/bkeuniv/control/delete-award",
				    type: 'post',
				    data: award,
				    datatype:"json",
				    success: function(data) {
				    	if(!!data.result) {
				    		table.rows().indexes().data().filter(function(e, index) {
				    			if(e.awardId == award.awardId) {
				    				e.index = index;
				    				return true;
				    			}
				    		}).map(function(el, index){
				    			table.row(el.index).remove().draw();
				    		})
				    		
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(data.result);
				    		}, 500);
				    	} else {
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(JSON.stringify(data.result));
				    		}, 500);
				    	}
				    },
				    error: function(err) {
				    	setTimeout(function() {
				    		closeLoader();
				    		alertify.success(err.result);
				    	}, 500);
				    	console.log(err);
				    	
				    }
				})
			},
			function(){
			});
}

function addAward(){
	openLoader();
	var newAward = {
		"description": $("#add-award #description").val().trim(),
		"year": getDate("#add-award #year","yy-mm-dd"),
		"staffId": $("#add-award #staffid").val().trim()
	}

	$.ajax({
	    url: "/bkeuniv/control/create-award",
	    type: 'post',
	    data: newAward,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.educationProgress) {
	    		table.row.add(data.educationProgress).draw();
		    	setTimeout(closeLoader(), 500);
		    	
		    	$("#add-award #description").val("");
				$("#add-award #year").val("");
				$("##add-award #staffid").val("");
				alertify.success('Created a new row');
	    	} else {
	    		setTimeout(function() {
		    		closeLoader();
		    		alertify.success(JSON.stringify(data._ERROR_MESSAGE_LIST_));
		    	}, 500);
	    	}
	    	
	    },
	    error: function(err) {
	    	setTimeout(function() {
	    		closeLoader();
	    		alertify.success(err);
	    	}, 500);
	    	console.log(err);
	    }
	})
}
function openLoader() {
	if($(".loader").hasClass("hidden-loading")) {
		$(".loader").removeClass("hidden-loading");
	}
}

function closeLoader() {
	if(!$(".loader").hasClass("hidden-loading")) {
		$(".loader").addClass("hidden-loading");
	}
}
