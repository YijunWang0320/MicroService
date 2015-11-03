package micro.course;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class ServiceUtil {
	/**
	 * Parse the request uri to api method
	 * @param uri request uri
	 * @return method name, or null if the request is invalid
	 */
	public static String parseUri(String uri) {
		
		String [] strs = uri.split("/");
		String request = strs[2].toUpperCase();
		if (request.equals("ENROLL")) {
			return "ENROLL";
		} else if (request.equals("DROP")) {
			return "DROP";
		} else if (request.equals("DELETE")){
			return "DELETE";
		} else if (request.equals("ADD")) {
			return "ADD";
		} else if (request.equals("SEARCH")){
			return "SEARCH";
		} else if (request.equals("UPDATE")){
			return "UPDATE";
		} else {
			return null;
		}
	}
	
	public static boolean studentChecker(String sid, String sname) throws URISyntaxException {
		HttpClient httpclient = HttpClients.createDefault();
		URI address = new URI("http", null, "129.236.212.69", 5000,
				"/api/check_exist/", "student_id="+ sid + "&student_name=" + sname, null);
//		URI address = new URI("http", null, "129.236.212.69", 5003, 
//				"/student/check_exist/", "data=%7B%22student_id%22%3A+%22wa4738%22%2C+%22student_name%22%3A+%22wangyijun%22%7D", null);
		HttpPost httppost = new HttpPost(address);
		httppost.addHeader("Accept", "*/*");
//		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//		params.add(new BasicNameValuePair("student_id", sid));
//		params.add(new BasicNameValuePair("student_name", sname));
		InputStream instream = null;
		System.out.println("In student checker:");
		try {
			//httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			System.out.println("Address: " + address + " Code: " + response.getStatusLine().getStatusCode());

			if (entity != null) {
			    instream = entity.getContent();
			    StringWriter writer = new StringWriter();
			    IOUtils.copy(instream, writer, "UTF-8");
			    String ans = writer.toString();
			    System.out.println(ans);
			    if (ans.toUpperCase().equals("TRUE")) {
			    	return true;
			    } else {
			    	return false;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			return false;
		}
		return true;
	}
}
