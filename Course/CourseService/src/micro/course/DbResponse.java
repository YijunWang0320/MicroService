package micro.course;

public class DbResponse {
	int status;
	String errMessage;
	public DbResponse(int status) {
		this.status = status;
	}
	public DbResponse(int status, String errMessage) {
		this.status = status;
		this.errMessage = errMessage;
	}
}
