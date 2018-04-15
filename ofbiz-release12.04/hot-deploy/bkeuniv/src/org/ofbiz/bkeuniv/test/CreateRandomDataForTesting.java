package org.ofbiz.bkeuniv.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class CreateRandomDataForTesting {
	public static final String module = CreateRandomDataForTesting.class
			.getName();
	public static java.util.Random R = new java.util.Random();

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
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
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
		return date;
	}

	public static Map<String, Object> createRandomAcctgTransAndEntriesForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		int nbAcctg = 100;
		int month = 4;
		int year = 2018;
		Debug.log(module + "::createRandomDataForTesting START");
		String[] creditGlAccountId = { "13111", "33311", "7112", "51131",
				"1111", "13114" };
		String[] debitGlAccountId = { "7112", "13111", "13111", "1111",
				"51131", "13111" };
		try {
			Map<String, Object> in = FastMap.newInstance();
			List<GenericValue> acctgTrans = delegator.findList("AcctgTrans",
					null, null, null, null, false);

			for (int k = 1; k <= nbAcctg; k++) {

				in.put("acctgTransTypeId", "SALES_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "N");
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

				BigDecimal amount = new BigDecimal(R.nextInt(1000000) + 10000);

				int idxGlAccount = R.nextInt(creditGlAccountId.length);
				// credit
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("debitCreditFlag", "C");
				in.put("amount", amount);
				in.put("glAccountId", creditGlAccountId[idxGlAccount]);
				in.put("organizationPartyId", "MB");
				in.put("transactionDate", transactionDate);
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("createAcctgTransEntry", in);

				// debit
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("debitCreditFlag", "D");
				in.put("amount", amount);
				in.put("glAccountId", debitGlAccountId[idxGlAccount]);
				in.put("organizationPartyId", "MB");
				in.put("transactionDate", transactionDate);
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				dispatcher.runSync("createAcctgTransEntry", in);

				/*
				GenericValue trans = delegator.findOne("AcctgTrans",
						UtilMisc.toMap("acctgTransId", acctgTransId), false);
				trans.put("isPosted", "Y");
				delegator.store(trans);
				*/
				// post this acctgTrans
				in.clear();
				in.put("acctgTransId", acctgTransId);
				dispatcher.runSync("postAcctgTrans", in);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createRandomAcctgTransAndEntriesSalesInvoiceForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 100;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		Debug.log(module
				+ "::createRandomAcctgTransAndEntriesSalesInvoiceForTesting START");
		String mainGlAccountId = "13111";
		String glAccountVAT = "33311";
		String[] creditGlAccountId = null;
		String[] debitGlAccountId = null;
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("invoiceTypeId",
					EntityOperator.EQUALS, "SALES_INVOICE"));
			conds.add(EntityCondition.makeCondition("defaultGlAccountId",
					EntityOperator.NOT_EQUAL, null));
			List<GenericValue> lstInvItem = delegator.findList(
					"InvoiceItemType", EntityCondition.makeCondition(conds),
					null, null, null, false);
			debitGlAccountId = new String[lstInvItem.size()];
			for (int i = 0; i < debitGlAccountId.length; i++)
				debitGlAccountId[i] = mainGlAccountId;
			creditGlAccountId = new String[debitGlAccountId.length];
			for (int i = 0; i < lstInvItem.size(); i++) {
				GenericValue g = lstInvItem.get(i);
				creditGlAccountId[i] = g.getString("defaultGlAccountId");
			}

			Map<String, Object> in = FastMap.newInstance();
			// List<GenericValue> acctgTrans =
			// delegator.findList("AcctgTrans",null,null,null,null,false);
			double totalTime = 0;
			for (int k = 1; k <= nbAcctg; k++) {
				double t0 = System.currentTimeMillis();
				in.put("acctgTransTypeId", "SALES_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "N");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSalesInvoiceForTesting, acctgTransId = "
						+ acctgTransId);

				// GenericValue trans =
				// acctgTrans.get(R.nextInt(acctgTrans.size()));
				// String acctgTransId = (String)(trans.get("acctgTransId"));
				// java.sql.Timestamp transactionDate =
				// (java.sql.Timestamp)trans.get("transactionDate");

				int nbEntries = R.nextInt(nbAcctgTransEntry) + 1;
				for (int q = 1; q <= nbEntries; q++) {
					if(amount == null)
						amount = new BigDecimal(
							R.nextInt(10000000) + 10000);

					int idxGlAccount = R.nextInt(creditGlAccountId.length);
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", creditGlAccountId[idxGlAccount]);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", debitGlAccountId[idxGlAccount]);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
					
					// VAT
					// credit
					amount = amount.multiply(new BigDecimal(0.1));
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", glAccountVAT);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", mainGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
					
				}
				/*
				GenericValue trans = delegator.findOne("AcctgTrans",
						UtilMisc.toMap("acctgTransId", acctgTransId), false);
				trans.put("isPosted", "Y");
				delegator.store(trans);
				*/
				// post this acctgTrans
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("postAcctgTrans", in);

				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSalesInvoiceForTesting, acctgTransId = "
						+ acctgTransId + " FINISHED " + k + " acctgTrans, time = " + t0);
				totalTime += t0;
			}
			Debug.log(module
					+ "::createRandomAcctgTransAndEntriesSalesInvoiceForTesting, FINISHED total time = " + totalTime);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createRandomAcctgTransAndEntriesPurchaseInvoiceForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 100;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		Debug.log(module
				+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting START");
		String mainGlAccountId = "3311";
		String glAccountVAT = "1331";
		String[] creditGlAccountId = null;
		String[] debitGlAccountId = null;
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("invoiceTypeId",
					EntityOperator.EQUALS, "PURCHASE_INVOICE"));
			conds.add(EntityCondition.makeCondition("defaultGlAccountId",
					EntityOperator.NOT_EQUAL, null));
			List<GenericValue> lstInvItem = delegator.findList(
					"InvoiceItemType", EntityCondition.makeCondition(conds),
					null, null, null, false);
			
			creditGlAccountId = new String[lstInvItem.size()];
			for (int i = 0; i < creditGlAccountId.length; i++)
				creditGlAccountId[i] = mainGlAccountId;
			
			debitGlAccountId = new String[lstInvItem.size()];
			for (int i = 0; i < lstInvItem.size(); i++) {
				GenericValue g = lstInvItem.get(i);
				debitGlAccountId[i] = g.getString("defaultGlAccountId");
			}

			Map<String, Object> in = FastMap.newInstance();
			// List<GenericValue> acctgTrans =
			// delegator.findList("AcctgTrans",null,null,null,null,false);

			double totalTime = 0;
			for (int k = 1; k <= nbAcctg; k++) {
				double t0 = System.currentTimeMillis();
				in.put("acctgTransTypeId", "PURCHASE_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "N");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId);

				// GenericValue trans =
				// acctgTrans.get(R.nextInt(acctgTrans.size()));
				// String acctgTransId = (String)(trans.get("acctgTransId"));
				// java.sql.Timestamp transactionDate =
				// (java.sql.Timestamp)trans.get("transactionDate");

				int nbEntries = R.nextInt(nbAcctgTransEntry) + 1;
				for (int q = 1; q <= nbEntries; q++) {
					if(amount == null)
						amount = new BigDecimal(
							R.nextInt(10000000) + 10000);

					int idxGlAccount = R.nextInt(creditGlAccountId.length);
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", creditGlAccountId[idxGlAccount]);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", debitGlAccountId[idxGlAccount]);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
					
					// VAT
					// credit
					amount = amount.multiply(new BigDecimal(0.1));
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", mainGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", glAccountVAT);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
					
				}
				// post this acctgTrans
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("postAcctgTrans", in);

				/*
				GenericValue trans = delegator.findOne("AcctgTrans",
						UtilMisc.toMap("acctgTransId", acctgTransId), false);
				trans.put("isPosted", "Y");
				delegator.store(trans);
				*/
				
				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId + " FINISHED " + k + " acctgTrans, time = " + t0);
				
			}
			Debug.log(module
					+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, FINISHED, totalTime = " + totalTime);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createRandomAcctgTransAndEntriesPurchaseOrderInvoiceForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		/*
		 * simulate purchase order, amount A:
		 * 	  -Tao giao dich SHIPMENT_RECEIPT:
		 * 			Co 151 (hang mua di duong), A 
		 *   		No 15611 (hang hoa), A
		 *    -Tao giao dich PURCHASE_INVOICE:
		 *    		No 151, A
		 *    		Co 3311 (phai tra cho nguoi ban), A
		 *    
		 *    		VAT (thue GTGT duoc khau tru)
		 *    		Co 3311 (phai tra cho nguoi ban) 10%A (thuc te 10% nay co the add linh dong bang tay, ko teo he thong)
		 *    		No 33311 (thue GTGT duoc khau tru) 10%A
		 *    
		 *    -Tao giao dich thanh toan OUTGOING_PAYMENT
		 *    		Co 1111 (tien mat), A + 10%A
		 *    		No 3311 (phai tra cho nguoi ban) A + 10%A
		 */
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 100;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		nbAcctgTransEntry = 1; // FIXED
		
		Debug.log(module
				+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting START");
		try {
			
			Map<String, Object> in = FastMap.newInstance();

			double totalTime = 0;
			
			for (int k = 1; k <= nbAcctg; k++) {
				double t0 = System.currentTimeMillis();
			
				// create SHIPMENT_RECEIPT
				in.put("acctgTransTypeId", "SHIPMENT_RECEIPT");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");
				
				
				
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId);

				//int nbEntries = R.nextInt(nbAcctgTransEntry) + 1;
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", "151");
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", "15611");
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
				
				// post this acctgTrans
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("postAcctgTrans", in);
					
					
				in.clear();
				in.put("acctgTransTypeId", "PURCHASE_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				//Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> rp = dispatcher.runSync("createAcctgTrans",
						in);
				acctgTransId = (String) rp.get("acctgTransId");
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId);


					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", "3311");
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", "151"); // hang hoa mua di duong
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
					
				

				// VAT duoc khau tru: 
				
				// credit
				BigDecimal vat_amount = amount.multiply(new BigDecimal(0.1)); // TEST with VAT = 10%
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("debitCreditFlag", "C");
				in.put("amount", vat_amount);
				in.put("glAccountId", "3311");// phai tra cho nguoi ban
				in.put("organizationPartyId", "MB");
				in.put("transactionDate", transactionDate);
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("createAcctgTransEntry", in);

				// debit
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("debitCreditFlag", "D");
				in.put("amount", vat_amount);
				in.put("glAccountId", "33311"); // thue GTGT duoc khau tru
				in.put("organizationPartyId", "MB");
				in.put("transactionDate", transactionDate);
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				dispatcher.runSync("createAcctgTransEntry", in);
				
				
				
				//GenericValue trans = delegator.findOne("AcctgTrans",
			//			UtilMisc.toMap("acctgTransId", acctgTransId), false);
				//trans.put("isPosted", "Y");
				//delegator.store(trans);

				// post this acctgTrans
				in.clear();
				in.put("acctgTransId", acctgTransId);
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("postAcctgTrans", in);

				
				// create giao dich OUTGOING_PAYMENT
				in.clear();
				in.put("acctgTransTypeId", "OUTGOING_PAYMENT");
				in.put("glFiscalTypeId", "ACTUAL");
				//Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> rop = dispatcher.runSync("createAcctgTrans",
						in);
				acctgTransId = (String) rop.get("acctgTransId");
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId);


					BigDecimal pay_amount = amount.add(vat_amount);
					
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", pay_amount);
					in.put("glAccountId", "1111"); // tien mat VN
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", pay_amount);
					in.put("glAccountId", "3311"); // phai tra cho KH
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
				
				
					// post this acctgTrans
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("postAcctgTrans", in);

				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId + " FINISHED " + k + " acctgTrans, time = " + t0);
				
			}
			Debug.log(module
					+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, FINISHED, totalTime = " + totalTime);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createRandomAcctgTransAndEntriesSalesOrderInvoiceForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		/*
		 * simulate sales order, amount gia tri hang hoa la A:
		 * 	  -Tao giao dich SALES_SHIPMENT:
		 * 			No 63211 (gia von hang ban), B  (gia von hang ban updated), vi du B = 70%A 
		 *   		Co 15611 (hang hoa), B
		 *    -Tao giao dich SALES_INVOICE:
		 *    		Doanh thu
		 *    		No 13111 (phai thu cua KH), 5%A
		 *    		Co 51111 (doanh thu hang hoa, cung cap dich vu), 5%A
		 *    		
		 *    		Chiet khau thuong mai
		 *    		No 5211 (chieu khau thuong mai 5% don hang)
		 *    		Co 13111 (phai thu cua KH)
		 *    
		 *    		VAT: 10%A
		 *    		No 13111 (phai thu cua KH) khoan VAT KH phai tra de nop lai cho nha nuoc, amount 10%A
		 *    		Co 33311 (thue (VAT KH pha tra) phai nop cho nha nuoc), amount 10%A
		 *    
		 *    -Tao giao dich INCOMING_PAYMENT
		 *    		No 11211 (tien ngan hang VCB), A - 5%A + 10%A
		 *    		Co 1311 (phai thu cua KH), A - 5%A + 10%A
		 */
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 100;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		Debug.log(module
				+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting START");
		String mainGlAccountId = "3311";
		String glAccountVAT = "1331";
		String[] creditGlAccountId = null;
		String[] debitGlAccountId = null;
		try {
			
			Map<String, Object> in = FastMap.newInstance();
		
			double totalTime = 0;
			
			for (int k = 1; k <= nbAcctg; k++) {
				double t0 = System.currentTimeMillis();
			
				// create SALES_SHIPMENT
				in.put("acctgTransTypeId", "SALES_SHIPMENT");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");

				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId);

					BigDecimal gia_von_amount = amount.multiply(new BigDecimal(0.7));
					
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", gia_von_amount);
					in.put("glAccountId", "15611");
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", gia_von_amount);
					in.put("glAccountId", "63211");
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
				
					// post this acctgTrans
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("postAcctgTrans", in);

					// create SALES_INVOICE
					in.put("acctgTransTypeId", "SALES_INVOICE");
					in.put("glFiscalTypeId", "ACTUAL");
					//Date transactionDate = getRandomDate(month, year);
					in.put("transactionDate", transactionDate);
					in.put("isPosted", "Y");
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					Map<String, Object> rs = dispatcher.runSync("createAcctgTrans",
							in);
					acctgTransId = (String) rs.get("acctgTransId");

					Debug.log(module
							+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
							+ acctgTransId);

						
						// credit
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("debitCreditFlag", "C");
						in.put("amount", amount);
						in.put("glAccountId", "51111");// doanh thu hang hoa
						in.put("organizationPartyId", "MB");
						in.put("transactionDate", transactionDate);
						in.put("userLogin", (GenericValue) context.get("userLogin"));
						dispatcher.runSync("createAcctgTransEntry", in);

						// debit
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("debitCreditFlag", "D");
						in.put("amount", amount);
						in.put("glAccountId", "13111");// phai thu cua KH
						in.put("organizationPartyId", "MB");
						in.put("transactionDate", transactionDate);
						in.put("userLogin", (GenericValue) context.get("userLogin"));

						dispatcher.runSync("createAcctgTransEntry", in);
					
							
						// chiet khau	
						BigDecimal chiet_khau_amount = amount.multiply(new BigDecimal(0.05));
						// credit
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("debitCreditFlag", "C");
						in.put("amount", chiet_khau_amount);
						in.put("glAccountId", "13111");// phai thu cua KH
						in.put("organizationPartyId", "MB");
						in.put("transactionDate", transactionDate);
						in.put("userLogin", (GenericValue) context.get("userLogin"));
						dispatcher.runSync("createAcctgTransEntry", in);

						// debit
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("debitCreditFlag", "D");
						in.put("amount", chiet_khau_amount);
						in.put("glAccountId", "5211");// chiet khau thuong mai
						in.put("organizationPartyId", "MB");
						in.put("transactionDate", transactionDate);
						in.put("userLogin", (GenericValue) context.get("userLogin"));

						dispatcher.runSync("createAcctgTransEntry", in);
					
						// VAT
						BigDecimal vat_amount = amount.multiply(new BigDecimal(0.1));
						// credit
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("debitCreditFlag", "C");
						in.put("amount", vat_amount);
						in.put("glAccountId", "33311");// thue GTGT phai nop cho nha nuoc
						in.put("organizationPartyId", "MB");
						in.put("transactionDate", transactionDate);
						in.put("userLogin", (GenericValue) context.get("userLogin"));
						dispatcher.runSync("createAcctgTransEntry", in);

						// debit
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("debitCreditFlag", "D");
						in.put("amount", vat_amount);
						in.put("glAccountId", "13111");// phai thu cua KH
						in.put("organizationPartyId", "MB");
						in.put("transactionDate", transactionDate);
						in.put("userLogin", (GenericValue) context.get("userLogin"));

						dispatcher.runSync("createAcctgTransEntry", in);
						
						// post this acctgTrans
						in.clear();
						in.put("acctgTransId", acctgTransId);
						in.put("userLogin", (GenericValue) context.get("userLogin"));
						dispatcher.runSync("postAcctgTrans", in);

						
				// create INCOMING_PAYMENT
						in.put("acctgTransTypeId", "INCOMING_PAYMENT");
						in.put("glFiscalTypeId", "ACTUAL");
						//Date transactionDate = getRandomDate(month, year);
						in.put("transactionDate", transactionDate);
						in.put("isPosted", "Y");
						in.put("userLogin", (GenericValue) context.get("userLogin"));

						Map<String, Object> rpmt = dispatcher.runSync("createAcctgTrans",
								in);
						acctgTransId = (String) rpmt.get("acctgTransId");

						Debug.log(module
								+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
								+ acctgTransId);

							
						BigDecimal pay_amount = amount.subtract(chiet_khau_amount);
						pay_amount = pay_amount.add(vat_amount);
						
							// credit
							in.clear();
							in.put("acctgTransId", acctgTransId);
							in.put("debitCreditFlag", "C");
							in.put("amount", pay_amount);
							in.put("glAccountId", "13111");// phai thu cua KH
							in.put("organizationPartyId", "MB");
							in.put("transactionDate", transactionDate);
							in.put("userLogin", (GenericValue) context.get("userLogin"));
							dispatcher.runSync("createAcctgTransEntry", in);

							// debit
							in.clear();
							in.put("acctgTransId", acctgTransId);
							in.put("debitCreditFlag", "D");
							in.put("amount", pay_amount);
							in.put("glAccountId", "11211");// phai thu cua KH
							in.put("organizationPartyId", "MB");
							in.put("transactionDate", transactionDate);
							in.put("userLogin", (GenericValue) context.get("userLogin"));

							dispatcher.runSync("createAcctgTransEntry", in);

							// post this acctgTrans
							in.clear();
							in.put("acctgTransId", acctgTransId);
							in.put("userLogin", (GenericValue) context.get("userLogin"));
							dispatcher.runSync("postAcctgTrans", in);

				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				
				Debug.log(module
						+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, acctgTransId = "
						+ acctgTransId + " FINISHED " + k + " acctgTrans, time = " + t0);
				
			}
			Debug.log(module
					+ "::createRandomAcctgTransAndEntriesSPurchaseInvoiceForTesting, FINISHED, totalTime = " + totalTime);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createAcctgTransAndEntriesKetchuyenLoinhuanForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 1;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String creditGlAccountId = (String)context.get("creditGlAccountId");
		String debitGlAccountId = (String)context.get("debitGlAccountId");
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		Debug.log(module
				+ "::createAcctgTransAndEntriesKetchuyenLoinhuanForTesting START");
		try {
			
			Map<String, Object> in = FastMap.newInstance();
		
			double totalTime = 0;
				double t0 = System.currentTimeMillis();
			
				// create SALES_SHIPMENT
				in.put("acctgTransTypeId", "CLOSING_ENTRY");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");

				Debug.log(module
						+ "::createAcctgTransAndEntriesKetchuyenLoinhuanForTesting, acctgTransId = "
						+ acctgTransId);

					//BigDecimal gia_von_amount = amount.multiply(new BigDecimal(0.7));
					
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", creditGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", debitGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
				
					// post this acctgTrans
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("postAcctgTrans", in);


				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				
				
			Debug.log(module
					+ "::createAcctgTransAndEntriesKetchuyenLoinhuanForTesting, FINISHED, totalTime = " + totalTime);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createAcctgTransAndEntriesKetchuyenDongkyketoanForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 1;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String creditGlAccountId = (String)context.get("creditGlAccountId");
		String debitGlAccountId = (String)context.get("debitGlAccountId");
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		Debug.log(module
				+ "::createAcctgTransAndEntriesKetchuyenDongkyketoanForTesting START");
		try {
			
			Map<String, Object> in = FastMap.newInstance();
		
			double totalTime = 0;
				double t0 = System.currentTimeMillis();
			
				// create SALES_SHIPMENT
				in.put("acctgTransTypeId", "PERIOD_CLOSING");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");

				Debug.log(module
						+ "::createAcctgTransAndEntriesKetchuyenDongkyketoanForTesting, acctgTransId = "
						+ acctgTransId);

					//BigDecimal gia_von_amount = amount.multiply(new BigDecimal(0.7));
					
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", creditGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", debitGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
				
					// post this acctgTrans
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("postAcctgTrans", in);


				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				
				
			Debug.log(module
					+ "::createAcctgTransAndEntriesKetchuyenDongkyketoanForTesting, FINISHED, totalTime = " + totalTime);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}
	
	public static Map<String, Object> createAcctgTransAndEntriesPhatsinhchiphiForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		
		int nbAcctg = 1;
		int month = 4;
		int year = 2018;
		int nbAcctgTransEntry = 10;
		String creditGlAccountId = (String)context.get("creditGlAccountId");
		String debitGlAccountId = (String)context.get("debitGlAccountId");
		String snbAcctg = (String)context.get("nbAcctgTrans");
		String sMonth = (String)context.get("month");
		String sYear = (String)context.get("year");
		String snbAcctgTransEntry = (String)context.get("nbAcctgTransEntry");
		String sAmount = (String)context.get("amount");
		BigDecimal amount = null;
		if(sAmount != null){
			try{
				amount = new BigDecimal(Long.valueOf(sAmount));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctg != null){
			try{
				nbAcctg = Integer.valueOf(snbAcctg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sMonth != null){
			try{
				month = Integer.valueOf(sMonth);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(sYear != null){
			try{
				year = Integer.valueOf(sYear);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(snbAcctgTransEntry != null){
			try{
				nbAcctgTransEntry = Integer.valueOf(snbAcctgTransEntry);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		Debug.log(module
				+ "::createAcctgTransAndEntriesPhatsinhchiphiForTesting START");
		try {
			
			Map<String, Object> in = FastMap.newInstance();
		
			double totalTime = 0;
				double t0 = System.currentTimeMillis();
			
				// create SALES_SHIPMENT
				in.put("acctgTransTypeId", "PAYROL_INVOICE");
				in.put("glFiscalTypeId", "ACTUAL");
				Date transactionDate = getRandomDate(month, year);
				in.put("transactionDate", transactionDate);
				in.put("isPosted", "Y");
				in.put("userLogin", (GenericValue) context.get("userLogin"));

				Map<String, Object> r = dispatcher.runSync("createAcctgTrans",
						in);
				String acctgTransId = (String) r.get("acctgTransId");

				Debug.log(module
						+ "::createAcctgTransAndEntriesPhatsinhchiphiForTesting, acctgTransId = "
						+ acctgTransId);

					//BigDecimal gia_von_amount = amount.multiply(new BigDecimal(0.7));
					
					// credit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "C");
					in.put("amount", amount);
					in.put("glAccountId", creditGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("createAcctgTransEntry", in);

					// debit
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("debitCreditFlag", "D");
					in.put("amount", amount);
					in.put("glAccountId", debitGlAccountId);
					in.put("organizationPartyId", "MB");
					in.put("transactionDate", transactionDate);
					in.put("userLogin", (GenericValue) context.get("userLogin"));

					dispatcher.runSync("createAcctgTransEntry", in);
				
					// post this acctgTrans
					in.clear();
					in.put("acctgTransId", acctgTransId);
					in.put("userLogin", (GenericValue) context.get("userLogin"));
					dispatcher.runSync("postAcctgTrans", in);


				t0 = System.currentTimeMillis() - t0;
				t0 = t0*0.001;
				
				
			Debug.log(module
					+ "::createAcctgTransAndEntriesPhatsinhchiphiForTesting, FINISHED, totalTime = " + totalTime);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createCustomTimePeriodForTesting(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Debug.log(module + "::createCustomTimePeriodForTesting START");

		try {
			Map<String, Object> in = FastMap.newInstance();

			for (int i = 1; i <= 12; i++) {
				in.put("organizationPartyId", "MB");
				java.sql.Date fromDate = makeDate(2018, i, 1);
				java.sql.Date thruDate = getLastDateOfMonth(fromDate);// makeDate(2018,
																		// i,
																		// 30);
				in.put("fromDate", fromDate);
				in.put("thruDate", thruDate);
				in.put("periodTypeId", "FISCAL_MONTH");
				in.put("periodName", "Ky ke toan thang " + i);
				in.put("isClosed", "N");
				in.put("userLogin", (GenericValue) context.get("userLogin"));
				dispatcher.runSync("createCustomTimePeriod", in);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;

	}

	public static void main(String[] args) {
		Date d = CreateRandomDataForTesting.makeDate(2018, 4, 1);
		System.out.println(d.toString());
	}
}
