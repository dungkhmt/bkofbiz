package org.ofbiz.routes;

import java.util.Locale;
import java.util.Map;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;


public class LearningRouteService {
	public final static String module = LearningRouteService.class.getName();
	
	
	public static Map<String, Object> createARoute(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
	        
			Delegator delegator = ctx.getDelegator();
	        LocalDispatcher dispatcher = ctx.getDispatcher();
	        
	        GenericValue userLogin = (GenericValue) context.get("userLogin");
	        Locale locale = (Locale) context.get("locale");
	         
	        
	        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
	
	        String date = (String)context.get("date");
	        String shipperId = (String)context.get("shipper");
	        int seq = (Integer)context.get("seq");
	        
	        System.out.println("userLogin = " + userLogin + ", createARoute, date = " + date + ", shipperId = " + shipperId + ", seq = " + seq);
	        
	        retSucc.put("routeid","456");
	        
	        return retSucc;
	}
	
}
