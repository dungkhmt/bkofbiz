package org.ofbiz.bkeuniv.projectdeclaration;

import java.sql.Date;
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

public class ProjectWorkingHourDeclaration {
	public static String module = ProjectDeclaration.class.getName();
	private final static String[] arrAtt = new String[] {"researchProjectDeclarationYearId", "researchProjectProposalId",
		"staffId", "createStaffId", "projectParticipationRoleId", "workinghours", "academicYearId", "academicYearName",
		"staffName", "researchProjectProposalName", "projectParticipationRoleName"};
	
	public static Map<String, Object> getProjectWorkingHourDeclaration(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		//System.out.println("staff id: "+staffId);
		
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> condss = FastList.newInstance();
			/*
			condss.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			condss.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			
			List<GenericValue> listProjectProposal = delegator.findList("ResearchProjectProposal", 
																EntityCondition.makeCondition(condss), 
																null, null, null, false);
			List<Map> projectDeclarations = FastList.newInstance();
			List<EntityCondition> conds = FastList.newInstance();
			for(GenericValue gvv : listProjectProposal){
				conds.add(EntityCondition.makeCondition("researchProjectProposalId", 
						EntityOperator.EQUALS, gvv.getString("researchProjectProposalId")));
			}
			*/
			List<Map> projectDeclarations = FastList.newInstance();
			List<EntityCondition> conds = FastList.newInstance();
			//conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("staffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			
			List<GenericValue> listProjectDeclaration = delegator.findList("ResearchProjectDeclarationYearView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			Debug.log(module + "::getProjectWorkingHourDeclaration, list = " + listProjectDeclaration.size());
			
			for(GenericValue gv : listProjectDeclaration){
				Map<String, Object> map = FastMap.newInstance();
				for(int i=0; i<arrAtt.length; i++){
					map.put(arrAtt[i], gv.getString(arrAtt[i]));
				}
				Debug.log(module + "::getProjectWorkingHourDeclaration, proposal name = " + map.get("researchProjectProposalName"));

				projectDeclarations.add(map);
			}
			retSucc.put("projectDeclarations", projectDeclarations);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> createProjectWorkingHourDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		List<String> staffId = (List<String>) context.get("staffId[]");
		//System.out.println("createProjectWorkingHourDeclaration :"+staffId);
		List<String> researchProjectProposalId = (List<String>) context.get("researchProjectProposalId[]");
		List<String> projectParticipationRoleId= (List<String>) context.get("projectParticipationRoleId[]");
		List<String> academicYearId= (List<String>) context.get("academicYearId[]");
		String sworkinghours = (String) context.get("workinghours");
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.makeValue("ResearchProjectDeclarationYear");
			gv.put("researchProjectDeclarationYearId", delegator.getNextSeqId("ResearchProjectDeclarationYear"));
			gv.put("staffId", staffId.get(0));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("projectParticipationRoleId", projectParticipationRoleId.get(0));
			gv.put("academicYearId", academicYearId.get(0));
			long workinghours = Long.valueOf(sworkinghours);
			gv.put("workinghours", workinghours);	
			
			delegator.create(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectDeclarationYearView", false, UtilMisc.toMap("researchProjectDeclarationYearId",
													gv.get("researchProjectDeclarationYearId")));
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
	
	public static Map<String, Object> updateProjectWorkingHourDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String researchProjectDeclarationYearId = (String) context.get("researchProjectDeclarationYearId");
		List<String> staffId = (List<String>) context.get("staffId[]");
		List<String> researchProjectProposalId = (List<String>) context.get("researchProjectProposalId[]");
		List<String> projectParticipationRoleId= (List<String>) context.get("projectParticipationRoleId[]");
		List<String> academicYearId= (List<String>) context.get("academicYearId[]");
		String sworkinghours = (String) context.get("workinghours");
		Debug.log(module + "::updateProjectWorkingHourDeclaration, staffId = " + staffId + ", sworkinghours = " + sworkinghours);
		
		Delegator delegator = ctx.getDelegator();
		try{
			GenericValue gv = delegator.findOne("ResearchProjectDeclarationYear", false, UtilMisc.toMap("researchProjectDeclarationYearId", researchProjectDeclarationYearId));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("staffId", staffId.get(0));
			gv.put("researchProjectProposalId", researchProjectProposalId.get(0));
			gv.put("projectParticipationRoleId", projectParticipationRoleId.get(0));
			gv.put("academicYearId", academicYearId.get(0));
			long workinghours = Long.valueOf(sworkinghours);
			gv.put("workinghours", workinghours);	
			
			delegator.store(gv);
			
			GenericValue result = delegator.findOne("ResearchProjectDeclarationYearView", false, UtilMisc.toMap("researchProjectDeclarationYearId",
													gv.get("researchProjectDeclarationYearId")));
			Map<String,  Object> map = FastMap.newInstance();
			for(int i=0;i<arrAtt.length;i++){
				map.put(arrAtt[i], result.getString(arrAtt[i]));
			}
			retSucc.put("projectDeclarations", map);
			retSucc.put("message", "Updated record with id: " + researchProjectDeclarationYearId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	
	public static Map<String, Object> deleteProjectWorkingHourDeclaration (DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
        String researchProjectDeclarationYearId = (String) context.get("researchProjectDeclarationYearId");
        try{
        	GenericValue gv = delegator.findOne("ResearchProjectDeclarationYear", UtilMisc.toMap("researchProjectDeclarationYearId", 
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
	
	public static Map<String, Object> getAcademicYear(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("AcademicYear",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> academicYears = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("academicYearId", gv.getString("academicYearId"));
					map.put("academicYearName", gv.getString("academicYearName"));
					academicYears.add(map);
			}
			retSucc.put("academicYears", academicYears);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getResearchProjectProposal(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		//System.out.println("getResearchProjectProposal by: "+staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			List<GenericValue> list = delegator.findList("ResearchProjectProposal",
					EntityCondition.makeCondition(conds),null, null, null, false);
			List<Map> projects = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("researchProjectProposalId", gv.getString("researchProjectProposalId"));
					map.put("researchProjectProposalName", gv.getString("researchProjectProposalName"));
					projects.add(map);
			}
			retSucc.put("researchProjectProposals", projects);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getAllRunningResearchProjectProposal(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		//System.out.println("getResearchProjectProposal by: "+staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			//conds.add(EntityCondition.makeCondition("createStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING));
			List<GenericValue> list = delegator.findList("ResearchProjectProposal",
					EntityCondition.makeCondition(conds),null, null, null, false);
			List<Map> projects = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("researchProjectProposalId", gv.getString("researchProjectProposalId"));
					map.put("researchProjectProposalName", gv.getString("researchProjectProposalName"));
					projects.add(map);
			}
			retSucc.put("researchProjectProposals", projects);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getProjectParticipationRole(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> list = delegator.findList("ProjectParticipationRole",
					EntityCondition.makeCondition(),null, null, null, false);
			List<Map> projects = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("projectParticipationRoleId", gv.getString("projectParticipationRoleId"));
					map.put("projectParticipationRoleName", gv.getString("projectParticipationRoleName"));
					projects.add(map);
			}
			retSucc.put("projectParticipationRoles", projects);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getStaff(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		String staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		try{
			//GenericValue staff = delegator.findOne("Staff", UtilMisc.toMap("staffId", 
	    	//		staffId), false);
			//String departmentId = staff.getString("departmentId");
			List<EntityCondition> conds = FastList.newInstance();
			//conds.add(EntityCondition.makeCondition("departmentId", EntityOperator.EQUALS, departmentId));
			
			List<GenericValue> list = delegator.findList("Staff",
					EntityCondition.makeCondition(conds),null, null, null, false);
			List<Map> staffs = FastList.newInstance();
			for(GenericValue gv : list){
				Map<String, Object> map = FastMap.newInstance();
					map.put("staffId", gv.getString("staffId"));
					map.put("staffName", gv.getString("staffName"));
				staffs.add(map);
			}
			retSucc.put("staffs", staffs);
		} catch (Exception ex) {
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
}
