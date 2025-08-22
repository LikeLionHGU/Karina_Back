package com.example.karina_project.byoungchanPage.mypage.fisher.response;

import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetFisherMyPageInfoResponse {

    private String name;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;

    public static GetFisherMyPageInfoResponse from(User user) {
        return GetFisherMyPageInfoResponse.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .mainAddress(user.getMainAddress())
                .detailAddress(user.getDetailAddress())
                .build();
    }
}
