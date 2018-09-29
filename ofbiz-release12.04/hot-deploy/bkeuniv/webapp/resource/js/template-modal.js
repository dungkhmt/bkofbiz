var modal = function (id) {
	this.id = id;
}

function setCustomValidity(id, text){
	var element = $(id)[0];
	if(!element) {
		return;
	}
	element.oninvalid = function(e) {
		e.target.setCustomValidity("");
        if (!e.target.validity.valid) {
            e.target.setCustomValidity(text);
        }
	};
	
	element.oninput = function(e) {
		e.target.setCustomValidity("");
		if (!e.target.validity.valid) {
            e.target.setCustomValidity(text);
        }
	};
}

function scrollToBottom(id) {
	$(id)[0].scrollTo(0, $(id)[0].scrollHeight)
}

function setCustomValidityRequireAndPattern(id, textRequire, textPattern){
	var element = $(id)[0];
	if(!element) {
		return;
	}
	element.oninvalid = function(e) {
		e.target.setCustomValidity("");
        if (!e.target.validity.valid) {
			if(e.target.value=="") {
				e.target.setCustomValidity(textRequire);
			} else {
				e.target.setCustomValidity(textPattern);
			}
        }
	};
	
	element.oninput = function(e) {
        e.target.setCustomValidity("");
        if (!e.target.validity.valid) {
			if(e.target.value=="") {
				e.target.setCustomValidity(textRequire);
			} else {
				e.target.setCustomValidity(textPattern);
			}
        }
	};
}

modal.prototype.progress = function(e){
	var _ = this;
	if(e.lengthComputable){
		var max = e.total;
		var current = e.loaded;

		var Percentage = Math.floor((current * 100)/max);

		if(Percentage >= 100)
		{
			document.getElementById("liner-upload-template").style.width="100%";
			document.getElementById("infor-liner-upload-template").innerHTML=BkEunivLoaded;
			setTimeout(function(){
				$([_.id, "#modal-template"].join(" ")).modal('hide');
				loadingProcess.close();
			}, 300);
		} else {
			document.getElementById("liner-upload-template").style.width=Percentage+"%";
			document.getElementById("infor-liner-upload-template").innerHTML=BkEunivUpload + ' ' + Percentage+"%";
		}
	}
}

