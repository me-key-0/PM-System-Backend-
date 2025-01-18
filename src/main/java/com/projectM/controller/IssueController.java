package com.projectM.controller;

import com.projectM.model.Issue;
import com.projectM.model.User;
import com.projectM.request.IssueRequest;
import com.projectM.response.MessageResponse;
import com.projectM.service.IssueService;
import com.projectM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }


    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }


    @PostMapping
    public ResponseEntity<Issue> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        User user = userService.findUserProfileByJwt(jwt);
        Issue newIssue = issueService.createIssue(issue,user);

        return new ResponseEntity<>(newIssue, HttpStatus.CREATED);
    }


    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        User assignee = userService.findUserProfileByJwt(jwt);
        issueService.deleteIssue(issueId, assignee.getId());

        MessageResponse res = new MessageResponse("issue deleted successfully");

        return ResponseEntity.ok(res);
    }


    @PutMapping("/{issueId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @RequestParam Long assigneeId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(issueService.addUserToIssue(issueId, assigneeId));
    }


    @PatchMapping("/{issueId}")
    public ResponseEntity<Issue> updateStatus(
            @PathVariable Long issueId,
            @RequestParam String status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(issueService.updateStatus(issueId,status));
    }

}
