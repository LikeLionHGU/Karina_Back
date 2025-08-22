package com.example.karina_project.byoungchanPage.mypage.factory.response;


import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
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
    private Map<String, Integer> fishInfo;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String matchingStatus;

    public static GetFactoryMyPageResponse from(Matching matching){
        return GetFactoryMyPageResponse.builder()
                .fishInfo(matching.getArticle().getFishInfo())
                .getDate(matching.getArticle().getGetDate())
                .getTime(matching.getArticle().getGetTime())
                .dateLimit(matching.getArticle().getDateLimit())
                .matchingStatus(matching.getMatchingStatus())
                .build();
    }

}
