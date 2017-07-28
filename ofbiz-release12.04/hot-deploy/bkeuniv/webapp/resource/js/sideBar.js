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
			openSessionSideBar(value.parentNode);
			return;
		}
	}) 
	
});

function openSessionSideBar(el) {
	if(el.className.indexOf("side-bar")!==-1) {
		return;
	}
	
	var els = el.parentNode.children;
	for(var i = 0, len = els.length; i < len; ++i ) {
		if(els[i].className.indexOf("nav-header")!==-1) {
			els[i].click();
			openSideBar();
			return;
		}
	}
	
	openSessionSideBar(el.parentNode);
}


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
