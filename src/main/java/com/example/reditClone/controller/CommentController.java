package com.example.reditClone.controller;

import com.example.reditClone.dto.CommentsDto;
import com.example.reditClone.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> createComment(@RequestBody CommentsDto dto){
        commentService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-postId/{postId}")
    public ResponseEntity<List<CommentsDto>> commentByPostId(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));

    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<Object> getCommentsByUserName(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsByUsername(username));
    }
}
