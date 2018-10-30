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
		          <fo:block font-weight="bold">${ExperienceSuccess}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			
			
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${FirstAndLastName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(uiLabelMap.PhDCoSupervision)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(uiLabelMap.PhDTheSisName)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-aline = "center	" >
						<fo:block>${YearSuccess}</fo:block>
					</fo:table-cell>
					
					
				</fo:table-row>	

				<#assign phdSequenceId = 0 />
				<#list listPhdStudentSupervision as phdStudentSupervision>
				<#assign phdSequenceId = phdSequenceId+1 />
				 <fo:table-row height="20pt" border-top-style="dotted" >
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${phdSequenceId}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(phdStudentSupervision.studentName?if_exists)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					<#if phdStudentSupervision.coSupervion == "YES">
						<fo:block>${StringUtil.wrapString(uiLabelMap.CommonYes?if_exists)}</fo:block>
					<#else>
						<fo:block>${StringUtil.wrapString(uiLabelMap.CommonNo?if_exists)}</fo:block>
					</#if>
						
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${StringUtil.wrapString(phdStudentSupervision.thesisName?if_exists)}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell>
						<fo:block>${phdStudentSupervision.graduateYear?if_exists}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				</#list>
				
				<fo:table-row height="20pt" border-top-style="dotted">
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
					</fo:table-cell>
					<fo:table-cell />
					<fo:table-cell text-aline = "center"></fo:table-cell>
				</fo:table-row>	
				
			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row> 
</#escape>
