package com.projectM.controller;

import com.projectM.model.Chat;
import com.projectM.model.Invitation;
import com.projectM.model.Project;
import com.projectM.model.User;
import com.projectM.request.InvitationRequest;
import com.projectM.response.MessageResponse;
import com.projectM.service.EmailService;
import com.projectM.service.InvitationService;
import com.projectM.service.ProjectService;
import com.projectM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class projectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user,category,tag);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
            )
            throws Exception {
        userService.findUserProfileByJwt(jwt);  // throws an error from (findUserByEmail) if user not found
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestBody Project project,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getId();
        projectService.deleteProject(projectId, userId);
        MessageResponse res = new MessageResponse("Project deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword,user);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InvitationRequest invitationRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(invitationRequest.getUserEmail(), invitationRequest.getProjectId());
        MessageResponse res = new MessageResponse("invitation sent successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptProjectInvitation(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token);
        projectService.addUserToProject(user.getId(), invitation.getProjectId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

//    @DeleteMapping("/remove_user")
//    public ResponseEntity<MessageResponse> removeUserFromProject(
//            @RequestParam Long projectId,
//            @RequestParam Long userId,
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception {
//        userService.findUserProfileByJwt(jwt);
//        projectService.removeUserFromProject(userId,projectId);
//        MessageResponse res = new MessageResponse("user removed from the project");
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }


}
