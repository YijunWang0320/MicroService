package micro.course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

	Connection c = null;

	public void connect(){
		try {
	    	try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			c = DriverManager.getConnection("jdbc:mysql://129.236.229.171:3306/Course?" +
			        "user=root&password=");
			System.out.println("Connect!");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
			return res;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	public int dropCourse(Enrollment e) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String dropSql = "DELETE from Enrollment WHERE course_id="
							+ String.valueOf(e.getCourse_id()) 
							+ " AND student_id=" + e.getStudent_id() +";";
			int res = stmt.executeUpdate(dropSql);
			stmt.close();
			return res;
		} catch (SQLException eee) {
			return -1;
		}
	}

	public int enrollCourse(Enrollment enroll) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			int student_id = enroll.getStudent_id();
			int course_id = enroll.getCourse_id();
			String student_name = enroll.getStudent_name();
			String addEnroll = "INSERT INTO Enrollment VALUES (" + 
					   String.valueOf(course_id) + "," + 
					   String.valueOf(student_id) + ",\"" + 
					   student_name + "\");";
			int res = stmt.executeUpdate(addEnroll);
			stmt.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<Enrollment> findEnrolledStudents(int courseId) {
		List<Enrollment> result = new ArrayList<Enrollment>();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String findSql = "SELECT * FROM Enrollment WHERE course_id = " + 
							String.valueOf(courseId) + ";";
			ResultSet rs = stmt.executeQuery(findSql);
			while(rs.next()) {
				int student_id = rs.getInt("student_id");
				String name = rs.getString("student_name");
				Enrollment enroll = new Enrollment();
				enroll.setCourse_id(courseId);
				enroll.setStudent_id(student_id);
				enroll.setStudent_name(name);
				result.add(enroll);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public void close(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}