package src.org.ofbiz.bkeuniv.department;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javolution.util.FastList;

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
	
	public static Map<String, Object> getDepartments(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String facultyId = (String)context.get("facultyId");
		List<EntityCondition> conds = FastList.newInstance();
		if(facultyId != null && !facultyId.equals("")){
			conds.add(EntityCondition.makeCondition("facultyId", EntityOperator.EQUALS,facultyId));
		}
		
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();

			List<GenericValue> list = delegator.findList("Department", 
					EntityCondition.makeCondition(conds), null, null, null, false);				
			result.put("departments", list);
			return result;
		
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
}
