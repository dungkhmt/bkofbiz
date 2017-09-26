package org.bkofbiz.slp.tspd;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import utils.JsonMapUtils;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class VRP3DService {
	public static final String MODULE_NAME = TSPDService.class.getName();
	public static final MediaType JSON_HEADER = MediaType
			.parse("application/json; charset=utf-8");
	
	public static String returnResult ;
	
	public static String computevrp3d(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("computeVRP3D");
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory());
		java.util.List lst = null;
		
		String result = "AttachementException";
		
		try {
			lst = fu.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result = "AttachementException";
			return result;
		}
		try {
			FileItem file_item = (FileItem) lst.get(0);
			InputStream file = file_item.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(file, writer,"UTF-8");
			String json = writer.toString();
			
			System.out.println("dataJson"+json);
			OkHttpClient client = new OkHttpClient();
			client.setConnectTimeout(180, TimeUnit.SECONDS);
			client.setReadTimeout(180, TimeUnit.SECONDS);
			client.setWriteTimeout(180, TimeUnit.SECONDS);
			RequestBody body = RequestBody.create(JSON_HEADER,
					json);
			Request request_server = new Request.Builder()
					.url("http://localhost:8088/ezRoutingAPI/vrp-load3d")
					.post(body).build();
			Response response_receive = null;
			String resultString = null;
			
			response_receive = client.newCall(request_server).execute();
			resultString = response_receive.body().string();
			returnResult = resultString;
			System.out.println("reponse vrp3d : "+resultString);
			//Debug.logInfo(resultString, MODULE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "AttachementException";
			return result;
		}
		return ("AttachementSuccess");
	}
	
	public static Map<String, Object> returnsolution(DispatchContext dctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> suc = ServiceUtil.returnSuccess();
		Map<String,Object> map =JsonMapUtils.json2MapStrObject(returnResult);
		suc.put("sol", map);
		return suc;
	}
}
