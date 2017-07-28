$(document).ready(function(){

	$('.tree-toggle').click(function () {$(this).parent().children('.tree').toggle(200);
	});

	$(function(){
	$('.tree-toggle').parent().children('.tree').toggle(200);
	})
	
	var href = window.location.href;
	Array.from($(".side-bar-item-container ul li a")).forEach(function(value, index) {
		if(href===value.href) {
			value.parentNode.classList.add("active");
			return;
		}
	}) 
	
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
