package org.ofbiz.bkeuniv.officialdocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastMap;

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
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class GetDataOfficialDocument {
	public static Map<String,Object> JQGetListOfficialDocument(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator officialDocuments = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0];
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"staffId", "staffName", "officialDocumentTypeName", "officialDocumentName", "officialDocumentId"}; 
				
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
			
		 	officialDocuments = delegator.find("OfficialDocumentsView", condition, null, null, sort, opts);
			
			result.put("listIterator", officialDocuments);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Debug Error get list OfficialDocuments");
		} finally {
//			if(staffs != null){
//				staffs.close();
//			}
		}
		return result;
	}
	
	public static Map<String,Object> JQGetListOfficialDocumentType(DispatchContext dpct,Map<String,?extends Object> context) throws GenericEntityException{
		Delegator delegator = (Delegator) dpct.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String,String[]> parameters = (Map<String,String[]>) context.get("parameters");
		Map<String,Object> result = FastMap.newInstance();
		EntityListIterator officialDocumentTypes = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null  ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
			
			if(parameters.containsKey("q")) {
				System.out.println("debug :::::::::: not null");
				String q = (String)parameters.get("q")[0];
				System.out.println("1. debug ::::::::::" +q);
				String[] searchKeys = {"officialDocumentTypeName", "officialDocumentTypeId"}; 
				
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
			
		 	officialDocumentTypes = delegator.find("OfficialDocumentTypes", condition, null, null, sort, opts);
			
			result.put("listIterator", officialDocumentTypes);
			
		} catch (Exception e) {
			Debug.log(e.getMessage());
			return ServiceUtil.returnError("Debug Error get list officialDocumentTypes");
		} finally {
//			if(staffs != null){
//				staffs.close();
//			}
		}
		return result;
	}
	
	public static void downloadFileOfficialDocument(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String officialDocumentId = request
				.getParameter("officialDocumentId");

		
		String type = request
				.getParameter("type");
		
		if(type==null||!type.equals("inline")) {
			type="attachment";
		}

		
		// String filename = "HDSD.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			GenericValue gv = delegator.findOne("OfficialDocuments",
					false, UtilMisc.toMap("officialDocumentId",
							officialDocumentId));
			String staffId = (String) gv.get("staffId");
			String filenameDB = (String) gv.get("sourceFileName");
			String fullFileName = ProjectProposalSubmissionServiceUtil
					.establishFullFilename(staffId, filenameDB, "officialdocument");

			Debug.log("::downloadFileOfficialDocument, officialDocumentId = "
					+ officialDocumentId + ", staffId = " + staffId
					+ ", filenameDB = " + filenameDB + ", fullFileName = "
					+ fullFileName);

			// File f = new File("C:/DungPQ/olbius/tmp/" + filename);
			File f = new File(fullFileName);
			FileInputStream fi = new FileInputStream(f);
			byte[] bytes = new byte[(int) f.length()];
			fi.read(bytes);

			response.setHeader("content-disposition", type+";filename="
					+ filenameDB);
			response.setContentType("application/pdf");
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
}
