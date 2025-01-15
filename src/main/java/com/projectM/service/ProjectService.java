package com.projectM.service;

import com.projectM.model.Chat;
import com.projectM.model.Project;
import com.projectM.model.User;

import java.util.List;

//@Service
public interface ProjectService {

    Project createProject(Project project, User user ) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;

    void addUserToProject(Long userId, Long projectId) throws Exception;

    void removeUserFromProject(Long userId, Long projectId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;
}
