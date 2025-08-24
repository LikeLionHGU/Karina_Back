package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetFisherMyPageProfileResponse {

    private String loginId;
    private String name;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
    private String postCode;

    public static GetFisherMyPageProfileResponse from(User user) {
        return GetFisherMyPageProfileResponse.builder()
                .loginId(user.getLoginId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .mainAddress(user.getMainAddress())
                .detailAddress(user.getDetailAddress())
                .postCode(user.getPostCode())
                .build();
    }
}
