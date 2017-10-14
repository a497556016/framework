package com.framework.dao;

import com.framework.bean.UnReadChatMessage;
import com.framework.model.ChatMessage;
import com.framework.model.ChatMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatMessageMapper {
    int countByExample(ChatMessageExample example);

    int deleteByExample(ChatMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    List<ChatMessage> selectByExample(ChatMessageExample example);

    ChatMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChatMessage record, @Param("example") ChatMessageExample example);

    int updateByExample(@Param("record") ChatMessage record, @Param("example") ChatMessageExample example);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);

	List<UnReadChatMessage> getUnReadList(String personCode);
}