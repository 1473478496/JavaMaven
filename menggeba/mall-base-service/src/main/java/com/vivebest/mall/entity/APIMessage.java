package com.vivebest.mall.entity;

public class APIMessage {
	
	public enum Error{
		
		PARAMS_ERR("-999","请求数据解析异常"),
		UNKNOW_ERR("9999","未知异常");
		
		private String code;
		private String desc;
		Error(String code,String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
	
		public static Error getBizType(String code){
	        for(Error o : Error.values()){
	            if(o.getCode().equals(code)){
	            	return o;
	            }
	        }
	        return null;
	    }
		
	}
	
	private String statusCode;
	
	private String errorMsg;
	
	private String userId;
	
	private String userKey;
	
	private String userName;
	
	private String userMobile;
	
	private Long userScore;
	
	private Long userBalance;
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Long getUserScore() {
		return userScore;
	}

	public void setUserScore(Long userScore) {
		this.userScore = userScore;
	}

	public Long getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Long userBalance) {
		this.userBalance = userBalance;
	}
	
	
	
}
