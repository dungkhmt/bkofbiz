function changeLanguage() {
	var selectBox = document.getElementById("select-language");
    var url = selectBox.options[selectBox.selectedIndex].value;
	console.log(url)
	window.location.href = url;
}