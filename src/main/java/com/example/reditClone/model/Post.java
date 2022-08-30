package com.example.reditClone.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data //generate getters and setters
@Entity
@Builder //generate builder method for class
@AllArgsConstructor //generate constructors for class
@NoArgsConstructor //generate constructors for class
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @NotBlank(message = "Post name must be required")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createDate;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private SubReddit subReddit;
}
