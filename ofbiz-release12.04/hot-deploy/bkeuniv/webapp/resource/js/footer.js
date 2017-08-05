function changeLanguage() {
	var selectBox = document.getElementById("select-language");
    var url = selectBox.options[selectBox.selectedIndex].value;
    $.ajax({
	    url: url,
	    type: 'post',
	    success: function(data) {
	    	location.reload(true)
	    },
	    error: function(err) {
	    	alertify.success(JSON.stringify(err));
	    }
    });
}