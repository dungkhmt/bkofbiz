package org.bkofbiz.slp.tspd;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ofbiz.service.DispatchContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mso.googlemap.direction.DirectionQuery;
import com.mso.googlemap.model.Point;
import com.squareup.okhttp.MediaType;

public class StoreDataService {
	public static final String MODULE_NAME = TSPDService.class.getName();
	public static Map<String, Object> storeDataJson(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		String str = (String) context.get("listPoints");
		Type listType = new TypeToken<List<Point>>() {}.getType();
		Gson gson= new GsonBuilder().setPrettyPrinting().create();
		List<Point> list = new Gson().fromJson(str, listType);
		DirectionQuery directionQuery= DirectionQuery.getInstance();
		Map<String,Object> res= directionQuery.getMatrixDirection(list, 4);
		return null;
	}
	public static void main(String[] args) {
		
	}

}
