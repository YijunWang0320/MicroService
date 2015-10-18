public class CourseDatabaseHelper {
	private Connection conn;
	private CourseManager manager = new CourseManager();
	// add a new course into database
	private int add(Course course) {

	}
	// delete a specific course given course id
	// send a message to student service after deletion
	private int delete(int id) {
		//course deletion
		Message msg = COURSE_DELETION_INFO;
		manager.sendMessage(msg);
	}
	// update the information of a course
	// send a message to student service after updating	
	private int update(Course newCourse) {
		// course update
		Message msg = COURSE_UPDATE_INFO;
		manager.sendMessage(msg);

	}
	// get the information of a course given course id
	private Course get(int id) {

	}
}