
<#escape x as x?xml>
	
	<@macro NumericalOrder numericalOrder=1>		
	</@macro>
	
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
	<#assign CodeOfScience="Ma chuyen nganh KH&CN"/>
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
	<#assign Time = "Thoi gian (tu nam ... den nam ..." />
	<#assign PositionBussiness = "Vi tri cong tac" />
	<#assign Domain = "Linh vuc chuyen mon" />
	<#assign Company = "Co quan cong tac" />
	<#assign ResearchConstruction = "Cac cong trinh KH&CN chu yeu duoc cong bo, sach chuyen khao " />
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
	<#assign Topic = "Cac de tai, du an, nhiem vu KH&CN da chu tri hoac tham gia trong 5 nam gan day" />
	<#assign SubTopic1 = "Cac de tai, du an, nhiem vu KH&CN da chu tri" />
	<#assign SubTopic2 = "Cac de tai, du an, nhiem vu KH&CN tham gia" />
	<#assign TimeStart = "Thoi gian (bat dau - ket thuc) " />
	<#assign Program = "Thuoc chuong trinh (neu co)" />
	<#assign Status1 = "Tinh trang (da nghiem thu - xep loai, chua nghiem thu) " />
	<#assign Status2 = "Tinh trang (da nghiem thu, chua nghiem thu)" />
	<#assign Award = "Giai thuong (ve KH&CN, ve chat luong san pham, ...)" />
	<#assign ModelAndContent = "Hinh thuc va noi dung giai thuong" />
	<#assign YearOfAward = "Nam tang thuong" />
	<#assign ExperienceManagement1 = "Kinh nghiem ve quan ly, danh gia KH&CN (so luong cac Hoi dong tu van, xet duyet, " />
	<#assign ExperienceManagement2 = "nghiem thu, danh gia cac chuong trinh, de tai, du an KH&CN cap quoc gia hoac tuong
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
	<#assign Information5 = "Ha Noi, ngay ... thang ... nam 2016" />
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
			<fo:table-column column-width="proportional-column-width(8)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(12)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(8)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
							
			<fo:table-body >
				<fo:table-row border-bottom-style="dotted" height="20pt" >
					//STT
					<fo:table-cell />
						

					<fo:table-cell>
						<fo:block>${NatureScience}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style = "solid">
						 <fo:table table-layout="fixed">
				          	<fo:table-column column-width="proportional-column-width(1)"/>
						  	
						    <fo:table-body>
						      <fo:table-row>
										//STT
						        <fo:table-cell border-style="solid" width="10" height="10">
						          <fo:block font-weight="bold"></fo:block>
						        </fo:table-cell>
						      </fo:table-row>
						    </fo:table-body>
						  </fo:table>
					</fo:table-cell>

					<fo:table-cell />

					<fo:table-cell >
						<fo:block>${ScienceAndTechnology}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style = "solid">
						 <fo:table table-layout="fixed">
				          	<fo:table-column column-width="proportional-column-width(1)"/>
						  	
						    <fo:table-body>
						      <fo:table-row>
										//STT
						        <fo:table-cell border-style="solid" width="10" height="10">
						          <fo:block font-weight="bold"></fo:block>
						        </fo:table-cell>
						      </fo:table-row>
						    </fo:table-body>
						  </fo:table>
					</fo:table-cell>
					
					<fo:table-cell />

					<fo:table-cell>
						<fo:block>${MedicalScience}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style = "solid">
						 <fo:table table-layout="fixed">
				          	<fo:table-column column-width="proportional-column-width(1)"/>
						  	
						    <fo:table-body>
						      <fo:table-row>
										//STT
						        <fo:table-cell border-style="solid" width="10" height="10">
						          <fo:block font-weight="bold"></fo:block>
						        </fo:table-cell>
						      </fo:table-row>
						    </fo:table-body>
						  </fo:table>
					</fo:table-cell>
				</fo:table-row>
					
				<fo:table-row border-bottom-style="dotted" height="20pt" >
					<fo:table-cell />

					<fo:table-cell >
						<fo:block>${SocialScience}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style = "solid">
						 <fo:table table-layout="fixed">
				          	<fo:table-column column-width="proportional-column-width(1)"/>
						  	
						    <fo:table-body>
						      <fo:table-row>
										//STT
						        <fo:table-cell border-style="solid" width="10" height="10">
						          <fo:block font-weight="bold"></fo:block>
						        </fo:table-cell>
						      </fo:table-row>
						    </fo:table-body>
						  </fo:table>
					</fo:table-cell>

					<fo:table-cell />

					<fo:table-cell>
						<fo:block>${HumanScience}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style = "solid">
						 <fo:table table-layout="fixed">
				          	<fo:table-column column-width="proportional-column-width(1)"/>
						  	
						    <fo:table-body>
						      <fo:table-row>
										//STT
						        <fo:table-cell border-style="solid" width="10" height="10">
						          <fo:block font-weight="bold"></fo:block>
						        </fo:table-cell>
						      </fo:table-row>
						    </fo:table-body>
						  </fo:table>
					</fo:table-cell>
					
					<fo:table-cell />

					<fo:table-cell>
						<fo:block>${AgriculturalScience}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style = "solid">
						 <fo:table table-layout="fixed">
				          	<fo:table-column column-width="proportional-column-width(1)"/>
						  	
						    <fo:table-body>
						      <fo:table-row>
										//STT
						        <fo:table-cell border-style="solid" width="10" height="10">
						          <fo:block font-weight="bold"></fo:block>
						        </fo:table-cell>
						      </fo:table-row>
						    </fo:table-body>
						  </fo:table>
					</fo:table-cell>
				</fo:table-row>
																										
				</fo:table-body>
		  	</fo:table>
		  
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
       	<fo:table-cell height="20pt" display-align="center" border-left-style="solid" border-right-style= "solid" border-bottom-style="dotted" >
          
          //Full name
          <fo:table table-layout="fixed">
          	<fo:table-column column-width="proportional-column-width(1)"/>
          	<fo:table-column column-width="proportional-column-width(9)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(1)"/>
		  	<fo:table-column column-width="proportional-column-width(15)"/>
		    <fo:table-body>
		      <fo:table-row>
						//STT
		        <fo:table-cell />

		        <fo:table-cell >
		          <fo:block>${CodeOfScience}</fo:block>
		        </fo:table-cell>
		        
		       <fo:table-cell border-right-style="solid" />
		        
		        <fo:table-cell border-top-style="solid" border-bottom-style="solid" />
		        
		        <fo:table-cell border-right-style="solid" border-top-style="solid" border-bottom-style="solid">
		          <fo:block>${ID1}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell border-top-style="solid" border-bottom-style="solid" />
		        
		        <fo:table-cell border-right-style="solid" border-top-style="solid" border-bottom-style="solid">
		          <fo:block>${ID0}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell border-top-style="solid" border-bottom-style="solid" />
		        
		        <fo:table-cell  border-right-style="solid" border-top-style="solid" border-bottom-style="solid">
		          <fo:block>${ID3}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell border-top-style="solid" border-bottom-style="solid" />
		        
		        <fo:table-cell   border-right-style="solid" border-top-style="solid" border-bottom-style="solid">
		          <fo:block>${ID0}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell border-top-style="solid" border-bottom-style="solid" />
		        
		        <fo:table-cell  border-right-style="solid" border-top-style="solid" border-bottom-style="solid">
		          <fo:block>${ID2}</fo:block>
		        </fo:table-cell>
		        
		        <fo:table-cell />
		        
		         <fo:table-cell>
		          <fo:block>${uiLabelMap.BkEunivName}: ${BkEunivName}</fo:block>
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
		          <fo:block >${ForeignLanguage}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${ForeignLanguageName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Listen}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Speak}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Read}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${Write}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted">
						<fo:block >${ID1}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Russia}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell>
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted">
						<fo:block >${ID2}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${English}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell >
						<fo:block>${Level}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="dotted" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell border-right-style="solid" />
					<fo:table-cell />
					<fo:table-cell  />
					
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
				//STT
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
				
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
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
					<fo:table-cell>
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
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
				//STT
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

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ResearchConstruction}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
				<fo:table-cell />
		        <fo:table-cell >
		          <fo:block font-weight="bold">${Construction}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(8)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${ConstructionName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${Author}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
						<fo:block>${Public}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="dotted"/>
					<fo:table-cell border-bottom-style="dotted">
						<fo:block>${Year}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<#assign index = 0>
				<#list cv.papers as p>
					<#assign index = index + 1>
				
					<fo:table-row height="20pt" >
						//STT
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block >${index}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.paperName?exists>
								<fo:block>${p.paperName}</fo:block>
							<#else>
								<fo:block>""</fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.paperName?exists>
								<fo:block></fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.journalConferenceName?exists>
								<fo:block>${p.journalConferenceName}</fo:block>
							<#else>
								<fo:block>""</fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-bottom-style="dotted">
							<#if p.year?exists>
								<fo:block>${p.year}</fo:block>
							<#else>
								<fo:block>""</fo:block>
							</#if>
						</fo:table-cell>
					</fo:table-row>	
					
				</#list>
			
				
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

		        <fo:table-cell >
		          <fo:block font-weight="bold">${NumberOfDegree1}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
				<fo:table-cell />
		        <fo:table-cell >
		          <fo:block font-weight="bold">${NumberOfDegree2}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell border-bottom-style="solid"/>
					
					<fo:table-cell border-right-style="solid" border-bottom-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="solid"/>
					
					<fo:table-cell border-right-style="solid" border-bottom-style="solid">
						<fo:block>${NameAndContent}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell  border-bottom-style="solid"/>
					
					<fo:table-cell border-bottom-style="solid">
						<fo:block text-align = "center">${YearDegree}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
			
				<#assign index = 0>
				<#list resultCV.cv.patents as pt>
					<#assign index = index + 1>
					<fo:table-row height="20pt" >
				
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if index?exists>
								<fo:block >${index}</fo:block>
							<#else>
								<fo:block ></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if pt.patentName?exists>
								<fo:block >${pt.patentName}</fo:block>
							<#else>
								<fo:block ></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-bottom-style="dotted">
							<#if pt.year?exists>
								<fo:block >${pt.year}</fo:block>
							<#else>
								<fo:block ></fo:block>
							</#if>
						</fo:table-cell>
					
					</fo:table-row>	

				</#list>
				
				
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

		        <fo:table-cell >
		          <fo:block font-weight="bold">${NumberOfConstruction}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(15)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(5)"/>
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${NameOfConstruction}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Model}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell >
						<fo:block>${TimeConstruction}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				
				<#assign index = 0>
				<#list resultCV.cv.projects as p>
					<#assign index = index+1>
					<fo:table-row height="20pt" border-top-style="dotted" >
					//STT
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block >${index}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.name?exists>
								<fo:block>${p.name}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if p.description?exists>
								<fo:block>${p.description}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell  border-bottom-style="dotted">
							<#if p.period?exists>
								<fo:block>${p.period}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					</fo:table-row>	
					
				</#list>
				
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
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
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
				//STT
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
				//STT
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
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
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
				//STT
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

		        <fo:table-cell >
		          <fo:block font-weight="bold">${Award}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${ModelAndContent}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-align = "center">
						<fo:block>${YearOfAward}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				
				<#assign index = 0>
				<#list resultCV.cv.awards as aw>
					<#assign index = index + 1>
					<fo:table-row height="20pt" border-top-style="dotted" >
						<fo:table-cell border-bottom-style="dotted"/>
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<fo:block >${index}</fo:block>
						</fo:table-cell>
					
						<fo:table-cell border-bottom-style="dotted" />
						<fo:table-cell border-right-style="solid" border-bottom-style="dotted">
							<#if aw.description?exists>
								<fo:block>${aw.description}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					
						<fo:table-cell  border-bottom-style="dotted"/>
						<fo:table-cell  border-bottom-style="dotted">
							<#if aw.year?exists>
								<fo:block>${aw.year}</fo:block>
							<#else>
								<fo:block></fo:block>
							</#if>
						</fo:table-cell>
					</fo:table-row>	

				</#list>
				
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

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ExperienceManagement1}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		      
		      <fo:table-row>
						//STT
		        <fo:table-cell />
			      	

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ExperienceManagement2}</fo:block>
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
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(13)"/>
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid" text-align = "center">
						<fo:block>${ModelCouncil}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-align = "center">
						<fo:block>${Times}</fo:block>
					</fo:table-cell>
					
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID1}</fo:block>
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
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID2}</fo:block>
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
						<fo:block>${Guide}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Unit}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-aline = "center	" >
						<fo:block>${YearSuccess}</fo:block>
					</fo:table-cell>
					
					
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID1}</fo:block>
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
					<fo:table-cell border-right-style="solid">
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					<fo:table-cell>
						<fo:block>${blank}</fo:block>
					</fo:table-cell>
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style="dotted" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID2}</fo:block>
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
     
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" border-top-style = "solid" >
          
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

		        <fo:table-cell >
		          <fo:block font-weight="bold">${ExperienceGuide}</fo:block>
		        </fo:table-cell>
		      </fo:table-row>
		    </fo:table-body>
		  </fo:table>
        </fo:table-cell>
      </fo:table-row>
      
      <fo:table-row>
        <fo:table-cell height="20pt" display-align="center" 
        border-style="solid">
          
          //Full name
          <fo:table table-layout="fixed">
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(2)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(6)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(4)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>
			<fo:table-column column-width="proportional-column-width(1)"/>
			<fo:table-column column-width="proportional-column-width(7)"/>	
			
			
						
			<fo:table-body>
				<fo:table-row height="20pt" >
				//STT
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${ExperienceName}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${MainGuide}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${NameTopic}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell border-right-style="solid">
						<fo:block>${Specialize}</fo:block>
					</fo:table-cell>
					
					<fo:table-cell />
					
					<fo:table-cell text-aline = "center	" >
						<fo:block>${TimeEducation}</fo:block>
					</fo:table-cell>
					
					
					
				</fo:table-row>	
				
				<fo:table-row height="20pt" border-top-style = "solid" >
				//STT
					<fo:table-cell />
					<fo:table-cell border-right-style="solid">
						<fo:block >${ID1}</fo:block>
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
			      	<fo:block >${stt}.</fo:block>
			    	</fo:table-cell>
			    	<#assign stt=stt+1 />

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

