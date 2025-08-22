package com.example.karina_project.byoungchanPage.mypage.factory.dto;

import com.example.karina_project.domain.Matching;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class GetFactoryMyPageDto {

    private Map<String, Integer> fishInfo;
    private String getDate;
    private String getTime;
    private String limitDate;
    private String limitTime;
    private String matchingStatus;

    public static GetFactoryMyPageDto from(Matching matching){
        return GetFactoryMyPageDto.builder()
                .fishInfo(matching.getArticle().getFishInfo())
                .getDate(matching.getArticle().getGetDate())
                .getTime(matching.getArticle().getGetTime())
                .limitDate(matching.getArticle().getLimitDate())
                .limitTime(matching.getArticle().getLimitTime())
                .matchingStatus(matching.getMatchingStatus())
                .build();
    }
}
