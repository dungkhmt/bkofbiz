<style>
    .textfield-HintText-FloatingLabelText:focus-within div hr:nth-child(2) {
    	transform: scaleX(1)!important;
    }

    .textfield-HintText-FloatingLabelText input:required:not(:invalid) ~ label{
    	transform: scale(0.75) translate(0px, -28px)!important;
    	color: rgb(0, 188, 212)!important;
    }
    
    .textfield-HintText-FloatingLabelText:focus-within input:required:invalid ~ .placeholder{
    	opacity: 1!important;
    }
    
    .textfield-HintText-FloatingLabelText:focus-within label {
    	transform: scale(0.75) translate(0px, -28px)!important;
    	color: rgb(0, 188, 212)!important;
    }

    hr {
        height: 0!important;
    }

</style>

<#macro TextField type="text" field="" value="" id="" name="" placeholder="">
    <div
    	class="textfield-HintText-FloatingLabelText"
        style="font-size: 16px; line-height: 24px; width: 100%; height: 72px; display: inline-block; position: relative; background-color: transparent; font-family: Roboto, sans-serif; transition: height 200ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; cursor: auto;">
        <input type="${type}" id="${id}" name="${name}"
            class="FloatingLabelText" value="${value}"
            onkeyup="this.setAttribute('value', this.value)"
            style="z-index: 100;padding: 0px; position: relative; width: 100%; border: none; outline: none; background-color: rgba(0, 0, 0, 0)!important; color: rgba(0, 0, 0, 0.87); cursor: inherit; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; opacity: 1; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); height: 100%; box-sizing: border-box; margin-top: 14px;" autocomplete="off" required/>
        <label
            style="position: absolute; line-height: 22px; top: 38px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1; transform: scale(1) translate(0px, 0px); transform-origin: left top 0px; pointer-events: none; user-select: none; color: rgba(0, 0, 0, 0.3);">
            ${field}</label>
        <div class="placeholder"
            style="position: absolute; opacity: 0; color: rgba(0, 0, 0, 0.3); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; bottom: 12px;">
            ${placeholder}</div>
        <div>
            <hr aria-hidden="true" class="hr-no-color"
                style="border-top: none rgb(224, 224, 224); border-left: none rgb(224, 224, 224); border-right: none rgb(224, 224, 224); border-bottom: 1px solid rgb(224, 224, 224); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%;" />
            <hr aria-hidden="true" class="hr-color"
                style="border-top: none rgb(0, 188, 212); border-left: none rgb(0, 188, 212); border-right: none rgb(0, 188, 212); border-bottom: 2px solid rgb(0, 188, 212); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%; transform: scaleX(0); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;" />
        </div>
    </div>
</#macro>

<#assign widthForm>22.5</#assign>
<div style='position: fixed; left: 0; top: 0; right: 0; bottom: 0;display: flex; flex-direction: column; min-height: 100vh; align-items: center; justify-content: center; background-color: rgb(245, 245, 245);'>
    <div style="width: ${widthForm+"%"}; color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; min-width: 300px;">
        <div style="padding-bottom: 0px;">
            <div style="margin: 1em; text-align: center;">
                <div size="60" style="color: rgb(255, 255, 255); background-color: rgb(255, 64, 129); user-select: none; display: inline-flex; align-items: center; justify-content: center; font-size: 30px; border-radius: 50%; height: 60px; width: 60px;">
                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgb(255, 255, 255); fill: rgb(255, 255, 255); height: 36px; width: 36px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; font-size: 36px; margin: 12px;">
                        <path d="M12 17c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm6-9h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zM8.9 6c0-1.71 1.39-3.1 3.1-3.1s3.1 1.39 3.1 3.1v2H8.9V6zM18 20H6V10h12v10z"></path>
                    </svg>
                </div>
            </div>
            <form method="post" action="<@ofbizUrl>login</@ofbizUrl>" name="loginform">
                <div style="padding: 0px 1em 1em;">
                    <@TextField id="username" name="USERNAME" field="Username" />
                    <@TextField type="password" id="password" name="PASSWORD" field="Password" />
                </div>
                <div style="padding: 8px; position: relative;">
                    <div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; display: inline-block; min-width: 100%; margin-right: 8px;">
                        <button tabindex="0" type="submit" value="login" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; width: 100%; border-radius: 2px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; background-color: rgb(0, 188, 212); text-align: center;">
                            <div>
                                <div style="height: 36px; border-radius: 2px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; top: 0px;">
                                    <span style="position: relative; opacity: 1; font-size: 14px; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; margin: 0px; user-select: none; padding-left: 16px; padding-right: 16px; color: rgb(255, 255, 255);">Sign in</span>
                                </div>
                            </div>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div style="position: fixed; left: 50%; display: flex; bottom: 0px; z-index: 2900; visibility: hidden; transform: translate(-50%, 48px); transition: transform 400ms cubic-bezier(0.23, 1, 0.32, 1) 0ms, visibility 400ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;">
        <div width="3" style="font-family: Roboto, sans-serif; background-color: rgba(0, 0, 0, 0.87); padding: 0px 24px; height: 48px; line-height: 48px; border-radius: 2px; max-width: 568px; min-width: 288px; width: auto; flex-grow: 0;">
            <div style="font-size: 14px; color: rgb(255, 255, 255); opacity: 0; transition: opacity 400ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;">
                <span></span>
            </div>
        </div>
    </div>
</div>
