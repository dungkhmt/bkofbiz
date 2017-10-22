package src.org.ofbiz.bkeuniv.foreignlanguagecatalog;

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

import javolution.util.FastList;
import javolution.util.FastMap;


public class ForeignLanguageCatalog {
	public final static String module = ForeignLanguageCatalog.class.getName();
	
	public static Map<String, Object> getForeignLanguageCatalog(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		String[] keys = {"foreignLanguageCatalogId", "foreignLanguageCatalogName"};
		String[] search = {"foreignLanguageCatalogName"};
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
			
			List<GenericValue> list = delegator.findList("ForeignLanguageCatalog", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listMap = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> map = FastMap.newInstance();
				for(String key: keys) {
					map.put(key, el.getString(key));
					
				}
				listMap.add(map);
			}
			result.put("foreignLanguageCatalog", listMap);
			result.put("result", listMap);
			result.put("count", String.valueOf(listMap.size()));
			return result;
		
		} catch (Exception e) {
			System.out.print("Foreign Language Catalog Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createForeignLanguageCatalog(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String foreignLanguageCatalogId = (String) context.get("foreignLanguageCatalogId");
		String foreignLanguageCatalogName = (String) context.get("foreignLanguageCatalogName");
		
		
		GenericValue gv = delegator.makeValue("ForeignLanguageCatalog");

		try {
			gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
			gv.put("foreignLanguageCatalogName", foreignLanguageCatalogName);
									
			delegator.create(gv);
			System.out.println("8");
			Map<String, Object> rs = new HashMap<String, Object>();
			rs.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
			rs.put("foreignLanguageCatalogName", foreignLanguageCatalogName);
						
			retSucc.put("foreignLanguageCatalog", rs);
			retSucc.put("message", "Create new row");
			return retSucc;
		} catch (Exception e) {
			System.out.print("Foreign Language Catalog Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> deleteForeignLanguageCatalog(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String foreignLanguageCatalogId = (String)context.get("foreignLanguageCatalogId");
        try{
        	GenericValue gv = delegator.findOne("ForeignLanguageCatalog", UtilMisc.toMap("foreignLanguageCatalogId",foreignLanguageCatalogId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + foreignLanguageCatalogId);
        	} else {
        		retSucc.put("result", "not found record with id: " + foreignLanguageCatalogId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateForeignLanguageCatalog(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String foreignLanguageCatalogId = (String) context.get("foreignLanguageCatalogId");
		String foreignLanguageCatalogName = (String) context.get("foreignLanguageCatalogName");
		
		try{
			GenericValue gv = delegator.findOne("ForeignLanguageCatalog", false, UtilMisc.toMap("foreignLanguageCatalogId",foreignLanguageCatalogId));
			if(gv != null){
				System.out.println("7");
				gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
				gv.put("foreignLanguageCatalogName", foreignLanguageCatalogName);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				System.out.println("9");
				rs.put("foreignLanguageCatalogId", foreignLanguageCatalogId);
				rs.put("foreignLanguageCatalogName", foreignLanguageCatalogName);
				
				retSucc.put("foreignLanguageCatalog", rs);
        		retSucc.put("message", "updated record with id: " + foreignLanguageCatalogId);
        	} else {
        		retSucc.put("message", "not found record with id: " + foreignLanguageCatalogId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}