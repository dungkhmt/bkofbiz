package org.ofbiz.routes;

import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.io.*;

import javolution.util.FastList;

public class RouteService {
	public final static String module = RouteService.class.getName();

	@SuppressWarnings("unchecked")
	public static String checkAccount(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		//Map outMap = FastMap.newInstance();
		String accountId = request.getParameter("accountId");
		System.out.println("RouteService::checkAccount, accountId = " + accountId);
		
		try {
							
			String rs = "{\"result\":\"" + accountId + "xxxxx" + accountId + "\"}";
			System.out.println("RouteService::checkAccount, return rs = " + rs);
			return rs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

	@SuppressWarnings({ "unchecked" })
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String filename = "HDSD.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			File f = new File("C:/DungPQ/olbius/tmp/" + filename);
			FileInputStream fi = new FileInputStream(f);
			byte[] bytes = new byte[(int) f.length()];
			fi.read(bytes);

			response.setHeader("content-disposition", "attachment;filename="
					+ filename);
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
	public static void exportRouteExcel(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String filename = "route";
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

	public static String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		// ServletFileUpload fu = new ServletFileUpload(new
		// DiskFileItemFactory(10240, new File(new File("runtime"), "tmp")));
		// //Creation of servletfileupload
		System.out
				.println("\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - start\n\t");
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory()); // Creation
																					// of
																					// servletfileupload
		java.util.List lst = null;
		String result = "AttachementException";
		String file_name = "";
		try {
			lst = fu.parseRequest(request);
		} catch (FileUploadException fup_ex) {
			System.out
					.println("\n\n\t****************************************\n\tException of FileUploadException \n\t");
			fup_ex.printStackTrace();
			result = "AttachementException";
			return (result);
		}

		if (lst.size() == 0) // There is no item in lst
		{
			System.out
					.println("\n\n\t****************************************\n\tLst count is 0 \n\t");
			result = "AttachementException";
			return (result);
		}

		FileItem file_item = null;
		FileItem selected_file_item = null;

		// Checking for form fields - Start
		for (int i = 0; i < lst.size(); i++) {
			file_item = (FileItem) lst.get(i);
			String fieldName = file_item.getFieldName();

			// Check for the attributes for user selected file - Start
			if (fieldName.equals("filename")) {
				selected_file_item = file_item;
				// String file_name=file_item.getString();
				// file_name=request.getParameter("filename");
				file_name = file_item.getName(); // Getting the file name
				System.out
						.println("\n\n\t****************************************\n\tThe selected file item's file name is : "
								+ file_name + "\n\t");
				break;
			}
			// Check for the attributes for user selected file - End
		}
		// Checking for form fields - End

		// Uploading the file content - Start
		if (selected_file_item == null) // If selected file item is null
		{
			System.out
					.println("\n\n\t****************************************\n\tThe selected file item is null\n\t");
			result = "AttachementException";
			return (result);
		}

		byte[] file_bytes = selected_file_item.get();
		byte[] extract_bytes = new byte[file_bytes.length];

		for (int l = 0; l < file_bytes.length; l++)
			extract_bytes[l] = file_bytes[l];
		// ByteBuffer byteWrap=ByteBuffer.wrap(file_bytes);
		// byte[] extract_bytes;
		// byteWrap.get(extract_bytes);

		// System.out.println("\n\n\t****************************************\n\tExtract succeeded :content are : \n\t");

		if (extract_bytes == null) {
			System.out
					.println("\n\n\t****************************************\n\tExtract bytes is null\n\t");
			result = "AttachementException";
			return (result);
		}

		/*
		 * for(int k=0;k<extract_bytes.length;k++)
		 * System.out.print((char)extract_bytes[k]);
		 */

		// String target_file_name="/hot-deploy/productionmgntSystem"
		// Creation & writing to the file in server - Start
		try {
			file_name = "C:/tmp/" + file_name;

			FileOutputStream fout = new FileOutputStream(file_name);
			System.out
					.println("\n\n\t****************************************\n\tAfter creating outputstream, file_name = "
							+ file_name);
			fout.flush();
			fout.write(extract_bytes);
			fout.flush();
			fout.close();
		} catch (Exception ioe_ex) {
			System.out
					.println("\n\n\t****************************************\n\tIOException occured on file writing");
			ioe_ex.printStackTrace();
			result = "AttachementException";
			return (result);
		}
		// Creation & writing to the file in server - End

		System.out
				.println("\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - end\n\t");
		return ("AttachementSuccess");
		// Uploading the file content - End
	}

	public static Map<String, Object> getSumGenerateDataTest(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try{
			Debug.log("getSumGenerateDataTest, START..................");
			double t0 = System.currentTimeMillis();
			List<GenericValue> lst = delegator.findList("ViewTestSumGroupBy"
					,null,null,null,null,false);
			t0 = System.currentTimeMillis() - t0;
			Debug.log("getSumGenerateDataTest, GOT " + lst.size() + ", time = " + (t0*0.001) + " (s)");
			retSucc.put("result", lst);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> generateDataTest(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		String size = (String)context.get("size");
		int MAX = 100000;
		try{
			MAX = Integer.valueOf(size);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		Debug.log("GENERATE, MAX = " + MAX);
		
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		List<String> glAccounts = new ArrayList<String>();
		for(int i = 0; i < 500; i++){
			glAccounts.add(i + "");
		}
		List<String> partyIds = new ArrayList<String>();
		for(int i = 0; i < 100; i++){
			partyIds.add(i + "");
		}
		
		java.util.Random R = new java.util.Random();
		int count = 0;
		double t0 = System.currentTimeMillis();
		try{
			
			for(int i = 0; i < MAX; i++){
				int idx = R.nextInt(glAccounts.size());
				String glAccountId = glAccounts.get(idx);
				idx = R.nextInt(partyIds.size());
				String partyId = partyIds.get(idx);
				
				String id = delegator.getNextSeqId("TestSumGroupBy1");
				int drAmount = R.nextInt(1000000) + 100000;
				int crAmount = R.nextInt(1000000) + 100000;
				BigDecimal bd = new BigDecimal(drAmount);
				BigDecimal bc = new BigDecimal(crAmount);
				//String transactionDate = "dd-mm-yyyy";
				java.sql.Timestamp transactionDate =  new java.sql.Timestamp(System.currentTimeMillis());
				int d = R.nextInt(100) + 0;
				transactionDate.setDate(transactionDate.getDate() + d);
				GenericValue gv = delegator.makeValue("TestSumGroupBy1");
				gv.put("id", id);
				gv.put("glAccountId", glAccountId);
				gv.put("partyId", partyId);
				gv.put("drAmount", bd);
				gv.put("crAmount", bc);
				gv.put("transactionDate", transactionDate);
				delegator.create(gv);
				count++;
				Debug.log("GENERATE item " + i + ", finished");
			}
			t0 = System.currentTimeMillis() - t0;
			
					
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		retSucc.put("result", count + "");
		Debug.log("GENERATE FINISHED, count = " + count + ", time = " + (t0*0.001) + " (s)");
		return retSucc;
	}
	
	public static Map<String, Object> computeSum(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		String arr = (String)context.get("array");
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Debug.log("API, arr = " + arr);
		String s = arr + arr + arr;
		retSucc.put("sum", s);
		return retSucc;
	}
	
	public static Map<String, Object> createARoute(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String date = (String) context.get("date");
		String shipperId = (String) context.get("shipper");
		int i_seq = (Integer) context.get("seq");

		long seq = i_seq;

		System.out.println("RouteService::createARoute, date = " + date
				+ ", shipperId = " + shipperId + ", seq = " + seq);
		GenericValue gv = delegator.makeValue("Routes");
		System.out.println("RouteService::createARoute, create gv OK");

		gv.put("id", delegator.getNextSeqId("Routes"));

		System.out.println("RouteService::createARoute, gv.put(id) OK");

		String id = (String) gv.get("id");

		System.out.println("RouteService::createARoute, retrieve id = " + id);

		try {
			gv.put("date", date);
			gv.put("shipperId", shipperId);
			gv.put("seq", seq);

			delegator.create(gv);
			System.out.println("RouteService::createARoute, FINISHED");

		} catch (Exception ex) {
			System.out.println("RouteService::createARoute, EXCEPTION");
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}

		retSucc.put("routeid", id);

		return retSucc;
	}

}
