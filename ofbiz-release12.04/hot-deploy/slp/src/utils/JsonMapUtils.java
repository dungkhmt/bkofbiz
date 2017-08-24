package utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class JsonMapUtils {
	public static Map<String,Object> json2MapStrObject(String json){
		Type mapType = new TypeToken<Map<String, Object>>(){}.getType();  
		Map<String,Object> map = new Gson().fromJson(json, mapType);
		return map;
	}
}
