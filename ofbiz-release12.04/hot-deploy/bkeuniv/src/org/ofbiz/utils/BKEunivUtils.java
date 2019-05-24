package org.ofbiz.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.ArrayUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

import com.google.gson.Gson;

public class BKEunivUtils {

	public static final String module = BKEunivUtils.class.getName();
	
	
	public static List<String> getListSecurityGroupsOfUserLogin(Delegator delegator, String staffId){
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("userLoginId",EntityOperator.EQUALS,staffId));
			
			
			List<GenericValue> list = delegator.findList("UserLoginSecurityGroup", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			
			List<String> retList = FastList.newInstance();
			
			for(GenericValue gv: list){
				retList.add(gv.getString("groupId"));
			}
			return retList;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<GenericValue> getListDepartmentsOfFaculty(Delegator delegator,
			String facultyId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("facultyId",EntityOperator.EQUALS,facultyId));
			conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"UPDATED"));
			
			List<GenericValue> depts = delegator.findList("Department", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			return depts;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListStaffsOfDepartment(Delegator delegator,
			String departmentId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("departmentId",EntityOperator.EQUALS,departmentId));
			
			List<GenericValue> staffs = delegator.findList("Staff", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<GenericValue> getListStaffsOfFaculty(Delegator delegator,
			String facultyId) {
		try {
			List<GenericValue> staffs = new ArrayList<GenericValue>();
			List<GenericValue> depts = getListDepartmentsOfFaculty(delegator,facultyId);
			for(GenericValue d: depts){
				String deptID = (String)d.get("departmentId");
				List<GenericValue> staffDept = getListStaffsOfDepartment(delegator, deptID);
				for(GenericValue st: staffDept)
					staffs.add(st);
			}
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<GenericValue> getListFaculties(Delegator delegator,
			String universityId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("universityId",EntityOperator.EQUALS,universityId));
			conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"UPDATED"));
			
			List<GenericValue> faculties = delegator.findList("Faculty", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			return faculties;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void getListAcademicYearsFaculties(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String universityId = (String)request.getParameter("universityId");
		
		try {

			List<GenericValue> acaYears = delegator.findList("AcademicYear",
					null, null, null, null, false);
			
			List<GenericValue> faculties = getListFaculties(delegator, universityId);
			
			Debug.log(module + "::getListAcademicYearsFaculties, university = " + universityId + ", years.sz = "
					+ acaYears.size() + ", faculties.sz = " + faculties.size());
			
			String rs = "{\"years\":[";
			for (int i = 0; i < acaYears.size(); i++) {
				GenericValue st = acaYears.get(i);
				rs += "{\"id\":\"" + st.get("academicYearId")
						+ "\",\"name\":\"" + st.get("academicYearName") + "\"}";
				if (i < acaYears.size() - 1)
					rs += ",";

			}
			rs += "]";
			rs += ",\"faculties\":[";
			for(int i = 0; i < faculties.size(); i++){
				GenericValue f =  faculties.get(i);
				String fId = (String)f.get("facultyId");
				String fName = (String)f.get("facultyName");
				rs += "{\"id\":\"" + fId
						+ "\",\"name\":\"" + fName + "\"}" + "\n";
				if (i < faculties.size() - 1)
					rs += ",";

			}
			rs += "]";
			rs += "}";
			
			Debug.log(module + "::getListAcademicYearsFaculties, json = " + rs);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static void getListAcademicYearsStaffs(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String universityId = (String)request.getParameter("universityId");
		
		try {

			List<GenericValue> acaYears = delegator.findList("AcademicYear",
					null, null, null, null, false);
			
			List<GenericValue> faculties = getListFaculties(delegator, universityId);
			List<GenericValue> staffs = FastList.newInstance();
			for(GenericValue f: faculties){
				List<GenericValue> staffs_faculty = getListStaffsOfFaculty(delegator, f.getString("facultyId"));
				for(GenericValue st: staffs_faculty)
					staffs.add(st);
			}
			
			
			Debug.log(module + "::getListAcademicYearsStaffs, university = " + universityId + ", years.sz = "
					+ acaYears.size() + ", faculties.sz = " + faculties.size() + ", staffs.sz = " + staffs.size());
			
			String rs = "{\"years\":[";
			for (int i = 0; i < acaYears.size(); i++) {
				GenericValue st = acaYears.get(i);
				rs += "{\"id\":\"" + st.get("academicYearId")
						+ "\",\"name\":\"" + st.get("academicYearName") + "\"}";
				if (i < acaYears.size() - 1)
					rs += ",";

			}
			rs += "]";
			rs += ",\"faculties\":[";
			for(int i = 0; i < faculties.size(); i++){
				GenericValue f =  faculties.get(i);
				String fId = (String)f.get("facultyId");
				String fName = (String)f.get("facultyName");
				rs += "{\"id\":\"" + fId
						+ "\",\"name\":\"" + fName + "\"}" + "\n";
				if (i < faculties.size() - 1)
					rs += ",";

			}
			rs += "]";
			
			rs += ",\"staffs\":[";
			for(int i = 0; i < staffs.size(); i++){
				GenericValue st =  staffs.get(i);
				String id = (String)st.get("staffId");
				String name = (String)st.get("staffName");
				rs += "{\"id\":\"" + id
						+ "\",\"name\":\"" + name + "\"}" + "\n";
				if (i < staffs.size() - 1)
					rs += ",";

			}
			rs += "}";
			
			Debug.log(module + "::getListAcademicYearsStaffs, json = " + rs);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@SuppressWarnings({ "unchecked" })
	public static void getListDepartmentsOfFacultyJSON(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String facultyId = (String)request.getParameter("facultyId");
		
		try {

			List<GenericValue> depts = getListDepartmentsOfFaculty(delegator, facultyId);
			
			String rs = "{\"departments\":[";
			for (int i = 0; i < depts.size(); i++) {
				GenericValue st = depts.get(i);
				rs += "{\"id\":\"" + st.get("departmentId")
						+ "\",\"name\":\"" + st.get("departmentName") + "\"}";
				if (i < depts.size() - 1)
					rs += ",";

			}
			rs += "]";
			rs += "}";
			
			Debug.log(module + "::getListAcademicYearsFaculties, json = " + rs);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@SuppressWarnings({ "unchecked" })
	public static void getListStaffsOfDepartmentJSON(
			HttpServletRequest request, HttpServletResponse response) {
		
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String departmentId = (String)request.getParameter("departmentId");
		Debug.log(module + "::getListStaffsOfDepartmentJSON, departmentId = " + departmentId);
		
		try {

			List<GenericValue> staffs = getListStaffsOfDepartment(delegator, departmentId);
			
			String rs = "{\"staffs\":[";
			for (int i = 0; i < staffs.size(); i++) {
				GenericValue st = staffs.get(i);
				rs += "{\"id\":\"" + st.get("staffId")
						+ "\",\"name\":\"" + st.get("staffName") + "\"}";
				if (i < staffs.size() - 1)
					rs += ",";

			}
			rs += "]";
			rs += "}";
			
			Debug.log(module + "::getListStaffsOfDepartmentJSON, json = " + rs);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static JSONObject parseJSONObject(Map<String, Object> map) {
		JSONObject result = new JSONObject();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			Object value = map.get(key);
			if (value instanceof Map) {
				result.put(key, parseJSONObject((Map<String, Object>) value));
			} else {
				if (value instanceof GenericValue) {
					result.put(key, parseJSONObject((GenericValue) value));
				} else {
					if (value instanceof List) {
						result.put(key, parseJSONArray((List<Object>) value));
					} else {
						Debug.log("parseJSONObject, key = " + key + ", value = " + value);
						result.put(key, value.toString());
					}
				}
			}
		}
		return result;
	}

	public static JSONArray parseJSONArray(List<Object> arr) {
		JSONArray result = new JSONArray();

		for (Object ob : arr) {
			if (ob instanceof Map) {
				result.add(parseJSONObject((Map<String, Object>) ob));
			} else {
				if (ob instanceof GenericValue) {
					result.add(parseJSONObject((GenericValue) ob));
				} else {
					if (ob instanceof List) {
						result.add(parseJSONArray((List<Object>) ob));
					} else {
						result.add(ob);
					}
				}
			}
		}
		return result;
	}

	public static JSONObject parseJSONObject(GenericValue gv) {
		JSONObject result = new JSONObject();
		Set<String> keys = gv.keySet();
		for (String key : keys) {
			result.put(key, gv.getString(key));
		}
		return result;
	}

	public static void writeJSONtoResponse(JSONObject json,
			HttpServletResponse response, int statusCode) {
		String jsonStr = json.toString();
		if (jsonStr == null) {
			Debug.logError("JSON Object was empty; fatal error!", module);
			response.setStatus(500);
			return;
		}

		// set the X-JSON content type
		response.setContentType("application/json");
		// jsonStr.length is not reliable for unicode characters
		try {
			response.setContentLength(jsonStr.getBytes("UTF8").length);
		} catch (UnsupportedEncodingException e) {
			Debug.logError("Problems with Json encoding: " + e, module);
		}

		// return the JSON String
		Writer out;
		try {
			response.setStatus(statusCode);
			out = response.getWriter();
			out.write(jsonStr);
			out.flush();
		} catch (IOException e) {
			response.setStatus(500);
			Debug.logError(e, module);
		}
	}
	
	public static Map<String, Object> buildObject(GenericValue object) {
		Set<String> keys = object.keySet();
		
		Map<String, Object> result = FastMap.newInstance();
		try {
			for(String key: keys) {
				System.out.println("Debug 22: ..." + key);
				Object element = object.get(key);
				if(element instanceof Date) {
					result.put(key, ((Date) element).getTime());
					continue;
				}
				result.put(key, element);
			}
		} catch (Exception e) {
			Debug.log(e.getMessage());
		}
		
		return result;
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
		  sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static Map<String, Object> httpPost(String sUrl, List<NameValuePair> data) throws IOException, JSONException {
		/**
		 * Todo
		 */
		
		Map<String, Object> reponse = new HashMap<String, Object>();
		
		URL url = new URL(sUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setReadTimeout(15000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
		
		
		reponse.put("statusCode", conn.getResponseCode());

		InputStream is = conn.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		String jsonText = readAll(rd);
		try {
			JSONObject json = JSONObject.fromObject(jsonText);
			reponse.put("reponse", json);
			
	    } catch (Exception ex) {
	    	reponse.put("reponse", jsonText);
	    	is.close();
	    } finally {
	    	is.close();
	    }
		return reponse;
	}
	
	public static Map<String, Object> httpGET(String sUrl) throws IOException {
		Map<String, Object> reponse = new HashMap<String, Object>();
		URL url = new URL(sUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		
		reponse.put("statusCode", conn.getResponseCode());

		InputStream is = conn.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		String jsonText = readAll(rd);
		reponse.put("reponse", jsonText);
		conn.disconnect();
    	is.close();
    	
		return reponse;
	}
	
	public static void quickSort (List<Map<String, Object>> list, int lowerIndex, int higherIndex, String field, String type){
		int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Map<String, Object> pivot = list.get(lowerIndex+(higherIndex-lowerIndex)/2);

        String key = getValueString(pivot, field);
        
        if(type.equals("asc")) {
        	while (i <= j) {
        		while (getValueString(list.get(i), field).compareTo(key) < 0) {
        			i++;
        		}
        		while (getValueString(list.get(j), field).compareTo(key) > 0) {
        			j--;
        		}
        		if (i <= j) {
        			Collections.swap(list, i, j);
        			//move index to next position on both sides
        			i++;
        			j--;
        		}
        	}
        } else {
        	while (i <= j) {
        		while (getValueString(list.get(i), field).compareTo(key) > 0) {
        			i++;
        		}
        		while (getValueString(list.get(j), field).compareTo(key) < 0) {
        			j--;
        		}
        		if (i <= j) {
        			Collections.swap(list, i, j);
        			//move index to next position on both sides
        			i++;
        			j--;
        		}
        	}
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
        	quickSort(list, lowerIndex, j, field, type);
        if (i < higherIndex)
        	quickSort(list, i, higherIndex, field, type);
	}
	
	public static String getValueString(Map<String, Object> map, String key) {
		String value = "";
		if(map.get(key)!= null) {
			value = (String) map.get(key);
		}
		return value;
	}
	
	public static double similarString(String string1, String string2) {
		int maxlengthString = Math.max(string1.length(), string2.length());
		int distance = editDistance(string1, string2);
		double similar = ((double)(maxlengthString - distance))/(double)maxlengthString;
//		if(similar > 0.8D) {
//			System.out.println("Do tuong dong maxlengthString = " + maxlengthString + ", distance = " + distance + ", similar = "  + similar);			
//		}
		return similar;
	}
	
	public static int editDistance(String string1, String string2) {
		String[] arrayString1 = string1.split("");
		String[] arrayString2 = string2.split("");
		
		int m = string1.length();
		int n = string2.length();
		int[][] d = new int[m+1][n+1];
		
		for(int i = 0; i <= m; ++i) {
			d[i][0] = i;
		}
		
		for(int j = 0; j <= n; ++j) {
			d[0][j] = j;
		}
		
		int cost;
		for(int i = 1; i <= m; ++i) {
			for(int j = 1; j <= n; ++j) {
				cost = 1;
				if(arrayString1[i].equals(arrayString2[j])) {
					cost = 0;
				}
				
				d[i][j] = Collections.min(Arrays.asList((d[i-1][j]+1), (d[i][j-1]+1), (d[i-1][j-1]+cost)));
				
			}
		}
		
		return d[m][n];
	}
	
	public static List<Map<String, Object>> clusterChord(List<Map<String, Object>> paperdistances) {
		List<Map<String, Object>> groups = new ArrayList<Map<String, Object>>();
		if(paperdistances == null || paperdistances.size() == 0) {
			return groups;
		}
		
		List<Map<String, Object>> clusters =  new ArrayList<Map<String,Object>>();
		//clusters.add(createCluster(paperdistances.get(0)));
		
		int index;
		int check;
		for(int i = 0; i < paperdistances.size(); ++i) {
			Map<String, Object> paperdistance = paperdistances.get(i);
			int paperId1 = Integer.valueOf((String)paperdistance.get("paperId1"));
			int paperId2 = Integer.valueOf((String)paperdistance.get("paperId2"));
			
			index = findCluster(clusters, paperdistance);
			if(index==-1) {
				paperdistance.put("cluster", clusters.size());
				clusters.add(createCluster(paperdistance));
			} else {
				Map<String, Object> cluster = clusters.get(index);
				List<Integer> nodes = (List<Integer>) cluster.get("nodes");
				
				check = nodes.indexOf(paperId1);
				if(check != -1) {
					nodes.add(paperId2);
					int maxKNode = Collections.max(nodes);
					cluster.put("maxKNode", maxKNode);
					paperdistance.put("cluster", index);
					
				} else {
					check = nodes.indexOf(paperId2);
					if(check != -1) {
						nodes.add(paperId1);
						int maxKNode = Collections.max(nodes);
						cluster.put("maxKNode", maxKNode);
						paperdistance.put("cluster", index);
						
					}					
				}
				
				
			}
			groups.add(paperdistance);
		}
		
//		for(Map<String, Object> cluster: clusters) {
//			List<String> group = new ArrayList<String>();
//			for(Integer paperId: (List<Integer>)cluster.get("nodes")) {
//				group.add(String.valueOf(paperId));
//			}
//			groups.add(group);
//		}
		
		return groups;
	}
	
	private static Map<String, Object> createCluster(Map<String, Object> paperdistance) {
		int paperId1 = Integer.valueOf((String)paperdistance.get("paperId1"));
		int paperId2 = Integer.valueOf((String)paperdistance.get("paperId2"));
		
		Map<String, Object> cluster = new HashMap<String, Object>();
		List<Integer> nodes = new ArrayList<Integer>();
		
		nodes.add(paperId1);
		nodes.add(paperId2);
		
		cluster.put("successor", paperId1);
		cluster.put("nodes", nodes);
		cluster.put("maxKNode", paperId2);
		
		return cluster;
	}
	
	private static int findCluster(List<Map<String, Object>> clusters, Map<String, Object> paperdistance) {
		
		int paperId1 = Integer.valueOf((String)paperdistance.get("paperId1"));
		int paperId2 = Integer.valueOf((String)paperdistance.get("paperId2"));
		int check;
		for(int i = 0; i < clusters.size(); ++i) {
			Map<String, Object> cluster = clusters.get(i);
			int successor = (int) cluster.get("successor");
			int maxKNode = (int) cluster.get("maxKNode");
			List<Integer> nodes = (List<Integer>) cluster.get("nodes");
			if(paperId1 >= successor && paperId1 <= maxKNode) {
				check = nodes.indexOf(paperId1);
				if(check != -1) {
					return i;
				}
			}
			
			if(paperId2 >= successor && paperId2 <= maxKNode) {
				check = nodes.indexOf(paperId2);
				if(check != -1) {
					return i;
				}
			}
		}
		
		return -1;
	}

}
