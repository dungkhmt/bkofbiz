<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
<#-- Only allow the search fields to be hidden when we have some results -->

<#if key_listShippers?exists>
  <table>
  <tbody>
  
  <#list key_listShippers as I>
  <tr>
  	<td>${I.id}</td>
  	<td>${I.name}</td>
  	<td><a href="deleteShipper?idofshipper=${I.id}">Delete shipper</a></td>
  	<td><a href="viewForUpdateShipper?idofshipper=${I.id}">Change</a></td>
  </tr>
  </#list>
  
  </tbody>
  </table>
<#else>
  <NULL list>
</#if>

    <form action="<@ofbizUrl>createAShipper</@ofbizUrl>" method="post">
        <input type="text" name="shipper-name" value=""/>
        <div class="button-bar">
            <input type="submit" value="${uiLabelMap.CreateShipper}"/>
        </div>
    </form>

<#if parameters.shipperid?exists>
	${parameters.shipperid}
</#if>

