var modal = function (id) {
	this.id = id;
}
var test;

modal.prototype._getDate = function (selector, format) {
    const [day, month, year] = $([this.id,selector].join(" ")).val().split(/\/|-|_|\|\s/);
    return $.datepicker.formatDate(format, new Date(year, month - 1, day))
}

modal.prototype._getText = function (selector) {
    return $([this.id,selector].join(" ")).val();
}

modal.prototype._date = function(value, edit, id){
	return '<input type="text" class=" date form-control"' +
					'id="' + id+'"' +
					(edit?"":"disabled ") +
					'value="' + $.datepicker.formatDate('dd/mm/yy', new Date(value||Date.now())) + '"'+
					'placeholder="dd/mm/yyyy"'+
					'>' +
    			'<script type="text/javascript">'+
    				'$(function () {'+
    					'$("#'+id+'").datepicker({format: "dd/mm/yyyy"});'+
        		'});'+
        		'</script>'
}

modal.prototype._text = function(value, edit, id){
	return '<input type="text" class="form-control"' +
					'id="' + id + '"' +
					(edit?"":"disabled ") +
					'value="' + value + '"'+
					'>';
}

modal.prototype._select = function(value, edit, id, option) {
	var _id = "#"+id;
	var script = '<script>'+
					'$('+id+').selectize({'+
						'create: true,'+
						'sortField: "text"'+
					'});'+
				'</script>';
	var _selected = op[option.value]==option.selected?"selected":"";
	var option = value.map(function(op, index) {
		return '<option value="'+op[option.value]+'" '+_selected+'>'+op[option.name]+'</option>';
	})
	return '<select id="'+id+'">'+option.join("")+'</select>'+script;
}

modal.prototype.setting = function(option) {
	this.option = option;
	this._data = option.data;
	this.columns = option.columns;
	
	for(var i = 0, len = this.columns.length; i < len; ++i) {
		var column = this.columns[i];
		column.id = column.value.replace(/\s/g, '-').toLowerCase();
		column.edit = column.hasOwnProperty("edit")?column.edit:true;
		column.type = column.hasOwnProperty("type")?column.type:"text";
		column._data = this._data[column.value]||"";
		var el;
		switch(column.type) {
		    case "date":
		    	el = this._date(column._data, column.edit, column.id);
		    	break;
		    case "text":
		    	el = this._text(column._data, column.edit, column.id);
		        break;
		    case "select":
		    	el = this._select(column._data, column.edit, column.id, column.option);
		    	break;
		}
		column.html = '<div class="row inline-box">'+
						'<label id="title-modal-input">'+column.name+'</label>'+el+
				      '</div>';
	}
	test = this.columns;
	return this;
}

modal.prototype.render = function() {
	
	var html = 
		'<div class="modal fade" id="modal-template" role="dialog">'+
		  '<div class="modal-dialog">'+
		    '<!-- Modal content-->'+
		    '<div class="modal-content">'+
		      '<div class="modal-header">'+
		        '<button type="button" class="close" data-dismiss="modal">&times;</button>'+
		        	'<h4 class="modal-title">'+this.option.title+'</h4>'+
		      '</div>'+
		      '<div class="modal-body">'+
		      	'<div class="container-fluid">'+this.columns.reduce(function(acc, curr){return acc + curr.html}, "")+'</div>'+
		      '</div>'+
		      '<div class="modal-footer">'+
		      	'<button type="button" class="btn btn-success" onclick="'+this.option.action.func+'" >'+this.option.action.name+'</button>'+
		   		'<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
		      '</div>'+
		    '</div>'+
		  '</div>'+
		'</div>';
	$(this.id).children().remove()
	$(this.id).append(html);
	return this;
}

modal.prototype.data = function() {
	var _ = this;
	var data = this.columns.reduce(function(acc, column) {
		if(column.edit) {
			switch(column.type) {
			    case "date":
			    	column._data = _._getDate("#"+column.id, "yy-mm-dd");
			    	break;
			    case "text":
			    	column._data = _._getText("#"+column.id);
			        break;
			}
		}
		acc[column.value] = column._data
		
		return acc;
	}, {})
	
	return this._mergeData(this._data, data)
}

modal.prototype._mergeData = function(base, compare) {
	if (!compare || typeof compare !== 'object') {
		return base;
	}

	var keys = Object.keys(compare);
	var key;
	
	for (var i = 0, len = keys.length; i < len; i++) {
		key = keys[i];
		if(compare.hasOwnProperty(key)) {
			base[key] = compare[key];			
		}
	}

	return base
}
