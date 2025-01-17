package com.projectM.controller;

import com.projectM.model.Chat;
import com.projectM.model.Message;
import com.projectM.model.User;
import com.projectM.request.CreateMessageRequest;
import com.projectM.response.MessageResponse;
import com.projectM.service.MessageService;
import com.projectM.service.ProjectService;
import com.projectM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestBody CreateMessageRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User sender = userService.findUserProfileByJwt(jwt);

        Chat chat = projectService.getChatByProjectId(req.getProjectId());
        if (chat == null) {
           throw new Exception("chat not found");
        }
        Message message = messageService.sendMessage(sender.getId(), req.getProjectId(), req.getMessage());


        return ResponseEntity.ok(message);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Message>> getMessageByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(messageService.getMessageByProjectId(projectId));
    }
}
