package com.example.karina_project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fisher_num", nullable = false)
    private Fisher fisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factory_num", nullable = false)
    private Factory factory;

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

    @Column(name = "status")
    private String status;
}
