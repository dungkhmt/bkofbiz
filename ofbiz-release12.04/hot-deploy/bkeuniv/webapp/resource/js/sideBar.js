var test;
$(document).ready(function () {
	$('.tree-toggle').click(function () {
		test = this;
		var self = this;
		var iconOpen = "fa-chevron-down";
		var iconClose = "fa-chevron-up";
		var idChildren = self.id + "_children";

		var status = $("#" + idChildren).css('display');

		var menuItem = menu.Items.find(function(m) {
			return m.id == self.id;
		})

		

		$(self).parent().children('ul.tree').toggle(50);
		setTimeout(function () {
			if (status == "block") {
				if(!!menuItem) {
					menuItem.status = "none";
				}
				if (!$(self).children(".item-icon-right").hasClass(iconOpen)) {
					$(self).children(".item-icon-right").removeClass(iconClose)
					$(self).children(".item-icon-right").addClass(iconOpen)
				}
			} else {
				if(!!menuItem) {
					menuItem.status = "block";
				}
				if (!$(self).children(".item-icon-right").hasClass(iconClose)) {
					$(self).children(".item-icon-right").removeClass(iconOpen)
					$(self).children(".item-icon-right").addClass(iconClose)
				}
			}
			localStorage.setItem("menu", JSON.stringify(menu));
		}, 51);
	});
	$('.tree-toggle').parent().children('ul.tree').toggle(0);

	var menuLocal = localStorage.getItem("menu");
	if(!!menuLocal) {
		try {
			menuLocal = JSON.parse(menuLocal);
			
			menuLocal.Items.forEach(function(item) {
				if(item.status == "block") {
					var m = $("#" + item.id + "_children");
					
					if(m.length > 0 && m.css('display') !== "block") {
						m.css('display', 'block')
					}
				}
			});
	
			if(menuLocal.status == "block") {
				openSideBar()
			} else {
				closeSideBar()
			}
	
			//upgrade menu
			menu = Object.assign({}, menu, menuLocal);
		}
		catch(err) {
			console.log("not get menu from store local")
		}
		

	} else {
		openSideBar();
	}


	
	// $('.tree-toggle').click(function () {

	// 	var _ = $(this)[0]
	// 	var el = $(this).parent().children('.tree');
	// 	el.toggle(200);
	// 	setTimeout(function(){
	// 		if(el[0].style.display==="none"){
	// 			_.children[0].classList.remove("glyphicon-minus");
	// 			_.children[0].classList.add("glyphicon-plus");
	// 		} else {
	// 			_.children[0].classList.remove("glyphicon-plus");
	// 			_.children[0].classList.add("glyphicon-minus");
	// 		}
	// 	}, 220);
	// });

	// $(function(){
	// 	var el = $('.tree-toggle').parent().children('.tree');
	// })

	var href = window.location.href;
	Array.from($(".side-bar-item-container ul li div a")).forEach(function(value, index) {
		if(href===value.href) {
			value.parentNode.classList.add("active-menu");
			return;
		}
	}) 
});
function openSessionSideBar(el) {
	if (el.className.indexOf("side-bar") !== -1) {
		return;
	}

	var els = el.parentNode.children;
	for (var i = 0, len = els.length; i < len; ++i) {
		if (els[i].className.indexOf("nav-header") !== -1) {
			openSideBar();
			return;
		}
	}

	openSessionSideBar(el.parentNode);
}


function openSideBar() {
	if ($(".side-bar").hasClass("hide-side-bar")) {
		$(".side-bar").removeClass("hide-side-bar");
		menu.status = "block"
	} else {
		closeSideBar();
	}
	localStorage.setItem("menu", JSON.stringify(menu));
}

function closeSideBar() {
	menu.status = "none"
	if (!$(".side-bar").hasClass("hide-side-bar")) {
		$(".side-bar").addClass("hide-side-bar");
	}
	localStorage.setItem("menu", JSON.stringify(menu));
}
