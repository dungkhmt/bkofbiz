var table, test, temp;
var modalAdd;
var modalChange;

$(document).ready(function(){
	
	$.ajax({
	    url: "/bkeuniv/control/get-education-progress",
	    method: 'POST',
	    dataType: "json",
	    contentType: 'application/json; charset=utf-8',
	    success: function(data) {
	    	var educationProgress = data.educationProgress;
	    	var sizeTable = $(window).innerHeight() - $(".title").innerHeight() - $(".nav").innerHeight() - $(".footer").innerHeight() - 165;
	    	table = $('#table-education-progress').DataTable({
	   		 data: educationProgress,
	           columns: [
	               { "data": "educationType" },
	               { "data": "institution" },
	               { "data": "speciality" },
	               { "data": "graduateDate" }
	           ],
	           "scrollY": sizeTable,
	           "scrollCollapse": true,
	           "bJQueryUI": true
	       });
	    	
			$(document).contextmenu({
			    delegate: "#table-education-progress td",
			menu: [
			  {title: edit, cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
			  {title: remove, cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
			],
			select: function(event, ui) {
				var el = ui.target.parent();
				var educationProgress = table.row( el ).data();
				switch(ui.cmd){
					case "edit":
						changeEducationProgress(educationProgress)
						break;
					case "delete":
						deleteEducationProgress(educationProgress);
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

function changeEducationProgress(educationProgress) {
	new Promise(function(resolve, reject) {
		resolve(new modal("#change-education-progress").setting({
			data: educationProgress,
			columns: [
			          {
			        	  name: educationType,
			        	  value: "educationType"
			          },
			          {
			        	  name: institution,
			        	  value: "institution"
			          },
			          {
			        	  name: speciality,
			        	  value: "speciality"
			          },
			          {
			        	  name: graduateDate,
			        	  value: "graduateDate",
			        	  type: "date"
			          },
			          {
			        	  name: staffId,
			        	  value: "staffId"
			          }
			          ],
			          title: titleEditEducationProgress,
			          action: {
			        	  func: "saveEducationProgress()",
			        	  name: update
			          }
		}).render());
	}).then(function(modal) {
		modalChange = modal;
		$("#change-education-progress #modal-template").modal('show');
	})
	
}

function newEducationProgress() {
	new Promise(function(resolve, reject) {
		resolve(new modal("#add-education-progress").setting({
			data: {},
			columns: [
			          {
			        	  name: educationType,
			        	  value: "educationType"
			          },
			          {
			        	  name: institution,
			        	  value: "institution"
			          },
			          {
			        	  name: speciality,
			        	  value: "speciality"
			          },
			          {
			        	  name: graduateDate,
			        	  value: "graduateDate",
			        	  type: "date"
			          },
			          {
			        	  name: staffId,
			        	  value: "staffId"
			          }
			          ],
			          title: titleNewEducationProgress,
			          action: {
			        	  func: "addEducationProgress()",
			        	  name: add
			          }
		}).render());
	}).then(function(modal) {
		modalAdd = modal
		$("#add-education-progress #modal-template").modal('show');
	})
}

function saveEducationProgress() {
	openLoader();
	var educationProgress = modalChange.data();

	$.ajax({
	    url: "/bkeuniv/control/update-education-progress",
	    type: 'post',
	    data: educationProgress,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.result) {
	    		table.rows().indexes().data().filter(function(e, index) {
	    			if(e.educationProgressId == educationProgress.educationProgressId) {
	    				e.index = index;
	    				return true;
	    			}
	    		}).map(function(el, index){
	    			el.educationType = educationProgress.educationType;
	    			el.institution = educationProgress.institution;
	    			el.speciality = educationProgress.speciality;
	    			el.graduateDate = educationProgress.graduateDate;
	    			el.staffId = educationProgress.staffId;
	    			table.row(el.index).data(el);
	    		})
	    		
	    		setTimeout(function() {
	    			closeLoader();
	    			$("#change-education-progress #modal-template").modal('hide');
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
	    		alertify.success(JSON.stringify(err));
	    	}, 500);
	    	console.log(err);
	    }
	})
}

function deleteEducationProgress(educationProgress) {
	alertify.confirm("Confirm", BkEunivTitleDeleteEducationProgress + " ID = " + educationProgress.educationProgressId,
	function(){
		openLoader();

		$.ajax({
		    url: "/bkeuniv/control/delete-education-progress",
		    type: 'post',
		    data: educationProgress,
		    datatype:"json",
		    success: function(data) {
		    	if(!!data.result) {
		    		table.rows().indexes().data().filter(function(e, index) {
		    			if(e.educationProgressId == educationProgress.educationProgressId) {
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
		    		alertify.success(JSON.stringify(err));
		    	}, 500);
		    	console.log(err);
		    	
		    }
		})
	},
	function(){
	});
}

function addEducationProgress(){
	openLoader();
	var newEducationProgress = modalAdd.data();

	$.ajax({
	    url: "/bkeuniv/control/create-education-progress",
	    type: 'post',
	    data: newEducationProgress,
	    datatype:"json",
	    success: function(data) {
	    	if(!!data.educationProgress) {
	    		table.row.add(data.educationProgress).draw();
		    	setTimeout(closeLoader(), 500);
		    	
		    	$("#add-education-progress #educationtype").val("");
				$("#add-education-progress #institution").val("");
				$("#add-education-progress #speciality").val("");
				$("#add-education-progress #graduatedate").val("");
				$("#add-education-progress #staffid").val("");
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
	    		alertify.success(JSON.stringify(err));
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
