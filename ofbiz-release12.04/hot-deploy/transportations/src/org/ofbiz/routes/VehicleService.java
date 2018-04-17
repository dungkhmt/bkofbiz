package src.org.ofbiz.routes;

import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;


public class VehicleService {
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
}
