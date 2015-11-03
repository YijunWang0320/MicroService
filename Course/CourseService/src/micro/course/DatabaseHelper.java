package micro.course;

import java.sql.*;

import javax.servlet.ServletContext;

public class DatabaseHelper {

	Connection c = null;
	ServletContext servletContext;
	/**
	public DatabaseHelper(ServletContext context) {
		servletContext = context;
	}**/
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			//System.out.println("Here");
			//String path = servletContext.getRealPath("course_service.db");
			//System.out.println(path);
			//c = DriverManager.getConnection("jdbc:sqlite:"+path);
			c = DriverManager.getConnection("jdbc:sqlite://course_service.db");
			c.setAutoCommit(false);
			System.out.println("Successful");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return;
		}
		
	}

	public int addCourse(Course course) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			int courseId = course.getCourseId();
			String courseName = course.getCourseName();
			String insertSql = "INSERT INTO Course VALUES (" + 
							   String.valueOf(courseId) + ",\"" + courseName + "\");";
			stmt.executeUpdate(insertSql);
			stmt.close();
			c.commit();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Course getCourse(int courseId) {
		Statement stmt = null;
		Course course = null;
		try {
			stmt = c.createStatement();
			String querySql = "SELECT * FROM Course WHERE course_id = " + String.valueOf(courseId) + ";";
			ResultSet rs = stmt.executeQuery(querySql);
			while(rs.next()) {
				int id = rs.getInt("course_id");
				String name = rs.getString("course_name");
				course = new Course();
				course.setCourseId(id);
				course.setCourseName(name);
			}
			stmt.close();
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return course;
	}

	public int updateCourse(Course newCourse) {
		Statement stmt = null;
		try {
		    stmt = c.createStatement();
		    int newId = newCourse.getCourseId();
		    String newName = newCourse.getCourseName();
		    String sql = "UPDATE Course set course_name = \"" + newName + 
		    			 "\"where course_id = " + String.valueOf(newId) + ";";
		    int res = stmt.executeUpdate(sql);
		    stmt.close();
		    c.commit();		
		    return res;
		} catch (SQLException e) {
			return -1;
		}
	}

	public int deleteCourse(int courseId) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String deleteSql = "DELETE from Course WHERE course_id=" + String.valueOf(courseId) +";";
			int res = stmt.executeUpdate(deleteSql);
			stmt.close();
			c.commit();
			return res;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	public int dropCourse(int course_id) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String dropSql = "DELETE from Enrollment WHERE course_id=" + String.valueOf(course_id) +";";
			int res = stmt.executeUpdate(dropSql);
			stmt.close();
			c.commit();
			return res;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	//public List<Enrollment>
	
	public void close(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}