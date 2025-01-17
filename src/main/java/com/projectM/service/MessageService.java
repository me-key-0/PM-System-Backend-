package com.projectM.service;

import com.projectM.model.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long userId, Long chatId, String message ) throws Exception;

    List<Message> getMessageByProjectId(Long projectId) throws Exception;



}
