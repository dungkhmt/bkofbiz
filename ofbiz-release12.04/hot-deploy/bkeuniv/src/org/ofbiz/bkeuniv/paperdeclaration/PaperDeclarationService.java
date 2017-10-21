package src.org.ofbiz.bkeuniv.paperdeclaration;

import java.awt.print.Paper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;

//import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import src.org.ofbiz.utils.BKEunivUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

public class PaperDeclarationService {

	public static String module = PaperDeclarationService.class.getName();
	public static String dataFolder = "C:/euniv-deploy";

	public static String establishFullFilename(String staffId, String name) {
		String fullname = dataFolder + File.separator + staffId
				+ File.separator + "papers" + File.separator + name;
		return fullname;
	}

	@SuppressWarnings({ "unchecked" })
	public static void exportExcelKV01(HttpServletRequest request,
			HttpServletResponse response) {
		
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String year = (String)request.getParameter("reportyear");
		
		Debug.log(module + "::exportExcelKV01, academic year = " + year);
		
		String filename = "KV01";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			// start renderExcel
			HSSFWorkbook wb = new HSSFWorkbook();

			Sheet sh = wb.createSheet("sheet1");

			int i;
			for (i = 1; i <= 10; i++) {
				Row r = sh.createRow(i);
				Cell c = r.createCell(0);
				c.setCellValue("1-" + i);

				c = r.createCell(1);
				c.setCellValue("PHAM Quang Dung - " + i);

				c = r.createCell(2);
				c.setCellValue("DHBKHN - " + i);
			}

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
	public static void updateStaffsOfPaper(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("paperId");
		String str_staffIds = (String)request.getParameter("staffsId");
		String[] staffIds = str_staffIds.split(",");
		HashSet<String> set_new_staffs = new HashSet<String>();
		for(String s: staffIds) set_new_staffs.add(s);
		
		
		Debug.log(module + "::updateStaffsOfPaper, paperId = " + paperId + ", str_staffIds = " + str_staffIds);
		
		try{
			List<GenericValue> staffOfPapers = PaperDeclarationUtil.getStaffsOfPaper(paperId, delegator);
			Set<String> set_staffs = FastSet.newInstance();
			for(GenericValue gv: staffOfPapers){
				String st = (String)gv.get("staffId");
					set_staffs.add(st);
			}
			Set<String> addedStaffs = FastSet.newInstance();
			Set<String> removedStaffs = FastSet.newInstance();
			
			for(String s: set_new_staffs){
				if(!set_staffs.contains(s)){
					addedStaffs.add(s);
				}
			}
			for(String s: set_staffs){
				if(!set_new_staffs.contains(s)){
					removedStaffs.add(s);
				}
			}
			
			for(String staffId: addedStaffs){
				PaperDeclarationUtil.createStaffPaperDeclarationc(paperId, staffId, delegator);
			}
			for(String staffId: removedStaffs){
				PaperDeclarationUtil.deleteStaffPaperDeclaration(staffId, paperId, delegator);
			}
			
			String rs = "{\"status\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			
		}catch(Exception ex){
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
			List<GenericValue> staffsOfPaper = PaperDeclarationUtil.getStaffsOfPaper(paperId,
					delegator);

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

					rs += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";

					if (i < staffsOfPaper.size() - 1)
						rs += ",";

					Debug.log(module + "::getStaffsOfPaper, staffs " + name);
				}
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
		String fn = request.getParameter("filename");
		String data = request.getParameter("data");
		String paperId = request.getParameter("paperId");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		try {
			GenericValue gv = delegator.findOne("PaperDeclaration", false,
					UtilMisc.toMap("paperId", paperId));
			String staffId = (String) gv.get("staffId");

			Debug.log(module + "::uploadFile, filename = " + fn
					+ ", paperName = " + (String) gv.get("paperName")
					+ ", staffId = " + staffId);
			String ext = getExtension(fn);
			java.util.Date currentDate = new java.util.Date();
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
					"HHmmssddMMyyyy");
			String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

			String filenameDB = sCurrentDate + "." + ext;
			String fullFileName = establishFullFilename(staffId, filenameDB);

			Debug.log(module + "::uploadFile, filename = " + fn
					+ ", paperId = " + paperId + ", extension = " + ext
					+ ", filenameDB = " + filenameDB + ", fullFileName = "
					+ fullFileName);

			// byte[] B = Base64.decode(data);
			byte[] B = Base64.decodeBase64(data);
			// PrintWriter out = new PrintWriter("C:/tmp/" + fn);
			// FileOutputStream fout = new FileOutputStream("C:/tmp/" + fn);
			FileOutputStream fout = new FileOutputStream(fullFileName);
			// fout.flush();
			fout.write(B);
			fout.flush();
			fout.close();

			gv.put("sourcePath", filenameDB);
			delegator.store(gv);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Map<String, Object> m = FastMap.newInstance();
		m.put("result", "ok");
		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
				response, 200);
		// return "result";
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
		// String staffId = (String)context.get("authorStaffId");

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");
		// String userLoginId =
		// (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String) userLogin.get("userLoginId");

		Debug.log(module + "::getPapersOfStaff, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("authorStaffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			List<GenericValue> papers = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue gv : papers) {
				Debug.log(module + "::getPapersOfStaff, paper "
						+ gv.get("paperName"));
			}
			Debug.log(module + "::getPapersOfStaff, papers.sz = "
					+ papers.size());
			retSucc.put("papers", papers);

		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	@SuppressWarnings({ "unchecked" })
	public static void createStaffPaperDeclaration(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String paperId = request.getParameter("paperId");
		String staffId = request.getParameter("staffId");
		Debug.log(module + "::createStaffPaperDeclaration, staffId = " + staffId + ", paperId = " + paperId);
		try{
			List<GenericValue> lst = PaperDeclarationUtil.getStaffsOfPaper(paperId, staffId, delegator);
			if(lst == null || lst.size() == 0){
				Map<String, Object> rs = PaperDeclarationUtil.createStaffPaperDeclarationc(paperId, staffId, delegator);
				GenericValue gv = (GenericValue) rs.get("staffPaperDeclaration");
				lst.add(gv);
			}else{
				
			}
			String json = "{\"staffsofpaper\":[";
			String id = staffId;//(String) st.get("staffId");
			String name = id;//mID2Name.get(id);

			json += "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
			
			json += "]}";
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static Map<String, Object> createStaffPaperDeclaration(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			Delegator delegator = ctx.getDelegator();
			String paperId = (String) context.get("paperId");
			String staffId = (String) context.get("staffId");
			
			retSucc = PaperDeclarationUtil.createStaffPaperDeclarationc(paperId, staffId, delegator);
			
			//retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
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
		if (paperCategoryIds != null)
			paperCategoryId = (String) (paperCategoryIds.get(0));

		String journalConferenceName = (String) context
				.get("journalConferenceName");
		String volumn = (String) context.get("volumn");
		String syear = (String) context.get("year");
		String smonth = (String) context.get("month");
		Long year = Long.valueOf(syear);
		Long month = Long.valueOf(smonth);
		String ISSN = (String) context.get("ISSN");
		String authors = (String) context.get("authors");
		// String academicYearId = (String)context.get("academicYearId");
		List<Object> academicYears = (List<Object>) context
				.get("academicYearId[]");
		String academicYearId = "NULL";
		if (academicYears != null)
			academicYearId = (String) academicYears.get(0);

		Debug.log(module + "::createPaperDeclaration, authorStaffId = "
				+ staffId + ", paperName = " + paperName + ", year = " + year
				+ ", month = " + month + ", academicYearId = " + academicYearId
				+ ", paperCategoryId = " + paperCategoryId);

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		try {
			GenericValue p = delegator.makeValue("PaperDeclaration");
			String paperId = delegator.getNextSeqId("PaperDeclaration");
			p.put("paperId", paperId);
			p.put("staffId", staffId);
			p.put("paperName", paperName);
			p.put("paperCategoryId", paperCategoryId);
			p.put("journalConferenceName", journalConferenceName);
			p.put("volumn", volumn);
			p.put("year", year);
			p.put("month", month);
			p.put("ISSN", ISSN);
			p.put("authors", authors);
			p.put("academicYearId", academicYearId);
			p.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);

			delegator.create(p);

			// add an item to StaffPaperDeclaration corresponding to the current
			// staffId
			Map<String, Object> input = FastMap.newInstance();
			input.put("staffId", staffId);
			input.put("paperId", paperId);

			Map<String, Object> rs = dispatcher.runSync(
					"createStaffPaperDeclaration", input);

			List<GenericValue> papers = FastList.newInstance();
			papers.add(p);
			retSucc.put("papers", p);
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
		String paperCategoryId = (String) context.get("paperCategoryId");
		String journalConferenceName = (String) context
				.get("journalConferenceName");
		String volumn = (String) context.get("volumn");
		String year = (String) context.get("year");
		String month = (String) context.get("month");
		String ISSN = (String) context.get("ISSN");
		String authors = (String) context.get("authors");
		String academicYearId = (String) context.get("academicYearId");

		Debug.log(module + "::updatePaper, authorStaffId = " + staffId
				+ ", paperId = " + paperId);
		Delegator delegator = ctx.getDelegator();

		try {
			List<GenericValue> papers = delegator.findList("PaperDeclaration",
					EntityCondition.makeCondition(EntityCondition
							.makeCondition("paperId", EntityOperator.EQUALS,
									paperId)), null, null, null, false);
			for (GenericValue gv : papers) {
				Debug.log(module + "::updatePaper, paper "
						+ gv.get("paperName") + ", new Name = " + paperName);
			}
			GenericValue p = papers.get(0);
			p.put("paperName", paperName);
			p.put("paperCategoryId", paperCategoryId);
			p.put("journalConferenceName", journalConferenceName);
			p.put("volumn", volumn);
			p.put("year", year);
			p.put("month", month);
			p.put("ISSN", ISSN);
			p.put("authors", authors);
			p.put("academicYearId", academicYearId);

			delegator.store(p);

			retSucc.put("papers", papers);

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

		retSucc = PaperDeclarationUtil.deleteStaffPaperDeclaration(staffId, paperId, delegator);

		return retSucc;
	}

	public static void main(String[] args) {
		String s = "aaa.pdf";
		String[] ss = s.split("\\.");
		System.out.println("s.lengt = " + ss.length + ", ss[0] = " + ss[0]
				+ ", ss[1] = " + ss[1]);
	}
}
