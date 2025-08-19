package com.example.karina_project.sehyukPage.detail_page;

import com.example.karina_project.sehyukPage.detail_page.request.DetailPageRequest;
import com.example.karina_project.sehyukPage.detail_page.response.DetailPageResponse;
import com.example.karina_project.sehyukPage.detail_page.service.DetailPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/factory")
public class DetailPageController {

    private final DetailPageService detailPageService;

    @GetMapping("/detail/{article_id}")
    public ResponseEntity<DetailPageResponse> getDetailPage(@PathVariable Long article_id) {
        DetailPageResponse detailPageResponse = DetailPageResponse.from(detailPageService.getDetailArticleInfo(article_id));

        return ResponseEntity.ok().body(detailPageResponse);
    }

    @PostMapping("/detail")
    public ResponseEntity<String> postMatchingRequest(@RequestBody DetailPageRequest request) {
        String message = detailPageService.sendMatchingRequest(request);

        return ResponseEntity.ok().body(message);
    }

}
