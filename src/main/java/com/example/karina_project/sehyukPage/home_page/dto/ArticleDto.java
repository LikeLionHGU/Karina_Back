package com.example.karina_project.sehyukPage.home_page.dto;

import com.example.karina_project.domain.Article;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long articleId;
    private Long userId;
    private String fisherName;
    private String mainAddress;
    private String detailAddress;
    private Map<String, Integer> fishInfo;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String postTime;
    private String status;
    private String thumbnail;

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
                .articleId(article.getId())
                .fisherName(article.getUser().getName())
                .fishInfo(article.getFishInfo())
                .postTime(article.getPostTime())
                .status(article.getStatus())
                .thumbnail(article.getThumbnail())
                .build();
    }

    public static List<ArticleDto> from(List<Article> articles) {
        return articles.stream().map(ArticleDto::from).collect(Collectors.toList());
    }
}
