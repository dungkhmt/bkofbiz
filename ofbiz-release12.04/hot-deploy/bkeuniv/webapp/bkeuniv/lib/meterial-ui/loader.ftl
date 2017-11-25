<#include "util.ftl"/>
<#macro Loader handleToggle>
    <#local code=random(1, 999999)?string["000000"] />
    <div id="loader-${code}" style="position: fixed; height: 100%; width: 100%; top: 0px; left: -100%; opacity: 0; background-color: rgba(0, 0, 0, 0.05); -webkit-tap-highlight-color: rgba(0, 0, 0, 0); will-change: opacity; transform: translateZ(0px); transition: left 0ms cubic-bezier(0.23, 1, 0.32, 1) 0ms, opacity 400ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1200;">
        <div style="bottom: 0; height: calc(0vw + 0vh); left: 0; margin: auto; position: absolute; right: 0; top: 0; width: calc(0vw + 0vh);">
        	<#nested />
        </div>
        <script>
            ${handleToggle} = {
                isOpen: false,
                open: function() {
                    if(!this.isOpen) {
                        document.getElementById("loader-${code}").style.left = "0px";
                        document.getElementById("loader-${code}").style.opacity = "1";
                    }
                    this.isOpen = true;
                },
                close: function() {
                    if(this.isOpen) {
                        document.getElementById("loader-${code}").style.left = "-100%";
                        document.getElementById("loader-${code}").style.opacity = "0";
                    }
                    this.isOpen = false;
                }
            }
        </script>
    </div>
</#macro>