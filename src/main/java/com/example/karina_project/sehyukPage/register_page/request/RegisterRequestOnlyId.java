package com.example.karina_project.sehyukPage.register_page.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestOnlyId {
    private String loginId;

    public RegisterRequestOnlyId(String loginId) {
        this.loginId = loginId;
    }
}
