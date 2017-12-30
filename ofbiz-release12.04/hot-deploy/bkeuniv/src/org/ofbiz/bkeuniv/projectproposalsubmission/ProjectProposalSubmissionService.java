package org.ofbiz.bkeuniv.projectproposalsubmission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.paperdeclaration.PaperDeclarationService;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.utils.BKEunivUtils;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;

public class ProjectProposalSubmissionService {
	public static String STATUS_CREATED = "CREATED";
	public static String STATUS_CANCELLED = "CANCELLED";
	
	public static String module = ProjectProposalSubmissionService.class.getName();

	public static String dataFolder = "." + File.separator + "euniv-deploy";

	public static String establishFullFilename(String staffId, String name) {
		String path = dataFolder + File.separator + staffId
				+ File.separator + "projects";
		Debug.log(module + "::establishFullFilename, path = " + path);
		String fullname = path + File.separator + name;

		File file = new File(path);
		
		if (! file.exists()){
		
			file.mkdirs();
			Debug.log(module + "::establishFullFilename, folder NOT exist --> Create folder\n\t");
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		
		
		
		
		return fullname;
	}

	public static void addProductProject(HttpServletRequest request, 
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		
		String researchProjectProposalId = request.getParameter("researchProjectProposalId");
		String researchProductTypeId = request.getParameter("researchProductTypeId");
		String squantity = request.getParameter("quantity");
		Long quantity = Long.valueOf(squantity);
		
		Debug.log(module + "::addWorkpackageProject, researchProjectProposalId = " + researchProjectProposalId +
				", researchProductTypeId = " + researchProductTypeId + ", quantity = " + quantity);
		try{
			GenericValue gv = delegator.makeValue("ResearchProposalProduct");
			String researchProductId = delegator.getNextSeqId("ResearchProposalProduct");
			
			gv.put("researchProjectProposalId", researchProjectProposalId);
			gv.put("researchProductId", researchProductId);
			gv.put("researchProductTypeId", researchProductTypeId);
			gv.put("quantity", quantity);
			
			delegator.create(gv);
			
			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void addWorkpackageProject(HttpServletRequest request, 
			HttpServletResponse response){
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		
		String researchProjectProposalId = request.getParameter("researchProjectProposalId");
		String staffId = request.getParameter("staffId");
		String content = request.getParameter("content");
		String sworkingdays = request.getParameter("workingdays");
		Long workingdays = Long.valueOf(sworkingdays);
		String sbudget = request.getParameter("budget");
		BigDecimal budget = new BigDecimal(sbudget);
		
		Debug.log(module + "::addWorkpackageProject, researchProjectProposalId = " + researchProjectProposalId +
				", staffId = " + staffId + ", content = " + content + ", workingdays = " + 
				workingdays + ", sbudget = " + sbudget + ", budget = " + budget);
		try{
			GenericValue gv = delegator.makeValue("ResearchProposalContentItem");
			String contentItemSeq = delegator.getNextSeqId("ResearchProposalContentItem");
			
			gv.put("researchProjectProposalId", researchProjectProposalId);
			gv.put("contentItemSeq", contentItemSeq);
			gv.put("staffId", staffId);
			gv.put("content", content);
			gv.put("workingDays", workingdays);
			gv.put("budget", budget);
			
			delegator.create(gv);
			
			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static Map<String, Object> getProjectProposalContentItem(DispatchContext ctx, 
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context.get("researchProjectProposalId") + "";
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("researchProjectProposalId",EntityOperator.EQUALS,researchProjectProposalId));
			List<GenericValue> list = delegator.findList("ResearchProposalContentItemView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			Debug.log(module + "::getProjectProposalContentItem, list.sz = " + list.size());
			retSucc.put("projectProposalContentItems", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getProjectProposalProducts(DispatchContext ctx, 
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context.get("researchProjectProposalId") + "";
		Debug.log(module + "::getProjectProposalProducts, researchProjectProposalId = " + researchProjectProposalId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("researchProjectProposalId",EntityOperator.EQUALS,researchProjectProposalId));
			List<GenericValue> list = delegator.findList("ResearchProposalProductView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			Debug.log(module + "::getProjectProposalProducts, list.sz = " + list.size());
			retSucc.put("projectProposalProducts", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getMembersOfProjectProposal(DispatchContext ctx, 
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context.get("researchProjectProposalId") + "";
		Delegator delegator = ctx.getDelegator();
		try{
			Debug.log(module + "::getMembersOfProjectProposal, researchProjectProposalId = " + researchProjectProposalId);
			
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("researchProjectProposalId",EntityOperator.EQUALS,researchProjectProposalId));
			List<GenericValue> list = delegator.findList("ProjectProposalRoleView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			Debug.log(module + "::getMembersOfProjectProposal, list.sz = " + list.size());
			retSucc.put("members", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	public static Map<String, Object> getListProjectProposalRoleTypes(DispatchContext ctx, 
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			List<GenericValue> list = delegator.findList("ProjectProposalRoleType", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			retSucc.put("projectProposalRoleTypes", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectProposalProductTypes(DispatchContext ctx, 
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			List<GenericValue> list = delegator.findList("ResearchProductType", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			retSucc.put("projectProposalProductTypes", list);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectCalls(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try{
			Delegator delegator = ctx.getDelegator();
			
			List<EntityCondition> conds = FastList.newInstance();
			
			List<GenericValue> projectCalls = delegator.findList("ProjectCall", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			retSucc.put("projectCalls", projectCalls);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getProjectProposal(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context.get("researchProjectProposalId") + "";
		Delegator delegator = ctx.getDelegator();
		
		Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
		
		Debug.log(module + "::getProjectProposal, researchProjectProposalId = " + researchProjectProposalId);
		
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("researchProjectProposalId", EntityOperator.EQUALS,researchProjectProposalId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,STATUS_CANCELLED));
			
			List<GenericValue> prj = delegator.findList("ResearchProjectProposalView", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			
			if(prj != null && prj.size() > 0)
				retSucc.put("projectproposal", prj.get(0));
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
		
	}
	
	public static Map<String, Object> getProjectProposalsOfStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffId = (String)context.get("staffId");
		Delegator delegator = ctx.getDelegator();
		
		Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
		
		if(staffId == null)
			staffId = (String) userLogin.get("userLoginId");
		
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS,staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,STATUS_CANCELLED));
			
			List<GenericValue> prj = delegator.findList("ResearchProjectProposalView", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			
			
			retSucc.put("projectproposals", prj);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
		
	}

	public static Map<String, Object> getListProjectProposals(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffId = (String)context.get("staffId");
		Delegator delegator = ctx.getDelegator();
		
		Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
		
		if(staffId == null)
			staffId = (String) userLogin.get("userLoginId");
		
		try{
			List<EntityCondition> conds = FastList.newInstance();
			//conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS,staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,STATUS_CANCELLED));
			
			List<GenericValue> prj = delegator.findList("ResearchProjectProposalView", 
					EntityCondition.makeCondition(conds), 
					null, 
					null, 
					null, 
					false);
			
			
			retSucc.put("projectproposals", prj);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
		
		
	}
	
	public static Map<String, Object> createProjectProposalContentItem(DispatchContext ctx, 
			Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = (String) context.get("researchProjectProposalId");
		String content = (String)context.get("content");
		BigDecimal amount = (BigDecimal)context.get("budget");
		String sDays = (String)context.get("workingDays");
		
		List<Object> staffIds = (List<Object>)context.get("staffId[]");
		Delegator delegator = ctx.getDelegator();
		
		try{
			GenericValue gv = delegator.makeValue("ResearchProposalContentItem");
			gv.put("researchProjectProposalId", researchProjectProposalId);
			if(staffIds != null && staffIds.size() > 0)
				gv.put("staffId", (String)staffIds.get(0));
			gv.put("content", content);
			gv.put("workingDays", Long.valueOf(sDays));
			gv.put("budget", amount);
			
			delegator.store(gv);
			
			retSucc.put("projectProposalContentItem", gv);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> createAMemberOfProjectProposal(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String researchProjectProposalId = (String)context.get("researchProjectProposalId");
		List<Object> staffIds = (List<Object>)context.get("staffId[]");
		List<Object> roleTypeIds = (List<Object>)context.get("roleTypeId[]");
		Debug.log(module + "::createAMemberOfProjectProposal, researchProjectProposalId = " + researchProjectProposalId);
		Delegator delegator = ctx.getDelegator();
		
		try{
			String staffName = "";
			String roleTypeName = "";
			
			GenericValue gv = delegator.makeValue("ProjectProposalRole");
			gv.put("researchProjectProposalId", researchProjectProposalId);
			if(staffIds != null && staffIds.size() > 0){
				gv.put("staffId", (String)staffIds.get(0));
				GenericValue st = delegator.findOne("Staff", UtilMisc.toMap("staffId",gv.get("staffId")),false);
				staffName = (String)st.get("staffName");
			}
			if(roleTypeIds != null && roleTypeIds.size() > 0){
				gv.put("roleTypeId", (String)roleTypeIds.get(0));
				GenericValue rt = delegator.findOne("ProjectProposalRoleType", 
						UtilMisc.toMap("roleTypeId",gv.get("roleTypeId")),false);
				roleTypeName = (String)rt.get("roleTypeName");
			}
			
			Timestamp now = UtilDateTime.nowTimestamp();
			
			gv.put("fromDate", now);
			
			delegator.create(gv);
			
			GenericValue rg = delegator.makeValue("ProjectProposalRoleView");
			
			rg.put("researchProjectProposalId", researchProjectProposalId);
			rg.put("staffId", (String)gv.get("staffId"));
			rg.put("roleTypeId",(String)gv.get("roleTypeId"));
			rg.put("staffName", staffName);
			rg.put("roleTypeName", roleTypeName);
			
			retSucc.put("members", rg);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	
	}
	
	public static Map<String, Object> createAProjectProposalSubmission(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
		
		String	staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		String researchProjectProposalName = (String)context.get("researchProjectProposalName");
		List<Object> listProjectCalls = (List<Object>)context.get("projectCallId[]");
		List<Object> listFaculties = (List<Object>)context.get("facultyId[]");
		
		try{
			String partyId = delegator.getNextSeqId("Party");
			
			Map<String, Object> info = UtilMisc.toMap("partyId", partyId);
			GenericValue pty = delegator.makeValue("Party",info);
			//pty.put("partyId", partyId);
			delegator.create(pty);
			
			GenericValue pps = delegator.makeValue("ResearchProjectProposal");
			String researchProjectProposalId = delegator.getNextSeqId("ResearchProjectProposal");
			pps.put("researchProjectProposalId", researchProjectProposalId);
			pps.put("researchProjectProposalName", researchProjectProposalName);
			pps.put("createStaffId", staffId);
			pps.put("partyId", partyId);
			pps.put("statusId", STATUS_CREATED);
			
			if(listProjectCalls != null && listProjectCalls.size() > 0){
				pps.put("projectCallId", listProjectCalls.get(0));
				//GenericValue pc = delegator.findOne("ProjectCall", UtilMisc.toMap("projectCallId", listProjectCalls.get(0)), false);
			}
			if(listFaculties != null && listFaculties.size() > 0){
				pps.put("facultyId", listFaculties.get(0));
			}
			
			
			delegator.create(pps);
			
			
			retSucc.put("projectproposals", pps);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> updateAProjectProposalSubmission(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
		
		String	staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		String researchProjectProposalId = (String)context.get("researchProjectProposalId");
		
		
		String researchProjectProposalName = (String)context.get("researchProjectProposalName");
		List<Object> listProjectCalls = (List<Object>)context.get("projectCallId[]");
		List<Object> listFaculties = (List<Object>)context.get("facultyId[]");
		
		
		try{
			GenericValue pps = delegator.findOne("ResearchProjectProposal", 
					UtilMisc.toMap("researchProjectProposalId",researchProjectProposalId),false);
			
			pps.put("researchProjectProposalName", researchProjectProposalName);
			
			if(listProjectCalls != null && listProjectCalls.size() > 0){
				pps.put("projectCallId", listProjectCalls.get(0));
			}
			if(listFaculties != null && listFaculties.size() > 0){
				pps.put("facultyId", listFaculties.get(0));
			}
			
			delegator.store(pps);
			
			GenericValue ppsv = delegator.findOne("ResearchProjectProposalView", 
					UtilMisc.toMap("researchProjectProposalId",researchProjectProposalId),false);
			
			retSucc.put("projects", ppsv);
			
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> deleteAProjectProposalSubmission(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>) context.get("userLogin");
		
		String	staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		String researchProjectProposalId = (String)context.get("researchProjectProposalId");
		
		try{
			GenericValue pps = delegator.findOne("ResearchProjectProposal", 
					UtilMisc.toMap("researchProjectProposalId",researchProjectProposalId),false);
			pps.put("statusId",STATUS_CANCELLED);
			
			delegator.store(pps);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getLocalizedMessage());
		}
		return retSucc;
	}
	
	public static String getExtension(String fn) {
		Debug.log(module + "::getExtension, fn = " + fn);
		String[] s = fn.split("\\.");
		Debug.log(module + "::getExtension, fn = " + fn + ", s.length = "
				+ s.length);
		return s[s.length - 1];
	}

	public static void uploadFileProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> m = FastMap.newInstance();
		
		//ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory(10240, new File(new File("runtime"), "tmp")));           //Creation of servletfileupload
        Debug.log(module + "::uploadFileProposal \n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - start\n\t");
        ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory());           //Creation of servletfileupload
        List lst = null;
        String result="AttachementException";
        String file_name="";
        String researchProjectProposalId = "";
        
        try 
        {
            lst = fu.parseRequest(request);
        }
        catch (FileUploadException fup_ex) 
        {
            Debug.log(module + "::uploadFileProposal \n\n\t****************************************\n\tException of FileUploadException \n\t");
            fup_ex.printStackTrace();
            result="AttachementException";
            m.put("result", result);
    		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
    				response, 200);
            return;
        }

        if(lst.size()==0)        //There is no item in lst
        {
            Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\tLst count is 0 \n\t");
            result="AttachementException";
            m.put("result", result);
    		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
    				response, 200);
            return;
        }


        FileItem file_item = null;
        FileItem selected_file_item=null;

        //Checking for form fields - Start
            for (int i=0; i < lst.size(); i++) 
            {
                file_item=(FileItem)lst.get(i);
                String fieldName = file_item.getFieldName();
                
                switch (fieldName) {
				case "file":
					selected_file_item=file_item;
					
                    file_name=file_item.getName();             //Getting the file name
                    Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\tThe selected file item's file name is : "+file_name+"\n\t");
					break;
				case "researchProjectProposalId":
					researchProjectProposalId = file_item.getString();
					Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\n researchProjectProposalId id : "
					+researchProjectProposalId+"\n\t");
					break;
				}
                
            }
        //Checking for form fields - End

        //Uploading the file content - Start
            if(selected_file_item==null)                    //If selected file item is null
            {
                Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\tThe selected file item is null\n\t");
                result="AttachementException";
                m.put("result", result);
        		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
        				response, 200);
                return;
            }

            byte[] file_bytes=selected_file_item.get();
            byte[] extract_bytes=new byte[file_bytes.length];

            for(int l=0;l<file_bytes.length;l++)
                extract_bytes[l]=file_bytes[l];
            //ByteBuffer byteWrap=ByteBuffer.wrap(file_bytes);
            //byte[] extract_bytes;
            //byteWrap.get(extract_bytes);


            //System.out.println("\n\n\t****************************************\n\tExtract succeeded :content are : \n\t");

            //Creation & writing to the file in server - End

    		
    		Delegator delegator = (Delegator) request.getAttribute("delegator");
    		Debug.log(module + "::uploadFile, researchProjectProposalId = " + researchProjectProposalId);
    		try {
    			GenericValue gv = delegator.findOne("ResearchProjectProposal", false,
    					UtilMisc.toMap("researchProjectProposalId", researchProjectProposalId));
    			String staffId = (String) gv.get("createStaffId");

    			Debug.log(module + "::uploadFileProposal, filename = " + file_name
    					+ ", staffId = " + staffId);
    			String ext = getExtension(file_name);
    			java.util.Date currentDate = new java.util.Date();
    			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
    					"HHmmssddMMyyyy");
    			String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

    			String filenameDB = sCurrentDate + "." + ext;
    			String fullFileName = establishFullFilename(staffId, filenameDB);

    			Debug.log(module + "::uploadFileProposal, filename = " + file_name
    					+ ", researchProjectProposalId = " + researchProjectProposalId + ", extension = " + ext
    					+ ", filenameDB = " + filenameDB + ", fullFileName = "
    					+ fullFileName);
    			
    			FileOutputStream fout=new FileOutputStream(fullFileName);
                Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\tAfter creating outputstream");
                fout.flush();
                fout.write(extract_bytes);
                fout.flush();
                fout.close();

    			gv.put("sourceFileUpload", filenameDB);
    			delegator.store(gv);
    			
    			Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - end\n\t");
                m.put("result", "AttachementSuccess");
        		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
        				response, 200);
        		
    		} catch (Exception ioe_ex) {
    			Debug.log(module + "::uploadFileProposal\n\n\t****************************************\n\tIOException occured on file writing");
                ioe_ex.printStackTrace();
                result="AttachementException";
                m.put("result", result);
        		BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
        				response, 200);
                return;
    		}	
    
	}
	public static void downloadFileProposal(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String researchProjectProposalId = request.getParameter("researchProjectProposalId");

		// String filename = "HDSD.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			GenericValue gv = delegator.findOne("ResearchProjectProposal", false,
					UtilMisc.toMap("researchProjectProposalId", researchProjectProposalId));
			String staffId = (String) gv.get("createStaffId");
			String filenameDB = (String) gv.get("sourceFileUpload");
			String fullFileName = establishFullFilename(staffId, filenameDB);

			Debug.log(module + "::downloadFileProposal, researchProjectProposalId = " + researchProjectProposalId
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

}
