package org.ofbiz.bkeuniv.academicyear;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastMap;

import org.apache.commons.net.ntp.TimeStamp;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.util.*;

public class AcademicYearService {
	public static String module = AcademicYearService.class.getName();
	public static java.util.Random R = new java.util.Random();

	@SuppressWarnings({ "unchecked" })
	public static void getListAcademicYears(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		try {

			List<GenericValue> acaYears = delegator.findList("AcademicYear",
					null, null, null, null, false);
			Debug.log(module + "::getListAcademicYears, years.sz = "
					+ acaYears.size());

			String rs = "{\"years\":[";
			for (int i = 0; i < acaYears.size(); i++) {
				GenericValue st = acaYears.get(i);
				rs += "{\"id\":\"" + st.get("academicYearId")
						+ "\",\"name\":\"" + st.get("academicYearName") + "\"}";
				if (i < acaYears.size() - 1)
					rs += ",";

			}
			rs += "]}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static Map<String, Object> getListAcademicYears(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try {
			Delegator delegator = ctx.getDelegator();
			List<GenericValue> acaYears = delegator.findList("AcademicYear",
					null, null, null, null, false);
			Debug.log(module + "::getListAcademicYears, years.sz = "
					+ acaYears.size());

			retSucc.put("academicYears", acaYears);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Date makeDate(int year, int month, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		// int year = 2014;
		// int month = 10;
		// int day = 31;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // <-- months start
											// at 0.
		cal.set(Calendar.DAY_OF_MONTH, day);

		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		return date;
	}

	public static Date getLastDateOfMonth(Date d) {
		Calendar today = Calendar.getInstance();
		// DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		today.setTime(d);
		// int month = d.getMonth();
		// int year = d.getYear();
		// int day = d.getDate();
		today.add(Calendar.MONTH, 1);
		today.set(Calendar.DAY_OF_MONTH, 1);
		today.add(Calendar.DATE, -1);
		return new Date(today.getTimeInMillis());
	}

	public static Date getRandomDate(int month, int year) {
		//SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		// int year = 2014;
		// int month = 10;
		// int day = 31;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // <-- months start
											// at 0.

		int day = R.nextInt(28) + 1;
		int hour = R.nextInt(24);
		int minute = R.nextInt(60);
		int second = R.nextInt(60);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		
		//Debug.log(module + "::getRandomDate, month = " + month + ", year = " + year + ", date = " + cal.toString());
		return date;
	}

	public static Map<String, Object> createRandomAcctgTransAndEntriesForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		String s_nbAcctg = (String) context.get("nbAcctg");
		String s_nbAcctgEntry = (String) context.get("nbAcctgEntry");
		String s_month = (String) context.get("month");
		String s_year = (String) context.get("year");
		int nbAcctg = 100;
		int nbAcctgTransEntry = 10;
		int month = 4;
		int year = 2018;
		Debug.log(module + "::createRandomDataForTesting START");
		String[] creditGlAccountId = null;// { "13111", "33311", "7112",
											// "51131", "1111", "13114" };
		String[] debitGlAccountId = null;// { "7112", "13111", "13111", "1111",
											// "51131", "13111" };
		try {
			if (s_nbAcctg != null) {
				nbAcctg = Integer.valueOf(s_nbAcctg);
			}
			if (s_nbAcctgEntry != null) {
				nbAcctgTransEntry = Integer.valueOf(s_nbAcctgEntry);
			}
			if (s_month != null) {
				month = Integer.valueOf(s_month);
			}
			if (s_year != null) {
				year = Integer.valueOf(s_year);
			}

			List<GenericValue> lstGl = delegator.findList("GlAccount", null,
					null, null, null, false);
			creditGlAccountId = new String[lstGl.size()];
			debitGlAccountId = new String[lstGl.size()];
			for (int i = 0; i < lstGl.size(); i++) {
				creditGlAccountId[i] = (String) lstGl.get(i).get("glAccountId");
				debitGlAccountId[i] = (String) lstGl.get(lstGl.size() - 1 - i)
						.get("glAccountId");
			}

			Map<String, Object> in = FastMap.newInstance();
			List<GenericValue> acctgTrans = delegator.findList("AcctgTrans",
					null, null, null, null, false);

			double t0 = System.currentTimeMillis();
			for (int k = 1; k <= nbAcctg; k++) {

				double t1 = System.currentTimeMillis();
				in.put("acctgTransTypeId", "SALES_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesForTesting, acctgTransId = "
						+ acctgTransId);

				// GenericValue trans =
				// acctgTrans.get(R.nextInt(acctgTrans.size()));
				// String acctgTransId = (String)(trans.get("acctgTransId"));
				// java.sql.Timestamp transactionDate =
				// (java.sql.Timestamp)trans.get("transactionDate");

				for (int i = 0; i < nbAcctgTransEntry; i++) {
					BigDecimal amount = new BigDecimal(
							R.nextInt(1000000) + 10000);

					int idxGlAccount = R.nextInt(creditGlAccountId.length);
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", creditGlAccountId[idxGlAccount]);
					in.put("organizationPartyId", "Company");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", debitGlAccountId[idxGlAccount]);
					in.put("organizationPartyId", "Company");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);

				}
				t1 = System.currentTimeMillis() - t1;
				t1 = t1*0.001;
				Debug.log(module + "::createRandomAcctgTransAndEntriesForTesting, finished " + k + "/" + nbAcctg + ", time = " + t1);
				
			}
			t0 = System.currentTimeMillis() - t0;
			t0 = t0*0.001;
			Debug.log(module + "::createRandomAcctgTransAndEntriesForTesting, finished all, time = " + t0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static String padding(int seq){
		String s = seq + "";
		while(s.length() < 5){
			s = "0" + s;
		}
		return s;
	}
	public static Map<String, Object> createRandomAcctgTransForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		String s_nbAcctg = (String) context.get("nbAcctg");
		String s_nbAcctgEntry = (String) context.get("nbAcctgEntry");
		String s_month = (String) context.get("month");
		String s_year = (String) context.get("year");
		int nbAcctg = 100;
		int nbAcctgTransEntry = 10;
		int month = 4;
		int year = 2018;
		Debug.log(module + "::createRandomDataForTesting START");
		String[] creditGlAccountId = null;// { "13111", "33311", "7112",
											// "51131", "1111", "13114" };
		String[] debitGlAccountId = null;// { "7112", "13111", "13111", "1111",
											// "51131", "13111" };
		try {
			if (s_nbAcctg != null) {
				nbAcctg = Integer.valueOf(s_nbAcctg);
			}
			if (s_nbAcctgEntry != null) {
				nbAcctgTransEntry = Integer.valueOf(s_nbAcctgEntry);
			}
			if (s_month != null) {
				month = Integer.valueOf(s_month);
			}
			if (s_year != null) {
				year = Integer.valueOf(s_year);
			}

			List<GenericValue> lstGl = delegator.findList("GlAccount", null,
					null, null, null, false);
			creditGlAccountId = new String[lstGl.size()];
			debitGlAccountId = new String[lstGl.size()];
			for (int i = 0; i < lstGl.size(); i++) {
				creditGlAccountId[i] = (String) lstGl.get(i).get("glAccountId");
				debitGlAccountId[i] = (String) lstGl.get(lstGl.size() - 1 - i)
						.get("glAccountId");
			}

			//Map<String, Object> in = FastMap.newInstance();
			//List<GenericValue> acctgTrans = delegator.findList("AcctgTrans",
			//		null, null, null, null, false);

			double t0 = System.currentTimeMillis();
			for (int k = 1; k <= nbAcctg; k++) {

				double t1 = System.currentTimeMillis();
				/*
				in.put("acctgTransTypeId", "SALES_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");
				*/
				
				GenericValue r = delegator.makeValue("AcctgTrans");
				String acctgTransId = delegator.getNextSeqId("AcctgTrans");
				r.put("acctgTransId", acctgTransId);
				r.put("acctgTransTypeId", "SALES_INVOICE");
				r.put("glFiscalTypeId", "ACTUAL");
				Date dateTransactionDate = getRandomDate(month, year);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateTransactionDate);
				cal.set(Calendar.MILLISECOND, 0);
				
				//java.sql.Timestamp transactionDate = new java.sql.Timestamp(dateTransactionDate.getTime());
				java.sql.Timestamp transactionDate = new java.sql.Timestamp(cal.getTimeInMillis());
				//Debug.log(module + "::createRandomAcctgTransForTesting, date = " + transactionDate);
				r.put("transactionDate", transactionDate);
				r.put("isPosted", "Y");
				//r.put("userLogin", (GenericValue) context.get("userLogin"));
				delegator.create(r);
						
				//Debug.log(module
				//		+ "::createRandomAcctgTransForTesting, acctgTransId = "
				//		+ acctgTransId);

				int seq = 0;
				for (int i = 0; i < nbAcctgTransEntry; i++) {
					BigDecimal amount = new BigDecimal(
							R.nextInt(1000000) + 10000);

					seq++;
					String sseq = padding(seq);
					int idxGlAccount = R.nextInt(creditGlAccountId.length);
					// credit
					GenericValue rc = delegator.makeValue("AcctgTransEntry");
					rc.put("acctgTransEntrySeqId", sseq);
					rc.put("acctgTransId", acctgTransId);
					rc.put("debitCreditFlag", "C");
					rc.put("amount", amount);
					rc.put("glAccountId", creditGlAccountId[idxGlAccount]);
					rc.put("organizationPartyId", "Company");
					delegator.create(rc);
					//in.put("transactionDate", transactionDate);
					//in.put("userLogin", (GenericValue) context.get("userLogin"));
					//dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					GenericValue rd = delegator.makeValue("AcctgTransEntry");
					seq++;
					sseq = padding(seq);
					rd.put("acctgTransEntrySeqId", sseq);
					rd.put("acctgTransId", acctgTransId);
					rd.put("debitCreditFlag", "D");
					rd.put("amount", amount);
					rd.put("glAccountId", debitGlAccountId[idxGlAccount]);
					rd.put("organizationPartyId", "Company");
					delegator.create(rd);
					//in.put("transactionDate", transactionDate);
					//in.put("userLogin", (GenericValue) context.get("userLogin"));

					//dispatcher.runSync("createAcctgTransEntry", in);

				}
				
				
				t1 = System.currentTimeMillis() - t1;
				t1 = t1*0.001;
				if(k%1000 == 0)
					Debug.log(module + "::createRandomAcctgTransForTesting, finished " + k + "/" + nbAcctg + ", time = " + t1);
				
			}
			t0 = System.currentTimeMillis() - t0;
			t0 = t0*0.001;
			Debug.log(module + "::createRandomAcctgTransForTesting, finished all, time = " + t0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createGlAccountOrganizationForTesting(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try{
			List<GenericValue> lst = delegator.findList("GlAccount", null,null,null,null,false);
			for(GenericValue gl: lst){
				String glAccountId = gl.getString("glAccountId");
				GenericValue r = delegator.makeValue("GlAccountOrganization");
				r.put("glAccountId", glAccountId);
				r.put("organizationPartyId", "Company");
				
				delegator.create(r);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retSucc;
	}
	public static Timestamp convertStr2TimeStamp(String d){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date udate = format.parse(d);
			java.sql.Date date = new java.sql.Date(udate.getTime());
			return new java.sql.Timestamp(date.getTime());
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	public static Map<String, Object> querySumPosted(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String sfromDate = (String)context.get("fromDate");
		String sthruDate = (String)context.get("thruDate");
		String glAccountId = (String)context.get("glAccountId");
		
		
		Debug.log(module + "::querySumPosted, fromDate = " + sfromDate + ", thruDate = " + sthruDate + ", glAccount = " + glAccountId);
		
		try{
			java.sql.Timestamp fromDate = convertStr2TimeStamp(sfromDate);
			java.sql.Timestamp thruDate = convertStr2TimeStamp(sthruDate);
			List mainAndExprs = new ArrayList();
			
			mainAndExprs.add(EntityCondition.makeCondition("glAccountId", EntityOperator.EQUALS, glAccountId));
//			mainAndExprs.add(EntityCondition.makeCondition("debitCreditFlag", EntityOperator.EQUALS, strFlag));
			mainAndExprs.add(EntityCondition.makeCondition("isPosted", EntityOperator.EQUALS, "Y"));
			mainAndExprs.add(EntityCondition.makeCondition("acctgTransTypeId", EntityOperator.NOT_EQUAL, "PERIOD_CLOSING"));
			if(fromDate != null){
				mainAndExprs.add(EntityCondition.makeCondition("transactionDate", EntityOperator.GREATER_THAN_EQUAL_TO, fromDate));
			}
			mainAndExprs.add(EntityCondition.makeCondition("transactionDate", EntityOperator.LESS_THAN_EQUAL_TO, thruDate));
			double t0 = System.currentTimeMillis();
			List<GenericValue> listData = delegator.findList("AcctgTransGlAccountSums", 
					EntityCondition.makeCondition(mainAndExprs, EntityOperator.AND), 
					UtilMisc.toSet("glAccountId","debitCreditFlag","amount"), 
					null, null, false);
			double t = System.currentTimeMillis() - t0;
			t = t * 0.001;
			
			Debug.log(module + "::querySumPosted, listData = " + listData.size() + ", time = " + t);
			
			retSucc.put("result", listData);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
	/*
	public static Map<String, BigDecimal> querySumPosted(String glAccountId, String strOrganizationPartyId, 
			Delegator delegator, Timestamp fromDate, Timestamp thruDate, String strFlag) throws GenericEntityException {
		List mainAndExprs = new ArrayList();
		
		mainAndExprs.add(EntityCondition.makeCondition("glAccountId", EntityOperator.EQUALS, glAccountId));
//		mainAndExprs.add(EntityCondition.makeCondition("debitCreditFlag", EntityOperator.EQUALS, strFlag));
		mainAndExprs.add(EntityCondition.makeCondition("isPosted", EntityOperator.EQUALS, "Y"));
		mainAndExprs.add(EntityCondition.makeCondition("acctgTransTypeId", EntityOperator.NOT_EQUAL, "PERIOD_CLOSING"));
		if(fromDate != null){
			mainAndExprs.add(EntityCondition.makeCondition("transactionDate", EntityOperator.GREATER_THAN_EQUAL_TO, fromDate));
		}
		mainAndExprs.add(EntityCondition.makeCondition("transactionDate", EntityOperator.LESS_THAN_EQUAL_TO, thruDate));
		List<GenericValue> listData = delegator.findList("AcctgTransGlAccountSums", 
				EntityCondition.makeCondition(mainAndExprs, EntityOperator.AND), 
				UtilMisc.toSet("glAccountId","debitCreditFlag","amount"), 
				null, null, false);
		Map<String, BigDecimal> balMap = FastMap.newInstance();
		return balMap;
	}	
	*/
	
	public static void main(String[] args){
		System.out.println("ABC");
	}
}
