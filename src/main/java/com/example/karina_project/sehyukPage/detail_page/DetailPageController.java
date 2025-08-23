package com.example.karina_project.sehyukPage.detail_page;

import com.example.karina_project.sehyukPage.detail_page.request.DetailPageRequest;
import com.example.karina_project.sehyukPage.detail_page.response.DetailPageResponse;
import com.example.karina_project.sehyukPage.detail_page.service.DetailPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/factory")
public class DetailPageController {

    private final DetailPageService detailPageService;

    @GetMapping("/detail/{articleId}")
    public ResponseEntity<DetailPageResponse> getDetailPage(@PathVariable Long articleId) {
        DetailPageResponse detailPageResponse = DetailPageResponse.from(detailPageService.getDetailArticleInfo(articleId));

        return ResponseEntity.ok().body(detailPageResponse);
    }

    @PostMapping("/detail")
    public ResponseEntity<String> postMatchingRequest(@RequestBody DetailPageRequest request, Authentication authentication) {
        String message = detailPageService.sendMatchingRequest(request, authentication);

        return ResponseEntity.ok().body(message);
    }

}
