package src.org.ofbiz.bkeuniv.scientificserviceexperience;

import java.util.List;
import java.util.Map;

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
}
