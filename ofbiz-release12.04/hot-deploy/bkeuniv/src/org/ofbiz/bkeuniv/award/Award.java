package org.ofbiz.bkeuniv.award;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;

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
import org.ofbiz.bkeuniv.educationprogress.EducationProgress;
import org.ofbiz.utils.BKEunivUtils;

public class Award {
	
	public final static String module = Award.class.getName();
	public static Map<String, Object> createAward(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String staffId = (String) context.get("staffId");
		if(staffId == null){
			staffId = (String)userLogin.getString("userLoginId");
		}
		String description = (String) context.get("description");
		String year = (String) context.get("year");
		
		GenericValue gv = delegator.makeValue("Award");
		gv.put("awardId",delegator.getNextSeqId("Award"));
		try{
			gv.put("description", description);
			gv.put("year", Long.valueOf(year));
			gv.put("staffId", staffId);
			
			delegator.create(gv);
		}catch(Exception ex){
			//System.out.println("aaa");
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("award", gv);
		return retSucc;
		
	}
	
	public static Map<String, Object> getAward(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		//String u1 = (String)ctx.getAttribute("userLoginId");
		//String u2 = (String)context.get("userLoginId");
		
		//if(u1 == null) u1 = "NULL";
		//if(u2 == null) u2 = "NULL";
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = (String)userLogin.getString("userLoginId");
		
		String[] keys = {"awardId", "description", "year", "staffId"};
		String[] search = {""};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			conditions.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
			
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
			
			List<GenericValue> list = delegator.findList("Award", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map<String, Object>> listAward = FastList.newInstance();
			System.out.print(list.size());
			for(GenericValue el: list) {
				Map<String, Object> mapAward = FastMap.newInstance();
				mapAward.put("awardId", el.getString("awardId"));
				mapAward.put("year", el.getString("year"));
				mapAward.put("staffId", el.getString("staffId"));
				mapAward.put("description", el.getString("description"));
				listAward.add(mapAward);
			}
			System.out.print(listAward.size());
			result.put("award", listAward);
			return result;
		
		} catch (Exception e) {
			//System.out.print("Error");
			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> deleteAward(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String awardId = (String)context.get("awardId");
        try{
        	GenericValue gv = delegator.findOne("Award", UtilMisc.toMap("awardId",awardId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + awardId);
        	} else {
        		retSucc.put("result", "not found record with id: " + awardId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateAward(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String description = (String) context.get("description");
		String year = (String) context.get("year");
		String staffId = (String) context.get("staffId");
		if(staffId == null){
			staffId = (String)userLogin.getString("userLoginId");
		}
		String awardId = (String) context.get("awardId");
		
		try{
			GenericValue gv = delegator.findOne("Award", false, UtilMisc.toMap("awardId",awardId));
			if(gv != null){
				gv.put("description", description);
				gv.put("year", Long.valueOf(year));
				gv.put("staffId", staffId);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("awardId", awardId);
				rs.put("description", description);
				rs.put("staffId", staffId);
				rs.put("year", year);
				
				retSucc.put("award", rs);
        		retSucc.put("message", "updated record with id: " + awardId);
        	} else {
        		retSucc.put("message", "not found record with id: " + awardId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}