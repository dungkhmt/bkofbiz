package src.org.ofbiz.bkeuniv.award;

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

public class Award {
	public static Map<String, Object> getAward(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		String awardId = (String)context.get("awardId");
		String staffId = (String)context.get("staffId");
		String description = (String)context.get("description");
		String year = (String)context.get("year");
		
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			EntityCondition entity;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list;
			if(awardId==null) {
				System.out.println("aaa");
				list = delegator.findList("Award", null, null, null, findOptions, true);				
			} else{
				entity = EntityCondition.makeCondition("awardId", EntityOperator.EQUALS, awardId);
				list = delegator.findList("Award", entity, null, null, findOptions, true);			
			}
			
			List<Map> lstAward = FastList.newInstance();
			for(GenericValue el: list){
				Map<String, Object> mapAward = FastMap.newInstance();
				mapAward.put("awardId", el.getString("awardId"));
				mapAward.put("staffId", el.getString("staffId"));
				mapAward.put("description", el.getString("description"));
				mapAward.put("year", el.getString("year"));
				lstAward.add(mapAward);
			}
			result.put("award", lstAward);
			return result;
		
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	public static Map<String, Object> createAward(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String staffId = (String) context.get("staffId");
		String description = (String) context.get("description");
		String year = (String) context.get("year");
		
		GenericValue gv = delegator.makeValue("Award");
		try{
			gv.put("staffId", staffId);
			gv.put("description", description);
			gv.put("year", year);
		}catch(Exception ex){
			System.out.println("aaa");
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		Map<String, Object> mapAward = FastMap.newInstance();
		mapAward.put("awardId", gv.getString("awardId"));
		mapAward.put("staffId", staffId);
		mapAward.put("description", description);
		mapAward.put("year", year);
		
		retSucc.put("award", mapAward);
		return retSucc;
		
	}
}
