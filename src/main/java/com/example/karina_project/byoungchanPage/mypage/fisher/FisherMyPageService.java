package com.example.karina_project.byoungchanPage.mypage.fisher;


import com.example.karina_project.byoungchanPage.mypage.fisher.dto.GetFisherMyPageArticleDto;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageInfoResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageResponse;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import com.example.karina_project.sehyukPage.register_page.service.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FisherMyPageService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final FileService fileService;

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
        matchings.forEach(matching -> {matching.setMatchingStatus("매칭 마감");});

        return "success";
    }

    public List<GetFisherMyPageArticleDto> getArticleThatIncomplete(Long userId) {
        List<GetFisherMyPageArticleDto> incompleteArticleList = articleRepository.findByUserIdAndStatusNotOrderByIdDesc(userId, "매칭 완료").stream().map(GetFisherMyPageArticleDto::from).collect(toList());

        return incompleteArticleList;
    }

    public List<GetFisherMyPageArticleDto> getArticleThatComplete(Long userId) {
        List<GetFisherMyPageArticleDto> completeArticleList = articleRepository.findByUserIdAndStatusOrderByIdDesc(userId, "매칭 완료").stream().map(GetFisherMyPageArticleDto::from).collect(toList());

        return completeArticleList;
    }

    public GetFisherMyPageArticleResponse getFisherMyPageArticleService(Long userId) {

        return GetFisherMyPageArticleResponse.builder()
                .completeArticle(getArticleThatComplete(userId))
                .incompleteArticle(getArticleThatIncomplete(userId))
                .build();
    }

    @Transactional
    public String editFisherMyPageArticleService(PutFisherMyPageArticleRequest request, String thumbnailUrl) throws IOException {
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다.. id=" + request.getArticleId()));
        fileService.deleteFile(article.getThumbnail());

        article.setGetDate(request.getGetDate());
        article.setGetTime(request.getGetTime());
        article.setLimitDate(request.getLimitDate());
        article.setLimitTime(request.getLimitTime());
        article.setThumbnail(thumbnailUrl);

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
