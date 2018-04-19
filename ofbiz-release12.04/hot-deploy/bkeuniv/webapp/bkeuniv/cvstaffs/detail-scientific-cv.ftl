<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">
<script src="/resource/bkeuniv/js/lib/dropify.min.js"></script>
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/dropify.min.css">
<link rel="stylesheet" href="/resource/bkeuniv/css/lib/alertify.min.css">
<script src="/resource/bkeuniv/js/lib/alertify.min.js"></script>
<style>
	.info-table{
		width: 100%;
    	border: 1px solid black;
    	overflow: scroll;
	}
	.info-box{
		padding:30px
	}
	.modal-dialog {
		width: 60%!important;
		margin-left: 20%!important;
	}
</style>

<style>
 tr {
    border-bottom: 1px solid black;
    border-collapse: collapse;
}​
</style>

<div class="info-table">
<table>
	<tr>
		<td>
			<div class="info-box">
				<table>
					<tr>
						<th colspan="4" style="text-align:center;">
							Danh sach de tai/du an duoc ung dung
						</th>
					</tr>
					<tr>
						<th>STT</th>
						<th>Ten cong trinh</th>
						<th>Mo ta</th>
						<th>Giai doan</th>
					</tr>
					
				<#assign index = 0>
				<#list resultCV.cv.appliedProjects as p>
					<tr>
					<#assign index = index+1>

						<td>${index}</td>
						<td>
							<#if p.name?exists>
								${p.name}
							<#else>
								
							</#if>
						</td>
						
						<td>
							<#if p.description?exists>
								${p.description}
							<#else>
								
							</#if>
						</td>
					
						<td>
							<#if p.period?exists>
								${p.period}
							<#else>

							</#if>
						</td>
					</tr>
				</#list>
				</table>
			</div>
		</td>
	</tr>
	
	<tr>
		<td>
			<div class="info-box">
				<table>
					<tr>
						<th colspan="4" style="text-align:center;">
							Kinh nghiem danh gia, hoi dong KHCN
						</th>
					</tr>
					<tr>
						<th>STT</th>
						<th>Noi dung</th>
						<th>So lan</th>
						
					</tr>
					
					<#assign index = 0>
					<#list resultCV.cv.scientificExperiences as se>
					<tr>
						<#assign index = index + 1>
						<td>
							${index}
						</td>
						<td>
							${se.description}
						</td>
						<td>
							${se.quantity}
						</td>
					</tr>
					</#list>
				</table>
			</div>
		</td>
	</tr>
	
	<tr>
		<td>
			<div class="info-box">
				<table border="1" rules="rows">
					<tr>
						<th colspan="5" style="text-align:center;">
							Danh sach cong trinh
						</th>
					</tr>
					<tr>
						<th>STT</th>
						<th>Ten cong trinh</th>
						<th>Tac gia/dong tac gia</th>
						<th>Tap chi/hoi nghi</th>
						<th>Nam xuat ban</th>
					</tr>
					<#assign index = 0>
					<#list resultCV.cv.papers as p>
						<#assign index = index + 1>
						<tr>
							<td>
								${index}
							</td>
							<td>
								<#if p.paperName?exists>
									${p.paperName}
								<#else>
									
								</#if>
							</td>
							<td>
								<#if p.roleName?exists>
									${p.roleName}
								<#else>
								
								</#if>
							</td>
							<td>
								<#if p.journalConferenceName?exists>
									${p.journalConferenceName}
								<#else>
									
								</#if>
							</td>
							<td>
								<#if p.year?exists>
									${p.year}
								<#else>
								
								</#if>
							</td>
						</tr>
					</#list>
				</table>
			</div>
		</td>
	</tr>
</table>
