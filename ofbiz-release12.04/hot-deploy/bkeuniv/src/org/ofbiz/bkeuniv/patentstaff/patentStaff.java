package org.ofbiz.bkeuniv.patentstaff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class patentStaff {
	public final static String module = patentStaff.class.getName();
	public static Map<String, Object> getPatent(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		//System.out.println("1");
		
		//String u1 = (String)ctx.getAttribute("userLoginId");
		//String u2 = (String)context.get("userLoginId");
		
		//if(u1 == null) u1 = "NULL";
		//if(u2 == null) u2 = "NULL";
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = null;
		if(userLogin != null)
			staffId = (String)userLogin.getString("userLoginId");
		
		String[] keys = {"patentId", "patentName", "year", "staffId"};
		String[] search = {"patentName"};
		
		try{
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			if(staffId != null)
				conditions.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
			
			
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			System.out.println("2");
			for(String key: keys){
				Object el = context.get(key);
				if(!(el == null||el == (""))){
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if(index == -1){
						condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
						
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);	
					}
					conditions.add(condition);
				}
			}
				System.out.println(conditions.size());
				List<GenericValue> list = delegator.findList("Patent", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
				Map<String, Object> result = ServiceUtil.returnSuccess();
				System.out.println(list.size());
				List<Map<String, Object>> lstPatent = FastList.newInstance();
				for(GenericValue el: list){
					Map<String, Object> mapPatent = FastMap.newInstance();
					mapPatent.put("patentId", el.getString("patentId"));
					mapPatent.put("patentName", el.getString("patentName"));
					mapPatent.put("staffId", el.getString("staffId"));
					mapPatent.put("year", el.getString("year"));
					lstPatent.add(mapPatent);
				}
				System.out.println(lstPatent.size());
				result.put("patent", lstPatent);
				return result;
		} catch (Exception e){
			System.out.print("Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}	
	}
	
	public static Map<String, Object> createPatent(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String patentName = (String) context.get("patentName");
		String staffId = (String) context.get("staffId");
		if(staffId == null){
			staffId = (String)userLogin.getString("userLoginId");
		}
		String year = (String) context.get("year");
		
		GenericValue gv = delegator.makeValue("Patent");
		gv.put("patentId",delegator.getNextSeqId("Patent"));
		try{
			gv.put("patentName", patentName);
			gv.put("year", Long.valueOf(year));
			gv.put("staffId", staffId);
			
			delegator.create(gv);
		}catch(Exception ex){
			System.out.println("aaa");
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("patent", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
		
	}
	
	public static Map<String, Object> deletePatent(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String patentId = (String)context.get("patentId");
        try{
        	GenericValue gv = delegator.findOne("Patent", UtilMisc.toMap("patentId",patentId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + patentId);
        	} else {
        		retSucc.put("result", "not found record with id: " + patentId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updatePatent(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String patentName = (String) context.get("patentName");
		String year = (String) context.get("year");
		String staffId = (String) context.get("staffId");
		if(staffId == null){
			staffId = (String)userLogin.getString("userLoginId");
		}
		String patentId = (String) context.get("patentId");
		
		try{
			GenericValue gv = delegator.findOne("Patent", false, UtilMisc.toMap("patentId",patentId));
			if(gv != null){
				gv.put("patentName", patentName);
				gv.put("year", Long.valueOf(year));
				gv.put("staffId", staffId);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("patentId", patentId);
				rs.put("patentName", patentName);
				rs.put("year", year);
				rs.put("staffId", staffId);
				
				retSucc.put("patent", rs);
        		retSucc.put("message", "updated record with id: " + patentId);
        	} else {
        		retSucc.put("message", "not found record with id: " + patentId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}

}
