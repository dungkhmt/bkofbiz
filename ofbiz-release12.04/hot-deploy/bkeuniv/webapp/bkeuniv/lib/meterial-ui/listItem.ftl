<#include "util.ftl"/>
<style>
    .list-item>span:nth-child(1):hover{
    	background-color: rgba(0, 0, 0, 0.1)!important;
    }
</style>

<#macro ListItem id="" primaryText="" rightIcon="" leftIcon="" disabled=false level=0 initiallyOpen=false open=false linkTo="">

	<#local marginLeft="margin-left: "+level*18+"px;" />

	<#local pathOpen="M12 8l-6 6 1.41 1.41L12 10.83l4.59 4.58L18 14z" />
	<#local pathClose="M16.59 8.59L12 13.17 7.41 8.59 6 10l6 6 6-6z" />

	<#local elOpen="display: none;" />
	<#if initiallyOpen>
		<#local elOpen="" />
	</#if>
	<#assign nested><#nested/></#assign>
	<#local code=random(1, 999999)?string["000000"] />
	<#local id_item="item-" + code />
	
	<#local right_icon_right = "4px"/>
	<#local paddingLeft = 16/>
	<#local paddingRight = 16/>

	<#if rightIcon!="">
		<#local paddingRight = paddingRight + 40/>
	<#else>
	
	</#if>

	<#if leftIcon!="">
		<#local paddingLeft = paddingLeft + 56/>
	<#else>
	
	</#if>

	<#if nested?has_content>
		<#local paddingRight = paddingRight + 44/>
		<#local right_icon_right = "52px"/>
	<#else>
	
	</#if>
	<div class="list-item" id="${id}">
	    <span tabindex="0" style="border: 10px; box-sizing: border-box; display: block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: 16px; font-weight: inherit; position: relative; color: rgba(0, 0, 0, 0.87); line-height: 16px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; background: none;">
		    <div id="${id_item}">
		        <span style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
		        <div style="${marginLeft} padding: 16px ${paddingRight}px 16px ${paddingLeft}px; position: relative;">
		            <#if leftIcon!="">
						<svg viewBox="0 0 24 24" style="display: block; color: rgba(0, 0, 0, 0.87); fill: rgb(117, 117, 117); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; position: absolute; top: 0px; margin: 12px; left: 4px;">
							<path d="${leftIcon}"></path>
						</svg>
					</#if>
					
					<#if nested?has_content>
						<button id='open-${id_item}' tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 12px; outline: none; font-size: 0px; font-weight: inherit; position: absolute; overflow: visible; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; width: 48px; height: 48px; top: 0px; right: 4px; background: none;">
							<div>
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: currentcolor; height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;">
									<path id='icon-${id_item}' d="<#if open>${pathOpen}<#else>${pathClose}</#if>"></path>
								</svg>
							</div>
						</button>
					</#if>
					<#if rightIcon!="">
						<svg viewBox="0 0 24 24" style="display: block; color: rgba(0, 0, 0, 0.87); fill: rgb(117, 117, 117); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; position: absolute; top: 0px; margin: 12px; right: ${right_icon_right};">
							<path d="${rightIcon}"></path>
						</svg>
					</#if>
		            <div>${primaryText}</div>
		        </div>
		    </div>
		</span>
		
		<#if !disabled>
			<script>
				document.getElementById("${id_item}").addEventListener("click", function(e) {
					<#if linkTo=="">
						<#if nested?has_content>
							var item = document.getElementById("children-${code}");
							var icon_item = document.getElementById("icon-${id_item}");
							if(!!item) {
								if(!!item.style.display) {
									item.style.display = "";
									icon_item.setAttribute("d", "${pathOpen}")
								} else {
									item.style.display = "none";
									icon_item.setAttribute("d", "${pathClose}")
								}
							}
						</#if>
					<#else>
						location.href = "${linkTo}";
					</#if>
				});
			</script>
		</#if>
		
		<#if nested?has_content>
			<div id="children-${code}" style="padding: 8px 0px; <#if !open>display: none;</#if>">
				<#nested/>
			</div>
		</#if>
	</div>
	
</#macro>
