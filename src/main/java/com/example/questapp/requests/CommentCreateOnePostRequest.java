package com.example.questapp.requests;

import lombok.Data;

@Data
public class CommentCreateOnePostRequest {
    Long id;
    Long userId;
    Long postId;
    String text;
}
