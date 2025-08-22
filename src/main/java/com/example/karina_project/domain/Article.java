package com.example.karina_project.domain;

import com.example.karina_project.byoungchanPage.postingArticle.converter.FishInfoConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Convert(converter = FishInfoConverter.class)
    @Column(name = "fish_info")
    private Map<String, Integer> fishInfo;

    @Column(name = "get_date")
    private String getDate;

    @Column(name = "get_time")
    private String getTime;

    @Column(name = "limit_date")
    private String limitDate;

    @Column(name = "limit_time")
    private String limitTime;

    @Column(name = "post_time")
    private String postTime;

    @Column(name = "status")
    private String status;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "video")
    private String video;
}