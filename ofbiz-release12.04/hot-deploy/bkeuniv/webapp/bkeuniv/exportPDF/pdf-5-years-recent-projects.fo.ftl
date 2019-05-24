<#escape x as x?xml>

          <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="solid">
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell>
			      	<fo:block font-weight="bold"><#if indexS??>${indexS}</#if>.</fo:block>
			    	</fo:table-cell>
		        <fo:table-cell >
		          <fo:block font-weight="bold">${Topic}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid" 	 border-bottom-style="dotted">
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(10)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(10)"/>
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${SubTopic1}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${TimeStart}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Program}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell >
						<fo:block>${Status1}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				
				<#assign index = 0>
				<#list resultCV.cv.projects_role_director as p>
					<#assign index = index + 1>
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${p.researchProjectProposalName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>
						<#if p.startDate?exists>${p.startDate}</#if> - <#if p.endDate?exists>${p.endDate}</#if>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<#if p.researchProgram?exists>
							<fo:block>${p.researchProgram}</fo:block>
						<#else>
							<fo:block></fo:block>
						</#if>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${p.statusName}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="solid" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${SubTopic2}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${TimeStart}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Program}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${Status2}</fo:block>
					</fo:table-cell>
				</fo:table-row>
				
				<#assign index = 0>
				<#list resultCV.cv.projects_role_member as p>
					<#assign index = index + 1>
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${p.researchProjectProposalName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>
						<#if p.startDate?exists>${p.startDate}</#if> - <#if p.endDate?exists>${p.endDate}</#if>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<#if p.researchProgram?exists>
							<fo:block>${p.researchProgram}</fo:block>
						<#else>
							<fo:block></fo:block>
						</#if>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${p.statusName}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>
						</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
					</fo:table-cell>
				</fo:table-row>	
					
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row> 
     
</#escape>
