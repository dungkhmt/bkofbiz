package org.bkofbiz.slp.sampledata;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.maps.model.LatLng;
import com.mso.googlemap.direction.DirectionQuery;
import com.mso.googlemap.model.Point;
import com.squareup.okhttp.MediaType;

public class DataSampleService {
	public static final String MODULE_NAME = DataSampleService.class.getName();
	/**
	 * Query googlemap and store data to database
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> saveData(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		String listPoints = (String) context.get("listPoints");
		String description = (String) context.get("description");
		String name = (String) context.get("name");
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher localDispatcher = dctx.getDispatcher();
		Type listType = new TypeToken<List<Point>>() {
		}.getType();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Point> list = new Gson().fromJson(listPoints, listType);
		/**
		 * Save data set
		 */
		GenericValue gv = delegator.makeValue("DataSet");
		String ds_id = delegator.getNextSeqId("DataSet");
		gv.put("DS_Id", ds_id);
		gv.put("DS_Description", description);
		gv.put("DS_Name", name);
		gv.put("DS_NumPoints", (long)list.size());
		Debug.log(gv.toString(), MODULE_NAME);
		try {
			delegator.create(gv);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
		List<Point> listNP= new ArrayList<Point>();
		for(int i=0;i<list.size();i++){
			GenericValue pGv = delegator.makeValue("PointDS");
			String p_id = delegator.getNextSeqId("PointDS");
			pGv.put("P_Id", p_id);
			pGv.put("P_Lat",""+list.get(i).getLat());
			pGv.put("P_Lng", ""+list.get(i).getLng());
			pGv.put("DS_Id", ds_id);
			listNP.add(new Point(list.get(i).getLat(), list.get(i).getLng(), p_id));
			try {
				delegator.create(pGv);
			} catch (GenericEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		for (int i = 0; i < listNP.size(); i++)
			for (int j = 0; j < listNP.size(); j++)
				if (i != j) {
					String key = listNP.get(i).getID() + "_"
							+ listNP.get(j).getID();
					GenericValue gvdr = delegator.makeValue("Direction");
					String dr_id = delegator.getNextSeqId("Direction");
					gvdr.put("D_Id", dr_id);
					gvdr.put("D_StartPointId",""+ listNP.get(i).getID());
					gvdr.put("D_EndPointId",""+listNP.get(j).getID());
					gvdr.put("D_Distance", distances.get(key));
					gvdr.put("D_Time", timedurations.get(key));
					gvdr.put("DS_Id", ds_id);
					Debug.log(gson.toJson(mapPath.get(key)));
					gvdr.put("D_Path", gson.toJson(mapPath.get(key)));
					try {
						delegator.create(gvdr);
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
}
