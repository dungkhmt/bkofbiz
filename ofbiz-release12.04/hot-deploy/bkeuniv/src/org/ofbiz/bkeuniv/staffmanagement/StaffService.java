package src.org.ofbiz.bkeuniv.staffmanagement;

import java.sql.Date;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

public class StaffService {
	public static String module = StaffService.class.getName();
	
	public static Map<String, Object> getGenders(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		
		String staffId = (String)userLogin.get("userLoginId");
		
		Debug.log(module + "::getStaffs, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> genders = delegator.findList("Gender", 
					null,null, null, null, false);
			for(GenericValue gv: genders){
				Debug.log(module + "::getGenders, gender " + gv.get("genderName"));
			}
		
			retSucc.put("genders", genders);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

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
		
			List<Map<String, Object>> ret_staffs = FastList.newInstance();
					
			for(GenericValue gv: staffs){
				Debug.log(module + "::getStaffs, staff " + gv.get("staffName"));
				Map<String, Object> rs = FastMap.newInstance();
				rs.put("staffId", gv.getString("staffId"));
				rs.put("staffId", gv.getString("staffId"));
				rs.put("staffName", gv.getString("staffName"));
				rs.put("staffEmail", gv.getString("staffEmail"));
				rs.put("departmentId", gv.getString("departmentId"));
				rs.put("staffGenderId", gv.getString("staffGenderId"));
				rs.put("staffDateOfBirth", gv.getString("staffDateOfBirth"));
				rs.put("staffPhone", gv.getString("staffPhone"));
				rs.put("departmentName", gv.getString("departmentName"));
				rs.put("genderName", gv.getString("genderName"));
				ret_staffs.add(rs);
			}
		
			retSucc.put("staffs", ret_staffs);
			
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
		String staffDateOfBirth = (String)context.get("staffDateOfBirth");
		List<Object> departmentIds = (List<Object>)context.get("departmentId[]");
		String departmentId = (String)departmentIds.get(0);
		
		List<Object> genderIds = (List<Object>)context.get("staffGenderId[]");
		String genderId = (String)genderIds.get(0);
		
		Debug.log(module + "::updateStaff, staffId = " + staffId + ", input staffName = " + staffName + 
				", phone = " + staffPhone + ", date of birth = " + staffDateOfBirth + 
				", departmentId = " + departmentId + ", genderId = " + genderId);
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
			//p.put("staffEmail", staffEmail);
			p.put("departmentId", departmentId);
			p.put("staffGenderId", genderId);
			p.put("staffDateOfBirth", Date.valueOf(staffDateOfBirth));
			delegator.store(p);
			
			
			List<GenericValue> SV = delegator.findList("StaffView", 
					EntityCondition.makeCondition(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId)), 
					null, null, null, false);
			
			GenericValue np = SV.get(0);
			Map<String, Object> rs = FastMap.newInstance();
			rs.put("staffId", np.getString("staffId"));
			rs.put("staffName", np.getString("staffName"));
			rs.put("staffEmail", np.getString("staffEmail"));
			rs.put("staffGenderId", np.getString("staffGenderId"));
			rs.put("staffDateOfBirth", np.getString("staffDateOfBirth"));
			rs.put("staffPhone", np.getString("staffPhone"));
			rs.put("departmentName", np.getString("departmentName"));
			rs.put("genderName",  np.getString("genderName"));
			rs.put("departmentId", np.getString("departmentId"));
			
			retSucc.put("staffs", rs);
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
}
