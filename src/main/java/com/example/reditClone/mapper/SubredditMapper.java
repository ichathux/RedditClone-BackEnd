package com.example.reditClone.mapper;

import com.example.reditClone.dto.SubredditDto;
import com.example.reditClone.model.Post;
import com.example.reditClone.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subReddit.getPosts()))")
    SubredditDto mapSubredditDto(SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts){return numberOfPosts.size();}

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDtoToSubreddit(SubredditDto subredditDto);
}
