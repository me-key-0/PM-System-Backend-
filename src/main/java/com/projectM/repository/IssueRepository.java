package com.projectM.repository;

import com.projectM.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> getByProjectId(Long projectId);

    List<Issue> getByAssigneeId(Long assigneeId);
}
