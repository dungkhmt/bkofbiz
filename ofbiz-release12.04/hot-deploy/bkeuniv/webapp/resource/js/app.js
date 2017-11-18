function getDate(selector, format) {
    //const [day, month, year] = $(selector).val().split(/\/|-|_|\|\s/);
    return $.datepicker.formatDate(format, new Date(year, month - 1, day))
}