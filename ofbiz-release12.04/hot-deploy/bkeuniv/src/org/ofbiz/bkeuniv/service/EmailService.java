package org.ofbiz.bkeuniv.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;

public class EmailService {
	
	public static final String module = EmailService.class.getName();
	
	public static String sendEmail(HttpServletRequest request,
			 HttpServletResponse response) {
			 // Get the local Service dispatcher from the context
			 // Note: Dont forget to import the org.ofbiz.service.* classes
			 LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
			 String errMsg = null;
			 // The following are hardcoded as an example only
			 // Your program would set these up from the context or from
			 // database lookups
			 String mailBody = (String) request.getParameter("body");
			 String mailTo = (String) request.getParameter("to");
			 String mailSubject = (String) request.getParameter("subject");
			 Map sendMailContext = new HashMap();
			 
			 sendMailContext.put("sendTo", mailTo);
			 //sendMailContext.put("sendCC", "cc@ofbiz.org");
			 //sendMailContext.put("sendBcc","you@ofbiz.org" );
			 sendMailContext.put("sendFrom", "contactbkeuniv@gmail.com");
			 sendMailContext.put("subject", mailSubject);
			 sendMailContext.put("body", mailBody);
			 try {
			 // Call the sendMail Service and pass the sendMailContext
			 // Map object.
			 // Set timeout to 360 and wrap with a new transaction
			 Map<String, Object> result =
			 dispatcher.runSync("sendMail", sendMailContext,360, true);
			 // Note easy way to get errors when they are returned
			 // from another Service
			 if (ModelService.RESPOND_ERROR.equals((String)
			 result.get(ModelService.RESPONSE_MESSAGE))) {
			 Map<String, Object> messageMap =
			 UtilMisc.toMap("errorMessage",
			 result.get(ModelService.ERROR_MESSAGE));
			 errMsg = "Problem sending this email";
			 request.setAttribute("_ERROR_MESSAGE_", errMsg);
			 return "error";
			 }
			 }
			 catch (GenericServiceException e) {
			 // For Events error messages are passed back
			 Debug.logWarning(e, module);
			 // as Request Attributes
			 errMsg = "Problem with the sendMail Service call";
			 request.setAttribute("_ERROR_MESSAGE_", errMsg);
			 return "error";
			 }
			 return "success";
			}
    
}
