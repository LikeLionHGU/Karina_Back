package com.example.karina_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "article",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Matching> matchings = new ArrayList<>();

    @Column(name = "fish_species")
    private String fishSpecies;

    @Column(name = "fish_count")
    private String fishCount;

    @Column(name = "get_date")
    private String getDate;

    @Column(name = "get_time")
    private String getTime;

    @Column(name = "date_limit")
    private String dateLimit;

    @Column(name = "post_time")
    private String postTime;

    @Column(name = "status")
    private String status;

    @Column(name = "thumbnail")
    private String thumbnail;
}