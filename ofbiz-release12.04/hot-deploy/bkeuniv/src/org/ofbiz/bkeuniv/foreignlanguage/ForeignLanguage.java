package org.ofbiz.bkeuniv.foreignlanguage;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.commons.collections.MapUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class ForeignLanguage {
	public static final String module = ForeignLanguage.class.getName();

	public static Map<String, Object> getListForeignLanguageCatalog(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = userLogin.getString("userLoginId");

		try {
			List<GenericValue> lst = delegator.findList(
					"ForeignLanguageCatalog", null, null, null, null, false);

			retSucc.put("foreignlanguageCatalog", lst);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> getForeignLanguage(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = userLogin.getString("userLoginId");

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			List<GenericValue> lst = delegator.findList(
					"ForeignLanguageStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			retSucc.put("foreignlanguages", lst);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> createForeignLanguage(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = userLogin.getString("userLoginId");
		List<String> lst_foreignLanguageCatalogId = (List<String>) context
				.get("foreignLanguageCatalogId[]");
		String foreignLanguageCatalogId = null;
		String listen = (String) context.get("listen");
		String speaking = (String) context.get("speaking");
		String reading = (String) context.get("reading");
		String writing = (String) context.get("writing");
		try {
			if (lst_foreignLanguageCatalogId != null
					&& lst_foreignLanguageCatalogId.size() > 0)
				foreignLanguageCatalogId = lst_foreignLanguageCatalogId.get(0);

			String foreignLanguageId = delegator
					.getNextSeqId("ForeignLanguage");
			GenericValue gv = delegator.makeValue("ForeignLanguage");
			gv.put("foreignLanguageId", foreignLanguageId);
			gv.put("staffId", staffId);

			if (foreignLanguageCatalogId != null)
				gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId);

			if (listen != null)
				gv.put("listen", listen);

			if (speaking != null)
				gv.put("speaking", speaking);

			if (reading != null)
				gv.put("reading", reading);

			if (writing != null)
				gv.put("writing", writing);

			delegator.create(gv);

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("foreignLanguageId",
					EntityOperator.EQUALS, foreignLanguageId));
			GenericValue m = delegator.findOne("ForeignLanguageStaffView",
					UtilMisc.toMap("foreignLanguageId", foreignLanguageId),
					false);

			retSucc.put("foreignlanguages", m);
			retSucc.put("message", "Create Successfully!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> updateForeignLanguage(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = userLogin.getString("userLoginId");
		String foreignLanguageId = (String) context.get("foreignLanguageId");
		List<String> lst_foreignLanguageCatalogId = (List<String>) context
				.get("foreignLanguageCatalogId[]");
		String foreignLanguageCatalogId = null;
		String listen = (String) context.get("listen");
		String speaking = (String) context.get("speaking");
		String reading = (String) context.get("reading");
		String writing = (String) context.get("writing");
		
		
		try {
			if (lst_foreignLanguageCatalogId != null
					&& lst_foreignLanguageCatalogId.size() > 0)
				foreignLanguageCatalogId = lst_foreignLanguageCatalogId.get(0);

			Debug.log(module + "::updateForeignLanguage, foreignLanguageId = " + foreignLanguageId + 
					", foreignLanguageCatalogId = " + foreignLanguageCatalogId + ", listen = " + listen
					+ ", speaking = " + speaking + ", reading = " + reading + ", writing = " + writing);
			
			GenericValue gv = delegator.findOne("ForeignLanguage",
					UtilMisc.toMap("foreignLanguageId", foreignLanguageId),
					false);

			if (gv != null) {
				if (foreignLanguageCatalogId != null)
					gv.put("foreignLanguageCatalogId", foreignLanguageCatalogId);

				if (listen != null)
					gv.put("listen", listen);

				if (speaking != null)
					gv.put("speaking", speaking);

				if (reading != null)
					gv.put("reading", reading);

				if (writing != null)
					gv.put("writing", writing);

				delegator.store(gv);
			}

			//List<EntityCondition> conds = FastList.newInstance();
			//conds.add(EntityCondition.makeCondition("foreignLanguageId",
			//		EntityOperator.EQUALS, foreignLanguageId));
			GenericValue m = delegator.findOne("ForeignLanguageStaffView",
					UtilMisc.toMap("foreignLanguageId", foreignLanguageId),
					false);

			retSucc.put("foreignlanguages", m);
			retSucc.put("message", "Update Successfully!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> deleteForeignLanguage(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = userLogin.getString("userLoginId");
		String foreignLanguageId = (String) context.get("foreignLanguageId");
		try {

			GenericValue gv = delegator.findOne("ForeignLanguage",
					UtilMisc.toMap("foreignLanguageId", foreignLanguageId),
					false);

			if (gv != null) {
				delegator.removeValue(gv);
			}
			retSucc.put("message", "Delete Successfully!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

}
