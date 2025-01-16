package com.projectM.repository;

import com.projectM.model.Comment;
import com.projectM.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByIssueId(Long issueId);

//    List<Comment> findCommentByIssueId(Issue issue);

}
