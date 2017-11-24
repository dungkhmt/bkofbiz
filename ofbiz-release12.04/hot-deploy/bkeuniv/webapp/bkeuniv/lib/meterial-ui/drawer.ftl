<#include "util.ftl"/>
<#macro Drawer handleToggle="" width="256px" open=false docked=true openSecondary=false>
	<#local code=random(1, 999999)?string["000000"] />
	<#local idDrawer="drawer-"+code />

	<#assign widthW = width?matches(r"(-?\d+)(\w+)")/>
	<#if !widthW>
		<#assign width="256px"/>
		<#assign widthW = width?matches(r"(-?\d+)(\w+)")/>
	</#if>

	<#if openSecondary>
		<#if open>
			<#assign openEl>
				transform:translate(0px, 0px);
			</#assign>
		<#else>
			<#assign openEl>
				transform:translate(0px, -${widthW?groups[1]?number+10+widthW?groups[2]});
			</#assign>
		</#if>

		<#assign transformOpen>translate(0px, 0px)</#assign>

		<#assign transformClose>translate(${widthW?groups[1]?number+10+widthW?groups[2]}, 0px)</#assign>

		<#assign style>right: 0px;left: auto;</#assign>

	<#else>
		<#if open>
			<#assign openEl>transform:translate(0px, 0px);</#assign>
		<#else>
			<#assign openEl>transform:translate(-${widthW?groups[1]?number+10+widthW?groups[2]}, 0px);</#assign>
		</#if>

		<#assign transformOpen>translate(0px, 0px)</#assign>

		<#assign transformClose>translate(-${widthW?groups[1]?number+10+widthW?groups[2]}, 0px)</#assign>

		<#assign style>
			right: auto;left: 0;
		</#assign>
	</#if>

	<div id="${idDrawer}">
		<div id="content-${code}" style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: transform 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 10px, rgba(0, 0, 0, 0.23) 0px 3px 10px; border-radius: 0px; height: 100%; width: ${width}; position: fixed; z-index: 1300; top: 0px; overflow: auto; ${openEl} ${style}">
			<#nested>
		</div>

		<#if !docked>
			<div id="docked-${code}" style="position: fixed; height: 100%; width: 100%; top: 0px; <#if open>left: 0px; opacity: 1;<#else>left: -100%; opacity: 0;</#if> background-color: rgba(0, 0, 0, 0.54); -webkit-tap-highlight-color: rgba(0, 0, 0, 0); will-change: opacity; transform: translateZ(0px); transition: left 0ms cubic-bezier(0.23, 1, 0.32, 1) 0ms, opacity 400ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1200; pointer-events: auto;">
			</div>
		</#if>
		<#if handleToggle!="">
			<script>
				${handleToggle} = {
					isOpen: ${open?string},
					open: function() {
						if(!this.isOpen) {
							document.getElementById("content-${code}").style.transform = "${transformOpen}";
							<#if !docked>
								document.getElementById("docked-${code}").style.left = "0px";
								document.getElementById("docked-${code}").style.opacity = "1";
							</#if>
						}
						this.isOpen = true;
					},
					close: function() {
						if(this.isOpen) {
							document.getElementById("content-${code}").style.transform = "${transformClose}";
							<#if !docked>
								document.getElementById("docked-${code}").style.left = "-100%";
								document.getElementById("docked-${code}").style.opacity = "0";
							</#if>
						}
						this.isOpen = false;
					}
				}
				<#if !docked>
					document.getElementById("docked-${code}").addEventListener("click", function(e) {
						${handleToggle}.close();
					})
				</#if>
			</script>
		</#if>
	</div>
</#macro>
