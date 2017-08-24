package src.org.ofbiz.bkeuniv.paperdeclaration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

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
	
	public static Map<String, Object> getPaperCategory(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		System.out.println(module + "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " + u2);
		Debug.log(module + "::getEducationProgress, Debug.log u1 = " + u1 + ", u2 = " + u2);
		
		
		String[] keys = {"paperCategoryId", "paperCategoryName", "paperCategoryCode", "journalIndexId"};
		String[] search = {"paperCategoryName"};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if( index == -1) {
						condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}
			
			List<GenericValue> list = delegator.findList("PaperCategory", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listMap = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> map = FastMap.newInstance();
				for(String key: keys) {
					map.put(key, el.getString(key));
					
				}
				listMap.add(map);
			}
			result.put("result", listMap);
			result.put("count", String.valueOf(listMap.size()));
			return result;
		
		} catch (Exception e) {
			System.out.print("Paper category Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
}
