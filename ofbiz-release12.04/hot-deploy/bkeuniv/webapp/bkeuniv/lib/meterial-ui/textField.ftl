<#include "util.ftl"/>
<style>
    <#--  .textfield-HintText-FloatingLabelText:focus-within div hr:nth-child(2) {
    	transform: scaleX(1)!important;
    }  -->

    <#--  .textfield-HintText-FloatingLabelText input:required:not(:invalid) ~ label{
    	transform: scale(0.75) translate(0px, -28px)!important;
    	color: rgb(0, 188, 212)!important;
    }
    
    .textfield-HintText-FloatingLabelText:focus-within input:required:invalid ~ .placeholder{
    	opacity: 1!important;
    }  -->
    
</style>

<#macro TextField type="text" field="" value="" id="" name="" placeholder="">
    <#local code=random(1, 999999)?string["000000"] />
    <div id="textfield-${code}"
    	class="textfield-HintText-FloatingLabelText"
        style="font-size: 16px; line-height: 24px; width: 100%; height: 72px; display: inline-block; position: relative; background-color: transparent; font-family: Roboto, sans-serif; transition: height 200ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; cursor: auto;">
        <input type="${type}" id="${id}" name="${name}"
            class="FloatingLabelText" value="${value}"
            style="z-index: 100;padding: 0px; position: relative; width: 100%; border: none; outline: none; background-color: rgba(0, 0, 0, 0)!important; color: rgba(0, 0, 0, 0.87); cursor: inherit; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; opacity: 1; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); height: 100%; box-sizing: border-box; margin-top: 14px;" autocomplete="off" required/>
        <label id="textfield-label-${code}"
            style="position: absolute; line-height: 22px; top: 38px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1; transform: scale(1) translate(0px, 0px); transform-origin: left top 0px; pointer-events: none; user-select: none; color: rgba(0, 0, 0, 0.3);">
            ${field}</label>
        <div id="textfield-placeholder-${code}" class="placeholder"
            style="position: absolute; opacity: 0; color: rgba(0, 0, 0, 0.3); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; bottom: 12px;">
            ${placeholder}</div>
        <div>
            <hr aria-hidden="true" class="hr-no-color"
                style="height: 0!important;border-top: none rgb(224, 224, 224); border-left: none rgb(224, 224, 224); border-right: none rgb(224, 224, 224); border-bottom: 1px solid rgb(224, 224, 224); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%;" />
            <hr id="hr-${code}" aria-hidden="true" class="hr-color"
                style="height: 0!important;border-top: none rgb(0, 188, 212); border-left: none rgb(0, 188, 212); border-right: none rgb(0, 188, 212); border-bottom: 2px solid rgb(0, 188, 212); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%; transform: scaleX(0); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;" />
        </div>
    </div>
    <script>
        document.getElementById("textfield-${code}").addEventListener("focusin", function(e) {
            document.getElementById("hr-${code}").style.transform = "scaleX(1)";
            document.getElementById("textfield-label-${code}").style.transform = "scale(0.75) translate(0px, -28px)";
            document.getElementById("textfield-label-${code}").style.color = "rgb(0, 188, 212)";

            var input = Array.from(this.children).find(function(el) {
                return el.tagName == "INPUT"
            })

            if(!!input && !!input.value) {
                document.getElementById("textfield-placeholder-${code}").style.opacity = "0";
            } else {
                document.getElementById("textfield-placeholder-${code}").style.opacity = "1";
            }
        });
        document.getElementById("textfield-${code}").addEventListener("focusout",  function(e) {
            document.getElementById("hr-${code}").style.transform = "scaleX(0)";  
            document.getElementById("textfield-placeholder-${code}").style.opacity = "0";
            var input = Array.from(this.children).find(function(el) {
                return el.tagName == "INPUT"
            })

            if(!!input && !!input.value) {
                document.getElementById("textfield-label-${code}").style.transform = "scale(0.75) translate(0px, -28px)";
                document.getElementById("textfield-label-${code}").style.color = "rgb(0, 188, 212)";
            } else {
                document.getElementById("textfield-label-${code}").style.transform = "scale(1) translate(0px, 0px)";
                document.getElementById("textfield-label-${code}").style.color = "rgba(0, 0, 0, 0.3)";
            }
            
        });

        document.getElementById("textfield-${code}").addEventListener("keyup",  function(e) {
            var input = Array.from(this.children).find(function(el) {
                return el.tagName == "INPUT"
            })
            if(!!input.value) {
                document.getElementById("textfield-placeholder-${code}").style.opacity = "0";
            } else {
                document.getElementById("textfield-placeholder-${code}").style.opacity = "1";
            }
        })
        
    </script>
</#macro>
