package com.example.karina_project.sehyukPage.home_page;

import com.example.karina_project.sehyukPage.home_page.response.FisherResponse;
import com.example.karina_project.sehyukPage.home_page.service.FisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final FisherService fisherService;

    @GetMapping("/fisher/home")
    public ResponseEntity<List<FisherResponse>> fisher_home() {
         List<FisherResponse> fisherResponses = fisherService.getArticlesByTime().stream().map(FisherResponse::from).collect(Collectors.toList());

         return ResponseEntity.ok(fisherResponses);
    }

//    @GetMapping("/factory/home")
//    public ResponseEntity<FisherResponse> factory_home() {
//
//    }

}
