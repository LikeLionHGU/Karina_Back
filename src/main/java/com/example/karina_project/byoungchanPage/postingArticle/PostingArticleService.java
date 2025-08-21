package com.example.karina_project.byoungchanPage.postingArticle;

import com.example.karina_project.byoungchanPage.postingArticle.request.CreateArticleInfoRequest;
import com.example.karina_project.byoungchanPage.postingArticle.request.EditFishInfoRequest;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostingArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public String registerProcess(Long userId, String s3Url) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found: " + userId));
        user.setAuthenticationFile(s3Url);
        return "ok";
    }

    @Transactional
    public String editFishInfo(Long articleId, EditFishInfoRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("article not found: " + articleId));

        article.setFishSpecies(request.getFishSpecies());
        article.setFishCount(request.getFishCounts());
        articleRepository.save(article);

        return "success";
    }

    @Transactional
    public void postArticleInfo(Long userId, CreateArticleInfoRequest request, String s3Url) {
        User userRef = userRepository.getReferenceById(userId);
        Article article = new Article();
        article.setUser(userRef);

        article.setGetDate(request.getGetDate());
        article.setGetTime(request.getGetTime());
        article.setDateLimit(request.getDateLimit());
        article.setThumbnail(s3Url);

        articleRepository.save(article);
    }
}
