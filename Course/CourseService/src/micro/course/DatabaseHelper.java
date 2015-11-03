package micro.course;

import java.sql.*;

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
	
	public void close(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}