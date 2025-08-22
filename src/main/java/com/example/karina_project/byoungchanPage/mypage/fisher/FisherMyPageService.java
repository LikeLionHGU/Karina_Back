package com.example.karina_project.byoungchanPage.mypage.fisher;

import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMypageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageInfoResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageResponse;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.MatchingRepository;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FisherMyPageService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final HttpServletResponse httpServletResponse;


    @Transactional
    public List<GetFisherMyPageResponse> getFisherMypageServiece(Long userId) {

        List<Article> articles =  articleRepository.findByUserIdAndStatusNotOrderByIdDesc(userId, "매칭 완료");

        if (articles.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        List<Long> articleIds = articles.stream().map(Article::getId).collect(toList());
        List<Matching> matchingList = matchingRepository.findByArticleIdInAndMatchingStatus(articleIds, "매칭 대기");
        List<GetFisherMyPageResponse> response = matchingList.stream().map(GetFisherMyPageResponse::from).toList();

        return response;
    }

    @Transactional
    public String matchAccepting(Long articleId, Authentication authentication) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다.. id=" + articleId));
        article.setStatus("매칭 완료");

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        User factory = userRepository.findByLoginId(userDetail.getUsername());

        Matching acceptMatching = matchingRepository.findByArticleIdAndFactory(articleId, factory);
        acceptMatching.setMatchingStatus("매칭 성공");

        List<Matching> matchings = matchingRepository.findAllByArticleIdAndFactoryNot(articleId, factory);
        matchings.forEach(matching -> {matching.setMatchingStatus("매칭 실패");});

        return "success";
    }

    @Transactional
    public List<GetFisherMyPageArticleResponse> getFisherMypageArticleServiece(Long userId) {
        return articleRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(GetFisherMyPageArticleResponse::from)
                .toList();
    }

    @Transactional
    public String editFisherMyPageArticleService(PutFisherMypageArticleRequest request) {
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다.. id=" + request.getArticleId()));
        article.setGetDate(request.getGetDate());
        article.setGetTime(request.getGetTime());
        article.setDateLimit(request.getDateLimit());

        return "success";
    }

    public GetFisherMyPageInfoResponse getFisherMypageInfo(Long userId) {
        return userRepository.findById(userId)
                .map(GetFisherMyPageInfoResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("유저가 없습니다. id=" + userId));
    }

    @Transactional
    public boolean editFisherMyPageInfoService(PutFisherMyPageInfoRequest putFisherMyPageInfoRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + userId));

        user.setPassword(putFisherMyPageInfoRequest.getPassword());
        user.setPhoneNumber(putFisherMyPageInfoRequest.getPhoneNumber());
        user.setMainAddress(putFisherMyPageInfoRequest.getMainAddress());
        user.setDetailAddress(putFisherMyPageInfoRequest.getDetailAddress());

        userRepository.save(user);
        return true;
    }

}
