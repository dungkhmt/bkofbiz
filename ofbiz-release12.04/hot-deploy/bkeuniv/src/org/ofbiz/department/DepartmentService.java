package src.org.ofbiz.department;


import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class DepartmentService {
	public final static String module = DepartmentService.class.getName();

	
	public static Map<String, Object> deleteADepartment(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String deptId = (String)context.get("idofdepartment");
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

	public static Map<String, Object> updateADepartment(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String deptId = (String)context.get("department-id");
		String deptName = (String)context.get("departmentName");
		String facultyId = (String)context.get("facultyId");
		
		try{
			GenericValue gv = delegator.findOne("Department", false, UtilMisc.toMap("departmentId",deptId));
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
	public static String name(){
		return "DepartmentService";
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
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}


		return retSucc;
	}

}
