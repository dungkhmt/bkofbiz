package src.org.ofbiz.bkeuniv.paperdeclaration;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class PaperDeclarationUtil {
	public static final String STATUS_ENABLED = "ENABLED";
	public static final String STATUS_DISABLED = "DISABLED";
	public static final String module = PaperDeclarationUtil.class.getName();


	public static Map<String, Object> deleteStaffPaperDeclaration(
			String staffId, String paperId, Delegator delegator) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",EntityOperator.EQUALS,paperId));
			conds.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
			
			List<GenericValue> lgv = delegator.findList("StaffPaperDeclaration", 
					EntityCondition.makeCondition(conds),
					null,
					null,null,
					false);
			
			if (lgv == null || lgv.size() == 0) {
				return ServiceUtil.returnError("paperId, staffId " + paperId
						+ "," + staffId + " not exists");
			}
			for(GenericValue gv: lgv){
				gv.put("statusId", PaperDeclarationUtil.STATUS_DISABLED);
				delegator.store(gv);
				Debug.log(module  + "::deleteStaffPaperDeclaration, paperId = " + paperId + ", staffId = " + staffId);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}

		return retSucc;
	}
	
	public static Map<String, Object> createStaffPaperDeclarationc(
			String paperId, String staffId, Delegator delegator) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			List<GenericValue> lst = PaperDeclarationUtil.getStaffsOfPaper(
					paperId, staffId, delegator);
			if (lst != null && lst.size() > 0) {
				retSucc.put("message", "StaffPaperDeclaration(" + staffId + ","
						+ paperId + ") exists");
				Debug.log("message", "StaffPaperDeclaration(" + staffId + ","
						+ paperId + ") exists");
				return retSucc;
			}

			String id = staffId + paperId;
			GenericValue gv = delegator.makeValue("StaffPaperDeclaration");
			gv.put("staffPaperDeclarationId", id);
			gv.put("staffId", staffId);
			gv.put("paperId", paperId);
			gv.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);

			Debug.log(module + "::createStaffPaperDeclaration, staffId = "
					+ staffId + ", paperId = " + paperId + ", ID = " + id);

			delegator.create(gv);
			
			retSucc.put("staffPaperDeclaration", gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		retSucc.put("message","Successfully");
		
		return retSucc;
	}
	public static List<GenericValue> getStaffsOfPaper(String paperId,
			Delegator delegator) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			List<GenericValue> staffsOfPaper = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return staffsOfPaper;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	

	public static List<GenericValue> getStaffsOfPaper(String paperId,
			String staffId, Delegator delegator) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));

			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));

			List<GenericValue> staffsOfPaper = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return staffsOfPaper;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
