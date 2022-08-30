package com.example.reditClone.controller;

import com.example.reditClone.dto.PostRequest;
import com.example.reditClone.dto.PostResponse;
import com.example.reditClone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity crestePost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id){
        return postService.getPost(id);
    }
    @GetMapping()
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/by-subreddit/{id}")
    public List<PostResponse> getPostsBySubreddit(@PathVariable Long id){
        return postService.getPostsBySubreddit(id);
    }
    @GetMapping("/by-user/{name}")
    public List<PostResponse> getPostsByUsername(@PathVariable String username){
        return postService.getPostsByUsername(username);
    }
}
