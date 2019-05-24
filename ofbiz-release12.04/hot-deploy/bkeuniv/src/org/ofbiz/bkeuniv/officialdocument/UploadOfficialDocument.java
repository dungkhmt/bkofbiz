package org.ofbiz.bkeuniv.officialdocument;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastMap;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.config.ConfigParams;
import org.ofbiz.bkeuniv.paperdeclaration.PaperDeclarationService;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.utils.BKEunivUtils;

public class UploadOfficialDocument {
	public static String module = PaperDeclarationService.class.getName();
	//public static String dataFolder = "." + File.separator + "euniv-deploy";
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final Charset ISO = Charset.forName("ISO-8859-1");

	
	public static String establishFullFilename(String staffId, String name) {
		
		String path = ConfigParams.dataFolder + File.separator + staffId + File.separator
				+ "officialdocument";
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
	
	public static String getExtension(String fn) {
		Debug.log(module + "::getExtension, fn = " + fn);
		String[] s = fn.split("\\.");
		Debug.log(module + "::getExtension, fn = " + fn + ", s.length = "
				+ s.length);
		return s[s.length - 1];
	}
	
	public static void uploadFileOfficialDocument(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> m = FastMap.newInstance();

		System.out
				.println("\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - start\n\t");
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory()); // Creation
																					// of
																					// servletfileupload
		List lst = null;
		String result = "AttachementException";
		String file_name = "";
		String officialDocumentTypeId = "";
		String officialDocumentName = "";

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
			case "officialDocumentName":
				officialDocumentName = file_item.getString();
				System.out
						.println("\n\n\t****************************************\n\n officialDocumentName : "
								+ officialDocumentName + "\n\t");
				break;
			case "officialDocumentTypeId":
				officialDocumentTypeId = file_item.getString();
				System.out
						.println("\n\n\t****************************************\n\n officialDocumentTypeId : "
								+ officialDocumentTypeId + "\n\t");
				break;
			}

		}
		
		if(officialDocumentName.isEmpty()||officialDocumentName.equals("")) {
			System.out
			.println("\n\n\t****************************************\n\t officialDocumentName is Empty \n\t");
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}
		
		if(officialDocumentTypeId.isEmpty()||officialDocumentTypeId.equals("")) {
			System.out
			.println("\n\n\t****************************************\n\t officialDocumentTypeId is Empty \n\t");
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		} else {
			officialDocumentName = new String(officialDocumentName.getBytes(ISO), UTF_8);
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

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Debug.log(module + "::uploadFile, officialDocumentName = " + officialDocumentName);
		try {
			GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
			String staffId = (String) staff.get("staffId");

			String ext = getExtension(file_name);
			java.util.Date currentDate = new java.util.Date();
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			
			String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

			String filenameDB = sCurrentDate + "." + ext;
			String fullFileName = establishFullFilename(staffId, filenameDB);

			Debug.log(module + "::uploadFile, filename = " + file_name
					+ ", officialDocumentName = " + officialDocumentName + ", extension = " + ext
					+ ", filenameDB = " + filenameDB + ", fullFileName = "
					+ fullFileName);

			FileOutputStream fout = new FileOutputStream(fullFileName);
			System.out
					.println("\n\n\t****************************************\n\tAfter creating outputstream");
			fout.flush();
			fout.write(extract_bytes);
			fout.flush();
			fout.close();
			
			GenericValue gv = delegator.makeValue("OfficialDocuments");

			gv.put("officialDocumentId", delegator.getNextSeqId("OfficialDocuments"));

			try {
				gv.put("staffId", staffId);
				gv.put("officialDocumentName", officialDocumentName);
				gv.put("officialDocumentTypeId", officialDocumentTypeId);
				gv.put("sourceFileName", filenameDB);
				
				gv.put("uploadDate", new java.sql.Date(currentDate.getTime()));

				delegator.create(gv);
			} catch (Exception ex) {
				ex.printStackTrace();
				result = "AttachementException";
				m.put("result", result);
				BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
						response, 200);
				return;
			}

			System.out
					.println("\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - end\n\t");
			m.put("result", "AttachementSuccess");
			m.put("officialDocumentId", gv.get("officialDocumentId"));
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
	

	public static String updateOfficialDocument(HttpServletRequest request,
			HttpServletResponse response) throws GenericEntityException{
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
		String userLoginId = (String) staff.get("staffId");
		 
		
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory());
		
		
		
		FileItem file = null;
		String officialDocumentId = null;
		String officialDocumentName = null;
		String officialDocumentTypeId = null;
		java.util.Date currentDate = new java.util.Date();
		String filenameDB = "";
		
		try {
			
			List<FileItem> lst = fu.parseRequest(request);
			
			FileItem file_item = null;
			FileItem selected_file_item = null;

			// Checking for form fields - Start
			for (int i = 0; i < lst.size(); i++) {
				file_item = (FileItem) lst.get(i);
				String fieldName = file_item.getFieldName();

				switch (fieldName) {
				case "file":
					file = file_item;
					break;
				case "officialDocumentId":
					officialDocumentId = file_item.getString();
					break;
				case "officialDocumentName":
					officialDocumentName = file_item.getString();
					break;
				case "officialDocumentTypeId":
					officialDocumentTypeId = file_item.getString();
					break;
				}

			}
			
			if(officialDocumentId==null||officialDocumentName==null||officialDocumentTypeId==null) {
				retSucc.put("message", "require officialDocumentId, officialDocumentName, officialDocumentTypeId");
			}
			
			
			if(file != null && file.getSize() != 0) {
				String file_name = file.getName();
				String ext = getExtension(file_name);
	
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				String sCurrentDate = dateformatyyyyMMdd.format(currentDate);
				
				filenameDB = sCurrentDate + "." + ext;
				String fullFileName = establishFullFilename(userLoginId, filenameDB);
				
				Debug.log(module + "::uploadFile, filename = " + file_name
				+ ", officialDocumentName = " + officialDocumentName + ", extension = " + ext
				+ ", filenameDB = " + filenameDB + ", fullFileName = "
				+ fullFileName);
				
				byte[] file_bytes = file.get();
				byte[] extract_bytes = new byte[file_bytes.length];
				
				for (int l = 0; l < file_bytes.length; l++)
				extract_bytes[l] = file_bytes[l];
				
				FileOutputStream fout = new FileOutputStream(fullFileName);
				System.out
				.println("\n\n\t****************************************\n\tAfter creating outputstream");
				fout.flush();
				fout.write(extract_bytes);
				fout.flush();
				fout.close();
			}
		
		
			GenericValue gv = delegator.findOne("OfficialDocuments", false, UtilMisc.toMap("officialDocumentId",officialDocumentId));
			
			if(gv != null){
				officialDocumentName = new String(officialDocumentName.getBytes(ISO), UTF_8);
				gv.put("officialDocumentName", officialDocumentName);
				gv.put("officialDocumentTypeId", officialDocumentTypeId);
				
				if(filenameDB.equals("")) {
					gv.put("sourceFileName", filenameDB);
					gv.put("uploadDate", new java.sql.Date(currentDate.getTime()));
					gv.put("staffId", userLoginId);
					
				}
				
				delegator.store(gv);
				
        		retSucc.put("message", "updated record with id: " + officialDocumentId);
        	} else {
        		retSucc.put("message", "not found record with id: " + officialDocumentId);
        	}
		} catch (Exception e) {
			e.printStackTrace();
        	ServiceUtil.returnError(e.getMessage());
        	return "error";
		}
		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(retSucc), response, 200);
		
		return "success";
	}
	
	public static Map<String, Object> deleteOfficialDocument(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String officialDocumentId = (String)context.get("officialDocumentId");
        try{
        	GenericValue gv = delegator.findOne("OfficialDocuments", UtilMisc.toMap("officialDocumentId",officialDocumentId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + officialDocumentId);
        	} else {
        		retSucc.put("result", "not found record with id: " + officialDocumentId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	

}
