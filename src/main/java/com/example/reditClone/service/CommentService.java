package com.example.reditClone.service;

import com.example.reditClone.dto.CommentsDto;
import com.example.reditClone.exception.SpringRedditException;
import com.example.reditClone.mapper.CommentMapper;
import com.example.reditClone.model.Comment;
import com.example.reditClone.model.NotificationEmail;
import com.example.reditClone.model.Post;
import com.example.reditClone.model.User;
import com.example.reditClone.repository.CommentRepository;
import com.example.reditClone.repository.PostRepository;
import com.example.reditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final MailService mailService;

    public Comment save(CommentsDto dto){
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new SpringRedditException("Can't find post: "+dto.getPostId()));

        mailService.sendEmail(new NotificationEmail("New Comment on your post",
                post.getUser().getEmail(),"User "+dto.getUsername()+" added comment to your post "+post.getPostName()+"."));

        return commentRepository.save(commentMapper.map(dto,post,authService.getCurrentUser()));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new SpringRedditException("Cannot find relevant post for id : "+postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("Cannot find relevant user for given username : "+username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
