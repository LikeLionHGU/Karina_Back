package com.example.karina_project.byoungchanPage.mypage.fisher.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutFisherMyPageInfoRequest {
    private String loginId; //없어도 될 듯
    private String password;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
}
