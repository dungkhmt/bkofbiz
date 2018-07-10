<#include "util.ftl"/>
<#include "button.ftl"/>

<#macro Dropdown id width="120px">
	<#local idDropdown = "dropdown-" + id />
	<#local code=random(1, 999999)?string["000000"] />
	<style>
		#${idDropdown} li:hover {
			background-color: rgba(153,153,153,0.1)!important;
		}
	</style>
	<script>
		function openDropdown${code}() {
			if($("#${idDropdown} ul").css("transform") == "matrix(1, 0, 0, 1, 0, 0)") {
				closeDropdown${code}();
			} else {
				$("#${idDropdown} ul").css("transform", "scale(1)");
			}
		}

		function closeDropdown${code}() {
			$("#${idDropdown} ul").css("transform", "scale(0)");
		}
	</script>

	<#local openDropdown = "openDropdown"+code+"()">
	<#local closeDropdown = "closeDropdown"+code+"()">
	<div id="${idDropdown}" style="position: inherit;">
		<@FlatButton id=id onClick=openDropdown style='color: rgb(0, 188, 212); text-transform: uppercase;width: '+width+'"' onBlur=closeDropdown>
			<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
				<path d="M10 18h4v-2h-4v2zM3 6v2h18V6H3zm3 7h12v-2H6v2z"></path>
			</svg>
			Add Filter
		</@FlatButton>
		<#nested/>
		<#--  <ul style="position: absolute; left: 1px; transition: all 0.3s ease; transform: scale(1); transform-origin: 0 0; box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.16), 0 1px 3px 0 rgba(0, 0, 0, 0.12);">
			<li style="display: block; width: 100%;">
				<div style="width: 100%; padding: 0.6em 14px; display: inline-block; box-sizing: border-box;">
				sadjasdksan
				</div>
			</li>
		</ul>  -->
	</div>
</#macro>

<#macro Ul id="" open=false>
	<ul id="${id}" style="background-color: #fff; position: absolute; left: 1px; transition: all 0.3s ease; transform: <#if open> scale(1) <#else> scale(0) </#if>; transform-origin: 0 0; box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.16), 0 1px 3px 0 rgba(0, 0, 0, 0.12);">
		<#nested/>
	</ul>
</#macro>

<#macro Li id="" onClick="">
	<li id="${id}" onClick='${onClick}' style="display: block; width: 100%; min-width: 150px">
		<div style="width: 100%; padding: 0.6em 14px; display: inline-block; box-sizing: border-box;">
			<#nested/>
		</div>
	</li>
</#macro>

<#macro FormInput id field width="auto" style="">
	<#local code=random(1, 999999)?string["000000"] />
	<style>
		#${id} {
			z-index: 100;
			padding: 0px;
			position: relative;
			width: 100%;
			border: none;
			outline: none;
			background-color: rgba(0, 0, 0, 0)!important;
			color: rgba(0, 0, 0, 0.87);
			font-style: inherit;
			font-variant: inherit;
			font-weight: inherit;
			font-stretch: inherit;
			font-size: inherit; 
			line-height: inherit; 
			font-family: inherit; 
			opacity: 1; 
			-webkit-tap-highlight-color: rgba(0, 0, 0, 0); 
			height: 100%; 
			box-sizing: border-box; 
			margin-top: 14px;
		}
	</style>
	<div id="textfield-${code}"
        style="font-size: 16px; line-height: 24px; width: ${width}; height: 72px; display: inline-block; position: relative; background-color: transparent; font-family: Roboto, sans-serif; transition: height 200ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; cursor: auto; ${style}">
			<#nested/>
        <label id="textfield-label-${id}"
            style="font-size: 13px; font-weight: normal;font-size: 14px;position: absolute; line-height: 22px; top: 38px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1; transform: scale(1) translate(0px, -22px); transform-origin: left top 0px; pointer-events: none; user-select: none; color: rgba(0, 0, 0, 0.3);">
            ${field}</label>
        <div>
            <hr aria-hidden="true" class="hr-no-color"
                style="height: 0!important;border-top: none rgb(224, 224, 224); border-left: none rgb(224, 224, 224); border-right: none rgb(224, 224, 224); border-bottom: 1px solid rgb(224, 224, 224); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%;" />
            <hr id="hr-${id}" aria-hidden="true" class="hr-color"
                style="height: 0!important;border-top: none rgb(0, 188, 212); border-left: none rgb(0, 188, 212); border-right: none rgb(0, 188, 212); border-bottom: 2px solid rgb(0, 188, 212); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%; transform: scaleX(0); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;" />
        </div>
    </div>
	<script>
		document.getElementById("${id}").addEventListener("focusin", function(e) {
			document.getElementById("hr-${id}").style.transform = "scaleX(1)";
			document.getElementById("textfield-label-${id}").style.color = "rgb(0, 188, 212)";
		});

		document.getElementById("${id}").addEventListener("focusout",  function(e) {
            document.getElementById("hr-${id}").style.transform = "scaleX(0)";

            document.getElementById("textfield-label-${id}").style.color = "rgba(0, 0, 0, 0.3)";
        });
	</script>
</#macro>
