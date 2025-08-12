package com.example.karina_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Fisher {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false)
    private String id;

    @Column(name = "secrete_key", nullable = false)
    private String secreteKey;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "fisher")
    private List<Article> articles = new ArrayList<>();
}
