package com.projectM.service;

import com.projectM.model.Issue;
import com.projectM.model.Project;
import com.projectM.model.User;
import com.projectM.repository.IssueRepository;
import com.projectM.request.IssueRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.isEmpty()) {
            throw new Exception("issue not found");
        }
        return issue.get();
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.getByProjectId(projectId);
    }

    @Override                   /////////////////////////////////////////////
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Project project = projectService.getProjectById(issue.getProjectId());

        Issue newIssue = new Issue();

        newIssue.setTitle(issue.getTitle());
        newIssue.setDescription(issue.getDescription());
        newIssue.setStatus(issue.getStatus());
        newIssue.setPriority(issue.getPriority());
        newIssue.setProjectId(issue.getProjectId());
        newIssue.setDueDate(issue.getDueDate());

        newIssue.setProject(project);

//        newIssue.setAssignee(user);
        issueRepository.save(newIssue);

//        project.getIssues().add(newIssue);
        return newIssue;
    }

    @Override
    public void deleteIssue(Long issueId, Long assigneeId) throws Exception {
        getIssueById(issueId);  // will throw an error if issue doesn't exist,
                                // assignee id not used yet,

        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long assigneeId) throws Exception {
        Issue issue = getIssueById(issueId);
        User assignee = userService.findUserById(assigneeId);
        issue.setAssignee(assignee);

        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);

        return issueRepository.save(issue);
    }


}
