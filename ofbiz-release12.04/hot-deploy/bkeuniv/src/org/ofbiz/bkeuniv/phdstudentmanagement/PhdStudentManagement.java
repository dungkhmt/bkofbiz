package org.ofbiz.bkeuniv.phdstudentmanagement;

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

public class PhdStudentManagement{
	
	public static String module = PhdStudentManagement.class.getName();
	public static final String resource = "CommonUiLabels";
	
	public static Map<String,Object> JQGetListPhdStudentSupervison(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		Locale locale = (Locale) context.get("locale");
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator listPhdStudentQueryResult = null;
		
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
				String[] searchKeys = {"studentName", "thesisName", "graduateYear"}; 
				
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
			
			listPhdStudentQueryResult = delegator.find("PhdStudentView", condition, null, null, sort, opts);
			
			List<GenericValue> listPhdStudentTmp = listPhdStudentQueryResult.getCompleteList();
			
			listPhdStudentQueryResult.close();
			
			List<Map<String, Object>> listResults = FastList.newInstance();
			
			for(GenericValue student : listPhdStudentTmp) {
				Map<String, Object> item = FastMap.newInstance();
				item.put("phDSupervisionId", student.getString("phDSupervisionId"));
				item.put("staffId", student.getString("staffId"));
				item.put("studentName", student.getString("studentName"));
				item.put("thesisName", student.getString("thesisName"));
				item.put("coSupervion", student.getString("coSupervion"));
				item.put("graduateYear", student.getString("graduateYear"));
				
				String coSupervion = student.getString("coSupervion");
				if("YES".equals(coSupervion)) {
					item.put("coSupervionName", YesLabel);
				}else {
					item.put("coSupervionName", NoLabel);
				}
				listResults.add(item);
			}
			
			listPhdStudentTmp.clear();
			
			result.put("listIterator", listResults);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list JQGetListPhdStudentSupervison" + e);
		}
		return result;
	}
	
	
	@SuppressWarnings("unused")
	public static Map<String, Object> createPhdStudentSupervision(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String studentName = (String) context.get("studentName");
		String thesisName = (String) context.get("thesisName");
		List<Object> coSupervions = (List<Object>)context.get("coSupervion[]");
		String coSupervion = "NO";
		if(coSupervions != null) {
			coSupervion = (String)coSupervions.get(0);
		} 
		Long graduateYear = Long.parseLong(String.valueOf(context.get("graduateYear")));
		
		GenericValue phdSupervisionItem = delegator.makeValue("PhDSupervision");
		
		phdSupervisionItem.set("phDSupervisionId", delegator.getNextSeqId("PhDSupervision"));
		phdSupervisionItem.set("staffId", staffId);
		phdSupervisionItem.set("studentName", studentName);
		phdSupervisionItem.set("thesisName", thesisName);
		phdSupervisionItem.set("coSupervion", coSupervion);
		phdSupervisionItem.set("graduateYear", graduateYear);
		phdSupervisionItem.set("startDate", UtilDateTime.nowTimestamp());
		
		try {
			delegator.create(phdSupervisionItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createPhdStudentSupervision" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> updatePhdStudentSupervision(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String phDSupervisionId = (String) context.get("phDSupervisionId");
		if(UtilValidate.isEmpty(phDSupervisionId)) {
			return ServiceUtil.returnError("Error at updatePhdStudentSupervision because: phDSupervisionId is null");
		}
		
		String studentName = (String) context.get("studentName");
		String thesisName = (String) context.get("thesisName");
		List<Object> coSupervions = (List<Object>)context.get("coSupervion[]");
		String coSupervion = "NO";
		if(coSupervions != null) {
			coSupervion = (String)coSupervions.get(0);
		} 
		Long graduateYear = Long.parseLong(String.valueOf(context.get("graduateYear")));
		
		try {
			GenericValue phdSupervisionItem = delegator.findOne("PhDSupervision", UtilMisc.toMap("phDSupervisionId", phDSupervisionId), false );
		
			if(UtilValidate.isEmpty(phdSupervisionItem)) {
				phdSupervisionItem = delegator.makeValue("PhDSupervision");
				phdSupervisionItem.set("phDSupervisionId", delegator.getNextSeqId("PhDSupervision"));
			}
			
			phdSupervisionItem.set("studentName", studentName);
			phdSupervisionItem.set("thesisName", thesisName);
			phdSupervisionItem.set("coSupervion", coSupervion);
			phdSupervisionItem.set("graduateYear", graduateYear);
		
			delegator.createOrStore(phdSupervisionItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updatePhdStudentSupervision" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> deletePhdStudentSupervision(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String phDSupervisionId = (String) context.get("phDSupervisionId");
		if(UtilValidate.isEmpty(phDSupervisionId)) {
			return ServiceUtil.returnError("Error at updatePhdStudentSupervision because: phDSupervisionId is null");
		}
		
		try {
			GenericValue phdSupervisionItem = delegator.findOne("PhDSupervision", UtilMisc.toMap("phDSupervisionId", phDSupervisionId), false );
		
			if(UtilValidate.isEmpty(phdSupervisionItem)) {
				return ServiceUtil.returnError("Error at updatePhdStudentSupervision");
			}
			
			phdSupervisionItem.set("thruDate", UtilDateTime.nowTimestamp());
		
			delegator.createOrStore(phdSupervisionItem);
			
			result.put("message", "Xoa thanh cong");
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			
		}
		
		return result;
	}
	
}