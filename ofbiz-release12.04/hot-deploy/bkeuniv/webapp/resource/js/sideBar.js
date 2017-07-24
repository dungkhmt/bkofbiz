$(document).ready(function(){
	
});
function openSideBar() {
	if($(".side-bar").hasClass("hide-side-bar")) {
		$(".side-bar").removeClass("hide-side-bar");
	} else {
		closeSideBar();
	}
}

function closeSideBar() {
	if(!$(".side-bar").hasClass("hide-side-bar")) {
		$(".side-bar").addClass("hide-side-bar");
	}
}