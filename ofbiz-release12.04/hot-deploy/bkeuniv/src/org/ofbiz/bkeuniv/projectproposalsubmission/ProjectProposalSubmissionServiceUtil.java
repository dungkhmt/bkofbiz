package org.ofbiz.bkeuniv.projectproposalsubmission;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.config.ConfigParams;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.utils.BKEunivUtils;

public class ProjectProposalSubmissionServiceUtil {

	public static final String module = ProjectProposalSubmissionServiceUtil.class
			.getName();
	public static String STATUS_PROJECT_CALL_CREATED = "CREATED";
	public static String STATUS_PROJECT_CALL_OPEN = "OPEN";
	public static String STATUS_PROJECT_CALL_CLOSED = "CLOSED";
	public static String STATUS_PROJECT_CALL_CANCELLED = "CANCELLED";
	public static String STATUS_PROJECT_CALL_OPEN_REVISED = "OPEN_REVISED";
	public static String STATUS_PROJECT_CALL_CLOSED_REVISED = "CLOSED_REVISED";

	public static String STATUS_PROJECT_APPROVED = "APPROVED";
	public static String STATUS_PROJECT_APPROVED_UNIVERSITY = "APPROVED_UNIVERSITY";
	public static String STATUS_PROJECT_ACCEPT_REVISE = "ACCEPT_REVISE";
	public static String STATUS_PROJECT_REJECTED = "REJECTED";
	public static String STATUS_PROJECT_CANCELLED = "CANCELLED";
	public static String STATUS_PROJECT_ASSIGNED_REVIEWER = "ASSIGNED_REVIEWER";
	public static String STATUS_PROJECT_SUBMITTED = "SUBMITTED";
	public static String STATUS_PROJECT_CREATED = "CREATED";
	public static String STATUS_PROJECT_UNDER_REVIEW = "UNDER_REVIEW";

	public static String STATUS_PROJECT_EVALUATION_CONFIRM = "CONFIRM";

	//public static String dataFolder = "." + File.separator + "euniv-deploy";
	//public static String dataFolder = "C:/DungPQ/projects/bkofbiz-github/euniv-deploy";
	
	public static String addPading(String idx, int len) {
		String s = idx + "";
		while (s.length() < len)
			s = "0" + s;
		return s;
	}

	public static String getExtension(String fn) {
		Debug.log(module + "::getExtension, fn = " + fn);
		String[] s = fn.split("\\.");
		Debug.log(module + "::getExtension, fn = " + fn + ", s.length = "
				+ s.length);
		return s[s.length - 1];
	}

	public static String establishFullFilename(String staffId, String name) {
		String path = ConfigParams.dataFolder + File.separator + staffId + File.separator
				+ "projects";
		Debug.log(module + "::establishFullFilename, path = " + path);
		String fullname = path + File.separator + name;

		File file = new File(path);

		if (!file.exists()) {

			file.mkdirs();
			Debug.log(module
					+ "::establishFullFilename, folder NOT exist --> Create folder\n\t");
			// If you require it to make the entire directory path including
			// parents,
			// use directory.mkdirs(); here instead.
		}

		return fullname;
	}

	public static Map<String, Object> cloneProjectProposalWithNewStatus(
			Delegator delegator, String researchProjectProposalId,
			String newStatusId) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		// String researchProjectProposalId =
		// context.get("researchProjectProposalId") + "";
		// String newStatusId = context.get("newStatusId") + "";
		// Delegator delegator = ctx.getDelegator();
		// LocalDispatcher dispatcher = ctx.getDispatcher();
		// Map<String, Object> userLogin = (Map<String, Object>)
		// context.get("userLogin");
		Debug.log(module
				+ "::cloneProjectProposalWithNewStatus, researchProjectProposalId = "
				+ researchProjectProposalId + ", newStatusId = " + newStatusId);

		try {
			GenericValue p = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);

