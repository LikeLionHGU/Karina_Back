package com.example.karina_project.sehyukPage.home_page.response;

import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class HomePageResponse {

    private Long articleId;
    private String fisherName;
    private String mainAddress;
    private String detailAddress;
    private Map<String, Integer> fishInfo;
    private String postDate;
    private String status;
    private String thumbnail;

    public static HomePageResponse from(ArticleDto articleDto) {
        return HomePageResponse.builder()
                .articleId(articleDto.getArticleId())
                .fisherName(articleDto.getFisherName())
                .mainAddress(articleDto.getMainAddress())
                .detailAddress(articleDto.getDetailAddress())
                .fishInfo(articleDto.getFishInfo())
                .postDate(articleDto.getPostDate())
                .status(articleDto.getStatus())
                .thumbnail(articleDto.getThumbnail())
                .build();
    }

    public static List<HomePageResponse> from(List<ArticleDto> articleDtos) {
        return articleDtos.stream().map(HomePageResponse::from).collect(Collectors.toList());
    }
}
