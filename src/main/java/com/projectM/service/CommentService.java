package com.projectM.service;

import com.projectM.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentByIssueId(Long issueId) throws Exception;

    Comment createComment(Long issueId, Long userId, String comment) throws Exception;

    void deleteComment(Long commentId, Long userId) throws Exception;
}
