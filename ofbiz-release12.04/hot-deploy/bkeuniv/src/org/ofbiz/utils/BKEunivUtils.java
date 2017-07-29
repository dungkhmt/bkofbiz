package src.org.ofbiz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class BKEunivUtils {
	public static Map<String, Object> testService(DispatchContext ctx,
			Map<String, ? extends Object> context){
		 String sa = (String)context.get("a");
		 String sb = (String)context.get("b");
		 int a = Integer.valueOf(sa);
		 int b = Integer.valueOf(sb);
		 int sum = a + b;
		 int prod = a*b;
		 if(a > b){
			 int tmp = a; a = b; b = tmp;
		 }
		 List<Integer> l = new ArrayList<Integer>();
		 for(int c = a; c <= b; c++) l.add(c);
		 
		 Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		 
		 System.out.println("BKEunivUtils::testService, a = " + a + ", b = " + b);
		 
		 retSucc.put("sum", sum + "");
		 retSucc.put("prod", prod + "");
		 retSucc.put("sequence", l);		 
		 return retSucc;
	}
}
