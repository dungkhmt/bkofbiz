<#escape x as x?xml>
     
     <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="solid">
          
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
			
		        <fo:table-cell>
			      	<fo:block font-weight="bold"><#if indexS??>${indexS}</#if>.</fo:block>
			    	</fo:table-cell>

		        <fo:table-cell >
		          <fo:block font-weight="bold">${BussinessProgress}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" text-align = "center"
        border-style="solid" border-top-style="dotted" border-bottom-style="dotted">
          
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${Time}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${PositionBussiness}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Domain}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell >
						<fo:block>${Company}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				
				<#list resultCV.cv.workProgress as wp>
				<fo:table-row height="20pt" border-top-style="dotted" >
				
					<fo:table-cell border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<#if wp.period?exists>
							<fo:block >${wp.period}</fo:block>
						<#else>
							<fo:block ></fo:block>
						</#if>
					</fo:table-cell>
					
					<fo:table-cell border-bottom-style="dotted" />
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<#if wp.position?exists>
							<fo:block >${wp.position}</fo:block>
						<#else>
							<fo:block ></fo:block>
						</#if>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<#if wp.specialization?exists>
							<fo:block >${wp.specialization}</fo:block>
						<#else>
							<fo:block ></fo:block>
						</#if>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-bottom-style="dotted">
						<#if wp.institution?exists>
							<fo:block >${wp.institution}</fo:block>
						<#else>
							<fo:block ></fo:block>
						</#if>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row>
</#escape>
