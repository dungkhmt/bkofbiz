<#include "util.ftl"/>

<#--  backgroundColor id href disableTouchRipple disabled width hoverColor icon label labelPosition labelStyle rippleColor style  -->


<#macro FlatButton style="" id="" type="" color="rgba(0, 0, 0, 0.87)" backgroundColor="rgba(0, 0, 0, 0)" disableTouchRipple=false disabled=false hoverColor="rgba(153,153,153,0.2)" href="">
    <#local code=random(1, 999999)?string["000000"] />

    <#switch type>
        <#case "default">
            <#local color="rgba(0, 0, 0, 0.87)" />
            <#break>
        <#case "primary">
            <#local color="rgb(0, 188, 212)" />
            <#break>
        <#case "secondary">
            <#local color="rgb(255, 64, 129)" />
            <#break>
        <#case "disabled">
            <#local color="rgba(0, 0, 0, 0.3)" />
            <#local disabled=true />
            <#break>
    </#switch>

    <style>
    
    <#if !disableTouchRipple&&!disabled>
        #ripple-${code}:hover {
            background-color: ${hoverColor}!important;
        }
        
        #ripple-container-${code} span {
            transform: scale(0);
            border-radius: 100%;
            position: absolute;
            opacity: 0.75;
            background-color: rgba(153,153,153,0.6);
            animation: ripple-${code} 1100ms;
        }

        @-moz-keyframes ripple-${code} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }

        @-webkit-keyframes ripple-${code} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }

        @-o-keyframes ripple-${code} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }

        @keyframes ripple-${code} {
            to {
                opacity: 0;
                transform: scale(2);
            }
        }
    </#if>
    </style>

    <button id="${id}" tabindex="0" class="flat-button-${code}" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: <#if disabled>default<#else>pointer</#if>; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; line-height: 36px; min-width: 88px; color: ${color}; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: ${backgroundColor}; text-align: center;${style}">
        <div id="ripple-${code}">
            <span id="ripple-container-${code}" style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
            <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; font-weight: 500; font-size: 14px;">
                <#nested/>
            </span>
        </div>
    </button>
    <script>
        <#if href!="">
            document.getElementById("ripple-container-${code}").parentElement.addEventListener('click', function(){
                setTimeout(function(){ 
                    window.location.href = "${href}";
                }, 100);
            });
        </#if>
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

                var ripple = document.getElementById("ripple-${code}");
                ripple.addEventListener('mousedown', showRipple);
                ripple.addEventListener('mouseup', debounce(cleanUp, 2500));
                ripple.rippleContainer = document.getElementById("ripple-container-${code}");
            }());
        </#if>
    </script>
</#macro>

<#macro RaisedButton style="" id="" type="default" color="rgba(0, 0, 0, 0.87)" backgroundColor="rgb(255, 255, 255)" disableTouchRipple=false disabled=false hoverColor="rgba(255,255,255,0.4)" href="">
    <#local code=random(1, 999999)?string["000000"] />
    <#switch type>
        <#case "default">
            <#local color="rgba(0, 0, 0, 0.87)" />
            <#local backgroundColor="rgb(255, 255, 255)" />
            <#local hoverColor="rgba(0,0,0,0.08)" />
            <#break>
        <#case "primary">
            <#local color="rgb(255, 255, 255)" />
            <#local backgroundColor="rgb(0, 188, 212)" />
            <#break>
        <#case "secondary">
            <#local color="rgb(255, 255, 255)" />
            <#local backgroundColor="rgb(255, 64, 129)" />
            <#break>
        <#case "disabled">
            <#local color="rgba(0, 0, 0, 0.3)" />
            <#local backgroundColor="rgb(229, 229, 229)" />
            <#local disabled=true />
            <#break>
    </#switch>
    <div id="raised-button-${code}" style="transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); <#if !disabled>box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px</#if>; border-radius: 2px; display: inline-block;">
        <@FlatButton id=id type="RaisedButton" style=style hoverColor=hoverColor disableTouchRipple=disableTouchRipple href=href color=color backgroundColor=backgroundColor disabled=disabled>
            <#nested/>
        </@FlatButton>
    </div>
</#macro>

<#macro FloatingActionButton icon style="" mini=false size="56px" id="" type="default" disabled=false hoverColor="rgba(255,255,255,0.3)" href="">
    <#local code=random(1, 999999)?string["000000"] />
    <#switch type>
        <#case "default">
            <#local color="rgba(0, 0, 0, 0.87)" />
            <#local backgroundColor="rgb(255, 255, 255)" />
            <#break>
        <#case "primary">
            <#local color="rgb(255, 255, 255)" />
            <#local backgroundColor="rgb(0, 188, 212)" />
            <#break>
        <#case "secondary">
            <#local color="rgb(255, 255, 255)" />
            <#local backgroundColor="rgb(255, 64, 129)" />
            <#break>
        <#case "disabled">
            <#local color="rgba(0, 0, 0, 0.3)" />
            <#local backgroundColor="rgb(229, 229, 229)" />
            <#local disabled=true />
            <#break>
    </#switch>

    <#if mini>
        <#local size="40px" />
    </#if>

    <style>
        <#if !disabled>
            #floating-action-button-${code}:active {
                box-shadow: rgba(0, 0, 0, 0.19) 0px 10px 30px, rgba(0, 0, 0, 0.23) 0px 6px 10px!important;
            }

            #floating-action-button-${code}:hover button div span {
                background-color: ${hoverColor};
            }
        </#if>
    </style>
    <div id="floating-action-button-${code}" style="color: ${color}; background-color: transparent; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); <#if !disabled>box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 10px, rgba(0, 0, 0, 0.23) 0px 3px 10px; </#if>border-radius: 50%; display: inline-block;">
        <button id="${id}" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: <#if disabled>default<#else>pointer</#if>; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; vertical-align: bottom; background-color: ${backgroundColor}; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; height: ${size}; width: ${size}; overflow: hidden; border-radius: 50%; text-align: center;${style}">
            <div>
                <span style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
                <div style="transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; top: 0px;">
                    <svg viewBox="0 0 24 24" style="display: inline-block; color: ${color}; fill: ${color}; height: ${size}; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; line-height: ${size};">
                        <path d="${icon}"></path>
                    </svg>
                </div>
            </div>
        </button>
    </div>
    <script>
        <#if href!="">
            document.getElementById("floating-action-button-${code}").addEventListener('click', function(){
                setTimeout(function(){ 
                    window.location.href = "${href}";
                }, 100);
            });
        </#if>
    </script>
</#macro>



