package org.ofbiz.bkeuniv.recentresearchdirection;

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
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javolution.util.FastList;
import javolution.util.FastMap;

public class RecentResearchDirectionService {
	
	public static String module = RecentResearchDirectionService.class.getName();
	public static final String resource = "CommonUiLabels";
	
	public static Map<String,Object> jqGetListRecentResearchDirection(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		Locale locale = (Locale) context.get("locale");
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator listRecentResearchDirection = null;
		
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
				String[] searchKeys = {"keywords", "startYear"}; 
				
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
			
			listRecentResearchDirection = delegator.find("RecentResearchDirection", condition, null, null, sort, opts);
			
			List<GenericValue> listRecentResearchDirectionTmp = listRecentResearchDirection.getCompleteList();
			
			listRecentResearchDirection.close();
			
			List<Map<String, Object>> listResults = FastList.newInstance();
			
			for(GenericValue researchDirection: listRecentResearchDirectionTmp) {
				Map<String, Object> item = FastMap.newInstance();
				item.put("recentResearchDirectionId", researchDirection.getString("recentResearchDirectionId"));
				item.put("keywords", researchDirection.getString("keywords"));
				item.put("startYear", researchDirection.getString("startYear"));
				item.put("staffId", researchDirection.getString("staffId"));
				item.put("startDate", researchDirection.getString("startDate"));
				item.put("description", researchDirection.getString("description"));

				listResults.add(item);
			}
			
			listRecentResearchDirectionTmp.clear();
			
			result.put("listIterator", listResults);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list jqGetListRecentResearchDirection" + e);
		}
		return result;
	}
	
	
	@SuppressWarnings("unused")
	public static Map<String, Object> createRecentResearchDirection(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String keywords = (String) context.get("keywords");

		String description = (String) context.get("description");
		Long startYear = Long.parseLong(String.valueOf(context.get("startYear")));
		
		GenericValue recentResearchDirection = delegator.makeValue("RecentResearchDirection");
		
		recentResearchDirection.set("recentResearchDirectionId", delegator.getNextSeqId("RecentResearchDirection"));
		recentResearchDirection.set("staffId", staffId);
		recentResearchDirection.set("keywords", keywords);
		recentResearchDirection.set("startYear", startYear);
		recentResearchDirection.set("description", description);

		recentResearchDirection.set("startDate", UtilDateTime.nowTimestamp());
		
		try {
			delegator.create(recentResearchDirection);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createRecentResearchDirection" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> updateRecentResearchDirection(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String recentResearchDirectionId = (String) context.get("recentResearchDirectionId");
		if(UtilValidate.isEmpty(recentResearchDirectionId)) {
			return ServiceUtil.returnError("Error at updateRecentResearchDirection because: recentResearchDirectionId is null");
		}
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		
		String staffId = userLogin.getString("userLoginId");
		String keywords = (String) context.get("keywords");
		String description = (String) context.get("description");

		Long startYear = Long.parseLong(String.valueOf(context.get("startYear")));
		
		try {
			GenericValue recentResearchDirection = delegator.findOne("RecentResearchDirection", UtilMisc.toMap("recentResearchDirectionId", recentResearchDirectionId), false );
		
			if(UtilValidate.isEmpty(recentResearchDirection)) {
				try {
					dispatcher.runSync("createRecentResearchDirection", UtilMisc.toMap("staffId", staffId, "keywords", keywords, "startYear", startYear, "description", description));
				} catch (GenericServiceException e) {
					e.printStackTrace();
				}
			}
			
			recentResearchDirection.set("keywords", keywords);
			recentResearchDirection.set("startYear", startYear);
			recentResearchDirection.set("description", description);
		
			delegator.createOrStore(recentResearchDirection);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updateRecentResearchDirection" + e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, Object> deleteRecentResearchDirection(DispatchContext dpct,Map<String,?extends Object> context){
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		
		String recentResearchDirectionId = (String) context.get("recentResearchDirectionId");
		if(UtilValidate.isEmpty(recentResearchDirectionId)) {
			return ServiceUtil.returnError("Error at deleteRecentResearchDirection because: recentResearchDirectionId is null");
		}
		
		try {
			GenericValue recentResearchDirection = delegator.findOne("RecentResearchDirection", UtilMisc.toMap("recentResearchDirectionId", recentResearchDirectionId), false );
		
			if(UtilValidate.isEmpty(recentResearchDirection)) {
				return ServiceUtil.returnError("Error at deleteRecentResearchDirection");
			}
			
			recentResearchDirection.set("thruDate", UtilDateTime.nowTimestamp());
		
			delegator.createOrStore(recentResearchDirection);
			
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			
		}
		
		return result;
	}

}
