package org.ofbiz.bkeuniv.intellectualproperty;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

import javolution.util.FastMap;

public class IntellectualPropertyService {

	public static Map<String, Object> jqGetListIntellectualProperty(DispatchContext dpct,
			Map<String, ? extends Object> context) throws GenericEntityException {

		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String, String[]> parameters = (Map<String, String[]>) context.get("parameters");
		Map<String, Object> result = FastMap.newInstance();
		EntityListIterator listIntellectualProperty = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);

			if (parameters.containsKey("q")) {
				String q = (String) parameters.get("q")[0].trim();
				String[] searchKeys = { "staffId", "staffName", "intellectualPropertyName", "description", "date" };

				List<EntityCondition> condSearch = new ArrayList<EntityCondition>();
				for (String key : searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(key),
							EntityOperator.LIKE, EntityFunction.UPPER("%" + q + "%"));
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch, EntityOperator.OR));
			}

			if (filter != null) {
				listAllConditions.add(filter);
			}

			listAllConditions.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));

			EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);

			listIntellectualProperty = delegator.find("IntellectualPropertyView", condition, null, null, sort, opts);

			result.put("listIterator", listIntellectualProperty);

		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list jqGetListIntellectualProperty");
		}
		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> createIntellectualProperty(DispatchContext dpct,
			Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String intellectualPropertyName = (String) context.get("intellectualPropertyName");
		String description = (String) context.get("description");
		String date = (String) context.get("date");
		String staffId = null;

		List<Object> listStaffId = (List<Object>) context.get("staffId[]");
		if (listStaffId != null) {
			staffId = (String) listStaffId.get(0);
		}

		GenericValue itellectualPropertyItem = delegator.makeValue("IntellectualProperty");

		itellectualPropertyItem.set("intellectualPropertyId", delegator.getNextSeqId("IntellectualProperty"));
		itellectualPropertyItem.set("staffId", staffId);
		itellectualPropertyItem.set("intellectualPropertyName", intellectualPropertyName);
		itellectualPropertyItem.set("description", description);
		itellectualPropertyItem.set("date", new Timestamp(Long.valueOf(date)));
		
		try {
			delegator.createOrStore(itellectualPropertyItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createIntellectualProperty" + e);
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> updateIntellectualProperty(DispatchContext dpct,
			Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String intellectualPropertyId = (String) context.get("intellectualPropertyId");
		if (UtilValidate.isEmpty(intellectualPropertyId)) {
			return ServiceUtil
					.returnError("Error at updateIntellectualProperty because: intellectualPropertyId is null");
		}

		String intellectualPropertyName = (String) context.get("intellectualPropertyName");
		String description = (String) context.get("description");
		String date = (String) context.get("date");
		String staffId = null;

		List<Object> listStaffId = (List<Object>) context.get("staffId[]");
		if (listStaffId != null) {
			staffId = (String) listStaffId.get(0);
		}

		try {
			GenericValue intellectualPropertyItem = delegator.findOne("IntellectualProperty",
					UtilMisc.toMap("intellectualPropertyId", intellectualPropertyId), false);

			if (UtilValidate.isEmpty(intellectualPropertyItem)) {
				try {
					dispatcher.runSync("createIntellectualProperty", UtilMisc.toMap("intellectualPropertyName", intellectualPropertyName, "staffId",
							staffId, "date", date, "description", description));
				} catch (GenericServiceException e) {
					e.printStackTrace();
				}
			}

			intellectualPropertyItem.set("intellectualPropertyName", intellectualPropertyName);
			intellectualPropertyItem.set("staffId", staffId);
			intellectualPropertyItem.set("date", new Timestamp(Long.valueOf(date)));
			intellectualPropertyItem.set("description", description);

			delegator.createOrStore(intellectualPropertyItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updateRecentResearchDirection" + e);
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> deleteIntellectualProperty(DispatchContext dpct,
			Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String intellectualPropertyId = (String) context.get("intellectualPropertyId");
		if (UtilValidate.isEmpty(intellectualPropertyId)) {
			return ServiceUtil
					.returnError("Error at deleteIntellectualProperty because: intellectualPropertyId is null");
		}

		try {
			GenericValue intellectualPropertyItem = delegator.findOne("IntellectualProperty",
					UtilMisc.toMap("intellectualPropertyId", intellectualPropertyId), false);

			if (UtilValidate.isEmpty(intellectualPropertyItem)) {
				return ServiceUtil.returnError("Error at deleteRecentResearchDirection");
			}

			intellectualPropertyItem.set("thruDate", UtilDateTime.nowTimestamp());

			delegator.createOrStore(intellectualPropertyItem);

		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());

		}

		return result;
	}
}
