package com.projectM.service;

import com.projectM.model.Issue;
import com.projectM.model.User;
import com.projectM.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId, Long assigneeId) throws Exception;

    Issue addUserToIssue(Long issueId, Long assigneeId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;

}
