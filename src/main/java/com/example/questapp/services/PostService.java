package com.example.questapp.services;

import com.example.questapp.entities.Post;
import com.example.questapp.entities.User;
import com.example.questapp.repository.PostRepository;
import com.example.questapp.requests.PostCreateRequest;
import com.example.questapp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public PostService(PostRepository postRepository,UserService userService) {
        this.postRepository = postRepository;
        this.userService=userService;
    }

    PostRepository postRepository;
    private UserService userService;

    public List<Post> getAllPost(Optional<Long> userId) {
        if(userId.isPresent()){
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPost) {
       User user =  userService.getOneUser(newPost.getUserId());
        if(user == null)
            return null;
        Post toSave = new Post();
        toSave.setId(newPost.getId());
        toSave.setText(newPost.getText());
        toSave.setTitle(newPost.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);

    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updateRequest) {
        Optional <Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setText(toUpdate.getText());
            toUpdate.setTitle(toUpdate.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
