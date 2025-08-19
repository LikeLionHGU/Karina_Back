package com.example.karina_project.sehyukPage.detail_page.response;

import com.example.karina_project.sehyukPage.detail_page.dto.DetailPageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class DetailPageResponse {

    private Long article_id;
    private String fisher_name;
    private String phone_number;
    private String address;
    private String fish_species;
    private String fish_count;
    private String get_date;
    private String get_time;
    private String date_limit;
    private String status;

    public static DetailPageResponse from(DetailPageDto detailPageDto) {
        return DetailPageResponse.builder()
                .article_id(detailPageDto.getArticle_id())
                .fisher_name(detailPageDto.getFisher_name())
                .phone_number(detailPageDto.getPhone_number())
                .address(detailPageDto.getDetail_address())
                .fish_species(detailPageDto.getFish_species())
                .fish_count(detailPageDto.getFish_count())
                .get_date(detailPageDto.getGet_date())
                .get_time(detailPageDto.getGet_time())
                .date_limit(detailPageDto.getDate_limit())
                .status(detailPageDto.getStatus())
                .build();
    }

    public static List<DetailPageResponse> from(List<DetailPageDto> detailPageDtos) {
        return detailPageDtos.stream().map(DetailPageResponse::from).collect(Collectors.toList());
    }

}
