package org.ofbiz.bkeuniv.budgetmanagement;

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
import org.ofbiz.base.util.UtilProperties;
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

import javolution.util.FastList;
import javolution.util.FastMap;

public class BudgetManagementService {

	public static String module = BudgetManagementService.class.getName();
	public static final String resource = "BkEunivUiLabels";

	public static Map<String, Object> jqGetListBudgetInOut(DispatchContext dpct, Map<String, ? extends Object> context)
			throws GenericEntityException {

		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		Locale locale = (Locale) context.get("locale");
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String, String[]> parameters = (Map<String, String[]>) context.get("parameters");
		Map<String, Object> result = FastMap.newInstance();

		String InLabel = UtilProperties.getMessage(resource, "BudgetIn", locale);
		String OutLabel = UtilProperties.getMessage(resource, "BudgetOut", locale);

		EntityListIterator listBudget = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);

			if (parameters.containsKey("q")) {
				String q = (String) parameters.get("q")[0].trim();
				String[] searchKeys = { "staffId", "staffName", "amount", "description", "date", "inOutFlag" };

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

			listBudget = delegator.find("BudgetInOutView", condition, null, null, sort, opts);

			List<GenericValue> listBudgetTmp = listBudget.getCompleteList();
			listBudget.close();

			List<Map<String, Object>> listBudgetResult = FastList.newInstance();
			for (GenericValue budget : listBudgetTmp) {
				Map<String, Object> item = FastMap.newInstance();
				item.put("staffName", budget.getString("staffName"));
				item.put("description", budget.getString("description"));
				item.put("amount", (BigDecimal) budget.get("amount"));
				item.put("date", budget.getString("date"));
				item.put("inOutFlag", budget.getString("inOutFlag"));
				item.put("budgetInOutId", budget.getString("budgetInOutId"));
				item.put("staffId", budget.getString("staffId"));
				if (budget.getString("inOutFlag").equals(BudgetInOut.IN.getValue())) {
					item.put("inOutFlagName", InLabel);
				} else {
					item.put("inOutFlagName", OutLabel);;
				}
				listBudgetResult.add(item);
			}

			listBudgetTmp.clear();

			result.put("listIterator", listBudgetResult);

		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list jqGetListBudget");
		}
		return result;
	}

	public static Map<String, Object> createBudgetInOut(DispatchContext dpct, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

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

		String inOutFlag = null;

		List<Object> listInOutFlag = (List<Object>) context.get("inOutFlag[]");
		if (listInOutFlag != null) {
			if (String.valueOf(listInOutFlag.get(0)).equals("OUT")) {
				inOutFlag = BudgetInOut.OUT.getValue();
			} else {
				inOutFlag = BudgetInOut.IN.getValue();
			}
		}

		GenericValue budgetInOutItem = delegator.makeValue("BudgetInOut");

		budgetInOutItem.set("budgetInOutId", delegator.getNextSeqId("BudgetInOut"));
		budgetInOutItem.set("staffId", staffId);
		budgetInOutItem.set("amount", amount);
		budgetInOutItem.set("description", description);
		budgetInOutItem.set("inOutFlag", inOutFlag);
		budgetInOutItem.set("date", new Timestamp(Long.valueOf(date)));

		try {
			delegator.createOrStore(budgetInOutItem);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at createBudgetInOut" + e);
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> updateBudgetInOut(DispatchContext dpct, Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String budgetInOutId = (String) context.get("budgetInOutId");
		if (UtilValidate.isEmpty(budgetInOutId)) {
			return ServiceUtil.returnError("Error at updateBudgetInOut because: budgetInOutId is null");
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

		String inOutFlag = null;

		List<Object> listInOutFlag = (List<Object>) context.get("inOutFlag[]");
		if (listInOutFlag != null) {
			if (String.valueOf(listInOutFlag.get(0)).equals("OUT")) {
				inOutFlag = BudgetInOut.OUT.getValue();
			} else {
				inOutFlag = BudgetInOut.IN.getValue();
			}
		}

		try {
			GenericValue budgetInOutItem = delegator.findOne("BudgetInOut",
					UtilMisc.toMap("budgetInOutId", budgetInOutId), false);

			if (UtilValidate.isEmpty(budgetInOutItem)) {
				try {
					dispatcher.runSync("createBudgetInOut", UtilMisc.toMap("amount", amount, "staffId", staffId, "date",
							date, "description", description, "inOutFlag", inOutFlag));
				} catch (GenericServiceException e) {
					e.printStackTrace();
				}
			}

			budgetInOutItem.set("staffId", staffId);
			budgetInOutItem.set("amount", amount);
			budgetInOutItem.set("description", description);
			budgetInOutItem.set("inOutFlag", inOutFlag);
			budgetInOutItem.set("date", new Timestamp(Long.valueOf(date)));

			delegator.createOrStore(budgetInOutItem);

		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error at updateBudgetInOut" + e);
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> deleteBudgetInOut(DispatchContext dpct, Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String budgetInOutId = (String) context.get("budgetInOutId");
		if (UtilValidate.isEmpty(budgetInOutId)) {
			return ServiceUtil.returnError("Error at deleteBudgetInOut because: budgetInOutId is null");
		}

		try {
			GenericValue budgetInOutItem = delegator.findOne("IntellectualProperty",
					UtilMisc.toMap("budgetInOutId", budgetInOutId), false);

			if (UtilValidate.isEmpty(budgetInOutItem)) {
				return ServiceUtil.returnError("Error at deleteBudgetInOut");
			}

			budgetInOutItem.set("thruDate", UtilDateTime.nowTimestamp());

			delegator.createOrStore(budgetInOutItem);

		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.log(e.getMessage());

		}

		return result;
	}
}
