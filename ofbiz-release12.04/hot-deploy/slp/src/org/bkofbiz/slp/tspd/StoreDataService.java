package org.bkofbiz.slp.tspd;

import java.util.Map;

import org.ofbiz.service.DispatchContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.squareup.okhttp.MediaType;

public class StoreDataService {
	public static final String MODULE_NAME = TSPDService.class.getName();
	public static Map<String, Object> storeDataJson(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		String str = (String) context.get("listPoints");
		JsonParser jsonParser= new JsonParser();
		JsonArray jsonArray= jsonParser.parse(str).getAsJsonArray();
		for(int i=0;i<jsonArray.size();i++){
			jsonArray.get(i).toString();
		}
		return null;
	}
	

}
