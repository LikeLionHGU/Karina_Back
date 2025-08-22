package com.example.karina_project.sehyukPage.register_page;

import com.example.karina_project.sehyukPage.register_page.request.RegisterRequest;
import com.example.karina_project.sehyukPage.register_page.request.RegisterRequestOnlyId;
import com.example.karina_project.sehyukPage.register_page.service.FileService;
import com.example.karina_project.sehyukPage.register_page.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/user")
public class RegisterController {

    private final RegisterService registerService;
    private final FileService fileService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestPart("user") RegisterRequest registerRequest, @RequestPart("file") MultipartFile file) throws IOException {
        String s3Url = fileService.uploadFile(file, "verification/");
        String message = registerService.registerProcess(registerRequest, s3Url);

        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/register/idValidation")
    public ResponseEntity<String> idValidate(@RequestBody RegisterRequestOnlyId registerRequestOnlyId) {
        String message = registerService.IdValidation(registerRequestOnlyId);

        return ResponseEntity.ok().body(message);
    }
}
