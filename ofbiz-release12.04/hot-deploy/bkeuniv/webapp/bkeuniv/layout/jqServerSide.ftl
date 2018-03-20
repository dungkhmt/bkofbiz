<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#macro jqMinimumLib >
	<!-- import jqMinimumLib lib css-->
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/font-awesome.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.default.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/selectize.default.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/bootstrap-datepicker.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dataTables.bootstrap.min.css">
	<link rel="stylesheet" href="/resource/bkeuniv/css/template-modal.css">

	<!-- import jqMinimumLib lib js -->
	<script src="/resource/bkeuniv/js/lib/bootstrap.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery-ui.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.ui-contextmenu.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
	<script src="/resource/bkeuniv/js/lib/selectize.js"></script>
	<script src="/resource/bkeuniv/js/lib/bootstrap-datepicker.js"></script>
	<script src="/resource/bkeuniv/js/lib/jquery.dataTables.min.js"></script>
	
	<script src="/resource/bkeuniv/js/template-modal.js"></script>
</#macro>

<#macro pfArray array depth=0 maxDepth=10>
	[
		<#if depth < maxDepth>
			<#list array as a>
				<#if a?is_hash_ex>
					<@pfObject object=a depth=depth+1/>,
				<#elseif a?is_sequence>
					<@pfArray array=a depth=depth+1/>,
				<#elseif (a?string)?index_of("function")==0>
					${a?replace("\n|\t", "", "r")},
				<#elseif a?is_string>
					'${a?j_string?replace("\n|\t", "", "r")}',
				<#else>
					${a},
				</#if>
			</#list>
		</#if>
	]
</#macro>

<#macro pfObject object depth=0 maxDepth=10>
	{
		<#if depth < maxDepth>
			<#list object?keys as k>
				<#if object[k]?is_hash_ex>
					'${k}': <@pfObject object=object[k] depth=depth+1/>,
				<#elseif object[k]?is_sequence>
					'${k}': <@pfArray array=object[k] depth=depth+1/>,
				<#elseif (object[k]?string)?index_of("function") == 0>
					'${k}': ${object[k]?replace("\n|\t", "", "r")},
				<#elseif object[k]?is_string>
					'${k}': '${object[k]?j_string?replace("\n|\t", "", "r")}',
				<#else>
					'${k}': ${object[k]},
				</#if>
			</#list>
		</#if>
	}
</#macro>


