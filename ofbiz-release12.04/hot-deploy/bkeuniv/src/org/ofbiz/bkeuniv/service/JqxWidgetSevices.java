package org.ofbiz.bkeuniv.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javolution.util.FastList;
import javolution.util.FastMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;

/*
 * TODO: seperate UI and functionality matters ! 
 Seperate business logic, do not access entity directly, please do it under a business logic service
 */
public class JqxWidgetSevices {
	public static final String module = JqxWidgetSevices.class.getName();
	public static final String resource = "widgetUiLabels";
	public static final String resourceError = "widgetErrorUiLabels";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> gridGeneralServicer(DispatchContext ctx,
			Map<String, ? extends Object> context) throws Exception {
		Map<String, Object> result = FastMap.newInstance();
		Map<String, String[]> parametersPrime = (Map<String, String[]>) context.get("parameters");
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		String strServiceName = null;
		parameters.putAll(parametersPrime);
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String strDictionaryColumns = ((String[]) parameters
				.get("dictionaryColumns") != null) ? ((String) parameters
				.get("dictionaryColumns")[0]) : ("");
		// Locale locale = (Locale) context.get("locale");
		String strViewIndex = "0";
		if(parameters.get("pagenum") != null){
		    strViewIndex = String.valueOf(parameters.get("pagenum")[0]);
		}
		String strViewSize = "10";
		if(parameters.get("pagesize") != null){
		    strViewSize = String.valueOf(parameters.get("pagesize")[0]);
		}
		JSONArray sort = new JSONArray();
		JSONObject filter = new JSONObject();
		try {
			
			if(parameters.get("sort") != null){
				sort = JSONArray.fromObject(parameters.get("sort")[0]);
			}
			
			if(parameters.get("filter") != null){
				filter = JSONObject.fromObject(parameters.get("filter")[0]);
			}
		} catch (Exception e) {
			Debug.logError(e, module);
			List<Map<String, Object>> res = FastList.newInstance();
			result.put("results", res);
			result.put("totalRows", "0");
		}
		
		if(parameters.get("sname") == null){
		    Map<String, Object> tmpReturn = new HashMap<String, Object>();
		    tmpReturn.put("results", new ArrayList<String>());
		    tmpReturn.put("totalRows", "0");
		    return tmpReturn;
		} else {
			strServiceName = parameters.get("sname")[0];
		}
		
		List<String> _sort = null;
		if(sort.size() > 0) {
			_sort = new ArrayList<String>();
			for(int i = 0; i < sort.size(); ++i) {
				JSONObject s = (JSONObject) sort.get(i);
				if ("asc".equals(s.get("type"))) {
					_sort.add((String) s.get("field"));
				} else {
					_sort.add("-" + s.get("field"));
				}
			}
		}
		
		EntityCondition filterB = null;
		if(!filter.isEmpty()) {
			filterB = buildFilter(filter);
		}
		
		
		EntityFindOptions opts = new EntityFindOptions(true,
				EntityFindOptions.TYPE_SCROLL_INSENSITIVE,
				EntityFindOptions.CONCUR_READ_ONLY, false);
		Integer iSize = 0;
		Integer iIndex = 0;
		if (strViewIndex != null && !strViewIndex.isEmpty()) {
			iIndex = new Integer(strViewIndex);
		}
		if (strViewSize != null && !strViewSize.isEmpty()) {
			iSize = new Integer(strViewSize);
		}
		
		
			
		try {
			// listAllConditions
			Map<String, Object> mapTmp = new HashMap<String, Object>();
			mapTmp.put("filter", filterB);
			mapTmp.put("sort", _sort);
			mapTmp.put("opts", opts);
			mapTmp.put("userLogin", context.get("userLogin"));
			mapTmp.put("parameters", parameters);
			mapTmp.put("locale", context.get("locale"));
			if (context.containsKey("request")) {
				mapTmp.put("request", context.get("request"));
			}
			mapTmp.put("timeZone", context.get("timeZone"));
			Map<String, Object> resultMap = dispatcher.runSync(strServiceName,
					mapTmp);
			Object resultList = resultMap.get("listIterator");
			String totalRows = (String) resultMap.get("totalRows");
			if (resultList == null) {
				result.put("results", new ArrayList<String>());
				result.put("totalRows", "0");
			} else {
				if (resultList instanceof EntityListIterator) {
					EntityListIterator tmpList = (EntityListIterator) resultList;
					List<GenericValue> listGenericValue = null;
					if (UtilValidate.isEmpty(totalRows)) {
						totalRows = String.valueOf(tmpList.getResultsTotalSize());
						if (iSize != 0) {
							if (iIndex == 0) {
								listGenericValue = tmpList.getPartialList(0, iSize);
							} else {
								listGenericValue = tmpList.getPartialList(iIndex
										* iSize + 1, iSize);
							}
						} else {
							listGenericValue = tmpList.getCompleteList();
						}
					} else {
						listGenericValue = tmpList.getCompleteList();
					}
					
					// get Translated value
					Locale locale = (Locale) context.get("locale");
					if(!listGenericValue.isEmpty()){
					    Collection<String> listKeys = listGenericValue.get(0).getAllKeys();
    					Iterator itr = listKeys.iterator();
    					for(int k = 0; k < listGenericValue.size(); k++){
						String modelEntity = listGenericValue.get(k).getModelEntity().toString();
						if (UtilValidate.isEmpty(listGenericValue.get(k).getModelEntity().getDefaultResourceName()) || modelEntity.contains("ModelViewEntity")) {
	    					    while(itr.hasNext()){
	    					        String strTmp = itr.next().toString();
	    					        if(listGenericValue.get(k).get(strTmp) != null){
	    					        	listGenericValue.get(k).put(strTmp, listGenericValue.get(k).get(strTmp));
	    					        }
	    					    }
    						} else {
    							while(itr.hasNext()){
	    					        String strTmp = itr.next().toString();
	    					        if(listGenericValue.get(k).get(strTmp) != null){
	    					        	listGenericValue.get(k).put(strTmp, listGenericValue.get(k).get(strTmp, locale));
	    					        }
	    					    }
    						}
    					}
					}
					/*
					 * update locale: for (int i=0; i < listGeneralValue.size();
					 * i++ ) + { + nextValue = listGeneralValue.get(i); +
					 * String[] arrDictionaryColumns =
					 * strDictionaryColumns.split(";"); + for (int iDic = 0;
					 * iDic < arrDictionaryColumns.length; iDic++) + { +
					 * nextValue.set(arrDictionaryColumns[iDic],
					 * nextValue.get(arrDictionaryColumns[iDic], locale)); + } +
					 * nextValue.set("total", invoiceTotal); +
					 * listGeneralValue.set(i, nextValue); + }
					 */
					if (!"".equals(strDictionaryColumns)) {
						String[] arrDic = strDictionaryColumns.split(";", -1);
						for (int i = 0; i < listGenericValue.size(); i++) {
							GenericValue nextValue = listGenericValue.get(i);
							for (int iDic = 0; iDic < arrDic.length; iDic++) {
								nextValue.set(arrDic[iDic],
										nextValue.get(arrDic[iDic], locale));
							}
							listGenericValue.set(i, nextValue);
						}
					}
					result.put("results", listGenericValue);
					// get total record
					// tmpList.last();
					if (UtilValidate.isEmpty(totalRows)) {
						result.put("totalRows", String.valueOf(tmpList.getResultsTotalSize()));
					} else {
						result.put("totalRows", totalRows);
					}
					//
					tmpList.close();
				} else if (resultList instanceof java.util.LinkedList) {
					java.util.LinkedList tmpList = (java.util.LinkedList) resultList;
					if (tmpList.size() > 0) {
						List<Object> list = null;
						if (iSize != 0 && (tmpList.size() > iSize)) {
							if (iIndex == 0) {
								list = tmpList.subList(0, iSize);
							} else {
								if(tmpList.size() > ((iIndex + 1) *iSize)){
									list = tmpList.subList(iIndex * iSize,
											((iIndex + 1) *iSize));
								}else{
									list = tmpList.subList(iIndex * iSize,
											tmpList.size());
								}
							}
						} else {
							list = tmpList;
						}
						result.put("results", list);
						if(totalRows != null){
							result.put("totalRows", totalRows);
						}else{
							result.put("totalRows", String.valueOf(tmpList.size()));
						}
					} else {
						result.put("results", tmpList);
						result.put("totalRows", "0");
					}
				} else { // if
					List<Object> list = (List<Object>) resultList;
					result.put("totalRows", String.valueOf(list.size()));
					if (iSize != 0 && (list.size() > iSize)) {
						if (iIndex == 0) {
							list = list.subList(0, iSize);
						} else {
							int toIndex = iIndex * iSize + iSize;
							if (list.size() > toIndex) {
								list = list.subList(iIndex * iSize, iIndex
										* iSize + iSize);
							} else {
								list = list.subList(iIndex * iSize, list.size());
							}
							result.put("results", list);
						}
					}

					if (totalRows != null) {
						result.put("totalRows", totalRows);
					}
					result.put("results", list);
				}
			}
		} catch (Exception e) {
			Debug.logError(e, module);
			List<Map<String, Object>> res = FastList.newInstance();
			result.put("results", res);
			result.put("totalRows", "0");
			
		}
		return result; 
	}
	
