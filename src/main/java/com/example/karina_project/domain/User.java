package com.example.karina_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;                         // PK

        @Column(name = "memberClassification")
        private String memberClassification;     // 회원구분

        @Column(name = "authenticationFile")
        private String authenticationFile;       // 회원인증

        @Column(name = "loginId")               // 로그인 아이디(컬럼명 가정)
        private String loginId;

        @Column(name = "password")
        private String password;                 // 비밀번호

        @Column(name = "name")
        private String name;                     // 이름

        @Column(name = "phoneNumber")
        private String phoneNumber;              // 전화번호

        @Column(name = "mainAddress")
        private String mainAddress;              // 메인주소

        @Column(name = "detailAddress")
        private String detailAddress;            // 상세주소
    }



