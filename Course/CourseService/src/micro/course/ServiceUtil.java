package micro.course;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		HttpPost httppost = new HttpPost(address);
		InputStream instream = null;
		String linkRef = null;
		URI redirect = null;
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
			    instream = entity.getContent();
			    StringWriter writer = new StringWriter();
			    IOUtils.copy(instream, writer, "UTF-8");
			    String ans = writer.toString();
			    Document doc = Jsoup.parse(ans);
			    Elements links = doc.getElementsByTag("a");
			    for(Element link : links) {
			    	linkRef = link.attr("href");
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
		}
		redirect = new URI(linkRef);
		httppost = new HttpPost(redirect);
		HttpResponse finalResponse = null;
		HttpEntity finalEntity = null;
		try {
			finalResponse = httpclient.execute(httppost);
			finalEntity = finalResponse.getEntity();
			if (finalEntity != null) {
				instream = finalEntity.getContent();
			    StringWriter writer = new StringWriter();
			    IOUtils.copy(instream, writer, "UTF-8");
			    String ans = writer.toString();
			    if (ans.toUpperCase().equals("TRUE")) {
			    	return true;
			    } else {
			    	return false;
			    }
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
