package com.example.karina_project.byoungchanPage.postingArticle;

import com.example.karina_project.byoungchanPage.postingArticle.response.VideoResultResponse;
import com.example.karina_project.byoungchanPage.postingArticle.request.CreateArticleInfoRequest;
import com.example.karina_project.byoungchanPage.postingArticle.request.EditFishInfoRequest;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostingArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final WebClient webClient;

    public Mono<Map<String, Integer>> videoAnalysis(String s3Url) {
        Map<String, String> request = Map.of("s3_url", s3Url);

        return webClient.post()
                .uri("/analyze_video")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(VideoResultResponse.class)
                .map(response -> {
                    Map<String, Integer> fullResults = response.analysisResult();

                    return fullResults.entrySet().stream()
                            .limit(3)
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (e1, e2) -> e1,
                                    LinkedHashMap::new
                            ));
                });
    }

    @Transactional
    public VideoResultResponse makeArticle(Long userId, String s3Url) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found: " + userId));

        Map<String, Integer> videoResult = videoAnalysis(s3Url).block();

        if (videoResult == null) {
            throw new IllegalStateException("Video analysis returned no result.");
        }

        Article article = new Article();
        article.setUser(user);
        article.setVideo(s3Url);
        article.setFishInfo(videoResult);

        articleRepository.save(article);

        VideoResultResponse videoResultResponse = new VideoResultResponse(article.getId(), videoResult);

        return videoResultResponse;
    }

    @Transactional
    public VideoResultResponse reanalyzeFishInfo(EditFishInfoRequest request) {
        Article article = articleRepository.findById(request.getArticle_id())
                .orElseThrow(() -> new IllegalArgumentException("article not found: " + request.getArticle_id()));

        String video = article.getVideo();

        Map<String, Integer> analysisResult = videoAnalysis(video).block();

        article.setFishInfo(analysisResult);

        VideoResultResponse videoResultResponse = new VideoResultResponse(article.getId(), analysisResult);

        return videoResultResponse;
    }

    @Transactional
    public void postArticleInfo(CreateArticleInfoRequest request, String s3Url) {
        Article article = articleRepository.findById(request.getArticle_id())
                .orElseThrow(() -> new IllegalArgumentException("article not found: " + request.getArticle_id()));

        article.setGetDate(request.getGetDate());
        article.setGetTime(request.getGetTime());
        article.setDateLimit(request.getDateLimit());
        article.setThumbnail(s3Url);
    }
}
