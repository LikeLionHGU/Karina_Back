package com.example.karina_project.sehyukPage.detail_page.response;

import com.example.karina_project.sehyukPage.detail_page.dto.DetailPageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class DetailPageResponse {

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

    public static DetailPageResponse from(DetailPageDto detailPageDto) {
        return DetailPageResponse.builder()
                .articleId(detailPageDto.getArticleId())
                .fisherName(detailPageDto.getFisherName())
                .phoneNumber(detailPageDto.getPhoneNumber())
                .mainAddress(detailPageDto.getMainAddress())
                .detailAddress(detailPageDto.getDetailAddress())
                .fishInfo(detailPageDto.getFishInfo())
                .getDate(detailPageDto.getGetDate())
                .getTime(detailPageDto.getGetTime())
                .dateLimit(detailPageDto.getDateLimit())
                .status(detailPageDto.getStatus())
                .video(detailPageDto.getVideo())
                .build();
    }

    public static List<DetailPageResponse> from(List<DetailPageDto> detailPageDtos) {
        return detailPageDtos.stream().map(DetailPageResponse::from).collect(Collectors.toList());
    }

}
