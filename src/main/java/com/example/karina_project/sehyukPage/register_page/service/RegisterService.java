package com.example.karina_project.sehyukPage.register_page.service;

import com.example.karina_project.domain.User;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.register_page.request.RegisterRequest;
import com.example.karina_project.sehyukPage.register_page.request.RegisterRequestOnlyId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String registerProcess(RegisterRequest request, String s3Url) {

        String loginId = request.getLoginId();
        String password = request.getPassword();
        String rawRole = request.getRole();
        String name = request.getName();
        String phoneNumber = request.getPhoneNumber();
        String mainAddress = request.getMainAddress();
        String detailAddress = request.getDetailAddress();

        Boolean isExist = userRepository.existsByLoginId(loginId);

        if (isExist) {
            return "User already exists";
        }

        String role = "ROLE_" + rawRole.toUpperCase();

        User newUser = new User();

        newUser.setLoginId(loginId);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setRole(role);
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setMainAddress(mainAddress);
        newUser.setDetailAddress(detailAddress);
        newUser.setAuthenticationFile(s3Url);

        userRepository.save(newUser);

        return "User created";
    }

    public String IdValidation(RegisterRequestOnlyId request) {

        Boolean isExit = userRepository.existsByLoginId(request.getLoginId());

        if(isExit){
            return "Invalid Id";
        }

        return "Valid Id";
    }


}
