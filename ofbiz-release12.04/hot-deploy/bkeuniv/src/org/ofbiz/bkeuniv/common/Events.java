package src.org.ofbiz.bkeuniv.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

public class Events {
	public static final String module = Events.class.getName();
	
	 public static String setSessionLocale(HttpServletRequest request, HttpServletResponse response) {
		 String localeString = request.getParameter("newLocale");
		 if (UtilValidate.isNotEmpty(localeString)) {
			 UtilHttp.setLocale(request, localeString);

			 // update the UserLogin object
			 GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
			 if (userLogin == null) {
				 userLogin = (GenericValue) request.getSession().getAttribute("autoUserLogin");
			 }
			 if(userLogin != null) {
				 GenericValue ulUpdate = GenericValue.create(userLogin);
				 ulUpdate.set("lastLocale", localeString);
				 try {
					 ulUpdate.store();
					 userLogin.refreshFromCache();
				 } catch (GenericEntityException e) {
					 Debug.logWarning(e, module);
				 }
			 }
		 }
		 
		 return "success";
		 
	 }
}
