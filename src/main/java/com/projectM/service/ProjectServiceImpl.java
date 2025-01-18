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

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {


        Project newProject = new Project();
        newProject.setOwner(user);
        newProject.setTags(project.getTags());
        newProject.setName(project.getName());
        newProject.setCategory(project.getCategory());
        newProject.setDescription(project.getDescription());
        newProject.getTeam().add(user);

        Project savedProject = projectRepository.save(newProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);  // set's the relationShip with the prj. (stores the prj-id as a foreign key)
        Chat savedChat = chatService.createChat(chat);
        // we don't need the chat instance to be stored in prj-table, because they already are related with the foreign key referring to the prj's id
        savedProject.setChat(savedChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category))
                    .toList();
        }

        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag))
                    .toList();
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new Exception("project not found");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());
        project.setCategory(updatedProject.getCategory());

        return projectRepository.save(project);
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }

    @Override
    public void addUserToProject(Long userId, Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (!project.getTeam().contains(user)) {
            project.getTeam().add(user);
            project.getChat().getUsers().add(user);
        }

        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long userId, Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
//        return chatRepository.findByProject(projectId);    // if the project doesnt include the chat when serialized (due to using @JsonIgnore annotation)

        Project project = getProjectById(projectId);
        return project.getChat();
    }
}
