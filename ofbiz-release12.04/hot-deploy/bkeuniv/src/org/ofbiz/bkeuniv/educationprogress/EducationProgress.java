package src.org.ofbiz.bkeuniv.educationprogress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.sql.Date;

import javolution.util.FastList;
import javolution.util.FastMap;

public class EducationProgress {
	public static Map<String, Object> getEducationProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String[] keys = {"educationProgressId", "staffId", "educationType", "institution", "speciality", "graduateDate"};
		String[] search = {"institution"};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if( index == -1) {
						if(key=="graduateDate") {
							condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, Date.valueOf((String) el));
						} else {
							condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
						}
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}
			
			List<GenericValue> list = delegator.findList("EducationProgress", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listEducationProgress = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> mapEducationProgress = FastMap.newInstance();
				mapEducationProgress.put("educationProgressId", el.getString("educationProgressId"));
				mapEducationProgress.put("staffId", el.getString("staffId"));
				mapEducationProgress.put("educationType", el.getString("educationType"));
				mapEducationProgress.put("institution", el.getString("institution"));
				mapEducationProgress.put("speciality", el.getString("speciality"));
				mapEducationProgress.put("graduateDate", el.getString("graduateDate"));
				listEducationProgress.add(mapEducationProgress);
			}
			result.put("educationProgress", listEducationProgress);
			return result;
		
		} catch (Exception e) {
			System.out.print("Education Progress Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createEducationProgress(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String staffId = (String) context.get("staffId");
		String educationType = (String) context.get("educationType");
		String institution = (String) context.get("institution");
		String speciality = (String) context.get("speciality");
		String graduateDate = (String) context.get("graduateDate");
		
		GenericValue gv = delegator.makeValue("EducationProgress");

		gv.put("educationProgressId", delegator.getNextSeqId("EducationProgress"));

		try {
			gv.put("staffId", staffId);
			gv.put("educationType", educationType);
			gv.put("institution", institution);
			gv.put("speciality", speciality);
			gv.put("graduateDate", Date.valueOf(graduateDate));

			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		Map<String, Object> mapEducationProgress = FastMap.newInstance();
		mapEducationProgress.put("educationProgressId", gv.getString("educationProgressId"));
		mapEducationProgress.put("staffId", staffId);
		mapEducationProgress.put("educationType", educationType);
		mapEducationProgress.put("institution", institution);
		mapEducationProgress.put("speciality", speciality);
		mapEducationProgress.put("graduateDate", graduateDate);
		
		
		retSucc.put("educationProgress", mapEducationProgress);

		return retSucc;
	}
	
	public static Map<String, Object> deleteEducationProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String educationProgressId = (String)context.get("educationProgressId");
        try{
        	GenericValue gv = delegator.findOne("EducationProgress", UtilMisc.toMap("educationProgressId",educationProgressId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + educationProgressId);
        	} else {
        		retSucc.put("result", "not found record with id: " + educationProgressId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateEducationProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		String educationType = (String) context.get("educationType");
		String institution = (String) context.get("institution");
		String speciality = (String) context.get("speciality");
		String graduateDate = (String) context.get("graduateDate");
		String educationProgressId = (String) context.get("educationProgressId");
		
		try{
			GenericValue gv = delegator.findOne("EducationProgress", false, UtilMisc.toMap("educationProgressId",educationProgressId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("educationType", educationType);
				gv.put("institution", institution);
				gv.put("speciality", speciality);
				gv.put("graduateDate", Date.valueOf(graduateDate));
				
				delegator.store(gv);	
        		retSucc.put("result", "updated record with id: " + educationProgressId);
        	} else {
        		retSucc.put("result", "not found record with id: " + educationProgressId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}
