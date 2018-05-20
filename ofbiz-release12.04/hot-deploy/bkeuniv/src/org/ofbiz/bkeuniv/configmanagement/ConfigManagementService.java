package org.ofbiz.bkeuniv.configmanagement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

import javolution.util.FastList;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConfigManagementService {
	public static final String module = ConfigManagementService.class.getName();

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
}
