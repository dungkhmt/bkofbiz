package src.org.ofbiz.bkeuniv.workprogress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class WorkProgress {
	public static String module = WorkProgress.class.getName();
	private static List<GenericValue> WorkProgressOfStaffs;
	
	public static Map<String, Object> getWorkProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId));
			List<GenericValue> listWorkProgress = delegator.findList("WorkProgress", EntityCondition.makeCondition(conds), null, null, null, false);
			WorkProgressOfStaffs = listWorkProgress;
			List<Map> workProgress = FastList.newInstance();
			for(GenericValue gv : listWorkProgress){
				Map<String, Object> map=FastMap.newInstance();
				map.put("workProgressId",gv.getString("workProgressId"));
				map.put("staffId",gv.getString("staffId"));
				map.put("period",gv.getString("period"));
				map.put("position",gv.getString("position"));
				map.put("specialization",gv.getString("specialization"));
				map.put("institution",gv.getString("institution"));
				workProgress.add(map);
			}
			retSucc.put("workProgress", workProgress);
			return retSucc;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createWorkProgress(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		String period = (String) context.get("period");
		String position = (String) context.get("position");
		String specialization = (String) context.get("specialization");
		String institution = (String) context.get("institution");
		
		Delegator delegator = ctx.getDelegator();
		try {
			GenericValue gv = delegator.makeValue("WorkProgress");
				gv.put("workProgressId", delegator.getNextSeqId("WorkProgress"));
				gv.put("staffId", staffId);
				gv.put("period", period);
				gv.put("position", position);
				gv.put("specialization", specialization);
				gv.put("institution", institution);
			delegator.create(gv);
			
			retSucc.put("workProgress", gv);
			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> updateWorkProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		
		String workProgressId = (String) context.get("workProgressId");
		String period = (String) context.get("period");
		String position = (String) context.get("position");
		String specialization = (String) context.get("specialization");
		String institution = (String) context.get("institution");
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.findOne("WorkProgress", false, UtilMisc.toMap("workProgressId",workProgressId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("period", period);
				gv.put("position", position);
				gv.put("specialization", specialization);
				gv.put("institution", institution);
				delegator.store(gv);
				
				retSucc.put("workProgress", gv);
        		retSucc.put("message", "Updated record with id: " + workProgressId);
        	} else {
        		retSucc.put("message", "Not found record with id: " + workProgressId);
        	}
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> deleteWorkProgress (DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        String workProgressId = (String)context.get("workProgressId");
        Delegator delegator = ctx.getDelegator();
        try{
        	GenericValue gv = delegator.findOne("WorkProgress", UtilMisc.toMap("workProgressId",workProgressId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "Deleted record with Id: " + workProgressId);
        	} else {
        		retSucc.put("result", "Not found record with Id: " + workProgressId);
        	}
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String filename = "Work_Progress";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			// start renderExcel
			HSSFWorkbook wb = new HSSFWorkbook();

			Sheet sh = wb.createSheet("sheet1");

			int i;
			
			for (i = 0; i < WorkProgressOfStaffs.size(); i++) {
				Row r = sh.createRow(0);
				Cell cell = r.createCell(0);
				cell.setCellValue("Work Progress");

				Cell cell1 = r.createCell(1);
				cell1.setCellValue("Period");

				Cell cell2 = r.createCell(2);
				cell2.setCellValue("Position");

				Cell cell3 = r.createCell(3);
				cell3.setCellValue("Specialization");

				Cell cell4 = r.createCell(4);
				cell4.setCellValue("Institution");
			}
			
			for (i = 0; i < WorkProgressOfStaffs.size(); i++) {
				Row r = sh.createRow(i+1);
				GenericValue gv = WorkProgressOfStaffs.get(i);
				Cell cell = r.createCell(0);
				cell.setCellValue(gv.getString("workProgressId"));

				Cell cell1 = r.createCell(1);
				cell1.setCellValue(gv.getString("period"));

				Cell cell2 = r.createCell(2);
				cell2.setCellValue(gv.getString("position"));

				Cell cell3 = r.createCell(3);
				cell3.setCellValue(gv.getString("specialization"));

				Cell cell4 = r.createCell(4);
				cell4.setCellValue(gv.getString("institution"));

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
