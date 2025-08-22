package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class GetFisherMyPageResponse {

    private Long articleId;
    private String factoryName;
    private String phoneNumber;
    private Map<String, Integer> fishInfo;
    private String requestDate;

    public static GetFisherMyPageResponse from(Article article) {
        return GetFisherMyPageResponse.builder()
                .factoryName(article.getUser() != null ? article.getUser().getName() : null)
                .phoneNumber(article.getUser() != null ? article.getUser().getPhoneNumber() : null)
                .articleId(article.getId())
                .fishInfo(article.getFishInfo())
                .requestDate(article.getGetDate())
                .build();
    }
}
