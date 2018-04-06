package org.ofbiz.bkeuniv.researchprogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.utils.BKEunivUtils;

public class researchProgram {
	public final static String module = researchProgram.class.getName();
	
	public static String createResearchProgramRequestResponse(HttpServletRequest request, HttpServletResponse response){
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
//		System.out.println("EducationProgress::createEducatinoProgressRequestResponse, Staff = " + staff.get("staffEmail"));
		Map<String, Object> context = FastMap.newInstance();
		
		context.put("staffId",staff.get("staffId"));
		context.put("researchProgramName",request.getParameter("researchProgramName"));
		context.put("fromDate",request.getParameter("fromDate"));
		context.put("thruDate",request.getParameter("thruDate"));
		try{
			Map<String, Object> resultNewResearchProgram = dispatcher.runSync("createResearchProgram", context);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(resultNewResearchProgram), response, 200);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	
	public static Map<String, Object> getResearchProgram(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		//String u1 = (String)ctx.getAttribute("userLoginId");
		//String u2 = (String)context.get("userLoginId");
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = null;
		if(userLogin != null) staffId = (String)userLogin.getString("userLoginId");
		
		//if(u1 == null) u1 = "NULL";
		//if(u2 == null) u2 = "NULL";
		
		//System.out.println(module + "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " + u2);
//		Debug.log(module + "::getEducationProgress, staffId = " + staffId);
		
		
		String[] keys = {"researchProgramId","researchProgramName", "staffId", "fromDate", "thruDate"};
		String[] search = {"researchProgramName"};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			if(staffId != null)
				conditions.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
			
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if( index == -1) {
						if(key=="fromDate") {
							condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, Date.valueOf((String) el));
						} else {
							condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
						}
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}
			
			List<GenericValue> list = delegator.findList("ResearchProgram", 
					EntityCondition.makeCondition(conditions), 
					null, null, 
					findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listResearchProgram = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> mapResearchProgram = FastMap.newInstance();
				mapResearchProgram.put("researchProgramId", el.getString("researchProgramId"));
				mapResearchProgram.put("staffId", el.getString("staffId"));
				mapResearchProgram.put("researchProgramName", el.getString("researchProgramName"));
				mapResearchProgram.put("fromDate", el.getString("fromDate"));
				mapResearchProgram.put("thruDate", el.getString("thruDate"));
				listResearchProgram.add(mapResearchProgram);
			}
			result.put("researchProgram", listResearchProgram);
			return result;
		
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	public static Map<String, Object> getResearchProgramOfLoginStaff(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String userLoginId = (String)userLogin.get("userLoginId");
		
		
		
//		Debug.log(module + "::getEducationProgress, userLoginId = " + userLoginId);
		
		
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			conditions.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,userLoginId));
			List<GenericValue> list = delegator.findList("ResearchProgram", 
					EntityCondition.makeCondition(conditions), 
					null, null, 
					null, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			result.put("researchProgram", list);
			return result;
		
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createResearchProgram(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		//String staffId = (String) context.get("staffId");
		String staffId = (String)context.get("staffId");
		
		String researchProgramName = (String) context.get("researchProgramName");
		String fromDate = (String) context.get("fromDate");
		String thruDate = (String) context.get("thruDate");
		
//		System.out.println("EducationProgress::createEducationProgress, staffId = " + staffId + 
//				", educationType = " + educationType + ", institution = " + institution + ", speciality = " + speciality);
		
		GenericValue gv = delegator.makeValue("ResearchProgram");

		gv.put("researchProgramId", delegator.getNextSeqId("ResearchProgram"));

		try {
			gv.put("staffId", staffId);
			gv.put("researchProgramName", researchProgramName);
			//gv.put("graduateDate", Date.valueOf(graduateDate));
			if(fromDate != null && !fromDate.equals("")){
				gv.put("fromDate", new Date(Long.valueOf(fromDate)));
			}
			if(thruDate != null && !thruDate.equals("")){
				gv.put("thruDate", new Date(Long.valueOf(thruDate)));
			}

			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("researchProgram", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
	}
	
	public static Map<String, Object> deleteResearchProgram(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String researchProgramId = (String)context.get("researchProgramId");
        try{
        	GenericValue gv = delegator.findOne("ResearchProgram", UtilMisc.toMap("researchProgramId",researchProgramId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + researchProgramId);
        	} else {
        		retSucc.put("result", "not found record with id: " + researchProgramId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateResearchProgram(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		String researchProgramName = (String) context.get("researchProgramName");
		String institution = (String) context.get("institution");
		String fromDate = (String) context.get("fromDate");
		String thruDate = (String) context.get("thruDate");
		String researchProgramId = (String) context.get("researchProgramId");
		
		try{
			GenericValue gv = delegator.findOne("ResearchProgram", false, UtilMisc.toMap("researchProgramId",researchProgramId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("researchProgramName", researchProgramName);
				//gv.put("graduateDate", Date.valueOf(graduateDate));
				if(fromDate != null && !fromDate.equals("")){
					gv.put("fromDate", new Date(Long.valueOf(fromDate)));
				}
				if(thruDate != null && !thruDate.equals("")){
					gv.put("thruDate", new Date(Long.valueOf(thruDate)));
				}
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("staffId", staffId);
				rs.put("researchProgramName", researchProgramName);
				rs.put("fromDate", fromDate);
				rs.put("thruDate", thruDate);
				rs.put("researchProgramId", researchProgramId);
				
				retSucc.put("researchProgram", rs);
        		retSucc.put("message", "updated record with id: " + researchProgramId);
        	} else {
        		retSucc.put("message", "not found record with id: " + researchProgramId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}
