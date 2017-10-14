package com.framework.websocket;

public class ResponseMessage {
	private Integer messageId;
	private String personCode;
	private String lastName;
	private String dateTime;
	private String message;
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ResponseMessage [messageId=" + messageId + ", personCode=" + personCode + ", lastName=" + lastName
				+ ", dateTime=" + dateTime + ", message=" + message + "]";
	}
}
