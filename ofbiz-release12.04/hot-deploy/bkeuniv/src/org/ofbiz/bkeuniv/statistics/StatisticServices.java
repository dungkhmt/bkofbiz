package org.ofbiz.bkeuniv.statistics;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

//import org.eclipse.birt.data.oda.mongodb.impl.MongoDBDriver;




import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

public class StatisticServices {
	public static String module = StatisticServices.class.getName();

	
	public static Map<String, Object> testElasticSearchClient(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		retSucc.put("result", "N/A");
		
		
		
		return retSucc;
	}
	public static Map<String, Object> testMongoDB(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		retSucc.put("result", "N/A");
		
		try {
			Delegator delegator = ctx.getDelegator();
			
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB("pqdtest");

			List<String> dbNames = mongoClient.getDatabaseNames();

			for (String n : dbNames)
				System.out.println(module + "::testMongoDB, name = " + n);

			DBCollection table = db.getCollection("pqdcollection");
			
			BasicDBObject cond1 = new BasicDBObject();
			//eq.put("staffId", "dung.phamquang@hust.edu.vn");
			cond1.put("staffId", new BasicDBObject("$gt", "dung.phamquang@hust.edu.vn").append("$lt", "giang.nguyenvan@hust.edu.vn"));
			
			BasicDBObject cond2 = new BasicDBObject();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			java.util.Date date = sdf1.parse("1900-03-09 00:00:00");
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
			
			System.out.println(module + "::testMongoDB, startDate = " + date.getTime() + ", endDate = " + date.getTime());
			
			date = sdf1.parse("2000-03-09 00:00:00");
			java.sql.Date sqlEndDate = new java.sql.Date(date.getTime());
			
			cond2.put("staffDateOfBirth", new BasicDBObject("$gt",sqlStartDate).append("$lt", sqlEndDate));
			
			
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(cond1);
			//obj.add(cond2);
			
			BasicDBObject andQuery = new BasicDBObject();
			andQuery.put("$and", obj);
			
			DBCursor cur = table.find(andQuery);
			String info = "";
			while(cur.hasNext()){
				DBObject o = cur.next(); 
				info += o.get("staffId") + "," + o.get("staffDateOfBirth");
				java.util.Date birthDate = (java.util.Date) o.get("staffDateOfBirth");
				System.out.println(info + ", getTime = " + (birthDate != null ? birthDate.getTime():"NULL"));
			}
			
			
			/*
			BasicDBObject document = new BasicDBObject();
			document.put("name", "pqd");
			document.put("age", 39);
			document.put("createdDate", new Date(System.currentTimeMillis()));
			table.insert(document);
			
			List<GenericValue> lst = delegator.findList("Staff", 
					null,null,null,null,false);
			
			for(GenericValue v: lst){
				BasicDBObject doc = new BasicDBObject();
				for(String k: v.keySet()){
					doc.put(k, v.get(k));
				}
				
				table.insert(doc);
			}
			*/
			retSucc.put("result", info);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return retSucc;
	}

	public static Map<String, Object> getPapersStatistic(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try {
			List<GenericValue> allYears = delegator.findList("AcademicYear",
					null, null, null, null, false);
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, "ENABLED"));

			List<GenericValue> papers = delegator.findList("PaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			Debug.log(module + "::getPapersStatistic, total = " + papers.size());
			Set<String> years = FastSet.newInstance();
			// for(GenericValue p: papers){
			// if(p.get("academicYearId") != null)
			// years.add((String)p.get("academicYearId"));
			// }
			for (GenericValue y : allYears) {
				years.add((String) y.get("academicYearId"));
			}
			String[] years_list = new String[years.size()];
			int idx = -1;
			for (String y : years) {
				idx++;
				years_list[idx] = y;
			}
			for (int i = 0; i < years_list.length - 1; i++)
				for (int j = i + 1; j < years_list.length; j++) {
					if (years_list[i].compareTo(years_list[j]) > 0) {
						String tmp = years_list[i];
						years_list[i] = years_list[j];
						years_list[j] = tmp;
					}
				}
			Map<String, Integer> m = FastMap.newInstance();
			for (int i = 0; i < years_list.length; i++)
				m.put(years_list[i], i);
			int[] count = new int[years_list.length];
			int[] countISI = new int[years_list.length];

			for (int i = 0; i < count.length; i++)
				count[i] = 0;
			for (int i = 0; i < countISI.length; i++)
				countISI[i] = 0;

			for (GenericValue p : papers)
				if (p.get("academicYearId") != null) {
					String y = p.getString("academicYearId");
					int ix = m.get(y);
					count[ix]++;
					if (p.getString("paperCategoryId") != null
							&& (p.getString("paperCategoryId").equals(
									"JINT_SCI") || p.getString(
									"paperCategoryId").equals("JINT_SCIE"))) {
						countISI[ix]++;
					}
				}
			List<String> Y = FastList.newInstance();
			List<Integer> C = FastList.newInstance();
			List<Integer> C_ISI = FastList.newInstance();
			for (int i = 0; i < years_list.length; i++) {
				Y.add(years_list[i]);
				C.add(count[i]);
				C_ISI.add(countISI[i]);
				Debug.log(module + "::getPapersStatistic, year "
						+ years_list[i] + " count " + count[i]);
			}
			idx = C.size() - 1;
			while (idx > 0) {
				if (C.get(idx) > 0)
					break;
				Y.remove(idx);
				C.remove(idx);
				C_ISI.remove(idx);
				idx = C.size() - 1;
			}
			retSucc.put("years", Y);
			retSucc.put("countPapers", C);
			retSucc.put("countPapersISI", C_ISI);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> updateCountResearchProposalSubmission(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try {
			List<GenericValue> list = delegator.findList(
					"ResearchProjectProposal", null, null, null, null, false);
			long count = list.size();
			GenericValue gv = delegator.makeValue("Statistic");
			String statisticId = delegator.getNextSeqId("Statistic");
			gv.put("statisticId", statisticId);
			gv.put("countResearchProposalSubmission", count);
			delegator.create(gv);
			Debug.log(module
					+ "::updateCountResearchProposalSubmission, list.sz = "
					+ list.size());
			retSucc.put("msg",
					"::updateCountResearchProposalSubmission --> list.sz = "
							+ list.size());

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}
