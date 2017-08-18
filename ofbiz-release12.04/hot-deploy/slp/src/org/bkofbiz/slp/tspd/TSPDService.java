package org.bkofbiz.slp.tspd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TSPDService {
	public static final String MODULE_NAME = TSPDService.class.getName();

	public static Map<String, Object> tspdSolve(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> suc = ServiceUtil.returnSuccess();
		String truckSpeed = (String) context.get("truckSpeed");
		String droneSpeed = (String) context.get("droneSpeed");
		String truckCost = (String) context.get("truckCost");
		String droneCost = (String) context.get("droneCost");
		String delta = (String) context.get("delta");
		String endurance = (String) context.get("endurance");
		String listPoints = (String) context.get("listPoints");
		Debug.logInfo("truckSpeed " + truckSpeed + " droneSpeed" + droneSpeed
				+ " truckCost" + truckCost + " droneCost" + droneCost
				+ " delta" + delta + " endurance" + endurance+" listPoints"+listPoints, MODULE_NAME);
		suc.put("sol", "");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		Gson gson=new Gson();
		JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty("truckSpeed", truckSpeed);
		jsonObject.addProperty("droneSpeed", droneSpeed);
		jsonObject.addProperty("truckCost", truckCost);
		jsonObject.addProperty("droneCost", droneCost);
		jsonObject.addProperty("delta", delta);
		jsonObject.addProperty("endurance", endurance);
		jsonObject.add("listPoints", new JsonParser().parse(listPoints).getAsJsonArray());
		Debug.logInfo(jsonObject.toString(), MODULE_NAME);
		HttpPost post = new HttpPost(
				"http://localhost:8088/ezRoutingAPI/tsp-with-drone");
		StringEntity params;
		try {
			params = new StringEntity(jsonObject.toString());
		
		post.addHeader("content-type", "application/json");
		post.setEntity(params);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpResponse response;
		try {
			response = httpClient.execute(post);
			HttpEntity res = response.getEntity();
			String responseString = EntityUtils.toString(res, "UTF-8");
			Debug.logInfo(responseString,MODULE_NAME);
			// Tour result = gson.fromJson(responseString, Tour.class);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	return suc;
	}

}
