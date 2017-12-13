<#include "util.ftl"/>
<#macro SideBar handleToggle="" width="256px" open=false openSecondary=false>
	<#local code=random(1, 999999)?string["000000"] />
	<#local idSideBar="side-bar-"+code />

	<#assign widthW = width?matches(r"(-?\d+)(\w+)")/>
	<#if !widthW>
		<#assign width="256px"/>
		<#assign widthW = width?matches(r"(-?\d+)(\w+)")/>
	</#if>

	<#if openSecondary>
		<#if open>
			<#assign openEl>margin-right: 0px;</#assign>
		<#else>
			<#assign openEl>margin-right: -${widthW?groups[1]?number+10+widthW?groups[2]};</#assign>
		</#if>

		<#assign marginOpen>0px</#assign>

		<#assign marginClose>-${widthW?groups[1]?number+10+widthW?groups[2]}</#assign>

		<#assign style>order: 1;</#assign>

	<#else>
		<#if open>
			<#assign openEl>margin-left: 0px;</#assign>
		<#else>
			<#assign openEl>margin-left: -${widthW?groups[1]?number+10+widthW?groups[2]};</#assign>
		</#if>

		<#assign marginOpen>0px</#assign>

		<#assign marginClose>-${widthW?groups[1]?number+10+widthW?groups[2]}</#assign>

		<#assign style>order: -1;</#assign>
	</#if>
    <div id="${idSideBar}" style="overflow-y: auto; color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: margin 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; flex: 0 0 ${width}; ${style} ${openEl}">
        <div id="content-${code}" style="z-index: 1300; width: ${width};">
            <#nested />
        </div>
        <#if handleToggle!="">
			<script>
				${handleToggle} = {
					isOpen: ${open?string},
					open: function() {
						if(!this.isOpen) {
                            <#if openSecondary>
                                document.getElementById("${idSideBar}").style.marginRight = "${marginOpen}";
                            <#else>
							    document.getElementById("${idSideBar}").style.marginLeft = "${marginOpen}";
                            </#if>
						}
						this.isOpen = true;
					},
					close: function() {
						if(this.isOpen) {
                            <#if openSecondary>
                                document.getElementById("${idSideBar}").style.marginRight = "${marginClose}";
                            <#else>
							    document.getElementById("${idSideBar}").style.marginLeft = "${marginClose}";
                            </#if>
						}
						this.isOpen = false;
					}
				}
			</script>
		</#if>
    </div>
</#macro>
