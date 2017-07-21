package src.org.ofbiz.bkeuniv.department;

import java.util.List;
import java.util.Map;

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
	
	public static Map<String, Object> getDepartmentByFacultyId(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String facultId = (String)context.get("facultyId");
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			EntityCondition entity = EntityCondition.makeCondition("facultyId", EntityOperator.EQUALS, facultId);
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list = delegator.findList("Department", entity, null, null, findOptions, false);
			
			result.put("departments", list);
			return result;
		
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}		
	}
	
	public static Map<String, Object> getDepartment(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delagator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String facultId = (String)context.get("facultyId");
		Map<String, Object> condition = UtilMisc.toMap("facultyId", facultId);
		try {
			Map<String, Object> rs = localDispatcher.runSync("getDepartmentsByFacultyId", condition);
			List<GenericValue> list = (List<GenericValue>)rs.get("departments");
			Map<String, Object> result = ServiceUtil.returnSuccess();
			result.put("departments", list);
			for(GenericValue d: list){
				System.out.println(name() + ":: get department (" + d.get("departmentName") + ")");
			}
			return result;
		} catch (GenericServiceException e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			
			e.printStackTrace();
			return rs;
		}
	}
}
