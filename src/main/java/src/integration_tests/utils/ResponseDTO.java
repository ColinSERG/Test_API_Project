package src.integration_tests.utils;

public class ResponseDTO {

	public String body;
	public int status;
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public String getBody() {
		return this.body;
	}
}
