var table;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-scientificserviceexperience",
	    type: 'post',
	    dataType: "json",
	    data:"",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	        console.log(data.listScientificServiceExperience);
	        var scientificServiceExperience=data.listScientificServiceExperience;
	        var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	        table = $('#table-scientificserviceexperience-management').DataTable({
		   		 data: scientificServiceExperience,
		           columns: [
		               { "data": "scientificServiceExperienceId" },
		               { "data": "staffId" },
		               { "data": "description" },
		               { "data": "quantity" }
		           ],
		           "scrollY": sizeTable,
		           "scrollCollapse": true,
		           "bJQueryUI": true
		       });
	        $(document).contextmenu({
			    delegate: "#table-scientificserviceexperience-management td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var e = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeScientificServiceExperience(e)
						break;
					case "delete":
						deleteScientificServiceExperience(e);
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

function changeScientificServiceExperience(scientificserviceexperience) {
	console.log("changeScientificServiceExperience");
	new Promise(function(resolve, reject) {
		resolve(new modal("#change-scientificserviceexperience").setting({
			data: scientificserviceexperience,
			columns: [
			          {
			        	  name: "ID",
			        	  value: "scientificServiceExperienceId",
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
			          title: updateTitle,
			          action: {
			        	  func: "saveScientificServiceExperience()",
			        	  name: updateCaption
			          }
		}).render());
	}).then(function(val) {
		$("#change-scientificserviceexperience #modal-template").modal('show');
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
			          title: addTitle,
			          action: {
			        	  func: "addScientificServiceExperience()",
			        	  name: addCaption
			          }
		}).render());
	}).then(function(val) {
		$("#add-scientificserviceexperience #modal-template").modal('show');
	})
}

function saveScientificServiceExperience() {
	//cosole.log("hereeee");
	openLoader();
	var json={
			"scientificServiceExperienceId":$('#scientificserviceexperienceid').val(),
			"staffId":$('#staffid').val(),
			"description":$('#description').val(),
			"quantity":$('#quantity').val()
	}
	console.log(json);
	$.ajax({
	    url: "/bkeuniv/control/update-scientificserviceexperience",
	    type: 'post',
	    data: json,
	    datatype:"json",
	    success: function(data) {
	    	console.log(data);
	    	if(data.object!=null) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.scientificServiceExperienceId == json.scientificServiceExperienceId) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.staffId = json.staffId;
	    			el.description = json.description;
	    			el.quantity = json.quantity;
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-scientificserviceexperience #modal-template").modal('hide');
	    			alertify.success(data.json);
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

function deleteScientificServiceExperience(se) {
	alertify.confirm("Confirm", deleteconfirm  + se.scientificServiceExperienceId,
			function(){
				openLoader();
				$.ajax({
				    url: "/bkeuniv/control/delete-scientificserviceexperience",
				    type: 'post',
				    data: se,
				    datatype:"json",
				    success: function(data) {
				    	if(data.id!=null) {
				    		table.rows().indexes().data().filter(function(e, index) {
				    			if(e.scientificServiceExperienceId == se.scientificServiceExperienceId) {
				    				e.index = index;
				    				return true;
				    			}
				    		}).map(function(el, index){
				    			table.row(el.index).remove().draw();
				    		})
				    		
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(remove + " "+data.id);
				    		}, 500);
				    	} else {
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(failStr+remove+" "+ JSON.stringify(data.id));
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

function addScientificServiceExperience(){
	
	var json={
			
			"staffId":$('#staffid').val(),
			"description":$('#description').val(),
			"quantity":$('#quantity').val()
	}
	console.log(json);
	$.ajax({
	    url: "/bkeuniv/control/create-scientificserviceexperience",
	    type: 'post',
	    datatype: "json",
	    data:json,
	    success: function(data) {
	        console.log("data",data);
	        if(data.object != null){
	    		table.row.add(data.object).draw();
		    	setTimeout(closeLoader(), 500);
		    	$('#staffid').val("");
		    	$('#description').val("");
		    	$('#quantity').val("");
		    	alertify.success('Created new row');
	    	} else{
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
	});
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

