package com.example.karina_project.sehyukPage.register_page.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    private String role;
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
    private String postCode;
}
