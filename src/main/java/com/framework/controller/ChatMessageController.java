package com.framework.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.UnReadChatMessage;
import com.framework.bean.common.ResultMessage;
import com.framework.model.ChatMessage;
import com.framework.service.ChatMessageService;

@Controller
@RequestMapping("/chatMsg")
public class ChatMessageController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@RequestMapping("/getUnReadList")
	@ResponseBody
	public ResultMessage<List<UnReadChatMessage>> getUnReadList(){
		ResultMessage<List<UnReadChatMessage>> resultMessage = new ResultMessage<>();
		try{
			List<UnReadChatMessage> chatMessages = chatMessageService.getUnReadList();
			resultMessage.setModel(chatMessages);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return resultMessage;
	}
	
	@RequestMapping("/setMsgRead")
	@ResponseBody
	public ResultMessage<?> setMsgRead(Integer id){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			chatMessageService.setMsgRead(id);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return resultMessage;
	}
	
	@RequestMapping("/setFromMsgRead")
	@ResponseBody
	public ResultMessage<?> setFromMsgRead(String fromPersonCode){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			chatMessageService.setFromMsgRead(fromPersonCode);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return resultMessage;
	}
}
