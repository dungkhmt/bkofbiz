package org.ofbiz.bkeuniv.cv;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.bkeuniv.cv.CiriculumVitaeService;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
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

		String staffAgencyWorkId = (String) context.get("staffAgencyWorkId");
		String agencyWorkLeaderName = (String) context.get("agencyWorkLeaderName");
		String agencyWorkAddress = (String) context.get("agencyWorkAddress");
		String agencyWorkPhone = (String) context.get("agencyWorkPhone");
		String agencyWorkFax = (String) context.get("agencyWorkFax");

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

		try {
			Map<String, Object> staffAgencyInfo = FastMap.newInstance();

			staffAgencyInfo.put("staffAgencyWorkId", staffAgencyWorkId);
			staffAgencyInfo.put("staffId", staffId);
			staffAgencyInfo.put("agencyWorkLeaderName", agencyWorkLeaderName);
			staffAgencyInfo.put("agencyWorkAddress", agencyWorkAddress);
			staffAgencyInfo.put("agencyWorkPhone", agencyWorkPhone);
			staffAgencyInfo.put("agencyWorkFax", agencyWorkFax);

			dispatcher.runSync("updateStaffAgencyWork", staffAgencyInfo);
		} catch (GenericServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnResult;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> updateStaffAgencyWork(DispatchContext dpct,
			Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> returnResult = ServiceUtil.returnSuccess();

		String staffAgencyWorkId = (String) context.get("staffAgencyWorkId");
		String staffId = (String) context.get("staffId");
		String agencyWorkLeaderName = (String) context.get("agencyWorkLeaderName");
		String agencyWorkAddress = (String) context.get("agencyWorkAddress");
		String agencyWorkPhone = (String) context.get("agencyWorkPhone");
		String agencyWorkFax = (String) context.get("agencyWorkFax");

		try {
			if (staffAgencyWorkId != null) {
				GenericValue staff = delegator.findOne("StaffAgencyWork",
						UtilMisc.toMap("staffAgencyWorkId", staffAgencyWorkId), false);
				if (UtilValidate.isNotEmpty(staff)) {
					staff.set("leaderName", agencyWorkLeaderName);
					staff.set("address", agencyWorkAddress);
					staff.set("phone", agencyWorkPhone);
					staff.set("fax", agencyWorkFax);
					staff.set("staffId", staffId);
					delegator.createOrStore(staff);
				}
			} else {
				Map<String, Object> staffAgencyInfo = FastMap.newInstance();
				staffAgencyInfo.put("staffId", staffId);
				staffAgencyInfo.put("agencyWorkLeaderName", agencyWorkLeaderName);
				staffAgencyInfo.put("agencyWorkAddress", agencyWorkAddress);
				staffAgencyInfo.put("agencyWorkPhone", agencyWorkPhone);
				staffAgencyInfo.put("agencyWorkFax", agencyWorkFax);
				try {
					dispatcher.runSync("createStaffAgencyWork", staffAgencyInfo);
				} catch (GenericServiceException e) {
					Debug.log("pullStaff error");
					e.printStackTrace();
				}
			}

		} catch (GenericEntityException e) {
			Debug.log("pullStaff error");
			e.printStackTrace();
		}

		return returnResult;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> createStaffAgencyWork(DispatchContext dpct,
			Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> returnResult = ServiceUtil.returnSuccess();

		String staffId = (String) context.get("staffId");
		String leaderName = (String) context.get("agencyWorkLeaderName");
		String address = (String) context.get("agencyWorkAddress");
		String phone = (String) context.get("agencyWorkPhone");
		String fax = (String) context.get("agencyWorkFax");

		GenericValue staffAgencyWork = delegator.makeValue("StaffAgencyWork");
		staffAgencyWork.set("staffAgencyWorkId", delegator.getNextSeqId("StaffAgencyWork"));
		staffAgencyWork.set("staffId", staffId);
		staffAgencyWork.set("startDate", UtilDateTime.nowTimestamp());
		staffAgencyWork.set("leaderName", leaderName);
		staffAgencyWork.set("address", address);
		staffAgencyWork.set("phone", phone);
		staffAgencyWork.set("fax", fax);

		try {
			delegator.create(staffAgencyWork);
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}

		return returnResult;
	}

}
