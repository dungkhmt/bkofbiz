package org.ofbiz.bkeuniv.events;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.utils.JSONx;

public class JqxEvents {
	public static final String module = JqxEvents.class.getName();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String jqxEventProcessor(HttpServletRequest request, HttpServletResponse response) {
		GenericDelegator delegator = (GenericDelegator) DelegatorFactory.getDelegator((String) request.getSession().getAttribute("delegatorName"));
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        TimeZone timeZone = (TimeZone) request.getSession().getAttribute("timeZone");
        Map<String, String[]> params = request.getParameterMap();
        Locale locale = UtilHttp.getLocale(request);
        Map<String,Object> context = new HashMap<String,Object>();
        context.put("parameters", params);
        context.put("userLogin", userLogin);
        context.put("timeZone", timeZone);
        context.put("locale", locale);
        if(params.containsKey("hasrequest")){
        	context.put("request", request); 
        }
        JqAction jqAction = JqAction.valueOf((params.get("jqaction") != null?((String)params.get("jqaction")[0]):("S")));
        try {
        	Map<String,Object> results = null;
        	switch (jqAction) {
			case S:{
				results = dispatcher.runSync("jqxGridGeneralServicer", context);
				break;
			}
			default:
				break;
			}
        	
        	JSONx json = JSONx.from(results);
            writeJSONtoResponse(json, response);
		} catch (Exception e) {
			Debug.logError("Problems with jqxEventProcessor: " + e.toString(), module);
			Map<String, Object> mapError = new HashMap<String, Object>();
			mapError.put("responseMessage", "error");
			mapError.put("errorMessage", e.toString());
			List<Map<String,Object>> listTMP = new ArrayList<Map<String,Object>>();
			mapError.put("results", listTMP);
			mapError.put("totalRows", "0");
			JSONx json;
			try {
				json = JSONx.from(mapError);
				writeJSONtoResponse(json, response);
			} catch (IOException e1) {
				Debug.logError(e1, module);
			}
			return "error";
		}
		return "success";
	}
	
	public static String toString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        df.setTimeZone( tz );
        String output = df.format( date );
        int inset0 = 9;
        int inset1 = 6;
        String s0 = output.substring( 0, output.length() - inset0 );
        String s1 = output.substring( output.length() - inset1, output.length() );
        String result = s0 + s1;
        result = result.replaceAll( "UTC", "+00:00" );
        return result;
    }
	
	private static void writeJSONtoResponse(JSONx json, HttpServletResponse response) {
        String jsonStr = json.toString();
        if (jsonStr == null) {
            Debug.logError("JSON Object was empty; fatal error!", module);
            return;
        }

        // set the X-JSON content type
        response.setContentType("application/x-json");
        // jsonStr.length is not reliable for unicode characters
        try {
            response.setContentLength(jsonStr.getBytes("UTF8").length);
        } catch (UnsupportedEncodingException e) {
            Debug.logError("Problems with Json encoding: " + e, module);
        }

        // return the JSON String
        Writer out;
        try {
            out = response.getWriter();
            out.write(jsonStr);
            out.flush();
        } catch (IOException e) {
            Debug.logError(e, module);
        }
    }
}
enum JqAction{
	S,U,D,C,CL,UL,DL
}