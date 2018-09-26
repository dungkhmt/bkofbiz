package org.ofbiz.bkeuniv.paperdeclaration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringEscapeUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.config.ConfigParams;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;

//import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.dev.ReSave;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ofbiz.utils.BKEunivUtils;







import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

public class PaperDeclarationService {
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final Charset ISO = Charset.forName("ISO-8859-1");
	
	public static String module = PaperDeclarationService.class.getName();
	//public static String dataFolder = "." + File.separator + "euniv-deploy";

	public static String establishFullFilename(String staffId, String name) {
		
		String path = ConfigParams.dataFolder + File.separator + staffId + File.separator
				+ "papers";
		System.out.println("\n\n\t****************************************\n\t"
				+ path + "+\n\t");
		String fullname = path + File.separator + name;

		File file = new File(path);

		if (!file.exists()) {

			file.mkdirs();
			System.out
					.println("\n\n\t****************************************\n\tCreate folder\n\t");
			// If you require it to make the entire directory path including
			// parents,
			// use directory.mkdirs(); here instead.
		}

		return fullname;
	}

	@SuppressWarnings({ "unchecked" })
	public static void exportExcelKV01(HttpServletRequest request,
			HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String year = (String) request.getParameter("reportyear-kv01");
		String facultyId = (String) request.getParameter("facultyId-kv01");
		
		Debug.log(module + "::exportExcelKV01, academic year = " + year);

		String filename = "KV01";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			HSSFWorkbook wb = PaperDeclarationUtil.createExcelFormKV01(
					delegator, year, facultyId);

			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void exportExcel01CN02CN(HttpServletRequest request,
			HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
		String staffId = (String) request.getParameter("staff-01cn-02cn");
		String academicYearId = (String) request.getParameter("reportyear-bm-01-02-03");
		//String facultyId = (String) request.getParameter("facultyId-kv01");
		Debug.log(module + "::exportExcel01CN02CN, academic year = " + academicYearId + ", userLoginId = " + staffId);

		String filename = staffId + "-01CN-02CN";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			HSSFWorkbook wb = PaperDeclarationUtil.createExcelForm01CN02CN(
					delegator, academicYearId, staffId);

			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void exportExcelKV04(HttpServletRequest request,
			HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String year = (String) request.getParameter("reportyear-kv04");
		String facultyId = (String) request.getParameter("facultyId-kv04");
		Debug.log(module + "::exportExcelKV04, academic year = " + year);

		String filename = "KNC-" + year;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			//HSSFWorkbook wb = PaperDeclarationUtil.createExcelFormKV04(delegator, year, facultyId);
			HSSFWorkbook wb = PaperDeclarationUtil.createExcelFormKNC(delegator, year, facultyId);
			
			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void exportExcelISI(HttpServletRequest request,
			HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String year = (String) request.getParameter("reportyear-isi");
		String facultyId = (String) request.getParameter("facultyId-isi");
		Debug.log(module + "::exportExcelISI, academic year = " + year);

		String filename = "ISI";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			HSSFWorkbook wb = PaperDeclarationUtil.createExcelFormISI(
					delegator, year, facultyId);

			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void exportExcelBM010203(HttpServletRequest request,
			HttpServletResponse response) {

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String year = (String) request.getParameter("reportyear-bm-01-02-03");
		String facultyId = (String) request
				.getParameter("facultyId-bm-01-02-03");
		String departmentId = (String) request
				.getParameter("departmentId-bm-01-02-03");
		Debug.log(module + "::exportExcelBM010203, academic year = " + year
				+ ", faculty = " + facultyId + ", department = " + departmentId);
		
		GenericValue dept = PaperDeclarationUtil.getDepartment(delegator, departmentId);
		String deptName = "";
		if(dept != null && dept.getString("departmentName") != null)
			deptName = dept.getString("departmentName");
		
		String filename = "BM010203-" + deptName;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			HSSFWorkbook wb = PaperDeclarationUtil.createExcelFormBM010203(
					delegator, year, facultyId, departmentId);

			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void getStaffs(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		// String paperId = request.getParameter("id-paper");
		try {
			List<GenericValue> staffs = delegator.findList("Staff", null, null,
					null, null, false);
			String rs = "{\"staffs\":[";
			for (int i = 0; i < staffs.size(); i++) {
				GenericValue st = staffs.get(i);
				rs += "{\"id\":\"" + st.get("staffId") + "\",\"name\":\""
						+ st.get("staffName") + "\"}";
				if (i < staffs.size() - 1)
					rs += ",";

				Debug.log(module + "::getStaffsOfPaper, staffs "
						+ st.get("staffId"));
			}
			rs += "]}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			// response.getOutputStream().print(rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void approveAPaperDeclaration(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			String paperId = request.getParameter("paperId");
			String staffId = request.getParameter("staffId");
			Debug.log(module + "::approveAPaperDeclaration, paperId = "
					+ paperId + ", staffId = " + staffId);
			PaperDeclarationUtil.approveAPaperDeclaration(delegator, paperId,
					staffId);

			String rs = "{\"status\":\"OK\"}";
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
	public static void rejectAPaperDeclaration(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			String paperId = request.getParameter("paperId");
			String staffId = request.getParameter("staffId");
			Debug.log(module + "::approveAPaperDeclaration, paperId = "
					+ paperId + ", staffId = " + staffId);
			PaperDeclarationUtil.rejectAPaperDeclaration(delegator, paperId,
					staffId);

			String rs = "{\"status\":\"OK\"}";
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
	public static void updateStaffsOfPaper(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("paperId");
		String str_staffIds = (String) request.getParameter("staffsId");
		String[] staffIds = str_staffIds.split(",");
		HashSet<String> set_new_staffs = new HashSet<String>();
		for (String s : staffIds)
			set_new_staffs.add(s);

		Debug.log(module + "::updateStaffsOfPaper, paperId = " + paperId
				+ ", str_staffIds = " + str_staffIds);

		try {
			List<GenericValue> staffOfPapers = PaperDeclarationUtil
					.getStaffsOfPaper(paperId, delegator);
			Set<String> set_staffs = FastSet.newInstance();
			for (GenericValue gv : staffOfPapers) {
				String st = (String) gv.get("staffId");
				set_staffs.add(st);
			}
			Set<String> addedStaffs = FastSet.newInstance();
			Set<String> removedStaffs = FastSet.newInstance();

			for (String s : set_new_staffs) {
				if (!set_staffs.contains(s)) {
					addedStaffs.add(s);
				}
			}
			for (String s : set_staffs) {
				if (!set_new_staffs.contains(s)) {
					removedStaffs.add(s);
				}
			}

			for (String staffId : addedStaffs) {
				PaperDeclarationUtil.createStaffPaperDeclarationc(paperId,
						staffId, null, delegator);
			}
			for (String staffId : removedStaffs) {
				PaperDeclarationUtil.deleteStaffPaperDeclaration(staffId,
						paperId, delegator);
			}

			String rs = "{\"status\":\"OK\"}";
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
	public static void getStaffsOfPaper(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("paperId");
		Debug.log(module + "::getStaffsOfPaper, paperId = " + paperId);
		try {
			
			Map<String, String> mID2Name = FastMap.newInstance();
			Map<String, String> mRoleID2Name = FastMap.newInstance();
			
			List<GenericValue> roles = delegator.findList("StaffPaperDeclarationRole",
					null, null, null, null, false);
			for(GenericValue r: roles){
				mRoleID2Name.put(r.getString("roleId"), r.getString("roleName"));
			}
			
			List<GenericValue> faculties = delegator.findList("Faculty",null,null,null,null,false);
			
			List<GenericValue> staffs = delegator.findList("Staff", null, null,
					null, null, false);

			/*
			 * List<EntityCondition> conds = FastList.newInstance();
			 * conds.add(EntityCondition
			 * .makeCondition("paperId",EntityOperator.EQUALS,paperId));
			 * conds.add
			 * (EntityCondition.makeCondition("statusId",EntityOperator.
			 * EQUALS,STATUS_ENABLED));
			 * 
			 * List<GenericValue> staffsOfPaper =
			 * delegator.findList("StaffPaperDeclaration",
			 * EntityCondition.makeCondition(conds), null, null, null, false);
			 */
			List<GenericValue> staffsOfPaper = PaperDeclarationUtil
					.getStaffsOfPaper(paperId, delegator);

			String rs = "{\"staffs\":[";
			for (int i = 0; i < staffs.size(); i++) {
				GenericValue st = staffs.get(i);
				String id = (String) st.get("staffId");
				String name = (String) st.get("staffName");
				mID2Name.put(id, name);

				rs += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
				if (i < staffs.size() - 1)
					rs += ",";

				// Debug.log(module + "::getStaffsOfPaper, staffs " +
				// st.get("staffId"));
			}
			rs += "]";

			rs += ",\"staffsofpaper\":[";
			if (staffsOfPaper != null) {
				for (int i = 0; i < staffsOfPaper.size(); i++) {
					GenericValue st = staffsOfPaper.get(i);
					String id = (String) st.get("staffId");
					String name = mID2Name.get(id);
					String role = "";
					if(st.getString("roleId") != null)
						role = mRoleID2Name.get(st.getString("roleId"));
					rs += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"role\":\"" + role + "\"}";

					if (i < staffsOfPaper.size() - 1)
						rs += ",";

					Debug.log(module + "::getStaffsOfPaper, staffs " + name);
				}
			}
			rs += "]";
			
			// faculties
			rs += ",\"faculties\":[";
			for(int i = 0; i < faculties.size(); i++){
				GenericValue f = faculties.get(i);
				rs += "{\"id\":\"" + f.getString("facultyId") + "\",\"name\":\"" + f.getString("facultyName") + "\"}";

				if (i < faculties.size() - 1)
					rs += ",";
				
			}
			rs += "]";
			
			rs += "}";

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			// response.getOutputStream().print(rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("id-paper");

		// String filename = "HDSD.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			GenericValue gv = delegator.findOne("PaperDeclaration", false,
					UtilMisc.toMap("paperId", paperId));
			String staffId = (String) gv.get("staffId");
			String filenameDB = (String) gv.get("sourcePath");
			String fullFileName = establishFullFilename(staffId, filenameDB);

			Debug.log(module + "::downloadFile, id-paper = " + paperId
					+ ", staffId = " + staffId + ", filenameDB = " + filenameDB
					+ ", fullFileName = " + fullFileName);

			// File f = new File("C:/DungPQ/olbius/tmp/" + filename);
			File f = new File(fullFileName);
			FileInputStream fi = new FileInputStream(f);
			byte[] bytes = new byte[(int) f.length()];
			fi.read(bytes);

			response.setHeader("content-disposition", "attachment;filename="
					+ filenameDB);
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getExtension(String fn) {
		Debug.log(module + "::getExtension, fn = " + fn);
		String[] s = fn.split("\\.");
		Debug.log(module + "::getExtension, fn = " + fn + ", s.length = "
				+ s.length);
		return s[s.length - 1];
	}

	public static void uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> m = FastMap.newInstance();

		// ServletFileUpload fu = new ServletFileUpload(new
		// DiskFileItemFactory(10240, new File(new File("runtime"), "tmp")));
		// //Creation of servletfileupload
		System.out
				.println("\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - start\n\t");
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory()); // Creation
																					// of
																					// servletfileupload
		List lst = null;
		String result = "AttachementException";
		String file_name = "";
		String paperId = "";

		try {
			lst = fu.parseRequest(request);
		} catch (FileUploadException fup_ex) {
			System.out
					.println("\n\n\t****************************************\n\tException of FileUploadException \n\t");
			fup_ex.printStackTrace();
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		if (lst.size() == 0) // There is no item in lst
		{
			System.out
					.println("\n\n\t****************************************\n\tLst count is 0 \n\t");
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		FileItem file_item = null;
		FileItem selected_file_item = null;

		// Checking for form fields - Start
		for (int i = 0; i < lst.size(); i++) {
			file_item = (FileItem) lst.get(i);
			String fieldName = file_item.getFieldName();

			switch (fieldName) {
			case "file":
				selected_file_item = file_item;

				file_name = file_item.getName(); // Getting the file name
				System.out
						.println("\n\n\t****************************************\n\tThe selected file item's file name is : "
								+ file_name + "\n\t");
				break;
			case "paperId":
				paperId = file_item.getString();
				System.out
						.println("\n\n\t****************************************\n\tPaper id : "
								+ paperId + "\n\t");
				break;
			}

		}
		// Checking for form fields - End

		// Uploading the file content - Start
		if (selected_file_item == null) // If selected file item is null
		{
			System.out
					.println("\n\n\t****************************************\n\tThe selected file item is null\n\t");
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		byte[] file_bytes = selected_file_item.get();
		byte[] extract_bytes = new byte[file_bytes.length];

		for (int l = 0; l < file_bytes.length; l++)
			extract_bytes[l] = file_bytes[l];
		// ByteBuffer byteWrap=ByteBuffer.wrap(file_bytes);
		// byte[] extract_bytes;
		// byteWrap.get(extract_bytes);

		// System.out.println("\n\n\t****************************************\n\tExtract succeeded :content are : \n\t");

		// Creation & writing to the file in server - End

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Debug.log(module + "::uploadFile, paperId = " + paperId);
		try {
			GenericValue gv = delegator.findOne("PaperDeclaration", false,
					UtilMisc.toMap("paperId", paperId));
			String staffId = (String) gv.get("staffId");

			Debug.log(module + "::uploadFile, filename = " + file_name
					+ ", paperName = " + (String) gv.get("paperName")
					+ ", staffId = " + staffId);
			String ext = getExtension(file_name);
			java.util.Date currentDate = new java.util.Date();
			//SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("HHmmssddMMyyyy");
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			
			String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

			String filenameDB = sCurrentDate + "." + ext;
			String fullFileName = establishFullFilename(staffId, filenameDB);

			Debug.log(module + "::uploadFile, filename = " + file_name
					+ ", paperId = " + paperId + ", extension = " + ext
					+ ", filenameDB = " + filenameDB + ", fullFileName = "
					+ fullFileName);

			FileOutputStream fout = new FileOutputStream(fullFileName);
			System.out
					.println("\n\n\t****************************************\n\tAfter creating outputstream");
			fout.flush();
			fout.write(extract_bytes);
			fout.flush();
			fout.close();

			gv.put("sourcePath", filenameDB);
			delegator.store(gv);

			System.out
					.println("\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - end\n\t");
			m.put("result", "AttachementSuccess");
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);

		} catch (Exception ioe_ex) {
			System.out
					.println("\n\n\t****************************************\n\tIOException occured on file writing");
			ioe_ex.printStackTrace();
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

	}

	/*
	 * public static String uploadFile(HttpServletRequest request,
	 * HttpServletResponse response) { // ServletFileUpload fu = new
	 * ServletFileUpload(new // DiskFileItemFactory(10240, new File(new
	 * File("runtime"), "tmp"))); // //Creation of servletfileupload System.out
	 * .println(
	 * "\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - start\n\t"
	 * ); ServletFileUpload fu = new ServletFileUpload(new
	 * DiskFileItemFactory()); // Creation // of // servletfileupload
	 * java.util.List lst = null; String result = "AttachementException"; String
	 * file_name = ""; try { lst = fu.parseRequest(request); } catch
	 * (FileUploadException fup_ex) { System.out .println(
	 * "\n\n\t****************************************\n\tException of FileUploadException \n\t"
	 * ); fup_ex.printStackTrace(); result = "AttachementException"; return
	 * (result); }
	 * 
	 * if (lst.size() == 0) // There is no item in lst { System.out .println(
	 * "\n\n\t****************************************\n\tLst count is 0 \n\t");
	 * result = "AttachementException"; return (result); }
	 * 
	 * FileItem file_item = null; FileItem selected_file_item = null;
	 * 
	 * // Checking for form fields - Start for (int i = 0; i < lst.size(); i++)
	 * { file_item = (FileItem) lst.get(i); String fieldName =
	 * file_item.getFieldName();
	 * 
	 * // Check for the attributes for user selected file - Start if
	 * (fieldName.equals("filename")) { selected_file_item = file_item; //
	 * String file_name=file_item.getString(); //
	 * file_name=request.getParameter("filename"); file_name =
	 * file_item.getName(); // Getting the file name System.out .println(
	 * "\n\n\t****************************************\n\tThe selected file item's file name is : "
	 * + file_name + "\n\t"); break; } // Check for the attributes for user
	 * selected file - End } // Checking for form fields - End
	 * 
	 * // Uploading the file content - Start if (selected_file_item == null) //
	 * If selected file item is null { System.out .println(
	 * "\n\n\t****************************************\n\tThe selected file item is null\n\t"
	 * ); result = "AttachementException"; return (result); }
	 * 
	 * byte[] file_bytes = selected_file_item.get(); byte[] extract_bytes = new
	 * byte[file_bytes.length];
	 * 
	 * for (int l = 0; l < file_bytes.length; l++) extract_bytes[l] =
	 * file_bytes[l]; // ByteBuffer byteWrap=ByteBuffer.wrap(file_bytes); //
	 * byte[] extract_bytes; // byteWrap.get(extract_bytes);
	 * 
	 * // System.out.println(
	 * "\n\n\t****************************************\n\tExtract succeeded :content are : \n\t"
	 * );
	 * 
	 * if (extract_bytes == null) { System.out .println(
	 * "\n\n\t****************************************\n\tExtract bytes is null\n\t"
	 * ); result = "AttachementException"; return (result); }
	 * 
	 * 
	 * // String target_file_name="/hot-deploy/productionmgntSystem" // Creation
	 * & writing to the file in server - Start try { file_name = "C:/tmp/" +
	 * file_name;
	 * 
	 * FileOutputStream fout = new FileOutputStream(file_name); System.out
	 * .println(
	 * "\n\n\t****************************************\n\tAfter creating outputstream, file_name = "
	 * + file_name); fout.flush(); fout.write(extract_bytes); fout.flush();
	 * fout.close(); } catch (Exception ioe_ex) { System.out .println(
	 * "\n\n\t****************************************\n\tIOException occured on file writing"
	 * ); ioe_ex.printStackTrace(); result = "AttachementException"; return
	 * (result); } // Creation & writing to the file in server - End
	 * 
	 * System.out .println(
	 * "\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - end\n\t"
	 * ); return ("AttachementSuccess"); // Uploading the file content - End }
	 */

	public static Map<String, Object> getPapersOfStaff(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String authoStaffId = (String) context.get("authorStaffId");

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");
		// String userLoginId =
		// (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = authoStaffId;
		if (staffId == null)
			staffId = (String) userLogin.get("userLoginId");

		Debug.log(module + "::getPapersOfStaff, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("authorStaffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			conds.add(EntityCondition.makeCondition("statusStaffPaper",
			EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			//conds.add(EntityCondition.makeCondition("approveStatusId",
			//		EntityOperator.NOT_EQUAL, PaperDeclarationUtil.STATUS_CANCELLED));

			List<GenericValue> papers = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			
			List<GenericValue> ret_papers = FastList.newInstance();
			for(GenericValue p: papers){
				if(p.get("approveStatusId") == null || !p.getString("approveStatusId").equals(PaperDeclarationUtil.STATUS_CANCELLED)){
					ret_papers.add(p);
				}
			}
			
			for (GenericValue gv : ret_papers) {
				Debug.log(module + "::getPapersOfStaff, paper "
						+ gv.get("paperName"));
			}
			Debug.log(module + "::getPapersOfStaff, papers.sz = "
					+ ret_papers.size());
			retSucc.put("papers", ret_papers);

		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getAPaperDeclaration(DispatchContext ctx,
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String paperId = (String)context.get("paperId");
		try{
			GenericValue p = delegator.findOne("PaperView", UtilMisc.toMap("paperId", paperId), false);
			retSucc.put("paper", p);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getMembersPaperDeclaration(DispatchContext ctx,
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		String paperId = (String)context.get("paperId");
		try{
			List<String> _sort = new ArrayList<String>();;
			_sort.add("sequence");
			
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));
			
			List<GenericValue> staffPaperDeclaration  = delegator.findList("StaffPaperDeclarationView", EntityCondition.makeCondition(conds, EntityOperator.AND), null, _sort, null, false);
			List<GenericValue> externalMemberPaperDeclaration  = delegator.findList("ExternalMemberPaperDeclaration", EntityCondition.makeCondition(conds, EntityOperator.AND), null, _sort, null, false);
			retSucc.put("staffPaperDeclaration", staffPaperDeclaration);
			retSucc.put("externalMemberPaperDeclaration", externalMemberPaperDeclaration);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getPaperDeclarations(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		// String staffId = (String)context.get("authorStaffId");

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");
		// String userLoginId =
		// (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String) userLogin.get("userLoginId");

		String facultyId = (String)context.get("facultyId");
		String academicYearId = (String)context.get("academicYearId");
		String paperCategoryId = (String)context.get("paperCategoryId");
		String paperDeclarationStatusId = (String)context.get("paperDeclarationStatusId");
		
		Debug.log(module + "::getPaperDeclarations, authorStaffId = " + staffId + 
				", facultyId = " + facultyId + ", academicYearId = " + academicYearId + ", paperCategoryId = "
				+ paperCategoryId + ", paperDeclarationStatusId = " + paperDeclarationStatusId);
		
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			// conds.add(EntityCondition.makeCondition("authorStaffId",
			// EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			if(academicYearId != null && !academicYearId.equals("all"))
				conds.add(EntityCondition.makeCondition("academicYearId",
						EntityOperator.EQUALS, academicYearId));

			if(paperCategoryId != null && !paperCategoryId.equals("all"))
				conds.add(EntityCondition.makeCondition("paperCategoryId",
						EntityOperator.EQUALS, paperCategoryId));

			if(paperDeclarationStatusId != null && !paperDeclarationStatusId.equals("all"))
				conds.add(EntityCondition.makeCondition("approveStatusId",
						EntityOperator.EQUALS, paperDeclarationStatusId));
			
			
			
			// List<GenericValue> papers = delegator.findList("PapersStaffView",
			List<GenericValue> papers = delegator.findList("PaperView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			
			HashSet<String> setStaffId = new HashSet<String>();
			if(facultyId != null && !facultyId.equals("all")){
				List<GenericValue> staffsOfFaculty = PaperDeclarationUtil.getListStaffsOfFaculty(delegator, facultyId);
				for(GenericValue st: staffsOfFaculty)
					setStaffId.add((String)st.getString("staffId"));
			}
			Debug.log(module + "::getPaperDeclarations, staff of selected faculty = " + setStaffId.size());	
			List<GenericValue> retList = FastList.newInstance();
			for (GenericValue gv : papers) {
				Debug.log(module + "::getPaperDeclarations, paper "
						+ gv.get("paperName"));
				
				boolean ok = true;
				if(facultyId != null && !facultyId.equals("all")){
					String paperId = (String)gv.getString("paperId");
					List<GenericValue> ST = PaperDeclarationUtil.getStaffsOfPaper(paperId, delegator);
					ok = false;
					for(GenericValue st: ST){
						String stId = (String)st.getString("staffId");
						if(setStaffId.contains(stId)){
							ok = true; break;
						}
					}
					
				}
				if(ok) retList.add(gv);
				
			}
			Debug.log(module + "::getPaperDeclarations, papers.sz = "
					+ papers.size() + ", retList = " + retList.size());
			//retSucc.put("papers", papers);
			retSucc.put("papers", retList);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> JQGetPaperDeclarations(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = (Delegator) ctx.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		JSONObject filterJS = null;
		String facultyId = null;
		if(parameters.get("facultyId") != null){
			facultyId = parameters.get("facultyId")[0];
		}
		
		Map<String,Object> result = FastMap.newInstance();
		List<GenericValue> papers = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0].trim();
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"staffName", "paperCategoryName", "researchProjectProposalName", "paperDeclarationStatusName", "researchProjectProposalCode", "paperName", "journalConferenceName"}; 
				
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
			
			listAllConditions.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));
			
		 	EntityCondition condition = EntityCondition.makeCondition(listAllConditions, EntityOperator.AND);
			
			System.out.println("4. debug ::::::::::"  + userLoginId);
			papers = delegator.findList("PaperView", condition, null, sort, opts, false);

			HashSet<String> setStaffId = new HashSet<String>();
			if(facultyId != null){
				List<GenericValue> staffsOfFaculty = PaperDeclarationUtil.getListStaffsOfFaculty(delegator, facultyId);
				for(GenericValue st: staffsOfFaculty)
					setStaffId.add((String)st.getString("staffId"));
			}
			Debug.log(module + "::getPaperDeclarations, staff of selected faculty = " + setStaffId.size());	
			List<GenericValue> retList = FastList.newInstance();
			for (GenericValue gv : papers) {
				Debug.log(module + "::getPaperDeclarations, paper "
						+ gv.get("paperName"));
				
				boolean ok = true;
				if(facultyId != null){
					String paperId = (String)gv.getString("paperId");
					List<GenericValue> ST = PaperDeclarationUtil.getStaffsOfPaper(paperId, delegator);
					ok = false;
					for(GenericValue st: ST){
						String stId = (String)st.getString("staffId");
						if(setStaffId.contains(stId)){
							ok = true; break;
						}
					}
					
				}
				if(ok) retList.add(gv);
				
			}
			Debug.log(module + "::getPaperDeclarations, papers.sz = "
					+ papers.size() + ", retList = " + retList.size());
			
			result.put("listIterator", retList);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Error get list PaperView");
		}
		
		return result;
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void removeStaffPaperDeclaration(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("paperId");
		String staffId = request.getParameter("staffId");
		Debug.log(module + "::removeStaffPaperDeclaration, staffId = "
				+ staffId + ", paperId = " + paperId);
		try{
			Map<String, Object> rs = PaperDeclarationUtil.removeStaffPaperDeclarationc(paperId, staffId, delegator);
			
			String json = "OK";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void createStaffPaperDeclaration(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("paperId");
		String staffId = request.getParameter("staffId");
		String roleId = request.getParameter("roleId");
		Debug.log(module + "::createStaffPaperDeclaration, staffId = "
				+ staffId + ", paperId = " + paperId + ", roleId = " + roleId);
		try {
			Map<String, String> mRoleID2Name = FastMap.newInstance();
			
			List<GenericValue> roles = delegator.findList("StaffPaperDeclarationRole",
					null, null, null, null, false);
			for(GenericValue r: roles){
				mRoleID2Name.put(r.getString("roleId"), r.getString("roleName"));
			}
			
			List<GenericValue> lst = PaperDeclarationUtil.getStaffsOfPaper(
					paperId, staffId, delegator);
			if (lst == null || lst.size() == 0) {
				Map<String, Object> rs = PaperDeclarationUtil
						.createStaffPaperDeclarationc(paperId, staffId,roleId,
								delegator);
				GenericValue gv = (GenericValue) rs
						.get("staffPaperDeclaration");
				lst.add(gv);
			} else {

			}
			lst = PaperDeclarationUtil.getStaffsOfPaper(
					paperId, delegator);
			String json = "{\"staffsofpaper\":[";
			String id = staffId;// (String) st.get("staffId");
			String name = id;// mID2Name.get(id);
			for(int i = 0; i < lst.size(); i++){
				GenericValue stp = lst.get(i);
				id = stp.getString("staffId");
				GenericValue st = delegator.findOne("Staff", UtilMisc.toMap("staffId",id), false);
				name = st.getString("staffName");
				String rId = mRoleID2Name.get(stp.getString("roleId")); 
				json += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"" + ",\"role\":\"" + rId + "\" }";
				if(i < lst.size()-1)
					json += ",";
			}
			
			json += "]}";

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static Map<String, Object> getStaffPaperDeclarationRole(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		try{
			List<GenericValue> list = delegator.findList("StaffPaperDeclarationRole", null, null, null, null, false);
			retSucc.put("roles", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	public static Map<String, Object> getYesNo(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		try{
			List<GenericValue> list = delegator.findList("YesNo", null, null, null, null, false);
			retSucc.put("yn", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getPaperDeclarationStatus(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		try{
			List<GenericValue> list = delegator.findList("PaperDeclarationStatus", null, null, null, null, false);
			retSucc.put("statuses", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> jcreateStaffPaperDeclaration(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			Delegator delegator = ctx.getDelegator();
			String paperId = (String) context.get("paperId");
			String staffId = (String) context.get("staffId");
			String roleId = (String) context.get("roleId");
			
			retSucc = PaperDeclarationUtil.createStaffPaperDeclarationc(
					paperId, staffId, roleId, delegator);

			// retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> createRecordDB(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		GenericValue g = (GenericValue)context.get("record");
		try{
			delegator.create(g);
			retSucc.put("result", "success");
			Debug.log(module + "::createRecordDB, CREATE successfully " + g);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static void createNewPaperDeclaration(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> m = FastMap.newInstance();

		System.out
				.println("\n\n\t****************************************\n\tcreateNewPaperDeclaration - start\n\t");
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory()); // Creation
																					// of
																					// servletfileupload
		
		
		GenericValue userLogin = (GenericValue) request.getSession()
				.getAttribute("userLogin");
		String staffId = (String) userLogin.getString("userLoginId");
		
		List lst = null;
		
		String result = "AttachementException";
		String file_name = "";
		
		String paperName = "";
		String authors = "";
        String roleId = "";
        String paperCategoryId = "";
        String paperCategoryKNCId = "";
        String researchProjectProposalId = "";
        String journalConferenceName = "";
        String academicYearId = "";
        String link = "";
        String volumn = "";
        String month = "";
        String year = "";
        String issn = "";
        String doi = "";
        String impactFactor = "";
        

        JSONArray members = null ;
        JSONArray externalMembers = null;

		try {
			lst = fu.parseRequest(request);
		} catch (FileUploadException fup_ex) {
			System.out
					.println("\n\n\t****************************************\n\tException of FileUploadException \n\t");
			fup_ex.printStackTrace();
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		if (lst.size() == 0) // There is no item in lst
		{
			System.out
					.println("\n\n\t****************************************\n\not found param \n\t");
			result = "Not found param";
			m.put("message", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		FileItem file_item = null;
		FileItem selected_file_item = null;

		// Checking for form fields - Start
		for (int i = 0; i < lst.size(); i++) {
			file_item = (FileItem) lst.get(i);
			String fieldName = file_item.getFieldName();

			switch (fieldName) {
			case "file":
				selected_file_item = file_item;

				file_name = file_item.getName(); // Getting the file name
				System.out
						.println("\n\n\t****************************************\n\tThe selected file item's file name is : "
								+ file_name + "\n\t");
				break;
			case "paperName":
				paperName = file_item.getString();
				paperName = new String(paperName.getBytes(ISO), UTF_8).trim();
				
				break;
			case "authors":
				authors = file_item.getString();
				authors = new String(authors.getBytes(ISO), UTF_8).trim();
				
				break;
			case "roleId":
				roleId = file_item.getString();
				roleId = new String(roleId.getBytes(ISO), UTF_8).trim();
				break;
			case "paperCategoryId":
				paperCategoryId = file_item.getString();
				paperCategoryId = new String(paperCategoryId.getBytes(ISO), UTF_8).trim();
				break;
			case "paperCategoryKNCId":
				paperCategoryKNCId = file_item.getString();
				paperCategoryKNCId = new String(paperCategoryKNCId.getBytes(ISO), UTF_8).trim();
				break;
			case "researchProjectProposalId":
				researchProjectProposalId = file_item.getString();
				researchProjectProposalId = new String(researchProjectProposalId.getBytes(ISO), UTF_8).trim();
				break;
			case "journalConferenceName":
				journalConferenceName = file_item.getString();
				journalConferenceName = new String(journalConferenceName.getBytes(ISO), UTF_8).trim();
				break;
			case "academicYearId":
				academicYearId = file_item.getString();
				academicYearId = new String(academicYearId.getBytes(ISO), UTF_8).trim();
				break;
			case "link":
				link = file_item.getString();
				link = new String(link.getBytes(ISO), UTF_8).trim();
				break;
			case "volumn":
				volumn = file_item.getString();
				volumn = new String(volumn.getBytes(ISO), UTF_8).trim();
				break;
			case "month":
				month = file_item.getString();
				month = new String(month.getBytes(ISO), UTF_8).trim();
				break;
			case "year":
				year = file_item.getString();
				year = new String(year.getBytes(ISO), UTF_8).trim();
				break;
			case "issn":
				issn = file_item.getString();
				issn = new String(issn.getBytes(ISO), UTF_8).trim();
				break;
			case "doi":
				doi = file_item.getString();
				doi = new String(doi.getBytes(ISO), UTF_8).trim();
				break;
			case "impactFactor":
				impactFactor = file_item.getString();
				impactFactor = new String(impactFactor.getBytes(ISO), UTF_8).trim();
				break;
			case "members":
				members = JSONArray.fromObject(file_item.getString());
				
				break;
			case "externalMembers":
				externalMembers = JSONArray.fromObject(file_item.getString());
				break;
			}

		}
		// Checking for form fields - End
		if(paperName.equals("") || authors.equals("") || roleId.equals("")
			|| paperCategoryId.equals("") || paperCategoryKNCId.equals("")
			|| researchProjectProposalId.equals("") || journalConferenceName.equals("")
			|| academicYearId.equals("") || month.equals("") || year.equals("")
			|| members.size() < 0
				) {
			System.out
				.println("\n\n\t****************************************\n\t Param is missing or the value is empty  \n\t");
			result = "Param is missing or the value is empty";
			m.put("message", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}
		
		try {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			
			GenericValue p = delegator.makeValue("PaperDeclaration");
			String paperId = delegator.getNextSeqId("PaperDeclaration");
			p.put("paperId", paperId);
			p.put("staffId", staffId);
			p.put("staffId", staffId);
			
			p.put("paperName", paperName);
			p.put("authors", authors);
			p.put("paperCategoryId", paperCategoryId);
			p.put("paperCategoryKNCId", paperCategoryKNCId);
			p.put("researchProjectProposalId", researchProjectProposalId);
			p.put("journalConferenceName", journalConferenceName);
			p.put("academicYearId", academicYearId);
			p.put("year", Long.valueOf(year));
			p.put("month", Long.valueOf(month));
			
			if (doi != null
					&& !doi.equals(""))
				p.put("DOI", doi);
			
			if (link != null
					&& !link.equals(""))
				p.put("link", link);
			
			if (impactFactor != null
					&& !impactFactor.equals(""))
				p.put("impactFactor", Double.valueOf(impactFactor));
			
			if (issn != null
					&& !issn.equals(""))
				p.put("ISSN", issn);
			
			if (volumn != null
					&& !volumn.equals(""))
				p.put("volumn", volumn);
			
			p.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);
			
			delegator.create(p);
			
//			PaperDeclarationUtil.createStaffPaperDeclarationc(
//					paperId, staffId, roleId, delegator);
			
			if (selected_file_item != null) // If selected file item is null
			{
				System.out
						.println("\n\n\t****************************************\n\tThe selected save file item \n\t");
				
				byte[] file_bytes = selected_file_item.get();
				byte[] extract_bytes = new byte[file_bytes.length];

				for (int l = 0; l < file_bytes.length; l++)
					extract_bytes[l] = file_bytes[l];
				
				GenericValue gv = delegator.findOne("PaperDeclaration", false,
						UtilMisc.toMap("paperId", paperId));
				
				Debug.log(module + "::uploadFile, filename = " + file_name
						+ ", paperName = " + (String) gv.get("paperName")
						+ ", staffId = " + staffId);
				
				String ext = getExtension(file_name);
				java.util.Date currentDate = new java.util.Date();
				//SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("HHmmssddMMyyyy");
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

				String filenameDB = sCurrentDate + "." + ext;
				String fullFileName = establishFullFilename(staffId, filenameDB);

				Debug.log(module + "::uploadFile, filename = " + file_name
						+ ", paperId = " + paperId + ", extension = " + ext
						+ ", filenameDB = " + filenameDB + ", fullFileName = "
						+ fullFileName);

				FileOutputStream fout = new FileOutputStream(fullFileName);
				System.out
						.println("\n\n\t****************************************\n\tAfter creating outputstream");
				fout.flush();
				fout.write(extract_bytes);
				fout.flush();
				fout.close();

				gv.put("sourcePath", filenameDB);
				delegator.store(gv);
				
			}
			

			for(int i = 0; i < externalMembers.size(); ++i) {
				JSONObject member = externalMembers.getJSONObject(i);
				
				if(member.getString("staffName")==null||member.getString("staffName").equals("")
						||member.getString("roleId")==null||member.getString("roleId").equals("")
						||member.getString("CAId")==null||member.getString("CAId").equals("")
						) {
					break;
				}
				
				GenericValue gv = delegator.makeValue("ExternalMemberPaperDeclaration");
				String externalMemberPaperDeclarationId = delegator.getNextSeqId("ExternalMemberPaperDeclaration");
				
				gv.put("externalMemberPaperDeclarationId", externalMemberPaperDeclarationId);
				gv.put("staffName", StringEscapeUtils.unescapeHtml(member.getString("staffName")).trim());

				if(member.getString("affilliation")!=null&&member.getString("affilliation").equals("")){
					gv.put("affilliation", StringEscapeUtils.unescapeHtml(member.getString("affilliation")).trim());						
				}
				gv.put("paperId", paperId);
				gv.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);
				gv.put("roleId", member.getString("roleId"));
				
				if(member.containsKey("sequence")){
					gv.put("sequence", Long.valueOf(member.getString("sequence")));						
				}
				
				gv.put("correspondingAuthor", member.getString("CAId"));
				
				delegator.create(gv);
				
			}
			
			for(int i = 0; i < members.size(); ++i) {
				JSONObject member = members.getJSONObject(i);
				
				if(member.getString("staffId")==null||member.getString("staffId").equals("")
						||member.getString("roleId")==null||member.getString("roleId").equals("")
						||member.getString("CAId")==null||member.getString("CAId").equals("")
						) {
					break;
				}
				
				GenericValue gv = delegator.makeValue("StaffPaperDeclaration");
				String staffPaperDeclarationId = delegator.getNextSeqId("StaffPaperDeclaration");
				
				gv.put("staffPaperDeclarationId", staffPaperDeclarationId);
				gv.put("staffId", StringEscapeUtils.unescapeHtml(member.getString("staffId")));
				gv.put("paperId", paperId);
				gv.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);
				gv.put("roleId", member.getString("roleId"));
				
				if(member.containsKey("sequence")){
					gv.put("sequence", Long.valueOf(member.getString("sequence")));						
				}
				
				gv.put("correspondingAuthor", member.getString("CAId"));
				
				delegator.create(gv);
				
			}
			 
			
			
			System.out
					.println("\n\n\t****************************************\n\tcreateNewPaperDeclaration - end\n\t");
			m.put("message", "Create a successful paper");
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);

		} catch (Exception ioe_ex) {
			System.out
					.println("\n\n\t****************************************\n\tIOException occured on file writing");
			ioe_ex.printStackTrace();
			result = "AttachementException";
			m.put("message", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

	}
	
	public static Map<String, Object> createPaperDeclaration(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		// String staffId = (String)context.get("authorStaffId");

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");
		// String userLoginId =
		// (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String) userLogin.get("userLoginId");
		// String paperId = (String)context.get("paperId");
		String paperName = (String) context.get("paperName");

		// String paperCategoryId = (String)context.get("paperCategoryId");
		List<Object> paperCategoryIds = (List<Object>) context
				.get("paperCategoryId[]");
		String paperCategoryId = "NULL";
		if (paperCategoryIds != null && paperCategoryIds.size() > 0)
			paperCategoryId = (String) (paperCategoryIds.get(0));

		List<Object> paperCategoryKNCIds = (List<Object>) context
				.get("paperCategoryKNCId[]");
		String paperCategoryKNCId = "";
		if (paperCategoryKNCIds != null && paperCategoryKNCIds.size() > 0)
			paperCategoryKNCId = (String) (paperCategoryKNCIds.get(0));

		List<Object> researchProjectProposalIds = (List<Object>) context.get("researchProjectProposalId[]");
		String researchProjectProposalId = null;
		if(researchProjectProposalIds != null && researchProjectProposalIds.size() > 0)
			researchProjectProposalId = (String) researchProjectProposalIds.get(0);
		
		List<Object> roleIds = (List<Object>) context
				.get("roleId[]");
		String roleId = null;
		if (roleIds != null && roleIds.size() > 0)
			roleId = (String) (roleIds.get(0));
		
		
		String journalConferenceName = (String) context
				.get("journalConferenceName");
		String DOI = (String) context
				.get("DOI");
		String link = (String) context
				.get("link");
		
		String impactFactor = (String) context
				.get("impactFactor");
		
		String volumn = (String) context.get("volumn");
		String syear = (String) context.get("year");
		String smonth = (String) context.get("month");
		// Long year = Long.valueOf(syear);
		// Long month = Long.valueOf(smonth);
		String ISSN = (String) context.get("ISSN");
		String authors = (String) context.get("authors");
		// String academicYearId = (String)context.get("academicYearId");
		List<Object> academicYears = (List<Object>) context
				.get("academicYearId[]");
		String academicYearId = "";
		if (academicYears != null && academicYears.size() > 0)
			academicYearId = (String) academicYears.get(0);

		Debug.log(module + "::createPaperDeclaration, authorStaffId = "
				+ staffId + ", paperName = " + paperName + ", year = " + syear
				+ ", month = " + smonth + ", academicYearId = "
				+ academicYearId + ", paperCategoryId = " + paperCategoryId);

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		try {
			GenericValue p = delegator.makeValue("PaperDeclaration");
			String paperId = delegator.getNextSeqId("PaperDeclaration");
			p.put("paperId", paperId);
			p.put("staffId", staffId);
			if (paperName != null && !paperName.equals(""))
				p.put("paperName", paperName);
			if (paperCategoryId != null && !paperCategoryId.equals(""))
				p.put("paperCategoryId", paperCategoryId);
			if (paperCategoryKNCId != null && !paperCategoryKNCId.equals(""))
				p.put("paperCategoryKNCId", paperCategoryKNCId);
			
			if(researchProjectProposalId != null)
				p.put("researchProjectProposalId", researchProjectProposalId);
			
			if (journalConferenceName != null
					&& !journalConferenceName.equals(""))
				p.put("journalConferenceName", journalConferenceName);
			if (DOI != null
					&& !DOI.equals(""))
				p.put("DOI", DOI);
			if (link != null
					&& !link.equals(""))
				p.put("link", link);
			
			if (impactFactor != null
					&& !impactFactor.equals(""))
				p.put("impactFactor", Double.valueOf(impactFactor));
			
			if (volumn != null && !volumn.equals(""))
				p.put("volumn", volumn);
			if (syear != null && !syear.equals("")) {
				long year = Long.valueOf(syear);
				p.put("year", year);
			}
			if (smonth != null && !smonth.equals("")) {
				long month = Long.valueOf(smonth);
				p.put("month", month);
			}
			if (ISSN != null && !ISSN.equals(""))
				p.put("ISSN", ISSN);
			if (authors != null && !authors.equals(""))
				p.put("authors", authors);
			if (academicYearId != null && !academicYearId.equals(""))
				p.put("academicYearId", academicYearId);
			p.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);

			//delegator.create(p);
			Map<String, Object> input = FastMap.newInstance();
			input.put("record", p);
			Map<String, Object> rsp = dispatcher.runSync("createRecordDB", input);
			//if(rsp.get("result").equals("success"))
				
			// add an item to StaffPaperDeclaration corresponding to the current
			// staffId
			//Map<String, Object> input = FastMap.newInstance();
			input.clear();
			input.put("staffId", staffId);
			input.put("paperId", paperId);
			if(roleId != null)
				input.put("roleId", roleId);

			Map<String, Object> rs = dispatcher.runSync(
					"createStaffPaperDeclaration", input);

			// List<GenericValue> papers = FastList.newInstance();
			// papers.add(p);
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",EntityOperator.EQUALS,paperId));
			List<GenericValue> lpv = delegator.findList("PapersStaffView", 
					EntityCondition.makeCondition(conds),null,null,null, false);
			if(lpv != null && lpv.size() > 0){
				retSucc.put("papers", lpv.get(0));
			}
			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retSucc;
	}

	public static Map<String, Object> updatePaper(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		// String staffId = (String)context.get("authorStaffId");

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");
		// String userLoginId =
		// (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String) userLogin.get("userLoginId");
		String paperId = (String) context.get("paperId");
		String paperName = (String) context.get("paperName");

		// String paperCategoryId = (String) context.get("paperCategoryId");
		List<Object> paperCategoryIds = (List<Object>) context
				.get("paperCategoryId[]");
		String paperCategoryId = "";
		if (paperCategoryIds != null && paperCategoryIds.size() > 0)
			paperCategoryId = (String) (paperCategoryIds.get(0));
		
		List<Object> paperCategoryKNCIds = (List<Object>) context
				.get("paperCategoryKNCId[]");
		String paperCategoryKNCId = "";
		if (paperCategoryKNCIds != null && paperCategoryKNCIds.size() > 0)
			paperCategoryKNCId = (String) (paperCategoryKNCIds.get(0));

		List<Object> researchProjectProposalIds = (List<Object>) context.get("researchProjectProposalId[]");
		String researchProjectProposalId = null;
		if(researchProjectProposalIds != null && researchProjectProposalIds.size() > 0)
			researchProjectProposalId = (String) researchProjectProposalIds.get(0);
		
		List<Object> roleIds = (List<Object>) context
				.get("roleId[]");
		String roleId = "";
		if (roleIds != null && roleIds.size() > 0)
			roleId = (String) (roleIds.get(0));

		String journalConferenceName = (String) context
				.get("journalConferenceName");
		String DOI = (String) context
				.get("DOI");
		String link = (String) context
				.get("link");
		
		String impactFactor = (String) context
				.get("impactFactor");

		String volumn = (String) context.get("volumn");
		String year = (String) context.get("year");
		String month = (String) context.get("month");
		String ISSN = (String) context.get("ISSN");
		String authors = (String) context.get("authors");
		// String academicYearId = (String) context.get("academicYearId");
		List<Object> academicYearIds = (List<Object>) context
				.get("academicYearId[]");
		String academicYearId = "";
		if (academicYearIds != null && academicYearIds.size() > 0)
			academicYearId = (String) (academicYearIds.get(0));

		Debug.log(module + "::updatePaper, authorStaffId = " + staffId
				+ ", paperId = " + paperId + ", paperCategoryId = "
				+ paperCategoryId + ", month = " + month + ", year = " + year + ", role = " + roleId);
		Delegator delegator = ctx.getDelegator();

		try {
			List<GenericValue> cat = delegator.findList("PaperCategory", null,
					null, null, null, false);
			String paperCategoryName = "";
			for (GenericValue c : cat) {
				String pc = (String) c.get("paperCategoryId");
				if (pc.equals(paperCategoryId)) {
					paperCategoryName = (String) c.get("paperCategoryName");
				}
			}

			List<GenericValue> papers = delegator.findList("PaperDeclaration",
					EntityCondition.makeCondition(EntityCondition
							.makeCondition("paperId", EntityOperator.EQUALS,
									paperId)), null, null, null, false);
			for (GenericValue gv : papers) {
				Debug.log(module + "::updatePaper, paper "
						+ gv.get("paperName") + ", new Name = " + paperName
						+ ", category = " + paperCategoryId);
			}
			GenericValue p = papers.get(0);

			if (paperName != null && !paperName.equals(""))
				p.put("paperName", paperName);
			if (paperCategoryId != null && !paperCategoryId.equals(""))
				p.put("paperCategoryId", paperCategoryId);
			if (paperCategoryKNCId != null && !paperCategoryKNCId.equals(""))
				p.put("paperCategoryKNCId", paperCategoryKNCId);
			if(researchProjectProposalId != null)
				p.put("researchProjectProposalId", researchProjectProposalId);
			
			if (journalConferenceName != null
					&& !journalConferenceName.equals(""))
				p.put("journalConferenceName", journalConferenceName);
			if (DOI != null
					&& !DOI.equals(""))
				p.put("DOI", DOI);
			if (link != null
					&& !link.equals(""))
				p.put("link", link);
			
			if (impactFactor != null
					&& !impactFactor.equals(""))
				p.put("impactFactor", Double.valueOf(impactFactor));

			if (volumn != null && !volumn.equals(""))
				p.put("volumn", volumn);
			if (year != null && !year.equals("")) {
				long l_year = Long.valueOf(year);
				p.put("year", l_year);
			}
			if (month != null && !month.equals("")) {
				long l_month = Long.valueOf(month);
				p.put("month", l_month);
			}
			if (ISSN != null && !ISSN.equals(""))
				p.put("ISSN", ISSN);
			if (authors != null && !authors.equals(""))
				p.put("authors", authors);
			if (academicYearId != null && !academicYearId.equals(""))
				p.put("academicYearId", academicYearId);
			
			
			
			delegator.store(p);

			// update Role to entity StaffPaperDeclaration
			if(roleId != null && !roleId.equals("")){
				List<EntityCondition> conds = FastList.newInstance();
				conds.add(EntityCondition.makeCondition("paperId",paperId));
				conds.add(EntityCondition.makeCondition("staffId",staffId));
				List<GenericValue> spl = delegator.findList("StaffPaperDeclaration",
						EntityCondition.makeCondition(conds), 
						null,null,null,false);
				if(spl != null && spl.size() > 0){
					//GenericValue sp = spl.get(0);
					for(GenericValue sp: spl){
						Debug.log(module + "::updatePaper, START update role");
						sp.put("roleId", roleId);
						delegator.store(sp);
					}
				}
			}

			/*
			 * Map<String, Object> ret_paper = FastMap.newInstance();
			 * ret_paper.put("paperName", p.get("paperName"));
			 * ret_paper.put("paperCategoryId", p.get("paperCategoryId"));
			 * ret_paper.put("journalConferenceName",
			 * p.get("journalConferenceName")); ret_paper.put("volumn",
			 * p.get("volumn")); ret_paper.put("year", p.get("year"));
			 * ret_paper.put("month", p.get("month")); ret_paper.put("ISSN",
			 * p.get("ISSN")); ret_paper.put("authors", p.get("authors"));
			 * ret_paper.put("academicYearId", p.get("academicYearId")); '
			 * ret_paper.put("paperCategoryName", paperCategoryName);
			 */

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, p.get("paperId")));
			List<GenericValue> ret_paper = delegator.findList(
					"PapersStaffView", EntityCondition.makeCondition(conds),
					null, null, null, false);

			GenericValue pv = ret_paper.get(0);
			retSucc.put("papers", pv);

			retSucc.put("message", "Update Row Success");

			Debug.log(module + "::updatePaper FINISHED, journal-conference = "
					+ (String) pv.get("categoryName"));

		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getPaperCategory(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();

		// String u1 = (String)ctx.getAttribute("userLoginId");
		// String u2 = (String)context.get("userLoginId");

		// if(u1 == null) u1 = "NULL";
		// if(u2 == null) u2 = "NULL";

		// System.out.println(module +
		// "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " +
		// u2);
		// Debug.log(module + "::getEducationProgress, Debug.log u1 = " + u1 +
		// ", u2 = " + u2);

		String[] keys = { "paperCategoryId", "paperCategoryName",
				"paperCategoryCode", "journalIndexId" };
		String[] search = { "paperCategoryName" };
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true,
					EntityFindOptions.TYPE_SCROLL_INSENSITIVE,
					EntityFindOptions.CONCUR_READ_ONLY, true);
			for (String key : keys) {
				Object el = context.get(key);
				if (!(el == null || el == (""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if (index == -1) {
						condition = EntityCondition.makeCondition(key,
								EntityOperator.EQUALS, el);
					} else {
						condition = EntityCondition.makeCondition(key,
								EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}

			List<GenericValue> list = delegator.findList("PaperCategory",
					EntityCondition.makeCondition(conditions), null, null,
					findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();

			List<Map> listMap = FastList.newInstance();
			for (GenericValue el : list) {
				Map<String, Object> map = FastMap.newInstance();
				for (String key : keys) {
					map.put(key, el.getString(key));

				}
				listMap.add(map);
			}
			result.put("result", listMap);
			result.put("count", String.valueOf(listMap.size()));
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}

	public static Map<String, Object> deletePaperDeclaration(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String paperId = (String) context.get("paperId");
		Delegator delegator = ctx.getDelegator();

		try {
			GenericValue gv = delegator.findOne("PaperDeclaration", false,
					UtilMisc.toMap("paperId", paperId));
			if (gv == null) {
				return ServiceUtil.returnError("paperId " + paperId
						+ " not exists");
			}
			gv.put("statusId", PaperDeclarationUtil.STATUS_DISABLED);
			delegator.store(gv);
			Debug.log(module
					+ "::deletePaperDeclaration, disable PaperDeclaration "
					+ paperId);

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			List<GenericValue> ps = delegator.findList("StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			for (GenericValue g : ps) {
				g.put("statusId", PaperDeclarationUtil.STATUS_DISABLED);
				delegator.store(g);
				Debug.log(module
						+ "::deletePaperDeclaration, disable StaffPaperDeclaration ("
						+ paperId + "," + g.get("staffId") + ")");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}

		return retSucc;
	}

	public static Map<String, Object> deleteStaffPaperDeclaration(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String paperId = (String) context.get("paperId");
		String staffId = (String) context.get("staffId");

		Delegator delegator = ctx.getDelegator();

		retSucc = PaperDeclarationUtil.deleteStaffPaperDeclaration(staffId,
				paperId, delegator);

		return retSucc;
	}

	public static Map<String, Object> getMembersOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String paperId = (String)context.get("paperId");
		Delegator delegator = ctx.getDelegator();
		Debug.log(module + "::getMembersOfAPaper, paperId = " + paperId);
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",EntityOperator.EQUALS,paperId));
			conds.add(EntityCondition.makeCondition("statusStaffPaper",EntityOperator.EQUALS,"ENABLED"));
			List<GenericValue> lst = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds),null,null,null,false);
			
			Debug.log(module + "::getMembersOfAPaper, lst.sz = " + lst.size());
			for(GenericValue g: lst){
				Debug.log(g.getString("staffPaperDeclarationId") 
						+ "," + g.getString("paperId")
						+ "," + g.getString("staffName")
						+ "," + g.getString("staffId")
						+ "," + g.getLong("sequence")
						+ "," + g.getString("correspondingAuthor")
						+ "," + g.getString("roleId")
						+ "," + g.getString("roleName")
						);
			}
			retSucc.put("staffs", lst);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
	public static Map<String, Object> getExternalMembersOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String paperId = (String)context.get("paperId");
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",EntityOperator.EQUALS,paperId));
			conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"ENABLED"));
			List<GenericValue> lst = delegator.findList("ExternalMemberPaperDeclarationView",
					EntityCondition.makeCondition(conds),null,null,null,false);
			
			retSucc.put("staffs", lst);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}

	public static Map<String, Object> createMemberOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String paperId = (String)context.get("paperId");
		List<String> list_staffId = (List<String>)context.get("staffId[]");
		List<String> list_roleId = (List<String>)context.get("roleId[]");
		String s_sequence = (String)context.get("sequence");
		List<String> list_correspondingAuthors = (List<String>)context.get("correspondingAuthor[]");
		String correspondingAuthor = null;
		if(list_correspondingAuthors != null && list_correspondingAuthors.size() > 0)
			correspondingAuthor = list_correspondingAuthors.get(0);
		
		Delegator delegator = ctx.getDelegator();
		String staffId = null;
		if(list_staffId != null && list_staffId.size() > 0)
			staffId = list_staffId.get(0);
		String roleId = null;
		if(list_roleId != null && list_roleId.size() > 0)
			roleId = list_roleId.get(0);
		Debug.log(module + "::createMemberOfAPaper, paperId = " + paperId + ", staffId = " + staffId
				+ ", roleId = " + roleId);
		long sequence = 0;
		try{
			sequence = Long.valueOf(s_sequence);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",EntityOperator.EQUALS,paperId));
			conds.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,paperId));
			conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"ENABLED"));
			List<GenericValue> lst = delegator.findList("PapersStaffView", 
					EntityCondition.makeCondition(conds), null,null,null, false);
			if(lst.size() > 0){
				return ServiceUtil.returnError("Thanh vien bai bao da ton tai");
			}
			
			String staffPaperDeclarationId = delegator.getNextSeqId("StaffPaperDeclaration");
			GenericValue sp = delegator.makeValue("StaffPaperDeclaration");
			sp.put("staffPaperDeclarationId", staffPaperDeclarationId);
			if(paperId != null) sp.put("paperId", paperId);
			if(staffId != null) sp.put("staffId", staffId);
			if(correspondingAuthor != null)sp.put("correspondingAuthor", correspondingAuthor);
			
			sp.put("sequence", sequence);
			if(roleId != null)sp.put("roleId", roleId);
			sp.put("statusId", "ENABLED");
			delegator.create(sp);
		
			Debug.log(module + "::createMemberOfAPaper, paperId = " + paperId + ", staffId = " + staffId
					+ ", roleId = " + roleId + ", CREATED");
			
			GenericValue role = delegator.findOne("StaffPaperDeclarationRole", 
					UtilMisc.toMap("roleId",roleId), false);
			
			GenericValue staff = delegator.findOne("Staff", 
					UtilMisc.toMap("staffId",staffId), false);
			
			
			GenericValue spv = delegator.makeValue("PapersStaffView");

			spv.put("staffPaperDeclarationId", staffPaperDeclarationId);
			if(sp.get("staffId") != null) spv.put("staffId", (String) sp.get("staffId"));
			else spv.put("staffId", "");
			if(sp.get("paperId") != null) spv.put("paperId", (String) sp.get("paperId"));
			else spv.put("paperId", "");
			if(sp.get("roleId") != null) spv.put("roleId", (String) sp.get("roleId"));
			else spv.put("roleId", "");
			if(staff != null && staff.getString("staffName") != null) spv.put("staffName", staff.getString("staffName"));
			else spv.put("staffName", "");
			if(staff != null && staff.getString("staffName") != null) spv.put("roleName", role.getString("roleName"));
			else spv.put("roleName", "");
			spv.put("sequence", sequence);
			spv.put("correspondingAuthor", correspondingAuthor);
			
			retSucc.put("staffs", spv);
			retSucc.put("message", "successfully");
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
	public static Map<String, Object> createExternalMemberOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String paperId = (String)context.get("paperId");
		
		List<String> list_roleId = (List<String>)context.get("roleId[]");
		String s_sequence = (String)context.get("sequence");
		List<String> list_correspondingAuthors = (List<String>)context.get("correspondingAuthor[]");
		String affilliation = (String)context.get("affilliation");
		String staffName = (String)context.get("staffName");
		Delegator delegator = ctx.getDelegator();
		String correspondingAuthor = " ";
		if(list_correspondingAuthors != null && list_correspondingAuthors.size() > 0)
			correspondingAuthor = list_correspondingAuthors.get(0);
		String roleId =  "";
		if(list_roleId != null && list_roleId.size() > 0)
			roleId = list_roleId.get(0);
		if(staffName == null) staffName = " ";
		if(affilliation == null) affilliation = " ";
		
		Debug.log(module + "::createExternalMemberOfAPaper, paperId = " + paperId + ", correspondingAuthor = " + correspondingAuthor
				+ ", roleId = " + roleId);
		long sequence = 0;
		try{
			sequence = Long.valueOf(s_sequence);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			
			String externalMemberPaperDeclarationId = delegator.getNextSeqId("ExternalMemberPaperDeclaration");
			GenericValue sp = delegator.makeValue("ExternalMemberPaperDeclaration");
			sp.put("externalMemberPaperDeclarationId", externalMemberPaperDeclarationId);
			sp.put("paperId", paperId);
			sp.put("correspondingAuthor", correspondingAuthor);
			sp.put("roleId", roleId);
			if(staffName != null && !staffName.equals(""))
				sp.put("staffName", staffName);
			if(affilliation != null && !affilliation.equals(""))
				sp.put("affilliation", affilliation);
			
			
			sp.put("sequence", sequence);
			sp.put("statusId", "ENABLED");
			delegator.create(sp);
		
			Debug.log(module + "::createExternalMemberOfAPaper, paperId = " + paperId + ", staffName = " + staffName
					+ ", roleId = " + roleId + ", CREATED");
			
			GenericValue role = delegator.findOne("StaffPaperDeclarationRole", 
					UtilMisc.toMap("roleId",roleId), false);
			
			
			GenericValue spv = delegator.makeValue("ExternalMemberPaperDeclarationView");

			spv.put("externalMemberPaperDeclarationId", externalMemberPaperDeclarationId);
			if(sp.get("staffName") != null)spv.put("staffName", (String) sp.get("staffName"));
			else spv.put("staffName", "");
			
			spv.put("paperId", (String) sp.get("paperId"));
			spv.put("roleId", (String) sp.get("roleId"));
			if(sp.get("affilliation")!=null)
				spv.put("affilliation", (String) sp.get("affilliation"));
			else spv.put("affilliation", "");
			
			spv.put("sequence", sp.getLong("sequence"));
			
			spv.put("correspondingAuthor", (String) sp.get("correspondingAuthor"));
			if(role.getString("roleName") != null)
				spv.put("roleName", role.getString("roleName"));
			else 
				spv.put("roleName", "");
			
			retSucc.put("staffs", spv);
			retSucc.put("message", "successfully");
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}
	public static Map<String, Object> updateExternalMemberOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String externalMemberPaperDeclarationId = (String)context.get("externalMemberPaperDeclarationId");

		String paperId = (String)context.get("paperId");
		List<String> list_correspondingAuthors = (List<String>)context.get("correspondingAuthor[]");
		List<String> list_roleId = (List<String>)context.get("roleId[]");
		String s_sequence = (String)context.get("sequence");
		String affilliation = (String)context.get("affilliation");
		String staffName = (String)context.get("staffName");
		Delegator delegator = ctx.getDelegator();
		String correspondingAuthor = " ";
		if(list_correspondingAuthors != null && list_correspondingAuthors.size() > 0)
			correspondingAuthor = list_correspondingAuthors.get(0);
		if(affilliation == null) affilliation = " ";
		if(staffName == null) staffName = " ";
		
		String roleId = " ";
		if(list_roleId != null && list_roleId.size() > 0)
			roleId = list_roleId.get(0);
		
		Debug.log(module + "::updateExternalMemberOfAPaper, externalMemberPaperDeclarationId = " + externalMemberPaperDeclarationId +
				", staffName = " + staffName
				+ ", roleId = " + roleId);
		long sequence = 0;
		try{
			sequence = Long.valueOf(s_sequence);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			GenericValue sp = delegator.findOne("ExternalMemberPaperDeclaration", 
					UtilMisc.toMap("externalMemberPaperDeclarationId",externalMemberPaperDeclarationId), false);
			if(sp != null){
				sp.put("paperId", paperId);
				sp.put("correspondingAuthor", correspondingAuthor);
				sp.put("roleId", roleId);
				if(staffName != null && !staffName.equals(""))
					sp.put("staffName", staffName);
				if(affilliation != null && !affilliation.equals(""))
					sp.put("affilliation", affilliation);
				
				sp.put("sequence", sequence);
				sp.put("statusId", "ENABLED");
				
				delegator.store(sp);
			
				GenericValue role = delegator.findOne("StaffPaperDeclarationRole", 
						UtilMisc.toMap("roleId",roleId), false);
				
				GenericValue spv = delegator.makeValue("ExternalMemberPaperDeclarationView");

				spv.put("externalMemberPaperDeclarationId", externalMemberPaperDeclarationId);
				if(sp.get("staffName") != null)
					spv.put("staffName", (String) sp.get("staffName"));
				else spv.put("staffName", "");
				spv.put("paperId", (String) sp.get("paperId"));
				spv.put("roleId", (String) sp.get("roleId"));
				if(sp.get("affilliation") != null)
					spv.put("affilliation", (String) sp.get("affilliation"));
				else spv.put("affilliation", "");
				spv.put("sequence", sp.getLong("sequence"));
				
				spv.put("correspondingAuthor", (String) sp.get("correspondingAuthor"));
				if(role != null && role.getString("roleName") != null)
					spv.put("roleName", role.getString("roleName"));
				else spv.put("roleName", "");
				
				retSucc.put("staffs", spv);
				retSucc.put("message", "successfully");
			}else{
				retSucc.put("message", "ban ghi khong ton tai");
			}
				
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}

	public static Map<String, Object> updateMemberOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffPaperDeclarationId = (String)context.get("staffPaperDeclarationId");
		List<String> list_staffId = (List<String>)context.get("staffId[]");
		List<String> list_roleId = (List<String>)context.get("roleId[]");
		String s_sequence = (String)context.get("sequence");
		List<String> list_correspondingAuthors = (List<String>)context.get("correspondingAuthor[]");
		String correspondingAuthor = null;
		if(list_correspondingAuthors != null && list_correspondingAuthors.size() > 0)
			correspondingAuthor = list_correspondingAuthors.get(0);
		Delegator delegator = ctx.getDelegator();
		String staffId = null;
		if(list_staffId != null && list_staffId.size() > 0)
			staffId = list_staffId.get(0);
		String roleId = null;
		if(list_roleId != null && list_roleId.size() > 0)
			roleId = list_roleId.get(0);
		Debug.log(module + "::updateMemberOfAPaper, staffPaperDeclarationId = " + staffPaperDeclarationId + ", staffId = " + staffId
				+ ", roleId = " + roleId);
		long sequence = 0;
		try{
			sequence = Long.valueOf(s_sequence);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			GenericValue sp = delegator.findOne("StaffPaperDeclaration", 
					UtilMisc.toMap("staffPaperDeclarationId",staffPaperDeclarationId), false);
			if(sp != null){
				if(staffId != null)
					sp.put("staffId", staffId);
				//else sp.put("staffId", "");
				if(roleId != null)
					sp.put("roleId", roleId);
				//else sp.put("roleId", ""); 
					
				if(correspondingAuthor != null)
					sp.put("correspondingAuthor", correspondingAuthor);
				//else sp.put("correspondingAuthor", "");
				sp.put("sequence", sequence);
				sp.put("statusId", "ENABLED");
				delegator.store(sp);
			
				GenericValue role = delegator.findOne("StaffPaperDeclarationRole", 
						UtilMisc.toMap("roleId",roleId), false);
				
				GenericValue staff = delegator.findOne("Staff", 
						UtilMisc.toMap("staffId",staffId), false);
				
				
				GenericValue spv = delegator.makeValue("PapersStaffView");

				spv.put("staffPaperDeclarationId", staffPaperDeclarationId);
				if(sp.get("staffId") != null) spv.put("staffId", (String) sp.get("staffId"));
				else spv.put("staffId", "");
				spv.put("paperId", (String) sp.get("paperId"));
				if(sp.get("roleId") != null) spv.put("roleId", (String) sp.get("roleId"));
				else spv.put("roleId", "");
				if(staff != null && staff.getString("staffName") != null)spv.put("staffName", staff.getString("staffName"));
				else spv.put("staffName", "");
				if(role != null && role.getString("roleName") != null) spv.put("roleName", role.getString("roleName"));
				else spv.put("roleName", "");
				
				retSucc.put("staffs", spv);
				retSucc.put("message", "successfully");
			}else{
				retSucc.put("message", "ban ghi khong ton tai");
			}
				
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}

	public static Map<String, Object> removeMemberOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffPaperDeclarationId = (String)context.get("staffPaperDeclarationId");
		Delegator delegator = ctx.getDelegator();
		Debug.log(module + "::removeMemberOfAPaper, staffPaperDeclarationId = " + staffPaperDeclarationId);
		try{
			GenericValue sp = delegator.findOne("StaffPaperDeclaration", 
					UtilMisc.toMap("staffPaperDeclarationId",staffPaperDeclarationId), false);
			if(sp != null){
				//delegator.removeValue(sp);
				sp.put("statusId", "DISABLED");
				delegator.store(sp);
				retSucc.put("message", "successfully");
			}else{
				retSucc.put("message", "ban ghi khong ton tai");
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}

	public static Map<String, Object> removeExternalMemberOfAPaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String externalMemberPaperDeclarationId = (String)context.get("externalMemberPaperDeclarationId");
		Delegator delegator = ctx.getDelegator();
		Debug.log(module + "::removeExternalMemberOfAPaper, externalMemberPaperDeclarationId = " + externalMemberPaperDeclarationId);
		try{
			GenericValue sp = delegator.findOne("ExternalMemberPaperDeclaration", 
					UtilMisc.toMap("externalMemberPaperDeclarationId",externalMemberPaperDeclarationId), false);
			if(sp != null){
				
				sp.put("statusId", "DISABLED");
				delegator.store(sp);
				//delegator.removeValue(sp);
				
				retSucc.put("message", "successfully");
			}else{
				retSucc.put("message", "ban ghi khong ton tai");
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
	}

	public static Map<String, Object> getListPaperCategoryKNC(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,"ENABLED"));
			
			List<GenericValue> listPaperCategoryKNC = delegator.findList("PaperCategoryKNC",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			
			retSucc.put("listPaperCategoryKNC", listPaperCategoryKNC);

		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getListPaperDeclaration(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		try {
			
			List<GenericValue> listPaperDeclaration = delegator.findList("PaperDeclarationStatus",
					null, null, null, null,
					false);
			
			retSucc.put("listPaperDeclaration", listPaperDeclaration);

		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> updatePaperDeclarationStatus(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String paperId = (String) context.get("paperId");
		String statusId = (String) context.get("statusId");
		
		try{
			GenericValue gv = delegator.findOne("PaperDeclaration", false, UtilMisc.toMap("paperId",paperId));
			if(gv != null){
				gv.put("approveStatusId", statusId);
				
				delegator.store(gv);
				
        		retSucc.put("message", "Thay đổi trạng thái thành công");
        	} else {
        		retSucc.put("message", "Không tìm thấy bài báo");
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
	
	public static Map<String, Object> updatePaperCategoryKNC(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String paperId = (String) context.get("paperId");
		String paperCategoryKNCId = (String) context.get("paperCategoryKNCId");
		
		try{
			GenericValue gv = delegator.findOne("PaperDeclaration", false, UtilMisc.toMap("paperId",paperId));
			if(gv != null){
				gv.put("paperCategoryKNCId", paperCategoryKNCId);
				
				delegator.store(gv);
				
        		retSucc.put("message", "Thay đổi thành công");
        	} else {
        		retSucc.put("message", "Không tìm thấy bài báo");
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}
