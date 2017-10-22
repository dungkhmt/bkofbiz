package src.org.ofbiz.bkeuniv.researchspeciality;

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

public class ResearchSpeciality {
	public final static String module = ResearchSpeciality.class.getName();
	
	public static Map<String, Object> getResearchSpeciality(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		String[] keys = {"researchSpecialityId", "researchSpecialityName", "researchDomainId"};
		String[] search = {"researchSpecialityName"};
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
			
			List<GenericValue> list = delegator.findList("ResearchSpeciality", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listMap = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> map = FastMap.newInstance();
				for(String key: keys) {
					System.out.println("researchSpeciality: " + key);
					map.put(key, el.getString(key));
					
				}
				listMap.add(map);
			}
			result.put("researchSpeciality", listMap);
			result.put("result", listMap);
			result.put("count", String.valueOf(listMap.size()));
			return result;
		
		} catch (Exception e) {
			System.out.print("Research Speciality Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createResearchSpeciality(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String researchSpecialityName = (String) context.get("researchSpecialityName");
		List<String> researchDomainId = (List<String>) context.get("researchDomainId[]");
		
		GenericValue gv = delegator.makeValue("ResearchSpeciality");

		try {
			gv.put("researchSpecialityId", researchSpecialityId);
			gv.put("researchSpecialityName", researchSpecialityName);
			gv.put("researchDomainId", researchDomainId.get(0));
									
			delegator.create(gv);
			System.out.println("8");
			Map<String, Object> rs = new HashMap<String, Object>();
			rs.put("researchSpecialityId", researchSpecialityId);
			rs.put("researchSpecialityName", researchSpecialityName);
			
			rs.put("researchDomainId", researchDomainId.get(0));
			System.out.println(researchDomainId.get(0));			
						
			retSucc.put("researchSpeciality", rs);
			System.out.println("16");
			retSucc.put("message", "Create new row");
			return retSucc;
		} catch (Exception e) {
			System.out.print("Research Speciality Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> deleteResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String researchSpecialityId = (String)context.get("researchSpecialityId");
        try{
        	GenericValue gv = delegator.findOne("ResearchSpeciality", UtilMisc.toMap("researchSpecialityId",researchSpecialityId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + researchSpecialityId);
        	} else {
        		retSucc.put("result", "not found record with id: " + researchSpecialityId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		System.out.println("1");
		Delegator delegator = ctx.getDelegator();
		System.out.println("2");
		LocalDispatcher dispatch = ctx.getDispatcher();
		System.out.println("3");
		
		String researchSpecialityId = (String) context.get("researchSpecialityId");
		System.out.println("4");
		String researchSpecialityName = (String) context.get("researchSpecialityName");
		System.out.println("5");
		List<String> researchDomainId = (List<String>) context.get("researchDomainId[]");
		System.out.println(researchDomainId);
		
		try{
			GenericValue gv = delegator.findOne("ResearchSpeciality", false, UtilMisc.toMap("researchSpecialityId",researchSpecialityId));
			if(gv != null){
				System.out.println("7");
				gv.put("researchSpecialityId", researchSpecialityId);
				gv.put("researchSpecialityName", researchSpecialityName);
				gv.put("researchDomainId",researchDomainId.get(0));
				System.out.println(researchDomainId);
				
				delegator.store(gv);
				System.out.println("8");
				Map<String, Object> rs = new HashMap<String, Object>();
				System.out.println("9");
				rs.put("researchSpecialityId", researchSpecialityId);
				rs.put("researchSpecialityName", researchSpecialityName);
				rs.put("researchDomainId", researchDomainId.get(0));
				System.out.println("10");
				
				retSucc.put("researchSpeciality", rs);
        		retSucc.put("message", "updated record with id: " + researchSpecialityId);
        	} else {
        		retSucc.put("message", "not found record with id: " + researchSpecialityId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}