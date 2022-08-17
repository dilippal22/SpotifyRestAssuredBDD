package com.spotify.oauth2.api;

public enum StatusCode {
	
	CODE_200(200, ""),
	CODE_201(201, ""),
	CODE_400(400, "Missing required field: name"),
	CODE_401_INVALID_TOKEN(401, "Invalid access token"),
	CODE_401_EXPIRED_TOKEN(401, "The access token expired");
	
	private final int StatucCode;
	private final String message;

	StatusCode(int StatusCode, String message){
		this.StatucCode = StatusCode;
		this.message = message;
	}
	
	public int getStatusCode(){
		return StatucCode;
	}
	
	public String getMessage(){
		return message;
	}
}
