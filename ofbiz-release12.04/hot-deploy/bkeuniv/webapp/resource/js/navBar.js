$(document).ready(function(){
	
});

function logout() {
	window.location = "/bkeuniv/control/logout";
}

function goto(url) {
	window.location = url;
}

function openDropdownUser() {
	if($("div .dropdown-content").hasClass("hidden")) {
		$("div .dropdown-content").removeClass("hidden");
		$("div .dropdown-content").addClass("open");
	} else {
		closeDropdownUser();
	}
}

function closeDropdownUser() {
	if($("div .dropdown-content").hasClass("open")) {
		$("div .dropdown-content").removeClass("open");
		$("div .dropdown-content").addClass("hidden");
	}
}