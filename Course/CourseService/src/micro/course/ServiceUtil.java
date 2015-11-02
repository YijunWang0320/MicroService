package micro.course;

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
}
