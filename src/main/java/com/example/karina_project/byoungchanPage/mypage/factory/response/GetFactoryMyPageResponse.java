package com.example.karina_project.byoungchanPage.mypage.factory.response;


import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Map;

@Getter
@Setter
@Builder
public class GetFactoryMyPageResponse {
    private String loginId;
    private Map<String, Integer> fishInfo;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String status;

    public static GetFactoryMyPageResponse from(Article article) {
        return GetFactoryMyPageResponse.builder()
                .loginId(article.getUser() != null ? article.getUser().getLoginId() : null)
                .fishInfo(article.getFishInfo())
                .getDate(article.getGetDate())
                .getTime(article.getGetTime())
                .dateLimit(article.getDateLimit())
                .status(article.getStatus())
                .build();
    }

}
