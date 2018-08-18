package org.ofbiz.bkeuniv.configmanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

























import javolution.util.FastList;
import javolution.util.FastMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.projectproposalsubmission.ProjectProposalSubmissionServiceUtil;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.utils.BKEunivUtils;

public class ConfigManagementService {
	public static final String module = ConfigManagementService.class.getName();
	
	public static Map<String, Object> pullStaff(DispatchContext dpct,Map<String,?extends Object> context) {
		Map<String, Object> result = ServiceUtil.returnSuccess();
		Delegator delegator = (Delegator) dpct.getDelegator();
		String publicURL = "http://service2.hust.edu.vn/api/canbo.php";
		try {
			Map<String, Object> reponse = BKEunivUtils.httpGET(publicURL);
			if((int)reponse.get("statusCode") != HttpURLConnection.HTTP_OK) {
				Debug.log("pullStaff error Status Code = " + reponse.get("statusCode"));
				return result;
			}
			
			JSONArray data = JSONArray.fromObject((String)reponse.get("reponse"));
				
			delegator.removeAll("StaffTemp");
			List<String> emailCheck = new ArrayList<String>();
			for(int i = 0; i < data.size(); ++i){
				JSONObject staffO = data.getJSONObject(i);
				
				GenericValue gv = delegator.makeValue("StaffTemp");

				JSONArray emails = staffO.getJSONArray("email");
				String email = null;
				for(int j = 0; j < emails.size(); ++j){
					String e = emails.getString(j);
					if(e.indexOf("@hust.edu.vn") != -1) {
						gv.put("staffEmail", e);
						email = e;
						break;
					}
				}
				
				if(email != null && !email.equals("")) {
					if(emailCheck.contains(email)) {
						continue;
					} else {
						emailCheck.add(email);
					}
				}
				
				gv.put("id", delegator.getNextSeqId("StaffTemp"));

				gv.put("shcc", staffO.get("shcc"));
				gv.put("sohieuchuan", staffO.get("sohieuchuan"));
				gv.put("staffName", staffO.get("hoten"));
				gv.put("departmentId", staffO.get("ma_bomon"));
				gv.put("departmentName", staffO.get("bomon"));
				gv.put("facultyId", staffO.get("ma_khoavien"));
				gv.put("facultyName", staffO.get("khoavien"));
				
				
				
				gv.put("gt", staffO.get("gt"));
				String ntns = staffO.getString("ntns");
				if(ntns != null && !ntns.equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = sdf.parse(ntns);
					
					gv.put("ntns", new java.sql.Date(date.getTime()));
				}
				
				delegator.create(gv);
			}
			
			result.put("statusCode", "200");
			result.put("message", "Đồng bộ thông tin thành công");
			
		} catch (JSONException | IOException | GenericEntityException | ParseException e) {
			// TODO Auto-generated catch block
			Debug.log("pullStaff error");
			result.put("statusCode", "404");
			result.put("message", "Lấy thông tin cập nhật không thành công");
			e.printStackTrace();
		}
		return result;
	}
	
