package src.org.ofbiz.bkeuniv.paperdeclaration;

import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

import java.util.List;

public class PaperDeclarationService {
	public static String module = PaperDeclarationService.class.getName();
	
	public static Map<String, Object> getPapersOfStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		
		Debug.log(module + "::getPapersOfStaff, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> papers = delegator.findList("PapersStaffView", 
					EntityCondition.makeCondition(EntityCondition.makeCondition("authorStaffId", EntityOperator.EQUALS, staffId)), 
					null, null, null, false);
			for(GenericValue gv: papers){
				Debug.log(module + "::getPapersOfStaff, paper " + gv.get("paperName"));
			}
		
			retSucc.put("papers", papers);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	public static Map<String, Object> updatePaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		String paperId = (String)context.get("paperId");
		String paperName = (String)context.get("paperName");
		
		Debug.log(module + "::updatePaper, authorStaffId = " + staffId + ", paperId = " + paperId);
		Delegator delegator = ctx.getDelegator();
		
		try{
			List<GenericValue> papers = delegator.findList("PaperDeclaration", 
					EntityCondition.makeCondition(EntityCondition.makeCondition("paperId", EntityOperator.EQUALS, paperId)), 
					null, null, null, false);
			for(GenericValue gv: papers){
				Debug.log(module + "::updatePaper, paper " + gv.get("paperName") + ", new Name = " + paperName);
			}
			GenericValue p = papers.get(0);
			p.put("paperName", paperName);
			
			delegator.store(p);
			
			retSucc.put("papers", papers);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
}
