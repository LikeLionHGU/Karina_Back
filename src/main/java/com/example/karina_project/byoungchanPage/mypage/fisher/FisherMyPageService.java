package com.example.karina_project.byoungchanPage.mypage.fisher;


import com.example.karina_project.byoungchanPage.mypage.fisher.dto.GetFisherMyPageArticleDto;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
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
import com.example.karina_project.sehyukPage.register_page.service.FileService;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FisherMyPageService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final HttpServletResponse httpServletResponse;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        var principal = (CustomUserDetail) authentication.getPrincipal();

        User me = userRepository.findByLoginId(principal.getUsername());
        if (me == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자 없음");

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글 없음: " + articleId));

        // [권한검증] 이 글의 소유자가 나인지(또는 내가 수락 권한이 있는지)
        if (!article.getUser().getId().equals(me.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 글에 대한 수락 권한이 없습니다.");
        }

        // ---- 여기가 핵심 분기 ----
        // (A) Fisher가 수락한다면: 현재 유저가 Fisher 기준으로 조회
        // Optional<Matching> opt = matchingRepository.findByArticleIdAndFisher(articleId, me);

        // (B) 특정 Factory를 수락한다면: 요청 바디에 factoryId(또는 matchingId)를 받자
        // 그리고 그 대상을 조회
        // Optional<Matching> opt = matchingRepository.findByArticleIdAndFactory(articleId, factoryUser);

        Matching accept = matchingRepository
                .findByArticleIdAndFactory(articleId, me) // <= 현재 구조에 맞게 ‘정말’ 맞는 메서드로 바꾸세요
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 조합의 매칭이 없습니다."));

        accept.setMatchingStatus("매칭 성공");

        // 나머지는 일괄 마감 (벌크 업데이트 권장)
        matchingRepository.closeOthers(articleId, accept.getId());

        article.setStatus("매칭 완료");
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
        article.setLimitDate(request.getDateLimit());
        article.setLimitTime(request.getTimeLimit());
        article.setThumbnail((thumbnailUrl));

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

        String encodedPassword = bCryptPasswordEncoder.encode(putFisherMyPageInfoRequest.getPassword());

        user.setPassword(encodedPassword);
        user.setPhoneNumber(putFisherMyPageInfoRequest.getPhoneNumber());
        user.setMainAddress(putFisherMyPageInfoRequest.getMainAddress());
        user.setDetailAddress(putFisherMyPageInfoRequest.getDetailAddress());

        userRepository.save(user);
        return true;
    }

}
