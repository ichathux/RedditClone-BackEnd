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
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubReddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name must be required")
    private String name;
    @NotBlank(message = "description must be required")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createdDAte;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
