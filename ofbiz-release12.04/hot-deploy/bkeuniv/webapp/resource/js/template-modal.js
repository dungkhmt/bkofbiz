var modal = function (id) {
	this.id = id;
}

modal.prototype._getDate = function (selector, format) {
	var value = $([this.id,selector].join(" ")).datepicker( "getDate" );
	console.log(value, typeof value)
	if(isNaN(value.getTime())){
		console.log(value, typeof value)
		return ;
	} else {
		return (new Date(value)).getTime();
	}
    // const [day, month, year] = $([this.id,selector].join(" ")).val().split(/\/|-|_|\|\s/);
    // return $.datepicker.formatDate(format, new Date(year, month - 1, day))
}

modal.prototype._getText = function (selector) {
    return $([this.id,selector].join(" ")).val();
}

modal.prototype._getSelect = function (selector) {
	var _data_select = ($([this.id,selector].join(" ")).val());
	
	if(typeof _data_select == "string") {
		_data_select = [_data_select];
	}
	
    return _data_select;
}

modal.prototype._date = function(value, edit, id, defaultValue=""){
	var _id = "#"+id;
	return '<input type="text" class=" date form-control"' +
					'id="' + id+'"' +
					(edit?"":"disabled ") +
					'value="' + $.datepicker.formatDate('dd/mm/yy', (!value?(!defaultValue?defaultValue:new Date(defaultValue)):new Date(value))) + '"'+
					'placeholder="dd/mm/yyyy"'+
					'>' +
    			'<script type="text/javascript">'+
    				'$(function () {'+
    					'$("'+[this.id, _id].join(" ")+'").datepicker({format: "dd/mm/yyyy"});'+
        		'});'+
        		'</script>'
}

modal.prototype._text = function(value, edit, id, column){
	console.log(column)
	return '<input type="text" class="form-control"' +
					'id="' + id + '"' +
					(!!edit?"":"disabled ") +
					(!column.pattern?"":(' pattern="'+column.pattern+'" title="'+column.title + '" ')) +
					'value="' + value + '"'+
					'/>';
}

modal.prototype._textarea = function(value, edit, id){
	return '<textarea class="form-control"' +
					'id="' + id + '"' +
					(edit?"":"disabled ") +
					'>'+value+'</textarea>';
}

modal.prototype._select = function(value, edit, id, option) {
	var _id = "#"+id;
	var maxItem = option.maxItem||1;
	var script = '<script type="text/javascript">'+
					'$(function () {'+
						'$("'+[this.id, _id].join(" ")+'").select2({'+
							(maxItem>1?'maximumSelectionLength: ' + maxItem:"")+
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
	return '<div style="width: 70%"><select class="js-states form-control" style="width: 100%" id="'+id+'" '+(maxItem>1?'multiple':"")+'>'+option.join("")+'</select></div>'+script;
}

modal.prototype._buildScriptObject = function(object = {}, result = "") {
	fields_build = object.build_script||[];
	result+= "{";
	var that = this;
	console.log(Object.keys(object), object)
	Object.keys(object).forEach(function(key, index, array){
		var dot = ",";
		if(index == array.length - 1||((index == array.length -2)&&(array[index+1]==="build_script"))) {
			dot = "";
		}

		if(key === "build_script") {
			return;
		}

		result += '\'' + key + '\'' + ":";

		var value = object[key];

		if(!!fields_build.find(function(e){return e==key})) {
			result += 'eval(\'' + value + '\')'+dot;
			return;
		}

		
		if(value instanceof Array) {
			result += that._buildScriptArray(value) + dot;
			return;
		}

		if(value instanceof Object) {
			result += that._buildScriptObject(value) + dot;
			return;
		}

		if(typeof value == "function") {
			result += '\'' + value.toString() + '\'' + dot;
			return;
		}

		result += '\'' + value + '\'' + dot;
	});
	result+= "}";
	return result;
}