	public static EntityCondition buildEntityCondition(String field, String value, String operation) {
		SqlOperator so = SqlOperator.valueOf(operation);
		EntityComparisonOperator<?, ?> fieldOp = null;
		switch (so) {
			case CONTAINS: {
				fieldOp = EntityOperator.LIKE;
				value = "%" + value + "%";
				break;
			}
			case DOES_NOT_CONTAIN: {
				fieldOp = EntityOperator.NOT_LIKE;
				value = "%" + value + "%";
				break;
			}
			case EQUAL: {
				fieldOp = EntityOperator.EQUALS;
				break;
			}
			case NOT_EQUAL: {
				fieldOp = EntityOperator.NOT_EQUAL;
				break;
			}
			case GREATER_THAN: {
				fieldOp = EntityOperator.GREATER_THAN;
				break;
			}
			case LESS_THAN: {
				fieldOp = EntityOperator.LESS_THAN;
				break;
			}
			case GREATER_THAN_OR_EQUAL: {
				fieldOp = EntityOperator.GREATER_THAN_EQUAL_TO;
				break;
			}
			case LESS_THAN_OR_EQUAL: {
				fieldOp = EntityOperator.LESS_THAN_EQUAL_TO;
				break;
			}
			case STARTS_WITH: {
				fieldOp = EntityOperator.LIKE;
				value = value + "%";
				break;
			}
			case ENDS_WITH: {
				fieldOp = EntityOperator.LIKE;
				value = "%" + value;
				break;
			}
			case NULL: {
				fieldOp = EntityOperator.EQUALS;
				value = null;
				break;
			}
			case NOT_NULL: {
				fieldOp = EntityOperator.NOT_EQUAL;
				value = null;
				break;
			}
			case EMPTY: {
				fieldOp = EntityOperator.EQUALS;
				value = null;
				break;
			}
			case NOT_EMPTY: {
				fieldOp = EntityOperator.NOT_EQUAL;
				value = null;
				break;
			}
			default:
				break;
		}
		
		Object fieldValue = null;
		
		if (field.contains("(BigDecimal)")) {
			field = field.substring(0, field.indexOf("("));
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern,
					symbols);
			decimalFormat.setParseBigDecimal(true);
			// parse the string
			try {
				fieldValue = (BigDecimal) decimalFormat.parse((String) value);
			} catch (ParseException e) {
				Debug.logError(e, module);
			}
		}
		if(field.contains("Long")){
			field = field.substring(0, field.indexOf("("));
			try {
				if(UtilValidate.isNotEmpty(value)){
					fieldValue = new java.lang.Long((String) value);
				}	
			} catch (Exception e) {
				Debug.logError(e, module);
			}	
		}
		
