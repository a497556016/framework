package com.framework.service;

import java.util.List;

import com.framework.bean.UnReadChatMessage;
import com.framework.model.ChatMessage;

public interface ChatMessageService {

	List<UnReadChatMessage> getUnReadList() throws Exception;

	void setMsgRead(Integer id) throws Exception;

	void setFromMsgRead(String fromPersonCode) throws Exception;

}
