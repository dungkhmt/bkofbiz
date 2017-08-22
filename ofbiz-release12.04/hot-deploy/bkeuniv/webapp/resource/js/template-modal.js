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

modal.prototype._getSelect = function (selector) {
    return $([this.id,selector].join(" ")).val();
}

modal.prototype._date = function(value, edit, id){
	var _id = "#"+id;
	return '<input type="text" class=" date form-control"' +
					'id="' + id+'"' +
					(edit?"":"disabled ") +
					'value="' + $.datepicker.formatDate('dd/mm/yy', new Date(value||Date.now())) + '"'+
					'placeholder="dd/mm/yyyy"'+
					'>' +
    			'<script type="text/javascript">'+
    				'$(function () {'+
    					'$("'+[this.id, _id].join(" ")+'").datepicker({format: "dd/mm/yyyy"});'+
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
	var maxItem = option.maxItem||1;
	var script = '<script type="text/javascript">'+
					'$(function () {'+
						'$("'+[this.id, _id].join(" ")+'").selectize({'+
							'maxItems: ' + maxItem + ', '+
							'sortField: "text"'+
						'});'+
					'});'+
				'</script>';
	var option;
	if(Object.prototype.toString.call( value ) === '[object Array]') {
		option = option.source.map(function(op, index) {
			var _selected = value.indexOf(op.value)!== -1?"selected":"";
			return '<option value="'+op.value+'" '+_selected+'>'+op.name+'</option>';
		})
	} else {
		option = option.source.map(function(op, index) {
			var _selected = value === op.value?"selected":"";
			return '<option value="'+op.value+'" '+_selected+'>'+op.name+'</option>';
		})	
	}
	return '<select style="width: 70%" id="'+id+'" multiple>'+option.join("")+'</select>'+script;
}

modal.prototype.setting = function(option) {
	this.option = option;
	this._data = option.data;
	this.columns = option.columns;
	this._action = option.action;
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
		    case "custom":
		    	el = column.el;
		    	break;
		    case "render":
		    	column.html = column.render(column._data, column.name, this._data, column.id);
		    	break;
		    	
		    	
		}
			column.html = column.html||'<div class="row inline-box">'+
				'<label id="title-modal-input">'+column.name+'</label>'+el+
			'</div>';
	}
	return this;
}


modal.prototype.action = function() {
	var _ = this;
	openLoader();
	$.ajax({
	    url: _._action.url,
	    type: 'post',
	    data: _.data(),
	    datatype:"json",
	    success: function(data) {
	    	if(!!_._action.update && typeof _._action.update === "function") {
	    		_._action.update(data)
	    	} else {
	    		_._updateDefault(data[_._action.fieldDataResult], data.message);
	    	}
	    }, error: function(err) {
			setTimeout(function() {
				closeLoader();
				alertify.error(JSON.stringify(err));
			}, 500);
			console.log(err);
		}
	})
}
	
modal.prototype._updateDefault = function(data, message) {
	if(!!this._action.dataTable) {
		var table = this._action.dataTable;
		var _ = this;
		var keys = this._action.keys||[];
		var element = table.rows().indexes().data().filter(function(e, index) {
			var check = keys.reduce(function(acc, curr) {
				return acc&&(e[curr]==data[curr]);
			}, true);
			
			if(check) {
				e.index = index;
				return true;
			}
		})[0]
		if(!!element&&(typeof element == "object")) {
			Object.keys(element).forEach(function(key, index){
				element[key] = data[key];
			})
			table.row(element.index).data(element);
			$([_.id, "#modal-template"].join(" ")).modal('hide');

	    	setTimeout(function() {
				closeLoader();
				alertify.success(message);
			}, 500);
		} else {
			table.row.add(data).draw();
	    	
			this.columns.forEach(function(column, index) {
				$([_.id,"#"+column.id].join(" ")).val("");
			})
			setTimeout(function() {
				closeLoader();
				alertify.success(message);
			}, 500);
			
		}
			
		
	} else {
		setTimeout(function() {
			closeLoader();
			alertify.error('No found data table');
		}, 500);
	}
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
		      	'<button type="button" class="btn btn-success" id="modal-action">'+(this._action.name||"Action")+'</button>'+
		   		'<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
		      '</div>'+
		    '</div>'+
		  '</div>'+
		'</div>';
	$(this.id).children().remove()
	$(this.id).append(html);
	var _ = this;
	$( this.id +" #modal-action" ).click(function() {
	  _.action();
	});
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
			    case "select":
			    	column._data = _._getSelect("#"+column.id);
			    	break;
			    case "custom":
			    	acc = _._mergeData(acc, column._data);
			    	return acc;
			    
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

