package org.ofbiz.bkeuniv.award;

import java.math.BigDecimal;
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
import org.ofbiz.bkeuniv.model.budget.BudgetInOut;
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

public class AwardManagementService {

	public static String module = AwardManagementService.class.getName();

	public static Map<String, Object> jqGetListAwardKHCN(DispatchContext dpct, Map<String, ? extends Object> context)
			throws GenericEntityException {

		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		Locale locale = (Locale) context.get("locale");
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String, String[]> parameters = (Map<String, String[]>) context.get("parameters");
		Map<String, Object> result = FastMap.newInstance();

		EntityListIterator listAward = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);

			if (parameters.containsKey("q")) {
				String q = (String) parameters.get("q")[0].trim();
				String[] searchKeys = { "staffId", "staffName", "amount", "description", "date", "awardKHCNName" };

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

			listAward = delegator.find("AwardKHCNView", condition, null, null, sort, opts);

			result.put("listIterator", listAward);

		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list jqGetListAwardKHCN");
		}
		return result;
	}

	public static Map<String, Object> createAwardKHCN(DispatchContext dpct, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		BigDecimal amount = null;

		try {
			amount = BigDecimal.valueOf(Double.valueOf(String.valueOf(context.get("amount")).replace(",", "")));
		} catch (Exception e) {
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createAwardKHCN" + e);
		}

		String description = (String) context.get("description");
		String date = (String) context.get("date");

		String staffId = null;

		List<Object> listStaffId = (List<Object>) context.get("staffId[]");
		if (listStaffId != null) {
			staffId = (String) listStaffId.get(0);
		}
		String awardKHCNName = (String) context.get("awardKHCNName");
		
		GenericValue awardKHCNItem = delegator.makeValue("AwardKHCN");

		awardKHCNItem.set("awardKHCNId", delegator.getNextSeqId("AwardKHCN"));
		awardKHCNItem.set("staffId", staffId);
		awardKHCNItem.set("amount", amount);
		awardKHCNItem.set("description", description);
		awardKHCNItem.set("awardKHCNName", awardKHCNName);
		awardKHCNItem.set("date", new Timestamp(Long.valueOf(date)));

		try {
			delegator.createOrStore(awardKHCNItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createAwardKHCN" + e);
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> updateAwardKHCN(DispatchContext dpct, Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String awardKHCNId = (String) context.get("awardKHCNId");
		if (UtilValidate.isEmpty(awardKHCNId)) {
			return ServiceUtil.returnError("Error at updateBudgetInOut because: updateAwardKHCN is null");
		}

		BigDecimal amount = null;
		try {
			amount = BigDecimal.valueOf(Double.valueOf(String.valueOf(context.get("amount")).replace(",", "")));
		} catch (Exception e) {
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createBudgetInOut" + e);
		}

		String description = (String) context.get("description");
		String date = (String) context.get("date");

		String staffId = null;

		List<Object> listStaffId = (List<Object>) context.get("staffId[]");
		if (listStaffId != null) {
			staffId = (String) listStaffId.get(0);
		}

		String awardKHCNName = (String) context.get("awardKHCNName");

		try {
			GenericValue budgetInOutItem = delegator.findOne("AwardKHCN",
					UtilMisc.toMap("awardKHCNId", awardKHCNId), false);

			if (UtilValidate.isEmpty(budgetInOutItem)) {
				try {
					dispatcher.runSync("createAwardKHCN", UtilMisc.toMap("amount", amount, "staffId", staffId, "date",
							date, "description", description, "awardKHCNName", awardKHCNName));
				} catch (GenericServiceException e) {
					e.printStackTrace();
				}
			}

			budgetInOutItem.set("staffId", staffId);
			budgetInOutItem.set("amount", amount);
			budgetInOutItem.set("description", description);
			budgetInOutItem.set("awardKHCNName", awardKHCNName);
			budgetInOutItem.set("date", new Timestamp(Long.valueOf(date)));

			delegator.createOrStore(budgetInOutItem);

		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updateAwardKHCN" + e);
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> deleteAwardKHCN(DispatchContext dpct, Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String awardKHCNId = (String) context.get("awardKHCNId");
		if (UtilValidate.isEmpty(awardKHCNId)) {
			return ServiceUtil.returnError("Error at deleteAwardKHCN because: awardKHCNId is null");
		}

		try {
			GenericValue awardKHCNItem = delegator.findOne("AwardKHCN",
					UtilMisc.toMap("awardKHCNId", awardKHCNId), false);

			if (UtilValidate.isEmpty(awardKHCNItem)) {
				return ServiceUtil.returnError("Error at deleteAwardKHCN");
			}

			awardKHCNItem.set("thruDate", UtilDateTime.nowTimestamp());

			delegator.createOrStore(awardKHCNItem);

		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());

		}

		return result;
	}
}
