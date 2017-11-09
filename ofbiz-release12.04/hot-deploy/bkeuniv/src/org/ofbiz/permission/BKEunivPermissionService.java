package src.org.ofbiz.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;

public class BKEunivPermissionService {
	public static String module = BKEunivPermissionService.class.getName();
	
	public static List<GenericValue> sort(List<GenericValue> L){
		GenericValue[] a = new GenericValue[L.size()];
		for(int i = 0; i < a.length; i++) a[i] = L.get(i);
		for(int i = 0; i < a.length - 1; i++)
			for(int j = i+1; j < a.length; j++)
				if((long)a[i].get("index") > (long)a[j].get("index")){
					GenericValue tmp = a[i]; a[i] = a[j]; a[j] = tmp;
				}
		List<GenericValue> sL = new ArrayList<GenericValue>();
		for(int i = 0; i < a.length; i++) sL.add(a[i]);
		return sL;
	}
	public static Map<String, Object> getPermissionFunctions(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String userLoginId = (String)userLogin.get("userLoginId");
		
		EntityCondition cond = EntityCondition.makeCondition("userLoginId", EntityOperator.EQUALS, userLoginId);
		
		Debug.log(module + "::getPermissionFunctions, userLoginId = " + userLoginId);
		
		try{
			List<GenericValue> tmp_functions = new ArrayList<GenericValue>();
			List<GenericValue> userLoginSecurityGroup = delegator.findList("UserLoginSecurityGroup", EntityCondition.makeCondition(cond), 
					null, null, null, false);
			Debug.log(module + "::getPermissionFunctions, groups.sz = " + userLoginSecurityGroup.size());
			
			HashSet<String> set_function_id = new HashSet<String>();
			
			for(GenericValue gv: userLoginSecurityGroup){
				String securityGroupId = (String)gv.get("groupId");
				List<GenericValue> funcs = delegator.findList("GroupFunction", 
						EntityCondition.makeCondition(EntityCondition.makeCondition("securityGroupId", EntityOperator.EQUALS, securityGroupId)), 
						null, null, null, false);
				for(GenericValue f: funcs){
					String functionId = (String)f.get("functionId");
					set_function_id.add(functionId);
					//GenericValue aFunction = delegator.findOne("Function", false, UtilMisc.toMap("functionId",functionId));
					//tmp_functions.add(aFunction);
				}
			}
			
			for(String functionId: set_function_id){
				GenericValue aFunction = delegator.findOne("Function", false, UtilMisc.toMap("functionId",functionId));
				tmp_functions.add(aFunction);
			}
			
			Debug.log(module + "::getPermissionFunctions, functions.sz = " + tmp_functions.size());
			
			for(GenericValue f: tmp_functions){
				Debug.log(module + "::getPermissionFunctions, get functions " + f.get("functionId"));
			}
			
			List<GenericValue> parent_functions = new ArrayList<GenericValue>();
			HashMap<String, GenericValue> mId2Function = new HashMap<String, GenericValue>();
			HashMap<GenericValue, List<GenericValue>> mFunction2ChildrenFunctions = new HashMap<GenericValue, List<GenericValue>>();
			
			for(GenericValue f: tmp_functions){
				String parentFunctionId = (String)f.get("parentFunctionId");
				String functionId = (String)f.get("functionId");
				if(parentFunctionId.equals("NULL")){// collect parent functions
					parent_functions.add(f);
					mId2Function.put(functionId, f);
					mFunction2ChildrenFunctions.put(f, new ArrayList<GenericValue>());
				}else{
					
				}
			}
			parent_functions = sort(parent_functions);
			
			for(GenericValue f: tmp_functions){
				String parentFunctionId = (String)f.get("parentFunctionId");
				if(!parentFunctionId.equals("NULL")){
					GenericValue pf = mId2Function.get(parentFunctionId);
					mFunction2ChildrenFunctions.get(pf).add(f);
				}
			}
			List<Map<String, Object>> functions = new ArrayList<Map<String, Object>>();
			//List<GenericValue> functions = new ArrayList<GenericValue>();
			
			for(GenericValue f: parent_functions){
				Map<String, Object> o = new HashMap<String, Object>();
				o.put("function", f);
				o.put("children", sort(mFunction2ChildrenFunctions.get(f)));
				functions.add(o);
			}
			
			retSucc.put("permissionFunctions", functions);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
}
