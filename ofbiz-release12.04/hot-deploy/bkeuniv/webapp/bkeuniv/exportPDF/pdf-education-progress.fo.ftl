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
			      	<fo:block font-weight="bold">${stt}.</fo:block>
			    	</fo:table-cell>
			    	<#assign stt=stt+1 />

		        <fo:table-cell>
		          <fo:block font-weight="bold">${EducationProgress}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid" border-top-style="dotted" border-bottom-style="dotted">
          
          //Full name
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
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block >${EducationType}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${Institution}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${Speciality}</fo:block>
					</fo:table-cell>		
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-bottom-style="dotted">
						<fo:block>${GraduateDate}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<#assign index = 0>
				<#assign cv = resultCV.cv>
		      	<#list cv.educationProgress as eduProg>
					<#assign index = index+1>			        
			    	<fo:table-row height="20pt" >
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block >${eduProg.educationType}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block>${eduProg.institution}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block>${eduProg.speciality}</fo:block>
						</fo:table-cell>		
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell  border-bottom-style="dotted">
							<fo:block>${eduProg.graduateDate}</fo:block>
						</fo:table-cell>
					</fo:table-row>	
				  
				</#list>

			</fo:table-body>
		</fo:table>
       </fo:table-cell>
     </fo:table-row>
