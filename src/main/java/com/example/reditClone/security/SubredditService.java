package com.example.reditClone.security;

import com.example.reditClone.dto.SubredditDto;
import com.example.reditClone.model.SubReddit;
import com.example.reditClone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubRedditRepository subRedditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        SubReddit save = subRedditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }
    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subRedditRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SubredditDto mapToDto(SubReddit subReddit) {
        return SubredditDto.builder().name(subReddit.getName())
                .id(subReddit.getId())
                .numberOfPosts(subReddit.getPosts().size())
                .build();
    }

    private SubReddit mapSubredditDto(SubredditDto subredditDto) {
        return SubReddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }


}
