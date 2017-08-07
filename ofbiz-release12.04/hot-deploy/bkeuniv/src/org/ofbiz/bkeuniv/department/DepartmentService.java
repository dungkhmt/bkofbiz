package src.org.ofbiz.bkeuniv.department;

import java.util.List;
import java.util.Locale;
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
		
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();
			EntityCondition entity;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			List<GenericValue> list;
			if(facultId.endsWith("all")) {
				list = delegator.findList("Department", null, null, null, null, true);
			} else {
				entity = EntityCondition.makeCondition("facultyId", EntityOperator.EQUALS, facultId);
				
				list = delegator.findList("Department", entity, null, null, findOptions, true);				
			}
			result.put("departments", list);
			return result;
		
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> deleteADepartment(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String deptId = (String)context.get("idDepartment");
		System.out.println(name() + "::deleteADepartment, departmentId = " + deptId);
		
		try{
			GenericValue gv = delegator.findOne("Department", false, UtilMisc.toMap("departmentId",deptId));
			
			if(gv != null){
				delegator.removeValue(gv);
			}else{
				System.out.println(name() + "::deleteADepartment, cannot find department with departmentId = " + deptId);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> updateADepartment(DispatchContext ctx,
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String deptId = (String)context.get("department-id");
		String deptName = (String)context.get("departmentName");
		String facultyId = (String)context.get("facultyId");
		
		try{
			GenericValue gv = delegator.findOne("Department", false, 
					UtilMisc.toMap("departmentId",deptId));
			if(gv != null){
				gv.put("departmentName", deptName);
				gv.put("facultyId", facultyId);
				delegator.store(gv);
			}else{
				System.out.println(name() + "::updateADepartment, cannot find department with departmentId = " + deptId);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> createADepartment(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		
		
		String departmentName = (String) context.get("departmentName");
		String facultyId = (String) context.get("facultyId");

		GenericValue gv = delegator.makeValue("Department");

		gv.put("departmentId", delegator.getNextSeqId("Department"));

		
		String id = (String) gv.get("id");


		try {
			gv.put("departmentName", departmentName);
			gv.put("facultyId", facultyId);
			
			delegator.create(gv);
			
			//Map<String, Object> input = UtilMisc.toMap("facultyId","SOICT");
			//Map<String, Object> rs = dispatcher.runSync("getListDepartments", input);
			
			//List<GenericValue> lst = (List<GenericValue>)rs.get("departments");
			//for(GenericValue d: lst){
			//	System.out.println(name() + "::createADepartment, get department (" + d.get("departmentName") + ")");
			//}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}


		return retSucc;
	}
}
