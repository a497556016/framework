package com.framework.bean;

import com.framework.model.ChatMessage;

public class UnReadChatMessage extends ChatMessage {
	private String fromLastName;

	public String getFromLastName() {
		return fromLastName;
	}

	public void setFromLastName(String fromLastName) {
		this.fromLastName = fromLastName;
	}
}
