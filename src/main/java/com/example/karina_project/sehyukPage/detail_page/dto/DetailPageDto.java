package com.example.karina_project.sehyukPage.detail_page.dto;

import com.example.karina_project.domain.Article;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailPageDto {

    private Long article_id;
    private Long user_id;
    private String fisher_name;
    private String phone_number;
    private String main_address;
    private String detail_address;
    private String fish_species;
    private String fish_count;
    private String get_date;
    private String get_time;
    private String date_limit;
    private String status;

    public static DetailPageDto from(Article article) {
        return DetailPageDto.builder()
                .article_id(article.getId())
                .user_id(article.getUser().getId())
                .fisher_name(article.getUser().getName())
                .phone_number(article.getUser().getPhoneNumber())
                .main_address(article.getUser().getMainAddress())
                .detail_address(article.getUser().getDetailAddress())
                .fish_species(article.getFishSpecies())
                .fish_count(article.getFishCount())
                .get_date(article.getGetDate())
                .get_time(article.getGetTime())
                .date_limit(article.getDateLimit())
                .status(article.getStatus())
                .build();
    }
}
