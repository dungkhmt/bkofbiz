package org.ofbiz.bkeuniv.cv;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class CVProject {
	public static String module = CVProject.class.getName();

	public static Map<String, Object> addCVProject(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String staffId = (String) context.get("staffId");
		List<String> lst_projectProposalMemberId = (List<String>) context
				.get("projectProposalMemberId[]");
		String projectProposalMemberId = null;
		if (lst_projectProposalMemberId != null
				&& lst_projectProposalMemberId.size() > 0)
			projectProposalMemberId = lst_projectProposalMemberId.get(0);
		String s_sequenceInCVProject = (String) context
				.get("sequenceInCVProject");
		if (staffId == null) {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			staffId = userLogin.getString("userLoginId");
		}

		try {
			long sequenceInCVProject = Long.valueOf(s_sequenceInCVProject);
			GenericValue p = delegator.makeValue("CVProject");
			String cvProjectId = delegator.getNextSeqId("cvProjectId");
			p.put("cvProjectId", cvProjectId);
			if (projectProposalMemberId != null)
				p.put("projectProposalMemberId", projectProposalMemberId);
			p.put("sequenceInCVProject", sequenceInCVProject);

			delegator.create(p);

			Map<String, Object> projects = FastMap.newInstance();
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("projectProposalMemberId",
					EntityOperator.EQUALS, projectProposalMemberId));
			List<GenericValue> lst = delegator.findList(
					"ProjectProposalMemberView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			String researchProjectProposalName = null;
			if (lst != null && lst.size() > 0) {
				GenericValue pm = lst.get(0);
				researchProjectProposalName = pm
						.getString("researchProjectProposalName");
				projects.put("researchProjectProposalName",
						researchProjectProposalName);
			}

			projects.put("projectProposalMemberId", projectProposalMemberId);
			projects.put("sequenceInCVProject", sequenceInCVProject);

			retSucc.put("projects", projects);
			retSucc.put("message", "Them moi thanh cong");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retSucc;
	}

	public static Map<String, Object> updateCVProject(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String staffId = (String) context.get("staffId");
		String cvProjectId = (String) context.get("cvProjectId");
		List<String> lst_projectProposalMemberId = (List<String>) context
				.get("projectProposalMemberId[]");
		String projectProposalMemberId = null;
		if (lst_projectProposalMemberId != null
				&& lst_projectProposalMemberId.size() > 0)
			projectProposalMemberId = lst_projectProposalMemberId.get(0);
		String s_sequenceInCVProject = (String) context
				.get("sequenceInCVProject");
		if (staffId == null) {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			staffId = userLogin.getString("userLoginId");
		}

		try {
			long sequenceInCVProject = Long.valueOf(s_sequenceInCVProject);
			GenericValue p = delegator.findOne("CVProject",
					UtilMisc.toMap("cvProjectId", cvProjectId), false);
			if (p != null) {

				if (projectProposalMemberId != null)
					p.put("projectProposalMemberId", projectProposalMemberId);
				p.put("sequenceInCVProject", sequenceInCVProject);

				delegator.store(p);

				Map<String, Object> projects = FastMap.newInstance();
				List<EntityCondition> conds = FastList.newInstance();
				conds.add(EntityCondition.makeCondition(
						"projectProposalMemberId", EntityOperator.EQUALS,
						projectProposalMemberId));
				List<GenericValue> lst = delegator.findList(
						"ProjectProposalMemberView",
						EntityCondition.makeCondition(conds), null, null, null,
						false);
				String researchProjectProposalName = null;
				if (lst != null && lst.size() > 0) {
					GenericValue pm = lst.get(0);
					researchProjectProposalName = pm
							.getString("researchProjectProposalName");
					projects.put("researchProjectProposalName",
							researchProjectProposalName);
				}

				projects.put("projectProposalMemberId", projectProposalMemberId);
				projects.put("sequenceInCVProject", sequenceInCVProject);

				retSucc.put("projects", projects);
				retSucc.put("message", "Cap nhat thanh cong");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retSucc;
	}
	public static Map<String, Object> deleteCVProject(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String staffId = (String) context.get("staffId");
		String cvProjectId = (String) context.get("cvProjectId");
		if (staffId == null) {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			staffId = userLogin.getString("userLoginId");
		}

		try {
			GenericValue p = delegator.findOne("CVProject",
					UtilMisc.toMap("cvProjectId", cvProjectId), false);
			if (p != null) {
				delegator.removeValue(p);
				retSucc.put("message", "Xoa thanh cong");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retSucc;
	}

	public static Map<String, Object> getCVProjects(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String staffId = (String) context.get("staffId");
		if (staffId == null) {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			staffId = userLogin.getString("userLoginId");
		}
		Debug.log(module + "::getCVProjects, staffId = " + staffId);

		try {
			List<String> orderBy = FastList.newInstance();
			orderBy.add("sequenceInCVProject");
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			List<GenericValue> lst = delegator.findList("CVProjectView",
					EntityCondition.makeCondition(conds), null, orderBy, null,
					false);

			retSucc.put("projects", lst);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}
