package src.org.ofbiz.bkeuniv.department;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class DepartmentService {
	public static String name(){
		return "DepartmentService";
	}
	
	public static Map<String, Object> getDepartment(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String departmentId = (String)context.get("facultyId");
		String departmentName = (String)context.get("facultyId");
		String facultId = (String)context.get("facultyId");
		Set<String> keys = context.keySet();
		for(String key: keys) {
			System.out.println(key);
			System.out.println(context.get(key));
		}
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			EntityCondition entity;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list;
			if(facultId.equals("all")) {
				entity = EntityCondition.makeCondition("facultyId", EntityOperator.EQUALS, facultId);
				
				list = delegator.findList("Department", entity, null, null, findOptions, false);
			} else {
				
				list = delegator.findList("Department", null, null, null, null, false);
			}
			result.put("departments", list);
			return result;
		
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
}
