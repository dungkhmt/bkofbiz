package src.org.ofbiz.bkeuniv.staffmanagement;

import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

import java.util.List;

public class StaffService {
	public static String module = StaffService.class.getName();
	
	public static Map<String, Object> getStaffs(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		
		Debug.log(module + "::getStaffs, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> staffs = delegator.findList("StaffView", 
					null,null, null, null, false);
			for(GenericValue gv: staffs){
				Debug.log(module + "::getStaffs, staff " + gv.get("staffName"));
			}
		
			retSucc.put("staffs", staffs);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	public static Map<String, Object> updateStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String userLoginId = (String)userLogin.get("userLoginId");
		String staffId = (String)context.get("staffId");
		String staffName = (String)context.get("staffName");
		String staffPhone = (String)context.get("staffPhone");
		
		
		Debug.log(module + "::updateStaff, staffId = " + staffId + ", input staffName = " + staffName + ", phone = " + staffPhone);
		Delegator delegator = ctx.getDelegator();
		
		try{
			List<GenericValue> staffs = delegator.findList("Staff", 
					EntityCondition.makeCondition(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId)), 
					null, null, null, false);
			for(GenericValue gv: staffs){
				Debug.log(module + "::updateStaff, staff " + gv.get("staffName") + ", new Name = " + staffName);
			}
			GenericValue p = staffs.get(0);
			p.put("staffName", staffName);
			p.put("staffPhone", staffPhone);
			
			delegator.store(p);
			
			retSucc.put("staffs", staffs);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
}
