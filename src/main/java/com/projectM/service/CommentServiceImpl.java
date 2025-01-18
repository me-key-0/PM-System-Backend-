package com.projectM.service;

import com.projectM.model.Comment;
import com.projectM.model.Issue;
import com.projectM.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Override
    public List<Comment> getCommentByIssueId(Long issueId) throws Exception {

        return commentRepository.findByIssueId(issueId);
    }

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception {
        Issue issue = issueService.getIssueById(issueId);
        Comment newComment = new Comment();

        newComment.setUser(userService.findUserById(userId));
        newComment.setContent(comment);
        newComment.setIssue(issue);
        newComment.setCreateDateTime(LocalDate.now());

        commentRepository.save(newComment);

        issue.getComments().add(newComment);

        return newComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()){
            throw new Exception("comment not found");
        }
        if(comment.get().getUser() != userService.findUserById(userId)) {
            throw new Exception("user doesn't have permission to delete this comment");
        }
        commentRepository.deleteById(commentId);
    }
}
