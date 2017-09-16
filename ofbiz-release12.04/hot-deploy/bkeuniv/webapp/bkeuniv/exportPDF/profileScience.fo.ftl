
<#escape x as x?xml>
	<@macro NumericalOrder numericalOrder=1>
		
	</@macro>
	<#assign stt=1 />
	<#assign fullName="Phạm Quang Dũng" />
	<#assign BkEunivBirthday="10/02/1996" />
	<#assign BkEunivGender="Nam" />
	<#assign BkEunivAcademic="" />
	<#assign BkEunivYearAcademic="" />
	<#assign BkEunivDegree="Tiến sĩ" />
	<#assign BkEunivYearDegree="2017" />
	
  <fo:table table-layout="fixed">
  	<fo:table-column column-width="proportional-column-width(1)"/>
    <fo:table-body>
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
		          <fo:block>${uiLabelMap.BkEunivFullName}: ${fullName}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="solid">
          
          //Full name
          <fo:table table-layout="fixed">
						<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(15)"/>
						<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(15)"/>
						
						<fo:table-body>

							<fo:table-row border-bottom-style="dotted" height="20pt" >
								//STT
								<fo:table-cell >
									<fo:block font-weight="bold">${stt}.</fo:block>
								</fo:table-cell>
								<#assign stt=stt+1 />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEunivBirthday}: ${BkEunivBirthday}</fo:block>
								</fo:table-cell>

								//STT
								<fo:table-cell>
									<fo:block font-weight="bold">${stt}.</fo:block>
								</fo:table-cell>
								<#assign stt=stt+1 />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEunivGender}: ${BkEunivGender}</fo:block>
								</fo:table-cell>
							</fo:table-row>

							<fo:table-row height="20pt" >
								//STT
								<fo:table-cell>
									<fo:block font-weight="bold">${stt}.</fo:block>
								</fo:table-cell>
								<#assign stt=stt+1 />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEunivAcademic}: ${BkEunivAcademic}</fo:block>
								</fo:table-cell>

								<fo:table-cell />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEunivYearAcademic}: ${BkEunivDegree}</fo:block>
								</fo:table-cell>
							</fo:table-row>

							<fo:table-row height="20pt" >
								<fo:table-cell />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEunivDegree}: ${BkEunivDegree}</fo:block>
								</fo:table-cell>

								<fo:table-cell />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEunivYearDegree}: ${BkEunivYearDegree}</fo:block>
								</fo:table-cell>
							</fo:table-row>
						
						</fo:table-body>
		  		</fo:table>
		  
        </fo:table-cell>
      </fo:table-row>
      	
    </fo:table-body>
  </fo:table>

</#escape>