modal.prototype._buildScriptArray = function(array = [], result = "") {
	result+= "[";
	indexs_build = object.build_script||[];
	var that = this;

	array.forEach(function (value, index, array) {
		var dot = ",";
		if(index == array.length - 1||((index == array.length -2)&&(array[index+1]==="build_script"))) {
			dot = "";
		}

		if(key === "build_script") {
			return;
		}

		if(!!indexs_build.find(function(e){return e==index})) {
			result += 'eval(\'' + value + '\')' + dot;
			return;
		}
		
		if(value instanceof Array) {
			result += that._buildScriptArray(value) + dot;
			return;
		}

		if(value instanceof Object) {
			result += that._buildScriptObject(value) + dot;
			return;
		}

		if(typeof value == "function") {
			result += '\'' + value.toString() + '\'' + dot;
			return;
		}

		result += '\'' + value + '\'' + dot;
	});

	result+= "]";
	return result;
}

modal.prototype._select_server_side = function(value, edit, id, option, column) {
	var _id = "#"+id;
	var maxItem = option.maxItem||1;
	var render;

	var query_custom = '{}';
	if(!!option.query) {
		query_custom = this._buildScriptObject(option.query||{});
	}
	

	if(typeof option.render === "function") {
		render =  option.render.toString();
	} else {
		render = 'function(r){return {id: r.'+option.value+', text: r.'+option.name+'}}';
	}

	var selected = "";
	if(!!value) {
		selected = 
		'$.ajax({'+
			'url: "'+option.url+'",'+
			'"method": "POST",'+
			'"content-type": "application/json",'+
			'"data": {'+
				'"filter": \'{"field": "'+column.value+'", "value": "'+value+'", "operation": \"EQUAL\" }\','+
				'"pagesize": "-1",'+
			'},'+
			'success: function(r) {'+
				'var data = r.results.map('+render+');'+
				'var options = data.map(function(d) { return new Option(d.text, d.id, true, true); });'+
				'$("'+[this.id, _id].join(" ")+'").append(options).trigger("change");'+
				'$("'+[this.id, _id].join(" ")+'").trigger({'+
					'type: "select2:select",'+
					'params: {'+
						'data: data'+
					'}'+
				'});'+
			'}'+
		'});';

	}
	
	var script = '<script type="text/javascript">'+
					'$(function () {'+
						'$("'+[this.id, _id].join(" ")+'").select2({'+
							'language: "vi",'+
							'ajax: {' +
								'url: "'+option.url+'",'+
								'"method": "POST",'+
								'"content-type": "application/json",'+
								'delay: 250,'+
								'cache: true,'+
								'data: function (params) {'+
									'var query = {'+
										'q: params.term||"",'+
										'pagenum:params.page-1||0,'+
										'pagesize:10,'+
									'};'+
									'query = Object.assign(query, '+query_custom+');'+
									'Object.keys(query).forEach(function(key){if(query[key] instanceof Object){query[key] = JSON.stringify(query[key]);}});'+
									'return query;'+
								'},'+
								'processResults: function (data) {'+
								'return {'+
									'results: data.results.map('+render+'),'+
									'"pagination": {'+
										'"more": !(data.results.length<10||data.results.length==data.totalRows),'+
									'},'+
								'};'+
								'},'+
							'},'+
							(maxItem>1?'maximumSelectionLength: ' + maxItem:"")+
						'});'+
						selected+
					'});'+
				'</script>';
	var a =  '<div style="width: 70%"><select class="js-states form-control" style="width: 100%" id="'+id+'" '+(maxItem>1?'multiple':"")+'></select></div>'+script;
	console.log(a)
	return a;
}

