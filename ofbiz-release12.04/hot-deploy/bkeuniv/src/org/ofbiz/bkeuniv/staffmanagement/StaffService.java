package org.ofbiz.bkeuniv.staffmanagement;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		Debug.log(module + "::getGenders, authorStaffId = " + staffId);
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
	
	public static Map<String,Object> JQGetListStaffs(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator staffs = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0];
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"staffId", "staffEmail", "staffName", "facultyName", "departmentName", "genderName", "facultyName"}; 
				
				List<EntityCondition> condSearch = new ArrayList<EntityCondition>(); 
				for(String key: searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(key), EntityOperator.LIKE, EntityFunction.UPPER("%" + q + "%"));
//							EntityCondition.makeCondition(, EntityOperator.LIKE, "%" + q + "%");
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch, EntityOperator.OR));
			}
			if(filter != null) {
				
				listAllConditions.add(filter);				
			}
			
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			System.out.println("4. debug ::::::::::"  + userLoginId);
			staffs = delegator.find("StaffView", condition, null, null, sort, opts);
			
			//int total = staffs.getResultsSizeAfterPartialList();
			//System.out.println("Size Staff ::::::::" + staffs.getCompleteList().size());
						
			result.put("listIterator", staffs);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list staffs");
		} finally {
//			if(staffs != null){
//				staffs.close();
//			}
		}
		return result;
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
			
			String q = (String)context.get("q");
			
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			
			String[] searchKeys = {"departmentName", "genderName", "facultyName"}; 
			
			for(String key: searchKeys) {
				if(q == null || q.equals("")){
					EntityCondition condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, q);
					conditions.add(condition);
				}else{
					EntityCondition condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, q);
					conditions.add(condition);
				}
			}
		
			staffs = delegator.findList("StaffView", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
		
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

	public static Map<String, Object> getAllStaffs(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		
		Debug.log(module + "::getAllStaffs, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> staffs = delegator.findList("StaffView", 
					null,null, null, null, false);
			
		
			retSucc.put("staffs", staffs);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	
	public static Map<String, Object> getCVProfileOfStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)context.get("staffId");
		if(staffId == null)
				staffId = (String)userLogin.get("userLoginId");
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Debug.log(module + "::getCVProfileOfStaff, staffId = " + staffId);
		
		try{
			Map<String, Object> cv = FastMap.newInstance();
			
			// get general info of staff
			Map<String, Object> param = FastMap.newInstance();
			param.put("staffId", staffId);
			Map<String, Object> result_staff = dispatcher.runSync("getStaffInfo", param);
			cv.put("info", result_staff.get("staff"));
			
			// get Education Progress
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_ep = dispatcher.runSync("getEducationProgress", param);
			cv.put("educationProgress", result_ep.get("educationProgress"));
			
			// get papers
			param.clear();
			param.put("authorStaffId", staffId);
			Map<String, Object> result_papers = dispatcher.runSync("getPapersOfStaff", param);
			cv.put("papers", result_papers.get("papers"));
			List<GenericValue> lstPapers = (List<GenericValue>)cv.get("papers");
			Debug.log(module + "::getCVProfileOfStaff, publications = " + lstPapers.size());
			
			// get patents
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_patent = dispatcher.runSync("getPatent", param);
			cv.put("patents", result_patent.get("patent"));
			
			// get applied projects
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_applied_projects = dispatcher.runSync("getAppliedProjects", param);
			cv.put("appliedProjects", result_applied_projects.get("projects"));
			
			// get awards
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_awards = dispatcher.runSync("getAward", param);
			cv.put("awards", result_awards.get("award"));
			
			// get work progress
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_wp = dispatcher.runSync("getWorkProgress", param);
			cv.put("workProgress", result_wp.get("workProgress"));
			
			// get projects
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_projects = dispatcher.runSync("getProjectDeclaration", param);
			cv.put("projects", result_projects.get("projectDeclarations"));
			
			List<Map> projects_role_director = FastList.newInstance();
			List<Map> projects_role_member = FastList.newInstance();
			for(Map p: (List<Map>)cv.get("projects")){
				String roleId = (String)p.get("projectParticipationRoleId"); 
				if(roleId.equals("DIRECTOR")){
					projects_role_director.add(p);
				}else if(roleId.equals("MEMBER")){
					projects_role_member.add(p);
				}
			}
			cv.put("projects_role_director",projects_role_director);
			cv.put("projects_role_member",projects_role_member);
			
			
			// get scientific service experiences
			param.clear();
			param.put("staffId", staffId);
			Map<String, Object> result_scientific_experiences = dispatcher.runSync("getScientificServiceExperience", param);
			cv.put("scientificExperiences", result_scientific_experiences.get("scientificServiceExperiences"));
			
			
			
			param.clear();
			
			param.put("userLogin", userLogin);
			param.put("parameters", FastMap.newInstance());
			param.put("opts", new EntityFindOptions(true,
					EntityFindOptions.TYPE_SCROLL_INSENSITIVE,
					EntityFindOptions.CONCUR_READ_ONLY, false));
			
			Map<String, Object> result_rsd = dispatcher.runSync("JQGetListResearchDomainManagement", param);
			EntityListIterator tmpList = (EntityListIterator) result_rsd.get("listIterator");
			
			List<GenericValue> listGenericValue = (List<GenericValue>)tmpList.getCompleteList();
			tmpList.close();
			param.put("staffId", staffId);
			Map<String, Object> result_srsd = dispatcher.runSync("JQGetListStaffResearchDomainManagement", param);
			tmpList = (EntityListIterator) result_srsd.get("listIterator");
			List<GenericValue> list_srsd = (List<GenericValue>)tmpList.getCompleteList();
			tmpList.close();
			List<Map<String, Object>> srsd = new ArrayList<Map<String,Object>>();
			for(GenericValue s1: listGenericValue) {
				Collection<String> keys = s1.getAllKeys();
				Map<String, Object> s = FastMap.newInstance();
				for(String key: keys) {
					s.put(key, s1.get(key));
				}
				s.put("check", false);
				for(GenericValue s2: list_srsd) {
					if(s1.get("researchDomainId").equals(s2.get("researchDomainId"))) {
						s.put("check", true);
						break;
					}
				}
				srsd.add(s);
			}
			
			cv.put("researchDomain", srsd);
			
			

			param.clear();
			
			param.put("userLogin", userLogin);
			param.put("parameters", FastMap.newInstance());
			param.put("opts", new EntityFindOptions(true,
					EntityFindOptions.TYPE_SCROLL_INSENSITIVE,
					EntityFindOptions.CONCUR_READ_ONLY, false));
			param.put("staffId", staffId);
			Map<String, Object> result_rss = dispatcher.runSync("JQGetListStaffResearchSpecialityManagement", param);
			EntityListIterator tmpListRSS = (EntityListIterator) result_rss.get("listIterator");
			
			List<GenericValue> listGenericValueRSS = (List<GenericValue>)tmpListRSS.getCompleteList();
			tmpListRSS.close();
			List<Map<String, Object>> RSS = new ArrayList<Map<String,Object>>();
			if(listGenericValueRSS.size() > 0) {
				RSS.add(listGenericValueRSS.get(0));				
			}
			cv.put("researchSpeciality", RSS);
			
			
			retSucc.put("cv", cv);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getStaffInfo(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		
		String staffId = (String)context.get("staffId");
		if(staffId == null)
			staffId = (String)userLogin.get("userLoginId");
		
		Debug.log(module + "::getStaffInfo, staffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS,staffId));
			
			List<GenericValue> staffs = delegator.findList("StaffView", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			if(staffs != null && staffs.size() > 0){
				GenericValue st = staffs.get(0);
				retSucc.put("staff", st);
				
				String deptId = (String)st.get("departmentId");
				String facultyId = "";
				conds.clear();
				
				List<GenericValue> all_departments = delegator.findList("Department", 
						null, 
						null, 
						null, 
						null, 
						false);
				
				
				List<GenericValue> all_faculty = delegator.findList("Faculty", 
						null, 
						null, 
						null, 
						null, 
						false);
				
				
				
				for(GenericValue d: all_departments){
					String dId = (String)d.get("departmentId");
					if(dId.equals(deptId)){
						facultyId = (String)d.get("facultyId");
						break;
					}
				}
				
				
				
				conds.add(EntityCondition.makeCondition("facultyId", EntityOperator.EQUALS,facultyId));
				List<GenericValue> sel_departments = delegator.findList("Department", 
						EntityCondition.makeCondition(conds), 
						null, 
						null, 
						null, 
						false);
				
				List<GenericValue> genders = delegator.findList("Gender", 
						null, 
						null, 
						null, 
						null, 
						false);
				
				retSucc.put("departments", sel_departments);
				retSucc.put("faculties", all_faculty);
				retSucc.put("selected_department_id", deptId);
				retSucc.put("selected_faculty_id", facultyId);
				retSucc.put("genders", genders);
				
			}
			
			
			/*
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
			*/
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
	/*
	public static void changePassword(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String pwd = request.getParameter("password");
		String staffId = request.getParameter("staffId");
		String hashedPassword = LoginServices.hashPassword(pwd);
		Debug.log(module + "::changePassword, pwd = " + pwd + ", staffId = " + staffId + ", hashedPassword = " + hashedPassword);
		try{
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	*/
	@SuppressWarnings({ "unchecked" })
	public static void updateStaffInfo(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String staffId = request.getParameter("staffId");
		String departmentId = request.getParameter("departmentId");
		String genderId = request.getParameter("genderId");
		String email = request.getParameter("email");
		String birthDate = request.getParameter("birthDate");
		String staffName = request.getParameter("staffName");
		String phone = request.getParameter("phone");
		
		Debug.log(module + "::updateStaffInfo, staffId = " + staffId + ", staffName = " + staffName
				+ ", departmentId = " + departmentId + ", email = " + email + ", phone = " + phone
				+ ", birthDate = " + birthDate + ", genderId = " + genderId);
		
		try{
			GenericValue st = delegator.findOne("Staff", UtilMisc.toMap("staffId",staffId), false);
			if(st != null){
				st.put("staffName", staffName);
				st.put("staffEmail", email);
				st.put("departmentId", departmentId);
				st.put("staffGenderId", genderId);
				st.put("staffDateOfBirth", Date.valueOf(birthDate));
				st.put("staffPhone", phone);
				delegator.store(st);
				
				String rs = "{\"result\":\"OK\"}";
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write(rs);
				out.close();
				
				Debug.log(module + "::updateStaffInfo, rs = " + rs);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
