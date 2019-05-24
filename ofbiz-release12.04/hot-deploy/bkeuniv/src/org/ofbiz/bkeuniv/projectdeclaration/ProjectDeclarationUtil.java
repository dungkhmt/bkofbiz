package org.ofbiz.bkeuniv.projectdeclaration;

import java.util.List;

import javolution.util.FastList;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

public class ProjectDeclarationUtil {
	public static void removeProjectCV(Delegator delegator, String projectProposalMemberId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("projectProposalMemberId", EntityOperator.EQUALS,projectProposalMemberId));
			List<GenericValue> lst = delegator.findList("CVProject", 
					EntityCondition.makeCondition(conds), null,null,null, false);
			for(GenericValue p: lst){
				delegator.removeValue(p);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void addUpdateProjectCV(Delegator delegator, String projectProposalMemberId, long sequenceInCVProject){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("projectProposalMemberId", EntityOperator.EQUALS,projectProposalMemberId));
			List<GenericValue> lst = delegator.findList("CVProject", 
					EntityCondition.makeCondition(conds), null,null,null, false);
			if(lst == null || lst.size() == 0){
				String cvProjectId = delegator.getNextSeqId("CVProject");
				GenericValue cvProject = delegator.makeValue("CVProject");
				cvProject.put("cvProjectId", cvProjectId);
				cvProject.put("projectProposalMemberId", projectProposalMemberId);
				cvProject.put("sequenceInCVProject", sequenceInCVProject);
				delegator.create(cvProject);
			}else{
				for(GenericValue cvProject: lst){
					cvProject.put("sequenceInCVProject", sequenceInCVProject);
					delegator.store(cvProject);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
