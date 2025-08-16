package com.example.karina_project.sehyukPage.home_page.dto;

import com.example.karina_project.domain.Article;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long article_id;
    private Long user_id;
    private String fisher_name;
    private String fish_species;
    private String fishCount;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String postTime;
    private String status;
//    private String thumbnail;

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
                .article_id(article.getId())
                .user_id(article.getUser().getId())
                .fisher_name(article.getUser().getName())
                .fish_species(article.getFishSpecies())
                .postTime(article.getPostTime())
                .status(article.getStatus())
                .build();
    }

    public static List<ArticleDto> from(List<Article> articles) {
        return articles.stream().map(ArticleDto::from).collect(Collectors.toList());
    }
}
