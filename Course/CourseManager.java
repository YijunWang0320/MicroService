public class CourseManager {
	private CourseDatabaseHelper helper = null;
	// deal with the request sent from the user
	public JSONObject requestHandler(JSONObject request) {
		// parse the request sent from the user
		helper = new CourseDatabaseHelper();
		// invoke corresponding operations based on request info
	}

	// send message specifying the course update/deletion to student service
	private void sendMessage(Message msg) {

	}
}