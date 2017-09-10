package src.org.ofbiz.bkeuniv.academicyear;

import java.util.List;
import java.util.Map;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class AcademicYearService {
	public static String module = AcademicYearService.class.getName();
	
	public static Map<String, Object> getListAcademicYears(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try{
			Delegator delegator = ctx.getDelegator();
			List<GenericValue> acaYears = delegator.findList("AcademicYear", 
					null, 
					null,
					null,
					null,
					false);
			
			retSucc.put("academicYears", acaYears);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}
