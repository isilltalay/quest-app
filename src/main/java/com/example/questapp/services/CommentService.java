package com.example.questapp.services;

import com.example.questapp.entities.Comment;
import com.example.questapp.entities.Post;
import com.example.questapp.entities.User;
import com.example.questapp.repository.CommentRepository;
import com.example.questapp.requests.CommentCreateOnePostRequest;
import com.example.questapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllCommentWithParam(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            return commentRepository.findByPostId(postId.get());
        }
        else{
            return commentRepository.findAll();
        }
    }

    public Comment getOneCommentById(Long commentId) {
       return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateOnePostRequest request) {
        User user= userService.getOneUser(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if(user != null && post != null){
            Comment commentToSave = new Comment();
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            return commentRepository.save(commentToSave);
        } else
        return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment toUpdate = comment.get();
            toUpdate.setText(request.getText());
            commentRepository.save(toUpdate);
        }
        return null;
    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
