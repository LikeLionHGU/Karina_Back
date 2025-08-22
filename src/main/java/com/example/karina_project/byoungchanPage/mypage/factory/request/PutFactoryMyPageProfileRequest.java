package com.example.karina_project.byoungchanPage.mypage.factory.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PutFactoryMyPageProfileRequest {
    private String name;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
}
