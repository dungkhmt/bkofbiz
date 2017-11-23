<#include "util.ftl"/>
<style>
    .list-item>span:nth-child(1):hover{
    	background-color: rgba(0, 0, 0, 0.1)!important;
    }
</style>

<#macro ListItem primaryText="" rightIcon="" leftIcon="" disabled=false level=0 initiallyOpen=false open="">

	<#local marginLeft=level*18 />
	<#local paddingTB=level*8 />

	<#local elOpen="display: none;" />
	<#if initiallyOpen>
		<#local elOpen="" />
	</#if>
	<#assign nested><#nested/></#assign>
	<#local code=random(1, 999999)?string["000000"] />
	<#local id_item="item-" + code />
	<div class="list-item" style="padding: ${paddingTB}px 0px;${open}">
	    <span tabindex="0" style="border: 10px; box-sizing: border-box; display: block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: 16px; font-weight: inherit; position: relative; color: rgba(0, 0, 0, 0.87); line-height: 16px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; background: none;">
		    <div id="${id_item}">
		        <span style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
		        <div style="margin-left: ${marginLeft}px; padding: 16px 16px 16px 72px; position: relative;">
		            <svg viewBox="0 0 24 24" style="display: block; color: rgba(0, 0, 0, 0.87); fill: rgb(117, 117, 117); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; position: absolute; top: 0px; margin: 12px; left: 4px;">
		                <path d="M19 3H4.99c-1.11 0-1.98.89-1.98 2L3 19c0 1.1.88 2 1.99 2H19c1.1 0 2-.9 2-2V5c0-1.11-.9-2-2-2zm0 12h-4c0 1.66-1.35 3-3 3s-3-1.34-3-3H4.99V5H19v10z"></path>
		            </svg>
					<#if nested?has_content>
						<button id='open-${id_item}'tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 12px; outline: none; font-size: 0px; font-weight: inherit; position: absolute; overflow: visible; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; width: 48px; height: 48px; top: 0px; right: 4px; background: none;">
							<div>
								<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: currentcolor; height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;">
									<path d="M12 8l-6 6 1.41 1.41L12 10.83l4.59 4.58L18 14z"></path>
								</svg>
							</div>
						</button>
					</#if>
		            <div>${primaryText}</div>
		        </div>
		    </div>
		</span>
		<#nested/>
	</div>
	<script>
		<#if !disabled>
			document.getElementById("${id_item}").addEventListener("click", function(e) {
				var child_items = Array.from(this.parentElement.parentElement.children).find(function(el){
					return el.className=="list-item"
				})
				if(!!child_items) {
					if(!!child_items.style.display) {
						child_items.style.display = "";
					} else {
						child_items.style.display = "none";
					}
				}
			});
		</#if>
	</script>
</#macro>
