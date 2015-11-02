package micro.course;

public class DbTester {
	public static void main(String args[]) {
		DatabaseHelper tester = new DatabaseHelper();
		tester.connect();

		Course c = tester.getCourse(1);
		System.out.println(c.getCourseId() + "   " + c.getCourseName());
		c.setCourseName("somecourse");
		tester.updateCourse(c);
		c = tester.getCourse(1);
		System.out.println(c.getCourseId() + "   " + c.getCourseName());
		tester.close();
	}
}
