package com.framework.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.bean.UnReadChatMessage;
import com.framework.dao.ChatMessageMapper;
import com.framework.model.ChatMessage;
import com.framework.model.ChatMessageExample;
import com.framework.model.ChatMessageExample.Criteria;
import com.framework.service.ChatMessageService;
import com.framework.util.LoginUtils;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChatMessageMapper chatMessageMapper;

	@Override
	public List<UnReadChatMessage> getUnReadList() throws Exception {
		List<UnReadChatMessage> chatMessages = chatMessageMapper.getUnReadList(LoginUtils.getUserInfo().getPersonCode());
		return chatMessages;
	}

	@Override
	public void setMsgRead(Integer id) throws Exception {
		ChatMessage record = new ChatMessage();
		record.setId(id);
		record.setIsRead("1");
		chatMessageMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void setFromMsgRead(String fromPersonCode) throws Exception {
		ChatMessage record = new ChatMessage();
		record.setIsRead("1");
		ChatMessageExample example = new ChatMessageExample();
		example.createCriteria()
		.andCreateByEqualTo(fromPersonCode)
		.andPersonCodeEqualTo(LoginUtils.getUserInfo().getPersonCode())
		.andIsReadIsNull();
		chatMessageMapper.updateByExampleSelective(record, example);
	}
}
