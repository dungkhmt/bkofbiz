package org.ofbiz.bkeuniv.studentresearchconference;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javolution.util.FastList;
import javolution.util.FastMap;

public class StudentResearchConferenceService {
	
	public static String module = StudentResearchConferenceService.class.getName();
	public static final String resource = "CommonUiLabels";
	
	public static Map<String,Object> jqGetListStudentResearchConference(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		Locale locale = (Locale) context.get("locale");
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator listStudentResearchConferenceCall = null;
		
		Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
		
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(context.get("staffId") != null) {
				userLoginId = (String) context.get("staffId");
			}
			
			if(parameters.containsKey("q")) {
				String q = (String)parameters.get("q")[0].trim();
				String[] searchKeys = {"studentResearchCallId", "studentResearchCallName"}; 
				
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
			
			listAllConditions.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			listStudentResearchConferenceCall = delegator.find("StudentResearchConferenceCall", condition, null, null, sort, opts);
			
			result.put("listIterator", listStudentResearchConferenceCall);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list jqGetListStudentResearchConference" + e);
		}
		return result;
	}
	
	
	@SuppressWarnings("unused")
	public static Map<String, Object> createResearchConference(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String studentResearchCallName = (String) context.get("studentResearchCallName");

		String date = (String) context.get("date");
		
		GenericValue studentResearchConferenceCall = delegator.makeValue("StudentResearchConferenceCall");
		
		studentResearchConferenceCall.set("studentResearchCallId", delegator.getNextSeqId("StudentResearchConferenceCall"));
		studentResearchConferenceCall.set("studentResearchCallName", studentResearchCallName);
		studentResearchConferenceCall.set("date", new Timestamp(Long.valueOf(date)));

		try {
			delegator.create(studentResearchConferenceCall);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createRecentResearchDirection" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> updateResearchConference(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String studentResearchCallId = (String) context.get("studentResearchCallId");
		if(UtilValidate.isEmpty(studentResearchCallId)) {
			return ServiceUtil.returnError("Error at updateResearchConference because: studentResearchCallId is null");
		}
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String studentResearchCallName = (String) context.get("studentResearchCallName");

		String date = (String) context.get("date");

		
		try {
			GenericValue studentResearchConferenceCall = delegator.findOne("StudentResearchConferenceCall", UtilMisc.toMap("studentResearchCallId", studentResearchCallId), false );
		
			if(UtilValidate.isEmpty(studentResearchConferenceCall)) {
				try {
					dispatcher.runSync("createResearchConference", UtilMisc.toMap("studentResearchCallName", studentResearchCallName, "date", date));
				} catch (GenericServiceException e) {
					e.printStackTrace();
				}
			}
			
			studentResearchConferenceCall.set("studentResearchCallName", studentResearchCallName);
			studentResearchConferenceCall.set("date", new Timestamp(Long.valueOf(date)));
		
			delegator.createOrStore(studentResearchConferenceCall);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updateRecentResearchDirection" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> deleteResearchConference(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String studentResearchCallId = (String) context.get("studentResearchCallId");
		if(UtilValidate.isEmpty(studentResearchCallId)) {
			return ServiceUtil.returnError("Error at deleteResearchConference because: studentResearchCallId is null");
		}
		
		try {
			GenericValue studentResearchConferenceCall = delegator.findOne("StudentResearchConferenceCall", UtilMisc.toMap("studentResearchCallId", studentResearchCallId), false );
		
			if(UtilValidate.isEmpty(studentResearchConferenceCall)) {
				return ServiceUtil.returnError("Error at deleteRecentResearchDirection");
			}
			
			studentResearchConferenceCall.set("thruDate", UtilDateTime.nowTimestamp());
		
			delegator.createOrStore(studentResearchConferenceCall);
			
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			
		}
		
		return result;
	}
}
