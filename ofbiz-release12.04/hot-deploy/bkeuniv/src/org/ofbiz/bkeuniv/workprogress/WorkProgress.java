package src.org.ofbiz.bkeuniv.workprogress;

import java.sql.Date;
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
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class WorkProgress {
	public static Map<String, Object> getWorkProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String[] keys = {"workProgressId", "staffId", "period", "position", "specialization", "institution"};
		
		try {
			
			EntityCondition entity = null;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			Map<String, Object> fields = new HashMap<String, Object>();
			//List<GenericValue> list = delegator.findList("EducationProgress", entity, null, null, findOptions, true);	
			List<GenericValue> list = delegator.findByAnd("WorkProgress", fields);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listWorkProgress = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> mapWorkProgress = FastMap.newInstance();
				mapWorkProgress.put("workProgressId", el.getString("workProgressId"));
				mapWorkProgress.put("staffId", el.getString("staffId"));
				mapWorkProgress.put("period", el.getString("period"));
				mapWorkProgress.put("position", el.getString("position"));
				mapWorkProgress.put("specialization", el.getString("specialization"));
				mapWorkProgress.put("institution", el.getString("institution"));
				listWorkProgress.add(mapWorkProgress);
			}
			result.put("workProgress", listWorkProgress);
			return result;		
		} catch (Exception e) {
			System.out.print("Education Progress Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createWorkProgress(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffId = (String) context.get("staffId");
		String period = (String) context.get("period");
		String position = (String) context.get("position");
		String specialization = (String) context.get("specialization");
		String institution = (String) context.get("institution");
		System.out.println(staffId+ ""+position);
		GenericValue gv = delegator.makeValue("WorkProgress");

		gv.put("workProgressId", delegator.getNextSeqId("WorkProgress"));

		try {
			gv.put("staffId", staffId);
			gv.put("period", period);
			gv.put("position", position);
			gv.put("specialization", specialization);
			gv.put("institution", institution);

			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		Map<String, Object> mapWorkProgress = FastMap.newInstance();
		mapWorkProgress.put("workProgressId", gv.getString("workProgressId"));
		mapWorkProgress.put("staffId", staffId);
		mapWorkProgress.put("period", period);
		mapWorkProgress.put("position", position);
		mapWorkProgress.put("specialization",specialization);
		mapWorkProgress.put("institution", institution);
		
		
		retSucc.put("workProgress", mapWorkProgress);

		return retSucc;
	}
	
	public static Map<String, Object> updateWorkProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		String period = (String) context.get("period");
		String position = (String) context.get("position");
		String specialization = (String) context.get("specialization");
		String institution = (String) context.get("institution");
		String workProgressId = (String) context.get("workProgressId");
		System.out.println(institution);
		try{
			GenericValue gv = delegator.findOne("WorkProgress", false, UtilMisc.toMap("workProgressId",workProgressId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("period", period);
				gv.put("position", position);
				gv.put("specialization", specialization);
				gv.put("institution", institution);
				
				delegator.store(gv);	
        		retSucc.put("result", "updated record with id: " + workProgressId);
        	} else {
        		retSucc.put("result", "not found record with id: " + workProgressId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}

	public static Map<String, Object> deleteWorkProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String workProgressId = (String)context.get("workProgressId");
        try{
        	GenericValue gv = delegator.findOne("WorkProgress", UtilMisc.toMap("workProgressId",workProgressId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + workProgressId);
        	} else {
        		retSucc.put("result", "not found record with id: " + workProgressId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
}
