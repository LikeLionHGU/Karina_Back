package com.example.karina_project.sehyukPage.home_page;

import com.example.karina_project.sehyukPage.home_page.response.HomePageResponse;
import com.example.karina_project.sehyukPage.home_page.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService homePageService;

    @GetMapping("/fisher/home")
    public ResponseEntity<List<HomePageResponse>> fisher_home_page() {
         List<HomePageResponse> homePageResponses = homePageService.getArticlesByTime().stream().map(HomePageResponse::from).collect(Collectors.toList());

         return ResponseEntity.ok(homePageResponses);
    }


    @GetMapping("/factory/home")
    public ResponseEntity<List<HomePageResponse>> factory_home_page() {
        List<HomePageResponse> homePageResponses = homePageService.getArticlesByTime().stream().map(HomePageResponse::from).collect(Collectors.toList());

        return ResponseEntity.ok(homePageResponses);
    }

}
