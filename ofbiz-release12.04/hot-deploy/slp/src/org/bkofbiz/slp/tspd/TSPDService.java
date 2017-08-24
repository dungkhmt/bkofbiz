package org.bkofbiz.slp.tspd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.HttpClient;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import utils.JsonMapUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class TSPDService {
	public static final String MODULE_NAME = TSPDService.class.getName();
	public static final MediaType JSON_HEADER = MediaType
			.parse("application/json; charset=utf-8");
	/**
	 * Cache data
	 */
	public static String dataJson="";
	
	public static Map<String, Object> tspdSolve(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> suc = ServiceUtil.returnSuccess();
		/**
		 * Call HTTP to ezRouting
		 */
		Debug.logInfo(dataJson,MODULE_NAME);
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(180, TimeUnit.SECONDS);
		client.setReadTimeout(180, TimeUnit.SECONDS);
		client.setWriteTimeout(180, TimeUnit.SECONDS);
		RequestBody body = RequestBody.create(JSON_HEADER,
				dataJson);
		Request request = new Request.Builder()
				.url("http://localhost:8088/ezRoutingAPI/tsp-with-drone")
				.post(body).build();
		Response response = null;
		String resultString = null;
		try {
			response = client.newCall(request).execute();
			resultString = response.body().string();
			Debug.logInfo(resultString, MODULE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!response.isSuccessful()) {
			return ServiceUtil.returnError("Request didn't compete!!");
		}
		Map<String,Object> map =JsonMapUtils.json2MapStrObject(resultString);
		suc.put("sol", map);
		return suc;
	}
	/** Store data to cache
	 * 
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> storeData2Cache(DispatchContext dctx,
			Map<String, ? extends Object> context){
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
				+ " delta" + delta + " endurance" + endurance + " listPoints"
				+ listPoints, MODULE_NAME);
		/**
		 * Make Json object
		 */
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("truckSpeed", truckSpeed);
		jsonObject.addProperty("droneSpeed", droneSpeed);
		jsonObject.addProperty("truckCost", truckCost);
		jsonObject.addProperty("droneCost", droneCost);
		jsonObject.addProperty("delta", delta);
		jsonObject.addProperty("endurance", endurance);
		jsonObject.add("listPoints", new JsonParser().parse(listPoints)
				.getAsJsonArray());
		Debug.logInfo(jsonObject.toString(), MODULE_NAME);
		dataJson=jsonObject.toString();
		return suc;
	}
}