<#macro jqDataTable 
		urlData="" 
		urlUpdate="" 
		urlAdd="" 
		urlDelete="" 
		keysId=[]
		fnInfoCallback=""
		dataFields=[]
		columns=""
		columnsChange = []
		columnsNew=[]
		id="jqDataTable" 
		sizeTable="500"
		bJQueryUI="true"
		fieldDataResult="result"
		titleChange=""
		titleNew=""
		titleDelete=""
		jqTitle=""
		contextmenu=true
	>
	<@jqMinimumLib />
	
	<style>

		th {
			transition: .2s cubic-bezier(0.55, 0.06, 0.68, 0.19);
		}
		
		#${id} .jqDataTable-title {
			padding: 30px 0px 30px 0px;
		}
		
		#${id} .jqDataTable-title .jqDataTable-title-hyperlink {
			font-size: 24px;
		    line-height: 1.35;
		    font-weight: normal;
		    margin-bottom: .5em;
		    padding: 0;
		    border: 0;
		    font: Helvetica, Arial, sans-serif;
		    vertical-align: baseline;
		    
			text-decoration: none;
			color: #000;
			cursor: pointer;
		}
		
		#${id}-content tbody tr:hover td:first-child {
		     border-left: 2px #0014ff solid;			
		}

		.dataTables_scrollHeadInner {
			width: 100%!important;
		}

		.dataTables_scrollHeadInner table {
			width: 100%!important;
		}
		
		
		.ui-menu-item {
		    list-style: none;
		    padding: 5px 10px 5px 10px;
		    
		    padding-left: 2em;
		    position: relative;
		    
		    cursor: pointer;
		}
		
		.ui-menu-item:hover {
			background-color: hsla(0, 0%, 93.3%, .4);
		}
		
		.ui-menu {
			padding: 0;
			margin: 0;
			
			background-color: #fff;
			border-radius: 2px;
			border: 1px solid transparent;
			box-shadow: 0 3px 12px rgba(27,31,35,0.15);
		}
		
		.ui-icon {
			height: 16px;
		    width: 16px;
		    left: 0.4em;
		    top: 0;
		    position: absolute;
		    bottom: 0;
		    margin: auto 0;
		}
		
		#jqDataTable-button-add{
		    position: relative;
		    width: 70px;
		    padding: 6px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #53a7df;
		    color: #fff;
		    text-align: center;
		    border: 1px solid #68b2e3;
		}
		#jqDataTable-button-update{
		    position: relative;
		    width: 70px;
		    padding: 6px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #53a7df;
		    color: #fff;
		    text-align: center;
		    border: 1px solid #68b2e3;
		}
		#jqDataTable-button-remove{
		    position: relative;
		    width: 70px;
		    padding: 6px;
		    border-radius: 5px;
		    box-shadow: 0 1px 1px rgba(255,255,255,.37), 1px 0 1px rgba(255,255,255,.07), 0 1px 0 rgba(0,0,0,.36), 0 -2px 12px rgba(0,0,0,.08);
		    cursor: pointer;
		    margin: 10px 0px;
		    background-color: #FF0000;
		    color: #fff;
		    text-align: center;
		    border: 1px solid #68b2e3;
		}
		#jqDataTable-button-add:hover {
			background-color: #3d9cdb;
		}
		
		.loader {
			background: url('/resource/bkeuniv/image/rolling.gif') 48% 43% no-repeat;
			background-color: rgba(255, 255, 255, 0.7);
			position: fixed;
			left: 0px;
			top: 0px;
			width: 100%;
			height: 100%;
			z-index: 9999;
		}
		
		.hidden-loading {
			background: transparent;
			visibility: hidden;
		}
	
	</style>
	<script type="text/javascript">
		var jqDataTable = new Object();
		jqDataTable.columns = [
			{
				name: "STT",
				orderable: false,
				data: "index"
			}
		];
		<#assign index=0 />
		<#list columns as column>
			<#assign index=index+1>
			var c${index} = {
				name: '${column.name}',
				<#if column.type??>
				type: '${column.type}',
				</#if>
				data: '${column.data}'
			}
			jqDataTable.columns.push(c${index});
		</#list>
		
		$(document).ready(function(){
			document.getElementById("jqTitlePage").innerHTML = titlePage;

            jqDataTable.table = $('#${id}-content').DataTable( {
                "processing": true,
                "serverSide": true,
				"order": [[ 1, "asc" ]],
                "sAjaxSource": "${urlData}",
				searchDelay: 350,
                columns: jqDataTable.columns,
                deferRender: true,
                "columnDefs": [
					<#assign index= 1 />
					<#list columns as column>
					<#if column.type??>
						<#if !column.render??>
						{
							"targets": ${index},
							"render": function ( data, type, row, meta ) {
								return jqDataTable.buildColumn(data, '${column.type}', row, meta);					      
							}
						},
						</#if>
					</#if>
						<#assign index=index+1 />
					</#list>
                <#assign index = 1 />
                <#list columns as column>
                    <#assign c = {} />
                    
                    <#if column.render?has_content>
                            <#assign c = c + {"render": column.render} />
                    </#if>
                    <#if column.width?has_content>
                            <#assign c = c + {"width": column.width} />
                    </#if>
                    
                    <#if c?has_content>
                            <#assign c = c + {"targets": index} />
                            <@pfObject object=c />,
                    </#if>
                    
                    <#assign index = index + 1 />
                </#list>
                ],
                <#if fnInfoCallback?has_content>
                    "fnInfoCallback": ${fnInfoCallback?replace("\n|\t", "", "r")},
                </#if>
                "bJQueryUI": true,
                
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                    <#--  loader.open();  -->
                    console.log(JSON.stringify(sSource), JSON.stringify(aoData), fnCallback)
                    var n_colunm = (aoData.find(function(data) {
                        return data.name=="iColumns";
                    })||{}).value||0;

                    var s_search = (aoData.find(function(data) {
                        return data.name=="sSearch";
                    })||{}).value||"";

                    var n_sort_field = (aoData.find(function(data) {
                        return data.name=="iSortCol_0";
                    })||{}).value||0;

                    n_sort_field = n_sort_field==0?1:n_sort_field;

                    var s_sort_field = (aoData.find(function(data) {
                        return data.name=="mDataProp_"+n_sort_field;
                    })||{}).value;

                    var s_sort_type = (aoData.find(function(data) {
                        return data.name=="sSortDir_0";
                    })||{}).value;

                    var i_start = (aoData.find(function(data) {
                        return data.name=="iDisplayStart";
                    })||{}).value||0;

                    var i_size = (aoData.find(function(data) {
                        return data.name=="iDisplayLength";
                    })||{}).value||0;
                    
                    var page = !i_size?0:i_start/i_size;

                    $.ajax( {
                        "dataType": 'json', 
                        "type": "POST", 
                        "url": sSource, 
                        "data": {
                            "q": s_search,
                            "pagenum" : page.toString(),
                            "pagesize": i_size.toString(),
                            <#--  "filter": '{"field": "staffId", "value": "dung", "operation": "CONTAINS" }',  -->
                            "sort": JSON.stringify([{"field": s_sort_field, "type": s_sort_type}]),
                        },
                        "success": function (reponse) {
                            <#--  setTimeout(function(){ loader.close();}, 500);  -->
                            let data = {
								data: reponse.results,
								recordsFiltered: reponse.totalRows,
								recordsTotal: reponse.totalRows,
                                <#--  aaData: reponse.results,
                                iTotalDisplayRecords: reponse.totalRows,
                                iTotalRecords: reponse.totalRows,
                                recordsTotal: reponse.totalRows,  -->
                            }

                            data.data = reponse.results.map(function(d, index) {
                                d.index = page*i_size + index + 1;
                                return d;
                            })
                            fnCallback(data)
                        }
                    } );
                    
                }
            } );
            <#if contextmenu>
                    $(document).contextmenu({
                        delegate: "#${id}-content td",
                    menu: [
                        {title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
                        {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
                    ],
                    select: function(event, ui) {
                        var el = ui.target.parent();
                        var data = jqDataTable.table.row( el ).data();
                        switch(ui.cmd){
                            case "edit":
                                jqChange(data)
                                break;
                            case "delete":
                                jqDelete(data);
                                break;
                            }
                        },
                        beforeOpen: function(event, ui) {
                            var $menu = ui.menu,
                                $target = ui.target,
                                extraData = ui.extraData;
                            ui.menu.zIndex(9999);
                        }
                        });
                </#if>


			
            <#--  jqDataTable.data = data.${fieldDataResult}.map(function(d, index) {
                    var r = new Object();
                    <#list dataFields as field>
                        r.${field} = d.${field}||"";
                    </#list>		
                    return r;
                })  -->
                
                <#--  jqDataTable.table = $('#${id}-content').DataTable({
                data: jqDataTable.data,
                columns: jqDataTable.columns,
                deferRender: true,
                "columnDefs": [
                {
                    "targets": 0,
                    "render": function ( data, type, row, meta ) {
                        
                        var row = meta.row;
                        if( Object.prototype.toString.call( row ) === '[object Array]' ) {
                            if(row.length > 0) {
                                return row[0] + 1
                            } else {
                                return jqDataTable.data.length
                            }
                        }
                        
                        return meta.row + 1;					      
                    }
                },
                <#assign index = 1 />
                <#list columns as column>
                    <#assign c = {} />
                    
                    <#if column.render?has_content>
                            <#assign c = c + {"render": column.render} />
                    </#if>
                    <#if column.width?has_content>
                            <#assign c = c + {"width": column.width} />
                    </#if>
                    
                    <#if c?has_content>
                            <#assign c = c + {"targets": index} />
                            <@pfObject object=c />,
                    </#if>
                    
                    <#assign index = index + 1 />
                </#list>
                ],
                <#if fnInfoCallback?has_content>
                    "fnInfoCallback": ${fnInfoCallback?replace("\n|\t", "", "r")},
                </#if>
                "bJQueryUI": true
                });
                <#if contextmenu>
                    $(document).contextmenu({
                        delegate: "#${id}-content td",
                    menu: [
                        {title: '${uiLabelMap.BkEunivEdit}', cmd: "edit", uiIcon: "glyphicon glyphicon-edit"},
                        {title: '${uiLabelMap.BkEunivRemove}', cmd: "delete", uiIcon: "glyphicon glyphicon-trash"}
                    ],
                    select: function(event, ui) {
                        var el = ui.target.parent();
                        var data = jqDataTable.table.row( el ).data();
                        switch(ui.cmd){
                            case "edit":
                                jqChange(data)
                                break;
                            case "delete":
                                jqDelete(data);
                                break;
                            }
                        },
                        beforeOpen: function(event, ui) {
                            var $menu = ui.menu,
                                $target = ui.target,
                                extraData = ui.extraData;
                            ui.menu.zIndex(9999);
                        }
                        });
                </#if>  -->
		});
		
		function jqChange(data) {
			new Promise(function(resolve, reject) {
				resolve(new modal("#jqModalChange").setting({
					data: data,
					columns: <@pfArray array=columnsChange />,
			        title: '${titleChange}',
			        action: {
						name: '${uiLabelMap.BkEunivUpdate}',
						url: '${urlUpdate}',
						dataTable: jqDataTable.table,
						keys: <@pfArray array=keysId />,
						fieldDataResult: '${fieldDataResult}',
						hidden: "auto"
					}
				}).render());
			}).then(function(modal) {
				jqDataTable.jqModalChange = modal;
				$("#jqModalChange #modal-template").modal('show');
			})
		}
		
		function jqNew() {
			new Promise(function(resolve, reject) {
				//TODO select
				resolve(new modal("#jqModalAdd").setting({
					data: {},
					columns: <@pfArray array=columnsNew />,
					
			        title: '${titleNew}',
			        action: {
						name: '${uiLabelMap.BkEunivAddRow}',
						url: '${urlAdd}',
						dataTable: jqDataTable.table,
						keys: <@pfArray array=keysId />,
						fieldDataResult: '${fieldDataResult}',
						hidden: "show"
					}
				}).render());
			}).then(function(modal) {
				jqDataTable.jqModalAdd = modal;
				$("#jqModalAdd #modal-template").modal('show');
			})
		}
		
		function jqDelete(data) {
			alertify.confirm("Confirm", '${titleDelete}',
			function(){
				openLoader();
				$.ajax({
				    url: '${urlDelete}',
				    type: 'post',
				    data: data,
				    datatype:"json",
				    success: function(d) {
				    	var table = jqDataTable.table;
				    	if(!!d) {
				    		var element = table.rows().indexes().data().filter(function(e, index) {
								
								var check = <@pfArray array=keysId />.reduce(function(acc, curr) {
									return acc&&(e[curr]==data[curr]);
								}, true);
								
								if(check) {
									e.index = index;
									return true;
								}
							})[0]
							
							if(!!element) {
								table.row(element.index).remove().draw();
							}
							
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(d.result);
				    		}, 500);
				    	} else {
				    		setTimeout(function() {
				    			closeLoader();
				    			alertify.success(JSON.stringify(d));
				    		}, 500);
				    	}
				    },
				    error: function(err) {
				    	setTimeout(function() {
				    		closeLoader();
				    		alertify.success(JSON.stringify(err));
				    	}, 500);
				    	console.log(err);
				    	
				    }
				})
			},
			function(){
			});
		}
		
		function JqRefresh() {
			<#--  loader.open();
			$.ajax({
			    url: "${urlData}",
			    type: 'post',
			    dataType: "json",
			    success: function(data) {
					setTimeout(function(){ loader.close();}, 500);
					jqDataTable.table.clear().draw();
					jqDataTable.data = data.${fieldDataResult}.map(function(d, index) {
			    		var r = new Object();
				    	<#list dataFields as field>
				    		r.${field} = d.${field}||"";
				    	</#list>		
				    	return r;
			    	});
			    	jqDataTable.table.rows.add(jqDataTable.data).draw();
			    }
			});  -->
			setTimeout(function(){ jqDataTable.table.ajax.reload(null, false); }, 100);
		}

		function openLoader() {
			if($(".loader").hasClass("hidden-loading")) {
				$(".loader").removeClass("hidden-loading");
			}
		}
		
		function closeLoader() {
			if(!$(".loader").hasClass("hidden-loading")) {
				$(".loader").addClass("hidden-loading");
			}
		}

		jqDataTable.buildColumn = function(data, type, row, meta) {
			console.log(data, type, row, meta)
			var value = data;
			switch(type) {
				case "date":
					value = parseDate(data);
					break;
				case "datetime":
					value = parseDateTime(data);
					break;
				default:
					value = data;
			}
			return value;
		}

		function parseDate(data) {
			var date = "";
			if(!!data) {
				date = new Date(data).toLocaleDateString('vi');
			}

			return date;
		}

		function parseDateTime(data) {
			var date = "";
			if(!!data) {
				options = {
					year: 'numeric', month: 'numeric', day: 'numeric',
					hour: 'numeric', minute: 'numeric', second: 'numeric',
					hour12: false,
					timeZone: 'Asia/Ho_Chi_Minh' 
				};
				date = new Intl.DateTimeFormat('vi', options).format(new Date(data));
			}

			return date;
		}

		function parseCurrency(data, locales="VND", currency="VND", maximumFractionDigits=2, minimumFractionDigits=2) {
			var price = ""
			if(!!data) {
				price= parseFloat(data).toLocaleString(locales, { style: 'currency', currency: currency, maximumFractionDigits: maximumFractionDigits, minimumFractionDigits: minimumFractionDigits });
			}

			return price;
		}
	
	</script>
	<!-- html -->
	<@Loader handleToggle="loader">
		<@IconSpinner/>
	</@Loader>

	<div id="${id}" style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
		<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
			<div style="display: flex; justify-content: space-between;">
				<div class="title" style="padding: 16px; position: relative;">
					<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
						<span id="jqTitlePage">${titlePage?if_exists}</span>
					</span>
					<span style="font-size: 14px; color: rgba(0, 0, 0, 0.54); display: block;"></span>
				</div>

				<div style="padding: 8px; position: relative; z-index: 2; display: flex; justify-content: flex-end; flex-wrap: wrap;">
					<#if urlAdd!="">
						<@FlatButton id="JqNewRecord" onClick="jqNew()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 100px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"></path>
							</svg>
							${uiLabelMap.BkEunivAdd}
						</@FlatButton>
					</#if>
					
					<@FlatButton id="JqRefresh" onClick="JqRefresh()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 120px">
						<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
							<path d="M17.65 6.35C16.2 4.9 14.21 4 12 4c-4.42 0-7.99 3.58-7.99 8s3.57 8 7.99 8c3.73 0 6.84-2.55 7.73-6h-2.08c-.82 2.33-3.04 4-5.65 4-3.31 0-6-2.69-6-6s2.69-6 6-6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z"></path>
						</svg>
						Làm mới
					</@FlatButton>
				</div>
			</div>
			
			<table id="${id}-content" style="width: 100%!important;" class="table table-striped">
			 <thead>
				<tr>
					<th>
						<@FlatButton id="STT" style="width: 100%; padding-right: 30px; padding-left: 10px;">
							STT
						</@FlatButton>
					</th>
					<#list columns as column>
						<th>
							<@FlatButton id="${column.data}" style="width: 100%; padding-right: 30px; padding-left: 10px;">
								${column.name}
							</@FlatButton>
						</th>
					</#list>
				</tr>
			</thead>
			</table>
		</div>
	</div>
	
	<div class="loader hidden-loading"></div>
	<div id="jqModalAdd"></div>
	<div id="jqModalChange"></div>
	
</#macro>