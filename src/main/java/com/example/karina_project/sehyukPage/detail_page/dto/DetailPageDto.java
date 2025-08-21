package com.example.karina_project.sehyukPage.detail_page.dto;

import com.example.karina_project.domain.Article;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailPageDto {

    private Long articleId;
    private String fisherName;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
    private Map<String, Integer> fishInfo;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String status;
    private String video;

    public static DetailPageDto from(Article article) {
        return DetailPageDto.builder()
                .articleId(article.getId())
                .fisherName(article.getUser().getName())
                .phoneNumber(article.getUser().getPhoneNumber())
                .mainAddress(article.getUser().getMainAddress())
                .detailAddress(article.getUser().getDetailAddress())
                .fishInfo(article.getFishInfo())
                .getDate(article.getGetDate())
                .getTime(article.getGetTime())
                .dateLimit(article.getDateLimit())
                .status(article.getStatus())
                .video(article.getVideo())
                .build();
    }
}
