package com.framework.bean.common;

public class ResultMessage<T> {
	private String message;
	private String code;
	private boolean success;
	
	private T model;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "ResultMessage [message=" + message + ", code=" + code + ", success=" + success + ", model=" + model
				+ "]";
	}
}
