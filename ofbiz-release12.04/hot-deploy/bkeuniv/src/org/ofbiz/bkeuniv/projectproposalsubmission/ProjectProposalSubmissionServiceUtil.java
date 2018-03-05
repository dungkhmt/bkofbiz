package org.ofbiz.bkeuniv.projectproposalsubmission;

import java.util.List;

import javolution.util.FastList;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.ServiceUtil;


public class ProjectProposalSubmissionServiceUtil {
	public static String STATUS_CREATED = "CREATED";
	public static String STATUS_CANCELLED = "CANCELLED";
	public static String STATUS_APPROVED = "APPROVED";
	

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
			
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,STATUS_CANCELLED));
			
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
