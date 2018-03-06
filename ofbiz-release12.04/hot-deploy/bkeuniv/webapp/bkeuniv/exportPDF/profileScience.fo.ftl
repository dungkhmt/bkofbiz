
<#escape x as x?xml>
	<#assign stt=1 />
	<#assign fullName=resultCV.cv.info.staffName />
	<#assign BkEunivBirthday=resultCV.cv.info.staffDateOfBirth?if_exists />
	<#assign BkEunivGender="" />
	<#assign BkEunivAcademic="" />
	<#assign BkEunivYearAcademic="" />
	<#assign BkEunivDegree="" />
	<#assign BkEunivYearDegree="" />
	<#assign ResearchDomain="Linh vuc nghien cuu trong 5 nam gan day" />
	<#assign NatureScience="Khoa hoc va tu nhien"/>
	<#assign ScienceAndTechnology="Khoa hoc Ky thuat va Cong nghe"/>
	<#assign MedicalScience="Khoa hoc Y duoc"/>
	<#assign SocialScience="Khoa hoc Xa hoi"/>
	<#assign HumanScience="Khoa hoc Nhan van"/>
	<#assign AgriculturalScience="Khoa hoc Nong nghiep"/>
	<#assign CodeOfScience="Ma chuyen nganh KH-CN"/>
	<#assign BkEunivNumber=""/>
	<#assign BkEunivName=""/>
	<#assign Additional=""/>
	<#assign BkEunivResearchSpecial=" "/>	
	<#assign BkEunivCurrentPosition=" "/>	
	<#assign BkEunivAdrressHome="Dia chi nha rieng "/>
	<#assign BkEunivPhoneAddress = ""/>	
	<#assign BkEunivCQ = ""/>	
	<#assign BkEunivMobile = ""/>
	<#assign BkEunivEmail = ""/>	
	<#assign agencyWork = "Co quan cong tac"/>
	<#assign BkEunivCompanyName = "" />
	<#assign BkEunivNameLeader = "" />
	<#assign BkEunivCompanyAddress = "" />
	<#assign BkEnuivPhone = "" />
	<#assign BkEnuivFax = "" />
	<#assign EducationProgress = "Qua trinh dao tao" />
	<#assign ForeignLanguage = "Trinh do ngoai ngu (moi muc de nghi ghi ro muc do: Tot/Kha/TB)" /> 
	<#assign EducationType = "Bac dao tao" />
	<#assign Institution = "Noi dao tao" />
	<#assign Speciality = "Chuyen nganh" />
	<#assign GraduateDate = "Ngay/Thang/Nam tot nghiep (TS, Thac si, KS, CN,...)" />
	<#assign University = "Dai hoc" />
	<#assign Masters = "Thac si" />
	<#assign Degree = "Tien si" />
	<#assign Internship = "Thuc tap sinh khoa hoc" />
	<#assign ID = "STT" />
	<#assign ForeignLanguageName = "Ten ngoai ngu" />
	<#assign Listen = "Nghe" />
	<#assign Speak = "Noi" />
	<#assign Read = "Doc" />
	<#assign Write = "Viet" />
	<#assign Russia = "Tieng Nga" />
	<#assign Level = "Tot" />
	<#assign English = "Tieng Anh" />
	<#assign BussinessProgress = "Qua trinh cong tac" />
	<#assign Time = "Thoi gian (tu nam    den nam " />
	<#assign PositionBussiness = "Vi tri cong tac" />
	<#assign Domain = "Linh vuc chuyen mon" />
	<#assign Company = "Co quan cong tac" />
	<#assign ResearchConstruction = "Cac cong trinh KH-CN chu yeu duoc cong bo, sach chuyen khao " />
	<#assign Construction = "(liet ke cong trinh tieu bieu da cong bo trong 5 nam gan nhat) " />
	<#assign ConstructionName = "Ten cong trinh (bai bao, cong trinh ...) " />
	<#assign Author = "La tac gia hoac la dong tac gia cong trinh" />
	<#assign Public = "Noi cong bo (ten tap chi da dang/nha xuat ban) " />
	<#assign Year = "Nam cong bo " />
	<#assign Internetional1 = "Tap chi Quoc te trong danh muc ISI" />
	<#assign Internetional2 = "Tap chi Quoc te trong danh muc Scopus" />
	<#assign Internetional3 = "Tap chi Quoc te khong trong danh muc ISI/Scopus" />
	<#assign Internetional4 = "Tap chi Quoc gia" />
	<#assign Internetional5 = "Ky yeu HN/HT quoc te" />
	<#assign Internetional6 = "Ky yeu HN/HT trong nuoc" />
	<#assign Internetional7 = "Sach chuyen khao" />
	<#assign Internetional8 = "Sach giao trinh.Sach tham khao" />
	<#assign NumberOfDegree1 = "So luong van bang doc quyen sang che/giai phap huu ich/van bang bao ho " /> 
	<#assign NumberOfDegree2 = "giong cay trong/thiet ke bo tri mach tich hop da duoc cap (neu co)" />
	<#assign NameAndContent = "Ten va noi dung van bang" />
	<#assign YearDegree = "Nam cap van bang" />
	<#assign NumberOfConstruction = "So luong cong trinh, ket qua nghien cuu duoc ap dung trong thuc tien" />
	<#assign NameOfConstruction = "Ten cong trinh" />
	<#assign Model = "Hinh thuc, quy mo, dia chi ap dung" />
	<#assign TimeConstruction = "Thoi gian" /> 
	<#assign Topic = "Cac de tai, du an, nhiem vu KH-CN da chu tri hoac tham gia trong 5 nam gan day" />
	<#assign SubTopic1 = "Cac de tai, du an, nhiem vu KH-CN da chu tri" />
	<#assign SubTopic2 = "Cac de tai, du an, nhiem vu KH-CN tham gia" />
	<#assign TimeStart = "Thoi gian (bat dau - ket thuc) " />
	<#assign Program = "Thuoc chuong trinh (neu co)" />
	<#assign Status1 = "Tinh trang (da nghiem thu - xep loai, chua nghiem thu) " />
	<#assign Status2 = "Tinh trang (da nghiem thu, chua nghiem thu)" />
	<#assign Award = "Giai thuong (ve KH-CN, ve chat luong san pham, ...)" />
	<#assign ModelAndContent = "Hinh thuc va noi dung giai thuong" />
	<#assign YearOfAward = "Nam tang thuong" />
	<#assign ExperienceManagement1 = "Kinh nghiem ve quan ly, danh gia KH-CN (so luong cac Hoi dong tu van, xet duyet, " />
	<#assign ExperienceManagement2 = "nghiem thu, danh gia cac chuong trinh, de tai, du an KH-CN cap quoc gia hoac tuong
		 duong trong va ngoai nuoc da tham gia trong 5 nam gan day)" />
		 
	<#assign ModelCouncil = "Hinh thuc hoi dong" />
	<#assign Times = "So lan" />
	<#assign ExperienceSuccess = "Nghien cuu sinh da huong dan bao ve thanh cong (neu co)" />
	<#assign FirstAndLastName = "Ho va ten" />
	<#assign Guide = "Huong dan hoac dong huong dan" />
	<#assign Unit = "Don vi cong tac" />
	<#assign YearSuccess = "Nam bao ve thanh cong" />
	
	<#assign ExperienceGuide = "Nghien cuu sinh va hoc vien cao hoc dang huong dan" />
	<#assign ExperienceName = "Ho va ten hoc vien/NCS" />
	<#assign MainGuide = "Huong dan chinh/HD phu" />
	<#assign NameTopic = "Ten de tai" />
	<#assign Specialize = "Chuyen nganh/ma chuyen nganh"/>
	<#assign TimeEducation = "Thoi gian dao tao" />
	<#assign Information1 = "Thong tin khac" />
	<#assign Information2 = "Linh vuc nghien cuu trong 5 nam gan day" />
	<#assign Information3 = "Cac huong nghien cuu cho NCS va HV cao hoc" />
	<#assign Information4 = "Toi xin cam doan nhung thong tin duoc ghi o tren la hoan toan chinh xac" />
	<#assign Information5 = "..., ngay ... thang ... nam ..." />
	<#assign Information6 = "Nguoi khai" />
	<#assign Information7 = "(Ky va ghi ro ho ten)" />
	
	<#assign blank = "" />
	<#assign ID0 = "0" />
	<#assign ID1 = "1" />
	<#assign ID2 = "2" />
	<#assign ID3 = "3" />
	<#assign ID4 = "4" />
	<#assign ID5 = "5" />
	<#assign ID6 = "6" />
	
	
		


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
		          <fo:block >${uiLabelMap.BkEunivFullName}: ${fullName}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="solid" border-bottom-style="dotted">
          
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
					
				<fo:table-row border-bottom-style="dotted" height="20pt" >
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
     
      <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted">
          
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
<#assign researchDomain = resultCV.cv.researchDomain />
		        <fo:table-cell>
		          <fo:block font-weight="bold">${ResearchDomain}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>

      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-style="dotted" border-left-style = "solid" 
        border-right-style = "solid" >
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(11)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(11)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(11)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
							
			<fo:table-body >
				<#assign researchDomain = resultCV.cv.researchDomain />
				<#assign size = researchDomain?size/>
				<#assign n = (size/3)?ceiling - 1  />
				<#list 0..n as i>
					<fo:table-row border-bottom-style="dotted" height="20pt" >
					<#list 0..2 as j>
						<#if i*3+j < size>
							<#assign rd = researchDomain[i*3+j] />
							<fo:table-cell />

							<fo:table-cell>
								<fo:block>${rd.researchDomainName}
								</fo:block>
							</fo:table-cell>
							
							<#if j==2>
								<fo:table-cell padding="3">
									<#if rd.check>
										<fo:table table-layout="fixed">
											<fo:table-column column-width="proportional-column-width(1)"/>
											
											<fo:table-body>
												<fo:table-row>
													//STT
													<fo:table-cell>
														<fo:block font-size="14pt" font-family="wingdings" >&#254;</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									<#else>
									<fo:table table-layout="fixed">
										<fo:table-column column-width="proportional-column-width(1)"/>
										
										<fo:table-body>
											<fo:table-row>
												//STT
												<fo:table-cell>
													<fo:block font-size="14pt" font-family="wingdings" >&#111;</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
									</#if>
								</fo:table-cell>
							<#else>
								<fo:table-cell border-right-style = "solid" padding="3">
										<#if rd.check>
											<fo:table table-layout="fixed">
												<fo:table-column column-width="proportional-column-width(1)"/>
												
												<fo:table-body>
													<fo:table-row>
														//STT
														<fo:table-cell>
															<fo:block font-size="14pt" font-family="wingdings" >&#254;</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
									<#else>
										<fo:table table-layout="fixed">
											<fo:table-column column-width="proportional-column-width(1)"/>
											
											<fo:table-body>
												<fo:table-row>
													//STT
													<fo:table-cell>
														<fo:block font-size="14pt" font-family="wingdings" >&#111;</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</#if>
								</fo:table-cell>
							</#if>
						<#else>
							<fo:table-cell />
							<fo:table-cell />
							<#if j==2>
								<fo:table-cell />
							<#else>
								<fo:table-cell border-right-style = "solid" />
							</#if>
						</#if>
						
					</#list>
					</fo:table-row>
				</#list>
														
				</fo:table-body>
		  	</fo:table>
		  
        </fo:table-cell>
      </fo:table-row>
			
      <#assign researchSpeciality = resultCV.cv.researchSpeciality />
      <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
          	<fo:table-column column-width="proportional-column-width(10)"/>
						<fo:table-column column-width="proportional-column-width(1)"/>

						<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(1)"/>

						<fo:table-column column-width="proportional-column-width(1)"/>
						
						<fo:table-column column-width="proportional-column-width(10)"/>
						<fo:table-body>
							<#list researchSpeciality as rs>
								<#assign code = (rs.researchSpecialityCode)?split("", "r")>

								<fo:table-row>
									<fo:table-cell />

									<fo:table-cell >
										<fo:block>${CodeOfScience}</fo:block>
									</fo:table-cell>

									<fo:table-cell />
									
									<#list code as c>
										<#if c!="">
											<fo:table-cell padding="3" border-style="solid">
												<fo:block text-align="center">${c}</fo:block>
											</fo:table-cell>
										</#if>
									</#list>
									
									
									<fo:table-cell />
									
									<fo:table-cell padding="1">
										<fo:block>${uiLabelMap.BkEunivName}: ${rs.researchSpecialityName}</fo:block>
									</fo:table-cell>
									
								</fo:table-row>

							</#list>
							
						</fo:table-body>
					</fo:table>
        </fo:table-cell>
      </fo:table-row>

      
      
      <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted">
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
						<fo:table-column column-width="proportional-column-width(30)"/>
						<fo:table-body>
							<fo:table-row>
								//STT
								<fo:table-cell />

								<fo:table-cell>
									<fo:block>${Additional}</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted">
          
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
		          <fo:block font-weight="bold">${uiLabelMap.BkEunivResearchSpecial}: ${BkEunivResearchSpecial}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		       <fo:table-row>
						//STT
		        <fo:table-cell />
		        <fo:table-cell>
		          <fo:block font-weight="bold">${uiLabelMap.BkEunivCurrentPosition}: ${BkEunivCurrentPosition}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted">
          
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
		          <fo:block font-weight="bold">${BkEunivAdrressHome}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
       <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted">
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(9)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(9)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(9)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell />
			      	
		        <fo:table-cell>
		        	<fo:block>${uiLabelMap.BkEunivPhoneAddress}: ${BkEunivPhoneAddress}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell />
			      	
		        <fo:table-cell>
		        	<fo:block >${uiLabelMap.BkEunivCQ}: ${BkEunivCQ}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell />
			      	
		        <fo:table-cell>
		        	<fo:block >${uiLabelMap.BkEunivMobile}: ${BkEunivMobile}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		       <fo:table-row>
						//STT
		        <fo:table-cell />
			      	
		        <fo:table-cell>
		        	<fo:block>${uiLabelMap.BkEunivEmail}: ${BkEunivEmail}</fo:block>
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
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell>
			      	<fo:block font-weight="bold">${stt}.</fo:block>
			    	</fo:table-cell>
			    	<#assign stt=stt+1 />

		        <fo:table-cell>
		          <fo:block font-weight="bold">${agencyWork}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      	
      
       <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid" border-top-style= "dotted">
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(9)"/>
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
						<fo:table-cell>
							<fo:block>${uiLabelMap.BkEunivCompanyName}: ${BkEunivCompanyName}</fo:block>
						</fo:table-cell>
						
						</fo:table-row>

						<fo:table-row height="20pt" >
							<fo:table-cell />

							<fo:table-cell>
								<fo:block>${uiLabelMap.BkEunivNameLeader}: ${BkEunivNameLeader}</fo:block>
							</fo:table-cell>
								
						</fo:table-row>
							
						<fo:table-row height="20pt" >
							<fo:table-cell />

							<fo:table-cell>
								<fo:block>${uiLabelMap.BkEunivCompanyAddress}: ${BkEunivCompanyAddress}</fo:block>
							</fo:table-cell>
								
							</fo:table-row>
							
							<fo:table-row height="20pt" >
								<fo:table-cell />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEnuivPhone}: ${BkEnuivPhone}</fo:block>
								</fo:table-cell>
								<fo:table-cell />
								
								<fo:table-cell />

								<fo:table-cell>
									<fo:block>${uiLabelMap.BkEnuivFax}: ${BkEnuivFax}</fo:block>
								</fo:table-cell>
								<fo:table-cell />
							</fo:table-row>																	
						</fo:table-body>
				</fo:table>
       		 </fo:table-cell>
     	 </fo:table-row>
 	<#assign indexS=9 />
 	<#if sections??>
		<#list sections as section>
			<#switch section>
                <#case "education-progress">
                    <#include "pdf-education-progress.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "patent">
                    <#include "pdf-patents.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "projects-applied">
                    <#include "pdf-projects-applied.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "phd-defensed">
                    <#include "pdf-phd-defensed.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "graduate-students">
                    <#include "pdf-current-graduate-students.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "publications">
                    <#include "pdf-publications.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "work-progress">
                    <#include "pdf-business-progress.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "recent-5-year-projects">
                    <#include "pdf-5-years-recent-projects.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "award">
                    <#include "pdf-awards.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
                <#case "scientific-service">
                    <#include "pdf-scientific-service-experience.fo.ftl"/>
										<#assign indexS=indexS+1 />
                    <#break>
			</#switch>
	 	</#list>
	</#if>
 	
 	
  	<#--  <#list 1..20 as idx>
 		<#if idx==idxEducationProgress>
 			<#include "pdf-education-progress.fo.ftl"/>
 		</#if>
 		<#if idx==idxPatent>
 			<#include "pdf-patents.fo.ftl"/>
 		</#if> 		
 		<#if idx==idxProjectsApplied>
 			<#include "pdf-projects-applied.fo.ftl"/>
 		</#if> 		
 		<#if idx==idxPhDDefensed>
 			<#include "pdf-phd-defensed.fo.ftl"/>
 		</#if> 		
 		<#if idx==idxGraduateStudents>
 			<#include "pdf-current-graduate-students.fo.ftl"/>
 		</#if> 		
 		<#if idx==idxPublications>
 			<#include "pdf-publications.fo.ftl"/>
 		</#if>
 		<#if idx==idxWorkProgress>
 			<#include "pdf-business-progress.fo.ftl"/>
 		</#if>
 		<#if idx==idxRecent5YearProjects>
 			<#include "pdf-5-years-recent-projects.fo.ftl"/>
 		</#if>
 		<#if idx==idxAward>
 			<#include "pdf-awards.fo.ftl"/>
 		</#if>
 		<#if idx==idxScientificService>
 			 <#include "pdf-scientific-service-experience.fo.ftl"/>
 		</#if>
 		
 	</#list>  -->

   	     	 
     
     
    
    
     
     
    
    
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
          	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(30)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		       <fo:table-cell />
		        <fo:table-cell>
			      	<fo:block ><#if indexS??>${indexS}</#if>.</fo:block>
			    	</fo:table-cell>

		        <fo:table-cell >
		          <fo:block >${Information1}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
						//STT
		       <fo:table-cell />
		       <fo:table-cell />
		       
		        <fo:table-cell >
		          <fo:block >${Information2}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
						//STT
		       <fo:table-cell />
		        <fo:table-cell />
		        
		        <fo:table-cell >
		          <fo:block >${Information3}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		       <fo:table-row>
						//STT
		       <fo:table-cell />
		        <fo:table-cell />
		        
		        <fo:table-cell >
		          <fo:block >${Information4}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		       
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(15)"/>
		  	<fo:table-column column-width="proportional-column-width(15)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell />

		        <fo:table-cell >
		          <fo:block >${Information5}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
       <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(17)"/>
		  	<fo:table-column column-width="proportional-column-width(13)"/>
		    <fo:table-body>
		      
		      <fo:table-row>
						//STT
		        <fo:table-cell />

		        <fo:table-cell >
		          <fo:block font-weight="bold">${Information6}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		     
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
       <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(16)"/>
		  	<fo:table-column column-width="proportional-column-width(14)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell />

		        <fo:table-cell >
		          <fo:block >${Information7}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
    </fo:table-body>
  </fo:table>

</#escape>

