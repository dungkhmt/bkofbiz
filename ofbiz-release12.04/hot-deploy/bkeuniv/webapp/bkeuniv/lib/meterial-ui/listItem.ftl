<#include "util.ftl"/>
<#include "button.ftl"/>

<#macro ListItem id="" disableTouchRipple=false  primaryText="" rightIcon="" leftIcon="" disabled=false level=0 initiallyOpen=false open=false linkTo="" style="">

	<#local marginLeft="margin-left: "+level*18+"px;" />

	<#local pathOpen="M12 8l-6 6 1.41 1.41L12 10.83l4.59 4.58L18 14z" />
	<#local pathClose="M16.59 8.59L12 13.17 7.41 8.59 6 10l6 6 6-6z" />

	<#local elOpen="display: none;" />
	<#if initiallyOpen>
		<#local elOpen="" />
	</#if>
	<#assign nested><#nested/></#assign>
	<#local code=random(1, 999999)?string["000000"] />
	
	<#if id!="">
		<#local code=id />
	</#if>
	
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

	<style>
    
    <#if !disableTouchRipple&&!disabled>
        #${id_item}:hover {
            background-color: ${hoverColor}!important;
        }
        
        #ripple-container-${code} span {
            transform: scale(0);
            border-radius: 100%;
            position: absolute;
            opacity: 0.75;
            background-color: rgba(153,153,153,0.6);
            animation: ${id_item} 1100ms;
        }

        @-moz-keyframes ${id_item} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }

        @-webkit-keyframes ${id_item} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }

        @-o-keyframes ${id_item} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }

        @keyframes ${id_item} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }
    </#if>
    </style>

	<div class="list-item-${code}" id="${id}">
	    <span tabindex="0" style="border: 10px; box-sizing: border-box; display: block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: 16px; font-weight: inherit; position: relative; color: rgba(0, 0, 0, 0.87); line-height: 16px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; background: none; ${style}">
		    <div id="${id_item}">
		        <span style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
				<span id="ripple-container-${code}" style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
				<div id="container-${id_item}" style="${marginLeft} padding: 16px ${paddingRight}px 16px ${paddingLeft}px; position: relative;">
		            <#if leftIcon!="">
						<svg viewBox="0 0 24 24" style="display: block; color: rgba(0, 0, 0, 0.87); fill: rgb(117, 117, 117); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; position: absolute; top: 0px; margin: 12px; left: 4px;">
							<path d="${leftIcon}"></path>
						</svg>
					</#if>
					
					<#if nested?has_content>
						<#if open>
							<#local iconD=pathOpen/>
						<#else>
							<#local iconD=pathClose/>
						</#if>
						<@IconButton id=("open-" +id_item) style="height: 100%!important;display: block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);margin: 0px; position: absolute; overflow: visible; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;top: 0px; right: 0px; background: none;"  icon=iconD size="50px"/>
						
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
				<#if !disableTouchRipple>
					(function () {
						var cleanUp, debounce, ripple, showRipple;

						debounce = function (func, delay) {
							var inDebounce;
							inDebounce = undefined;
							return function () {
								var args, context;
								context = this;
								args = arguments;
								clearTimeout(inDebounce);
								return inDebounce = setTimeout(function () {
									return func.apply(context, args);
								}, delay);
							};
						};

						showRipple = function (e) {
							var path = e.path;
							for(i = 0; i < path.length; ++i) {
								var p = path[i];
								if(p.id===`open-${id_item}`) {
									return ;
								}
							}
							var pos, ripple, rippler, size, style, x, y;
							ripple = this;
							rippler = document.createElement('span');
							size = ripple.offsetWidth;
							pos = ripple.getBoundingClientRect();

							x = e.pageX - pos.left - (size / 2);
							y = e.pageY - pos.top - (size / 2);
							style = 'top:' + y + 'px; left: ' + x + 'px; height: ' + size + 'px; width: ' + size + 'px;';
							ripple.rippleContainer.append(rippler);
							return rippler.setAttribute('style', style);
						};

						cleanUp = function () {
							while (this.rippleContainer.firstElementChild) {
								this.rippleContainer.firstElementChild.remove();
							}
						};

						var ripple = document.getElementById("${id_item}");
						ripple.addEventListener('mousedown', showRipple);
						ripple.addEventListener('mouseup', debounce(cleanUp, 2500));
						ripple.rippleContainer = document.getElementById("ripple-container-${code}");
					}());
				</#if>

				<#if nested?has_content>
					document.getElementById("open-${id_item}").addEventListener("mouseover", function( event ) {
						event.stopPropagation();
					});

					document.getElementById("open-${id_item}").addEventListener("mouseout", function( event ) {
						event.stopPropagation();
					});
				</#if>
				document.getElementById("container-${id_item}").addEventListener("mouseover", function( event ) {
					document.getElementById("${id_item}").style.backgroundColor = "rgba(0, 0, 0, 0.1)";
				});

				document.getElementById("container-${id_item}").addEventListener("mouseout", function( event ) {
					document.getElementById("${id_item}").style.backgroundColor = "";
				});

				document.getElementById("${id_item}").addEventListener("click", function(e) {
					<#if linkTo=="">
						<#if nested?has_content>
							var item = document.getElementById("children-${code}");
							var icon_item = document.getElementById("open-${id_item}").children[0].children[1].children[0].children[0];
							if(!!item) {
								if(!!item.style.display) {
									setTimeout(function(){ 
										item.style.display = "";
									}, 100);
									
									icon_item.setAttribute("d", "${pathOpen}")
								} else {
									setTimeout(function(){ 
										item.style.display = "none";
									}, 100);
									
									icon_item.setAttribute("d", "${pathClose}")
								}
							}
						</#if>
					<#else>
						location.href = "${linkTo}";
					</#if>
				});
				<#if nested?has_content>
					document.getElementById("open-${id_item}").addEventListener("click", function(e) {
						e.stopPropagation();
						var item = document.getElementById("children-${code}");
						var icon_item = document.getElementById("open-${id_item}").children[0].children[1].children[0].children[0];
						if(!!item) {
							if(!!item.style.display) {
								item.style.display = "";
								icon_item.setAttribute("d", "${pathOpen}")
							} else {
								item.style.display = "none";
								icon_item.setAttribute("d", "${pathClose}")
							}
						}
					})
				</#if>
			</script>
		</#if>
		
		<#if nested?has_content>
			<div id="children-${code}" style="padding: 8px 0px; <#if !open>display: none;</#if>">
				<#nested/>
			</div>
		</#if>
	</div>
	
</#macro>
