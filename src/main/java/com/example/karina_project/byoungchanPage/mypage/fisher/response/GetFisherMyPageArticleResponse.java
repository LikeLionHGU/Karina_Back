package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.byoungchanPage.mypage.fisher.dto.GetFisherMyPageArticleDto;
import com.example.karina_project.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class GetFisherMyPageArticleResponse {

    private List<GetFisherMyPageArticleDto> completeArticle;
    private List<GetFisherMyPageArticleDto> incompleteArticle;

}
