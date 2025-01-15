package com.projectM.service;

import com.projectM.model.Chat;
import com.projectM.model.Project;
import com.projectM.model.User;
import com.projectM.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
            return null;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        return List.of();
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        return null;
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        return null;
    }

    @Override
    public void addUserToProject(Long userId, Long projectId) throws Exception {

    }

    @Override
    public void removeUserFromProject(Long userId, Long projectId) throws Exception {

    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        return null;
    }
}
