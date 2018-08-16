package org.ofbiz.bkeuniv.researchdomainmanagement;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
//import org.ofbiz.common.login.LoginServices;
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

import org.ofbiz.utils.BKEunivUtils;

public class ResearchDomainManagement {
	
	public static String module = ResearchDomainManagement.class.getName();
	
	public static Map<String,Object> JQGetListResearchDomainManagement(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator researchDomain = null;
		try {
			
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0].trim();
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"researchDomainName"}; 
				
				List<EntityCondition> condSearch = new ArrayList<EntityCondition>(); 
				for(String key: searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(key), EntityOperator.LIKE, EntityFunction.UPPER("%" + q + "%"));
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch, EntityOperator.OR));
			}
			
			if(filter != null) {
				listAllConditions.add(filter);				
			}
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			System.out.println("4. debug ::::::::::");
			researchDomain = delegator.find("ResearchDomain", condition, null, null, sort, opts);
				
			result.put("listIterator", researchDomain);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list JQGetListResearchDomainManagement");
		}
		return result;
	}
	
	public static Map<String,Object> JQGetListStaffResearchDomainManagement(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator staffResearchDomain = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			
			if(context.get("staffId") != null) {
				userLoginId = (String) context.get("staffId");
			}
			
			Collection<String> test = userLogin.getAllKeys();
			for(String t: test) {
				System.out.println("1. test User key ::::::::::" +t);
			}
			
			
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0].trim();
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"researchDomainName"}; 
				
				List<EntityCondition> condSearch = new ArrayList<EntityCondition>(); 
				for(String key: searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(key), EntityOperator.LIKE, EntityFunction.UPPER("%" + q + "%"));
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch, EntityOperator.OR));
			}
			
			Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
			List<EntityCondition> cond = new ArrayList<EntityCondition>();
			cond.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.GREATER_THAN, new Date(nowTimestamp.getTime())));
			cond.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));
			
			listAllConditions.add(EntityCondition.makeCondition(cond, EntityOperator.OR));
			
			if(filter != null) {
				listAllConditions.add(filter);				
			}
			
			listAllConditions.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("staffId"), EntityOperator.EQUALS, EntityFunction.UPPER(userLoginId)));
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			System.out.println("4. debug ::::::::::");
			staffResearchDomain = delegator.find("StaffResearchDomainView", condition, null, null, sort, opts);
				
			result.put("listIterator", staffResearchDomain);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list JQGetListResearchDomainManagement");
		}
		return result;
	}
	
	public static Map<String,Object> createStaffResearchDomain(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String,Object> result = FastMap.newInstance();
		
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			
			Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
			List<EntityCondition> cond = new ArrayList<EntityCondition>();
			cond.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.GREATER_THAN, new Date(nowTimestamp.getTime())));
			cond.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));
			
			List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
			listAllConditions.add(EntityCondition.makeCondition(cond, EntityOperator.OR));
			
			listAllConditions.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("staffId"), EntityOperator.EQUALS, EntityFunction.UPPER(userLoginId)));
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			List<GenericValue> list = delegator.findList("StaffResearchDomain", 
					condition,
					null, null, 
					null, false);
			
			for(GenericValue l: list) {
				l.put("thruDate", new Date(nowTimestamp.getTime()));
				delegator.store(l);
			}
			
			String researchDomainId = (String) ((List) context.get("researchDomainId[]")).get(0);
			
			GenericValue gvs = delegator.makeValue("StaffResearchDomain");
			String staffResearchDomainId = delegator.getNextSeqId("StaffResearchDomain");
			gvs.put("staffResearchDomainId", staffResearchDomainId);
			gvs.put("staffId", userLoginId);
			gvs.put("researchDomainId", researchDomainId);
			
			gvs.put("fromDate", new Date(nowTimestamp.getTime()));
			
			delegator.create(gvs);
			
			GenericValue newResearchDomain = delegator.findOne("StaffResearchDomainView",UtilMisc.toMap("staffResearchDomainId", staffResearchDomainId), false);
			result.put("results", BKEunivUtils.buildObject(newResearchDomain));
			result.put("message", "Create successfully");
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			result.put("message", "Did not create successfully");
		}
		return result;
	}
	
	public static Map<String,Object> updateStaffResearchDomain(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String,Object> result = FastMap.newInstance();
		
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			
			String staffResearchDomainId = (String) context.get("staffResearchDomainId");
			String researchDomainId = (String)((List) context.get("researchDomainId[]")).get(0);
			
			GenericValue gv = delegator.findOne("StaffResearchDomain", UtilMisc.toMap("staffResearchDomainId",staffResearchDomainId), false);
			if(gv != null){
				if(!gv.get("staffId").equals(userLoginId)) {
					result.put("message", "Not Access");
					return result;
				}
				

				Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
				
				gv.put("fromDate", new Date(nowTimestamp.getTime()));
				
				gv.put("researchDomainId", researchDomainId);
				
				delegator.store(gv);
				
				GenericValue researchDomain = delegator.findOne("StaffResearchDomainView",UtilMisc.toMap("staffResearchDomainId", staffResearchDomainId), false);
				
				result.put("results", BKEunivUtils.buildObject(researchDomain));
				result.put("message", "Update successfully");
			} else {
				result.put("message", "Not found StaffResearchDomain");
			}
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			result.put("message", "Did not update successfully");
		}
		return result;
	}
	
	public static Map<String,Object> deleteStaffResearchDomain(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String,Object> result = FastMap.newInstance();
		
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			
			String staffResearchDomainId = (String) context.get("staffResearchDomainId");
			
			GenericValue gv = delegator.findOne("StaffResearchDomain", UtilMisc.toMap("staffResearchDomainId",staffResearchDomainId), false);
			
			if(gv != null){
				if(!gv.get("staffId").equals(userLoginId)) {
					result.put("message", "Not Access");
					return result;
				}
				

				Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
				
				gv.put("thruDate", new Date(nowTimestamp.getTime()));
				
				delegator.store(gv);
				
				result.put("result", "Deleted record with id: " + staffResearchDomainId);
			} else {
				result.put("result", "Not found record with id: " + staffResearchDomainId);
			}
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			result.put("result", "Error delete");
		}
		return result;
	}
	
	public static Map<String,Object> JQGetListResearchSubDomainManagement(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator researchDomain = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0].trim();
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"researchSubDomainName", "researchSubDomainCode"}; 
				
				List<EntityCondition> condSearch = new ArrayList<EntityCondition>(); 
				for(String key: searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(key), EntityOperator.LIKE, EntityFunction.UPPER("%" + q + "%"));
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch, EntityOperator.OR));
			}
			
			if(filter != null) {
				listAllConditions.add(filter);				
			}
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			System.out.println("4. debug ::::::::::");
			researchDomain = delegator.find("ResearchSubDomain", condition, null, null, sort, opts);
				
			result.put("listIterator", researchDomain);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list JQGetListResearchSubDomainManagement");
		}
		return result;
	}
}
