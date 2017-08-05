var table, test, temp;
var modalChange,modalAdd;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-work-progress",
	    method: 'POST',
	    dataType: "json",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	    	var workProgress = data.workProgress;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-work-progress').DataTable({
	   		 data: workProgress,
	           columns: [
	               { "data": "workProgressId" },
	               { "data": "staffId" },
	               { "data": "period" },
	               { "data": "position" },
	               { "data": "specialization" },
	               { "data": "institution" }
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
	    	
	    	$(document).contextmenu({
			    delegate: "#table-work-progress td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var workProgress = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeWorkProgress(workProgress)
						break;
					case "delete":
						deleteWorkProgress(workProgress);
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

function changeWorkProgress(workProgress){
	new Promise(function(resolve, reject) {
		resolve(new modal("#change-work-progress").setting({
			data: workProgress,
			columns: [
			          {
			        	  name: workProgressId,
			        	  value: "workProgressId",
			        	  edit: false
			          },
			          {
			        	  name: staffId,
			        	  value: "staffId"
			          },
			          {
			        	  name: period,
			        	  value: "period"
			          },
			          {
			        	  name: position,
			        	  value: "position"
			          },
			          {
			        	  name: specialization,
			        	  value: "specialization"
			          },
			          {
			        	  name: institution,
			        	  value: "institution"
			          }
			          ],
			          title: titleEditWorkProgress,
			          action: {
			        	  func: "saveWorkProgress()",
			        	  name: update
			          }
		}).render());
	}).then(function(modal) {
		modalChange=modal;
		$("#change-work-progress #modal-template").modal('show');
	})
}
function saveWorkProgress(){
	openLoader();
	var workProgress=modalChange.data();
	/*var workProgress = {
		"workProgressId": ["workProgressId"],
		"staffId": $("#change-work-progress #staffid").val(),
		"period": $("#change-work-progress #period").val(),
		"position": $("#change-work-progress #position").val(),
		"specialization": $("#change-work-progress #specialization").val(),
		"institution": $("#change-work-progress #institution").val()
	}*/

	$.ajax({
	    url: "/bkeuniv/control/update-work-progress",
	    type: 'post',
	    data: workProgress,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.result) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.workProgressId == workProgress.workProgressId) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.staffId = workProgress.staffId;
	    			el.period = workProgress.period;
	    			el.position = workProgress.position;
	    			el.specialization = workProgress.specialization;
	    			el.institution = workProgress.institution;
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-work-progress #modal-template").modal('hide');
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
function deleteWorkProgress(workProgress){
	alertify.confirm("Confirm", titleDeleteWorkProgress + " ID = " + workProgress.workProgressId,
			function(){
				openLoader();

				$.ajax({
				    url: "/bkeuniv/control/delete-work-progress",
				    type: 'post',
				    data: workProgress,
				    datatype:"json",
				    success: function(data) {
				    	if(!!data.result) {
				    		table.rows().indexes().data().filter(function(e, index) {
				    			if(e.workProgressId == workProgress.workProgressId) {
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

function newWorkProgress() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-work-progress").setting({
			data: {},
			columns: [
			          {
			        	  name: staffId,
			        	  value: "staffId"
			          },
			          {
			        	  name: period,
			        	  value: "period"
			          },
			          {
			        	  name: position,
			        	  value: "position"
			          },
			          {
			        	  name: specialization,
			        	  value: "specialization"
			          },
			          {
			        	  name: institution,
			        	  value: "institution"
			          }
			          ],
			          title: titleNewWorkProgress,
			          action: {
			        	  func: "addWorkProgress()",
			        	  name: add
			          }
		}).render());
	}).then(function(modal) {
		modalAdd=modal;
		$("#add-work-progress #modal-template").modal('show');
	})
}

function addWorkProgress(){
	openLoader();
	var newWorkProgress=modalAdd.data();
	/*var newWorkProgress = {
			"staffId": $("#add-work-progress #staffid").val(),
			"period": $("#add-work-progress #period").val(),
			"position": $("#add-work-progress #position").val(),
			"specialization": $("#add-work-progress #specialization").val(),
			"institution": $("#add-work-progress #institution").val()
		}
	console.log(""+$("#add-work-progress #staffid").val());*/

		$.ajax({
		    url: "/bkeuniv/control/create-work-progress",
		    type: 'post',
		    data: newWorkProgress,
		    datatype:"json",
		    success: function(data) {
		    	if(!!data.workProgress) {
		    		table.row.add(data.workProgress).draw();
			    	setTimeout(closeLoader(), 500);
			    	$("#add-work-progress #staffId").val("");
					$("#add-work-progress #period").val("");
					$("#add-work-progress #position").val("");
					$("#add-work-progress #specialization").val("");
					$("#add-work-progress #institution").val("");
					alertify.success('Created new row');
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
