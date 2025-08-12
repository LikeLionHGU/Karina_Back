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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    long userId;
    String photo;
    String name;
    String instaUrl;
    String kakaoUrl;
    String youtubeUrl;
    String notionUrl;
    String url;
    int mandatorySemesters;

    @Lob
    @Column(columnDefinition = "TEXT")
    String content;

    String category;

    @OneToMany(mappedBy = "club")
    private List<ShowTable> shows = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Entertain> entertains = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Recruiting> recruitings = new ArrayList<>();
}
