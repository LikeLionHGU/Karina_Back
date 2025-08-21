package com.example.karina_project.sehyukPage.home_page;

import com.example.karina_project.sehyukPage.home_page.response.HomePageResponse;
import com.example.karina_project.sehyukPage.home_page.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService homePageService;

    @GetMapping({"/fisher/home", "/factory/home"})
    public ResponseEntity<List<HomePageResponse>> home_page() {
         List<HomePageResponse> homePageResponses = homePageService.getArticlesByTime().stream().map(HomePageResponse::from).collect(Collectors.toList());

         return ResponseEntity.ok(homePageResponses);
    }

    @GetMapping({"/fisher/search", "/factory/search"})
    public ResponseEntity<List<HomePageResponse>> search(@RequestParam("fishSpecies") String fishSpecies) {
        List<HomePageResponse> homePageResponses = homePageService.getArticlesByFishSpecies(fishSpecies).stream().map(HomePageResponse::from).collect(Collectors.toList());

        return ResponseEntity.ok(homePageResponses);
    }

}
