package org.ofbiz.bkeuniv.projectproposalsubmission;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.ServiceUtil;


public class ProjectProposalSubmissionServiceUtil {
	
	public static final String module = ProjectProposalSubmissionServiceUtil.class.getName();
	public static String STATUS_PROJECT_CALL_CREATED = "CREATED";
	public static String STATUS_PROJECT_CALL_OPEN = "OPEN";
	public static String STATUS_PROJECT_CALL_CLOSED = "CLOSED";
	public static String STATUS_PROJECT_CALL_CANCELLED = "CANCELLED";
	public static String STATUS_PROJECT_CALL_OPEN_REVISED = "OPEN_REVISED";
	public static String STATUS_PROJECT_CALL_CLOSED_REVISED = "CLOSED_REVISED";
	
	public static String STATUS_PROJECT_APPROVED = "APPROVED";
	public static String STATUS_PROJECT_ACCEPT_REVISE = "ACCEPT_REVISE";
	public static String STATUS_PROJECT_REJECTED = "REJECTED";
	public static String STATUS_PROJECT_CANCELLED = "CANCELLED";
	public static String STATUS_PROJECT_ASSIGNED_REVIEWER = "ASSIGNED_REVIEWER";
	public static String STATUS_PROJECT_SUBMITTED = "SUBMITTED";
	public static String STATUS_PROJECT_CREATED = "CREATED";
	
	public static String STATUS_PROJECT_EVALUATION_CONFIRM = "CONFIRM";
	
	
	public static GenericValue createAProjectProposalSubmission(Delegator delegator, String projectProposalName,
			String facultyId, String projectCallId, String staffId){
		try{
			String partyId = delegator.getNextSeqId("Party");
			
			Map<String, Object> info = UtilMisc.toMap("partyId", partyId);
			GenericValue pty = delegator.makeValue("Party",info);
			//pty.put("partyId", partyId);
			delegator.create(pty);
			
			GenericValue pps = delegator.makeValue("ResearchProjectProposal");
			String researchProjectProposalId = delegator.getNextSeqId("ResearchProjectProposal");
			pps.put("researchProjectProposalId", researchProjectProposalId);
			pps.put("researchProjectProposalName", projectProposalName);
			pps.put("createStaffId", staffId);
			pps.put("partyId", partyId);
			pps.put("statusId", ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CREATED);
			
			pps.put("projectCallId", projectCallId);
			pps.put("facultyId", facultyId);
			
			
			delegator.create(pps);
			
			return pps;

		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<GenericValue> getListFilteredProjectProposals(Delegator delegator,
			String projectCallId, String facultyId, String projectProposalStatusId){
		Debug.log(module + "::getListFilteredProjectProposals, facultyId = " + facultyId + 
				", projectCallId = " + projectCallId + ", projectProposalStatusId = " + projectProposalStatusId);
		try{
			List<EntityCondition> conds = FastList.newInstance();
			if(facultyId != null && !facultyId.equals("all")){
				conds.add(EntityCondition.makeCondition("facultyId",EntityOperator.EQUALS,facultyId));
			}
			if(projectCallId != null && !projectCallId.equals("all")){
				conds.add(EntityCondition.makeCondition("projectCallId",EntityOperator.EQUALS,projectCallId));
			}
			if(projectProposalStatusId != null && !projectProposalStatusId.equals("all")){
				conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,projectProposalStatusId));
			}
			
			List<GenericValue> prj = delegator.findList("ResearchProjectProposalView", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			return prj;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	
	}

	public static List<GenericValue> getListProjectProposals(Delegator delegator,
			String juryId){
		try{
			String projectCallId = null;
			String facultyId = null;
			
			// get projectCall of the current jury if any
				GenericValue J = delegator.findOne("Jury", UtilMisc.toMap("juryId", juryId), false);
				if(J != null){
					projectCallId = (String)J.get("projectCallId");
					facultyId = (String)J.get("facultyId");
				}
			
			
			
			List<EntityCondition> conds = FastList.newInstance();
			if(projectCallId != null)
				conds.add(EntityCondition.makeCondition("projectCallId", EntityOperator.EQUALS,projectCallId));
			if(facultyId != null)
				conds.add(EntityCondition.makeCondition("facultyId", EntityOperator.EQUALS,facultyId));
			
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,STATUS_PROJECT_CANCELLED));
			
			List<GenericValue> prj = delegator.findList("ResearchProjectProposalView", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			
			
			return prj;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}

	}
	
	public static List<GenericValue> getListProjectProposalsAssignedForReview(Delegator delegator,
			String staffId, String juryId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			if(juryId != null)
				conds.add(EntityCondition.makeCondition("juryId", EntityOperator.EQUALS,juryId));
			
			if(staffId != null)
				conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS,staffId));
			
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS,"ASSIGNED_REVIEWER"));
			
			List<GenericValue> list = delegator.findList("ReviewerResearchProposalView", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			
			
			return list;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}

	}
}