			// create a ResearchProjectProposal
			String newResearchProjectProposalId = delegator
					.getNextSeqId("ResearchProjectProposal");
			GenericValue np = delegator.makeValue("ResearchProjectProposal");
			np.put("researchProjectProposalId", newResearchProjectProposalId);
			np.put("statusId", newStatusId);

			np.put("researchProjectProposalCode",
					(String) p.get("researchProjectProposalCode"));
			np.put("partyId", (String) p.get("partyId"));
			np.put("parentResearchProjectProposalId", researchProjectProposalId);
			
			Debug.log(module + "::::cloneProjectProposalWithNewStatus, set parentProposal " + researchProjectProposalId);
			
			np.put("createStaffId", (String) p.get("createStaffId"));
			np.put("projectCallId", (String) p.get("projectCallId"));
			np.put("projectCategoryId", (String) p.get("projectCategoryId"));
			np.put("researchProjectProposalName",
					(String) p.get("researchProjectProposalName"));
			np.put("totalBudget", (Long) p.get("totalBudget"));
			np.put("approvedByStaffId", (String) p.get("approvedByStaffId"));
			np.put("facultyId", (String) p.get("facultyId"));
			np.put("startDate", (java.sql.Date) p.get("startDate"));
			np.put("endDate", (java.sql.Date) p.get("endDate"));
			np.put("deliverable", (String) p.get("deliverable"));
			np.put("materialBudget", (Long) p.get("materialBudget"));
			np.put("evaluationOpenFlag", (String) p.get("evaluationOpenFlag"));

