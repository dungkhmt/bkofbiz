package org.ofbiz.party.shipper;

import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class ShiperServices {
	public final static String module = ShiperServices.class.getName();
	
	
	public static Map<String, Object> deleteAShipper(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String id = (String)context.get("idofshipper");
        try{
        	GenericValue gv = delegator.findOne("Shippers", UtilMisc.toMap("id",id), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}

	public static Map<String, Object> updateAShipper(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String name = (String)context.get("shipper-name");
		String id = (String)context.get("shipper-id");
		
		retSucc.put("shipper-id", id);
		
		try{
			GenericValue gv = delegator.findOne("Shippers", false, UtilMisc.toMap("id",id));
			if(gv != null){
				gv.put("name", name);
				delegator.store(gv);
			}else{
				return ServiceUtil.returnError("Do not find shipper with id = " + id);
			}
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
	
	public static Map<String, Object> createAShipper(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
	        
			Delegator delegator = ctx.getDelegator();
	        LocalDispatcher dispatcher = ctx.getDispatcher();
	        
	        GenericValue userLogin = (GenericValue) context.get("userLogin");
	        Locale locale = (Locale) context.get("locale");
	         
	        
	        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
	        
	        String shipperName = (String)context.get("shipper-name");
	        GenericValue gv = delegator.makeValue("Shippers");
	        gv.put("id", delegator.getNextSeqId("Shippers"));
	        
	        String id = (String)gv.get("id");
	        
	        GenericValue gvParty = delegator.makeValue("Party");
	        gvParty.put("partyId", id);
	        try{
	        	delegator.create(gvParty);
	        	gv.put("name", shipperName);
	        
	        	delegator.create(gv);
	        	
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	return ServiceUtil.returnError(ex.getMessage());
	        }
	        
	        retSucc.put("shipperid", id);
	        
	        return retSucc;
	 }
}
