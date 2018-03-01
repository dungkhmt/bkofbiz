package org.ofbiz.bkeuniv.scientificserviceexperience;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.projectdeclaration.ProjectDeclaration;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class ScientificServiceExperience {
	
	public static String module = ScientificServiceExperience.class.getName();
	
	public static Map<String, Object> getScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		System.out.println("StaffId: "+ staffId);
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId));
			List<GenericValue> listSSE = delegator.findList("ScientificServiceExperienceView", 
																EntityCondition.makeCondition(conds), 
																null, null, null, false);
			
			List<Map> scientificServiceExperiences = FastList.newInstance();
			for(GenericValue gv : listSSE){
				Map<String, Object> map = FastMap.newInstance();
					map.put("scientificServiceExperienceId", gv.getString("scientificServiceExperienceId"));
					map.put("staffId", gv.getString("staffId"));
					map.put("staffName", gv.getString("staffName"));
					map.put("description", gv.getString("description"));
					map.put("quantity", gv.getString("quantity"));
				
					scientificServiceExperiences.add(map);
			}
			retSucc.put("scientificServiceExperiences", scientificServiceExperiences);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> createScientificServiceExperience(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		
		String description = (String)context.get("description");
		Long quantity = Long.parseLong((String)context.get("quantity"));
		System.out.println("ScientificServiceExperience"+" "+staffId+" "+description+" "+quantity);
		
		Delegator delegator = ctx.getDelegator();
		try {
			GenericValue gv=delegator.makeValue("ScientificServiceExperience");
				gv.put("scientificServiceExperienceId", delegator.getNextSeqId("ScientificServiceExperience"));
				gv.put("staffId", staffId);
				gv.put("description", description);
				gv.put("quantity", quantity);
			delegator.create(gv);
			
			GenericValue result = delegator.findOne("ScientificServiceExperienceView", false, UtilMisc.toMap("scientificServiceExperienceId",
					gv.get("scientificServiceExperienceId")));
			Map<String,  Object> map = FastMap.newInstance();
				map.put("scientificServiceExperienceId", result.getString("scientificServiceExperienceId"));
				map.put("staffId", result.getString("staffId"));
				map.put("staffName", result.getString("staffName"));
				map.put("description", result.getString("description"));
				map.put("quantity", result.getString("quantity"));
			
			retSucc.put("scientificServiceExperiences", map);
			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> updateScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		
		String scientificServiceExperienceId = (String)context.get("scientificServiceExperienceId");
		String description = (String)context.get("description");
		Long quantity = Long.parseLong((String)context.get("quantity"));
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.findOne("ScientificServiceExperience", false, UtilMisc.toMap("scientificServiceExperienceId",scientificServiceExperienceId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("description", description);
				gv.put("quantity", quantity);
				delegator.store(gv);
				
				GenericValue result = delegator.findOne("ScientificServiceExperienceView", false, UtilMisc.toMap("scientificServiceExperienceId",
						gv.get("scientificServiceExperienceId")));
				Map<String,  Object> map = FastMap.newInstance();
					map.put("scientificServiceExperienceId", result.getString("scientificServiceExperienceId"));
					map.put("staffId", result.getString("staffId"));
					map.put("staffName", result.getString("staffName"));
					map.put("description", result.getString("description"));
					map.put("quantity", result.getString("quantity"));
				
				retSucc.put("scientificServiceExperiences", map);
        		retSucc.put("message", "Updated record with id: " + scientificServiceExperienceId);
        	} else {
        		retSucc.put("message", "Not found record with id: " + scientificServiceExperienceId);
        	}
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> deleteScientificServiceExperience(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        String scientificServiceExperienceId = (String)context.get("scientificServiceExperienceId");
        Delegator delegator = ctx.getDelegator();
        try{
        	GenericValue gv = delegator.findOne("ScientificServiceExperience", UtilMisc.toMap("scientificServiceExperienceId",scientificServiceExperienceId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "Deleted record with Id: " + scientificServiceExperienceId);
        	} else {
        		retSucc.put("result", "Not found record with Id: " + scientificServiceExperienceId);
        	}
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
}
