package org.ofbiz.bkeuniv.officialdocument;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class GetDataOfficialDocument {
	public static Map<String,Object> JQGetListOfficialDocument(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator officialDocuments = null;
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
				String[] searchKeys = {"staffId", "staffName", "officialDocumentTypeName", "officialDocumentName", "officialDocumentId"}; 
				
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
			
		 	officialDocuments = delegator.find("OfficialDocumentsView", condition, null, null, sort, opts);
			
			result.put("listIterator", officialDocuments);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Debug Error get list OfficialDocuments");
		} finally {
//			if(staffs != null){
//				staffs.close();
//			}
		}
		return result;
	}
	
	public static Map<String,Object> JQGetListOfficialDocumentType(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator officialDocumentTypes = null;
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
				String[] searchKeys = {"officialDocumentTypeName", "officialDocumentTypeId"}; 
				
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
			
		 	officialDocumentTypes = delegator.find("OfficialDocumentTypes", condition, null, null, sort, opts);
			
			result.put("listIterator", officialDocumentTypes);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Debug Error get list officialDocumentTypes");
		} finally {
//			if(staffs != null){
//				staffs.close();
//			}
		}
		return result;
	}
}