modal.prototype.setting = function(option) {
	this.option = option;
	this._data = option.data;
	this.columns = option.columns;
	this._optionAjax = option.optionAjax||{};
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
		    	el = this._date(column._data, column.edit, column.id, column.defaultValue);
		    	break;
		    case "text":
		    	el = this._text(column._data, column.edit, column.id, column);
				break;
			case "textarea":
		    	el = this._textarea(column._data, column.edit, column.id);
		        break;
		    case "select":
		    	el = this._select(column._data, column.edit, column.id, column.option);
				break;
			case "select_server_side":
		    	el = this._select_server_side(column._data, column.edit, column.id, column.option, column);
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
	var option = {
	    url: _._action.url,
	    type: 'post',
	    datatype:"json",
	};

	option = Object.assign({}, option, this._optionAjax);
	option.data = Object.assign({},_.data(), this._optionAjax.data||{})
	option.success = function(data) {
		if(!!_._action.update && typeof _._action.update === "function") {
			_._action.update(data)
			$([_.id, "#modal-template"].join(" ")).modal('hide');
			closeLoader();
		} else {
			_._updateDefault(data[_._action.fieldDataResult], data.message);
		}
	};
	option.error =  function(err) {
		setTimeout(function() {
			closeLoader();
			alertify.error(JSON.stringify(err));
		}, 500);
		console.log(err);
	}
	$.ajax(option);
}
	
modal.prototype._updateDefault = function(data, message) {
	if(!!this._action.dataTable) {
		var table = this._action.dataTable;
		var _ = this;
		var keys = this._action.keys||[];
		
		var elementIndex = Array.from(table.rows().indexes().data()).findIndex(function(e, index) {
			return keys.reduce(function(acc, curr) {
				return acc&&(e[curr]==data[curr]);
			}, true);
		});
		
		var element = table.rows().indexes().data()[elementIndex];
		
		if(!!element&&(typeof element == "object")) {
			//element = Object.assign(data, element)
			 Object.keys(element).forEach(function(key, index){
				 if(data.hasOwnProperty(key)) {
					 element[key] = data[key]
				 }
			 		
			 })
			
			table.row(elementIndex).data(element).draw();
			$([_.id, "#modal-template"].join(" ")).modal('hide');

	    	setTimeout(function() {
				closeLoader();
				alertify.success(message);
			}, 500);
		} else {
			table.row.add(data).draw();
	    	$([_.id, "#modal-template"].join(" ")).modal('hide');
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
		      	'<form id="'+ [this.id, "form"].join("") +'" action="javascript:void(0);" class="container-fluid">'+this.columns.reduce(function(acc, curr){return acc + curr.html}, "")+'<button id="submit" style="display: none" type="submit">Click Me!</button></form>'+
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
	console.log([this.id,"#modal-template"].join(" "));
	$([this.id,"#modal-template"].join(" ")).ready(function(){
		var maxHeight = parseInt(window.innerHeight*0.6)
		if($(this).height() >= maxHeight) {
	        $('.modal-body').css('max-height', maxHeight);
	    }else{
	        $('.modal-body').css('max-height', '');
	    }
	});
	
	var _ = this;
	$( this.id +" #modal-action" ).click(function() {
		$([_.id, "#submit"].join(" ")).click();
		if(document.getElementById([_.id, "form"].join("")).checkValidity()) {
			if(!!_._action.type&&_._action.type=="custom") {
				_._action.update(_.data());
			} else {
				_.action();		  
			}
		}
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
				case "textarea":
			    	column._data = _._getText("#"+column.id);
			        break;
			    case "select":
			    	column._data = _._getSelect("#"+column.id);
					break;
				case "select_server_side":
			    	column._data = _._getSelect("#"+column.id);
					break;
			    case "custom":
			    	if(typeof column.getData == "function") {
			    		column._data = column.getData("#"+column.id);
			    	} else {
			    		column._data = _._getText("#"+column.id);
			    	}
			    	break;
			    case "render":
			    	if(typeof column.getData == "function") {
			    		column._data = column.getData("#"+column.id);
			    	} else {
			    		column._data = _._getText("#"+column.id);
			    	}
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

