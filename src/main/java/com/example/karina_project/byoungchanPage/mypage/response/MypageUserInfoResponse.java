package com.example.karina_project.byoungchanPage.mypage.response;


import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MypageUserInfoResponse {
    private Long id;
    private String fishSpecies;
    private String getDate;
    private String getTime;
    private String dateLimit;
    private String status;

    public static MypageUserInfoResponse from(Article entity) {

        return MypageUserInfoResponse.builder()
                .id(entity.getId())
                .fishSpecies(entity.getFishSpecies())
                .getDate(entity.getGetDate())
                .getTime(entity.getGetTime())
                .dateLimit(entity.getDateLimit())
                .status(entity.getStatus())
                .build();
    }
}
