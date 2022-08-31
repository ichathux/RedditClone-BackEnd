package com.example.reditClone.repository;

import com.example.reditClone.model.Post;
import com.example.reditClone.model.SubReddit;
import com.example.reditClone.model.User;
import com.example.reditClone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubReddit(SubReddit subReddit);
    List<Post> findAllByUser(User user);


}
