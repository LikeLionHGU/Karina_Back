package com.example.karina_project.byoungchanPage.mypage.factory.response;

import com.example.karina_project.byoungchanPage.mypage.factory.dto.GetFactoryMyPageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetFactoryMyPageResponse {

    private List<GetFactoryMyPageDto> matchingSuccessList;
    private List<GetFactoryMyPageDto> matchingNotSuccessList;


}
