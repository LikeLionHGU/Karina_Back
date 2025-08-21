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
    private String factoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "request_date")
    private String requestDate;

    public static Matching from(Article article, String factoryId, String requestDate) {
        return Matching.builder()
                .factoryId(factoryId)
                .article(article)
                .requestDate(requestDate)
                .build();
    }
}
