package org.ofbiz.bkeuniv.thesismanagement;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
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
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javolution.util.FastList;
import javolution.util.FastMap;

public class ThesisManagementService {
	
	public static String module = ThesisManagementService.class.getName();
	public static final String resource = "CommonUiLabels";
	
	public static Map<String,Object> jqGetListThesisSubject(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		Locale locale = (Locale) context.get("locale");
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator listThesisSubjects = null;
		
		Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
		
		String YesLabel = UtilProperties.getMessage("CommonUiLabels", "CommonYes", locale);
		String NoLabel = UtilProperties.getMessage("CommonUiLabels", "CommonNo", locale);

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
				String[] searchKeys = {"thesisSubjectPhDMasterName"}; 
				
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
			
			listAllConditions.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("staffId"), EntityOperator.EQUALS, EntityFunction.UPPER(userLoginId)));
			listAllConditions.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
		 	listThesisSubjects = delegator.find("ThesisSubjectView", condition, null, null, sort, opts);
			
			
			result.put("listIterator", listThesisSubjects);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list JQGetListPhdStudentSupervison" + e);
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> createThesisSubject (DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String thesisSubjectPhDMasterName = (String) context.get("thesisSubjectPhDMasterName");
		String description = (String) context.get("description");
		
		List<Object> educationLevelIds = (List<Object>)context.get("educationLevelId[]");
		
		String educationLevelId = null;
		
		if(educationLevelIds != null) {
			educationLevelId = (String) educationLevelIds.get(0);
		}
		
		GenericValue thesisSubject = delegator.makeValue("ThesisSubjectPhDMaster");
		
		thesisSubject.set("thesisSubjectPhDMasterId", delegator.getNextSeqId("ThesisSubjectPhDMaster"));
		thesisSubject.set("staffId", staffId);
		thesisSubject.set("thesisSubjectPhDMasterName", thesisSubjectPhDMasterName);
		thesisSubject.set("educationLevelId", educationLevelId);
		thesisSubject.set("description", description);
		thesisSubject.set("startDate", UtilDateTime.nowTimestamp());
		
		try {
			delegator.create(thesisSubject);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createThesisSubject" + e);
		}
		
		return result;
	}
	
	
	@SuppressWarnings("unused")
	public static Map<String, Object> updateThesisSubject(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String thesisSubjectPhDMasterId = (String) context.get("thesisSubjectPhDMasterId");
		String thesisSubjectPhDMasterName = (String) context.get("thesisSubjectPhDMasterName");
		String description = (String) context.get("description");
		
		List<Object> educationLevelIds = (List<Object>)context.get("educationLevelId[]");
		
		String educationLevelId = null;
		
		if(educationLevelIds != null) {
			educationLevelId = (String) educationLevelIds.get(0);
		}
		
		try {
			GenericValue thesisSubjectItem = delegator.findOne("ThesisSubjectPhDMaster", UtilMisc.toMap("thesisSubjectPhDMasterId", thesisSubjectPhDMasterId), false );
		
			if(UtilValidate.isEmpty(thesisSubjectItem)) {
				thesisSubjectItem = delegator.makeValue("ThesisSubjectPhDMaster");
				thesisSubjectItem.set("thesisSubjectPhDMasterId", delegator.getNextSeqId("ThesisSubjectPhDMaster"));
			}
			
			thesisSubjectItem.set("thesisSubjectPhDMasterName", thesisSubjectPhDMasterName);
			thesisSubjectItem.set("educationLevelId", educationLevelId);
			thesisSubjectItem.set("description", description);
		
			delegator.createOrStore(thesisSubjectItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updatePhdStudentSupervision" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> deleteThesisSubject(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String thesisSubjectPhDMasterId = (String) context.get("thesisSubjectPhDMasterId");
		
		if(UtilValidate.isEmpty(thesisSubjectPhDMasterId)) {
			return ServiceUtil.returnError("Error at deleteThesisSubject because: thesisSubjectPhDMasterId is null");
		}
		
		try {
			GenericValue thesisSubjectItem = delegator.findOne("ThesisSubjectPhDMaster", UtilMisc.toMap("thesisSubjectPhDMasterId", thesisSubjectPhDMasterId), false );
		
			if(UtilValidate.isEmpty(thesisSubjectItem)) {
				return ServiceUtil.returnError("Error at deleteThesisSubject");
			}
			
			thesisSubjectItem.set("thruDate", UtilDateTime.nowTimestamp());
		
			delegator.createOrStore(thesisSubjectItem);
			
			result.put("message", "Xoa thanh cong");
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			
		}
		
		return result;
	}

}
