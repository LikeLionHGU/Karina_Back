package com.example.karina_project.sehyukPage.detail_page;

import com.example.karina_project.sehyukPage.detail_page.request.DetailPageRequest;
import com.example.karina_project.sehyukPage.detail_page.response.DetailPageResponse;
import com.example.karina_project.sehyukPage.detail_page.service.DetailPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/factory")
public class DetailPageController {

    private final DetailPageService detailPageService;

    @GetMapping("/detail")
    public ResponseEntity<DetailPageResponse> getDetailPage(@RequestBody DetailPageRequest request) {
        DetailPageResponse detailPageResponse = DetailPageResponse.from(detailPageService.getDetailArticleInfo(request));

        return ResponseEntity.ok().body(detailPageResponse);
    }

    @PostMapping("/detail")
    public ResponseEntity<String> postMatchingRequest(@RequestBody DetailPageRequest request) {
        String message = detailPageService.sendMatchingRequest(request);

        return ResponseEntity.ok().body(message);
    }

}
