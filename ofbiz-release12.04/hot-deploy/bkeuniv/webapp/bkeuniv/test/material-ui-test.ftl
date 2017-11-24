<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<style>
	body {
		margin: 0px!important;
	}
</style>
<#--  
<@TextField />
<@ListItem primaryText="Test">
    <@ListItem primaryText="Test" level=1/>
</@ListItem>  -->
<@Body>
	<@IconSpinner/>
	<@SideBar open=true handleToggle="menu" openSecondary=true>
		<@List>
			<@ListItem primaryText="Test">
	        <@ListItem primaryText="Test" level=1 linkTo=""/>
	        <@ListItem primaryText="Test" level=1/>
	        <@ListItem primaryText="Test" level=1/>
	        <@ListItem primaryText="Test" level=1/>
			</@ListItem>
			<@ListItem primaryText="Menu2">
				<@ListItem primaryText="Test" level=1/>
			</@ListItem>
		</@List>
	</@SideBar>
	
	<!-- Test body -->
	<div style="flex: 1 1 0%; padding: 2em;">
	    <div class="list-page">
	        <div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;">
	            <div style="padding-bottom: 0px;">
	                <div style="display: flex; justify-content: space-between;">
	                    <div class="title" style="padding: 16px; position: relative;">
	                        <span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
	                            <span>Danh sách tài xế</span>
	                        </span>
	                        <span style="font-size: 14px; color: rgba(0, 0, 0, 0.54); display: block;"></span>
	                    </div>
	                    <div style="padding: 8px; position: relative; z-index: 2; display: flex; justify-content: flex-end; flex-wrap: wrap;">
	                        <button tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                            <div>
	                                <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                    <path d="M17.65 6.35C16.2 4.9 14.21 4 12 4c-4.42 0-7.99 3.58-7.99 8s3.57 8 7.99 8c3.73 0 6.84-2.55 7.73-6h-2.08c-.82 2.33-3.04 4-5.65 4-3.31 0-6-2.69-6-6s2.69-6 6-6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z"></path>
	                                </svg>
	                                <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Làm mới</span>
	                            </div>
	                        </button>
	                    </div>
	                </div>
	                <div>
	                    <div style="padding: 0px 16px 16px; font-size: 14px; color: rgba(0, 0, 0, 0.87); margin-top: -14px; display: flex; justify-content: flex-end; align-items: flex-end; flex-wrap: wrap;">
	                        <div data-source="q" class="filter-field" style="display: flex; align-items: flex-end;">
	                            <div style="width: 48px;">&nbsp;</div>
	                            <div>
	                                <div style="font-size: 16px; line-height: 24px; width: 256px; height: 72px; display: inline-block; position: relative; background-color: transparent; font-family: Roboto, sans-serif; transition: height 200ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; cursor: auto;">
	                                    <label for="q-undefined-objectObject-16253" style="position: absolute; line-height: 22px; top: 38px; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1; transform: scale(1) translate(0px, 0px); transform-origin: left top 0px; pointer-events: none; user-select: none; color: rgba(0, 0, 0, 0.3);">
	                                        <span>Search</span>
	                                    </label>
	                                    <input type="text" name="q" value="" id="q-undefined-objectObject-16253" style="padding: 0px; position: relative; width: 100%; border: none; outline: none; background-color: rgba(0, 0, 0, 0); color: rgba(0, 0, 0, 0.87); cursor: inherit; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; opacity: 1; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); height: 100%; box-sizing: border-box; margin-top: 14px;">
	                                    <div>
	                                        <hr aria-hidden="true" style="border-top: none rgb(224, 224, 224); border-left: none rgb(224, 224, 224); border-right: none rgb(224, 224, 224); border-bottom: 1px solid rgb(224, 224, 224); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%;">
	                                        <hr aria-hidden="true" style="border-top: none rgb(0, 188, 212); border-left: none rgb(0, 188, 212); border-right: none rgb(0, 188, 212); border-bottom: 2px solid rgb(0, 188, 212); bottom: 8px; box-sizing: content-box; margin: 0px; position: absolute; width: 100%; transform: scaleX(0); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms;">
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                    <div style="clear: right;"></div>
	                </div>
	                <div>
	                    <div style="height: auto; overflow: auto;">
	                        <div style="height: inherit; overflow-x: hidden; overflow-y: auto;">
	                            <table style="background-color: rgb(255, 255, 255); width: 100%; border-collapse: collapse; border-spacing: 0px; table-layout: auto; font-family: Roboto, sans-serif;">
	                                <thead style="border-bottom: 1px solid rgb(224, 224, 224);">
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px; border-top-color: rgb(224, 224, 224); border-right-color: rgb(224, 224, 224); border-left-color: rgb(224, 224, 224);">
	                                        <th style="font-weight: normal; font-size: 12px; padding: 0px 0px 0px 12px; height: 56px; text-align: left; white-space: nowrap; text-overflow: ellipsis; color: rgb(158, 158, 158); position: relative;">
	                                            <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">
	                                                <span>STT</span>
	                                            </span>
	                                        </th>
	                                        <th style="font-weight: normal; font-size: 12px; padding: 0px; height: 56px; text-align: left; white-space: nowrap; text-overflow: ellipsis; color: rgb(158, 158, 158); position: relative;">
	                                            <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">
	                                                <span>Họ và Tên</span>
	                                            </span>
	                                        </th>
	                                        <th style="font-weight: normal; font-size: 12px; padding: 0px; height: 56px; text-align: left; white-space: nowrap; text-overflow: ellipsis; color: rgb(158, 158, 158); position: relative;">
	                                            <button data-sort="gender" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 40px; color: rgba(0, 0, 0, 0.87); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">
	                                                        <span>Giới tính</span>
	                                                    </span>
	                                                </div>
	                                            </button>
	                                        </th>
	                                        <th style="font-weight: normal; font-size: 12px; padding: 0px; height: 56px; text-align: left; white-space: nowrap; text-overflow: ellipsis; color: rgb(158, 158, 158); position: relative;">
	                                            <button data-sort="birthDate" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 40px; color: rgba(0, 0, 0, 0.87); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">
	                                                        <span>Ngày sinh</span>
	                                                    </span>
	                                                </div>
	                                            </button>
	                                        </th>
	                                        <th style="font-weight: normal; font-size: 12px; padding: 0px; height: 56px; text-align: left; white-space: nowrap; text-overflow: ellipsis; color: rgb(158, 158, 158); position: relative;">
	                                            <button data-sort="socialSecurityNumber" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 40px; color: rgba(0, 0, 0, 0.87); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">
	                                                        <span>Số CMND</span>
	                                                    </span>
	                                                </div>
	                                            </button>
	                                        </th>
	                                        <th style="font-weight: normal; font-size: 12px; padding: 0px; height: 56px; text-align: left; white-space: nowrap; text-overflow: ellipsis; color: rgb(158, 158, 158); position: relative;">
	                                            <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">
	                                                <span></span>
	                                            </span>
	                                        </th>
	                                    </tr>
	                                </thead>
	                                <tbody class="datagrid-body">
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>1</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/2" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>2</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402881435f02171b015f02176d6f0000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>3</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402880b65f057301015f057329930000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>4</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402880b65f0592a2015f05931ae80000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>5</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402880b65f0596af015f0596fe660000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>6</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402880b65f059f23015f059f49810000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>7</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402880b65f05a152015f05a737ab0001" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>8</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402880b65f05f264015f05f75e930001" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="border-bottom: 1px solid rgb(224, 224, 224); color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>9</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402881435f07096f015f070a6e440000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                    <tr style="color: rgba(0, 0, 0, 0.87); height: 48px;">
	                                        <td class="column-undefined" style="padding: 0px 12px 0px 16px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>10</span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span>Nguyen Chi PHEO</span>
	                                        </td>
	                                        <td class="column-gender" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-birthDate" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-socialSecurityNumber" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <span></span>
	                                        </td>
	                                        <td class="column-undefined" style="padding: 0px 12px; height: 48px; text-align: left; font-size: 13px; overflow: hidden; white-space: normal; text-overflow: ellipsis; background-color: inherit;">
	                                            <a tabindex="0" href="#/drivers/402881435f08dff3015f08e0856e0000" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: inherit; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                                <div>
	                                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 12px; margin-right: 0px;">
	                                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
	                                                    </svg>
	                                                    <span style="position: relative; padding-left: 8px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Sửa</span>
	                                                </div>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                    <div style="box-sizing: border-box; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); background-color: rgb(232, 232, 232); height: 56px; padding: 0px 24px; display: flex; justify-content: space-between;">
	                        <div style="position: relative; margin-left: -24px; display: flex; justify-content: space-between; align-items: center;">
	                            <span class="displayed-records" style="padding: 1.2em;">1-10 của 36</span>
	                        </div>
	                        <div style="position: relative; display: flex; justify-content: space-between; align-items: center;">
	                            <button class="page-number" data-page="1" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 10px 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgba(0, 0, 0, 0.87); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                <div>
	                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">1</span>
	                                </div>
	                            </button>
	                            <button class="page-number" data-page="2" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 10px 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                <div>
	                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">2</span>
	                                </div>
	                            </button>
	                            <button class="page-number" data-page="3" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 10px 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                <div>
	                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">3</span>
	                                </div>
	                            </button>
	                            <button class="page-number" data-page="4" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 10px 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                <div>
	                                    <span style="position: relative; padding-left: 16px; padding-right: 16px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">4</span>
	                                </div>
	                            </button>
	                            <button class="next-page" tabindex="0" type="button" style="border: 10px; box-sizing: border-box; display: inline-block; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); cursor: pointer; text-decoration: none; margin: 10px 0px; padding: 0px; outline: none; font-size: inherit; font-weight: inherit; position: relative; height: 36px; line-height: 36px; min-width: 88px; color: rgb(0, 188, 212); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; border-radius: 2px; user-select: none; overflow: hidden; background-color: rgba(0, 0, 0, 0); text-align: center;">
	                                <div>
	                                    <span style="position: relative; padding-left: 16px; padding-right: 8px; vertical-align: middle; letter-spacing: 0px; text-transform: uppercase; font-weight: 500; font-size: 14px;">Tiếp</span>
	                                    <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 12px;">
	                                        <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
	                                    </svg>
	                                </div>
	                            </button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</@Body>	
