package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.byoungchanPage.mypage.fisher.dto.GetFisherMyPageArticleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetFisherMyPageArticleResponse {

    private List<GetFisherMyPageArticleDto> completeArticle;
    private List<GetFisherMyPageArticleDto> incompleteArticle;

}
