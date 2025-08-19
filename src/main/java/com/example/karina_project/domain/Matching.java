package com.example.karina_project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "factory_id")
    private Long factoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;


    public static Matching from(Article article, Long factory_id) {
        return Matching.builder()
                .factoryId(factory_id)
                .article(article)
                .build();
    }
}
