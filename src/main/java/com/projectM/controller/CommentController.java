package com.projectM.controller;

import com.projectM.model.Comment;
import com.projectM.model.User;
import com.projectM.request.CreateCommentRequest;
import com.projectM.response.MessageResponse;
import com.projectM.service.CommentService;
import com.projectM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(commentService.getCommentByIssueId(issueId));
    }


    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);


        return ResponseEntity.ok(commentService.createComment(req.getIssueId(), user.getId(), req.getComment()));
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse res = new MessageResponse("comment deleted successfully");

        return ResponseEntity.ok(res);
    }
}
