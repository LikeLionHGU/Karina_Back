package com.example.karina_project.byoungchanPage.postingArticle.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CreateFishInfoRequest {
    private String getDate;
    private String getTime;
    private String dateLimit;

}
