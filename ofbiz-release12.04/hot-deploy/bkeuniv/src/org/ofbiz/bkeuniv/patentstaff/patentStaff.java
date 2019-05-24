package org.ofbiz.bkeuniv.patentstaff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
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
	public static final String ENABLED = "ENABLED";
	public static final String DISABLED = "DISABLED";
	
	public static Map<String, Object> getPatentCategory(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//List<GenericValue> list = FastList.newInstance();
		try{
			List<GenericValue> list = delegator.findList("PatentCategory", 
					null,null,null,null,false);
			Debug.log(module + "::getPatentCategory, list.sz = " + list.size());
			retSucc.put("patentCategory", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getPatent(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = null;
		if(userLogin != null)
			staffId = (String)userLogin.getString("userLoginId");
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,ENABLED));
			conds.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
			List<GenericValue> list = delegator.findList("PatentView", 
					EntityCondition.makeCondition(conds),
					null,null,null,false);
			retSucc.put("patent", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		/*
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
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
		*/	
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
		List<String> patentCategoryIds = (List<String>) context.get("patentCategoryId[]");
		List<String> academicYearIds = (List<String>) context.get("academicYearId[]");
		
		String year = (String) context.get("year");
		
		GenericValue gv = delegator.makeValue("Patent");
		gv.put("patentId",delegator.getNextSeqId("Patent"));
		try{
			gv.put("patentName", patentName);
			gv.put("year", Long.valueOf(year));
			gv.put("staffId", staffId);
			gv.put("statusId", ENABLED);
			if(patentCategoryIds != null && patentCategoryIds.size() > 0){
				String patentCategoryId = patentCategoryIds.get(0);
				gv.put("patentCategoryId", patentCategoryId);
				
			}
			if(academicYearIds != null && academicYearIds.size() > 0){
				String academicYearId = academicYearIds.get(0);
				gv.put("academicYearId", academicYearId);
			}
			
			delegator.create(gv);
		}catch(Exception ex){
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
        Debug.log(module + "::deletePatent, patentId = " + patentId);
        
        try{
        	GenericValue gv = delegator.findOne("Patent", UtilMisc.toMap("patentId",patentId), false);
        	if(gv != null){
        		//delegator.removeValue(gv);
        		gv.put("statusId", DISABLED);
        		delegator.store(gv);
        		retSucc.put("message", "deleted record with id: " + patentId);
        		//retSucc.put("patent", gv);
        	} else {
        		retSucc.put("message", "not found record with id: " + patentId);
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
		List<String> patentCategoryIds = (List<String>) context.get("patentCategoryId[]");
		List<String> academicYearIds = (List<String>) context.get("academicYearId[]");
		
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
				if(patentCategoryIds != null && patentCategoryIds.size() > 0){
					String patentCategoryId = patentCategoryIds.get(0);
					gv.put("patentCategoryId", patentCategoryId);
					
				}
				if(academicYearIds != null && academicYearIds.size() > 0){
					String academicYearId = academicYearIds.get(0);
					gv.put("academicYearId", academicYearId);
				}
				
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
