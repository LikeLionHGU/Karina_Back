package com.example.karina_project.byoungchanPage.mypage;


import com.example.karina_project.byoungchanPage.mypage.response.MypageUserInfoResponse;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public List<MypageUserInfoResponse> getUserArticles(Long userId) {
        return articleRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(MypageUserInfoResponse::from)
                .toList();
    }
}
