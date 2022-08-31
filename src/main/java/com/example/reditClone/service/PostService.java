package com.example.reditClone.service;

import com.example.reditClone.dto.PostRequest;
import com.example.reditClone.dto.PostResponse;
import com.example.reditClone.exception.SpringRedditException;
import com.example.reditClone.mapper.PostMapper;
import com.example.reditClone.model.Post;
import com.example.reditClone.model.SubReddit;
import com.example.reditClone.model.User;
import com.example.reditClone.repository.PostRepository;
import com.example.reditClone.repository.SubRedditRepository;
import com.example.reditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final SubRedditRepository subRedditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post save(PostRequest postRequest) {

        SubReddit subReddit = subRedditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException("Subreddit not found"));
        User currentUser = authService.getCurrentUser();
        return postRepository.save(postMapper.map(postRequest,subReddit, currentUser));
    }
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No posts found by given id"));
        return postMapper.mapToDto(post);
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        List<PostResponse> posts = postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
        return posts;
    }

    public List<PostResponse> getPostsBySubreddit(Long id) {
        SubReddit subReddit = subRedditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit fot given ID"));
        return postRepository.findAllBySubReddit(subReddit).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("Can't find user by given username"));
        return postRepository.findAllByUser(user).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
