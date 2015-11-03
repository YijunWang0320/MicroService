package micro.course;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CourseService
 */
@WebServlet("/CourseService")
public class CourseService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String course_id_key = "course_id";
	private static final String course_name_key = "course_name";
	private static final String student_id_key = "student_id";
	private static final String student_name_key = "student_name";
    private DatabaseHelper dbhelper;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseService() {
        super();
        // TODO Auto-generated constructor stub
        dbhelper = new DatabaseHelper();
        dbhelper.connect();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 // Set response content type
	      response.setContentType("text/html");

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println("<h1>" + "Your mama" + "</h1>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestUri = request.getRequestURI().toString();
		PrintWriter out = response.getWriter();
		String command = ServiceUtil.parseUri(requestUri);
		if (command.equals("ADD")) {
			int res = addCourse(request);
			if (res == 0) {
				out.println("ADD successful.");
			} else {
				out.println("Failed to ADD.");
			}
			
		} else if (command.equals("SEARCH")) {
			Course course = searchCourse(request);
			if (course != null) {
				out.println("Course id: "+course.getCourseId());
				out.println("Course name: "+course.getCourseName());
			} else {
				out.println("Course does not exist!");
			}
		} else if (command.equals("DELETE")) {
			int res = deleteCourse(request);
			if (res != -1) {
				out.println("DELETE successful");
			} else {
				out.println("Failed to DELETE.");
			}
		} else if (command.equals("UPDATE")) {
			int res = updateCourse(request);
			if (res != -1) {
				out.println("UPDATE successful");
			} else {
				out.println("Failed to UPDATE.");
			}
		} else if (command.equals("ENROLL")) {
			int res = enrollCourse(request);
			if (res != -1) {
				out.println("ENROLL successful");
			} else {
				out.println("Failed to ENROLL.");
			}
		} else if (command.equals("DROP")) {
			int res = dropCourse(request);
			if (res != -1) {
				out.println("DROP successful");
			} else {
				out.println("Failed to DROP.");
			}
		}
		out.close();
	}
	
	private int addCourse(HttpServletRequest request) {
		String id_str = request.getParameter(course_id_key);
		int id = Integer.parseInt(id_str);
		String name = request.getParameter(course_name_key);
		Course course = new Course();
		course.setCourseId(id);
		course.setCourseName(name);
		return dbhelper.addCourse(course);
	}
	
	private Course searchCourse(HttpServletRequest request) {
		String id_str = request.getParameter(course_id_key);
		int id = Integer.parseInt(id_str);
		return dbhelper.getCourse(id);
	}
	
	// Return the number of deleted enrollment. (-1 / > 0)
	private int deleteCourse(HttpServletRequest request) {
		String id_str = request.getParameter(course_id_key);
		int id = Integer.parseInt(id_str);
		int delete_res = dbhelper.deleteCourse(id);
		if (delete_res <= 0) {
			return -1;
		}
		//TODO: Add delete related relationship.
		List<Enrollment> elist = dbhelper.findEnrolledStudents(id);
		int count = 0;
		for (Enrollment e : elist) {
			if (dbhelper.dropCourse(e) >= 0) {
				count ++;
			}
		}
		return count;
	}
	
	private int updateCourse(HttpServletRequest request) {
		String id_str = request.getParameter(course_id_key);
		int id = Integer.parseInt(id_str);
		String name = request.getParameter(course_name_key);
		Course course = new Course();
		course.setCourseId(id);
		course.setCourseName(name);
		return dbhelper.updateCourse(course);
	}
	
	private int enrollCourse(HttpServletRequest request) {
		int sid = Integer.parseInt(request.getParameter(student_id_key));
		int cid = Integer.parseInt(request.getParameter(course_id_key));
		String sname = request.getParameter(student_name_key);
		Course course = dbhelper.getCourse(cid);
		if (course == null) {
			// Course id not exist
			return -1;
		}
		//TODO: check student exist
		Enrollment e = new Enrollment();
		e.setCourse_id(cid);
		e.setStudent_id(sid);
		e.setStudent_name(sname);
		return dbhelper.enrollCourse(e);
	}
	
	private int dropCourse(HttpServletRequest request) {
		int sid = Integer.parseInt(request.getParameter(student_id_key));
		int cid = Integer.parseInt(request.getParameter(course_id_key));
		String sname = request.getParameter(student_name_key);
		Course course = dbhelper.getCourse(cid);
		if (course == null) {
			// Course id not exist
			return -1;
		}
		//TODO: check student exist
		Enrollment e = new Enrollment();
		e.setCourse_id(cid);
		e.setStudent_id(sid);
		e.setStudent_name(sname);
		return dbhelper.dropCourse(e);
	}
}
