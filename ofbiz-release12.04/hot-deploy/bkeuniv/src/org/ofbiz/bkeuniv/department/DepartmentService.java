package org.ofbiz.bkeuniv.department;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javolution.util.FastList;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class DepartmentService {
	public static final String module = DepartmentService.class.getName();
	public static String name() {
		return "DepartmentService";
	}

	public static Map<String, Object> getDepartments(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String facultyId = (String) context.get("facultyId");
		List<EntityCondition> conds = FastList.newInstance();
		if (facultyId != null && !facultyId.equals("")) {
			conds.add(EntityCondition.makeCondition("facultyId",
					EntityOperator.EQUALS, facultyId));
		}
		conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"UPDATED"));
		
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();

			List<GenericValue> list = delegator.findList("Department",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			result.put("departments", list);
			return result;

		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}

	public static Map<String, Object> getFacultyOfStaff(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = (String) userLogin.getString("userLoginId");
		String universityId = (String) context.get("universityId");
		String facultyId = (String) context.get("facultyId");

		List<EntityCondition> conds = FastList.newInstance();
		if (universityId == null)
			universityId = "HUST";

		if (universityId != null && !universityId.equals("")) {
			conds.add(EntityCondition.makeCondition("universityId",
					EntityOperator.EQUALS, universityId));
		}
		Debug.log(module + "::getFacultyOfStaff, staffId = " + staffId + ", facultyId = " + facultyId);
				
		try {
			if (facultyId == null || facultyId.equals("")) {
				GenericValue st = delegator.findOne("Staff",
						UtilMisc.toMap("staffId", staffId), false);
				String departmentId = (String) st.get("departmentId");
				GenericValue dept = delegator.findOne("Department",
						UtilMisc.toMap("departmentId", departmentId), false);
				facultyId = (String) dept.getString("facultyId");
				Debug.log(module + "::getFacultyOfStaff, staffId = " + staffId + ", GOT departmentId = " + departmentId
						+ ", GOT facultyId = " + facultyId);
			}

			GenericValue facul = delegator.findOne("Faculty",
					UtilMisc.toMap("facultyId", facultyId), false);
			List<GenericValue> list = FastList.newInstance();
			list.add(facul);
			retSucc.put("faculties", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getFaculties(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		String universityId = (String) context.get("universityId");
		String facultyId = (String) context.get("facultyId");
		List<EntityCondition> conds = FastList.newInstance();
		if (universityId == null)
			universityId = "HUST";

		if (universityId != null && !universityId.equals("")) {
			conds.add(EntityCondition.makeCondition("universityId",
					EntityOperator.EQUALS, universityId));
		}
		if(facultyId != null){
			conds.add(EntityCondition.makeCondition("facultyId",
					EntityOperator.EQUALS, facultyId));
		}
		conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"UPDATED"));
		try {
			Map<String, Object> result = ServiceUtil.returnSuccess();

			List<GenericValue> list = delegator.findList("Faculty",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			result.put("faculties", list);
			return result;

		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}

}
