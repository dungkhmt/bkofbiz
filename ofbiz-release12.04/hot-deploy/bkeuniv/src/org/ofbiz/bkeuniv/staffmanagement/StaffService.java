package src.org.ofbiz.bkeuniv.staffmanagement;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
//import org.ofbiz.common.login.LoginServices;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

public class StaffService {
	
	public static String module = StaffService.class.getName();
	
	/*
	public static Map<String, Object> updatePassword(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String newPassword = (String)context.get("password");
		String staffId = (String)context.get("staffId");
		String hashedPassword = LoginServices.hashPassword(newPassword);
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue u = delegator.findOne("UserLogin", false, UtilMisc.toMap("userLoginId", staffId));
			Debug.log(module + "::changePassword, staffId = " + staffId + ", password = " + newPassword + 
					", current Hashed Password = " + u.get("currentPassword"));
			
			u.put("currentPassword", hashedPassword);
			delegator.store(u);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		return retSucc;
	}
	*/
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
				//Debug.log(module + "::getStaffs, staff " + gv.get("staffName"));
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
				rs.put("facultyId", gv.getString("facultyId"));
				rs.put("facultyName", gv.getString("facultyName"));
				ret_staffs.add(rs);
			}
		
			retSucc.put("staffs", ret_staffs);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	public static Map<String, Object> removeAStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String userLoginId = (String)userLogin.get("userLoginId");
		String staffId = (String)context.get("staffId");
		Debug.log(module + "::removeAStaff, staffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		
		try{
			
			GenericValue gv = delegator.findOne("Staff", false, UtilMisc.toMap("staffId",staffId));
			
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			String curDate = sdf.format(cal.getTime());
			Debug.log(module + "::removeAStaff, staffId = " + staffId + " at " + curDate);
			gv.put("thruDate", Date.valueOf(curDate));
			
			delegator.store(gv);
			
			
			retSucc.put("message", "Remove Staff " + staffId + " successfully");
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		Debug.log(module + "::removeAStaff, staffId = " + staffId + " --> OK");
		
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
		String staffEmail = (String)context.get("staffEmail");
		String staffPhone = (String)context.get("staffPhone");
		String staffDateOfBirth = (String)context.get("staffDateOfBirth");
		List<Object> departmentIds = (List<Object>)context.get("departmentId[]");
		String departmentId = (String)departmentIds.get(0);
		
		List<Object> genderIds = (List<Object>)context.get("staffGenderId[]");
		String genderId = (String)genderIds.get(0);
		
		Debug.log(module + "::updateStaff, staffId = " + staffId + ", input staffName = " + staffName + 
				", phone = " + staffPhone + ", date of birth = " + staffDateOfBirth + 
				", departmentId = " + departmentId + ", genderId = " + genderId + ", email = " + staffEmail);
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
			p.put("staffEmail", staffEmail);
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

	public static Map<String, Object> addPermissionGroupStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String staffId = (String)context.get("staffId");
		String groupId = (String)context.get("groupId");
		Delegator delegator = ctx.getDelegator();
		try{
			
			 Calendar calendar = Calendar.getInstance();
			 java.sql.Timestamp currentTime = new java.sql.Timestamp(calendar.getTime().getTime());
			   
			GenericValue gv = delegator.makeValue("UserLoginSecurityGroup");
			gv.put("userLoginId", staffId);
			gv.put("groupId", groupId);
			gv.put("fromDate", currentTime);
			delegator.create(gv);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
	
	public static Map<String, Object> createAStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Debug.log(module + "::createAStaff START....");
		
		//Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		//String userLoginId = (String)userLogin.get("userLoginId");
		
		String staffId = (String)context.get("staffId");
		String password = (String)context.get("password");
		String staffEmail = "NULL";
		if(context.get("staffEmail") !=null) staffEmail = (String)context.get("staffEmail");
		String staffName = (String)context.get("staffName");
		String staffPhone = "NULL";
		if(context.get("staffPhone") != null) staffPhone = (String)context.get("staffPhone");
		
		String staffDateOfBirth = "NULL";
		if(context.get("staffDateOfBirth") != null) staffDateOfBirth = (String)context.get("staffDateOfBirth");
		
		List<Object> departmentIds = (List<Object>)context.get("departmentId[]");
		String departmentId = "NULL";
		if(departmentIds != null) departmentId = (String)departmentIds.get(0);
		List<Object> genderIds = (List<Object>)context.get("staffGenderId[]");
		String genderId = "NULL";
		if(genderIds != null) genderId = (String)genderIds.get(0);
		String groupId = (String)context.get("groupId");
		
		
		Debug.log(module + "::createAStaff, staffId = " + staffId + ", input staffName = " + staffName
				+ ", phone = " + staffPhone + ", date of birth = " + staffDateOfBirth + 
				", departmentId = " + departmentId + ", genderId = " + genderId + ", password = " + password + ", groupId = " + groupId);
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		try{
			/*
			// check the existence of staffId
			GenericValue st = delegator.findOne("UserLogin", false, UtilMisc.toMap("userLoginId", staffId));
			if(st != null){
				retSucc.put("message", "userLoginId " + staffId + " existing in entity UserLogin");
				retSucc.put("staffs", null);
				Debug.log(module + "::createAStaff userLoginId " + staffId + " existing in entity UserLogin");
				return retSucc;
			}
			*/
			
			GenericValue st = delegator.findOne("Staff", false, UtilMisc.toMap("staffId", staffId));
			if(st != null){
				retSucc.put("message", "staffId " + staffId + " existing in entity Staff");
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
				Debug.log(module + "::createAStaff userLoginId " + staffId + " existing in entity Staff");
				return retSucc;
			}
			
			
			// create a user login
			Map<String, Object> input = FastMap.newInstance();
			input.put("userLoginId", staffId);
			input.put("currentPassword", password);
			input.put("currentPasswordVerify", password);			
			dispatcher.runSync("createUserLogin", input);
			
			Debug.log(module + "::createAStaff, staffId = " + staffId + ", input staffName = " + staffName
					+ ", create UserLogin --> OK"); 
			
			// create a staff
			st = delegator.makeValue("Staff");
			st.put("staffId", staffId);
			if(!staffEmail.equals("NULL")) st.put("staffEmail", staffEmail);
			st.put("staffName", staffName);
			if(!staffPhone.equals("NULL")) st.put("staffPhone", staffPhone);
			if(!staffDateOfBirth.equals("NULL")) st.put("staffDateOfBirth",  Date.valueOf(staffDateOfBirth));
			if(!departmentId.equals("NULL")) st.put("departmentId", departmentId);
			if(!genderId.equals("NULL")) st.put("staffGenderId", genderId);

			delegator.create(st);
			
			Debug.log(module + "::createAStaff, staffId = " + staffId + ", input staffName = " + staffName
					+ ", create Staff --> OK"); 
			
			
			// assign permission group
			Map<String, Object> input_permission = FastMap.newInstance();
			input_permission.put("staffId", staffId);
			input_permission.put("groupId", groupId);
			Map<String, Object> ret_permission = dispatcher.runSync("addPermissionGroupStaff", input_permission);
			
			Debug.log(module + "::createAStaff, staffId = " + staffId + ", input staffName = " + staffName
					+ ", add permission --> OK"); 

			
			
			//List<GenericValue> SV = delegator.findList("StaffView", 
			//		EntityCondition.makeCondition(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId)), 
			//		null, null, null, false);
			
			String departmentName = "NULL";
			String genderName = "NULL";
			//staffEmail = "NULL";
			//genderId = "NULL";
			//staffDateOfBirth = "NULL";
			//staffPhone = "NULL";
			
			//GenericValue np = SV.get(0);
			Map<String, Object> rs = FastMap.newInstance();
			rs.put("staffId", staffId); 
			rs.put("staffName", staffName);
			rs.put("staffEmail", staffEmail);
			rs.put("staffGenderId", genderId);
			rs.put("staffDateOfBirth", staffDateOfBirth);
			rs.put("staffPhone", staffPhone);
			rs.put("departmentName", departmentName);
			rs.put("genderName",  genderName);
			rs.put("departmentId", departmentId);
			
			retSucc.put("staffs", rs);
			retSucc.put("message", "Create a Staff successfully");
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
}