			// clone uploaded file
			String staffId = (String) p.get("createStaffId");
			String filenameDB = (String) p.get("sourceFileUpload");
			if (filenameDB != null && !filenameDB.equals("")) {
				String fullFileName = establishFullFilename(staffId, filenameDB);

				String ext = getExtension(filenameDB);
				java.util.Date currentDate = new java.util.Date();
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

				String newFilenameDB = sCurrentDate + "." + ext;
				String newFullFileName = establishFullFilename(staffId,
						newFilenameDB);

				Files.copy((new File(fullFileName)).toPath(), (new File(
						newFullFileName)).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				np.put("sourceFileUpload", newFilenameDB);
			}

			delegator.create(np);

			// clone content of project proposal
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));

			List<GenericValue> lstContents = delegator.findList(
					"ResearchProposalContentItem",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue c : lstContents) {
				GenericValue nc = delegator
						.makeValue("ResearchProposalContentItem");
				String contentItemSeq = delegator
						.getNextSeqId("ResearchProposalContentItem");

				nc.put("researchProjectProposalId",
						newResearchProjectProposalId);
				nc.put("contentItemSeq", contentItemSeq);
				nc.put("staffId", (String) c.get("staffId"));
				nc.put("content", (String) c.get("content"));
				nc.put("workingDays", (Long) c.getLong("workingDays"));
				nc.put("budget", (BigDecimal) c.getBigDecimal("budget"));

				delegator.create(nc);
			}

			// clone members of project proposal
			List<GenericValue> lstMembers = delegator.findList(
					"ProjectProposalMember",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue m : lstMembers) {
				GenericValue nm = delegator.makeValue("ProjectProposalMember");
				String projectProposalMemberId = delegator
						.getNextSeqId("ProjectProposalMember");

				nm.put("projectProposalMemberId", projectProposalMemberId);
				nm.put("researchProjectProposalId",
						newResearchProjectProposalId);
				nm.put("staffId", (String) m.get("staffId"));
				nm.put("roleTypeId", (String) m.get("roleTypeId"));
				nm.put("fromDate", (java.sql.Date) m.get("fromDate"));
				nm.put("thruDate", (java.sql.Date) m.get("thruDate"));

				delegator.create(nm);
			}

			// clone products of project proposal
			List<GenericValue> lstProducts = delegator.findList(
					"ResearchProposalProduct",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue pr : lstProducts) {
				String researchProductId = delegator
						.getNextSeqId("ResearchProposalProduct");
				GenericValue npr = delegator
						.makeValue("ResearchProposalProduct");

				npr.put("researchProductId", researchProductId);
				npr.put("researchProjectProposalId",
						newResearchProjectProposalId);
				npr.put("researchProductTypeId",
						(String) pr.get("researchProductTypeId"));
				npr.put("researchProductName",
						(String) pr.get("researchProductName"));
				npr.put("quantity", (Long) pr.get("quantity"));

				delegator.create(npr);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static GenericValue createAProjectProposalSubmission(
			Delegator delegator, String projectProposalName, String facultyId,
			String projectCallId, String staffId) {
		try {
			String partyId = delegator.getNextSeqId("Party");

			Map<String, Object> info = UtilMisc.toMap("partyId", partyId);
			GenericValue pty = delegator.makeValue("Party", info);
			// pty.put("partyId", partyId);
			delegator.create(pty);

			GenericValue pps = delegator.makeValue("ResearchProjectProposal");
			String researchProjectProposalId = delegator
					.getNextSeqId("ResearchProjectProposal");
			pps.put("researchProjectProposalId", researchProjectProposalId);
			pps.put("researchProjectProposalName", projectProposalName);
			pps.put("createStaffId", staffId);
			pps.put("partyId", partyId);
			pps.put("statusId",
					ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CREATED);

			pps.put("projectCallId", projectCallId);
			pps.put("facultyId", facultyId);

			delegator.create(pps);

			return pps;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListFilteredProjectProposals(
			Delegator delegator, String projectCallId, String facultyId,
			String projectProposalStatusId) {
		Debug.log(module + "::getListFilteredProjectProposals, facultyId = "
				+ facultyId + ", projectCallId = " + projectCallId
				+ ", projectProposalStatusId = " + projectProposalStatusId);
		try {
			List<EntityCondition> conds = FastList.newInstance();
			if (facultyId != null && !facultyId.equals("all")) {
				conds.add(EntityCondition.makeCondition("facultyId",
						EntityOperator.EQUALS, facultyId));
			}
			if (projectCallId != null && !projectCallId.equals("all")) {
				conds.add(EntityCondition.makeCondition("projectCallId",
						EntityOperator.EQUALS, projectCallId));
			}
			if (projectProposalStatusId != null
					&& !projectProposalStatusId.equals("all")) {
				conds.add(EntityCondition.makeCondition("statusId",
						EntityOperator.EQUALS, projectProposalStatusId));
			}

			List<GenericValue> prj = delegator.findList(
					"ResearchProjectProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return prj;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static List<GenericValue> getListProjectProposals(
			Delegator delegator, String juryId) {
		try {
			String projectCallId = null;
			String facultyId = null;

			// get projectCall of the current jury if any
			GenericValue J = delegator.findOne("Jury",
					UtilMisc.toMap("juryId", juryId), false);
			if (J != null) {
				projectCallId = (String) J.get("projectCallId");
				facultyId = (String) J.get("facultyId");
			}

			List<EntityCondition> conds = FastList.newInstance();
			if (projectCallId != null)
				conds.add(EntityCondition.makeCondition("projectCallId",
						EntityOperator.EQUALS, projectCallId));
			if (facultyId != null)
				conds.add(EntityCondition.makeCondition("facultyId",
						EntityOperator.EQUALS, facultyId));

			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.NOT_EQUAL, STATUS_PROJECT_CANCELLED));

			List<GenericValue> prj = delegator.findList(
					"ResearchProjectProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			return prj;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static List<GenericValue> getListProjectProposalsAssignedForReview(
			Delegator delegator, String staffId, String juryId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			if (juryId != null)
				conds.add(EntityCondition.makeCondition("juryId",
						EntityOperator.EQUALS, juryId));

			if (staffId != null)
				conds.add(EntityCondition.makeCondition("staffId",
						EntityOperator.EQUALS, staffId));

			// conds.add(EntityCondition.makeCondition("statusId",
			// EntityOperator.EQUALS,"ASSIGNED_REVIEWER"));

			List<GenericValue> list = delegator.findList(
					"ReviewerResearchProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}
}
