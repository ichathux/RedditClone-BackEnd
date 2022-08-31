package com.example.reditClone.service;

import com.example.reditClone.dto.VoteDto;
import com.example.reditClone.exception.SpringRedditException;
import com.example.reditClone.model.Post;
import com.example.reditClone.model.User;
import com.example.reditClone.model.Vote;
import com.example.reditClone.model.VoteType;
import com.example.reditClone.repository.PostRepository;
import com.example.reditClone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new SpringRedditException("Cannot find post for given id : "+ voteDto.getPostId()));
        User user = authService.getCurrentUser();

        Optional<Vote> vote = voteRepository.
                findTopByPostAndUserOrderByVoteIdDesc(post,user);
        if (vote.isPresent() && vote.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have alraedy "+voteDto.getVoteType()+"'s vote for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else {
            post.setVoteCount(post.getVoteCount() + -1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
