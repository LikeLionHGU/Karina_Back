package com.example.karina_project.byoungchanPage.mypage.fisher.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutFisherMypageArticleRequest {

    private Long userId;
    private Long articleId;
    private String getDate;
    private String getTime;
    private String dateLimit;

}
