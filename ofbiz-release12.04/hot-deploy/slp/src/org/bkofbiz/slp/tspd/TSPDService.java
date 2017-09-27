package org.bkofbiz.slp.tspd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.bkofbiz.slp.sampledata.DataSampleService;
import org.bkofbiz.slp.tspd.model.DroneDelivery;
import org.bkofbiz.slp.tspd.model.LatLng;
import org.bkofbiz.slp.tspd.model.Point;
import org.bkofbiz.slp.tspd.model.TSPDSolution;
import org.bkofbiz.slp.tspd.model.Tour;
import org.bkofbiz.slp.tspd.model.TruckTour;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.HttpClient;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import utils.JsonMapUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
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
	public static String dataJson = "";

	public static Map<String, Object> tspdSolve(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Debug.logInfo(dataJson, MODULE_NAME);
		/**
		 * Load data
		 */
		Map<String, Object> suc = ServiceUtil.returnSuccess();
		Gson gson = new Gson();
		String datasetid = new JsonParser().parse(dataJson).getAsJsonObject()
				.getAsJsonPrimitive("datasetid").getAsString();
		List<Map<String, Object>> listDirection = null;
		List<Map<String, Object>> listPoint = null;
		try {
			listDirection = DataSampleService.getDirectionbyDataSet(delegator,
					datasetid);
			listPoint = DataSampleService.getPointbyDataSet(delegator,
					datasetid);
		} catch (GenericEntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return ServiceUtil.returnError(e1.getMessage());
		}
		/**
		 * RE-mapping with new ID
		 */
		List<Map<String, String>> listPointMaped = new ArrayList<Map<String, String>>();
		Map<String, Integer> mapId = new HashMap<String, Integer>();
		Map<String, String> mapValue = new HashMap<String, String>();
		for (int i = 0; i < listPoint.size(); i++) {
			Map<String, Object> point = listPoint.get(i);
			mapId.put((String) point.get("P_Id"), i);
			mapValue.put(i + "", (String) point.get("P_Id"));
			Map<String, String> pointMaped = new HashMap<String, String>();
			pointMaped.put("id", "" + i);
			pointMaped.put("lat", (String) point.get("P_Lat"));
			pointMaped.put("lng", (String) point.get("P_Lng"));
			listPointMaped.add(pointMaped);
		}

		Map<String, Object> map = FastMap.newInstance();
		for (int i = 0; i < listDirection.size(); i++) {
			Map<String, Object> direction = listDirection.get(i);
			String key = mapId.get(direction.get("D_StartPointId")) + "_"
					+ mapId.get(direction.get("D_EndPointId"));
			map.put(key, direction.get("D_Distance"));
		}
		/**
		 * Build json data
		 */
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(dataJson).getAsJsonObject();
		jsonObject.remove("datasetid");
		jsonObject.add("listPoints", gson.toJsonTree(listPointMaped));
		jsonObject.add("map", gson.toJsonTree(map));
		/**
		 * Call HTTP to ezRouting
		 */
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(180, TimeUnit.SECONDS);
		client.setReadTimeout(180, TimeUnit.SECONDS);
		client.setWriteTimeout(180, TimeUnit.SECONDS);
		RequestBody body = RequestBody.create(JSON_HEADER,
				jsonObject.toString());
		Debug.log(jsonObject.toString(), MODULE_NAME);
		Request request = new Request.Builder()
				.url("http://localhost:8088/ezRoutingAPI/tsp-with-drone-input-distance")
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
			return ServiceUtil.returnError(e.getMessage());
		}
		if (!response.isSuccessful()) {
			return ServiceUtil.returnError("Request didn't complete!!");
		}
		Map<String, Object> res = JsonMapUtils.json2MapStrObject(resultString);
		TSPDSolution solution = gson.fromJson(resultString, TSPDSolution.class);
		Tour tours[] = solution.getTours();
		Map<String, List<LatLng>> mapDirectionPath = new HashMap<String, List<LatLng>>();
		for (int i = 0; i < tours.length; i++)
			if (tours[i] != null) {
				ArrayList<DroneDelivery> dds = tours[i].getDD();
				for (int j = 0; j < dds.size(); j++) {

					DroneDelivery dd = dds.get(j);
					Point p = dd.getRendezvous_node();
					/**
					 * check point last tour
					 */
					if (Integer.parseInt(p.getID()) >= listPoint.size()) {
						p.setID(mapValue.get("0"));
					} else {
						p.setID(mapValue.get(p.getID()));
					}
					dd.setRendezvous_node(p);

					p = dd.getLauch_node();
					p.setID(mapValue.get(p.getID()));
					dd.setLauch_node(p);

					p = dd.getDrone_node();
					p.setID(mapValue.get(p.getID()));
					dd.setDrone_node(p);
					dds.set(j, dd);
				}

				ArrayList<Point> truckTour = tours[i].getTD().getTruck_tour();
				for (int j = 0; j < truckTour.size(); j++) {
					Point p = truckTour.get(j);
					/**
					 * check point last tour
					 */
					if (Integer.parseInt(p.getID()) >= listPoint.size()) {
						p.setID(mapValue.get("0"));
					} else {
						p.setID(mapValue.get(p.getID()));
					}
					truckTour.set(j, p);
				}
				for (int j = 1; j < truckTour.size(); j++) {
					Point p2 = truckTour.get(j);
					Point p1 = truckTour.get(j - 1);
					String key = p1.getID() + "_" + p2.getID();
					if (mapDirectionPath.containsKey(key))
						continue;
					List<Map<String, Object>> qres = null;
					try {
						qres = DataSampleService.getDirectiobyPoint(delegator,
								p1.getID(), p2.getID());
					} catch (GenericEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return ServiceUtil.returnError(e.getMessage());
					}
					Map<String, Object> tmp = qres.get(0);
					String str = (String) tmp.get("D_Path");
					Debug.log(str,MODULE_NAME);
					Type listType = new TypeToken<List<LatLng>>() {
					}.getType();
					List<LatLng> path = gson.fromJson(str, listType);
					Debug.log(path.toString(),MODULE_NAME);
					mapDirectionPath.put(key, path);
				}
			}
		Debug.log(solution.toString(), MODULE_NAME);
		suc.put("sol", JsonMapUtils.json2MapStrObject(gson.toJson(solution)));
		suc.put("directionPath", mapDirectionPath);
		return suc;
	}

	public static Map<String, Object> tspkdSolve(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Debug.logInfo(dataJson, MODULE_NAME);
		/**
		 * Load data
		 */
		Map<String, Object> suc = ServiceUtil.returnSuccess();
		Gson gson = new Gson();
		String datasetid = new JsonParser().parse(dataJson).getAsJsonObject()
				.getAsJsonPrimitive("datasetid").getAsString();
		List<Map<String, Object>> listDirection = null;
		List<Map<String, Object>> listPoint = null;
		try {
			listDirection = DataSampleService.getDirectionbyDataSet(delegator,
					datasetid);
			listPoint = DataSampleService.getPointbyDataSet(delegator,
					datasetid);
		} catch (GenericEntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return ServiceUtil.returnError(e1.getMessage());
		}
		/**
		 * RE-mapping with new ID
		 */
		List<Map<String, String>> listPointMaped = new ArrayList<Map<String, String>>();
		Map<String, Integer> mapId = new HashMap<String, Integer>();
		for (int i = 0; i < listPoint.size(); i++) {
			Map<String, Object> point = listPoint.get(i);
			mapId.put((String) point.get("P_Id"), i);
			Map<String, String> pointMaped = new HashMap<String, String>();
			pointMaped.put("id", "" + i);
			pointMaped.put("lat", (String) point.get("P_Lat"));
			pointMaped.put("lng", (String) point.get("P_Lng"));
			listPointMaped.add(pointMaped);
		}

		Map<String, Object> map = FastMap.newInstance();
		for (int i = 0; i < listDirection.size(); i++) {
			Map<String, Object> direction = listDirection.get(i);
			String key = mapId.get(direction.get("D_StartPointId")) + "_"
					+ mapId.get(direction.get("D_EndPointId"));
			map.put(key, direction.get("D_Distance"));
		}
		/**
		 * Build json data
		 */
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(dataJson).getAsJsonObject();
		jsonObject.remove("datasetid");
		jsonObject.add("listPoints", gson.toJsonTree(listPointMaped));
		jsonObject.add("map", gson.toJsonTree(map));
		/**
		 * Call HTTP to ezRouting
		 */
		Debug.logInfo(dataJson, MODULE_NAME);
		if (dataJson.isEmpty())
			ServiceUtil.returnError("No data in cache!");
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(180, TimeUnit.SECONDS);
		client.setReadTimeout(180, TimeUnit.SECONDS);
		client.setWriteTimeout(180, TimeUnit.SECONDS);
		RequestBody body = RequestBody.create(JSON_HEADER,
				jsonObject.toString());
		Debug.log(jsonObject.toString(), MODULE_NAME);
		Request request = new Request.Builder()
				.url("http://localhost:8088/ezRoutingAPI/tsp-with-kdrone-input-distance")
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
			return ServiceUtil.returnError(e.getMessage());
		}
		if (!response.isSuccessful()) {
			return ServiceUtil.returnError("Request didn't compete!!");
		}
		// Map<String,Object> map =JsonMapUtils.json2MapStrObject(resultString);
		suc.put("sol", JsonMapUtils.json2MapStrObject(resultString));
		return suc;
	}

	/**
	 * Store data to cache
	 * 
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> storeData2Cache(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> suc = ServiceUtil.returnSuccess();
		String truckSpeed = (String) context.get("truckSpeed");
		String droneSpeed = (String) context.get("droneSpeed");
		String truckCost = (String) context.get("truckCost");
		String droneCost = (String) context.get("droneCost");
		String delta = (String) context.get("delta");
		String endurance = (String) context.get("endurance");
		// String listPoints = (String) context.get("listPoints");
		String datasetid = (String) context.get("datasetid");
		Debug.logInfo("truckSpeed " + truckSpeed + " droneSpeed" + droneSpeed
				+ " truckCost" + truckCost + " droneCost" + droneCost
				+ " delta" + delta + " endurance" + endurance + " datasetid"
				+ datasetid, MODULE_NAME);
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
		jsonObject.addProperty("datasetid", datasetid);
		Debug.logInfo(jsonObject.toString(), MODULE_NAME);
		dataJson = jsonObject.toString();
		return suc;
	}
}
