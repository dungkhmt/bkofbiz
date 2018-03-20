package org.ofbiz.bkeuniv.statistics;

import java.util.Map;
import java.util.Set;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

public class StatisticServices {
	public static String module = StatisticServices.class.getName();
	
	public static Map<String, Object> getPapersStatistic(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> allYears = delegator.findList("AcademicYear", null, null, null, null, false);
			
			List<GenericValue> papers = delegator.findList("PaperDeclaration", 
					null, null, null, null, false);
			Debug.log(module + "::getPapersStatistic, total = " + papers.size());
			Set<String> years = FastSet.newInstance();
			//for(GenericValue p: papers){
			//	if(p.get("academicYearId") != null)
			//		years.add((String)p.get("academicYearId"));
			//}
			for(GenericValue y: allYears){
				years.add((String)y.get("academicYearId"));
			}
			String[] years_list = new String[years.size()];
			int idx = -1;
			for(String y: years){
				idx++;
				years_list[idx] = y;
			}
			for(int i = 0; i < years_list.length-1; i++)
				for(int j = i+1; j < years_list.length; j++){
					if(years_list[i].compareTo(years_list[j]) > 0){
						String tmp = years_list[i];
						years_list[i] = years_list[j];
						years_list[j] = tmp;
					}
				}
			Map<String, Integer> m = FastMap.newInstance();
			for(int i = 0; i<years_list.length; i++)
				m.put(years_list[i], i);
			int[] count = new int[years_list.length];
			for(int i = 0; i < count.length; i++) count[i] = 0;
			for(GenericValue p: papers)if(p.get("academicYearId") != null){
				String y = p.getString("academicYearId");
				int ix = m.get(y);
				count[ix]++;
			}
			List<String> Y = FastList.newInstance();
			List<Integer> C = FastList.newInstance();
			for(int i = 0; i < years_list.length; i++){
				Y.add(years_list[i]);
				C.add(count[i]);
				Debug.log(module + "::getPapersStatistic, year " + years_list[i] + " count " + count[i]);
			}
			
			retSucc.put("years", Y);
			retSucc.put("count", C);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
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
