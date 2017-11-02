package src.org.ofbiz.bkeuniv.staffresearchspeciality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import javolution.util.FastList;
import javolution.util.FastMap;

public class StaffResearchSpeciality{
	public static Map<String, Object> getStaffResearchSpeciality(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		//System.out.println(module + "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " + u2);
		//Debug.log(module + "::getEducationProgress, Debug.log u1 = " + u1 + ", u2 = " + u2);
		
		
		String[] keys = {"staffResearchSpecialityId", "staffId", "researchSpecialityId", "fromDate", "thruDate"};
		String[] search = {"researchSpecialityId"};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if( index == -1) {
						condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}
			List<GenericValue> list = delegator.findList("StaffResearchSpeciality", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listStaffResearchSpeciality = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> mapStaffResearchSpeciality = FastMap.newInstance();
				mapStaffResearchSpeciality.put("staffResearchSpecialityId", el.getString("staffResearchSpecialityId"));
				mapStaffResearchSpeciality.put("staffId", el.getString("staffId"));
				mapStaffResearchSpeciality.put("researchSpecialityId", el.getString("researchSpecialityId"));
				mapStaffResearchSpeciality.put("fromDate", el.getString("fromDate"));
				mapStaffResearchSpeciality.put("thruDate", el.getString("thruDate"));
				listStaffResearchSpeciality.add(mapStaffResearchSpeciality);
			}
			result.put("staffResearchSpeciality", listStaffResearchSpeciality);
			return result;
		
		} catch (Exception e) {
			System.out.print("Staff Research Speciality Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	

	
	public static Map<String, Object>createStaffResearchSpeciality(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		//String staffId = (String)userLogin.get("userLoginId");
		List staffId = (List)context.get("staffId[]");
		List researchSpecialityId = (List)context.get("researchSpecialityId[]");
		String fromDate = (String)context.get("fromDate");
		String thruDate = (String)context.get("thruDate");
		
		System.out.println(staffId);
		System.out.println(researchSpecialityId);
		System.out.println(fromDate);
		System.out.println(thruDate);
	
					
		GenericValue gv = delegator.makeValue("StaffResearchSpeciality");

		gv.put("staffResearchSpecialityId", delegator.getNextSeqId("StaffResearchSpeciality"));
//		gv.get("staffResearchSpecialityId");
//		System.out.println(gv.get("staffResearchSpecialityId"));
	
		try {
			gv.put("staffId", staffId.get(0));
			gv.put("researchSpecialityId", researchSpecialityId.get(0));
			gv.put("fromDate", Date.valueOf(fromDate));
			gv.put("thruDate", Date.valueOf(thruDate));
			
			System.out.println(staffId);
			System.out.println(researchSpecialityId);
			System.out.println(fromDate);
			System.out.println(thruDate);
			
			

			delegator.create(gv);
			System.out.println("1");
			
			Map<String, Object> mapStaffResearchSpeciality = FastMap.newInstance();
			mapStaffResearchSpeciality.put("staffResearchSpecialityId", gv.getString("staffResearchSpecialityId"));
			mapStaffResearchSpeciality.put("staffId[]", staffId.get(0));
			mapStaffResearchSpeciality.put("researchSpecialityId[]", researchSpecialityId.get(0));
			mapStaffResearchSpeciality.put("fromDate", fromDate);
			mapStaffResearchSpeciality.put("thruDate", thruDate);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		retSucc.put("staffResearchSpeciality", gv);
		System.out.println("2");
		retSucc.put("message", "Create new row");
		System.out.println("3");
		return retSucc;
	}
	

	public static Map<String, Object> deleteStaffResearchSpeciality(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String staffResearchSpecialityId = (String)context.get("staffResearchSpecialityId");
        try{
        	GenericValue gv = delegator.findOne("StaffResearchSpeciality", UtilMisc.toMap("staffResearchSpecialityId",staffResearchSpecialityId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + staffResearchSpecialityId);
        	} else {
        		retSucc.put("result", "not found record with id: " + staffResearchSpecialityId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}

	public static Map<String, Object> updateStaffResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		System.out.println("11");
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		System.out.println("12");
		
		String staffResearchSpecialityId = (String)context.get("staffResearchSpecialityId");	
		System.out.println("13");
		List staffId = (List)context.get("staffId[]");	
		System.out.println("14");
		List researchSpecialityId = (List)context.get("researchSpecialityId[]");
		System.out.println("15");
		String fromDate = (String)context.get("fromDate");
		System.out.println("16");
		String thruDate = (String)context.get("thruDate");
		System.out.println("17");
		
		
		System.out.println(staffId);
		System.out.println(researchSpecialityId);
		System.out.println(fromDate);
		System.out.println(thruDate);
		try{
			GenericValue gv = delegator.findOne("StaffResearchSpeciality", false, UtilMisc.toMap("staffResearchSpecialityId",staffResearchSpecialityId));
			if(gv != null){
				gv.put("staffId", staffId.get(0));
				System.out.println("1");
				gv.put("researchSpecialityId", researchSpecialityId.get(0));
				System.out.println("2");
				gv.put("fromDate", Date.valueOf(fromDate));
				System.out.println("3");
				gv.put("thruDate", Date.valueOf(thruDate));
				System.out.println("4");
				
				
				
				
				delegator.store(gv);	
				System.out.println(staffId);
				System.out.println(researchSpecialityId);
				System.out.println(fromDate);
				System.out.println(thruDate);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("staffId", staffId);
				rs.put("researchSpecialityId", researchSpecialityId);
				rs.put("fromDate", fromDate);
				rs.put("thruDate", thruDate);
				
				retSucc.put("staffResearchSpeciality", rs);
				System.out.println("5");
				System.out.println(researchSpecialityId);
				System.out.println(fromDate);
				System.out.println(thruDate);
        		retSucc.put("message", "updated record with id: " + staffResearchSpecialityId);
        	} else {
        		retSucc.put("message", "not found record with id: " + staffResearchSpecialityId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
		
}