package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetFisherMyPageArticleResponse {
    private String loginId;
    private Long articleId;
    private String fishSpecies;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String status;

    public static GetFisherMyPageArticleResponse from(Article article) {
        return GetFisherMyPageArticleResponse.builder()
                .loginId(article.getUser() != null ? article.getUser().getLoginId() : null)
                .articleId(article.getId())
                .fishSpecies(article.getFishSpecies())
                .getDate(article.getGetDate())
                .getTime(article.getGetTime())
                .dateLimit(article.getGetDate())
                .status(article.getStatus())
                .build();
    }
}
