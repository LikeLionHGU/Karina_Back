package com.example.karina_project.byoungchanPage.mypage.factory.response;

import com.example.karina_project.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetFactoryMyPageProfileResponse {
        private String name;
        private String phoneNumber;
        private String mainAddress;
        private String detailAddress;

        public static GetFactoryMyPageProfileResponse from(User entity) {

            return GetFactoryMyPageProfileResponse.builder()
                    .phoneNumber(entity.getPhoneNumber())
                    .mainAddress(entity.getMainAddress())
                    .detailAddress(entity.getDetailAddress())
                    .name(entity.getName())
                    .build();
        }

}
