package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetFisherMyPageResponse {

    private Long articleId;
    private String factoryName;
    private String phoneNumber;
    private Map<String, Integer> fishInfo;
    private String requestDate;
    private String status;

    public static GetFisherMyPageResponse from(Article article) {
        return GetFisherMyPageResponse.builder()
                .factoryName(article.getUser() != null ? article.getUser().getName() : null)
                .phoneNumber(article.getUser() != null ? article.getUser().getPhoneNumber() : null)
                .articleId(article.getId())
                .fishInfo(article.getFishInfo())
                .requestDate(article.getGetDate())
                .build();
    }

    public static GetFisherMyPageResponse from(Matching matching) {
        return GetFisherMyPageResponse.builder()
                .factoryName(matching.getFactory().getName())
                .phoneNumber(matching.getFactory().getPhoneNumber())
                .articleId(matching.getArticle().getId())
                .fishInfo(matching.getArticle().getFishInfo())
                .requestDate(matching.getRequestDate())
                .status(matching.getArticle().getStatus())
                .build();
    }

    public static List<GetFisherMyPageResponse> from(List<Matching> matchingList) {
        return matchingList.stream().map(GetFisherMyPageResponse::from).collect(Collectors.toList());
    }
}
