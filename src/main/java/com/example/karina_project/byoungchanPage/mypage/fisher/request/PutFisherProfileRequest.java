package com.example.karina_project.byoungchanPage.mypage.fisher.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutFisherProfileRequest {

    private String password;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
    private String postCode;
}
