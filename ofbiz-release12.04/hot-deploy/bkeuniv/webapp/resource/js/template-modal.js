var modal = function (id) {
	this.id = id;
}

modal.prototype.setting = function(option) {
	this.option = option;
	this.data = option.data;
	this.columns = option.columns;

	var _ = this;
	var els = [];
	
	this.columns.forEach(function(column) {
		var name = column.name;
		var value = column.value;
		var type = column.type||"text";
		var edit = column.hasOwnProperty("edit")?column.edit:true;
		
		var el = 
			'<div class="row inline-box">'+
			'<label id="title-modal-input">'+name+'</label>'+
			(edit?'<input type="'+type+'" class="form-control" id="'+value.replace(/\s/g, '-').toLowerCase()+'" value="'+(_['data'][value]||"")+'">':
			'<div id="title-modal-value">'+_['data'][value]+'</div>')+
			'</div>';
		els.push(el);
	})
	this.els = els.join("");
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
		      	'<div class="container-fluid">'+this.els+'</div>'+
		      '</div>'+
		      '<div class="modal-footer">'+
		      	'<button type="button" class="btn btn-success" onclick=\''+this.option.action.func +'(' + JSON.stringify(this.data) +')\' >'+this.option.action.name+'</button>'+
		   		'<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
		      '</div>'+
		    '</div>'+
		  '</div>'+
		'</div>';
	$(this.id).children().remove()
	$(this.id).append(html);
	return this;
}

