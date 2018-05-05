package org.ofbiz.routes;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;


public class VehicleService {
	
	public static void removeVehicle(HttpServletRequest request, HttpServletResponse response){
		try{
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			String vehicleId = request.getParameter("vehicleId");
			Debug.log("removeVehicle, vehicleId = " + vehicleId);
			GenericValue v = delegator.findOne("Vehicle", UtilMisc.toMap("vehicleId",vehicleId), false);
			if(v != null){
				delegator.removeValue(v);
				Debug.log("removeVehicle, vehicleId = " + vehicleId + " DELETED");
			}else{
				Debug.log("removeVehicle, vehicleId = " + vehicleId + " NOT exists");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static Map<String, Object> getListOfVehicles(DispatchContext ctx, 
			Map<String, ? extends Object> context){ 
		
		Map<String, Object> retSucc = FastMap.newInstance();
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> lst = delegator.findList("Vehicle",
					null,null,null,null,false);
			
			retSucc.put("listVehicles", lst);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return retSucc;
	}
	
	public static Map<String, Object> createAVehicle(DispatchContext ctx, 
			Map<String, ? extends Object> context){ 
		
		Map<String, Object> retSucc = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();// tuong tac DB
		
		String vehicleCode = (String)context.get("vehicleCode");
		String description = (String)context.get("description");
		
		Debug.log("createAVehicle, create a vehicle, vehicleCode = " + 
		vehicleCode
		+ ", description = " + description);
		
		try{
			String vehicleId = delegator.getNextSeqId("Vehicle");// lay ID tu tang
			
			GenericValue v = delegator.makeValue("Vehicle");// 1 ban ghi
			
			v.put("vehicleId", vehicleId);
			v.put("vehicleCode", vehicleCode);
			v.put("description", description);
			
			delegator.create(v);// insert into table Vehicle
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	public static void main(String[] args){
		System.out.println("abc");
	}
}
