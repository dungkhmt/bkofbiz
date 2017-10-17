package src.org.ofbiz.bkeuniv.academicyear;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class AcademicYearService {
	public static String module = AcademicYearService.class.getName();
	
	@SuppressWarnings({ "unchecked" })
	public static void getListAcademicYears(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		try{
			
			List<GenericValue> acaYears = delegator.findList("AcademicYear", 
					null, 
					null,
					null,
					null,
					false);
			Debug.log(module + "::getListAcademicYears, years.sz = " + acaYears.size());
			
			String rs = "{\"years\":[";
			for (int i = 0; i < acaYears.size(); i++) {
				GenericValue st = acaYears.get(i);
				rs += "{\"id\":\"" + st.get("academicYearId") + "\",\"name\":\""
						+ st.get("academicYearName") + "\"}";
				if (i < acaYears.size() - 1)
					rs += ",";

				
			}
			rs += "]}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}
	
	public static Map<String, Object> getListAcademicYears(DispatchContext ctx, Map<String, ? extends Object> context){
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try{
			Delegator delegator = ctx.getDelegator();
			List<GenericValue> acaYears = delegator.findList("AcademicYear", 
					null, 
					null,
					null,
					null,
					false);
			Debug.log(module + "::getListAcademicYears, years.sz = " + acaYears.size());
			
			retSucc.put("academicYears", acaYears);
		}catch(Exception ex){
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}
}
