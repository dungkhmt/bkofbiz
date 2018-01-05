package org.ofbiz.bkeuniv.statistics;

import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.List;

public class StatisticServices {
	public static String module = StatisticServices.class.getName();
	
	public static Map<String, Object> updateCountResearchProposalSubmission(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("ResearchProjectProposal", 
					null, null, null, null, false);
			long count = list.size();
			GenericValue gv = delegator.makeValue("Statistic");
			String statisticId = delegator.getNextSeqId("Statistic");
			gv.put("statisticId", statisticId);
			gv.put("countResearchProposalSubmission", count);
			delegator.create(gv);
			Debug.log(module + "::updateCountResearchProposalSubmission, list.sz = " + list.size());
			retSucc.put("msg", "::updateCountResearchProposalSubmission --> list.sz = " + list.size());
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}
