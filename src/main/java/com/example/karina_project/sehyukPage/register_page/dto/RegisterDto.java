package com.example.karina_project.sehyukPage.register_page.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String memberClassification;
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
    private String fileUrl;

}
