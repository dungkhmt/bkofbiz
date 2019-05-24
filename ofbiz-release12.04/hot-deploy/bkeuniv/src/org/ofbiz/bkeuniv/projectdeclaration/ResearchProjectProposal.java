package org.ofbiz.bkeuniv.projectdeclaration;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.paperdeclaration.PaperDeclarationUtil;
import org.ofbiz.bkeuniv.projectproposalsubmission.ProjectProposalSubmissionServiceUtil;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class ResearchProjectProposal {
	public static String module = ProjectDeclaration.class.getName();
	private final static String[] arrAtt = new String[] {"researchProjectProposalId", "researchProjectProposalCode",
		"partyId", "createStaffId", "projectCallId", "projectCategoryId", "researchProjectProposalName",
		"totalBudget", "statusId", "approvedByStaffId", "sourceFileUpload", "facultyId", "startDate",
		"endDate", "deliverable", "materialBudget", "evaluationOpenFlag", "createStaffName",
		"projectCallName", "projectCategoryName", "facultyName", "statusName",
		"roleTypeId","roleTypeName"
	};
	
	@SuppressWarnings({ "unchecked" })
	public static void updateCVProjects(HttpServletRequest request,
			HttpServletResponse response) {
		String json = (String)request.getParameter("json");
		System.out.println(module + "::updateCVProjects, json = " + json);
		
		GenericDelegator delegator = (GenericDelegator) DelegatorFactory.getDelegator("default");
		
		try{
			JSONArray L = JSONArray.fromObject(json);
			for(int i = 0; i< L.size(); i++){
				JSONObject o = (JSONObject)L.get(i);
				String projectProposalMemberId = o.getString("projectProposalMemberId");
				String seq = o.getString("seq");
				if(seq == null || seq.equals("")){
					ProjectDeclarationUtil.removeProjectCV(delegator, projectProposalMemberId);
					Debug.log(module + "::updateCVProjects, remove " + projectProposalMemberId + "-----" + seq);
				}else{
					ProjectDeclarationUtil.addUpdateProjectCV(delegator, projectProposalMemberId, Long.valueOf(seq));
					Debug.log(module + "::updateCVProjects, addUpdate " + projectProposalMemberId + "-----" + seq);
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static Map<String, Object> JQGetProjectsOfStaffCV(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = (Delegator) ctx.getDelegator();
		List<EntityCondition> listAllConditions = new ArrayList<EntityCondition>();
		EntityCondition filter = (EntityCondition) context.get("filter");
		List<String> sort = (List<String>) context.get("sort");
		EntityFindOptions opts = (EntityFindOptions) context.get("opts");
		Map<String, String[]> parameters = (Map<String, String[]>) context
				.get("parameters");
		
		Map<String, Object> result = FastMap.newInstance();
		List<GenericValue> projects = null;
		try {
			GenericValue userLogin = (GenericValue) context.get("userLogin");
			String userLoginId = userLogin.getString("userLoginId");
			opts = opts != null ? opts : new EntityFindOptions();
			opts.setDistinct(true);
			opts.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);

			if (parameters.containsKey("q")) {
				//System.out.println("debug :::::::::: not null");
				String q = (String) parameters.get("q")[0].trim();
				//System.out.println("1. debug ::::::::::" + q);
				String[] searchKeys = { "staffName", "researchProjectProposalName" };

				List<EntityCondition> condSearch = new ArrayList<EntityCondition>();
				for (String key : searchKeys) {
					EntityCondition condition = EntityCondition.makeCondition(
							EntityFunction.UPPER_FIELD(key),
							EntityOperator.LIKE,
							EntityFunction.UPPER("%" + q + "%"));
					condSearch.add(condition);
				}
				listAllConditions.add(EntityCondition.makeCondition(condSearch,
						EntityOperator.OR));
			}
			if (filter != null) {

				listAllConditions.add(filter);
			}
			
			
			

			listAllConditions.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, userLoginId));
			//listAllConditions.add(EntityCondition.makeCondition("statusId",
			//		EntityOperator.EQUALS, "RUNNING"));

			
			EntityCondition condition = EntityCondition.makeCondition(
					listAllConditions, EntityOperator.AND);

			//System.out.println("4. debug ::::::::::" + userLoginId);
			
			projects = delegator.findList("ProjectProposalMemberViewSequenceInCV", condition, null, sort,
					opts, false);

			List<GenericValue> ret_projects = FastList.newInstance();
			for (GenericValue p : projects) {
				if (p.get("statusId") == null
						|| p.get("statusId").equals("RUNNING")
						|| p.get("statusId").equals("COMPLETE")
						) {
					ret_projects.add(p);
				}
			}

			//for (GenericValue gv : ret_papers) {
			//	Debug.log(module + "::getPapersOfStaff, paper "
			//			+ gv.get("paperName"));
			//}
			//Debug.log(module + "::getPapersOfStaff, papers.sz = "
			//		+ ret_papers.size());
			
			
			result.put("listIterator", ret_projects);

		} catch (Exception e) {
			Debug.log(e.getMessage());
			e.printStackTrace();
			return ServiceUtil.returnError("Error get list ProjectView");
		}

		return result;
	}

	public static Map<String, Object> getProjectsOfStaff(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffId = (String)context.get("staffId");
		if(staffId == null){
			GenericValue userLogin = (GenericValue)context.get("userLogin");
			staffId = userLogin.getString("userLoginId");
		}
		Debug.log(module + "::getProjectsOfStaff, staffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId",EntityOperator.EQUALS,staffId));
			
			List<GenericValue> lst = delegator.findList("ProjectProposalMemberView",
					EntityCondition.makeCondition(conds), null,null,null, false);
			
			Debug.log(module + "::getProjectsOfStaff, staffId = " + staffId + ", lst.sz = " + lst.size());
			
			retSucc.put("projectDeclarations",lst);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getProjectDeclaration(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
			
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		System.out.println("staff id: "+staffId);
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			
			List<GenericValue> listProjectDeclaration = delegator.findList("ResearchProjectProposalView", 
																EntityCondition.makeCondition(conds), 
																null, null, null, false);
			List<Map> projectDeclarations = FastList.newInstance();
			for(GenericValue gv : listProjectDeclaration){
				Map<String, Object> map = FastMap.newInstance();
				for(int i=0; i<arrAtt.length; i++){
					map.put(arrAtt[i], gv.getString(arrAtt[i]));
				}
				projectDeclarations.add(map);
			}
			retSucc.put("projectDeclarations", projectDeclarations);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getProjectDeclarationOfStaff(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
			
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		//System.out.println("staff id: "+staffId);
		Debug.log(module + "::getProjectDeclarationOfStaff, staffId = " + staffId);
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId));
			
			//conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			
			List<GenericValue> listProjectDeclaration = delegator.findList("ProjectProposalMemberView", 
																EntityCondition.makeCondition(conds), 
																null, null, null, false);
			List<Map> projectDeclarations = FastList.newInstance();
			for(GenericValue gv : listProjectDeclaration){
				Map<String, Object> map = FastMap.newInstance();
				for(int i=0; i<arrAtt.length; i++){
					map.put(arrAtt[i], gv.getString(arrAtt[i]));
				}
				projectDeclarations.add(map);
			}
			retSucc.put("projectDeclarations", projectDeclarations);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> createProjectDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		//System.out.println("staffId"+staffId);
		List<String> projectCategoryId = (List<String>) context.get("projectCategoryId[]");
		String researchProjectProposalName = (String) context.get("researchProjectProposalName");
		String researchProjectProposalCode = (String) context.get("researchProjectProposalCode");
		String totalBudget = (String) context.get("totalBudget");
		List<String> facultyId = (List<String>) context.get("facultyId[]");
		String startDate = (String) context.get("startDate");
		String endDate = (String) context.get("endDate");
		//System.out.println(endDate);
		if(researchProjectProposalName == null || researchProjectProposalName.equals(""))
			researchProjectProposalName = " ";
		if(researchProjectProposalCode == null || researchProjectProposalCode.equals(""))
			researchProjectProposalCode = " ";
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.makeValue("ResearchProjectProposal");
			GenericValue gvv = delegator.makeValue("Party");
			gv.put("researchProjectProposalId", delegator.getNextSeqId("ResearchProjectProposal"));
			gvv.put("partyId", delegator.getNextSeqId("Party"));
			delegator.create(gvv);
			gv.put("partyId", gvv.getString("partyId"));
			gv.put("partyId", "_NA_");
			gv.put("createStaffId", staffId);
			gv.put("projectCategoryId", projectCategoryId.get(0));
			gv.put("researchProjectProposalName", researchProjectProposalName);
			gv.put("researchProjectProposalCode", researchProjectProposalCode);
			BigDecimal totalBudgetl = BigDecimal.ZERO;
			if(totalBudget != null)
				totalBudgetl =	new BigDecimal(totalBudget);//Long.valueOf(totalBudget);
			gv.put("totalBudget", totalBudgetl);
			gv.put("statusId", ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING);
			
			if (startDate != null) {
				gv.put("startDate", new Date(Long.valueOf(startDate)));
			}
			if (endDate != null) {
				gv.put("endDate", new Date(Long.valueOf(endDate)));		
			}
			
			delegator.create(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectProposalView", false, UtilMisc.toMap("researchProjectProposalId",
													gv.get("researchProjectProposalId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAtt.length;i++){
				map.put(arrAtt[i], result.getString(arrAtt[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	
	public static Map<String, Object> createProjectDeclarationStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		//System.out.println("staffId"+staffId);
		List<String> projectCategoryId = (List<String>) context.get("projectCategoryId[]");
		String researchProjectProposalName = (String) context.get("researchProjectProposalName");
		String researchProjectProposalCode = (String) context.get("researchProjectProposalCode");
		String totalBudget = (String) context.get("totalBudget");
		List<String> facultyId = (List<String>) context.get("facultyId[]");
		String startDate = (String) context.get("startDate");
		String endDate = (String) context.get("endDate");
		//System.out.println(endDate);
		if(researchProjectProposalName == null || researchProjectProposalName.equals(""))
			researchProjectProposalName = " ";
		if(researchProjectProposalCode == null || researchProjectProposalCode.equals(""))
			researchProjectProposalCode = " ";
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.makeValue("ResearchProjectProposal");
			GenericValue gvv = delegator.makeValue("Party");
			gv.put("researchProjectProposalId", delegator.getNextSeqId("ResearchProjectProposal"));
			gvv.put("partyId", delegator.getNextSeqId("Party"));
			delegator.create(gvv);
			gv.put("partyId", gvv.getString("partyId"));
			gv.put("partyId", "_NA_");
			gv.put("createStaffId", staffId);
			gv.put("projectCategoryId", projectCategoryId.get(0));
			gv.put("researchProjectProposalName", researchProjectProposalName);
			gv.put("researchProjectProposalCode", researchProjectProposalCode);
			BigDecimal totalBudgetl = BigDecimal.ZERO;
			if(totalBudget != null)
				totalBudgetl =	new BigDecimal(totalBudget);//Long.valueOf(totalBudget);
			gv.put("totalBudget", totalBudgetl);
			gv.put("statusId", ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING);
			
			if (startDate != null) {
				gv.put("startDate", new Date(Long.valueOf(startDate)));
			}
			if (endDate != null) {
				gv.put("endDate", new Date(Long.valueOf(endDate)));		
			}
			
			delegator.create(gv);
			
			// create entry of the table ProjectProposalMember
			GenericValue ppm = delegator.makeValue("ProjectProposalMember");
			String projectProposalMemberId = delegator.getNextSeqId("ProjectProposalMember");
			ppm.put("projectProposalMemberId", projectProposalMemberId);
			ppm.put("researchProjectProposalId", gv.get("researchProjectProposalId"));
			ppm.put("staffId", staffId);
			
			delegator.create(ppm);
			
			
			
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("projectProposalMemberId", EntityOperator.EQUALS, projectProposalMemberId));
			
			List<GenericValue> listProjectDeclaration = delegator.findList("ProjectProposalMemberView", 
																EntityCondition.makeCondition(conds), 
																null, null, null, false);
			
			Debug.log(module + "::createProjectDeclarationStaff, projectProposalMemberId = " + projectProposalMemberId
					+ ", GOT listProjectDeclaration.sz = " + listProjectDeclaration.size());
			
			GenericValue result = listProjectDeclaration.get(0);
			
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAtt.length;i++){
				map.put(arrAtt[i], result.getString(arrAtt[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> updateProjectDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String)userLogin.get("userLoginId");
		String researchProjectProposalId = (String) context.get("researchProjectProposalId");
		List<String> projectCategoryId = (List<String>) context.get("projectCategoryId[]");
		String researchProjectProposalName = (String) context.get("researchProjectProposalName");
		String totalBudget = (String) context.get("totalBudget");
		String startDate = (String) context.get("startDate");
		String endDate = (String) context.get("endDate");
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.findOne("ResearchProjectProposal", false, UtilMisc.toMap("researchProjectProposalId", researchProjectProposalId));
			gv.put("researchProjectProposalId", researchProjectProposalId);
			gv.put("createStaffId", staffId);
			gv.put("projectCategoryId", projectCategoryId.get(0));
			gv.put("researchProjectProposalName", researchProjectProposalName);
			//long totalBudgetl = Long.valueOf(totalBudget);
			//gv.put("totalBudget", totalBudgetl);
			
			BigDecimal totalBudgetl = BigDecimal.ZERO;
			if(totalBudget != null)
				totalBudgetl =	new BigDecimal(totalBudget);//Long.valueOf(totalBudget);
			gv.put("totalBudget", totalBudgetl);
			
			if (startDate != null){
				gv.put("startDate", new Date(Long.valueOf(startDate)));
			}
			if (endDate != null){
				gv.put("endDate", new Date(Long.valueOf(endDate)));
			}
			delegator.store(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectProposalView", false, UtilMisc.toMap("researchProjectProposalId",
													gv.get("researchProjectProposalId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAtt.length;i++){
				map.put(arrAtt[i], result.getString(arrAtt[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Updated record with id: " + researchProjectProposalId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	
	public static Map<String, Object> deleteProjectDeclaration (DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
        String researchProjectProposalId = (String) context.get("researchProjectProposalId");
        try{
//        	GenericValue gvv = delegator.findOne("ResearchProjectDeclarationYear", UtilMisc.toMap("researchProjectProposalId", 
//        			researchProjectProposalId), false);
//        	if(gvv != null){
//        		retSucc.put("result", "Không thể xóa đề tài này: " + researchProjectProposalId);
//        	}else{
    		GenericValue gv = delegator.findOne("ResearchProjectProposal", UtilMisc.toMap("researchProjectProposalId", 
        			researchProjectProposalId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "Deleted record with Id: " + researchProjectProposalId);
        	} else {
        		retSucc.put("result", "Not found record with Id: " + researchProjectProposalId);
        	}
//        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> getProjectProposalStatus(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("ProjectProposalStatus",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> statuss = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("statusId", gv.getString("statusId"));
					map.put("statusName", gv.getString("statusName"));
				statuss.add(map);
			}
			retSucc.put("projectProposalStatus", statuss);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getProjectCall(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("ProjectCall",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> projectCalls = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("projectCallId", gv.getString("projectCallId"));
					map.put("projectCallName", gv.getString("projectCallName"));
				projectCalls.add(map);
			}
			retSucc.put("projectCalls", projectCalls);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getProjectCategory(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("ProjectCategory",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> categorys = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("projectCategoryId", gv.getString("projectCategoryId"));
					map.put("projectCategoryName", gv.getString("projectCategoryName"));
				categorys.add(map);
			}
			retSucc.put("projectCategorys", categorys);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getFaculty(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("Faculty",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> facultys = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("facultyId", gv.getString("facultyId"));
					map.put("facultyName", gv.getString("facultyName"));
				facultys.add(map);
			}
			retSucc.put("facultys", facultys);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getStaff(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("Staff",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> staffs = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("staffId", gv.getString("staffId"));
					map.put("staffName", gv.getString("staffName"));
				staffs.add(map);
			}
			
			Debug.log(module + "::getStaff, GOT list.sz = " + list.size() + " == " + staffs.size());
			
			retSucc.put("staffs", staffs);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
}
