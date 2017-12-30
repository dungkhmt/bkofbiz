package org.ofbiz.bkeuniv.scientificserviceexperience;

import java.util.HashMap;
import java.util.List;
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

public class ScientificServiceExperience {
	public static Map<String, Object> getScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String scientificServiceExperienceId = (String)context.get("scientificServiceExperienceId");
		String staffId = (String)context.get("staffId");
		String description = (String)context.get("description");
		String quantity = (String)context.get("quantity");
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			EntityCondition entity;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list;
			if(scientificServiceExperienceId==null) {
				System.out.println("empty");
				list = delegator.findList("ScientificServiceExperience", null, null, null, findOptions, true);
			} else {
				entity = EntityCondition.makeCondition("scientificServiceExperienceId", EntityOperator.EQUALS, scientificServiceExperienceId);
				list = delegator.findList("ScientificServiceExperience", entity, null, null, findOptions, true);				
			}
			System.out.println("list "+list);
			result.put("listScientificServiceExperience", list);
			return result;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	public static Map<String, Object> createScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
		System.out.println("createScientificServiceExperience");
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String staffId = (String)context.get("staffId");
		String description = (String)context.get("description");
		Long quantity = Long.parseLong((String)context.get("quantity"));
		System.out.println("ScientificServiceExperience"+" "+staffId+" "+description+" "+quantity);
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			GenericValue gv=delegator.makeValue("ScientificServiceExperience");
			gv.put("scientificServiceExperienceId", delegator.getNextSeqId("ScientificServiceExperience"));
			gv.put("staffId", staffId);
			gv.put("description", description);
			gv.put("quantity", quantity);
			System.out.println(gv);
			delegator.create(gv);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("scientificServiceExperienceId", gv.get("scientificServiceExperienceId"));
			map.put("staffId",  gv.get("staffId"));
			map.put("description", gv.get("description"));
			map.put("quantity", gv.get("quantity"));
			result.put("object", map);
			return result;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		
	}
	public static Map<String, Object> updateScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
		System.out.println("updateScientificServiceExperience");
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String scientificServiceExperienceId = (String)context.get("scientificServiceExperienceId");
		String staffId = (String)context.get("staffId");
		String description = (String)context.get("description");
		Long quantity = Long.parseLong((String)context.get("quantity"));
		System.out.println("ScientificServiceExperience update "+" "+staffId+" "+description+" "+quantity+" "+scientificServiceExperienceId);
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			GenericValue gv=delegator.findOne("ScientificServiceExperience", false, UtilMisc.toMap("scientificServiceExperienceId",scientificServiceExperienceId));
			//gv.put("scientificServiceExperienceId", delegator.getNextSeqId("ScientificServiceExperience"));
			gv.put("staffId", staffId);
			gv.put("description", description);
			gv.put("quantity", quantity);
			System.out.println(gv);
			delegator.store(gv);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("scientificServiceExperienceId", gv.get("scientificServiceExperienceId"));
			map.put("staffId",  gv.get("staffId"));
			map.put("description", gv.get("description"));
			map.put("quantity", gv.get("quantity"));
			result.put("object", map);
			return result;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		
	}
	public static Map<String, Object> deleteScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
		//System.out.println("updateScientificServiceExperience");
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String scientificServiceExperienceId = (String)context.get("scientificServiceExperienceId");
		System.out.println("ScientificServiceExperience update "+" "+scientificServiceExperienceId);
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			GenericValue gv=delegator.findOne("ScientificServiceExperience", false, UtilMisc.toMap("scientificServiceExperienceId",scientificServiceExperienceId));
			//gv.put("scientificServiceExperienceId", delegator.getNextSeqId("ScientificServiceExperience"));
			delegator.removeValue(gv);
			result.put("id", scientificServiceExperienceId);
			return result;
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		
	}
}
