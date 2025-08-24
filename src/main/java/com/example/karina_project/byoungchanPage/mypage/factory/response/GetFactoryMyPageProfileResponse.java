package com.example.karina_project.byoungchanPage.mypage.factory.response;

import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetFactoryMyPageProfileResponse {

        private String loginId;
        private String name;
        private String phoneNumber;
        private String mainAddress;
        private String detailAddress;
        private String postCode;

        public static GetFactoryMyPageProfileResponse from(User user) {

            return GetFactoryMyPageProfileResponse.builder()
                    .loginId(user.getLoginId())
                    .phoneNumber(user.getPhoneNumber())
                    .mainAddress(user.getMainAddress())
                    .detailAddress(user.getDetailAddress())
                    .name(user.getName())
                    .postCode(user.getPostCode())
                    .build();
        }

}