	public static Map<String,Object> JQGetListSynchronizeStaff(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		List<GenericValue> listStaff = null;
		List<GenericValue> listStaffTemp = null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				String q = (String)parameters.get("q")[0].trim();
				String[] searchKeys = {"staffName", "departmentName", "facultyName", "staffEmail"}; 
				
				List<EntityCondition> condSearch = new ArrayList<EntityCondition>(); 
				for(String key: searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(key), EntityOperator.LIKE, EntityFunction.UPPER("%" + q + "%"));
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch, EntityOperator.OR));
			}
			if(filter != null) {
				listAllConditions.add(filter);				
			}
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
		 	
		 	listStaffTemp = delegator.findList("StaffTemp", condition, null, Arrays.asList("staffEmail"), opts, false);
			int iS = 0, iST = 0;
			listStaff = delegator.findList("StaffView", condition, null, Arrays.asList("staffUserLoginId"), opts, false);
			Debug.log("StaffTemp: " + listStaffTemp.size() + " ---- StaffView: " + listStaff.size());
			while(listStaff.size()>iS&&listStaffTemp.size()>iST) {
				GenericValue staff1 = listStaff.get(iS);
				GenericValue staff2 = listStaffTemp.get(iST);
				Map<String, Object> staff = new HashMap<String, Object>();
				
				String email1 = staff1.getString("staffUserLoginId");
				String email2 = staff2.getString("staffEmail");
				//email1 = email1.trim();
								
				if(email2 == null || email2.equals("")) {
					staff.put("staffName", staff2.get("staffName"));
					staff.put("departmentName", staff2.get("departmentName"));
					staff.put("facultyName", staff2.get("facultyName"));
					
					switch (staff2.getString("gt")) {
					case "0":
						staff.put("genderName", "Nữ");
						break;
					case "1":
						staff.put("genderName", "Nam");
						break;
					}
					staff.put("staffDateOfBirth", staff2.get("ntns"));
					iST++;
				} else {
					//email2 = email2.replaceAll("\\s","");
					email2 = email2.trim().replace("@hust.edu.vn", "");
					email1 = email1.trim().replace("@hust.edu.vn", "");

					
					if(email1.compareTo(email2) < 0) {
						staff.put("staffId", staff1.get("staffId"));
						staff.put("staffName", staff1.get("staffName"));
						staff.put("departmentName", staff1.get("departmentName"));
						staff.put("facultyName", staff1.get("facultyName"));
						staff.put("staffEmail", staff1.get("staffEmail"));
						staff.put("genderName", staff1.get("genderName"));
						staff.put("staffDateOfBirth", staff1.get("staffDateOfBirth"));
						
						iS++;
					} else {
						if(email1.compareTo(email2) > 0) {
							staff.put("staffName", staff2.get("staffName"));
							staff.put("departmentName", staff2.get("departmentName"));
							staff.put("facultyName", staff2.get("facultyName"));
							staff.put("staffEmail", staff2.get("staffEmail"));
							switch (staff2.getString("gt")) {
							case "0":
								staff.put("genderName", "Nữ");
								break;
							case "1":
								staff.put("genderName", "Nam");
								break;
							}
							staff.put("staffDateOfBirth", staff2.get("ntns"));
							iST++;
						} else {
							staff.put("staffName", staff2.get("staffName"));
							staff.put("departmentName", staff2.get("departmentName"));
							staff.put("facultyName", staff2.get("facultyName"));
							staff.put("staffEmail", staff2.get("staffEmail"));
							switch (staff2.getString("gt")) {
							case "0":
								staff.put("genderName", "Nữ");
								break;
							case "1":
								staff.put("genderName", "Nam");
								break;
							}
							staff.put("staffDateOfBirth", staff2.get("ntns"));
							iST++;
							
							Map<String, Object> old = new HashMap<String, Object>();
							old.put("staffId", staff1.get("staffId"));
							old.put("staffName", staff1.get("staffName"));
							old.put("departmentName", staff1.get("departmentName"));
							old.put("facultyName", staff1.get("facultyName"));
							old.put("staffEmail", staff1.get("staffEmail"));
							old.put("genderName", staff1.get("genderName"));
							old.put("staffDateOfBirth", staff1.get("staffDateOfBirth"));
							iS++;
							
							staff.put("old", old);
						}
					}
				}
				
				list.add(staff);
			}
			Debug.log("StaffTempCurr: " + iST + " ---- StaffViewCurr: " + iS);
			if(listStaff.size()>iS||listStaff.size()==0) {
				for(int k = iS; k < listStaff.size(); ++k) {
					GenericValue staff1 = listStaff.get(k);
					Map<String, Object> staff = new HashMap<String, Object>();
					staff.put("staffId", staff1.get("staffId"));
					staff.put("staffName", staff1.get("staffName"));
					staff.put("departmentName", staff1.get("departmentName"));
					staff.put("facultyName", staff1.get("facultyName"));
					staff.put("staffEmail", staff1.get("staffEmail"));
					staff.put("genderName", staff1.get("genderName"));
					staff.put("staffDateOfBirth", staff1.get("staffDateOfBirth"));
					
					list.add(staff);
				}
			}
			
			if(listStaffTemp.size()>iST||listStaffTemp.size()==0) {
				for(int k = iST; k < listStaffTemp.size(); ++k) {
					GenericValue staff2 = listStaffTemp.get(k);
					Map<String, Object> staff = new HashMap<String, Object>();
					
					staff.put("staffName", staff2.get("staffName"));
					staff.put("departmentName", staff2.get("departmentName"));
					staff.put("facultyName", staff2.get("facultyName"));
					staff.put("staffEmail", staff2.get("staffEmail"));
					switch (staff2.getString("gt")) {
					case "0":
						staff.put("genderName", "Nữ");
						break;
					case "1":
						staff.put("genderName", "Nam");
						break;
					}
					staff.put("staffDateOfBirth", staff2.get("ntns"));
					
					list.add(staff);
				}
			}
			
			JSONArray _sort = new JSONArray();
			if(parameters.get("sort") != null){
				_sort = JSONArray.fromObject(parameters.get("sort")[0]);
				if(_sort.size() > 0) {
					JSONObject s = (JSONObject) _sort.get(0);
					String field = s.getString("field");
					String type = s.getString("type");
					BKEunivUtils.quickSort(list, 0, list.size()-1, field, type);
					
				}
			}
			
			List<Map<String, Object>> listNew = new ArrayList<Map<String,Object>>();
			
			if(parameters.containsKey("category")) {
				String category = (String)parameters.get("category")[0].trim();
				
				switch (category) {
				case "new":
					for(int i = 0; i < list.size(); ++i) {
						Map<String, Object> temp = list.get(i);
						if(temp.get("old")==null&&temp.get("staffId")==null&&temp.get("staffEmail")!=null) {
							listNew.add(temp);
						}
					}
					break;
				case "update":
					for(int i = 0; i < list.size(); ++i) {
						Map<String, Object> temp = list.get(i);
						if(temp.get("old")!=null) {
							Map<String, Object> old = (Map<String, Object>) temp.get("old");
							if(!temp.get("staffName").equals(old.get("staffName")) ||
									!temp.get("departmentName").equals(old.get("departmentName"))||
									!temp.get("facultyName").equals(old.get("facultyName"))||
									!temp.get("genderName").equals(old.get("genderName"))||
									!temp.get("staffDateOfBirth").equals(old.get("staffDateOfBirth"))) {
								listNew.add(temp);								
							}
						}
					}
					break;
				case "unqualified":
					for(int i = 0; i < list.size(); ++i) {
						Map<String, Object> temp = list.get(i);
						if(temp.get("staffEmail")==null&&temp.get("staffId")==null) {
							listNew.add(temp);
						}
					}
					break;
				default:
					listNew = list;
				}
			} else {
				listNew = list;
			}
			
			
			result.put("listIterator", listNew);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error JQGetListSynchronizeStaff");
		}
		
		return result;
	}
	


	public static void performSynchronizeStaff(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		try {
			
			List<GenericValue> lst_staffs = delegator.findList("Staff", null,
					null, null, null, false);
			HashSet<String> emailDB = new HashSet<String>();
			for (GenericValue gv : lst_staffs) {
				String email = (String) gv.get("staffEmail");
				if (email != null && !email.equals(""))
					emailDB.add(email);
			}
			URL url = new URL("http://service2.hust.edu.vn/api/canbo.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			// String input = "{\"a\":5,\"b\":3}";
			Gson gson = new Gson();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				// throw new RuntimeException("Failed : HTTP error code : "
				// + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String rs = "";
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				rs += output;
			}

			// rs = "{\"staffs\":" + rs + "}";
			conn.disconnect();

			PrintWriter log = new PrintWriter("C:/tmp/canbo.txt");
			// ObjectMapper mapper = new ObjectMapper();
			// JsonObject obj = new JsonParser().parse(rs).getAsJsonObject();
			// JsonArray staffs = obj.getAsJsonArray("staffs");
			JsonArray staffs = new JsonParser().parse(rs).getAsJsonArray();
			HashSet<String> newEmail = new HashSet<String>();
			HashSet<String> ma_bomon_set = new HashSet<String>();
			HashSet<String> ma_khoavien_set = new HashSet<String>();
			HashMap<String, String> mCode2Name = new HashMap<String, String>();
			HashMap<String, String> mBomon2Faculty = new HashMap<String, String>();
			HashMap<String, String> mStaff2Bomon = new HashMap<String, String>();
			HashMap<String, Boolean> mStaff2Update = new HashMap<String, Boolean>();
			
			for (int i = 0; i < staffs.size(); i++) {
				JsonObject st = staffs.get(i).getAsJsonObject();
				String hoten = st.get("hoten").getAsString();
				String ngaysinh = st.get("ntns").getAsString();
				JsonArray emails = st.get("email").getAsJsonArray();
				String email = null;
				String gioitinh = st.get("gt").getAsString();
				if (gioitinh.equals("0"))
					gioitinh = "female";
				else
					gioitinh = "male";
				String sohieuchuan = st.get("sohieuchuan").getAsString();
				String ma_bomon = st.get("ma_bomon").getAsString();
				String ten_bomon = st.get("bomon").getAsString();
				String ma_khoavien = st.get("ma_khoavien").getAsString();
				String ten_khoavien = st.get("khoavien").getAsString();
				ma_bomon_set.add(ma_bomon);
				ma_khoavien_set.add(ma_khoavien);
				mCode2Name.put(ma_bomon, ten_bomon);
				mCode2Name.put(ma_khoavien, ten_khoavien);
				mBomon2Faculty.put(ma_bomon, ma_khoavien);
				mStaff2Bomon.put(sohieuchuan, ma_bomon);
				if (ma_bomon == null && ma_bomon.equals("")) {
					log.println("MA BO MON NULL cua can bo: " + sohieuchuan
							+ "\t" + hoten);
				}
				if (ma_khoavien == null && ma_khoavien.equals("")) {
					log.println("MA KHOA VIEN NULL cua can bo: " + sohieuchuan
							+ "\t" + hoten);
				}

				if (emails != null && emails.size() > 0) {
					for (int j = 0; j < emails.size(); j++) {
						String em = emails.get(j).getAsString();
						if (em.contains("@hust.edu.vn")) {
							newEmail.add(em);
							email = em;
							break;
						}
					}
				}
				System.out.println(sohieuchuan + "\t" + hoten + "\t" + email
						+ "\t" + ngaysinh + "\t" + gioitinh);
				log.println(sohieuchuan + "\t" + hoten + "\t" + email + "\t"
						+ ngaysinh + "\t" + gioitinh);
				
				
				if (email != null) {
					if(mStaff2Update.get(email) != null){
						log.println("DUPLICATE " + email + "\t" + hoten);
						continue;
					}
					mStaff2Update.put(email, true);
					
					List<EntityCondition> conds = FastList.newInstance();
					conds.add(EntityCondition
							.makeCondition("staffId", email));
					List<GenericValue> lst = delegator.findList("Staff",
							EntityCondition.makeCondition(conds), null, null,
							null, false);

					if (lst != null && lst.size() > 0) {
						// EXIST --> UPDATE
						GenericValue stf = lst.get(0);
						stf.put("staffCode", sohieuchuan);
						stf.put("staffName", hoten);
						stf.put("staffEmail", email);
						stf.put("departmentId", ma_bomon);
						stf.put("staffGenderId", gioitinh);
						SimpleDateFormat sdf1 = new SimpleDateFormat(
								"yyyy-MM-dd");
						java.util.Date date = sdf1.parse(ngaysinh);
						java.sql.Date ngaysinh_date = new java.sql.Date(
								date.getTime());
						stf.put("staffDateOfBirth", ngaysinh_date);

						delegator.store(stf);
						
						System.out.println("UPDATE " + email);
						log.println("UPDATE " + email);
					} else {
						// NOT EXIST --> CREATE NEW
						GenericValue u = delegator.findOne("UserLogin",
								UtilMisc.toMap("userLoginId", email), false);
						if (u == null) {
							u = delegator.makeValue("UserLogin");
							u.put("userLoginId", email);
							u.put("currentPassword",
									"{SHA}47ca69ebb4bdc9ae0adec130880165d2cc05db1a");// default password = ofbiz
							u.put("enabled", "Y");
							delegator.create(u);
						}

						GenericValue stf = delegator.makeValue("Staff");
						stf.put("staffId", email);
						stf.put("staffCode", sohieuchuan);
						stf.put("staffName", hoten);
						stf.put("staffEmail", email);
						stf.put("staffUserLoginId", email);
						stf.put("departmentId", ma_bomon);
						stf.put("staffGenderId", gioitinh);
						SimpleDateFormat sdf1 = new SimpleDateFormat(
								"yyyy-MM-dd");
						java.util.Date date = sdf1.parse(ngaysinh);
						java.sql.Date ngaysinh_date = new java.sql.Date(
								date.getTime());
						stf.put("staffDateOfBirth", ngaysinh_date);
						delegator.create(stf);
						
						System.out.println("CREATE NEW " + email);
						log.println("CREATE NEW " + email);
					}
				}
				
			}
			for (String ma_khoavien : ma_khoavien_set) {
				GenericValue kv = delegator.findOne("Faculty",
						UtilMisc.toMap("facultyId", ma_khoavien), false);
				if (kv == null) {
					kv = delegator.makeValue("Faculty");
					kv.put("facultyId", ma_khoavien);
					kv.put("universityId", "HUST");
					kv.put("facultyName", mCode2Name.get(ma_khoavien));
					kv.put("statusId", "UPDATED");
					delegator.create(kv);
				} else {
					kv.put("statusId", "UPDATED");
					kv.put("facultyName", mCode2Name.get(ma_khoavien));
					delegator.store(kv);
				}
			}
			for (String ma_bomon : ma_bomon_set) {
				GenericValue bm = delegator.findOne("Department",
						UtilMisc.toMap("departmentId", ma_bomon), false);
				if (bm == null) {
					bm = delegator.makeValue("Department");
					bm.put("departmentId", ma_bomon);
					bm.put("facultyId", mBomon2Faculty.get(ma_bomon));
					bm.put("departmentName", mCode2Name.get(ma_bomon));
					bm.put("statusId", "UPDATED");
					delegator.create(bm);
				} else {
					bm.put("facultyId", mBomon2Faculty.get(ma_bomon));
					bm.put("departmentName", mCode2Name.get(ma_bomon));
					bm.put("statusId", "UPDATED");

					delegator.store(bm);
				}
			}

			for (String e : emailDB) {
				if (!newEmail.contains(e)) {
					log.println("NOT: " + e);
				}
			}
			for (String e : newEmail) {
				if (!emailDB.contains(e)) {
					log.println("NEW: " + e);
				}
			}
			log.println("OLD: " + emailDB.size() + "\t" + "NEW: "
					+ newEmail.size());
			log.close();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			System.out.println("RETURN json = " + rs);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Map<String, Object> mergeSynchronizeStaff(DispatchContext dpct,Map<String,?extends Object> context) {
		Map<String, Object> result = ServiceUtil.returnSuccess();
		Delegator delegator = (Delegator) dpct.getDelegator();
		
		Map<String, Object> list_khoavien = new HashMap<String, Object>();
		
		try {
			List<GenericValue> listStaffTemp = delegator.findList("StaffTemp", EntityCondition
					.makeCondition("staffEmail", EntityOperator.NOT_EQUAL, null), null, null, null, false);
			
			for(GenericValue staffTemp: listStaffTemp) {
				String email =  staffTemp.getString("staffEmail");
				List<GenericValue> lStaff = delegator.findList("Staff", EntityCondition
						.makeCondition("staffId", EntityOperator.EQUALS, email), null, null, null, false);
				String gioitinh = "";
				if (staffTemp.get("gt").equals("0"))
					gioitinh = "female";
				else
					gioitinh = "male";
				
				if (lStaff != null && lStaff.size() > 0) {
					// EXIST --> UPDATE
					GenericValue stf = lStaff.get(0);
					
					
					stf.put("staffCode", staffTemp.get("sohieuchuan"));
					stf.put("staffName", staffTemp.get("staffName"));
					stf.put("staffEmail", email);
					stf.put("departmentId", staffTemp.get("departmentId"));
					stf.put("staffGenderId", gioitinh);
					
					stf.put("staffDateOfBirth", staffTemp.get("ntns"));

					delegator.store(stf);
					
					System.out.println("UPDATE " + email);
				} else {
					// NOT EXIST --> CREATE NEW
					GenericValue u = delegator.findOne("UserLogin",
							UtilMisc.toMap("userLoginId", email), false);
					if (u == null) {
						u = delegator.makeValue("UserLogin");
						u.put("userLoginId", email);
						u.put("currentPassword",
								"{SHA}47ca69ebb4bdc9ae0adec130880165d2cc05db1a");// default password = ofbiz
						u.put("enabled", "Y");
						delegator.create(u);
					}

					GenericValue stf = delegator.makeValue("Staff");
					stf.put("staffId", email);
					stf.put("staffCode", staffTemp.get("sohieuchuan"));
					stf.put("staffName", staffTemp.get("staffName"));
					stf.put("staffEmail", email);
					stf.put("staffUserLoginId", email);
					stf.put("departmentId", staffTemp.get("departmentId"));
					stf.put("staffGenderId", gioitinh);
					
					stf.put("staffDateOfBirth", staffTemp.get("ntns"));
					delegator.create(stf);
					
					System.out.println("CREATE NEW " + email);
				}
				
				
				String ma_bomon = staffTemp.getString("departmentId");
				String bomon = staffTemp.getString("departmentName");
				String ma_khoavien = staffTemp.getString("facultyId");
				String khoavien = staffTemp.getString("facultyName");
				
				if (ma_khoavien != null && !ma_khoavien.equals("")&&
						khoavien != null && !khoavien.equals("")) {
					if(!list_khoavien.containsKey(ma_khoavien)) {
						Map<String, Object> kv = new HashMap<String, Object>();
						kv.put("facultyId", ma_khoavien);
						kv.put("facultyName", khoavien);
						kv.put("bm", new HashMap<String, String>());
						list_khoavien.put(ma_khoavien, kv);
					}
					
					Map<String, Object> kv = (Map<String, Object>) list_khoavien.get(ma_khoavien);
					
					if (ma_bomon != null && !ma_bomon.equals("")&&
							bomon != null && !bomon.equals("")) {
						Map<String, String> bm = (Map<String, String>) kv.get("bm");
						if(!bm.containsKey(ma_bomon)) {
							bm.put(ma_bomon, bomon);
						}
					}
				}
			}
			
			for (String ma_kv : list_khoavien.keySet()) {
				Map<String, Object> kv = (Map<String, Object>) list_khoavien.get(ma_kv);
				
				GenericValue khoavien = delegator.findOne("Faculty",
						UtilMisc.toMap("facultyId", kv.get("facultyId")), false);
				if (khoavien == null) {
					khoavien = delegator.makeValue("Faculty");
					khoavien.put("facultyId", kv.get("facultyId"));
					khoavien.put("universityId", "HUST");
					khoavien.put("facultyName", kv.get("facultyName"));
					khoavien.put("statusId", "UPDATED");
					delegator.create(khoavien);
				} else {
					khoavien.put("statusId", "UPDATED");
					khoavien.put("facultyName", kv.get("facultyName"));
					delegator.store(khoavien);
				}
				
				Map<String, String> bm = (Map<String, String>) kv.get("bm");
				
				for (String ma_bomon : bm.keySet()) {
					GenericValue bomon = delegator.findOne("Department",
							UtilMisc.toMap("departmentId", ma_bomon), false);
					if (bomon == null) {
						bomon = delegator.makeValue("Department");
						bomon.put("departmentId", ma_bomon);
						bomon.put("facultyId", kv.get("facultyId"));
						bomon.put("departmentName", bm.get(ma_bomon));
						bomon.put("statusId", "UPDATED");
						delegator.create(bomon);
					} else {
						bomon.put("facultyId", kv.get("facultyId"));
						bomon.put("departmentName", bm.get(ma_bomon));
						bomon.put("statusId", "UPDATED");

						delegator.store(bomon);
					}
				}
			}
			delegator.removeAll("StaffTemp");
			result.put("statusCode", "200");
			result.put("message", "Cập nhật thông tin thành công");
			
			
		} catch (GenericEntityException e1) {
			result.put("statusCode", "500");
			result.put("message", "Cập nhật thông tin thất bại");
			e1.printStackTrace();
		}
		
		return result;
	}
}
