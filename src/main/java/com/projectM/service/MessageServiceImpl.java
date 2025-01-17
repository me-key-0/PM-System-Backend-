package com.projectM.service;

import com.projectM.model.Chat;
import com.projectM.model.Message;
import com.projectM.model.User;
import com.projectM.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String message) throws Exception {
        Message newMessage = new Message();
        Chat chat = projectService.getChatByProjectId(projectId);
        User sender = userService.findUserById(senderId);

        newMessage.setSender(sender);
        newMessage.setContent(message);
        newMessage.setCreatedAt(LocalDate.now());
        newMessage.setChat(chat);

        chat.getMessages().add(newMessage);
        messageRepository.save(newMessage);

        return newMessage;
    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);

        return  messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }

}
