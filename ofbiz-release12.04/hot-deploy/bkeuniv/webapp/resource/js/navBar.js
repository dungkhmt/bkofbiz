$(document).ready(function(){
	
});
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