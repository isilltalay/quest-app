package com.example.questapp.controller;

import com.example.questapp.entities.Comment;
import com.example.questapp.requests.CommentCreateOnePostRequest;
import com.example.questapp.requests.CommentUpdateRequest;
import com.example.questapp.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
        return commentService.getAllCommentWithParam(userId,postId);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping
    public Comment createOnePost(@RequestBody CommentCreateOnePostRequest request){
        return commentService.createOneComment(request);
    }
    @PutMapping("/{commentId}")
    public Comment  updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        return  commentService.updateOneCommentById(commentId,request);
    }
    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneCommentById(commentId);
    }

}
