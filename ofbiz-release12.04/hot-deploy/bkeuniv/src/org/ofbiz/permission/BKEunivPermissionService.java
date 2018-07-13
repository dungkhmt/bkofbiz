package org.ofbiz.permission;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;

public class BKEunivPermissionService {
	public static String module = BKEunivPermissionService.class.getName();
	public static String STATUS_ENABLED = "ENABLED";
	
	public static List<GenericValue> getFunctionsOfASecurityGroup(Delegator delegator, String groupId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("securityGroupId",EntityOperator.EQUALS,groupId));
			conds.add(EntityCondition.makeCondition("thruDate",EntityOperator.EQUALS,null));
			
			List<GenericValue> rs = delegator.findList("GroupFunction", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			return rs;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	public static List<GenericValue> getStaffsOfASecurityGroup(Delegator delegator, String groupId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("groupId",EntityOperator.EQUALS,groupId));
			conds.add(EntityCondition.makeCondition("thruDate",EntityOperator.EQUALS,null));
			
			List<GenericValue> rs = delegator.findList("UserLoginSecurityGroup", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			return rs;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean groupFunctionEnabled(Delegator delegator, String groupId, String functionId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("securityGroupId",EntityOperator.EQUALS,groupId));
			conds.add(EntityCondition.makeCondition("functionId",EntityOperator.EQUALS,functionId));
			conds.add(EntityCondition.makeCondition("thruDate",EntityOperator.EQUALS,null));
			
			List<GenericValue> rs = delegator.findList("GroupFunction", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			return (rs.size() > 0);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	public static boolean groupUserEnabled(Delegator delegator, String groupId, String staffId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("groupId",EntityOperator.EQUALS,groupId));
			conds.add(EntityCondition.makeCondition("userLoginId",EntityOperator.EQUALS,staffId));
			conds.add(EntityCondition.makeCondition("thruDate",EntityOperator.EQUALS,null));
			
			List<GenericValue> rs = delegator.findList("UserLoginSecurityGroup", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			return (rs.size() > 0);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	public static void createGroupFunction(Delegator delegator, String groupId, String functionId){
		try{
			GenericValue gv = delegator.makeValue("GroupFunction");
			String id = delegator.getNextSeqId("GroupFunction");
			Timestamp now = UtilDateTime.nowTimestamp();
			gv.put("groupFunctionId", id);
			gv.put("securityGroupId", groupId);
			gv.put("functionId", functionId);
			gv.put("fromDate", now);
			delegator.create(gv);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void createGroupUser(Delegator delegator, String groupId, String staffId){
		try{
			GenericValue gv = delegator.makeValue("UserLoginSecurityGroup");
			String id = delegator.getNextSeqId("UserLoginSecurityGroup");
			Timestamp now = UtilDateTime.nowTimestamp();
			gv.put("groupId", groupId);
			gv.put("userLoginId", staffId);
			gv.put("fromDate", now);
			delegator.create(gv);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void disableGroupFunction(Delegator delegator, String groupId, String functionId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("securityGroupId",EntityOperator.EQUALS,groupId));
			conds.add(EntityCondition.makeCondition("functionId",EntityOperator.EQUALS,functionId));
			conds.add(EntityCondition.makeCondition("thruDate",EntityOperator.EQUALS,null));
			
			List<GenericValue> rs = delegator.findList("GroupFunction", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			for(GenericValue gv: rs){
				Timestamp now = UtilDateTime.nowTimestamp();
				gv.put("thruDate", now);
				delegator.store(gv);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void disableGroupUser(Delegator delegator, String groupId, String staffId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("groupId",EntityOperator.EQUALS,groupId));
			conds.add(EntityCondition.makeCondition("userLoginId",EntityOperator.EQUALS,staffId));
			conds.add(EntityCondition.makeCondition("thruDate",EntityOperator.EQUALS,null));
			
			List<GenericValue> rs = delegator.findList("UserLoginSecurityGroup", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			for(GenericValue gv: rs){
				Timestamp now = UtilDateTime.nowTimestamp();
				gv.put("thruDate", now);
				delegator.store(gv);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void getFunctionsOfASecurityGroup(HttpServletRequest request,
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String groupId = request.getParameter("groupId");
		List<GenericValue> funcs = getFunctionsOfASecurityGroup(delegator, groupId);
		HashMap<String, Integer> mFunction2Checked = new HashMap<String, Integer>();
		
		try{
			List<GenericValue> functions = delegator.findList("Function", 
					null, 
					null, 
					null, 
					null, 
					false);
			
			functions = sortGroupParent(functions);
			
			for(GenericValue gv: functions){
				mFunction2Checked.put((String)gv.get("functionId"), 0);
			}
			if(funcs != null){
				for(GenericValue gv: funcs){
					mFunction2Checked.put((String)gv.get("functionId"), 1);
				}
			}
				
			String json = "{\"functions\":[";
			for(int i = 0; i < functions.size(); i++){
				GenericValue gv = functions.get(i);
				String parentFunctionId = "-";
				if(gv.getString("parentFunctionId") != null)
					parentFunctionId = gv.getString("parentFunctionId");
				json += "{\"functionId\":\"" + gv.get("functionId") 
						+ "\",\"vnLabel\":\"" + gv.get("vnLabel") 
						+ "\","
						+ "\"checked\":" + mFunction2Checked.get((String)gv.get("functionId"))
						+ ","
						+ "\"parentFunctionId\":\"" + parentFunctionId + "\""
						+ "}";
				if(i < functions.size() - 1)
					json += ",\n";
			}
				json += "]}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.close();
			Debug.log(module + "::getFunctionsOfASecurityGroup, RETURN JSON = " + json);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static Map<String,Object> JQGetStaffsOfASecurityGroup(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		List<GenericValue> staffs = null;
		List<GenericValue> staffsOfASecurityGroup = null;
		
		
		
		
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
			
			
		 	staffs = delegator.findList("StaffView", EntityCondition.makeCondition(listAllConditions, EntityOperator.AND), null, sort, opts, false);
		 	
		 	if(filter != null) {
				
				listAllConditions.add(filter);				
			}
			System.out.println("4. debug ::::::::::"  + userLoginId);
			staffsOfASecurityGroup = delegator.findList("StaffSecurityGroupView", EntityCondition.makeCondition(listAllConditions, EntityOperator.AND), null, sort, opts, false);
			
			//int total = staffs.getResultsSizeAfterPartialList();
			//System.out.println("Size Staff ::::::::" + staffs.getCompleteList().size());
			
			List<GenericValue> rsf = new ArrayList<GenericValue>();
			
			int i = 0, j = 0;
			
			while (i < staffs.size()) {
				GenericValue s = staffs.get(i);
				
				if(j >= staffsOfASecurityGroup.size()) {
					rsf.add(s);
					i++;
					continue;
				}
				
				GenericValue sf = staffsOfASecurityGroup.get(j);
				
				if(s.getString("staffId").equals(sf.getString("staffId"))) {
					rsf.add(sf);
					j++;
				} else {
					rsf.add(s);
				}
				i++;
			}
			
			Debug.log("tesssst staffsOfASecurityGroup" + staffsOfASecurityGroup.size());
			result.put("listIterator", rsf);
			
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
	
	@SuppressWarnings({ "unchecked" })
	public static void getStaffsOfASecurityGroup(HttpServletRequest request,
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String groupId = request.getParameter("groupId");
		List<GenericValue> staffs = getStaffsOfASecurityGroup(delegator, groupId);
		HashMap<String, Integer> mStaff2Checked = new HashMap<String, Integer>();
		
		try{
			List<GenericValue> all_staffs = delegator.findList("Staff", 
					null, 
					null, 
					null, 
					null, 
					false);
			for(GenericValue gv: all_staffs){
				mStaff2Checked.put((String)gv.get("staffId"), 0);
			}
			if(staffs != null){
				for(GenericValue gv: staffs){
					mStaff2Checked.put((String)gv.get("userLoginId"), 1);
				}
			}
				
			String json = "{\"staffs\":[";
			for(int i = 0; i < all_staffs.size(); i++){
				GenericValue gv = all_staffs.get(i);
				json += "{\"staffId\":\"" + gv.get("staffId") + "\",\"staffName\":\"" + gv.get("staffName") + "\","
						+ "\"checked\":" + mStaff2Checked.get((String)gv.get("staffId")) + "}";
				if(i < all_staffs.size() - 1)
					json += ",\n";
			}
				json += "]}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.close();
			//Debug.log(module + "::getStaffsOfASecurityGroup, RETURN JSON = " + json);
			//Debug.log(module + "::getStaffsOfASecurityGroup, HAS staffs.sz = " + staffs.size());
			//for(GenericValue gv: staffs){
			//	Debug.log(module + "::getStaffsOfASecurityGroup, HAS staff " + (String)gv.get("userLoginId"));
			//}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void storeSecurityGroupFunctions(HttpServletRequest request,
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String groupId = request.getParameter("groupId");
		String functions = request.getParameter("functions");
		Debug.log(module + "::storeSecurityGroupFunctions, groupId = " + groupId + ", functions = " + functions);
		String[] lst_function_id = functions.split(",");
		
		
		
		try{
			List<GenericValue> all_functions = delegator.findList("Function", 
					null, 
					null, 
					null, 
					null, 
					false);
			
			HashSet<String> F = new HashSet<String>();
			for(GenericValue gv: all_functions){
				F.add((String)gv.get("functionId"));
			}
			
			for(int i = 0; i < lst_function_id.length; i++){
				String functionId = lst_function_id[i];
				if(!groupFunctionEnabled(delegator, groupId, functionId)){
					createGroupFunction(delegator, groupId, functionId);
				}
				
				F.remove(functionId);
			}
			for(String fId: F){
				if(groupFunctionEnabled(delegator, groupId, fId)){
					disableGroupFunction(delegator, groupId, fId);
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("{}");
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}

	@SuppressWarnings({ "unchecked" })
	public static void storeSecurityGroupUsers(HttpServletRequest request,
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String groupId = request.getParameter("groupId");
		String staff_id_insert = request.getParameter("staffsInsert");
		String staff_id_remove = request.getParameter("staffsRemove");
		Debug.log(module + "::storeSecurityGroupUsers, groupId = " + groupId + ", staffs = " + staff_id_insert + staff_id_remove);
		
		String[] lst_staff_id_insert = staff_id_insert.split(",");
		String[] lst_staff_id_remove = staff_id_remove.split(",");
		
		try{
			List<GenericValue> all_staffs = delegator.findList("Staff", 
					null, 
					null, 
					null, 
					null, 
					false);
			
			HashSet<String> U = new HashSet<String>();
			for(GenericValue gv: all_staffs){
				U.add((String)gv.get("staffId"));
			}
			
			for(int i = 0; i < lst_staff_id_insert.length; i++){
				String staffId = lst_staff_id_insert[i];
				if(!groupUserEnabled(delegator, groupId, staffId)){
					createGroupUser(delegator, groupId, staffId);
				}
			}
			
			for(String staffId: lst_staff_id_remove){
				if(groupUserEnabled(delegator, groupId, staffId)){
					disableGroupUser(delegator, groupId, staffId);
				}
			}
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("{}");
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public static void addASecurityGroup(HttpServletRequest request,
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String groupId = request.getParameter("groupId");
		String description = request.getParameter("description");
		Debug.log(module + "::addASecurityGroup, groupId = " + groupId + ", description = " + description);
		try{
			GenericValue gv = delegator.makeValue("SecurityGroup");
			gv.put("groupId", groupId);
			gv.put("description", description);
			gv.put("status", STATUS_ENABLED);
			delegator.create(gv);
		
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("{}");
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}

	public static Map<String, Object> getListSecurityGroups(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("status",EntityOperator.EQUALS,STATUS_ENABLED));
			
			Delegator delegator = ctx.getDelegator();
			List<GenericValue> securityGroups = delegator.findList("SecurityGroup", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			
			retSucc.put("securityGroups", securityGroups);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
	public static List<GenericValue> sortGroupParent(List<GenericValue> functions){
		GenericValue[] a = new GenericValue[functions.size()];
		for(int i = 0; i < functions.size(); i++){
			a[i] = functions.get(i);
		}
		for(int i = 0; i < a.length-1; i++){
			String pi = a[i].getString("parentFunctionId");
			if(pi.equals("NULL")) pi = a[i].getString("functionId");
			for(int j = i+1; j < a.length; j++){
				String pj = a[j].getString("parentFunctionId");
				if(pj.equals("NULL")) pj = a[j].getString("functionId");
				if(pi.compareTo(pj) > 0){
					GenericValue tmp = a[i]; a[i] = a[j]; a[j] = tmp;
					Debug.log(module + "::sortGroupParent, pi = " + pi + ", pj = " + pj + ", SWAP " + i + "," + j);
				}
			}
		}
		for(int i = 0; i < a.length; i++){
			Debug.log(module + "::sortGroupParent, AFTER SORT -> " + a[i].getString("parentFunctionId"));
		}
		List<GenericValue> retL = FastList.newInstance();
		for(int i = 0; i < a.length; i++){
			retL.add(a[i]);
		}
		return retL;
	}
	public static Map<String, Object> getListFunctions(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try{
			Delegator delegator = ctx.getDelegator();
			List<GenericValue> functions = delegator.findList("Function", 
					null, 
					null, 
					null, 
					null, 
					false);
			
			//Debug.logInfo("functions.sz = " + functions.size(), module);
			
			retSucc.put("functions", functions);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}

	public static List<GenericValue> sort(List<GenericValue> L){
		GenericValue[] a = new GenericValue[L.size()];
		for(int i = 0; i < a.length; i++) a[i] = L.get(i);
		for(int i = 0; i < a.length - 1; i++)
			for(int j = i+1; j < a.length; j++)
				if((long)a[i].get("index") > (long)a[j].get("index")){
					GenericValue tmp = a[i]; a[i] = a[j]; a[j] = tmp;
				}
		List<GenericValue> sL = new ArrayList<GenericValue>();
		for(int i = 0; i < a.length; i++) sL.add(a[i]);
		return sL;
	}
	public static Map<String, Object> getPermissionFunctions(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String userLoginId = (String)userLogin.get("userLoginId");
		
		List<EntityCondition> cond = FastList.newInstance();
		cond.add(EntityCondition.makeCondition("userLoginId", EntityOperator.EQUALS, userLoginId));
		cond.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));
		
		Debug.log(module + "::getPermissionFunctions, userLoginId = " + userLoginId);
		
		try{
			List<GenericValue> tmp_functions = new ArrayList<GenericValue>();
			List<GenericValue> userLoginSecurityGroup = delegator.findList("UserLoginSecurityGroup", 
					EntityCondition.makeCondition(cond), 
					null, null, null, false);
			//Debug.log(module + "::getPermissionFunctions, groups.sz = " + userLoginSecurityGroup.size());
			
			HashSet<String> set_function_id = new HashSet<String>();
			
			for(GenericValue gv: userLoginSecurityGroup){
				String securityGroupId = (String)gv.get("groupId");
				cond.clear();
				cond.add(EntityCondition.makeCondition("securityGroupId", EntityOperator.EQUALS, securityGroupId));
				cond.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));
				List<GenericValue> funcs = delegator.findList("GroupFunction", 
						EntityCondition.makeCondition(cond), 
						null, null, null, false);
				for(GenericValue f: funcs){
					String functionId = (String)f.get("functionId");
					set_function_id.add(functionId);
					//GenericValue aFunction = delegator.findOne("Function", false, UtilMisc.toMap("functionId",functionId));
					//tmp_functions.add(aFunction);
				}
			}
			
			for(String functionId: set_function_id){
				GenericValue aFunction = delegator.findOne("Function", false, UtilMisc.toMap("functionId",functionId));
				tmp_functions.add(aFunction);
			}
			
			//Debug.log(module + "::getPermissionFunctions, functions.sz = " + tmp_functions.size());
			//for(GenericValue f: tmp_functions){
			//	Debug.log(module + "::getPermissionFunctions, get functions " + f.get("functionId"));
			//}
			
			List<GenericValue> parent_functions = new ArrayList<GenericValue>();
			HashMap<String, GenericValue> mId2Function = new HashMap<String, GenericValue>();
			HashMap<GenericValue, List<GenericValue>> mFunction2ChildrenFunctions = new HashMap<GenericValue, List<GenericValue>>();
			
			for(GenericValue f: tmp_functions){
				String parentFunctionId = (String)f.get("parentFunctionId");
				String functionId = (String)f.get("functionId");
				//Debug.log(module + "::getPermissionFunctions, function " + functionId + ", parentFunction = " + parentFunctionId);
				if(parentFunctionId.equals("NULL")){// collect parent functions
					parent_functions.add(f);
					mId2Function.put(functionId, f);
					mFunction2ChildrenFunctions.put(f, new ArrayList<GenericValue>());
				}else{
					
					
				}
			}
			parent_functions = sort(parent_functions);
			
			for(GenericValue f: tmp_functions){
				String parentFunctionId = (String)f.get("parentFunctionId");
				if(!parentFunctionId.equals("NULL")){
					GenericValue pf = mId2Function.get(parentFunctionId);
					if(pf == null){
						pf = delegator.findOne("Function", false, 
								UtilMisc.toMap("functionId",parentFunctionId));
						mId2Function.put(parentFunctionId, pf);
						mFunction2ChildrenFunctions.put(pf, new ArrayList<GenericValue>());
					}
					mFunction2ChildrenFunctions.get(pf).add(f);
				}
			}
			List<Map<String, Object>> functions = new ArrayList<Map<String, Object>>();
			//List<GenericValue> functions = new ArrayList<GenericValue>();
			
			for(GenericValue f: parent_functions){
				Map<String, Object> o = new HashMap<String, Object>();
				o.put("function", f);
				o.put("children", sort(mFunction2ChildrenFunctions.get(f)));
				functions.add(o);
			}
			
			retSucc.put("permissionFunctions", functions);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
}
