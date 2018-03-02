package src.org.ofbiz.bkeuniv.researchdomain;

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
import src.org.ofbiz.utils.BKEunivUtils;

public class ResearchDomain {
	public final static String module = ResearchDomain.class.getName();
	
	public static Map<String, Object> getResearchDomain(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		//System.out.println(module + "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " + u2);
		//Debug.log(module + "::getEducationProgress, Debug.log u1 = " + u1 + ", u2 = " + u2);
		
		
		String[] keys = {"researchDomainId", "researchDomainName"};
		String[] search = {"researchDomainName"};
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
			
			List<GenericValue> list = delegator.findList("ResearchDomain", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listMap = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> map = FastMap.newInstance();
				for(String key: keys) {
					map.put(key, el.getString(key));
					
				}
				listMap.add(map);
			}
			result.put("researchDomain", listMap);
			result.put("result", listMap);
			result.put("count", String.valueOf(listMap.size()));
			return result;
		
		} catch (Exception e) {
			System.out.print("Research Domain Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	
	public static Map<String, Object> createResearchDomain1(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String researchDomainId = (String) context.get("researchDomainId");
		String researchDomainName = (String) context.get("researchDomainName");
		
		GenericValue gv = delegator.makeValue("ResearchDomain");

		try {
			gv.put("researchDomainId", researchDomainId);
			gv.put("researchDomainName", researchDomainName);
			
			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("researchDomain", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
	}
	
	public static Map<String, Object> createResearchDomain(DispatchContext ctx, Map<String, ? extends Object> context) {
		System.out.println("createResearchDomain");
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String researchDomainId = (String) context.get("researchDomainId");
		String researchDomainName = (String) context.get("researchDomainName");
		System.out.println("ResearchDomain"+" "+researchDomainId+" "+researchDomainName);
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			GenericValue gv=delegator.makeValue("ResearchDomain");
			//gv.put("researchDomainId", delegator.getNextSeqId("ResearchDomain"));
			gv.put("researchDomainId", researchDomainId);
			gv.put("researchDomainName", researchDomainName);
			
			System.out.println(gv);
			delegator.create(gv);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("researchDomainId", gv.get("researchDomainId"));
			map.put("researchDomainName",  gv.get("researchDomainName"));
//			map.put("description", gv.get("description"));
//			map.put("quantity", gv.get("quantity"));
			
			result.put("researchDomain", gv);
			result.put("message", "Create new row");
			return result;
//			result.put("object", map);
//			return result;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		
	}
	
	public static Map<String, Object> deleteResearchDomain(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String researchDomainId = (String)context.get("researchDomainId");
        try{
        	GenericValue gv = delegator.findOne("ResearchDomain", UtilMisc.toMap("researchDomainId",researchDomainId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + researchDomainId);
        	} else {
        		retSucc.put("result", "not found record with id: " + researchDomainId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateResearchDomain(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String researchDomainId = (String) context.get("researchDomainId");
		String researchDomainName = (String) context.get("researchDomainName");
		
		try{
			GenericValue gv = delegator.findOne("ResearchDomain", false, UtilMisc.toMap("researchDomainId",researchDomainId));
			if(gv != null){
				
				gv.put("researchDomainId", researchDomainId);
				gv.put("researchDomainName", researchDomainName);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("researchDomainId", researchDomainId);
				rs.put("researchDomainName", researchDomainName);
				
				retSucc.put("researchDomain", rs);
        		retSucc.put("message", "updated record with id: " + researchDomainId);
        	} else {
        		retSucc.put("message", "not found record with id: " + researchDomainId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}