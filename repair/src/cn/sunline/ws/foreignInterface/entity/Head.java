package cn.sunline.ws.foreignInterface.entity;

public class Head {

	
	private String RequestType;//请求类型
	
	private String User;//用户名
	
	private String Password;//密码
	

	public String getRequestType() {
		return RequestType;
	}

	public void setRequestType(String requestType) {
		RequestType = requestType;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
}
