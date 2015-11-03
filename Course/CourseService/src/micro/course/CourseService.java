package micro.course;

import java.io.IOException;
import java.io.PrintWriter;

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
	private static final String course_name_key = "course_name_key";
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
		// TODO Auto-generated method stub
		
		String requestUri = request.getRequestURI().toString();
		PrintWriter out = response.getWriter();
		String command = ServiceUtil.parseUri(requestUri);
		if (command.equals("ADD")) {
			int res = addCourse(request);
			if (res == 0) {
				out.println("ADD successful.");
			} else {
				out.println("failed to add a new entry.");
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
	
	private int deleteCourse(HttpServletRequest request) {
		String id_str = request.getParameter(course_id_key);
		int id = Integer.parseInt(id_str);
		int delete_res = dbhelper.deleteCourse(id);
		return delete_res;
		//TODO: Add delete related relationship.
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
}
