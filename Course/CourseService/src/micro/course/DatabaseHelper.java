package micro.course;

import java.sql.*;

public class DatabaseHelper {

	Connection c = null;

	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:course_service.db");
			c.setAutoCommit(false);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Successful");
	}

	public void addCourse(Course course) {
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
		} catch (SQLException e) {
			e.printStackTrace();
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
			
		}
		return course;
	}

	public void updateCourse(Course newCourse) {
		Statement stmt = null;
		try {
		    stmt = c.createStatement();
		    int newId = newCourse.getCourseId();
		    String newName = newCourse.getCourseName();
		    String sql = "UPDATE Course set course_name = \"" + newName + 
		    			 "\"where course_id = " + String.valueOf(newId) + ";";
		    stmt.executeUpdate(sql);
		    stmt.close();
		    c.commit();			
		} catch (SQLException e) {
			
		}
	}

	public void deleteCourse(int courseId) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String deleteSql = "DELETE from Course WHERE course_id=" + String.valueOf(courseId) +";";
			stmt.executeUpdate(deleteSql);
			stmt.close();
			c.commit();
		} catch (SQLException e) {
			
		}
	}
	
	public void close(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}