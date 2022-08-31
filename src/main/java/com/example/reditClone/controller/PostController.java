package com.example.reditClone.controller;

import com.example.reditClone.dto.PostRequest;
import com.example.reditClone.dto.PostResponse;
import com.example.reditClone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.ResponseEntity.status;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }
    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }
    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username){
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
