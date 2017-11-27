<#include "util.ftl"/>

<#--  backgroundColor id href disableTouchRipple disabled width hoverColor icon label labelPosition labelStyle rippleColor style  -->


<#macro FlatButton id="">
    <#local code=random(1, 999999)?string["000000"] />
    <style>
    .flat-button-${code}:hover {
        background-color: #fff;
    }
    </style>
    <button tabindex="0" class="flat-button-${code}" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(255, 64, 129); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
        <div>
            <span style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; overflow: hidden; pointer-events: none; z-index: 1;"></span>
            <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Secondary</span>
        </div>
    </button>
    
    
</#macro>