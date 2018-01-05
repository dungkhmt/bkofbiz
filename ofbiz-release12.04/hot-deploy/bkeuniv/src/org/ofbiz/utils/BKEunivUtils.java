package org.ofbiz.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;

public class BKEunivUtils {

	public static final String module = BKEunivUtils.class.getName();

	public static List<GenericValue> getListDepartmentsOfFaculty(Delegator delegator,
			String facultyId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("facultyId",EntityOperator.EQUALS,facultyId));
			
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

}