modal.prototype._getDate = function (selector, format) {
	var value = $([this.id,selector].join(" ")).datepicker( "getDate" );
	
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

modal.prototype._getDropify = function (selector, multiple) {
	var _id_upload = [this.id,selector].join(" ");
	
	var files = $(_id_upload)[0].files;

	if(files.length == 0) {
		return;
	}

	if(!!multiple) {
		return files;
	} else {
		return files[0];
	}
}

modal.prototype._date = function(column){
	var value = column._data, edit = column.edit, id = column.id, defaultValue=column.defaultValue||"";
	var require = column.require||false;
	var customValidity = column.customValidity||"";
	var _id = "#"+id;
	return '<input type="text" class=" date form-control"' +
					'id="' + id+'"' +
					(edit?"":"disabled ") +
					'value="' + $.datepicker.formatDate('dd/mm/yy', (!value?(!defaultValue?defaultValue:new Date(defaultValue)):new Date(value))) + '"'+
					'placeholder="dd/mm/yyyy" '+
					(require?'required':"")+
					'/>'+
    			'<script type="text/javascript">'+
    				'$(function () {'+
						'$("'+[this.id, _id].join(" ")+'").datepicker({format: "dd/mm/yyyy"});'+
						(require&&!!customValidity?'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ customValidity + '");':"")+
					'});'+
        		'</script>'
}

modal.prototype._text = function(column){
	var value = column._data, edit = column.edit, id = column.id;
	var require = column.require||false;
	var customValidity = column.customValidity||"";
	var _id = "#"+id;

	var setValidity = "";

	if(require&&!!column.pattern) {
		setValidity = 'setCustomValidityRequireAndPattern("'+[this.id, _id].join(" ") +'","'+ customValidity + '","' + column.title + '");'
	} else {
		if(!!column.pattern) {
			setValidity = 'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ column.title + '");';
		} else {
			if(require) {
				setValidity = 'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ customValidity + '");';
			}
		}
	}

	return '<input type="text" class="form-control"' +
					'id="' + id + '"' +
					(!!edit?"":"disabled ") +
					(!column.pattern?"":(' pattern="'+column.pattern+'"')) +
					'value="' + value + '" '+
					(require?'required':"")+
					'/>'+
					(!column.pattern?"":'<div class="input-validation"></div>')+
			'<script type="text/javascript">'+
				'$(function () {'+
					(!!setValidity?setValidity:"")+
				'});'+
			'</script>';
}

modal.prototype._textarea = function(column){
	var value = column._data, edit = column.edit, id =column.id;
	var require = column.require||false;
	var customValidity = column.customValidity||"";
	var _id = "#"+id;
	var setValidity = "";

	if(require&&!!column.pattern) {
		setValidity = 'setCustomValidityRequireAndPattern("'+[this.id, _id].join(" ") +'","'+ customValidity + '","' + column.title + '");'
	} else {
		if(!!column.pattern) {
			setValidity = 'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ column.title + '");';
		} else {
			if(require) {
				setValidity = 'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ customValidity + '");';
			}
		}
	}
	return '<textarea class="form-control"' +
					'id="' + id + '"' +
					(!column.pattern?"":(' pattern="'+column.pattern+'" ')) +
					(edit?"":"disabled ") +
					(require?'required ':"")+
					'>'+value+'</textarea>'+
				(!column.pattern?"":'<div class="input-validation"></div>')+
				'<script type="text/javascript">'+
					'$(function () {'+
					(!!setValidity?setValidity:"")+
					'});'+
				'</script>';
}

modal.prototype._select = function(column) {
	var value = column._data, edit = column.edit, id = column.id, option = column.option;
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
			if(typeof value == "number") {
				result += 'eval(\'' + value + '\')'+dot;
				return;
			}

			if(typeof value == "string") {
				result += 'eval(\'' + value + '\')'+dot;
				return;
			}
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
	var that = this;

	array.forEach(function (value, index, array) {
		var dot = ",";
		
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

modal.prototype._dropify = function(column) {
	var value = column._data, edit = column.edit, id = column.id;
	var _id = "#"+id;
	var render;
	var require = column.require||false;
	var customValidity = column.customValidity||"";
	var script = '$("'+_id+'").dropify({'+
		'messages: {'+
			'default: BkEunivDropifyDefault,'+
			'replace: BkEunivDropifyReplace,'+
			'remove:  BkEunivDropifyRemove,'+
			'error:   BkEunivDropifyError'+
		'}'+
	'}).data("dropify");';
	//value = "http://alex.smola.org/drafts/thebook.pdf";
	var a =  '<input type="file" class="form-control"' +
	'id="' + id + '" ' +
	'class="dropify" ' +
	(!!edit?"":'disabled="disabled" ') +
	(!column.accept?"":(' accept="'+column.accept+'" '))+
	(!column.pattern?"":(' pattern="'+column.pattern+'" title="'+column.title + '" ')) +
	'data-default-file="' + value + '" '+
	(!!column.multiple?"multiple ":'') +
	(require?'required ':"")+
	'/>'+
	'<script type="text/javascript">'+
	script +
	'$(function () {'+
		(require&&!!customValidity?'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ customValidity + '");':"")+
	'});'+
	'</script>';

	
	console.log(a)
	return '<div class="row inline-box" style="display: block;">'+
		'<label id="title-modal-input">'+column.name+'</label>'+a+
	'</div>'
	;
}

modal.prototype._select_server_side = function(column) {
	var value = column._data, edit = column.edit, id = column.id, option = column.option;
	var _id = "#"+id;
	var maxItem = option.maxItem||1;
	var require = column.require||false;
	var customValidity = column.customValidity||"";
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

	var eventChange="";
	if(!!option.eventChange) {
		eventChange= '$("'+[this.id, _id].join(" ")+'").on("change", function(e) {'+
			'var id = e.target.value;'+
			option.eventChange+'(id);'+
		'});'
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
						eventChange+
					'});'+
					'$(function () {'+
						(require&&!!customValidity?'setCustomValidity("'+[this.id, _id].join(" ") +'","'+ customValidity + '");':"")+
					'});'+
				'</script>';
	var a =  '<div style="width: 70%"><select class="js-states form-control" style="width: 100%" id="'+id+'" '+ (require?' required ':"")+ +(maxItem>1?'multiple':"")+'></select></div>'+script;
	
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
		var require = column.require||false;
		var customValidity = column.customValidity||"";
		var el;
		switch(column.type) {
		    case "date":
		    	el = this._date(column);
		    	break;
		    case "text":
		    	el = this._text(column);
				break;
			case "textarea":
		    	el = this._textarea(column);
		        break;
		    case "select":
		    	el = this._select(column);
				break;
			case "select_server_side":
		    	el = this._select_server_side(column);
				break;
			case "dropify":
				column.html = this._dropify(column);
				this._formData=true;
				break;
		    case "custom":
		    	el = column.el;
		    	break;
		    case "render":
		    	column.html = column.render(column._data, column.name, this._data, column.id);
		    	break;
		}
			column.html = column.html||'<div class="row inline-box">'+
				'<label id="title-modal-input">'+column.name + (require&&!!customValidity?' <span style="color: #db4437;" title="Câu hỏi bắt buộc">*</span>':"") + '</label>'+el+
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

	if(!!_._formData) {
		option.contentType = false;
		option.processData = false;

		var formData = new FormData();
		Object.keys(option.data).forEach(function(key){
			var value = option.data[key];
			formData.append(key, value);
		})

		option.data = formData;
		
		option.xhr = function() {
			var myXhr = $.ajaxSettings.xhr();
			if(myXhr.upload){
				loadingProcess.open();
				myXhr.upload.addEventListener('progress',_.progress, false);
				closeLoader();
			}
			console.log(myXhr)
			return myXhr;
		}
	}

	option.success = function(data) {
		if(!!_._formData) {
			alertify.success(BkEunivLoaded);
		}

		if(!!_._action.update && typeof _._action.update === "function") {
			_._action.update(data)
			$([_.id, "#modal-template"].join(" ")).modal('hide');
			setTimeout(function(){ closeLoader(); }, 300);
			
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
				  '<form id="'+ [this.id.replace('#', ''), "form"].join("") +'" action="javascript:void(0);" class="container-fluid">'+this.columns.reduce(function(acc, curr){return acc + curr.html}, "")+'<button id="submit" style="display: none" type="submit">Click Me!</button></form>'+
				  '<div id="floating-action-button-576574" onClick=\'scrollToBottom("#jqModalAdd .modal-body")\' style="margin: auto; color: rgb(255, 255, 255); background-color: transparent; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 10px, rgba(0, 0, 0, 0.23) 0px 3px 10px; border-radius: 50%; display: inline-block;position: fixed;left: 50%;margin-left: -20px;">'+
					'<button id="" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; vertical-align: bottom; background-color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; height: 40px; width: 40px; overflow: hidden; border-radius: 50%; text-align: center;">'+
						'<div>'+
							'<span style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>'+
							'<div style="transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; top: 0px;">'+
								'<svg viewBox="0 0 24 24" style="display: inline-block; color: rgb(255, 255, 255); fill: rgb(255, 255, 255); height: 40px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; line-height: 40px;">'+
									'<path d="M20 12l-1.41-1.41L13 16.17V4h-2v12.17l-5.58-5.59L4 12l8 8 8-8z"></path>'+
								'</svg>'+
							'</div>'+
						'</div>'+
					'</button>'+
				'</div>'+
		      '</div>'+
		      '<div class="modal-footer">'+
		      	'<button type="button" class="btn btn-success" id="modal-action">'+(this._action.name||"Action")+'</button>'+
		   		'<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
		      '</div>'+
		    '</div>'+
		  '</div>'+
		'</div>'+
		'<script>'+
		'$("'+[this.id,"#modal-template"].join(" ")+'").ready(function(){'+
			'setTimeout(function(){'+
				'var that = this;'+
				'var maxHeight = parseInt(window.innerHeight*0.6);'+
				'if($(that).height() >= maxHeight) {'+
					'$(".modal-body").css("max-height", maxHeight);'+
				'}else{'+
					'$(".modal-body").css("max-height", "");'+
				'}'+
				'var element = $("'+[this.id,".modal-body"].join(" ")+'")[0];'+
				'if(element.scrollHeight - element.clientHeight - element.scrollTop > 30) {'+
					'$("'+[this.id,"#floating-action-button-576574"].join(" ")+'").css("display", "block");'+
				'} else {'+
					'$("'+[this.id,"#floating-action-button-576574"].join(" ")+'").css("display", "none");'+
				'}'+
				'$("'+[this.id,"#floating-action-button-576574"].join(" ")+'").css("top", $("'+[this.id, ".modal-header"].join(" ")+'").position().top+$("'+[this.id, ".modal-body"].join(" ")+'").height()+ 70);'+
			'}, 200);'+
			'});'+
		'</script>';
	$(this.id).children().remove()
	$(this.id).append(html);
	$([this.id,".modal-body"].join(" ")).scroll(function(e) {
		var element = e.target;
		if(element.scrollHeight - element.clientHeight - element.scrollTop > 30) {
			$([this.id,"#floating-action-button-576574"].join(" ")).css("display", "block");
		} else {
			$([this.id,"#floating-action-button-576574"].join(" ")).css("display", "none");
		}
	})
	var _ = this;
	$( this.id +" #modal-action" ).click(function() {
		$([_.id, "#submit"].join(" ")).click();
		if(document.getElementById([_.id.replace('#', ''), "form"].join("")).checkValidity()) {
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
				case "dropify":
			    	column._data = _._getDropify("#"+column.id, column.multiple);
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

	keys = Object.keys(base);
	var key;
	
	for (var i = 0, len = keys.length; i < len; i++) {
		key = keys[i];
		if(base[key]==undefined) {
			delete base[key];
		}
	}

	return base
}

