package org.bkofbiz.slp.sampledata;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.maps.model.LatLng;
import com.mso.googlemap.direction.DirectionQuery;
import com.mso.googlemap.model.Point;

public class DataSampleService {
	public static final String MODULE_NAME = DataSampleService.class.getName();

	/**
	 * Query googlemap and store data to database
	 * 
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> saveData(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		String listPoints = (String) context.get("listPoints");
		String description = (String) context.get("description");
		String name = (String) context.get("name");
		String datasetid = (String) context.get("datasetid");
		// Debug.log(datasetid,MODULE_NAME);

		Delegator delegator = dctx.getDelegator();
		LocalDispatcher localDispatcher = dctx.getDispatcher();
		Type listType = new TypeToken<List<Point>>() {
		}.getType();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Point> list = new Gson().fromJson(listPoints, listType);
		GenericValue gv = null;
		String ds_id;
		if (datasetid == null) {
			/**
			 * make new dataset
			 */
			gv = delegator.makeValue("DataSet");
			ds_id = delegator.getNextSeqId("DataSet");
			gv.put("DS_Id", ds_id);
		} else {
			/**
			 * Store dataset
			 */
			try {
				gv = delegator.findOne("DataSet",
						UtilMisc.toMap("DS_Id", datasetid), false);
			} catch (GenericEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Map<String, Object> rs = ServiceUtil
						.returnError(e.getMessage());
				return rs;
			}
			ds_id = datasetid;
		}
		/**
		 * Put atribute
		 */
		if (description != null)
			gv.put("DS_Description", description);
		if (name != null)
			gv.put("DS_Name", name);
		gv.put("DS_NumPoints", (long) list.size());
		Debug.log(gv.toString(), MODULE_NAME);
		/**
		 * Push Database
		 */
		if (datasetid == null) {
			try {
				delegator.create(gv);
			} catch (GenericEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Map<String, Object> rs = ServiceUtil
						.returnError(e.getMessage());
				return rs;
			}
		} else {
			try {
				delegator.store(gv);
				/**
				 * Remove old diretion add point
				 */
				deleteDirections(ds_id, delegator);
				deletePoints(ds_id, delegator);
			} catch (GenericEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Map<String, Object> rs = ServiceUtil
						.returnError(e.getMessage());
				return rs;
			}
		}
		/**
		 * create new point
		 */
		List<Point> listNP = new ArrayList<Point>();
		for (int i = 0; i < list.size(); i++) {
			try {
				String p_id = addAPoint(delegator, "" + list.get(i).getLat(),
						"" + list.get(i).getLng(), ds_id);
				listNP.add(new Point(list.get(i).getLat(),
						list.get(i).getLng(), p_id));
			} catch (GenericEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Map<String, Object> rs = ServiceUtil
						.returnError(e.getMessage());
				return rs;
			}
		}
		DirectionQuery directionQuery = DirectionQuery.getInstance();
		/**
		 * Query data
		 */
		Map<String, Object> res = directionQuery.getMatrixDirection(listNP, 4);
		Debug.log(res.toString());

		Map<String, Long> timedurations = (Map<String, Long>) res
				.get("duration");
		Map<String, Double> distances = (Map<String, Double>) res
				.get("distance");
		Map<String, List<LatLng>> mapPath = (Map<String, List<LatLng>>) res
				.get("path");
		/**
		 * create new direction
		 */
		for (int i = 0; i < listNP.size(); i++)
			for (int j = 0; j < listNP.size(); j++)
				if (i != j) {
					String key = listNP.get(i).getID() + "_"
							+ listNP.get(j).getID();
					try {
						addADirection(delegator, "" + listNP.get(i).getID(),
								listNP.get(j).getID(), distances.get(key)/1000,
								timedurations.get(key), ds_id,
								gson.toJson(mapPath.get(key)));
					} catch (GenericEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Map<String, Object> rs = ServiceUtil.returnError(e
								.getMessage());
						return rs;
					}
				}
		Map<String, Object> rs = ServiceUtil.returnSuccess();
		return rs;

	}

	/**
	 * add a Points
	 * 
	 * @param delegator
	 * @param P_Lat
	 * @param P_Lng
	 * @param DS_Id
	 * @throws GenericEntityException
	 */
	public static String addAPoint(Delegator delegator, String P_Lat,
			String P_Lng, String DS_Id) throws GenericEntityException {
		GenericValue pGv = delegator.makeValue("PointDS");
		String p_id = delegator.getNextSeqId("PointDS");
		pGv.put("P_Id", p_id);
		pGv.put("P_Lat", P_Lat);
		pGv.put("P_Lng", P_Lng);
		pGv.put("DS_Id", DS_Id);
		pGv.put("P_AllowDrone", 1);

		delegator.create(pGv);
		return p_id;
	}

	/**
	 * Add a Direction
	 * 
	 * @param delegator
	 * @param D_StartPointId
	 * @param D_EndPointId
	 * @param D_Distance
	 * @param D_Time
	 * @param DS_Id
	 * @param D_Path
	 * @return
	 * @throws GenericEntityException
	 *             if create false
	 */
	public static String addADirection(Delegator delegator,
			String D_StartPointId, String D_EndPointId, Double D_Distance,
			Long D_Time, String DS_Id, String D_Path)
			throws GenericEntityException {
		GenericValue gvdr = delegator.makeValue("Direction");
		String dr_id = delegator.getNextSeqId("Direction");
		gvdr.put("D_Id", dr_id);
		gvdr.put("D_StartPointId", D_StartPointId);
		gvdr.put("D_EndPointId", D_EndPointId);
		gvdr.put("D_Distance", D_Distance);
		gvdr.put("D_Time", D_Time);
		gvdr.put("DS_Id", DS_Id);
		gvdr.put("D_Path", D_Path);
		delegator.create(gvdr);
		return dr_id;
	}

	/**
	 * Delete all direction by ds_id
	 * 
	 * @param DS_Id
	 * @param delegator
	 * @return
	 * @throws GenericEntityException
	 *             if delete false
	 */
	public static void deleteDirections(String DS_Id, Delegator delegator)
			throws GenericEntityException {
		EntityCondition entity = EntityCondition.makeCondition("DS_Id",
				EntityOperator.EQUALS, DS_Id);

		delegator.removeByCondition("Direction", entity);

	}

	/**
	 * Delete all point by ds_id
	 * 
	 * @param DS_Id
	 * @param delegator
	 * @return
	 * @throws GenericEntityException
	 *             if delete false
	 */
	public static void deletePoints(String DS_Id, Delegator delegator)
			throws GenericEntityException {
		EntityCondition entity = EntityCondition.makeCondition("DS_Id",
				EntityOperator.EQUALS, DS_Id);

		delegator.removeByCondition("PointDS", entity);

	}

	public static Map<String, Object> getDataSets(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		List<GenericValue> list = null;
		try {
			list = delegator.findList("DataSet", null, null, null, null, false);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		List<Map> listRes = FastList.newInstance();
		for (GenericValue el : list) {
			Map<String, Object> map = FastMap.newInstance();
			map.put("DS_Id", el.getString("DS_Id"));
			map.put("DS_Description", el.getString("DS_Description"));
			map.put("DS_Name", el.getString("DS_Name"));
			map.put("DS_NumPoints", el.getString("DS_NumPoints"));
			listRes.add(map);
		}
		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("sol", listRes);
		return result;

	}

	/**
	 * Get all direction in a DataSet
	 * 
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> getDirections(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		String DS_Id = (String) context.get("DS_Id");
		Delegator delegator = dctx.getDelegator();
		List<Map<String,Object>> listRes = FastList.newInstance();
		try {
			listRes = getDirectionbyDataSet(delegator, DS_Id);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		
		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("sol", listRes);
		return result;

	}

	public static List<Map<String, Object>> getDirectionbyDataSet(
			Delegator delegator, String DS_Id) throws GenericEntityException {
		EntityCondition entity = EntityCondition.makeCondition("DS_Id",
				EntityOperator.EQUALS, DS_Id);
		List<GenericValue> list = null;

		list = delegator.findList("Direction", entity, null, null, null, false);
		List<Map<String, Object>> listRes = FastList.newInstance();
		for (GenericValue el : list) {
			Map<String, Object> map = FastMap.newInstance();
			map.put("D_Id", el.getString("D_Id"));
			map.put("D_StartPointId", el.getString("D_StartPointId"));
			map.put("D_EndPointId", el.getString("D_EndPointId"));
			map.put("D_Distance", el.getString("D_Distance"));
			map.put("D_Time", el.getString("D_Time"));
			map.put("DS_Id", el.getString("DS_Id"));
			map.put("D_Path", el.getString("D_Path"));
			listRes.add(map);
		}
		return listRes;
	}
	/**
	 * Get Direction by point
	 * @param delegator
	 * @param startPointId
	 * @param endPointId
	 * @return
	 * @throws GenericEntityException
	 */
	
	public static List<Map<String, Object>> getDirectiobyPoint(Delegator delegator,String startPointId,String endPointId) throws GenericEntityException{
		List<EntityCondition> conditions=new ArrayList<EntityCondition>();
		conditions.add(EntityCondition.makeCondition("D_StartPointId",EntityOperator.EQUALS,startPointId));
		conditions.add(EntityCondition.makeCondition("D_EndPointId",EntityOperator.EQUALS,endPointId));
		EntityCondition entity = EntityCondition.makeCondition(conditions);
		List<GenericValue> list = null;
		list = delegator.findList("Direction", entity, null, null, null, false);
		List<Map<String, Object>> listRes = FastList.newInstance();
		for (GenericValue el : list) {
			Map<String, Object> map = FastMap.newInstance();
			map.put("D_Id", el.getString("D_Id"));
			map.put("D_StartPointId", el.getString("D_StartPointId"));
			map.put("D_EndPointId", el.getString("D_EndPointId"));
			map.put("D_Distance", el.getString("D_Distance"));
			map.put("D_Time", el.getString("D_Time"));
			map.put("DS_Id", el.getString("DS_Id"));
			map.put("D_Path", el.getString("D_Path"));
			listRes.add(map);
		}
		return listRes;
	}
	/**
	 * Get all Point in a DataSet
	 * 
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> getPoints(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		String DS_Id = (String) context.get("DS_Id");
		Delegator delegator = dctx.getDelegator();
		List<Map<String,Object>> listRes = FastList.newInstance();
		try {
			listRes = getPointbyDataSet(delegator,DS_Id);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("sol", listRes);
		return result;
	}
	
	/**
	 * Get point by datasetid
	 * @param delegator
	 * @param DS_Id
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<Map<String, Object>> getPointbyDataSet(Delegator delegator,String DS_Id) throws GenericEntityException{
		EntityCondition entity = EntityCondition.makeCondition("DS_Id",
				EntityOperator.EQUALS, DS_Id);
		List<GenericValue> list = delegator.findList("PointDS", entity, null, null, null,
				false);
		List<Map<String,Object>> listRes = FastList.newInstance();
		for (GenericValue el : list) {
			Map<String, Object> map = FastMap.newInstance();
			map.put("P_Id", el.getString("P_Id"));
			map.put("P_Lat", el.getString("P_Lat"));
			map.put("P_Lng", el.getString("P_Lng"));
			map.put("DS_Id", el.getString("DS_Id"));
			map.put("P_AllowDrone", el.getLong("P_AllowDrone"));
			listRes.add(map);
		}
		return listRes;
	}
}
