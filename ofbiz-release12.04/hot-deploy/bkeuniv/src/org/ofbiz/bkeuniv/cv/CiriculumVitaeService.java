package org.ofbiz.bkeuniv.cv;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.bkeuniv.cv.CiriculumVitaeService;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javolution.util.FastMap;

public class CiriculumVitaeService {

	public static final String module = CiriculumVitaeService.class.getName();

	@SuppressWarnings("unused")
	public static Map<String, Object> getListAcademicRank(DispatchContext dpct, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> returnResult = ServiceUtil.returnSuccess();
		try {
			List<GenericValue> listAcademicRank = delegator.findList("HocHam", null, null, null, null, false);
			if (!listAcademicRank.isEmpty()) {
				returnResult.put("listAcademicRank", listAcademicRank);
			}
		} catch (GenericEntityException e) {
			Debug.log("pullStaff error");
			returnResult.put("statusCode", "404");
			e.printStackTrace();
		}

		return returnResult;

	}

	@SuppressWarnings("unused")
	public static Map<String, Object> getListDegree(DispatchContext dpct, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> returnResult = ServiceUtil.returnSuccess();
		try {
			List<GenericValue> listDegree = delegator.findList("HocVi", null, null, null, null, false);
			if (!listDegree.isEmpty()) {
				returnResult.put("listDegree", listDegree);
			}
		} catch (GenericEntityException e) {
			Debug.log("pullStaff error");
			returnResult.put("statusCode", "404");
			e.printStackTrace();
		}

		return returnResult;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> updateCVInfo(DispatchContext dpct, Map<String, ? extends Object> context) {
		
		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> returnResult = ServiceUtil.returnSuccess();
		
		String staffId = (String) context.get("staffId");
		String academicRankId = (String) context.get("academicRankId");
		String degreeId = (String) context.get("degreeId");
		
		Long academicRankYear = Long.parseLong(String.valueOf(context.get("academicRankYear")));
		
		Long degreeYear = Long.parseLong(String.valueOf(context.get("degreeYear")));
		
		String duty = (String) context.get("duty");
		
		try {
			GenericValue staff = delegator.findOne("Staff", UtilMisc.toMap("staffId", staffId), false);
			if (UtilValidate.isNotEmpty(staff)) {
				staff.set("hocHamId", academicRankId);
				staff.set("hocViId", degreeId);
				staff.set("yearHocHam", academicRankYear);
				staff.set("yearHocVi", degreeYear);
				staff.set("chucVuHienNay", duty);
				delegator.createOrStore(staff);
				returnResult.put("statusCode", "200");
			}
		} catch (GenericEntityException e) {
			Debug.log("pullStaff error");
			returnResult.put("statusCode", "404");
			e.printStackTrace();
		}

		return returnResult;
	}

}
