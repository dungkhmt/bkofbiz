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
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javolution.util.FastList;
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
	public static Map<String, Object> getCVInformation(DispatchContext ctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		String staffId = (String) context.get("staffId");
		Map<String, Object> returnResult = FastMap.newInstance();

		try {
			EntityCondition idCond = EntityCondition.makeCondition("staffId", staffId);
			List<GenericValue> listResult = delegator.findList("StaffGenaralInformation", idCond, null, null, null,
					false);
			if (!listResult.isEmpty()) {
				GenericValue result = listResult.get(0);
				returnResult.put("academicName", result.get("hocHamName"));
				returnResult.put("academicRankId", result.get("hocHamId"));
				returnResult.put("degreeId", result.get("hocViId"));
				returnResult.put("degreeName", result.get("hocViName"));
				returnResult.put("departmentName", result.get("departmentName"));
				returnResult.put("staffName", result.get("staffName"));
				returnResult.put("academicRankYear", result.get("yearHocHam"));
				returnResult.put("degreeYear", result.get("yearHocVi"));
				returnResult.put("duty", result.get("chucVuHienNay"));
				returnResult.put("researchPositionId", result.get("chucDanhNghienCuuId"));
				returnResult.put("researchPosition", result.get("chucDanhNghienCuuName"));
			}

			List<EntityCondition> listAgencyWorkCond = FastList.newInstance();
			listAgencyWorkCond.add(idCond);
			listAgencyWorkCond.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));
			List<GenericValue> listStaffAgencyWorkInfo = delegator.findList("StaffAgencyWork",
					EntityCondition.makeCondition(listAgencyWorkCond, EntityOperator.AND), null, null, null, false);

			if (listStaffAgencyWorkInfo != null && !listStaffAgencyWorkInfo.isEmpty()) {
				GenericValue staffAgencyWorkInfo = listStaffAgencyWorkInfo.get(0);
				returnResult.put("staffAgencyWorkId", staffAgencyWorkInfo.getString("staffAgencyWorkId"));
				returnResult.put("agencyWorkLeaderName", staffAgencyWorkInfo.getString("leaderName"));
				returnResult.put("agencyWorkAddress", staffAgencyWorkInfo.getString("address"));
				returnResult.put("agencyWorkPhone", staffAgencyWorkInfo.getString("phone"));
				returnResult.put("agencyWorkFax", staffAgencyWorkInfo.getString("fax"));
			}
			retSucc.put("staffCVInfo", returnResult);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> updateCVInfo(DispatchContext dpct, Map<String, ? extends Object> context) {

		LocalDispatcher dispatcher = dpct.getDispatcher();
		Delegator delegator = (Delegator) dpct.getDelegator();
		Map<String, Object> returnResult = ServiceUtil.returnSuccess();

		String staffId = (String) context.get("staffId");
		String academicRankId = (String) context.get("academicRankId");
		String degreeId = (String) context.get("degreeId");

		Long academicRankYear = null;
		Long degreeYear = null;

		try {

			if (context.get("academicRankYear") != null) {
				academicRankYear = Long.parseLong(String.valueOf(context.get("academicRankYear")));
			}
			if (context.get("degreeYear") != null) {
				degreeYear = Long.parseLong(String.valueOf(context.get("degreeYear")));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String duty = (String) context.get("duty");
		String researchPositionId = (String) context.get("researchPositionId");

		String staffAgencyWorkId = (String) context.get("staffAgencyWorkId");
		String agencyWorkLeaderName = (String) context.get("agencyWorkLeaderName");
		String agencyWorkAddress = (String) context.get("agencyWorkAddress");
		String agencyWorkPhone = (String) context.get("agencyWorkPhone");
		String agencyWorkFax = (String) context.get("agencyWorkFax");

		try {
			GenericValue staff = delegator.findOne("Staff", UtilMisc.toMap("staffId", staffId), false);
			if (UtilValidate.isNotEmpty(staff)) {
				if (academicRankId != null)
					staff.set("hocHamId", academicRankId);
				if (degreeId != null)
					staff.set("hocViId", degreeId);
				if (academicRankYear != null)
					staff.set("yearHocHam", academicRankYear);
				if (degreeYear != null)
					staff.set("yearHocVi", degreeYear);
				if (duty != null)
					staff.set("chucVuHienNay", duty);
				if (researchPositionId != null)
					staff.set("chucDanhNghienCuuId", researchPositionId);
				
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
