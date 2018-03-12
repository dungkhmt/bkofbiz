package org.ofbiz.bkeuniv.projectdeclaration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class ProjectDeclaration {
	public static String module = ProjectDeclaration.class.getName();
	private static List<GenericValue> projectDeclarationOfStaffs;
	
	public static Map<String, Object> getProjectDeclarationOfStaff(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)context.get("staffId");
		if(staffId == null)
			staffId = (String)userLogin.get("userLoginId");
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("declarationStaffId", EntityOperator.EQUALS, staffId));
			List<GenericValue> listProjectDeclaration = delegator.findList("ProjectDeclarationView", 
																EntityCondition.makeCondition(conds), 
																null, null, null, false);
			projectDeclarationOfStaffs = listProjectDeclaration;
			List<Map> projectDeclarations = FastList.newInstance();
			for(GenericValue gv : listProjectDeclaration){
				Map<String, Object> map = FastMap.newInstance();
					map.put("projectDeclarationId", gv.getString("projectDeclarationId"));
					map.put("projectCategoryId", gv.getString("projectCategoryId"));
					map.put("projectCategoryName", gv.getString("projectCategoryName"));
					map.put("projectName", gv.getString("projectName"));
					map.put("startDate", gv.getString("startDate"));
					map.put("endDate", gv.getString("endDate"));
					map.put("projectStatusId", gv.getString("projectStatusId"));
					map.put("projectStatusName", gv.getString("projectStatusName"));
					map.put("researchProgram", gv.getString("researchProgram"));
					map.put("declarationStaffId", gv.getString("declarationStaffId"));
					map.put("projectParticipationRoleId", gv.getString("projectParticipationRoleId"));
					map.put("projectParticipationRoleName", gv.getString("projectParticipationRoleName"));
					map.put("approverStaffId", gv.getString("approverStaffId"));
					map.put("staffName", gv.getString("staffName"));
					map.put("hourOfStaff", gv.getString("hourOfStaff"));
					map.put("totalhour", gv.getString("totalhour"));
					map.put("budget", gv.getString("budget"));
					map.put("academicYearId", gv.getString("academicYearId"));
					map.put("academicYearName", gv.getString("academicYearName"));
					
					map.put("sponsor", gv.getString("sponsor"));
				
				projectDeclarations.add(map);
			}
			retSucc.put("projectDeclarations", projectDeclarations);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> createProjectDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		List<String> projectCategoryId = (List<String>) context.get("projectCategoryId[]");
		String projectName = (String) context.get("projectName");
		String startDate = (String) context.get("startDate");
		String endDate = (String) context.get("endDate");
		List<String> projectStatusId = (List<String>) context.get("projectStatusId[]");
		String researchProgram = (String) context.get("researchProgram");
		List<String> projectParticipationRoleId = (List<String>) context.get("projectParticipationRoleId[]");
		List<String> approverStaffId = (List<String>) context.get("approverStaffId[]");
		
		String stotalHour = (String)context.get("totalhour");
		String sStaffHour = (String)context.get("hourOfStaff");
		String sBudget = (String)context.get("budget");
		String sponsor = (String)context.get("sponsor");
		List<String> academicYearId = (List<String>) context.get("academicYearId[]");
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.makeValue("ProjectDeclaration");
			gv.put("projectDeclarationId", delegator.getNextSeqId("ProjectDeclaration"));
			if(projectCategoryId != null && projectCategoryId.size() > 0)
				gv.put("projectCategoryId", projectCategoryId.get(0));
			
			if(academicYearId != null && academicYearId.size() > 0)
				gv.put("academicYearId", academicYearId.get(0));
			
			gv.put("projectName", projectName);
			
			Debug.log(module + "::createProjectDeclaration, startDate = " + startDate + ", endDate = " + endDate);
			
			if(startDate != null && !startDate.equals(""))
				gv.put("startDate", Date.valueOf(startDate));
			if(endDate != null && !endDate.equals(""))
				gv.put("endDate", Date.valueOf(endDate));
			if(projectStatusId != null && projectStatusId.size() > 0)
				gv.put("projectStatusId", projectStatusId.get(0));
			
			
			
			if(researchProgram != null && ! researchProgram.equals(""))
				gv.put("researchProgram", researchProgram);
			
			gv.put("declarationStaffId", staffId);
			
			if(projectParticipationRoleId != null && projectParticipationRoleId.size() > 0)
				gv.put("projectParticipationRoleId", projectParticipationRoleId.get(0));
			
			if(approverStaffId != null && approverStaffId.size() > 0)
				gv.put("approverStaffId", approverStaffId.get(0));
			
			if(stotalHour != null && !stotalHour.equals("")){
				long totalHour = Long.valueOf(stotalHour);
				gv.put("totalhour", totalHour);
			}
			if(sStaffHour != null && !sStaffHour.equals("")){
				long staffHour = Long.valueOf(sStaffHour);
				gv.put("hourOfStaff", staffHour);
			}
			if(sBudget != null && !sBudget.equals("")){
				long budget = Long.valueOf(sBudget);
				gv.put("budget", budget);
			}
			if(sponsor != null && ! sponsor.equals(""))
				gv.put("sponsor", sponsor);
			
			delegator.create(gv);
			
			GenericValue result = delegator.findOne("ProjectDeclarationView", false, UtilMisc.toMap("projectDeclarationId",
													gv.get("projectDeclarationId")));
			Map<String,  Object> map = FastMap.newInstance();
				map.put("projectDeclarationId", result.getString("projectDeclarationId"));
				map.put("projectCategoryId", result.getString("projectCategoryId"));
				map.put("projectCategoryName", result.getString("projectCategoryName"));
				map.put("projectName", result.getString("projectName"));
				map.put("startDate", result.getString("startDate"));
				map.put("endDate", result.getString("endDate"));
				map.put("projectStatusId", result.getString("projectStatusId"));
				map.put("projectStatusName", result.getString("projectStatusName"));
				map.put("researchProgram", result.getString("researchProgram"));
				map.put("declarationStaffId", result.getString("declarationStaffId"));
				map.put("projectParticipationRoleId", result.getString("projectParticipationRoleId"));
				map.put("projectParticipationRoleName", result.getString("projectParticipationRoleName"));
				map.put("hourOfStaff", result.getString("hourOfStaff"));
				map.put("totalhour", result.getString("totalhour"));
				map.put("budget", result.getString("budget"));
				map.put("academicYearId", result.getString("academicYearId"));
				map.put("academicYearName", result.getString("academicYearName"));
				
				map.put("sponsor", result.getString("sponsor"));
				
				map.put("approverStaffId", result.getString("approverStaffId"));
				map.put("staffName", result.getString("staffName"));
			
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	
	public static Map<String, Object> updateProjectDeclaration(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		String projectDeclarationId = (String) context.get("projectDeclarationId");
		List<String> projectCategoryId = (List<String>) context.get("projectCategoryId[]");
		String projectName = (String) context.get("projectName");
		String startDate = (String) context.get("startDate");
		String endDate = (String) context.get("endDate");
		List<String> projectStatusId = (List<String>) context.get("projectStatusId[]");
		String researchProgram = (String) context.get("researchProgram");
		List<String> projectParticipationRoleId = (List<String>) context.get("projectParticipationRoleId[]");
		String approverStaffId = (String) context.get("approverStaffId");
		String stotalHour = (String)context.get("totalhour");
		String sStaffHour = (String)context.get("hourOfStaff");
		String sBudget = (String)context.get("budget");
		String sponsor = (String)context.get("sponsor");
		List<String> academicYearId = (List<String>) context.get("academicYearId[]");
		
		Debug.log(module + "::updateProjectDeclaration, projectDeclarationId = " + projectDeclarationId + 
				", stotalHour = " + stotalHour + ", sStaffHour= " + sStaffHour);
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.findOne("ProjectDeclaration", false, UtilMisc.toMap("projectDeclarationId", projectDeclarationId));
			if(gv != null){
				if(projectCategoryId != null && projectCategoryId.size() > 0)
					gv.put("projectCategoryId", projectCategoryId.get(0));
				
				if(academicYearId != null && academicYearId.size() > 0)
					gv.put("academicYearId", academicYearId.get(0));
				
				gv.put("projectName", projectName);
				if(startDate != null && !startDate.equals(""))
					gv.put("startDate", Date.valueOf(startDate));
				if(endDate != null && !endDate.equals(""))
					gv.put("endDate", Date.valueOf(endDate));
				if(projectStatusId != null && projectStatusId.size() > 0)
					gv.put("projectStatusId", projectStatusId.get(0));
				if(researchProgram != null && ! researchProgram.equals(""))
					gv.put("researchProgram", researchProgram);
				//gv.put("declarationStaffId", staffId);
				if(projectParticipationRoleId != null && projectParticipationRoleId.size() > 0)
					gv.put("projectParticipationRoleId", projectParticipationRoleId.get(0));
				
				//gv.put("approverStaffId", approverStaffId);
				
				if(stotalHour != null && !stotalHour.equals("")){
					long totalHour = Long.valueOf(stotalHour);
					gv.put("totalhour", totalHour);
					Debug.log(module + "::updateProjectDeclaration, totalHour = " + totalHour);
				}
				if(sStaffHour != null && !sStaffHour.equals("")){
					long staffHour = Long.valueOf(sStaffHour);
					gv.put("hourOfStaff", staffHour);
					Debug.log(module + "::updateProjectDeclaration, staffHour = " + staffHour);
				}
				
				if(sBudget != null && !sBudget.equals("")){
					long budget = Long.valueOf(sBudget);
					gv.put("budget", budget);
				}
				if(sponsor != null && ! sponsor.equals(""))
					gv.put("sponsor", sponsor);
				
				delegator.store(gv);
				
				GenericValue result = delegator.findOne("ProjectDeclarationView", false, UtilMisc.toMap("projectDeclarationId", 
														gv.get("projectDeclarationId")));
				Map<String,  Object> map = FastMap.newInstance();
					map.put("projectDeclarationId", result.getString("projectDeclarationId"));
					map.put("projectCategoryId", result.getString("projectCategoryId"));
					map.put("projectCategoryName", result.getString("projectCategoryName"));
					map.put("projectName", result.getString("projectName"));
					map.put("startDate", result.getString("startDate"));
					map.put("endDate", result.getString("endDate"));
					map.put("projectStatusId", result.getString("projectStatusId"));
					map.put("projectStatusName", result.getString("projectStatusName"));
					map.put("researchProgram", result.getString("researchProgram"));
					map.put("declarationStaffId", result.getString("declarationStaffId"));
					map.put("projectParticipationRoleId", result.getString("projectParticipationRoleId"));
					map.put("projectParticipationRoleName", result.getString("projectParticipationRoleName"));
					map.put("approverStaffId", result.getString("approverStaffId"));
					map.put("staffName", result.getString("staffName"));
					map.put("hourOfStaff", result.getString("hourOfStaff"));
					map.put("totalhour", result.getString("totalhour"));
					map.put("budget", result.getString("budget"));
					map.put("academicYearId", result.getString("academicYearId"));
					map.put("academicYearName", result.getString("academicYearName"));
					map.put("sponsor", result.getString("sponsor"));
					
					
				retSucc.put("projectDeclarations", map);
        		retSucc.put("message", "Updated record with id: " + projectDeclarationId);
        	} else {
        		retSucc.put("message", "Not found record with id: " + projectDeclarationId);
        	}
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> deleteProjectDeclaration (DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
        String projectDeclarationId = (String) context.get("projectDeclarationId");
        try{
        	GenericValue gv = delegator.findOne("ProjectDeclaration", UtilMisc.toMap("projectDeclarationId", 
        										projectDeclarationId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "Deleted record with Id: " + projectDeclarationId);
        	} else {
        		retSucc.put("result", "Not found record with Id: " + projectDeclarationId);
        	}
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> getProjectCategory(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list = delegator.findList("ProjectCategory", null, null, null, findOptions, false);
			List<Map> listProjectCategory = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map=FastMap.newInstance();
					map.put("projectCategoryId",gv.getString("projectCategoryId"));
					map.put("projectCategoryName",gv.getString("projectCategoryName"));
				listProjectCategory.add(map);
			}
			retSucc.put("projectCategorys", listProjectCategory);
			return retSucc;
		} catch (Exception e) {
			System.out.print("Education Progress Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> getProjectStatus(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list = delegator.findList("ProjectStatus", null, null, null, findOptions, false);
			List<Map> listProjectStatus = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map=FastMap.newInstance();
					map.put("projectStatusId", gv.getString("projectStatusId"));
					map.put("projectStatusName", gv.getString("projectStatusName"));
				listProjectStatus.add(map);
			}
			retSucc.put("projectStatuss", listProjectStatus);
			return retSucc;
		} catch (Exception e) {
			System.out.print("Education Progress Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}

	public static Map<String, Object> getProjectParticipationRole(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list = delegator.findList("ProjectParticipationRole", null, null, null, findOptions, false);
			List<Map> listProjectParticipationRole = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map=FastMap.newInstance();
					map.put("projectParticipationRoleId", gv.getString("projectParticipationRoleId"));
					map.put("projectParticipationRoleName", gv.getString("projectParticipationRoleName"));
				listProjectParticipationRole.add(map);
			}
			retSucc.put("projectParticipationRoles", listProjectParticipationRole);
			return retSucc;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String filename = "Project_Declaration";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			// start renderExcel
			HSSFWorkbook wb = new HSSFWorkbook();

			Sheet sh = wb.createSheet("sheet1");

			int i;
			
			for (i = 0; i < projectDeclarationOfStaffs.size(); i++) {
				Row r = sh.createRow(0);
				Cell cell = r.createCell(0);
				cell.setCellValue("Project declaration");

				Cell cell1 = r.createCell(1);
				cell1.setCellValue("Project category");

				Cell cell2 = r.createCell(2);
				cell2.setCellValue("Project name");

				Cell cell3 = r.createCell(3);
				cell3.setCellValue("Start date");

				Cell cell4 = r.createCell(4);
				cell4.setCellValue("End date");

				Cell cell5 = r.createCell(5);
				cell5.setCellValue("Project status");

				Cell cell6 = r.createCell(6);
				cell6.setCellValue("Research program");
				
				Cell cell7 = r.createCell(7);
				cell7.setCellValue("Project participation role");
				
				Cell cell8 = r.createCell(8);
				cell8.setCellValue("Approver staff");
			}
			
			for (i = 0; i < projectDeclarationOfStaffs.size(); i++) {
				Row r = sh.createRow(i+1);
				GenericValue gv = projectDeclarationOfStaffs.get(i);
				Cell cell = r.createCell(0);
				cell.setCellValue(gv.getString("projectDeclarationId"));

				Cell cell1 = r.createCell(1);
				cell1.setCellValue(gv.getString("projectCategoryName"));

				Cell cell2 = r.createCell(2);
				cell2.setCellValue(gv.getString("projectName"));

				Cell cell3 = r.createCell(3);
				cell3.setCellValue(gv.getString("startDate"));

				Cell cell4 = r.createCell(4);
				cell4.setCellValue(gv.getString("endDate"));

				Cell cell5 = r.createCell(5);
				cell5.setCellValue(gv.getString("projectStatusName"));

				Cell cell6 = r.createCell(6);
				cell6.setCellValue(gv.getString("researchProgram"));
				
				Cell cell7 = r.createCell(7);
				cell7.setCellValue(gv.getString("projectParticipationRoleName"));
				
				Cell cell8 = r.createCell(8);
				cell8.setCellValue(gv.getString("staffName"));

			}

			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
