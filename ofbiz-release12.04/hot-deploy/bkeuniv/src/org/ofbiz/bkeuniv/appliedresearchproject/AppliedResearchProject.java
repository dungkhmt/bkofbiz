package src.org.ofbiz.bkeuniv.appliedresearchproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class AppliedResearchProject {
	public final static String module = AppliedResearchProject.class.getName();
	
	public static Map<String, Object> getAppliedProjects(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		if(staffId == null){
			Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
			staffId = (String)userLogin.get("userLoginId");
		}
		try{
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			conditions.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
				List<GenericValue> list = delegator.findList("AppliedResearchProject", 
						EntityCondition.makeCondition(conditions), 
						null, null, 
						null, false);
				
				Map<String, Object> result = ServiceUtil.returnSuccess();
				result.put("projects", list);
				
				return result;
		}
		catch(Exception e){
			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}

	public static Map<String, Object> getProject(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		String[] keys = {"appliedResearchProjectId","staffId","name","description","period"};
		String[] search = {"name"};
		
		try{
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys){
				Object el = context.get(key);
				if(!(el == null||el == (""))){
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if(index == -1){
						condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
						
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);	
					}
					conditions.add(condition);
				}
			}
			
				System.out.println(conditions.size());
				List<GenericValue> list = delegator.findList("AppliedResearchProject", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
				Map<String, Object> result = ServiceUtil.returnSuccess();
				System.out.println(list.size());
				List<Map<String, Object>> listProject = FastList.newInstance();
				for(GenericValue el: list){
					Map<String, Object> mapProject = FastMap.newInstance();
					mapProject.put("appliedResearchProjectId", el.getString("appliedResearchProjectId"));
					mapProject.put("staffId", el.getString("staffId"));
					mapProject.put("name", el.getString("name"));
					mapProject.put("description", el.getString("description"));
					mapProject.put("period", el.getString("period"));
					listProject.add(mapProject);
				}
				System.out.println(listProject.size());
				result.put("project", listProject);
				return result;
		}
		catch(Exception e){
			System.out.print("Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	public static Map<String, Object> createProject(DispatchContext ctx, Map<String,? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String staffId = (String) context.get("staffId");
		String name = (String) context.get("name");
		String description = (String) context.get("description");
		String period = (String) context.get("period");
		
		GenericValue gv = delegator.makeValue("AppliedResearchProject");
		gv.put("appliedResearchProjectId", delegator.getNextSeqId("AppliedResearchProject"));
		try{
			gv.put("staffId", staffId);
			gv.put("name", name);
			gv.put("description", description);
			gv.put("period", period);
			
			delegator.create(gv);
		}
		catch(Exception ex){
			System.out.println("aaa");
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		retSucc.put("project", gv);
		retSucc.put("message", "Create a new row");
		return retSucc;
	}
	
	public static Map<String, Object> deleteProject(DispatchContext ctx, Map<String, ? extends Object> context){
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String appliedResearchProjectId = (String) context.get("appliedResearchProjectId");
		try{
			GenericValue gv = delegator.findOne("AppliedResearchProject", UtilMisc.toMap("appliedResearchProjectId", appliedResearchProjectId), false);
			if(gv != null){
				delegator.removeValue(gv);
				retSucc.put("result", "deleted record with id: " +appliedResearchProjectId);
			} else {
				retSucc.put("result", "not found record with id: "+appliedResearchProjectId);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> updateProject(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		String name = (String) context.get("name");
		String description = (String) context.get("description");
		String period = (String) context.get("period");
		String appliedResearchProjectId = (String) context.get("appliedResearchProjectId");
		
		try{
			GenericValue gv = delegator.findOne("AppliedResearchProject", false, UtilMisc.toMap("appliedResearchProjectId", appliedResearchProjectId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("name", name);
				gv.put("description", description);
				gv.put("period", period);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("appliedResearchProjectId", appliedResearchProjectId);
				rs.put("staffId", staffId);
				rs.put("name", name);
				rs.put("description", description);
				rs.put("period", period);
				
				retSucc.put("project", rs);
				
				retSucc.put("message", "updated record with id: "+appliedResearchProjectId);
			}
			else {
				retSucc.put("message", "not found record with id: " +appliedResearchProjectId);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}