		if(field.contains("Double")){
			field = field.substring(0, field.indexOf("("));
			try {
				if(UtilValidate.isNotEmpty(value)){
					fieldValue = new java.lang.Double((String) value);	
				}
			} catch (Exception e) {
				Debug.logError(e, module);
			}	
		}
		
		if (field.contains("(Date)")) {
			field = field.substring(0, field.indexOf("("));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				fieldValue = new java.sql.Date(
						((java.util.Date) sdf.parse((String) value))
								.getTime());
			} catch (ParseException e) {
				Debug.logError(e, module);
				return null;
			}
		}
		if (field.contains("(Timestamp)")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			if (field.contains("[")) {
				dateFormat = new SimpleDateFormat(field.substring(field.indexOf("[") + 1, field.indexOf("]")));
			}else{
				// define list of patterns
				List<DateRegexPattern> listPatterns = new ArrayList<DateRegexPattern>();
				listPatterns.add(new DateRegexPattern("HH:mm:ss dd-MM-yyyy", "^[0-9]{2}:[0-9]{2}:[0-9]{2}\\s[0-9]{2}-[0-9]{2}-[0-9]{4}$")); //HH:mm:ss dd-MM-yyyy
				listPatterns.add(new DateRegexPattern("HH:mm:ss dd/MM/yyyy", "^[0-9]{2}:[0-9]{2}:[0-9]{2}\\s[0-9]{2}/[0-9]{2}/[0-9]{4}$")); //HH:mm:ss dd/MM/yyyy
				// Iterate to get missing pattern
				for(DateRegexPattern tmpDRP:listPatterns){
					Pattern r = Pattern.compile(tmpDRP.getRegexPattern());
					Matcher m = r.matcher(value.toString());
					if (m.find()) {
						dateFormat = new SimpleDateFormat(tmpDRP.getDatePattern());
						break;
					}
				}
			}
			try {
				Date parsedDate = new java.sql.Date(dateFormat.parse((String) value).getTime());
				fieldValue = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e) {
				Debug.logError(e, module);
				return null;
			}
			field = field.substring(0, field.indexOf("("));
		}
		
		EntityCondition condition = null;
		if(fieldOp != null) {
			condition = EntityCondition.makeCondition(field, fieldOp, value);
		}
		
		return condition;
	}
	
	public static EntityCondition buildFilter(JSONObject filter) {
		String operation = filter.getString("operation");
		
		List<EntityCondition> conditions = new ArrayList<EntityCondition>();
		if (filter.get("expressions") != null) {
			JSONArray expressions = filter.getJSONArray("expressions");
			for(int i = 0; i < expressions.size(); ++i) {
				JSONObject f = expressions.getJSONObject(i);
				EntityCondition condition = buildFilter(f);
				if(condition != null) {
					conditions.add(condition);
				}
			}
		} else {
			String field = (String) filter.get("field");
			String value = (String) filter.get("value");
			String op = (String) filter.get("operation");
			if(field!= null && value != null && op != null) {
				EntityCondition condition = buildEntityCondition(field,value,op);
				conditions.add(condition);
			}
		}
		SqlOperator so = SqlOperator.valueOf(operation);
		switch (so) {
		case AND:
			return EntityCondition.makeCondition(conditions, EntityOperator.AND);
		case OR:
			return EntityCondition.makeCondition(conditions, EntityOperator.AND);
		default:
			return EntityCondition.makeCondition(conditions);
		}
	}

}

class DateRegexPattern{
	String datePattern;
	String regexPattern;
	public DateRegexPattern(String datePattern,String regexPattern){
		this.datePattern = datePattern;
		this.regexPattern = regexPattern;
	}
	public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	public String getRegexPattern() {
		return regexPattern;
	}
	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}
	
}

enum SqlOperator {
	CONTAINS, DOES_NOT_CONTAIN, NOT_EMPTY, EMPTY, EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL, STARTS_WITH, ENDS_WITH, NULL, NOT_NULL,
	AND, OR
}