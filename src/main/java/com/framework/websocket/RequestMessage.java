package com.framework.websocket;

public class RequestMessage {
	private String toPersonCode;
	private String message;
	public String getToPersonCode() {
		return toPersonCode;
	}
	public void setToPersonCode(String toPersonCode) {
		this.toPersonCode = toPersonCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "RequestMessage [toPersonCode=" + toPersonCode + ", message=" + message + "]";
	}
}
