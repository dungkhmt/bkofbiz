package org.ofbiz.bkeuniv.projectdeclaration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.projectproposalsubmission.ProjectProposalSubmissionServiceUtil;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class ProjectDeclarationYear {
	public static String module = ProjectDeclaration.class.getName();
	/*
	private final static String[] arrAtt = new String[] {"researchProjectDeclarationYearId", "researchProjectProposalId",
		"staffId", "createStaffId", "projectParticipationRoleId", "workinghours", "academicYearId", "academicYearName",
		"staffName", "researchProjectProposalName", "projectParticipationRoleName",
		"equipmentBudget",
		"managementBudget",
		"allocatedBudgetYear",
		"staffParticipationPercentage"
		};
	*/
	private final static String[] arrAttBudget = new String[] {"researchProjectDeclarationYearId", "researchProjectProposalId",
		"createStaffId", "academicYearId", "academicYearName",
		"researchProjectProposalName",
		"equipmentBudget",
		"managementBudget",
		"allocatedBudgetYear"
		};
	private final static String[] arrAttParticipation = new String[] {"researchProjectDeclarationYearId", "researchProjectProposalId",
		"staffId", "createStaffId", "projectParticipationRoleId", "workinghours", "academicYearId", "academicYearName",
		"staffName", "researchProjectProposalName", "projectParticipationRoleName",
		"staffParticipationPercentage"
		};

	public static Map<String, Object> getProjectBudgetDeclaration(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		//System.out.println("staff id: "+staffId);
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> condss = FastList.newInstance();
			List<Map> projectDeclarations = FastList.newInstance();
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			
			List<GenericValue> listProjectDeclaration = delegator.findList("ResearchProjectBudgetDeclarationYearView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			Debug.log(module + "::getProjectBudgetDeclaration, list = " + listProjectDeclaration.size());
			
			for(GenericValue gv : listProjectDeclaration){
				Map<String, Object> map = FastMap.newInstance();
				for(int i=0; i<arrAttBudget.length; i++){
					map.put(arrAttBudget[i], gv.getString(arrAttBudget[i]));
				}
				Debug.log(module + "::getProjectBudgetDeclaration, proposal name = " + map.get("researchProjectProposalName"));

				projectDeclarations.add(map);
			}
			retSucc.put("projectDeclarations", projectDeclarations);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}

		return retSucc;
	}
	
	public static Map<String, Object> getProjectParticipationDeclaration(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		//System.out.println("staff id: "+staffId);
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<Map> projectDeclarations = FastList.newInstance();
			
			
			// get list declaration that user_login (staffId) is the createStaffId
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			List<GenericValue> listProjectDeclaration1 = delegator.findList("ResearchProjectParticipationDeclarationYearView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			HashSet<String> researchProjectDeclarationIds = new HashSet<String>();
			for(GenericValue gv: listProjectDeclaration1){
				researchProjectDeclarationIds.add((String)gv.get("researchProjectDeclarationYearId"));
			}
			
			// get list declaration that other staffs declared for the user_login (staffId)
			conds.clear();
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			List<GenericValue> listProjectDeclaration2 = delegator.findList("ResearchProjectParticipationDeclarationYearView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			
			
			List<GenericValue> listProjectDeclaration = FastList.newInstance();
			for(GenericValue gv: listProjectDeclaration1) listProjectDeclaration.add(gv);
			for(GenericValue gv: listProjectDeclaration2){
				if(!researchProjectDeclarationIds.contains((String)gv.get("researchProjectDeclarationYearId")))
					listProjectDeclaration.add(gv);
			}
			
			
			Debug.log(module + "::getProjectBudgetDeclaration, list = " + listProjectDeclaration.size());
			
			for(GenericValue gv : listProjectDeclaration){
				Map<String, Object> map = FastMap.newInstance();
				for(int i=0; i<arrAttParticipation.length; i++){
					map.put(arrAttParticipation[i], gv.getString(arrAttParticipation[i]));
				}
				Debug.log(module + "::getProjectBudgetDeclaration, proposal name = " + map.get("researchProjectProposalName"));

				projectDeclarations.add(map);
			}
			retSucc.put("projectDeclarations", projectDeclarations);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}

		return retSucc;
	}
	public static Map<String, Object> createProjectParticipationDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		List<String> staffId = (List<String>) context.get("staffId[]");
		//System.out.println("createProjectWorkingHourDeclaration :"+staffId);
		List<String> researchProjectProposalId = (List<String>) context.get("researchProjectProposalId[]");
		List<String> projectParticipationRoleId= (List<String>) context.get("projectParticipationRoleId[]");
		List<String> academicYearId= (List<String>) context.get("academicYearId[]");
		String s_staffParticipationPercentage = (String) context.get("staffParticipationPercentage");
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.makeValue("ResearchProjectParticipationDeclarationYear");
			gv.put("researchProjectDeclarationYearId", delegator.getNextSeqId("ResearchProjectParticipationDeclarationYear"));
			gv.put("staffId", staffId.get(0));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("projectParticipationRoleId", projectParticipationRoleId.get(0));
			gv.put("academicYearId", academicYearId.get(0));
			long staffParticipationPercentage = Long.valueOf(s_staffParticipationPercentage);
			gv.put("staffParticipationPercentage", staffParticipationPercentage);	
			
			delegator.create(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectParticipationDeclarationYearView", false, UtilMisc.toMap("researchProjectDeclarationYearId",
													gv.get("researchProjectDeclarationYearId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAttParticipation.length;i++){
				map.put(arrAttParticipation[i], result.getString(arrAttParticipation[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	public static Map<String, Object> updateProjectParticipationDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String researchProjectDeclarationYearId = (String) context.get("researchProjectDeclarationYearId");
		List<String> staffId = (List<String>) context.get("staffId[]");
		List<String> researchProjectProposalId = (List<String>) context.get("researchProjectProposalId[]");
		List<String> projectParticipationRoleId= (List<String>) context.get("projectParticipationRoleId[]");
		List<String> academicYearId= (List<String>) context.get("academicYearId[]");
		String s_staffParticipationPercentage = (String) context.get("staffParticipationPercentage");
		Debug.log(module + "::updateProjectParticipationDeclaration, staffId = " + staffId + ", staffParticipationPercentage = " + s_staffParticipationPercentage);
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.findOne("ResearchProjectParticipationDeclarationYear", false, UtilMisc.toMap("researchProjectDeclarationYearId", researchProjectDeclarationYearId));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("staffId", staffId.get(0));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("projectParticipationRoleId", projectParticipationRoleId.get(0));
			gv.put("academicYearId", academicYearId.get(0));
			long staffParticipationPercentage = Long.valueOf(s_staffParticipationPercentage);
			gv.put("staffParticipationPercentage", staffParticipationPercentage);	
			
			delegator.store(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectParticipationDeclarationYearView", false, UtilMisc.toMap("researchProjectDeclarationYearId",
													gv.get("researchProjectDeclarationYearId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAttParticipation.length;i++){
				map.put(arrAttParticipation[i], result.getString(arrAttParticipation[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Updated record with id: " + researchProjectDeclarationYearId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	public static Map<String, Object> deleteProjectParticipationDeclaration (DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
        String researchProjectDeclarationYearId = (String) context.get("researchProjectDeclarationYearId");
        try{
        	GenericValue gv = delegator.findOne("ResearchProjectParticipationDeclarationYear", UtilMisc.toMap("researchProjectDeclarationYearId", 
        			researchProjectDeclarationYearId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "Deleted record with Id: " + researchProjectDeclarationYearId);
        	} else {
        		retSucc.put("result", "Not found record with Id: " + researchProjectDeclarationYearId);
        	}
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}

	public static Map<String, Object> createProjectBudgetDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		
		List<String> researchProjectProposalId = (List<String>) context.get("researchProjectProposalId[]");
		List<String> academicYearId= (List<String>) context.get("academicYearId[]");
		//String sequipmentBudget = (String) context.get("equipmentBudget");
		//String smanagementBudget = (String) context.get("managementBudget");
		//String sallocatedBudgetYear = (String) context.get("allocatedBudgetYear");
		
		
		Delegator delegator = ctx.getDelegator();
		try{
			BigDecimal equipmentBudget = (BigDecimal) context.get("equipmentBudget");//new BigDecimal(sequipmentBudget);
			BigDecimal managementBudget = (BigDecimal) context.get("managementBudget");//new BigDecimal(smanagementBudget);
			BigDecimal allocatedBudgetYear = (BigDecimal) context.get("allocatedBudgetYear");//new BigDecimal(sallocatedBudgetYear);
			
			GenericValue gv = delegator.makeValue("ResearchProjectBudgetDeclarationYear");
			gv.put("researchProjectDeclarationYearId", delegator.getNextSeqId("ResearchProjectDeclarationYear"));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("equipmentBudget", equipmentBudget);
			gv.put("equipmentBudget", equipmentBudget);
			
			
			gv.put("managementBudget", managementBudget);
			gv.put("allocatedBudgetYear", allocatedBudgetYear);
			gv.put("academicYearId", academicYearId.get(0));
			
			delegator.create(gv);
			
			Debug.log(module+ "::createProjectBudgetDeclaration, researchProjectProposalId = " + gv.get("researchProjectProposalId")
					+ ", allocatedBudgetYear = " + gv.get("allocatedBudgetYear"));
			
			GenericValue result = delegator.findOne("ResearchProjectBudgetDeclarationYearView", false, UtilMisc.toMap("researchProjectDeclarationYearId",
													gv.get("researchProjectDeclarationYearId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAttBudget.length;i++){
				map.put(arrAttBudget[i], result.getString(arrAttBudget[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> updateProjectBudgetDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String researchProjectDeclarationYearId = (String) context.get("researchProjectDeclarationYearId");
		List<String> researchProjectProposalId = (List<String>) context.get("researchProjectProposalId[]");
		List<String> academicYearId= (List<String>) context.get("academicYearId[]");
		//String sequipmentBudget = (String) context.get("equipmentBudget");
		//String smanagementBudget = (String) context.get("managementBudget");
		//String sallocatedBudgetYear = (String) context.get("allocatedBudgetYear");
		
		Delegator delegator = ctx.getDelegator();
		try{
			BigDecimal equipmentBudget = (BigDecimal) context.get("equipmentBudget");//new BigDecimal(sequipmentBudget);
			BigDecimal managementBudget = (BigDecimal) context.get("managementBudget");//new BigDecimal(smanagementBudget);
			BigDecimal allocatedBudgetYear = (BigDecimal) context.get("allocatedBudgetYear");//new BigDecimal(sallocatedBudgetYear);
			
			GenericValue gv = delegator.findOne("ResearchProjectBudgetDeclarationYear", false, UtilMisc.toMap("researchProjectDeclarationYearId", researchProjectDeclarationYearId));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("equipmentBudget", equipmentBudget);
			gv.put("managementBudget", managementBudget);
			gv.put("allocatedBudgetYear", allocatedBudgetYear);
			gv.put("academicYearId", academicYearId.get(0));
			
			delegator.store(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectBudgetDeclarationYearView", false, UtilMisc.toMap("researchProjectDeclarationYearId",
													gv.get("researchProjectDeclarationYearId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAttBudget.length;i++){
				map.put(arrAttBudget[i], result.getString(arrAttBudget[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Updated record with id: " + researchProjectDeclarationYearId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	
	public static Map<String, Object> deleteProjectBudgetDeclaration (DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
        String researchProjectDeclarationYearId = (String) context.get("researchProjectDeclarationYearId");
        try{
        	GenericValue gv = delegator.findOne("ResearchProjectBudgetDeclarationYear", UtilMisc.toMap("researchProjectDeclarationYearId", 
        			researchProjectDeclarationYearId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "Deleted record with Id: " + researchProjectDeclarationYearId);
        	} else {
        		retSucc.put("result", "Not found record with Id: " + researchProjectDeclarationYearId);
        	}
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}

}
