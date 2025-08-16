package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetFisherMyPageResponse {

    private String loginId;
    private Long articleId;
    private String name;
    private String phoneNumber;
    private String fishSpecies;
    private String getDate;
    private String fishCount;

    public static GetFisherMyPageResponse from(Article article) {
        return GetFisherMyPageResponse.builder()
                .loginId(article.getUser() != null ? article.getUser().getLoginId() : null)
                .name(article.getUser() != null ? article.getUser().getName() : null)
                .phoneNumber(article.getUser() != null ? article.getUser().getPhoneNumber() : null)
                .articleId(article.getId())
                .fishSpecies(article.getFishSpecies())
                .getDate(article.getGetDate())
                .fishCount(article.getFishCount())
                .build();
    }
}
