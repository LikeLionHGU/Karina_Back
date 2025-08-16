package com.example.karina_project.sehyukPage.register_page.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String memberClassification;
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;

    public RegisterRequest(String memberClassification, String loginId, String password, String name, String phoneNumber, String mainAddress, String detailAddress) {
        this.memberClassification = memberClassification;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
    }
}
