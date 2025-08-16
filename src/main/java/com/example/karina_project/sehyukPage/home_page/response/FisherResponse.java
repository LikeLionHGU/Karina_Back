package com.example.karina_project.sehyukPage.home_page.response;

import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class FisherResponse {

    private Long article_id;
    private Long user_id;
    private String fisher_name;
    private String fish_species;
    private String status;
//    private String image;

    public static FisherResponse from(ArticleDto articleDto) {
        return FisherResponse.builder()
                .article_id(articleDto.getArticle_id())
                .user_id(articleDto.getUser_id())
                .fisher_name(articleDto.getFisher_name())
                .fish_species(articleDto.getFish_species())
                .status(articleDto.getStatus())
                .build();
    }

    public static List<FisherResponse> from(List<ArticleDto> articleDtos) {
        return articleDtos.stream().map(FisherResponse::from).collect(Collectors.toList());
    }
}
