package src.org.ofbiz.bkeuniv.paperdeclaration;

import java.awt.print.Paper;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

public class PaperDeclarationService {
	public static final String STATUS_ENABLED = "ENABLED";
	public static final String STATUS_DISABLED = "DISABLED";
	
	public static String module = PaperDeclarationService.class.getName();

	public static Map<String, Object> getPapersOfStaff(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		
		Debug.log(module + "::getPapersOfStaff, authorStaffId = " + staffId);
		Delegator delegator = ctx.getDelegator();
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("authorStaffId", EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, STATUS_ENABLED));
			
			List<GenericValue> papers = delegator.findList("PapersStaffView", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			for(GenericValue gv: papers){
				Debug.log(module + "::getPapersOfStaff, paper " + gv.get("paperName"));
			}
			Debug.log(module + "::getPapersOfStaff, papers.sz = " + papers.size());
			retSucc.put("papers", papers);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	public static Map<String, Object> createStaffPaperDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		try{
			Delegator delegator = ctx.getDelegator();
			String paperId = (String)context.get("paperId");
			String staffId = (String)context.get("staffId");
			String id = staffId + paperId;
			GenericValue gv = delegator.makeValue("StaffPaperDeclaration");
			gv.put("staffPaperDeclarationId", id);
			gv.put("staffId", staffId);
			gv.put("paperId", paperId);
			gv.put("statusId", STATUS_ENABLED);
			
			Debug.log(module + "::createStaffPaperDeclaration, staffId = " + staffId + ", paperId = " + 
			paperId + ", ID = " + id);
			
			delegator.create(gv);
			
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> createPaperDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		//String paperId = (String)context.get("paperId");
		String paperName = (String)context.get("paperName");
		
		//String paperCategoryId = (String)context.get("paperCategoryId");
		List<Object> paperCategoryIds = (List<Object>)context.get("paperCategoryId[]");
		String paperCategoryId = "NULL";
		if(paperCategoryIds != null)
			paperCategoryId = (String)(paperCategoryIds.get(0));
		
		String journalConferenceName = (String)context.get("journalConferenceName");
		String volumn = (String)context.get("volumn");
		String syear = (String)context.get("year");
		String smonth = (String)context.get("month");
		Long year = Long.valueOf(syear);
		Long month = Long.valueOf(smonth);
		String ISSN = (String)context.get("ISSN");
		String authors = (String)context.get("authors");
		//String academicYearId = (String)context.get("academicYearId");
		List<Object> academicYears = (List<Object>)context.get("academicYearId[]");
		String academicYearId = "NULL";
		if(academicYears != null)
			academicYearId = (String)academicYears.get(0);
		
		Debug.log(module + "::createPaperDeclaration, authorStaffId = " + staffId + ", paperName = " + 
		paperName + ", year = " + year + ", month = " + month + 
				", academicYearId = " + academicYearId + ", paperCategoryId = " + paperCategoryId);
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		
		try{
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
			p.put("statusId", STATUS_ENABLED);
			
			delegator.create(p);
			
			// add an item to StaffPaperDeclaration corresponding to the current staffId
			Map<String, Object> input = FastMap.newInstance();
			input.put("staffId", staffId);
			input.put("paperId", paperId);
			
			Map<String, Object> rs = dispatcher.runSync("createStaffPaperDeclaration", input);
			
			List<GenericValue> papers = FastList.newInstance();
			papers.add(p);
			retSucc.put("papers", p);
			retSucc.put("message", "Successfully");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return retSucc;
	}
	public static Map<String, Object> updatePaper(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		//String staffId = (String)context.get("authorStaffId");
		
		Map<String, Object> userLogin = (Map<String, Object>)context.get("userLogin");
		//String userLoginId = (String)context.get("userId");//(String)userLogin.get("userLoginId");
		String staffId = (String)userLogin.get("userLoginId");
		String paperId = (String)context.get("paperId");
		String paperName = (String)context.get("paperName");
		String paperCategoryId = (String)context.get("paperCategoryId");
		String journalConferenceName = (String)context.get("journalConferenceName");
		String volumn = (String)context.get("volumn");
		String year = (String)context.get("year");
		String month = (String)context.get("month");
		String ISSN = (String)context.get("ISSN");
		String authors = (String)context.get("authors");
		String academicYearId = (String)context.get("academicYearId");
		
		
		Debug.log(module + "::updatePaper, authorStaffId = " + staffId + ", paperId = " + paperId);
		Delegator delegator = ctx.getDelegator();
		
		try{
			List<GenericValue> papers = delegator.findList("PaperDeclaration", 
					EntityCondition.makeCondition(EntityCondition.makeCondition("paperId", EntityOperator.EQUALS, paperId)), 
					null, null, null, false);
			for(GenericValue gv: papers){
				Debug.log(module + "::updatePaper, paper " + gv.get("paperName") + ", new Name = " + paperName);
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
			
		}catch(Exception ex){
			ex.printStackTrace();
			ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	
	public static Map<String, Object> getPaperCategory(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		//String u1 = (String)ctx.getAttribute("userLoginId");
		//String u2 = (String)context.get("userLoginId");
		
		//if(u1 == null) u1 = "NULL";
		//if(u2 == null) u2 = "NULL";
		
		//System.out.println(module + "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " + u2);
		//Debug.log(module + "::getEducationProgress, Debug.log u1 = " + u1 + ", u2 = " + u2);
		
		
		String[] keys = {"paperCategoryId", "paperCategoryName", "paperCategoryCode", "journalIndexId"};
		String[] search = {"paperCategoryName"};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if( index == -1) {
						condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}
			
			List<GenericValue> list = delegator.findList("PaperCategory", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listMap = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> map = FastMap.newInstance();
				for(String key: keys) {
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
	
	public static Map<String, Object> deletePaperDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String paperId = (String)context.get("paperId");
		Delegator delegator = ctx.getDelegator();
		
		try{
			GenericValue gv = delegator.findOne("PaperDeclaration", false, UtilMisc.toMap("paperId",paperId));
			if(gv == null){
				return ServiceUtil.returnError("paperId " + paperId + " not exists");
			}
			gv.put("statusId", STATUS_DISABLED);
			delegator.store(gv);
			Debug.log(module + "::deletePaperDeclaration, disable PaperDeclaration " + paperId);
					
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId", EntityOperator.EQUALS,paperId));
			List<GenericValue> ps = delegator.findList("StaffPaperDeclaration", 
					EntityCondition.makeCondition(conds), 
					null, null, null, false);
			
			for(GenericValue g: ps){
				g.put("statusId", STATUS_DISABLED);
				delegator.store(g);
				Debug.log(module + "::deletePaperDeclaration, disable StaffPaperDeclaration (" + paperId + "," + g.get("staffId") + ")");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		return retSucc;
	}

	public static Map<String, Object> deleteStaffPaperDeclaration(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		String paperId = (String)context.get("paperId");
		String staffId = (String)context.get("staffId");
		
		Delegator delegator = ctx.getDelegator();
		
		try{
			GenericValue gv = delegator.findOne("StaffPaperDeclaration", false, UtilMisc.toMap("paperId",paperId,"staffId",staffId));
			if(gv == null){
				return ServiceUtil.returnError("paperId, staffId " + paperId + "," + staffId + " not exists");
			}
			gv.put("statusId", STATUS_DISABLED);
			
			delegator.store(gv);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		return retSucc;
	}

}
