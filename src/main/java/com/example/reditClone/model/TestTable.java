package com.example.reditClone.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestTable {
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
    @NotNull
    private Integer voteCount = 0;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createDate;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private SubReddit subReddit;
}
