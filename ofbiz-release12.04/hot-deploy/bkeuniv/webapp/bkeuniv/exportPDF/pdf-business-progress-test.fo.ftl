<#escape x as x?xml>

<#list resultCV.cv.workProgress as wp>
				<fo:table-row height="20pt" border-top-style="dotted" >
					<fo:table-cell border-bottom-style="dotted">
						<#if wp.institution?exists>
							<fo:block >${wp.institution}</fo:block>
						<#else>
							<fo:block ></fo:block>
						</#if>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
</#escape>				