package com.example.karina_project.byoungchanPage.mypage.fisher.dto;

import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class GetFisherMyPageArticleDto {

    private Long articleId;
    private Map<String, Integer> fishInfo;
    private String getDate;
    private String getTime;
    private String limitDate;
    private String limitTime;
    private String status;

    public static GetFisherMyPageArticleDto from(Article article) {
        return GetFisherMyPageArticleDto.builder()
                .articleId(article.getId())
                .fishInfo(article.getFishInfo())
                .getDate(article.getGetDate())
                .getTime(article.getGetTime())
                .limitDate(article.getLimitDate())
                .limitTime(article.getLimitTime())
                .status(article.getStatus())
                .build();
    }
}
