package com.example.reditClone.mapper;

import com.example.reditClone.dto.PostRequest;
import com.example.reditClone.dto.PostResponse;
import com.example.reditClone.model.Post;
import com.example.reditClone.model.SubReddit;
import com.example.reditClone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subReddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, SubReddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
//    @Mapping(target = "postName", source = "postName")
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "url", source = "url")
    @Mapping(target = "subredditName", source = "subReddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);

}
