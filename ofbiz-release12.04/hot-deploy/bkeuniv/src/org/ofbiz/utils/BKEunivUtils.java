package src.org.ofbiz.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;


public class BKEunivUtils {

	public static final String module = BKEunivUtils.class.getName();
	
	public static JSONObject parseJSONObject(Map<String, Object> map) {
		JSONObject result = new JSONObject();
		Set<String> keys = map.keySet();
		for(String key: keys) {
			Object value = map.get(key);
			if(value instanceof Map) {
				result.put(key, parseJSONObject((Map<String, Object>) value));
			} else {
				if(value instanceof GenericValue) {
					result.put(key, parseJSONObject((GenericValue) value));
				} else {
					if(value instanceof List) {
						result.put(key, parseJSONArray((List<Object>) value));
					} else {
						result.put(key, value.toString());
					}	
				}				
			}
		}
		return result;
	}
	
	public static JSONArray parseJSONArray(List<Object> arr) {
		JSONArray result = new JSONArray();
		
		for(Object ob: arr) {
			if(ob instanceof Map) {
				result.add(parseJSONObject((Map<String, Object>) ob));
			} else {
				if(ob instanceof GenericValue) {
					result.add(parseJSONObject((GenericValue) ob));
				} else {
					if(ob instanceof List) {
						result.add(parseJSONArray((List<Object>) ob));
					} else {
						result.add(ob);
					}	
				}				
			}
		}
		return result;
	}
	
	public static JSONObject parseJSONObject(GenericValue gv) {
		JSONObject result = new JSONObject();
		Set<String> keys = gv.keySet();
		for(String key: keys) {
			result.put(key, gv.getString(key));
		}
		return result;
	}
	
	public static void writeJSONtoResponse(JSONObject json, HttpServletResponse response, int statusCode) {
        String jsonStr = json.toString();
        if (jsonStr == null) {
            Debug.logError("JSON Object was empty; fatal error!", module);
            response.setStatus(500);
            return;
        }
        
        // set the X-JSON content type
        response.setContentType("application/json");
        // jsonStr.length is not reliable for unicode characters
        try {
            response.setContentLength(jsonStr.getBytes("UTF8").length);
        } catch (UnsupportedEncodingException e) {
            Debug.logError("Problems with Json encoding: " + e, module);
        }

        // return the JSON String
        Writer out;
        try {
        	response.setStatus(statusCode);
            out = response.getWriter();
            out.write(jsonStr);
            out.flush();
        } catch (IOException e) {
        	response.setStatus(500);
        	Debug.logError(e, module);
        }
    }
	
}

