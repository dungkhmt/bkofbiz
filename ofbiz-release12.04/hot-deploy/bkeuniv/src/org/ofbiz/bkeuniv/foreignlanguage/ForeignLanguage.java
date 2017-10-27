package src.org.ofbiz.bkeuniv.foreignlanguage;

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

public class ForeignLanguage {
	public final static String module = ForeignLanguage.class.getName();
	
	public static Map<String, Object> getForeignLanguage(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		String[] keys = {"foreignLanguageId", "staffId", "foreignLanguageCatalogId", "listen", "speaking", "reading", "writing"};
		String[] search = {"staffId"};
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
			
			List<GenericValue> list = delegator.findList("ForeignLanguage", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listMap = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> map = FastMap.newInstance();
				for(String key: keys) {
					map.put(key, el.getString(key));
					
				}
				listMap.add(map);
			}
			result.put("foreignLanguage", listMap);
			result.put("result", listMap);
			result.put("count", String.valueOf(listMap.size()));
			return result;
		
		} catch (Exception e) {
			System.out.print("Foreign Language Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createForeignLanguage(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String foreignLanguageId = (String) context.get("foreignLanguageId");
		List<String> staffId = (List<String>) context.get("staffId[]");
		List<String> foreignLanguageCatalogId = (List<String>) context.get("foreignLanguageCatalogId[]");
		String listen = (String) context.get("listen");
		String speaking = (String) context.get("speaking");
		String reading = (String) context.get("reading");
		String writing = (String) context.get("writing");
		
		GenericValue gv = delegator.makeValue("ForeignLanguage");

		try {
			gv.put("foreignLanguageId", foreignLanguageId);
			gv.put("staffId", staffId.get(0));
			gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId.get(0));
			gv.put("listen", listen);
			gv.put("speaking", speaking);
			gv.put("reading", reading);
			gv.put("writing", writing);
									
			delegator.create(gv);
			System.out.println("8");
			Map<String, Object> rs = new HashMap<String, Object>();
			rs.put("foreignLanguageId", foreignLanguageId);
			rs.put("staffId", staffId.get(0));
			rs.put("foreignLanguageCatalogId", foreignLanguageCatalogId.get(0));
			rs.put("listen", listen);
			rs.put("speaking", speaking);
			rs.put("reading", reading);
			rs.put("writing", writing);
			
			System.out.println(staffId.get(0));			
						
			retSucc.put("foreignLanguage", rs);
			System.out.println("16");
			retSucc.put("message", "Create new row");
			return retSucc;
		} catch (Exception e) {
			System.out.print("Foreign Language Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> deleteForeignLanguage(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String foreignLanguageId = (String)context.get("foreignLanguageId");
        try{
        	GenericValue gv = delegator.findOne("ForeignLanguage", UtilMisc.toMap("foreignLanguageId",foreignLanguageId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + foreignLanguageId);
        	} else {
        		retSucc.put("result", "not found record with id: " + foreignLanguageId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateForeignLanguage(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		System.out.println("1");
		Delegator delegator = ctx.getDelegator();
		System.out.println("2");
		LocalDispatcher dispatch = ctx.getDispatcher();
		System.out.println("3");
		
		String foreignLanguageId = (String) context.get("foreignLanguageId");
		System.out.println("4");
		List<String> staffId = (List<String>) context.get("staffId[]");
		System.out.println(staffId);
		List<String> foreignLanguageCatalogId = (List<String>) context.get("foreignLanguageCatalogId[]");
		System.out.println(foreignLanguageCatalogId);
		String listen = (String) context.get("listen");
		String speaking = (String) context.get("speaking");
		String reading = (String) context.get("reading");
		String writing = (String) context.get("writing");
		
		try{
			GenericValue gv = delegator.findOne("ForeignLanguage", false, UtilMisc.toMap("foreignLanguageId",foreignLanguageId));
			if(gv != null){
				System.out.println("7");
				gv.put("foreignLanguageId", foreignLanguageId);
				gv.put("staffId",staffId.get(0));
				System.out.println(staffId);
				gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId.get(0));
				gv.put("listen", listen);
				gv.put("speaking", speaking);
				gv.put("reading", reading);
				gv.put("writing", writing);
				
				delegator.store(gv);
				System.out.println("8");
				Map<String, Object> rs = new HashMap<String, Object>();
				System.out.println("9");
				rs.put("foreignLanguageId", foreignLanguageId);
				rs.put("staffId", staffId.get(0));
				rs.put("foreignLanguageCatalogId", foreignLanguageCatalogId.get(0));
				rs.put("listen", listen);
				rs.put("speaking", speaking);
				rs.put("reading", reading);
				rs.put("writing", writing);
				System.out.println("10");
				
				retSucc.put("foreignLanguage", rs);
        		retSucc.put("message", "updated record with id: " + foreignLanguageId);
        	} else {
        		retSucc.put("message", "not found record with id: " + foreignLanguageId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}